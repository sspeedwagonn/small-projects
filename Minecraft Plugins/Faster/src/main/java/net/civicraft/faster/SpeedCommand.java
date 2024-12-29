package net.civicraft.faster;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SpeedCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender instanceof Player player) {
            if (strings.length == 0) {
                player.sendMessage("Specify a speed from 1-10!");
            } else if (strings.length == 1) {
                int speed = Integer.parseInt(strings[0]);
                if (speed >= 1 && speed <= 10) {
                    float fspeed = speed / 10.0f;
                    player.setFlySpeed(fspeed);
                    player.sendMessage("Set fly speed to " + speed + "!");
                } else {
                    player.sendMessage("Specify a speed from 1-10!");
                }
            }
        } else {
            commandSender.sendMessage("You must be a player to use this command! Why even run this from the console?");
        }
        return false;
    }
}
