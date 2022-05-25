package me.simondumalski.heartrandomizer.utils;

import me.simondumalski.heartrandomizer.Main;
import me.simondumalski.heartrandomizer.enums.Message;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;

import java.util.concurrent.ThreadLocalRandom;

public class HeartRandomizer {

    private Main plugin;

    public HeartRandomizer(Main plugin) {
        this.plugin = plugin;
    }

    public void randomizeHearts(Player p) {

        //Get the minimum random health value and check if its valid
        int min = plugin.getConfig().getInt("heart-randomizer.min");
        if (min < 1) {
            plugin.getMessageManager().sendConsoleMessage(Message.SERVER_ERROR_MIN_VALUE);
            return;
        }

        //Get the maximum random health value and check if its valid
        int max = plugin.getConfig().getInt("heart-randomizer.max");
        if (max < 1 || max < min) {
            plugin.getMessageManager().sendConsoleMessage(Message.SERVER_ERROR_MAX_VALUE);
            return;
        }

        //Randomly generate a new health value
        int newHealthValue = ThreadLocalRandom.current().nextInt(min, max + 1);

        //Get the max health attribute and check if its null
        AttributeInstance maxHealthAttribute = p.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        if (maxHealthAttribute == null) {
            plugin.getMessageManager().sendConsoleMessage(Message.SERVER_ERROR_MAX_HEALTH_ATTRIBUTE);
            return;
        }

        //Set the player's new max health
        maxHealthAttribute.setBaseValue(newHealthValue * 2);
        plugin.getMessageManager().sendConfigMessage(p, Message.EVENT_HEART_RANDOMIZER_USE, new String[]{Integer.toString(newHealthValue)});

    }

}
