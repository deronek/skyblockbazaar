package com.mattdion.skyblockbazaar.products;

import net.hypixel.api.reply.skyblock.BazaarReply;

import java.util.HashMap;
import java.util.Map;

public class MyBazaarReply {
    private Map<String, MyProduct> products = new HashMap<>();

    // create map equivalent, but with MyProduct instead of Product
    public MyBazaarReply(BazaarReply bazaarReply) {
        for(BazaarReply.Product product : bazaarReply.getProducts().values()) {
            MyProduct myProduct = new MyProduct(product);
            products.put(myProduct.product.getProductId(), myProduct);
        }
    }

    public Map<String, MyProduct> getProducts() {
        return products;
    }

    public MyProduct getProduct(String productId) {
        return products.get(productId);
    }

    public class MyProduct {
        private final BazaarReply.Product product;

        // TODO: total production per hour etc...

        public MyProduct(BazaarReply.Product product) {
            this.product = product;
        }
    }
}
