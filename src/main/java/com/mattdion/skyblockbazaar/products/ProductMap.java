package com.mattdion.skyblockbazaar.products;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class ProductMap {
    public static final long PRODUCTS_UPDATE_INTERVAL_MINUTES = 2;
    private final Map<ProductID, Product> products;
    private Instant lastUpdated;

    /**
     * Constructs the empty {@link ProductMap}.
     */
    public ProductMap() {
        this.products = new HashMap<>();
    }

    public void addToProductMap(Product product) {
        products.put(product.getId(), product);
    }

    public Map<ProductID, Product> getProducts() {
        return new HashMap<>(products);
    }

    public Instant getLastUpdated() {
        return lastUpdated;
    }
}
