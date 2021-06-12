package com.mattdion.skyblockbazaar.apiutils;

import net.hypixel.api.HypixelAPI;
import net.hypixel.api.reply.skyblock.BazaarReply;
import net.hypixel.api.reply.skyblock.SkyBlockProfileReply;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Repository
public class HypixelAPIUtil {
    private static final HypixelAPI hypixelAPI = new HypixelAPI(UUID.fromString(System.getenv("HYPIXEL_API_KEY")));
    public static final long UPDATE_INTERVAL_MINUTES = 1;
    public static final long UPDATE_TIMEOUT_SECONDS = 20;
    private BazaarReply bazaarReply;
    private Instant lastUpdated;

    public HypixelAPIUtil() throws TimeoutException {
        updateProducts();
    }

    private void updateProducts() throws TimeoutException {
        try {
            bazaarReply = hypixelAPI.getBazaar().get(UPDATE_TIMEOUT_SECONDS, TimeUnit.SECONDS);
            lastUpdated = Instant.now();
            System.out.println("UPDATED PRODUCTS");
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void requestUpdateProducts() throws TimeoutException {
        if (Duration.between(lastUpdated, Instant.now())
                .compareTo(Duration.ofMinutes(UPDATE_INTERVAL_MINUTES)) > 0) {
            updateProducts();
        }
    }

    public void forceUpdateProducts() throws TimeoutException {
        updateProducts();
    }

    public BazaarReply getBazaarReply() {
        return bazaarReply;
    }

    public SkyBlockProfileReply getSkyblockProfileReply(String uuid) {
        try {
            return hypixelAPI.getSkyBlockProfile(uuid).get(UPDATE_TIMEOUT_SECONDS, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return null;
    }
}
