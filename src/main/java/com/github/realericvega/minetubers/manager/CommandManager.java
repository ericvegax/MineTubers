package com.github.realericvega.minetubers.manager;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.BukkitCommandManager;
import co.aikar.commands.annotation.Dependency;
import com.github.realericvega.minetubers.MineTubersPlugin;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandManager {

    private static CommandManager instance;
    private static BukkitCommandManager manager;

    @Getter
    private final List<BaseCommand> BASE_COMMANDS = new ArrayList<>();

    public static CommandManager getInstance() {
        if (instance == null)
            instance = new CommandManager();

        return instance;
    }

    private static BukkitCommandManager getBukkitCommandManager() {
        if (manager == null)
            manager = new BukkitCommandManager(MineTubersPlugin.getProvidingPlugin(MineTubersPlugin.class));

        return manager;
    }

    public void registerCommands() {
        BASE_COMMANDS.forEach(baseCommand -> {
            if (Arrays.stream(baseCommand.getClass().getFields()).
                    anyMatch(field -> field.isAnnotationPresent(Dependency.class) && field.getType() == MineTubersPlugin.class)) {
                getBukkitCommandManager().registerDependency(MineTubersPlugin.class, baseCommand);
            } else {
                getBukkitCommandManager().registerCommand(baseCommand);
            }
        });
    }
}
