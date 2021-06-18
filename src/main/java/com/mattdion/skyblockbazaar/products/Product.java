package com.mattdion.skyblockbazaar.products;

import com.mattdion.skyblockbazaar.minions.MinionID;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Product {
    private final ProductID id;
    private final Map<MinionID, Double> minionOutputNeeded;
    private double instantSellPrice;
    private double expectedSellPrice;
    private double moneyPerHour;

    public Product(ProductID id) {
        this.id = id;

        this.minionOutputNeeded = new HashMap<>();
    }

    public ProductID getId() {
        return id;
    }

    public double getInstantSellPrice() {
        return instantSellPrice;
    }

    public void setInstantSellPrice(double instantSellPrice) {
        this.instantSellPrice = instantSellPrice;
    }

    public double getExpectedSellPrice() {
        return expectedSellPrice;
    }

    public void setExpectedSellPrice(double expectedSellPrice) {
        this.expectedSellPrice = expectedSellPrice;
    }

    public double getMoneyPerHour() {
        return moneyPerHour;
    }

    public void setMoneyPerHour(double moneyPerHour) {
        this.moneyPerHour = moneyPerHour;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product product)) return false;
        return Double.compare(product.getInstantSellPrice(), getInstantSellPrice()) == 0 && Double.compare(product.getExpectedSellPrice(), getExpectedSellPrice()) == 0 && Double.compare(product.getMoneyPerHour(), getMoneyPerHour()) == 0 && getId() == product.getId() && Objects.equals(minionOutputNeeded, product.minionOutputNeeded);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
