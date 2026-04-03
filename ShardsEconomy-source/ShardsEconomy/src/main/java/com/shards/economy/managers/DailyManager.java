package com.shards.economy.managers;

import com.shards.economy.ShardsEconomy;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;

public class DailyManager {

    private final ShardsEconomy plugin;
    private final File dataFile;
    private FileConfiguration data;
    private static final double DAILY_AMOUNT = 500.0;

    public DailyManager(ShardsEconomy plugin) {
        this.plugin   = plugin;
        this.dataFile = new File(plugin.getDataFolder(), "daily.yml");
        if (!dataFile.exists()) {
            try { dataFile.createNewFile(); } catch (IOException e) { e.printStackTrace(); }
        }
        this.data = YamlConfiguration.loadConfiguration(dataFile);
    }

    public boolean canClaim(UUID uuid) {
        String last = data.getString(uuid.toString());
        if (last == null) return true;
        return !last.equals(LocalDate.now().toString());
    }

    public void setClaimed(UUID uuid) {
        data.set(uuid.toString(), LocalDate.now().toString());
        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> {
            try { data.save(dataFile); } catch (IOException e) { e.printStackTrace(); }
        });
    }

    public double getDailyAmount() { return DAILY_AMOUNT; }
}
