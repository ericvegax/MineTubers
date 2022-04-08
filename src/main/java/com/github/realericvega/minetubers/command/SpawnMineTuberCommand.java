package com.github.realericvega.minetubers.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import org.bukkit.entity.Player;

@CommandAlias("minetuber|mt")
public class SpawnMineTuberCommand extends BaseCommand implements MineTuberCommand {

    @Subcommand("spawn")
    @CommandPermission("minetuber.command.spawn")
    @Override
    public void execute(Player player) {

    }
}
