package me.simondumalski.heartrandomizer.utils;

import me.simondumalski.heartrandomizer.Main;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;
import java.util.List;

public class ItemManager {

    private Main plugin;
    private ItemStack heartRandomizerItem;

    public ItemManager(Main plugin) {
        this.plugin = plugin;
        this.heartRandomizerItem = createItem();
    }

    public ItemStack getHeartRandomizerItem() {
        return heartRandomizerItem;
    }

    public boolean giveHeartRandomizerItem(Player p) {

        HashMap<Integer, ItemStack> hashMap = p.getInventory().addItem(heartRandomizerItem);

        if (!hashMap.isEmpty()) {
            return false;
        }

        return true;

    }

    private ItemStack createItem() {

        //Get the item name
        String itemName = plugin.getConfig().getString("heart-randomizer.name");

        if (itemName == null) {
            System.out.println(ChatColor.RED + "Invalid item name!.");
            itemName = ChatColor.translateAlternateColorCodes('&', "&cHeart &dRandomizer");
        } else {
            itemName = ChatColor.translateAlternateColorCodes('&', itemName);
        }

        //Get the item lore
        List<String> itemLore = plugin.getConfig().getStringList("heart-randomizer.lore");

        if (itemLore.isEmpty()) {
            System.out.println(ChatColor.RED + "Invalid item lore!");
            itemLore.add(ChatColor.translateAlternateColorCodes('&', "&eRandomize your hearts!"));
            itemLore.add(ChatColor.translateAlternateColorCodes('&', ""));
            itemLore.add(ChatColor.translateAlternateColorCodes('&', "&aRight-click to use"));
        } else {
            for (String lore : itemLore) {
                itemLore.set(itemLore.indexOf(lore), ChatColor.translateAlternateColorCodes('&', lore));
            }
        }

        //Get the item material
        String itemMaterialString = plugin.getConfig().getString("heart-randomizer.material");
        Material itemMaterial;

        if (itemMaterialString == null) {
            System.out.println(ChatColor.RED + "Invalid item material!");
            itemMaterial = Material.NETHER_STAR;
        } else {
            itemMaterial = Material.getMaterial(itemMaterialString);
        }

        //Create the item
        ItemStack item = new ItemStack(itemMaterial);
        ItemMeta itemMeta = item.getItemMeta();

        itemMeta.setDisplayName(itemName);
        itemMeta.setLore(itemLore);

        //Start of persistent data
        PersistentDataContainer data = itemMeta.getPersistentDataContainer();

        data.set(new NamespacedKey(plugin, "heart-randomizer"), PersistentDataType.STRING, "randomizer");

        item.setItemMeta(itemMeta);

        return item;
    }

}
