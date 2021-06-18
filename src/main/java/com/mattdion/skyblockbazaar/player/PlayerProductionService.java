package com.mattdion.skyblockbazaar.player;

import com.mattdion.skyblockbazaar.exceptions.NoPlayerFoundException;
import com.mattdion.skyblockbazaar.hypixelapi.BazaarReplyService;
import com.mattdion.skyblockbazaar.hypixelapi.PlayerReplyService;
import com.mattdion.skyblockbazaar.minions.Minion;
import com.mattdion.skyblockbazaar.minions.MinionID;
import com.mattdion.skyblockbazaar.products.Product;
import com.mattdion.skyblockbazaar.products.ProductConstants;
import com.mattdion.skyblockbazaar.products.ProductID;
import net.hypixel.api.reply.skyblock.BazaarReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.TimeoutException;

@Service
public class PlayerProductionService {
    private static final Logger log = LoggerFactory.getLogger(PlayerProductionService.class);
    private static final double PROFIT_MARGIN = 0.05;
    private final PlayerMap playerMap;
    private final PlayerReplyService playerReplyService;
    private final BazaarReplyService bazaarReplyService;

    @Autowired
    public PlayerProductionService(PlayerMap playerMap, PlayerReplyService playerReplyService, BazaarReplyService bazaarReplyService) {
        this.playerMap = playerMap;
        this.playerReplyService = playerReplyService;
        this.bazaarReplyService = bazaarReplyService;
    }

    public Player addPlayer(String name) throws NoPlayerFoundException, TimeoutException {
        Player player = playerMap.getPlayer(name);
        if (player == null) return playerMap.addPlayer(name);

        if (Duration.between(player.getLastUpdated(), Instant.now())
                .compareTo(Duration.ofMinutes(Player.UUID_UPDATE_INTERVAL_MINUTES)) <= 0) {
            return playerMap.getPlayer(name);
        }
        playerMap.removePlayer(name);
        return playerMap.addPlayer(name);
    }

    public void updateMinionMap(Player player) throws NoPlayerFoundException, TimeoutException {
        Map<MinionID, Integer> minionLevels = playerReplyService.getMinionLevelsMap(player);

        player.updateMinionMap(minionLevels);
    }

    public void getPlayerProducts(Player player) throws TimeoutException {
        BazaarReply bazaarReply = bazaarReplyService.getBazaarReply();

        for (Map.Entry<String, BazaarReply.Product> entry : bazaarReply.getProducts().entrySet()) {
            BazaarReply.Product product = entry.getValue();
            String productName = product.getProductId();
            BazaarReply.Product.Status status = product.getQuickStatus();

            double instantSellPrice = status.getSellPrice();
            double expectedSellPrice = status.getBuyPrice();
            expectedSellPrice *= (1 - PROFIT_MARGIN);

            ProductID productId = ProductID.getProductId(productName);
            Product playerProduct = new Product(productId);

            Map<MinionID, Double> productMinions = ProductConstants.productInputNeeded.get(productId);
            if (productMinions == null) {
                log.info("Couldn't find product " + productName + " in productsInputNeeded Map");
                continue;
            }

            double totalprod;
            // if Product depends only on 1 Minion, computing is easy
            if (productMinions.size() == 1) {
                Map.Entry<MinionID, Double> e =
                        productMinions
                                .entrySet()
                                .iterator()
                                .next();
                Minion minion = player
                        .getMinionMap()
                        .getMinion(e.getKey());

                totalprod = minion.getTotalProductionPerHour() / e.getValue();
            }

            // if there are multiple minions, use different solution
            // maybe linear equation solver?
            else {
                // TODO: implement multiple Minion Product calculation
                log.warn("Skipping Product " + productName);
                continue;
            }

            playerProduct.setInstantSellPrice(instantSellPrice);
            playerProduct.setExpectedSellPrice(expectedSellPrice);
            playerProduct.setMoneyPerHour(totalprod * expectedSellPrice);

            player.addToProductMap(playerProduct);
        }
    }
}
