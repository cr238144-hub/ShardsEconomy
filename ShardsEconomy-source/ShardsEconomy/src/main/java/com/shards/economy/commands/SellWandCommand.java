package com.shards.economy.commands;

import com.shards.economy.ShardsEconomy;
import com.shards.economy.listeners.SellWandListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SellWandCommand implements CommandExecutor {
    private final ShardsEconomy plugin;
    public SellWandCommand(ShardsEconomy plugin) { this.plugin = plugin; }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player target;
        if (args.length > 0) {
            target = Bukkit.getPlayer(args[0]);
            if (target == null) { sender.sendMessage(ChatColor.RED + "Player not found."); return true; }
        } else {
            if (!(sender instanceof Player)) { sender.sendMessage("Specify a player."); return true; }
            target = (Player) sender;
        }

        ItemStack wand = SellWandListener.createSellWand();
        target.getInventory().addItem(wand);
        target.sendMessage(ChatColor.GOLD + "✦ " + ChatColor.YELLOW + "You received a Sell Wand! Right-click a chest to sell its contents.");
        if (!sender.equals(target)) {
            sender.sendMessage(ChatColor.GREEN + "✓ Gave sell wand to " + target.getName() + ".");
        }
        return true;
    }
}
