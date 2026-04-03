package com.shards.economy.commands;

import com.shards.economy.ShardsEconomy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BalanceCommand implements CommandExecutor {
    private final ShardsEconomy plugin;
    public BalanceCommand(ShardsEconomy plugin) { this.plugin = plugin; }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            if (!(sender instanceof Player player)) {
                sender.sendMessage("Specify a player name.");
                return true;
            }
            double bal = plugin.getBalanceManager().getBalance(player.getUniqueId());
            player.sendMessage(ChatColor.GOLD + "✦ " + ChatColor.YELLOW + "Your balance: " +
                ChatColor.WHITE + plugin.getBalanceManager().formatBalance(bal) + ChatColor.GOLD + " Shards");
        } else {
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                sender.sendMessage(ChatColor.RED + "Player not found.");
                return true;
            }
            double bal = plugin.getBalanceManager().getBalance(target.getUniqueId());
            sender.sendMessage(ChatColor.GOLD + "✦ " + ChatColor.YELLOW + target.getName() + "'s balance: " +
                ChatColor.WHITE + plugin.getBalanceManager().formatBalance(bal) + ChatColor.GOLD + " Shards");
        }
        return true;
    }
}
