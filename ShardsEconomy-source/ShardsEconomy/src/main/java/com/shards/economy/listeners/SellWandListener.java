package com.shards.economy.listeners;

import com.shards.economy.ShardsEconomy;
import com.shards.economy.shop.ShopItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class SellWandListener implements Listener {

    private final ShardsEconomy plugin;
    private static final String WAND_NAME = ChatColor.GOLD + "✦ Sell Wand";

    public SellWandListener(ShardsEconomy plugin) {
        this.plugin = plugin;
    }

    public static ItemStack createSellWand() {
        ItemStack wand = new ItemStack(Material.BLAZE_ROD);
        ItemMeta meta = wand.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(WAND_NAME);
            meta.setLore(Arrays.asList(
                ChatColor.GRAY + "Right-click a chest to sell",
                ChatColor.GRAY + "all sellable contents!",
                "",
                ChatColor.YELLOW + "Uses: " + ChatColor.WHITE + "Unlimited"
            ));
            wand.setItemMeta(meta);
        }
        return wand;
    }

    public static boolean isSellWand(ItemStack item) {
        if (item == null || item.getType() != Material.BLAZE_ROD) return false;
        ItemMeta meta = item.getItemMeta();
        return meta != null && WAND_NAME.equals(meta.getDisplayName());
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        Player player = e.getPlayer();
        ItemStack hand = player.getInventory().getItemInMainHand();

        if (!isSellWand(hand)) return;
        e.setCancelled(true);

        Block block = e.getClickedBlock();
        if (block == null) return;
        if (block.getType() != Material.CHEST && block.getType() != Material.TRAPPED_CHEST) {
            player.sendMessage(ChatColor.RED + "✗ Right-click a chest!");
            return;
        }

        Chest chest = (Chest) block.getState();
        Inventory inv;
        if (chest.getInventory().getHolder() instanceof DoubleChest) {
            inv = ((DoubleChest) chest.getInventory().getHolder()).getInventory();
        } else {
            inv = chest.getInventory();
        }

        double total = 0;
        int soldItems = 0;

        for (int i = 0; i < inv.getSize(); i++) {
            ItemStack item = inv.getItem(i);
            if (item == null || item.getType() == Material.AIR) continue;

            ShopItem si = plugin.getShopManager().getItemByMaterial(item.getType());
            if (si == null || !si.canSell()) continue;

            double earned = si.getSellPrice() * item.getAmount();
            total += earned;
            soldItems += item.getAmount();
            inv.setItem(i, null);
        }

        if (soldItems == 0) {
            player.sendMessage(ChatColor.RED + "✗ No sellable items found in the chest.");
            return;
        }

        plugin.getBalanceManager().addBalance(player.getUniqueId(), total);
        player.sendMessage(ChatColor.GREEN + "✓ Sold " + soldItems + " items for " +
            ChatColor.GOLD + formatPrice(total) + " Shards" + ChatColor.GREEN + "!");
    }

    private String formatPrice(double price) {
        if (price >= 1_000_000) return String.format("%.1fM", price / 1_000_000);
        if (price >= 1_000)     return String.format("%.1fK", price / 1_000);
        return String.format("%.0f", price);
    }
}
