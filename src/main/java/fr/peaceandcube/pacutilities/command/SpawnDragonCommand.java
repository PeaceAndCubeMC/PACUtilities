package fr.peaceandcube.pacutilities.command;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.boss.DragonBattle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Stream;

public class SpawnDragonCommand implements CommandExecutor, TabExecutor {
    private static final String PERM_SPAWNDRAGON = "pacutilities.spawndragon";
    private static final TextComponent DRAGON_SPAWNED = Component.text(">> ", TextColor.color(0xAAAAAA), TextDecoration.BOLD)
            .append(Component.text("Le ", TextColor.color(0xFFFF55), TextDecoration.BOLD))
            .append(Component.text("dragon ", TextColor.color(0xFFAA00), TextDecoration.BOLD))
            .append(Component.text("est apparu ! ", TextColor.color(0xFFFF55), TextDecoration.BOLD))
            .append(Component.text("- ", TextColor.color(0xAAAAAA), TextDecoration.BOLD))
            .append(Component.text("/dragon", TextColor.color(0xFF55FF), TextDecoration.BOLD).clickEvent(ClickEvent.suggestCommand("/dragon")));
    private static final TextComponent DRAGON_CANCELLED = Component.text(">> ", TextColor.color(0xAAAAAA), TextDecoration.BOLD)
            .append(Component.text("Le ", TextColor.color(0xFFFF55), TextDecoration.BOLD))
            .append(Component.text("dragon ", TextColor.color(0xFFAA00), TextDecoration.BOLD))
            .append(Component.text("est annulé, il n'y a pas assez de joueurs :(", TextColor.color(0xFFFF55), TextDecoration.BOLD));

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender.hasPermission(PERM_SPAWNDRAGON)) {
            int minPlayerCount = args.length >= 1 ? Integer.parseInt(args[0]) : 0;
            int radiustocheck = args.length >= 2 ? Integer.parseInt(args[1]) : 50;
            World world = Bukkit.getServer().getWorld("world_the_end");

            if (world != null) {
                DragonBattle dragonBattle = world.getEnderDragonBattle();

                if (dragonBattle != null && dragonBattle.getEndPortalLocation() != null) {
                    Location portalPos = dragonBattle.getEndPortalLocation();
                    int nearbyPlayers = world.getNearbyPlayers(portalPos, radiustocheck).size();

                    if (nearbyPlayers >= minPlayerCount) {
                        world.spawnEntity(portalPos.clone().add(0, 1, 3).toCenterLocation(), EntityType.ENDER_CRYSTAL);
                        world.spawnEntity(portalPos.clone().add(0, 1, -3).toCenterLocation(), EntityType.ENDER_CRYSTAL);
                        world.spawnEntity(portalPos.clone().add(3, 1, 0).toCenterLocation(), EntityType.ENDER_CRYSTAL);
                        world.spawnEntity(portalPos.clone().add(-3, 1, 0).toCenterLocation(), EntityType.ENDER_CRYSTAL);
                        dragonBattle.initiateRespawn();
                        Bukkit.getServer().broadcast(DRAGON_SPAWNED);
                    } else {
                        Bukkit.getServer().broadcast(DRAGON_CANCELLED);
                    }
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender.hasPermission(PERM_SPAWNDRAGON)) {
            if (args.length == 1 || args.length == 2) {
                return Stream.of("1", "10", "100").filter(s -> s.startsWith(args[0])).toList();
            }
        }
        return List.of();
    }
}
