package fr.peaceandcube.pacessentials;

import org.bukkit.plugin.java.JavaPlugin;

import fr.peaceandcube.pacessentials.command.ToggleMsgSoundCommand;
import fr.peaceandcube.pacessentials.event.PrivateMessageReceived;
import fr.peaceandcube.pacessentials.file.PlayersFile;

public class PACEssentials extends JavaPlugin {

	@Override
	public void onEnable() {
		this.getCommand("togglemsgsound").setExecutor(new ToggleMsgSoundCommand());
		
		this.getServer().getPluginManager().registerEvents(new PrivateMessageReceived(), this);
		
		PlayersFile.load(this, "players.yml");
	}
}
