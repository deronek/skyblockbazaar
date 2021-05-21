package com.mattdion.skyblockbazaar.hypixelapi;

import net.hypixel.api.HypixelAPI;
import net.hypixel.api.reply.skyblock.BazaarReply;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Repository
public class HypixelAPIUtil {
    public static final HypixelAPI hypixelAPI = new HypixelAPI(UUID.fromString(System.getenv("HYPIXEL_API_KEY")));
    private static final long UPDATE_INTERVAL_MINUTES = 1;
    private BazaarReply bazaarReply;
    private Instant lastUpdated;

    public HypixelAPIUtil() {
        updateProducts();
    }

    private void updateProducts() {
        try {
            bazaarReply = hypixelAPI.getBazaar().get();
            lastUpdated = Instant.now();
            System.out.println("UPDATED PRODUCTS");
        } catch (ExecutionException e) {
            System.err.println("Failed to update products");
        } catch (InterruptedException e) {
            System.err.println("Interrupted updating products");
        }
    }

    public void requestUpdateProducts() {
        if (Duration.between(lastUpdated, Instant.now())
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
}
