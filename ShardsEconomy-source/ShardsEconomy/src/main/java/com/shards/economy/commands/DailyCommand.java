package com.shards.economy.commands;

import com.shards.economy.ShardsEconomy;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DailyCommand implements CommandExecutor {
    private final ShardsEconomy plugin;
    public DailyCommand(ShardsEconomy plugin) { this.plugin = plugin; }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player player)) { sender.sendMessage("Player only."); return true; }

        if (!plugin.getDailyManager().canClaim(player.getUniqueId())) {
            player.sendMessage(ChatColor.RED + "✗ You've already claimed your daily reward today! Come back tomorrow.");
            return true;
        }

        double amount = plugin.getDailyManager().getDailyAmount();
        plugin.getBalanceManager().addBalance(player.getUniqueId(), amount);
        plugin.getDailyManager().setClaimed(player.getUniqueId());

        player.sendMessage(ChatColor.GOLD + "✦ " + ChatColor.YELLOW + "Daily Reward Claimed!");
        player.sendMessage(ChatColor.GREEN + "  + " + ChatColor.GOLD +
            plugin.getBalanceManager().formatBalance(amount) + " Shards" +
            ChatColor.GRAY + " added to your balance.");
        player.sendMessage(ChatColor.GRAY + "  New balance: " + ChatColor.YELLOW +
            plugin.getBalanceManager().formatBalance(plugin.getBalanceManager().getBalance(player.getUniqueId())) +
            ChatColor.GOLD + " Shards");
        return true;
    }
}
