package fr.peaceandcube.pacutilities.command;

import java.util.List;

import fr.peaceandcube.pacutilities.PACUtilities;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ToggleMsgSoundCommand implements CommandExecutor, TabExecutor {
	private static final String PERM_TOGGLEMSGSOUND = "pacutilities.togglemsgsound";

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
		if (sender.hasPermission(PERM_TOGGLEMSGSOUND) && sender instanceof Player player) {
			String playerUuid = player.getUniqueId().toString();
			PACUtilities.playersFile.setMsgSound(playerUuid, !PACUtilities.playersFile.isMsgSoundEnabled(playerUuid));

			TextComponent toggled = Component.text("Le son des messages privés est " + (PACUtilities.playersFile.isMsgSoundEnabled(playerUuid) ? "activé" : "désactivé"), TextColor.color(0xFFFF55));
			sender.sendMessage(toggled);
			return true;
		}
		return false;
	}

	@Override
	public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
		return List.of();
	}
}
