package me.simondumalski.heartrandomizer.enums;

public enum Message {

    PLUGIN_PREFIX("messages.plugin.prefix"),
    PLUGIN_HELP("messages.plugin.help"),

    ERROR_UNKNOWN_COMMAND("messages.errors.unknown-command"),
    ERROR_INSUFFICIENT_PERMISSIONS("messages.errors.insufficient-permissions"),
    ERROR_INVALID_USAGE("messages.errors.invalid-usage"),
    ERROR_INVALID_PLAYER("messages.errors.invalid-player"),
    ERROR_INVALID_HEALTH("messages.errors.invalid-health"),
    ERROR_TARGET_INVENTORY_FULL("messages.errors.inventory-full"),
    ERROR_CHANGING_HEALTH("messages.errors.changing-max-health"),

    SERVER_ERROR_MAX_VALUE("messages.server-errors.max-value"),
    SERVER_ERROR_MIN_VALUE("messages.server-errors.min-value"),
    SERVER_ERROR_MAX_HEALTH_ATTRIBUTE("messages.server-errors.max-health-attribute"),

    SERVER_ERROR_RECIPE_TOP_SLOTS("messages.server-errors.recipe-top-slots"),
    SERVER_ERROR_RECIPE_MIDDLE_SLOTS("messages.server-errors.recipe-middle-slots"),
    SERVER_ERROR_RECIPE_BOTTOM_SLOTS("messages.server-errors.recipe-bottom-slots"),
    SERVER_ERROR_RECIPE_DICTIONARY("message.server-errors.recipe-dictionary"),

    EVENT_HEART_RANDOMIZER_USE("messages.events.heart-randomizer-use"),

    COMMAND_SET("messages.commands.set"),
    COMMAND_RESET("messages.commands.reset"),
    COMMAND_GIVE("messages.commands.give");

    private String value;

    Message(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
