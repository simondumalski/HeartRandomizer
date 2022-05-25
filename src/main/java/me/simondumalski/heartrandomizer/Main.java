package me.simondumalski.heartrandomizer;

import me.simondumalski.heartrandomizer.commands.HeartRandomizerCommand;
import me.simondumalski.heartrandomizer.listeners.PlayerInteract;
import me.simondumalski.heartrandomizer.utils.HeartRandomizer;
import me.simondumalski.heartrandomizer.utils.ItemManager;
import me.simondumalski.heartrandomizer.utils.MessageManager;
import me.simondumalski.heartrandomizer.utils.RecipeManager;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    private RecipeManager recipeManager = new RecipeManager(this);
    private ItemManager itemManager = new ItemManager(this);
    private MessageManager messageManager = new MessageManager(this);
    private HeartRandomizer heartRandomizer = new HeartRandomizer(this);

    @Override
    public void onEnable() {

        //Create the default config.yml
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        //Create the custom recipes
        recipeManager.createRecipe();

        //Register the events
        getServer().getPluginManager().registerEvents(new PlayerInteract(this), this);

        //Register the commands
        getCommand("heartrandomizer").setExecutor(new HeartRandomizerCommand(this));

    }

    @Override
    public void onDisable() {

        getServer().removeRecipe(NamespacedKey.fromString("heart-randomizer-recipe"));

    }

    public ItemManager getItemManager() {
        return itemManager;
    }

    public MessageManager getMessageManager() {
        return messageManager;
    }

    public HeartRandomizer getHeartRandomizer() {
        return heartRandomizer;
    }

}
