package fr.peaceandcube.pacutilities.command;

import fr.peaceandcube.pacutilities.event.PlayerBuyGradeEvent;
import fr.peaceandcube.pacutilities.event.PlayerFinishDonjonEvent;
import fr.peaceandcube.pacutilities.util.PlayerMessages;
import fr.peaceandcube.pacutilities.util.SuggestionProviders;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DispatchEventCommand implements CommandExecutor, TabExecutor {
    private static final String PERM_DISPATCHEVENT = "pacutilities.dispatchevent";
    private static final List<String> TYPES = List.of("boutique", "donjon");
    private static final TextComponent TYPE_INVALID = Component.text("Le type est invalide", TextColor.color(0xFF5555));

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender.hasPermission(PERM_DISPATCHEVENT)) {
            if (args.length >= 3) {
                String type = args[0];
                Player player = Bukkit.getPlayer(args[1]);

                if (!TYPES.contains(type)) {
                    sender.sendMessage(TYPE_INVALID);
                    return true;
                }

                if (player == null) {
                    sender.sendMessage(PlayerMessages.PLAYER_NOT_FOUND);
                    return true;
                }

                if (type.equals("donjon")) {
                    String name = String.join(" ", List.of(args).subList(2, args.length));
                    Bukkit.getServer().getPluginManager().callEvent(new PlayerFinishDonjonEvent(player, name));
                } else if (type.equals("boutique")) {
                    String gradeName = args[2];
                    int months = Integer.parseInt(args[3]);
                    Bukkit.getServer().getPluginManager().callEvent(new PlayerBuyGradeEvent(player, gradeName, months));
                }

                return true;
            }
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (sender.hasPermission(PERM_DISPATCHEVENT)) {
            if (args.length == 1) {
                return TYPES.stream().filter(s -> s.startsWith(args[0])).toList();
            } else if (args.length == 2) {
                return SuggestionProviders.getOnlinePlayers(args[1]);
            }
        }
        return List.of();
    }
}
