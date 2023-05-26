package fr.peaceandcube.pacutilities.event;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

public class PlayerBuyGradeEvent extends PlayerEvent {
    private static final HandlerList handlers = new HandlerList();

    private final String gradeName;
    private final int months;

    public PlayerBuyGradeEvent(Player player, String gradeName, int months) {
        super(player);
        this.gradeName = gradeName;
        this.months = months;
    }

    public String getGradeName() {
        return gradeName;
    }

    public int getMonths() {
        return months;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    @NotNull
    public static HandlerList getHandlerList() {
        return handlers;
    }
}
