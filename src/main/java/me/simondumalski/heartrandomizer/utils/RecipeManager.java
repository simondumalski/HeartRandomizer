package me.simondumalski.heartrandomizer.utils;

import me.simondumalski.heartrandomizer.Main;
import me.simondumalski.heartrandomizer.enums.Message;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ShapedRecipe;

import java.util.ArrayList;
import java.util.List;

public class RecipeManager {

    private Main plugin;

    public RecipeManager(Main plugin) {
        this.plugin = plugin;
    }

    public void createRecipe() {

        //Get the recipe shape from the config.yml
        String topSlots = plugin.getConfig().getString("recipe.top-slots");
        String middleSlots = plugin.getConfig().getString("recipe.middle-slots");
        String bottomSlots = plugin.getConfig().getString("recipe.bottom-slots");

        //Check if the specified recipe shape is valid
        if (topSlots == null || topSlots.length() < 3) {
            plugin.getMessageManager().sendConsoleMessage(Message.SERVER_ERROR_RECIPE_TOP_SLOTS);
            return;
        }

        if (middleSlots == null || middleSlots.length() < 3) {
            plugin.getMessageManager().sendConsoleMessage(Message.SERVER_ERROR_RECIPE_MIDDLE_SLOTS);
            return;
        }

        if (bottomSlots == null || bottomSlots.length() < 3) {
            plugin.getMessageManager().sendConsoleMessage(Message.SERVER_ERROR_RECIPE_BOTTOM_SLOTS);
            return;
        }

        //Start creating the recipe
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(plugin, "heart-randomizer-recipe"), plugin.getItemManager().getHeartRandomizerItem());

        //Set the recipe shape
        recipe.shape(
                topSlots,
                middleSlots,
                bottomSlots
        );

        //Get the recipe dictionary section from the config.yml and check if it exists
        ConfigurationSection recipeDictionarySection = plugin.getConfig().getConfigurationSection("recipe-dictionary");

        if (recipeDictionarySection == null) {
            plugin.getMessageManager().sendConsoleMessage(Message.SERVER_ERROR_RECIPE_DICTIONARY);
            return;
        }

        //Add the dictionary elements from the config.yml to a list
        List<String> dictionaryElements = new ArrayList<>(recipeDictionarySection.getKeys(false));

        //Add each element as a recipe ingredient
        for (String elementString : dictionaryElements) {

            try {

                //Get the character the material is for
                char character = elementString.charAt(0);

                //Get the material the character is for and check if its valid
                String materialString = recipeDictionarySection.getString(elementString);

                if (materialString == null) {
                    plugin.getMessageManager().sendConsoleMessage(Message.SERVER_ERROR_RECIPE_DICTIONARY);
                    return;
                }

                Material material = Material.getMaterial(materialString);

                if (material == null) {
                    plugin.getMessageManager().sendConsoleMessage(Message.SERVER_ERROR_RECIPE_DICTIONARY);
                    return;
                }

                //Set the material as an ingredient for the recipe
                recipe.setIngredient(character, material);

            } catch (Exception ex) {
                ex.printStackTrace();
                System.out.println(ChatColor.RED + "Error when getting dictionary!");
            }

        }

        //Register the recipe
        plugin.getServer().addRecipe(recipe);

    }

}
