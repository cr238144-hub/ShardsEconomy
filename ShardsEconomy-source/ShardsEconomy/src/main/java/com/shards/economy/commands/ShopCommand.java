package com.shards.economy.commands;

import com.shards.economy.ShardsEconomy;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ShopCommand implements CommandExecutor {
    private final ShardsEconomy plugin;
    public ShopCommand(ShardsEconomy plugin) { this.plugin = plugin; }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Player only.");
            return true;
        }
        plugin.getShopManager().openMainMenu(player);
        return true;
    }
}
