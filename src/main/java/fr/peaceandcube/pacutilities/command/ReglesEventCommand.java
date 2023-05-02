package fr.peaceandcube.pacutilities.command;

import fr.peaceandcube.pacutilities.PACUtilities;
import fr.peaceandcube.pacutilities.util.PlayerMessages;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ReglesEventCommand implements CommandExecutor, TabExecutor {
    private static final String PERM_REGLESEVENT = "pacutilities.reglesevent";

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender.hasPermission(PERM_REGLESEVENT)) {
            if (args.length >= 1 && args.length <= 2) {
                String event = args[0];
                String page = args.length >= 2 ? args[1] : "1";

                if (!PACUtilities.eventsFile.getEventNames().contains(event)) {
                    sender.sendMessage(PlayerMessages.REGLESEVENT_EVENT_UNKNOWN);
                    return true;
                }

                if (!PACUtilities.eventsFile.getEventPages(event).contains(page)) {
                    sender.sendMessage(PlayerMessages.REGLESEVENT_PAGE_UNKNOWN);
                    return true;
                }

                List<String> content = PACUtilities.eventsFile.getContent(event, page);
                TextComponent text = Component.newline();
                for (String contentLine : content) {
                    text = text.append(Component.text(contentLine)).append(Component.newline());
                }

                sender.sendMessage(text);
                return true;
            }
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (sender.hasPermission(PERM_REGLESEVENT)) {
            if (args.length == 1) {
                return PACUtilities.eventsFile.getEventNames().stream().filter(s -> s.startsWith(args[0])).toList();
            } else if (args.length == 2) {
                return PACUtilities.eventsFile.getEventPages(args[0]).stream().filter(s -> s.startsWith(args[1])).toList();
            }
        }
        return List.of();
    }
}
