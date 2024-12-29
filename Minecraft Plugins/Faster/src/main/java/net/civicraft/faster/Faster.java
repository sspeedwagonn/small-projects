package net.civicraft.faster;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public final class Faster extends JavaPlugin {

    @Override
    public void onEnable() {
        getCommand("speed").setExecutor(new SpeedCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
