package fr.peaceandcube.pacutilities.event;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

public class PlayerFinishDonjonEvent extends PlayerEvent {
    private static final HandlerList handlers = new HandlerList();

    private final String donjonName;

    public PlayerFinishDonjonEvent(Player player, String donjonName) {
        super(player);
        this.donjonName = donjonName;
    }

    public String getDonjonName() {
        return donjonName;
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
