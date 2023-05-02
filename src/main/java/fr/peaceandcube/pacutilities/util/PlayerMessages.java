package fr.peaceandcube.pacutilities.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;

public class PlayerMessages {
    public static final TextComponent PLAYER_NOT_FOUND = error("Le joueur n'a pas été trouvé");
    public static final TextComponent OPERATION_INVALID = error("L'opération est invalide");

    public static final TextComponent REGLESEVENT_EVENT_UNKNOWN = error("L'event n'existe pas");
    public static final TextComponent REGLESEVENT_PAGE_UNKNOWN = error("La page n'existe pas");

    public static TextComponent error(String msg) {
        return Component.text(msg, TextColor.color(0xFF5555));
    }

    public static TextComponent success(String msg) {
        return Component.text(msg, TextColor.color(0x55FF55));
    }
}
