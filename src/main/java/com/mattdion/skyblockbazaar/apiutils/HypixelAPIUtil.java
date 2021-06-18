package com.mattdion.skyblockbazaar.apiutils;

import com.mattdion.skyblockbazaar.exceptions.HypixelAPIConnectionException;
import net.hypixel.api.HypixelAPI;
import net.hypixel.api.reply.skyblock.BazaarReply;
import net.hypixel.api.reply.skyblock.SkyBlockProfileReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Repository
public class HypixelAPIUtil {
    public static final long UPDATE_INTERVAL_MINUTES = 1;
    public static final long UPDATE_TIMEOUT_SECONDS = 20;
    private static final HypixelAPI hypixelAPI = new HypixelAPI(UUID.fromString(System.getenv("HYPIXEL_API_KEY")));
    private static final Logger log = LoggerFactory.getLogger(HypixelAPIUtil.class);
    private BazaarReply bazaarReply;
    private Instant lastUpdated;

    private void updateProducts() {
        hypixelAPIGetBazaar();
        lastUpdated = Instant.now();
        log.info("Updated products from Hypixel API");
    }

    private void hypixelAPIGetBazaar() {
        try {
            bazaarReply = hypixelAPI.getBazaar().get(UPDATE_TIMEOUT_SECONDS, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException e) {
            throw new HypixelAPIConnectionException("Error connecting to Hypixel API", e);
        } catch (TimeoutException e) {
            throw new ResponseStatusException(HttpStatus.GATEWAY_TIMEOUT, "Hypixel API timeout", e);
        }
    }

    public void requestUpdateProducts() {
        if (lastUpdated == null ||
                Duration.between(lastUpdated, Instant.now())
                        .compareTo(Duration.ofMinutes(UPDATE_INTERVAL_MINUTES)) > 0) {
            updateProducts();
        }
    }

    public void forceUpdateProducts() {
        updateProducts();
    }

    public BazaarReply getBazaarReply() {
        return bazaarReply;
    }

    public SkyBlockProfileReply getSkyblockProfileReply(String uuid) {
        try {
            return hypixelAPI.getSkyBlockProfile(uuid).get(UPDATE_TIMEOUT_SECONDS, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException e) {
            throw new HypixelAPIConnectionException("Error connecting to Hypixel API", e);
        } catch (TimeoutException e) {
            throw new ResponseStatusException(HttpStatus.GATEWAY_TIMEOUT, "Hypixel API timeout", e);
        }
    }


}
