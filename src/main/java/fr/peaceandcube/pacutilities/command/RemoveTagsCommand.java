package fr.peaceandcube.pacutilities.command;

import fr.peaceandcube.pacutilities.util.PlayerMessages;
import fr.peaceandcube.pacutilities.util.SuggestionProviders;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RemoveTagsCommand implements CommandExecutor, TabExecutor {
    private static final String PERM_REMOVETAGS = "pacutilities.removetags";
    private static final List<String> FILTERS = List.of("all", "contains", "ends_with", "matches", "starts_with");
    private static final TextComponent INVALID_FILTER = PlayerMessages.error("Le filtre de tags est invalide");
    private static final String MESSAGE_ALL = "Tous les tags de %s ont été supprimés";
    private static final String MESSAGE_CONTAINS = "Tous les tags de %s contenant \"%s\" ont été supprimés";
    private static final String MESSAGE_ENDS_WITH = "Tous les tags de %s finissant par \"%s\" ont été supprimés";
    private static final String MESSAGE_MATCHES = "Tous les tags de %s correspondant à \"%s\" ont été supprimés";
    private static final String MESSAGE_STARTS_WITH = "Tous les tags de %s commençant par \"%s\" ont été supprimés";

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender.hasPermission(PERM_REMOVETAGS)) {
            if (args.length >= 2) {
                Player player = Bukkit.getPlayer(args[0]);
                String filter = args[1];

                if (player == null) {
                    sender.sendMessage(PlayerMessages.PLAYER_NOT_FOUND);
                    return true;
                }

                if (!FILTERS.contains(filter)) {
                    sender.sendMessage(INVALID_FILTER);
                    return true;
                }

                Component senderText = Component.empty();
                List<String> playerTags = player.getScoreboardTags().stream().toList();

                switch (filter) {
                    case "all" -> {
                        player.getScoreboardTags().forEach(player::removeScoreboardTag);
                        senderText = PlayerMessages.success(String.format(MESSAGE_ALL, player.getName()));
                    }
                    case "contains" -> {
                        if (args.length == 3) {
                            String tag = args[2];
                            playerTags.stream().filter(s -> s.contains(tag)).forEach(player::removeScoreboardTag);
                            senderText = PlayerMessages.success(String.format(MESSAGE_CONTAINS, player.getName(), tag));
                        }
                    }
                    case "ends_with" -> {
                        if (args.length == 3) {
                            String tag = args[2];
                            playerTags.stream().filter(s -> s.endsWith(tag)).forEach(player::removeScoreboardTag);
                            senderText = PlayerMessages.success(String.format(MESSAGE_ENDS_WITH, player.getName(), tag));
                        }
                    }
                    case "matches" -> {
                        if (args.length == 3) {
                            String tag = args[2];
                            playerTags.stream().filter(s -> s.matches(tag)).forEach(player::removeScoreboardTag);
                            senderText = PlayerMessages.success(String.format(MESSAGE_MATCHES, player.getName(), tag));
                        }
                    }
                    case "starts_with" -> {
                        if (args.length == 3) {
                            String tag = args[2];
                            playerTags.stream().filter(s -> s.startsWith(tag)).forEach(player::removeScoreboardTag);
                            senderText = PlayerMessages.success(String.format(MESSAGE_STARTS_WITH, player.getName(), tag));
                        }
                    }
                }

                sender.sendMessage(senderText);
                return true;
            }
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (sender.hasPermission(PERM_REMOVETAGS)) {
            if (args.length == 1) {
                return SuggestionProviders.getOnlinePlayers(args[0]);
            } else if (args.length == 2) {
                return FILTERS.stream().filter(s -> s.startsWith(args[1])).toList();
            } else if (args.length == 3 && !args[1].equals("all")) {
                return SuggestionProviders.getAllTags(args[2]);
            }
        }
        return List.of();
    }
}
