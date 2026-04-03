package com.shards.economy.managers;

import com.shards.economy.ShardsEconomy;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class BalanceManager {

    private final ShardsEconomy plugin;
    private final File dataFile;
    private FileConfiguration data;
    private final Map<UUID, Double> cache = new HashMap<>();

    public BalanceManager(ShardsEconomy plugin) {
        this.plugin  = plugin;
        this.dataFile = new File(plugin.getDataFolder(), "balances.yml");
        if (!dataFile.exists()) {
            try { dataFile.createNewFile(); } catch (IOException e) { e.printStackTrace(); }
        }
        this.data = YamlConfiguration.loadConfiguration(dataFile);
        loadAll();
    }

    private void loadAll() {
        if (data.getConfigurationSection("balances") == null) return;
        for (String key : data.getConfigurationSection("balances").getKeys(false)) {
            try {
                cache.put(UUID.fromString(key), data.getDouble("balances." + key));
            } catch (IllegalArgumentException ignored) {}
        }
    }

    public void saveAll() {
        for (Map.Entry<UUID, Double> entry : cache.entrySet()) {
            data.set("balances." + entry.getKey(), entry.getValue());
        }
        try { data.save(dataFile); } catch (IOException e) { e.printStackTrace(); }
    }

    public double getBalance(UUID uuid) {
        return cache.getOrDefault(uuid, 0.0);
    }

    public void setBalance(UUID uuid, double amount) {
        cache.put(uuid, Math.max(0, amount));
        saveAsync();
    }

    public void addBalance(UUID uuid, double amount) {
        setBalance(uuid, getBalance(uuid) + amount);
    }

    public boolean takeBalance(UUID uuid, double amount) {
        double current = getBalance(uuid);
        if (current < amount) return false;
        setBalance(uuid, current - amount);
        return true;
    }

    public Map<UUID, Double> getAllBalances() {
        return Collections.unmodifiableMap(cache);
    }

    private void saveAsync() {
        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, this::saveAll);
    }

    public String formatBalance(double amount) {
        if (amount >= 1_000_000) return String.format("%.1fM", amount / 1_000_000);
        if (amount >= 1_000)     return String.format("%.1fK", amount / 1_000);
        return String.format("%.0f", amount);
    }
}
