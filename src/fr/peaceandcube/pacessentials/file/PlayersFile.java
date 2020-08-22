package fr.peaceandcube.pacessentials.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class PlayersFile {
	private static File file;
	private static FileConfiguration config;

	public static void load(Plugin plugin, String name) {
		file = new File(plugin.getDataFolder(), name);
		
		if (!file.exists()) {
			plugin.getDataFolder().mkdirs();
			try {
				Files.createFile(file.toPath());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		config = YamlConfiguration.loadConfiguration(file);
	}

	protected static void save() {
		try {
			saveToDisk();
		} catch (IOException ex) {
			ex.printStackTrace();
			Bukkit.getLogger().log(Level.SEVERE, "Unable to save data file!");
		}
	}

	private static void saveToDisk() throws IOException {
		if (config != null && file != null) {
			config.save(file);
		}
	}

	public static boolean isMsgSoundEnabled(String player) {
		ConfigurationSection section = config.getConfigurationSection(player);
		if (section != null) {
			return section.getBoolean("msgsound");
		}
		return true;
	}

	public static void setMsgSound(String player, boolean msgSoundEnabled) {
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
