package net.momirealms.craftengine.bukkit.plugin.command.feature;

import net.kyori.adventure.text.Component;
import net.momirealms.craftengine.bukkit.entity.mob.BukkitCustomMobManager;
import net.momirealms.craftengine.bukkit.plugin.command.BukkitCommandFeature;
import net.momirealms.craftengine.core.plugin.CraftEngine;
import net.momirealms.craftengine.core.plugin.command.CraftEngineCommandManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.incendo.cloud.Command;
import org.incendo.cloud.parser.standard.StringParser;

public final class CustomMobCommand extends BukkitCommandFeature<CommandSender> {
    private final BukkitCustomMobManager mobManager;

    public CustomMobCommand(CraftEngineCommandManager<CommandSender> commandManager, CraftEngine plugin) {
        super(commandManager, plugin);
        this.mobManager = BukkitCustomMobManager.getOrCreate(plugin());
    }

    @Override
    public Command.Builder<? extends CommandSender> assembleCommand(org.incendo.cloud.CommandManager<CommandSender> manager, Command.Builder<CommandSender> builder) {
        return builder
                .optional("id", StringParser.stringParser())
                .handler(context -> {
                    if (context.optional("id").isEmpty()) {
                        context.sender().sendMessage(Component.text("Custom mobs: " + String.join(", ", mobManager.ids())));
                        return;
                    }
                    if (!(context.sender() instanceof Player player)) {
                        context.sender().sendMessage(Component.text("This command must be run by a player."));
                        return;
                    }
                    String id = context.get("id");
                    if (mobManager.spawn(id, player.getLocation()).isPresent()) {
                        context.sender().sendMessage(Component.text("Spawned custom mob: " + id));
                    } else {
                        context.sender().sendMessage(Component.text("Unknown custom mob: " + id));
                    }
                });
    }

    @Override
    public String getFeatureID() {
        return "custom-mob";
    }
}
