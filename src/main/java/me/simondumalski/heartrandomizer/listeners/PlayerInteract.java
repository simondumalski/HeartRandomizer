package me.simondumalski.heartrandomizer.listeners;

import me.simondumalski.heartrandomizer.Main;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class PlayerInteract implements Listener {

    private Main plugin;

    public PlayerInteract(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onClick(PlayerInteractEvent e) {

        //Get the player who clicked
        Player p = e.getPlayer();

        //Check if the player right-clicked
        if (!e.getAction().equals(Action.RIGHT_CLICK_AIR) && !e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            return;
        }

        //Get the item the player is holding and check if its null
        ItemStack item = e.getItem();
        if (item == null) {
            return;
        }

        //Get the item metadata and check if its null
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return;
        }

        //Get the persistent data container
        PersistentDataContainer data = meta.getPersistentDataContainer();

        //Check if the item has a heart-randomizer stored
        if (!data.has(new NamespacedKey(plugin, "heart-randomizer"), PersistentDataType.STRING)) {
            return;
        }

        //Call the heart randomizer functionality
        plugin.getHeartRandomizer().randomizeHearts(p);

        //Remove the item from the player's inventory
        if (item.getAmount() > 1) {
            item.setAmount(item.getAmount() - 1);
        } else {
            p.getInventory().remove(item);
        }

    }

}
