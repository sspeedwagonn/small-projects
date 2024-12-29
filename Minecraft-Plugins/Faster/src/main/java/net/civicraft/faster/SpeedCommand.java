package net.civicraft.faster;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SpeedCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (commandSender instanceof Player player) {
            if (args.length < 2) {
                player.sendMessage("Usage: /speed <walk|fly> <1-10>");
                return false;
            }

            String mode = args[0].toLowerCase();
            int speed;

            try {
                speed = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                player.sendMessage("Invalid speed value. Please enter a number between 1 and 10.");
                return false;
            }

            if (speed < 1 || speed > 10) {
                player.sendMessage("Please specify a speed between 1 and 10.");
                return false;
            }

            float fspeed = speed / 10.0f;

            switch (mode) {
                case "fly":
                    player.setFlySpeed(fspeed);
                    player.sendMessage("Set fly speed to " + speed + "!");
                    break;

                case "walk":
                    player.setWalkSpeed(fspeed);
                    player.sendMessage("Set walk speed to " + speed + "!");
                    break;

                default:
                    player.sendMessage("Invalid usage. /speed <walk|fly> <1-10>");
                    return false;
            }
        } else {
            commandSender.sendMessage("You must be a player to use this command!");
        }

        return true;
    }
}
