package fr.peaceandcube.pacutilities.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class Timer {
    public static final Map<Player, Timer> PLAYER_TIMERS = new HashMap<>();
    private final Player player;
    private long startTime;
    private long endTime;
    private String scoreboard;
    private boolean ifBetter;

    public Timer(Player player) {
        this.player = player;
        PLAYER_TIMERS.put(player, this);
    }

    public void start() {
        this.startTime = System.currentTimeMillis();
    }

    public void stop() {
        this.endTime = System.currentTimeMillis();
        this.store();
    }

    public void setScoreboard(String scoreboard, boolean ifBetter) {
        this.scoreboard = scoreboard;
        this.ifBetter = ifBetter;
    }

    public void store() {
        if (this.scoreboard != null) {
            int previous = this.player.getScoreboard().getObjective(this.scoreboard).getScore(this.player.getName()).getScore();
            int current = (int) ((this.endTime - this.startTime) / 1000);
            String scoreboardCmd = "scoreboard players set " + this.player.getName() + " " + this.scoreboard + " " + current;
            if (this.ifBetter) {
                if (current < previous) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), scoreboardCmd);
                }
            } else {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), scoreboardCmd);
            }
        }
    }

    public void remove() {
        PLAYER_TIMERS.remove(this.player);
    }

    public String getTime() {
        long time = this.endTime - this.startTime;
        Date date = new Date(time);
        SimpleDateFormat formatter = new SimpleDateFormat("HH'h' mm'm' ss,SSS's'");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        return formatter.format(date);
    }
}
