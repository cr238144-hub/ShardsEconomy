package com.shards.economy.shop;

import org.bukkit.Material;

public class ShopItem {
    private final Material material;
    private final String displayName;
    private final double buyPrice;
    private final double sellPrice; // 0 = cannot sell
    private final String category;

    public ShopItem(Material material, String displayName, double buyPrice, double sellPrice, String category) {
        this.material    = material;
        this.displayName = displayName;
        this.buyPrice    = buyPrice;
        this.sellPrice   = sellPrice;
        this.category    = category;
    }

    public Material getMaterial()    { return material; }
    public String getDisplayName()   { return displayName; }
    public double getBuyPrice()      { return buyPrice; }
    public double getSellPrice()     { return sellPrice; }
    public boolean canSell()         { return sellPrice > 0; }
    public String getCategory()      { return category; }
}
