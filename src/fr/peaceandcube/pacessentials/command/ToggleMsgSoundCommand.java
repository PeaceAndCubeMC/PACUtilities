package fr.peaceandcube.pacessentials.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import fr.peaceandcube.pacessentials.file.PlayersFile;

public class ToggleMsgSoundCommand implements CommandExecutor, TabExecutor {
	private static final String TOGGLED = ChatColor.YELLOW + "Le son des messages privés est %s.";

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			String player = ((Player) sender).getUniqueId().toString();
			PlayersFile.setMsgSound(player, !PlayersFile.isMsgSoundEnabled(player));
			sender.sendMessage(String.format(ToggleMsgSoundCommand.TOGGLED, PlayersFile.isMsgSoundEnabled(player) ? "activé" : "désactivé"));
			return true;
		}
		return false;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		return new ArrayList<>();
	}
}
