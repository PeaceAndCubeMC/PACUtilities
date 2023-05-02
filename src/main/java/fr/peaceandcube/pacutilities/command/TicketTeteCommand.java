package fr.peaceandcube.pacutilities.command;

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
import org.bukkit.scoreboard.Objective;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Stream;

public class TicketTeteCommand implements CommandExecutor, TabExecutor {
    private static final String PERM_TICKETTETE = "pacutilities.tickettete";
    private static final String SCOREBOARD = "ticket_tete";
    private static final List<String> OPERATIONS = List.of("add", "get", "remove");
    private static final String MESSAGE_ADD = "%d tickets tête ont été donnés à %s";
    private static final String MESSAGE_REMOVE = "%d tickets tête ont été retirés à %s";
    private static final String MESSAGE_GET = "%s a %d tickets tête";

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender.hasPermission(PERM_TICKETTETE)) {
            if (args.length >= 2 && args.length <= 3) {
                String operation = args[0];
                Player player = Bukkit.getPlayer(args[1]);
                int amount = args.length >= 3 ? Integer.parseInt(args[2]) : 1;

                if (!OPERATIONS.contains(operation)) {
                    sender.sendMessage(PlayerMessages.OPERATION_INVALID);
                    return true;
                }

                if (player == null) {
                    sender.sendMessage(PlayerMessages.PLAYER_NOT_FOUND);
                    return true;
                }

                String scoreboardCmd = "scoreboard players " + operation + " " + player.getName() + " " + SCOREBOARD;
                Component senderText = Component.empty();

                if (operation.equals("add") || operation.equals("remove")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), scoreboardCmd + " " + amount);

                    String msgPart = operation.equals("add") ? "donnés" : "retirés";
                    TextComponent playerText = Component.text("[", TextColor.color(0xAAAAAA))
                            .append(Component.text("Ticket tête", TextColor.color(0x00AAAA)))
                            .append(Component.text("] " + amount + " tickets tête t'ont été " + msgPart, TextColor.color(0xAAAAAA)));
                    player.sendMessage(playerText);

                    String message = operation.equals("add") ? MESSAGE_ADD : MESSAGE_REMOVE;
                    senderText = PlayerMessages.success(String.format(message, amount, player.getName()));
                }

                if (operation.equals("get")) {
                    Objective objective = Bukkit.getScoreboardManager().getMainScoreboard().getObjective(SCOREBOARD);
                    int score = objective != null ? objective.getScore(player.getName()).getScore() : 0;

                    senderText = PlayerMessages.success(String.format(MESSAGE_GET, player.getName(), score));
                }

                sender.sendMessage(senderText);
                return true;
            }
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (sender.hasPermission(PERM_TICKETTETE)) {
            if (args.length == 1) {
                return OPERATIONS.stream().filter(s -> s.startsWith(args[0])).toList();
            } else if (args.length == 2) {
                return SuggestionProviders.getOnlinePlayers(args[1]);
            } else if (args.length == 3 && (args[0].equals("add") || args[0].equals("remove"))) {
                return Stream.of("1", "10", "100").filter(s -> s.startsWith(args[2])).toList();
            }
        }
        return List.of();
    }
}
