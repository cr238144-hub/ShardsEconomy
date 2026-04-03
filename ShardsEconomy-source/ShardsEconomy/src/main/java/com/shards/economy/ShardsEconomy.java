package com.shards.economy;

import com.shards.economy.commands.*;
import com.shards.economy.listeners.SellWandListener;
import com.shards.economy.managers.BalanceManager;
import com.shards.economy.managers.DailyManager;
import com.shards.economy.shop.ShopManager;
import org.bukkit.plugin.java.JavaPlugin;

public class ShardsEconomy extends JavaPlugin {

    private static ShardsEconomy instance;
    private BalanceManager balanceManager;
    private ShopManager shopManager;
    private DailyManager dailyManager;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        balanceManager = new BalanceManager(this);
        shopManager    = new ShopManager(this);
        dailyManager   = new DailyManager(this);

        getCommand("shop").setExecutor(new ShopCommand(this));
        getCommand("balance").setExecutor(new BalanceCommand(this));
        getCommand("pay").setExecutor(new PayCommand(this));
        getCommand("baltop").setExecutor(new BaltopCommand(this));
        getCommand("daily").setExecutor(new DailyCommand(this));
        getCommand("sellwand").setExecutor(new SellWandCommand(this));
        getCommand("eco").setExecutor(new EcoCommand(this));

        getServer().getPluginManager().registerEvents(new SellWandListener(this), this);
        getServer().getPluginManager().registerEvents(shopManager, this);

        getLogger().info("ShardsEconomy enabled!");
    }

    @Override
    public void onDisable() {
        if (balanceManager != null) balanceManager.saveAll();
        getLogger().info("ShardsEconomy disabled — data saved.");
    }

    public static ShardsEconomy getInstance() { return instance; }
    public BalanceManager getBalanceManager()  { return balanceManager; }
    public ShopManager getShopManager()        { return shopManager; }
    public DailyManager getDailyManager()      { return dailyManager; }
}
