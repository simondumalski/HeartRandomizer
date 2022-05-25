package me.simondumalski.heartrandomizer.commands;

import me.simondumalski.heartrandomizer.Main;
import me.simondumalski.heartrandomizer.enums.Message;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.*;

public class HeartRandomizerCommand implements TabExecutor {

    private Main plugin;

    public HeartRandomizerCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {

            Player p = (Player) sender;

            //Check if the player has permission
            if (!p.hasPermission("heartrandomizer.admin")) {
                plugin.getMessageManager().sendConfigMessage(p, Message.ERROR_INSUFFICIENT_PERMISSIONS, null);
                return true;
            }

            //Check if player is sending a subcommand
            if (args.length < 1) {
                plugin.getMessageManager().sendHelpMessage(p);
                return true;
            }

            //help
            if (args[0].equalsIgnoreCase("help")) {
                plugin.getMessageManager().sendHelpMessage(p);
                return true;
            }

            //set <player> <hearts>
            if (args[0].equalsIgnoreCase("set")) {

                if (args.length < 3) {
                    plugin.getMessageManager().sendConfigMessage(p, Message.ERROR_INVALID_USAGE, null);
                    return true;
                }

                //Get player
                Player target = plugin.getServer().getPlayer(args[1]);

                if (target == null) {
                    plugin.getMessageManager().sendConfigMessage(p, Message.ERROR_INVALID_PLAYER, null);
                    return true;
                }

                //Get health
                int health;

                try {
                    health = Integer.parseInt(args[2]);
                } catch (NumberFormatException ex) {
                    plugin.getMessageManager().sendConfigMessage(p, Message.ERROR_INVALID_HEALTH, null);
                    return true;
                }

                if (health < 1) {
                    plugin.getMessageManager().sendConfigMessage(p, Message.ERROR_INVALID_HEALTH, null);
                    return true;
                }

                AttributeInstance maxHealthAttribute = p.getAttribute(Attribute.GENERIC_MAX_HEALTH);
                if (maxHealthAttribute == null) {
                    plugin.getMessageManager().sendConsoleMessage(Message.SERVER_ERROR_MAX_HEALTH_ATTRIBUTE);
                    return true;
                }

                maxHealthAttribute.setBaseValue(health * 2);
                plugin.getMessageManager().sendConfigMessage(p, Message.COMMAND_SET, new String[]{target.getName(), Integer.toString(health)});

                return true;
            }

            //reset <player>
            if (args[0].equalsIgnoreCase("reset")) {

                if (args.length < 2) {
                    plugin.getMessageManager().sendConfigMessage(p, Message.ERROR_INVALID_USAGE, null);
                    return true;
                }

                //Get player
                Player target = plugin.getServer().getPlayer(args[1]);

                if (target == null) {
                    plugin.getMessageManager().sendConfigMessage(p, Message.ERROR_INVALID_PLAYER, null);
                    return true;
                }

                AttributeInstance maxHealthAttribute = p.getAttribute(Attribute.GENERIC_MAX_HEALTH);
                if (maxHealthAttribute == null) {
                    plugin.getMessageManager().sendConfigMessage(p, Message.SERVER_ERROR_MAX_HEALTH_ATTRIBUTE, null);
                    return true;
                }

                maxHealthAttribute.setBaseValue(20);
                plugin.getMessageManager().sendConfigMessage(p, Message.COMMAND_RESET, new String[]{target.getName()});

                return true;
            }

            //give <player>
            if (args[0].equalsIgnoreCase("give")) {

                if (args.length < 2) {
                    plugin.getMessageManager().sendConfigMessage(p, Message.ERROR_INVALID_USAGE, null);
                    return true;
                }

                //Get player
                Player target = plugin.getServer().getPlayer(args[1]);

                if (target == null) {
                    plugin.getMessageManager().sendConfigMessage(p, Message.ERROR_INVALID_PLAYER, null);
                    return true;
                }

                if (!plugin.getItemManager().giveHeartRandomizerItem(p)) {
                    plugin.getMessageManager().sendConfigMessage(p, Message.ERROR_TARGET_INVENTORY_FULL, null);
                    return true;
                }

                plugin.getMessageManager().sendConfigMessage(p, Message.COMMAND_GIVE, new String[]{target.getName()});

                return true;
            }

            plugin.getMessageManager().sendConfigMessage(p, Message.ERROR_UNKNOWN_COMMAND, null);
            return true;

        } else {
            sender.sendMessage(ChatColor.RED + "Only players may use this command.");
            return true;
        }

    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

        //Make a list for storing the arguments
        List<String> arguments = new ArrayList<>();

        //If the player is on the first argument
        if (args.length == 1) {

            arguments.add("help");
            arguments.add("set");
            arguments.add("reset");
            arguments.add("give");

            return arguments;
        }

        //If the player is on the second argument
        if (args.length == 2) {

            //Get the list of online players and add them as arguments
            for (Player player : plugin.getServer().getOnlinePlayers()) {
                arguments.add(player.getName());
            }

            return arguments;
        }

        return null;
    }

}
