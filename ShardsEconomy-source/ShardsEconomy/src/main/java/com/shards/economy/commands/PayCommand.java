package com.shards.economy.commands;

import com.shards.economy.ShardsEconomy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PayCommand implements CommandExecutor {
    private final ShardsEconomy plugin;
    public PayCommand(ShardsEconomy plugin) { this.plugin = plugin; }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player player)) { sender.sendMessage("Player only."); return true; }
        if (args.length < 2) {
            player.sendMessage(ChatColor.RED + "Usage: /pay <player> <amount>");
            return true;
        }
        Player target = Bukkit.getPlayer(args[0]);
        if (target == null || target == player) {
            player.sendMessage(ChatColor.RED + "✗ Invalid player.");
            return true;
        }
        double amount;
        try {
            amount = Double.parseDouble(args[1]);
        } catch (NumberFormatException e) {
            player.sendMessage(ChatColor.RED + "✗ Invalid amount.");
            return true;
        }
        if (amount <= 0) {
            player.sendMessage(ChatColor.RED + "✗ Amount must be positive.");
            return true;
        }
        if (!plugin.getBalanceManager().takeBalance(player.getUniqueId(), amount)) {
            player.sendMessage(ChatColor.RED + "✗ Insufficient Shards.");
            return true;
        }
        plugin.getBalanceManager().addBalance(target.getUniqueId(), amount);
        String fmt = plugin.getBalanceManager().formatBalance(amount);
        player.sendMessage(ChatColor.GREEN + "✓ Sent " + ChatColor.GOLD + fmt + " Shards" + ChatColor.GREEN + " to " + target.getName() + ".");
        target.sendMessage(ChatColor.GREEN + "✓ Received " + ChatColor.GOLD + fmt + " Shards" + ChatColor.GREEN + " from " + player.getName() + ".");
        return true;
    }
}
