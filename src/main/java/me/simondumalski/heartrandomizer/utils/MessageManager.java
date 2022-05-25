package me.simondumalski.heartrandomizer.utils;

import me.simondumalski.heartrandomizer.Main;
import me.simondumalski.heartrandomizer.enums.Message;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;

public class MessageManager {

    private Main plugin;

    public MessageManager(Main plugin) {
        this.plugin = plugin;
    }

    public void sendHelpMessage(Player p) {

        List<String> helpMessage = plugin.getConfig().getStringList(Message.PLUGIN_HELP.getValue());

        for (String line : helpMessage) {
            if (line.contains("%version%")) {
                line = line.replace("%version%", plugin.getDescription().getVersion());
            }
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', line));
        }

    }

    public void sendConfigMessage(Player p, Message configValue, String[] args) {

        //Get the message from the config.yml
        String message = plugin.getConfig().getString(configValue.getValue());

        //Replace the prefix placeholder
        if (message.contains("%prefix%")) {
            message = message.replace("%prefix%", plugin.getConfig().getString(Message.PLUGIN_PREFIX.getValue()));
        }

        //Send the message if there are no arguments
        if (args == null) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
            return;
        }

        //Replace the args placeholder
        for (int i = 0; i < args.length; i++) {
            if (message.contains("%args" + i + "%")) {
                message = message.replace("%args" + i + "%", args[i]);
            }
        }

        //Send the message
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', message));

    }

    public void sendConsoleMessage(Message configValue) {

        //Get the message from the config.yml
        String message = configValue.getValue();

        //Send the message to the console
        System.out.println(ChatColor.translateAlternateColorCodes('&', message));

    }

}
