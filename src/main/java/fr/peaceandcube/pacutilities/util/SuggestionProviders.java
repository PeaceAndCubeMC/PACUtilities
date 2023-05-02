package fr.peaceandcube.pacutilities.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

public class SuggestionProviders {

    public static List<String> getOnlinePlayers(String prefix) {
        List<String> players = new ArrayList<>();

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getName().toLowerCase().startsWith(prefix.toLowerCase())) {
                players.add(player.getName());
            }
        }

        return players;
    }

    public static List<String> getScoreboards(String prefix) {
        Set<Objective> objectives = Bukkit.getScoreboardManager().getMainScoreboard().getObjectives();
        List<String> scoreboards = new ArrayList<>();

        for (Objective objective : objectives) {
            if (objective.getName().toLowerCase().startsWith(prefix.toLowerCase())) {
                scoreboards.add(objective.getName());
            }
        }

        return scoreboards;
    }
}
