package com.shards.economy.commands;

import com.shards.economy.ShardsEconomy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.*;

public class BaltopCommand implements CommandExecutor {
    private final ShardsEconomy plugin;
    public BaltopCommand(ShardsEconomy plugin) { this.plugin = plugin; }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Map<UUID, Double> all = plugin.getBalanceManager().getAllBalances();
        List<Map.Entry<UUID, Double>> sorted = new ArrayList<>(all.entrySet());
        sorted.sort((a, b) -> Double.compare(b.getValue(), a.getValue()));

        sender.sendMessage(ChatColor.GOLD + "━━━━━━━━━━━━━━━━━━━━━━━━━━");
        sender.sendMessage(ChatColor.GOLD + "  ✦ " + ChatColor.YELLOW + "Top Richest Players" + ChatColor.GOLD + " ✦");
        sender.sendMessage(ChatColor.GOLD + "━━━━━━━━━━━━━━━━━━━━━━━━━━");

        int rank = 1;
        for (Map.Entry<UUID, Double> entry : sorted) {
            if (rank > 10) break;
            String name = Bukkit.getOfflinePlayer(entry.getKey()).getName();
            if (name == null) name = "Unknown";
            String prefix = rank == 1 ? ChatColor.GOLD + "#1" :
                            rank == 2 ? ChatColor.GRAY + "#2" :
                            rank == 3 ? ChatColor.RED  + "#3" :
                            ChatColor.WHITE + "#" + rank;
            sender.sendMessage(prefix + ChatColor.WHITE + " " + name + ChatColor.GRAY +
                " — " + ChatColor.YELLOW + plugin.getBalanceManager().formatBalance(entry.getValue()) +
                ChatColor.GOLD + " Shards");
            rank++;
        }

        if (sorted.isEmpty()) {
            sender.sendMessage(ChatColor.GRAY + "No data yet.");
        }
        sender.sendMessage(ChatColor.GOLD + "━━━━━━━━━━━━━━━━━━━━━━━━━━");
        return true;
    }
}
