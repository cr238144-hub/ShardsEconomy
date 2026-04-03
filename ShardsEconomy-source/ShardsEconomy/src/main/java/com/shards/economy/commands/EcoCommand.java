package com.shards.economy.commands;

import com.shards.economy.ShardsEconomy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EcoCommand implements CommandExecutor {
    private final ShardsEconomy plugin;
    public EcoCommand(ShardsEconomy plugin) { this.plugin = plugin; }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("shards.eco")) {
            sender.sendMessage(ChatColor.RED + "No permission.");
            return true;
        }
        if (args.length < 3) {
            sender.sendMessage(ChatColor.RED + "Usage: /eco <give|take|set> <player> <amount>");
            return true;
        }

        String action = args[0].toLowerCase();
        Player target = Bukkit.getPlayer(args[1]);
        if (target == null) {
            sender.sendMessage(ChatColor.RED + "Player not found.");
            return true;
        }

        double amount;
        try {
            amount = Double.parseDouble(args[2]);
        } catch (NumberFormatException e) {
            sender.sendMessage(ChatColor.RED + "Invalid amount.");
            return true;
        }

        var bm = plugin.getBalanceManager();
        switch (action) {
            case "give" -> {
                bm.addBalance(target.getUniqueId(), amount);
                sender.sendMessage(ChatColor.GREEN + "✓ Gave " + bm.formatBalance(amount) + " Shards to " + target.getName() + ".");
                target.sendMessage(ChatColor.GREEN + "✓ Admin gave you " + ChatColor.GOLD + bm.formatBalance(amount) + " Shards" + ChatColor.GREEN + ".");
            }
            case "take" -> {
                bm.takeBalance(target.getUniqueId(), amount);
                sender.sendMessage(ChatColor.GREEN + "✓ Took " + bm.formatBalance(amount) + " Shards from " + target.getName() + ".");
                target.sendMessage(ChatColor.RED + "✗ Admin removed " + ChatColor.GOLD + bm.formatBalance(amount) + " Shards" + ChatColor.RED + " from your balance.");
            }
            case "set" -> {
                bm.setBalance(target.getUniqueId(), amount);
                sender.sendMessage(ChatColor.GREEN + "✓ Set " + target.getName() + "'s balance to " + bm.formatBalance(amount) + " Shards.");
                target.sendMessage(ChatColor.YELLOW + "Your balance was set to " + ChatColor.GOLD + bm.formatBalance(amount) + " Shards" + ChatColor.YELLOW + " by an admin.");
            }
            default -> sender.sendMessage(ChatColor.RED + "Unknown action. Use give, take, or set.");
        }
        return true;
    }
}
