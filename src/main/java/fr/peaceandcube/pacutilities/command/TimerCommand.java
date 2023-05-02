package fr.peaceandcube.pacutilities.command;

import fr.peaceandcube.pacutilities.util.PlayerMessages;
import fr.peaceandcube.pacutilities.util.SuggestionProviders;
import fr.peaceandcube.pacutilities.util.Timer;
import net.kyori.adventure.text.Component;
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

public class TimerCommand implements CommandExecutor, TabExecutor {
    private static final String PERM_TIMER = "pacutilities.timer";
    private static final List<String> OPERATIONS = List.of("start", "stop", "store");
    private static final String MESSAGE_STARTED = "Un chrono a été démarré pour %s";
    private static final String MESSAGE_STOPPED = "Le chrono a été arrêté pour %s";
    private static final String MESSAGE_TIME = "Tu as fait un temps de %s !";
    private static final String MESSAGE_STORED = "Le chrono de %s sera stocké dans le score %s";
    private static final String MESSAGE_UNKNOWN_SCOREBOARD = "Le score %s n'existe pas";
    private static final String MESSAGE_NOT_STARTED = "%s n'a pas de chrono démarré";

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender.hasPermission(PERM_TIMER)) {
            if (args.length >= 2) {
                Player player = Bukkit.getPlayer(args[0]);
                String operation = args[1];

                if (player == null) {
                    sender.sendMessage(PlayerMessages.PLAYER_NOT_FOUND);
                    return true;
                }

                if (!OPERATIONS.contains(operation)) {
                    sender.sendMessage(PlayerMessages.OPERATION_INVALID);
                    return true;
                }

                Component senderText = Component.empty();

                switch (operation) {
                    case "start" -> {
                        Timer timer = new Timer(player);
                        timer.start();
                        senderText = PlayerMessages.success(String.format(MESSAGE_STARTED, player.getName()));
                    }
                    case "stop" -> {
                        Timer timerStop = Timer.PLAYER_TIMERS.get(player);
                        if (timerStop != null) {
                            timerStop.stop();
                            senderText = PlayerMessages.success(String.format(MESSAGE_STOPPED, player.getName()));
                            player.sendMessage(PlayerMessages.result(String.format(MESSAGE_TIME, timerStop.getTime())));
                            timerStop.remove();
                        } else {
                            senderText = PlayerMessages.error(String.format(MESSAGE_NOT_STARTED, player.getName()));
                        }
                    }
                    case "store" -> {
                        Timer timerStore = Timer.PLAYER_TIMERS.get(player);
                        if (timerStore != null) {
                            String scoreboard = args[2];
                            Objective objective = Bukkit.getScoreboardManager().getMainScoreboard().getObjective(scoreboard);

                            if (objective == null) {
                                sender.sendMessage(PlayerMessages.error(String.format(MESSAGE_UNKNOWN_SCOREBOARD, scoreboard)));
                                return true;
                            }

                            timerStore.setScoreboard(scoreboard, args.length == 4 && Boolean.parseBoolean(args[3]));
                            senderText = PlayerMessages.success(String.format(MESSAGE_STORED, player.getName(), args[2]));
                        } else {
                            senderText = PlayerMessages.error(String.format(MESSAGE_NOT_STARTED, player.getName()));
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
        if (sender.hasPermission(PERM_TIMER)) {
            if (args.length == 1) {
                return SuggestionProviders.getOnlinePlayers(args[0]);
            } else if (args.length == 2) {
                return OPERATIONS.stream().filter(s -> s.startsWith(args[1])).toList();
            } else if (args.length == 3 && args[1].equals("store")) {
                return SuggestionProviders.getScoreboards(args[2]);
            } else if (args.length == 4 && args[1].equals("store")) {
                return Stream.of("false", "true").filter(s -> s.startsWith(args[3])).toList();
            }
        }
        return List.of();
    }
}
