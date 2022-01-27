package fr.peaceandcube.pacutilities.file;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.Plugin;

public class PlayersFile extends YamlFile {

	public PlayersFile(String name, Plugin plugin) {
		super(name, plugin);
	}

	public boolean isMsgSoundEnabled(String player) {
		ConfigurationSection section = config.getConfigurationSection(player);
		if (section != null) {
			return section.getBoolean("msgsound");
		}
		return true;
	}

	public void setMsgSound(String player, boolean msgSoundEnabled) {
		ConfigurationSection section;
		if (config.getConfigurationSection(player) != null) {
			section = config.getConfigurationSection(player);
		} else {
			section = config.createSection(player);
		}
		
		section.set("msgsound", msgSoundEnabled);
		save();
	}
}
