package com.shards.economy.shop;

import com.shards.economy.ShardsEconomy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class ShopManager implements Listener {

    private final ShardsEconomy plugin;
    private final Map<String, List<ShopItem>> categories = new LinkedHashMap<>();
    private final Map<Material, ShopItem> itemLookup = new HashMap<>();

    // GUI title markers
    private static final String MAIN_TITLE   = ChatColor.GOLD + "✦ " + ChatColor.YELLOW + "Shards Shop" + ChatColor.GOLD + " ✦";
    private static final String BUY_PREFIX   = ChatColor.GREEN + "Buy: ";
    private static final String SELL_PREFIX  = ChatColor.AQUA + "Sell: ";

    public ShopManager(ShardsEconomy plugin) {
        this.plugin = plugin;
        registerItems();
    }

    private void registerItems() {
        // ── BLOCKS ──────────────────────────────────────────────────────────
        List<ShopItem> blocks = new ArrayList<>();
        add(blocks, Material.DIRT,          "Dirt",          5,     2,     "Blocks");
        add(blocks, Material.GRASS_BLOCK,   "Grass Block",   8,     3,     "Blocks");
        add(blocks, Material.SAND,          "Sand",          6,     2,     "Blocks");
        add(blocks, Material.GRAVEL,        "Gravel",        6,     2,     "Blocks");
        add(blocks, Material.STONE,         "Stone",         8,     3,     "Blocks");
        add(blocks, Material.COBBLESTONE,   "Cobblestone",   5,     1,     "Blocks");
        add(blocks, Material.GRANITE,       "Granite",       10,    4,     "Blocks");
        add(blocks, Material.DIORITE,       "Diorite",       10,    4,     "Blocks");
        add(blocks, Material.ANDESITE,      "Andesite",      10,    4,     "Blocks");
        add(blocks, Material.OAK_LOG,       "Oak Log",       10,    4,     "Blocks");
        add(blocks, Material.BIRCH_LOG,     "Birch Log",     10,    4,     "Blocks");
        add(blocks, Material.SPRUCE_LOG,    "Spruce Log",    10,    4,     "Blocks");
        add(blocks, Material.JUNGLE_LOG,    "Jungle Log",    12,    5,     "Blocks");
        add(blocks, Material.DARK_OAK_LOG,  "Dark Oak Log",  12,    5,     "Blocks");
        add(blocks, Material.ACACIA_LOG,    "Acacia Log",    12,    5,     "Blocks");
        add(blocks, Material.CHERRY_LOG,    "Cherry Log",    15,    6,     "Blocks");
        add(blocks, Material.GLASS,         "Glass",         15,    5,     "Blocks");
        add(blocks, Material.OBSIDIAN,      "Obsidian",      50,    20,    "Blocks");
        add(blocks, Material.CRYING_OBSIDIAN,"Crying Obsidian",80,  30,    "Blocks");
        add(blocks, Material.BLACKSTONE,    "Blackstone",    20,    8,     "Blocks");
        add(blocks, Material.DEEPSLATE,     "Deepslate",     15,    6,     "Blocks");
        add(blocks, Material.TUFF,          "Tuff",          12,    4,     "Blocks");
        add(blocks, Material.CALCITE,       "Calcite",       12,    4,     "Blocks");
        add(blocks, Material.DRIPSTONE_BLOCK,"Dripstone",    20,    8,     "Blocks");
        add(blocks, Material.MOSS_BLOCK,    "Moss Block",    25,    10,    "Blocks");
        add(blocks, Material.SNOW_BLOCK,    "Snow Block",    10,    3,     "Blocks");
        add(blocks, Material.ICE,           "Ice",           15,    5,     "Blocks");
        add(blocks, Material.PACKED_ICE,    "Packed Ice",    25,    10,    "Blocks");
        add(blocks, Material.BLUE_ICE,      "Blue Ice",      40,    15,    "Blocks");
        add(blocks, Material.CLAY,          "Clay",          15,    5,     "Blocks");
        add(blocks, Material.SOUL_SAND,     "Soul Sand",     20,    8,     "Blocks");
        add(blocks, Material.SOUL_SOIL,     "Soul Soil",     20,    8,     "Blocks");
        add(blocks, Material.NETHERRACK,    "Netherrack",    5,     1,     "Blocks");
        add(blocks, Material.BASALT,        "Basalt",        15,    5,     "Blocks");
        add(blocks, Material.MAGMA_BLOCK,   "Magma Block",   30,    12,    "Blocks");
        add(blocks, Material.END_STONE,     "End Stone",     30,    12,    "Blocks");
        add(blocks, Material.PURPUR_BLOCK,  "Purpur Block",  40,    15,    "Blocks");
        categories.put("Blocks", blocks);

        // ── ORES & MINERALS ─────────────────────────────────────────────────
        List<ShopItem> ores = new ArrayList<>();
        add(ores, Material.COAL,            "Coal",          15,    8,     "Ores");
        add(ores, Material.CHARCOAL,        "Charcoal",      12,    5,     "Ores");
        add(ores, Material.RAW_IRON,        "Raw Iron",      30,    15,    "Ores");
        add(ores, Material.IRON_INGOT,      "Iron Ingot",    45,    22,    "Ores");
        add(ores, Material.RAW_COPPER,      "Raw Copper",    20,    8,     "Ores");
        add(ores, Material.COPPER_INGOT,    "Copper Ingot",  30,    12,    "Ores");
        add(ores, Material.RAW_GOLD,        "Raw Gold",      80,    40,    "Ores");
        add(ores, Material.GOLD_INGOT,      "Gold Ingot",    120,   60,    "Ores");
        add(ores, Material.GOLD_NUGGET,     "Gold Nugget",   15,    7,     "Ores");
        add(ores, Material.REDSTONE,        "Redstone",      20,    10,    "Ores");
        add(ores, Material.LAPIS_LAZULI,    "Lapis Lazuli",  25,    12,    "Ores");
        add(ores, Material.DIAMOND,         "Diamond",       500,   250,   "Ores");
        add(ores, Material.EMERALD,         "Emerald",       400,   180,   "Ores");
        add(ores, Material.QUARTZ,          "Nether Quartz", 20,    8,     "Ores");
        add(ores, Material.AMETHYST_SHARD,  "Amethyst Shard",35,   15,    "Ores");
        add(ores, Material.RAW_NETHERITE,   "Raw Netherite", 1500,  700,   "Ores");
        add(ores, Material.NETHERITE_INGOT, "Netherite Ingot",2500, 1000,  "Ores");
        add(ores, Material.NETHERITE_SCRAP, "Netherite Scrap",600,  250,   "Ores");
        add(ores, Material.GLOWSTONE_DUST,  "Glowstone Dust",20,   8,     "Ores");
        categories.put("Ores", ores);

        // ── FARMING ─────────────────────────────────────────────────────────
        List<ShopItem> farming = new ArrayList<>();
        add(farming, Material.WHEAT,         "Wheat",         5,     2,     "Farming");
        add(farming, Material.WHEAT_SEEDS,   "Wheat Seeds",   3,     1,     "Farming");
        add(farming, Material.CARROT,        "Carrot",        6,     2,     "Farming");
        add(farming, Material.POTATO,        "Potato",        5,     2,     "Farming");
        add(farming, Material.BAKED_POTATO,  "Baked Potato",  8,     3,     "Farming");
        add(farming, Material.BEETROOT,      "Beetroot",      5,     2,     "Farming");
        add(farming, Material.MELON_SLICE,   "Melon Slice",   4,     1,     "Farming");
        add(farming, Material.PUMPKIN,       "Pumpkin",       15,    6,     "Farming");
        add(farming, Material.SUGAR_CANE,    "Sugar Cane",    5,     2,     "Farming");
        add(farming, Material.CACTUS,        "Cactus",        4,     1,     "Farming");
        add(farming, Material.BAMBOO,        "Bamboo",        3,     1,     "Farming");
        add(farming, Material.COCOA_BEANS,   "Cocoa Beans",   8,     3,     "Farming");
        add(farming, Material.INK_SAC,       "Ink Sac",       10,    4,     "Farming");
        add(farming, Material.GLOW_INK_SAC,  "Glow Ink Sac",  20,    8,     "Farming");
        add(farming, Material.HONEYCOMB,     "Honeycomb",     15,    6,     "Farming");
        add(farming, Material.HONEY_BOTTLE,  "Honey Bottle",  20,    8,     "Farming");
        add(farming, Material.KELP,          "Kelp",          3,     1,     "Farming");
        add(farming, Material.DRIED_KELP,    "Dried Kelp",    2,     0,     "Farming");
        add(farming, Material.APPLE,         "Apple",         10,    4,     "Farming");
        add(farming, Material.GOLDEN_APPLE,  "Golden Apple",  200,   0,     "Farming");
        add(farming, Material.ENCHANTED_GOLDEN_APPLE,"Enchanted Golden Apple",5000,0,"Farming");
        categories.put("Farming", farming);

        // ── MOB DROPS ───────────────────────────────────────────────────────
        List<ShopItem> mobs = new ArrayList<>();
        // Common drops — low sell price, requires bulk
        add(mobs, Material.ROTTEN_FLESH,     "Rotten Flesh",   2,    1,     "Mob Drops");
        add(mobs, Material.BONE,             "Bone",           5,    2,     "Mob Drops");
        add(mobs, Material.BONE_MEAL,        "Bone Meal",      3,    1,     "Mob Drops");
        add(mobs, Material.GUNPOWDER,        "Gunpowder",      10,   5,     "Mob Drops");
        add(mobs, Material.STRING,           "String",         8,    3,     "Mob Drops");
        add(mobs, Material.SPIDER_EYE,       "Spider Eye",     8,    3,     "Mob Drops");
        add(mobs, Material.ARROW,            "Arrow",          3,    1,     "Mob Drops");
        add(mobs, Material.FEATHER,          "Feather",        8,    3,     "Mob Drops");
        add(mobs, Material.LEATHER,          "Leather",        12,   5,     "Mob Drops");
        add(mobs, Material.PORKCHOP,         "Raw Porkchop",   5,    2,     "Mob Drops");
        add(mobs, Material.COOKED_PORKCHOP,  "Cooked Porkchop",8,   3,     "Mob Drops");
        add(mobs, Material.BEEF,             "Raw Beef",       5,    2,     "Mob Drops");
        add(mobs, Material.COOKED_BEEF,      "Cooked Beef",    8,    3,     "Mob Drops");
        add(mobs, Material.CHICKEN,          "Raw Chicken",    4,    1,     "Mob Drops");
        add(mobs, Material.COOKED_CHICKEN,   "Cooked Chicken", 6,    2,     "Mob Drops");
        add(mobs, Material.MUTTON,           "Raw Mutton",     5,    2,     "Mob Drops");
        add(mobs, Material.COOKED_MUTTON,    "Cooked Mutton",  8,    3,     "Mob Drops");
        add(mobs, Material.RABBIT,           "Raw Rabbit",     5,    2,     "Mob Drops");
        add(mobs, Material.RABBIT_FOOT,      "Rabbit Foot",    30,   12,    "Mob Drops");
        add(mobs, Material.RABBIT_HIDE,      "Rabbit Hide",    8,    3,     "Mob Drops");
        add(mobs, Material.SLIME_BALL,       "Slimeball",      15,   6,     "Mob Drops");
        add(mobs, Material.MAGMA_CREAM,      "Magma Cream",    20,   8,     "Mob Drops");
        // Balanced against spawner costs — blaze spawner is 20k, rod sells for 8
        add(mobs, Material.BLAZE_ROD,        "Blaze Rod",      8,    8,     "Mob Drops");
        add(mobs, Material.BLAZE_POWDER,     "Blaze Powder",   4,    4,     "Mob Drops");
        add(mobs, Material.ENDER_PEARL,      "Ender Pearl",    20,   10,    "Mob Drops");
        add(mobs, Material.ENDER_EYE,        "Eye of Ender",   40,   20,    "Mob Drops");
        // Wither skeleton drops — wither spawner 25k, skull is 0 sell
        add(mobs, Material.WITHER_SKELETON_SKULL,"Wither Skull",2000,0,    "Mob Drops");
        add(mobs, Material.NETHER_STAR,      "Nether Star",    5000, 0,     "Mob Drops");
        add(mobs, Material.GHAST_TEAR,       "Ghast Tear",     40,   15,    "Mob Drops");
        add(mobs, Material.PHANTOM_MEMBRANE, "Phantom Membrane",30,  12,    "Mob Drops");
        add(mobs, Material.SHULKER_SHELL,    "Shulker Shell",  200,  80,    "Mob Drops");
        add(mobs, Material.PRISMARINE_SHARD, "Prismarine Shard",15,  6,    "Mob Drops");
        add(mobs, Material.PRISMARINE_CRYSTALS,"Prism Crystals",20,  8,    "Mob Drops");
        add(mobs, Material.SPONGE,           "Sponge",         100,  0,     "Mob Drops");
        add(mobs, Material.TURTLE_SCUTE,     "Turtle Scute",   50,   20,    "Mob Drops");
        add(mobs, Material.ARMADILLO_SCUTE,  "Armadillo Scute",40,   15,    "Mob Drops");
        add(mobs, Material.BREEZE_ROD,       "Breeze Rod",     60,   25,    "Mob Drops");
        add(mobs, Material.WIND_CHARGE,      "Wind Charge",    15,   6,     "Mob Drops");
        add(mobs, Material.DRAGON_BREATH,    "Dragon Breath",  150,  60,    "Mob Drops");
        add(mobs, Material.DRAGON_EGG,       "Dragon Egg",     10000,0,     "Mob Drops");
        categories.put("Mob Drops", mobs);

        // ── SPAWNERS ────────────────────────────────────────────────────────
        // Buy only — prices balanced so drops can't ROI too fast
        List<ShopItem> spawners = new ArrayList<>();
        add(spawners, Material.SPAWNER, "Zombie Spawner",     8000,  0, "Spawners");
        add(spawners, Material.SPAWNER, "Skeleton Spawner",   10000, 0, "Spawners");
        add(spawners, Material.SPAWNER, "Spider Spawner",     8000,  0, "Spawners");
        add(spawners, Material.SPAWNER, "Cave Spider Spawner",9000,  0, "Spawners");
        add(spawners, Material.SPAWNER, "Blaze Spawner",      20000, 0, "Spawners");
        add(spawners, Material.SPAWNER, "Creeper Spawner",    15000, 0, "Spawners");
        add(spawners, Material.SPAWNER, "Slime Spawner",      12000, 0, "Spawners");
        add(spawners, Material.SPAWNER, "Witch Spawner",      18000, 0, "Spawners");
        add(spawners, Material.SPAWNER, "Enderman Spawner",   22000, 0, "Spawners");
        add(spawners, Material.SPAWNER, "Wither Skeleton Spawner",25000,0,"Spawners");
        add(spawners, Material.SPAWNER, "Piglin Spawner",     12000, 0, "Spawners");
        add(spawners, Material.SPAWNER, "Magma Cube Spawner", 15000, 0, "Spawners");
        add(spawners, Material.SPAWNER, "Ghast Spawner",      20000, 0, "Spawners");
        add(spawners, Material.SPAWNER, "Drowned Spawner",    10000, 0, "Spawners");
        add(spawners, Material.SPAWNER, "Husk Spawner",       8000,  0, "Spawners");
        add(spawners, Material.SPAWNER, "Stray Spawner",      10000, 0, "Spawners");
        add(spawners, Material.SPAWNER, "Guardian Spawner",   18000, 0, "Spawners");
        add(spawners, Material.SPAWNER, "Shulker Spawner",    30000, 0, "Spawners");
        add(spawners, Material.SPAWNER, "Phantom Spawner",    15000, 0, "Spawners");
        categories.put("Spawners", spawners);

        // ── ENCHANTS ────────────────────────────────────────────────────────
        // Max level, buy only
        List<ShopItem> enchants = new ArrayList<>();
        add(enchants, Material.ENCHANTED_BOOK, "Protection V",       8000,  0, "Enchants");
        add(enchants, Material.ENCHANTED_BOOK, "Fire Protection V",  5000,  0, "Enchants");
        add(enchants, Material.ENCHANTED_BOOK, "Feather Falling V",  6000,  0, "Enchants");
        add(enchants, Material.ENCHANTED_BOOK, "Blast Protection V", 5000,  0, "Enchants");
        add(enchants, Material.ENCHANTED_BOOK, "Projectile Protect V",5000, 0, "Enchants");
        add(enchants, Material.ENCHANTED_BOOK, "Thorns III",         4000,  0, "Enchants");
        add(enchants, Material.ENCHANTED_BOOK, "Respiration III",    4000,  0, "Enchants");
        add(enchants, Material.ENCHANTED_BOOK, "Aqua Affinity",      3000,  0, "Enchants");
        add(enchants, Material.ENCHANTED_BOOK, "Depth Strider III",  5000,  0, "Enchants");
        add(enchants, Material.ENCHANTED_BOOK, "Frost Walker II",    4000,  0, "Enchants");
        add(enchants, Material.ENCHANTED_BOOK, "Sharpness VI",       12000, 0, "Enchants");
        add(enchants, Material.ENCHANTED_BOOK, "Smite VI",           8000,  0, "Enchants");
        add(enchants, Material.ENCHANTED_BOOK, "Bane of Arth VI",    8000,  0, "Enchants");
        add(enchants, Material.ENCHANTED_BOOK, "Knockback III",      4000,  0, "Enchants");
        add(enchants, Material.ENCHANTED_BOOK, "Fire Aspect III",    5000,  0, "Enchants");
        add(enchants, Material.ENCHANTED_BOOK, "Looting V",          15000, 0, "Enchants");
        add(enchants, Material.ENCHANTED_BOOK, "Sweeping Edge V",    8000,  0, "Enchants");
        add(enchants, Material.ENCHANTED_BOOK, "Efficiency VI",      10000, 0, "Enchants");
        add(enchants, Material.ENCHANTED_BOOK, "Silk Touch",         8000,  0, "Enchants");
        add(enchants, Material.ENCHANTED_BOOK, "Fortune V",          15000, 0, "Enchants");
        add(enchants, Material.ENCHANTED_BOOK, "Power VI",           10000, 0, "Enchants");
        add(enchants, Material.ENCHANTED_BOOK, "Punch III",          4000,  0, "Enchants");
        add(enchants, Material.ENCHANTED_BOOK, "Flame II",           4000,  0, "Enchants");
        add(enchants, Material.ENCHANTED_BOOK, "Infinity",           6000,  0, "Enchants");
        add(enchants, Material.ENCHANTED_BOOK, "Luck of the Sea V",  12000, 0, "Enchants");
        add(enchants, Material.ENCHANTED_BOOK, "Lure V",             8000,  0, "Enchants");
        add(enchants, Material.ENCHANTED_BOOK, "Mending",            10000, 0, "Enchants");
        add(enchants, Material.ENCHANTED_BOOK, "Unbreaking V",       8000,  0, "Enchants");
        add(enchants, Material.ENCHANTED_BOOK, "Swift Sneak III",    6000,  0, "Enchants");
        add(enchants, Material.ENCHANTED_BOOK, "Soul Speed III",     5000,  0, "Enchants");
        add(enchants, Material.ENCHANTED_BOOK, "Wind Burst III",     8000,  0, "Enchants");
        add(enchants, Material.ENCHANTED_BOOK, "Breach IV",          8000,  0, "Enchants");
        add(enchants, Material.ENCHANTED_BOOK, "Density V",          8000,  0, "Enchants");
        categories.put("Enchants", enchants);

        // ── SPECIAL ITEMS ───────────────────────────────────────────────────
        List<ShopItem> special = new ArrayList<>();
        add(special, Material.ELYTRA,         "Elytra",              50000, 0, "Special");
        add(special, Material.TOTEM_OF_UNDYING,"Totem of Undying",   30000, 0, "Special");
        add(special, Material.BEACON,         "Beacon",              25000, 0, "Special");
        add(special, Material.HEART_OF_THE_SEA,"Heart of the Sea",   8000,  0, "Special");
        add(special, Material.CONDUIT,        "Conduit",             15000, 0, "Special");
        add(special, Material.TRIDENT,        "Trident",             20000, 0, "Special");
        add(special, Material.MACE,           "Mace",                15000, 0, "Special");
        add(special, Material.END_CRYSTAL,    "End Crystal",         5000,  0, "Special");
        add(special, Material.OMINOUS_BOTTLE, "Ominous Bottle",      1000,  0, "Special");
        categories.put("Special", special);
    }

    private void add(List<ShopItem> list, Material mat, String name, double buy, double sell, String cat) {
        ShopItem item = new ShopItem(mat, name, buy, sell, cat);
        list.add(item);
        // Only register first entry per material for lookup (spawners handled separately)
        if (!itemLookup.containsKey(mat) || !cat.equals("Spawners")) {
            itemLookup.put(mat, item);
        }
    }

    public void openMainMenu(Player player) {
        Inventory inv = Bukkit.createInventory(null, 54, MAIN_TITLE);
        List<String> catNames = new ArrayList<>(categories.keySet());
        Material[] catIcons = {
            Material.GRASS_BLOCK, Material.DIAMOND_ORE, Material.WHEAT,
            Material.ROTTEN_FLESH, Material.SPAWNER, Material.ENCHANTED_BOOK,
            Material.ELYTRA
        };
        ChatColor[] catColors = {
            ChatColor.GREEN, ChatColor.AQUA, ChatColor.YELLOW,
            ChatColor.RED, ChatColor.LIGHT_PURPLE, ChatColor.GOLD,
            ChatColor.DARK_PURPLE
        };

        int[] slots = {10, 12, 14, 16, 28, 30, 32};
        for (int i = 0; i < catNames.size() && i < slots.length; i++) {
            String cat   = catNames.get(i);
            Material icon = i < catIcons.length ? catIcons[i] : Material.CHEST;
            ChatColor col = i < catColors.length ? catColors[i] : ChatColor.WHITE;
            ItemStack item = createItem(icon, col + "» " + cat,
                Arrays.asList(ChatColor.GRAY + "Click to browse " + cat));
            inv.setItem(slots[i], item);
        }

        // Sell wand info
        ItemStack wand = createItem(Material.BLAZE_ROD,
            ChatColor.GOLD + "✦ Sell Wand",
            Arrays.asList(
                ChatColor.GRAY + "Right-click a chest to",
                ChatColor.GRAY + "sell all its contents!",
                "",
                ChatColor.YELLOW + "/sellwand to get one (OP)"
            ));
        inv.setItem(49, wand);

        player.openInventory(inv);
    }

    public void openCategory(Player player, String category) {
        List<ShopItem> items = categories.get(category);
        if (items == null) return;

        int size = (int) Math.ceil(items.size() / 9.0) * 9 + 9;
        size = Math.min(Math.max(size, 27), 54);

        Inventory inv = Bukkit.createInventory(null, size,
            ChatColor.GOLD + "✦ " + ChatColor.YELLOW + category + ChatColor.GOLD + " ✦");

        for (int i = 0; i < items.size() && i < size - 9; i++) {
            ShopItem si = items.get(i);
            List<String> lore = new ArrayList<>();
            lore.add("");
            lore.add(BUY_PREFIX + ChatColor.WHITE + formatPrice(si.getBuyPrice()) + " Shards");
            if (si.canSell()) {
                lore.add(SELL_PREFIX + ChatColor.WHITE + formatPrice(si.getSellPrice()) + " Shards");
            } else {
                lore.add(ChatColor.RED + "✗ Cannot sell");
            }
            lore.add("");
            lore.add(ChatColor.YELLOW + "Left-click » Buy x1");
            lore.add(ChatColor.YELLOW + "Right-click » Sell x1");
            lore.add(ChatColor.YELLOW + "Shift+Left » Buy x64");
            lore.add(ChatColor.YELLOW + "Shift+Right » Sell All");

            ItemStack display = createItem(si.getMaterial(), ChatColor.WHITE + si.getDisplayName(), lore);
            inv.setItem(i, display);
        }

        // Back button
        ItemStack back = createItem(Material.ARROW, ChatColor.RED + "« Back", Collections.singletonList(ChatColor.GRAY + "Return to main menu"));
        inv.setItem(size - 5, back);

        player.openInventory(inv);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player player)) return;
        e.setCancelled(true);

        String title = e.getView().title().toString();
        if (!title.contains("Shards Shop") && !title.contains("✦")) return;

        ItemStack clicked = e.getCurrentItem();
        if (clicked == null || clicked.getType() == Material.AIR) return;

        // Main menu
        if (title.equals(MAIN_TITLE)) {
            String itemName = ChatColor.stripColor(clicked.getItemMeta() != null ?
                clicked.getItemMeta().getDisplayName() : "");
            String cat = itemName.replace("» ", "").trim();
            if (categories.containsKey(cat)) {
                openCategory(player, cat);
            }
            return;
        }

        // Category menu
        String catTitle = ChatColor.stripColor(title).replace("✦ ", "").replace(" ✦", "").trim();

        // Back button
        if (clicked.getType() == Material.ARROW) {
            openMainMenu(player);
            return;
        }

        List<ShopItem> items = categories.get(catTitle);
        if (items == null) return;

        int slot = e.getSlot();
        if (slot >= items.size()) return;

        ShopItem si = items.get(slot);
        boolean shift = e.isShiftClick();
        boolean right = e.isRightClick();

        if (right) {
            // Sell
            if (!si.canSell()) {
                player.sendMessage(ChatColor.RED + "✗ This item cannot be sold.");
                return;
            }
            int amount = shift ? countInInventory(player, si.getMaterial()) : 1;
            if (amount == 0) {
                player.sendMessage(ChatColor.RED + "✗ You don't have any " + si.getDisplayName() + " to sell.");
                return;
            }
            removeFromInventory(player, si.getMaterial(), amount);
            double earned = si.getSellPrice() * amount;
            plugin.getBalanceManager().addBalance(player.getUniqueId(), earned);
            player.sendMessage(ChatColor.GREEN + "✓ Sold " + amount + "x " + si.getDisplayName() +
                " for " + ChatColor.GOLD + formatPrice(earned) + " Shards" + ChatColor.GREEN + "!");
        } else {
            // Buy
            int amount = shift ? 64 : 1;
            double cost = si.getBuyPrice() * amount;
            if (!plugin.getBalanceManager().takeBalance(player.getUniqueId(), cost)) {
                player.sendMessage(ChatColor.RED + "✗ You need " + ChatColor.GOLD + formatPrice(cost) +
                    " Shards" + ChatColor.RED + " to buy this. You have " + ChatColor.GOLD +
                    formatPrice(plugin.getBalanceManager().getBalance(player.getUniqueId())) + " Shards" + ChatColor.RED + ".");
                return;
            }
            ItemStack give = new ItemStack(si.getMaterial(), amount);
            player.getInventory().addItem(give).forEach((k, v) ->
                player.getWorld().dropItemNaturally(player.getLocation(), v));
            player.sendMessage(ChatColor.GREEN + "✓ Bought " + amount + "x " + si.getDisplayName() +
                " for " + ChatColor.GOLD + formatPrice(cost) + " Shards" + ChatColor.GREEN + "!");
        }
    }

    private int countInInventory(Player player, Material mat) {
        int count = 0;
        for (ItemStack item : player.getInventory().getContents()) {
            if (item != null && item.getType() == mat) count += item.getAmount();
        }
        return count;
    }

    private void removeFromInventory(Player player, Material mat, int amount) {
        int remaining = amount;
        for (ItemStack item : player.getInventory().getContents()) {
            if (item != null && item.getType() == mat) {
                if (item.getAmount() <= remaining) {
                    remaining -= item.getAmount();
                    item.setAmount(0);
                } else {
                    item.setAmount(item.getAmount() - remaining);
                    remaining = 0;
                }
                if (remaining == 0) break;
            }
        }
    }

    public ShopItem getItemByMaterial(Material mat) {
        return itemLookup.get(mat);
    }

    public Map<String, List<ShopItem>> getCategories() { return categories; }

    private ItemStack createItem(Material mat, String name, List<String> lore) {
        ItemStack item = new ItemStack(mat);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(name);
            meta.setLore(lore);
            item.setItemMeta(meta);
        }
        return item;
    }

    private String formatPrice(double price) {
        if (price >= 1_000_000) return String.format("%.1fM", price / 1_000_000);
        if (price >= 1_000)     return String.format("%.1fK", price / 1_000);
        return String.format("%.0f", price);
    }
}
