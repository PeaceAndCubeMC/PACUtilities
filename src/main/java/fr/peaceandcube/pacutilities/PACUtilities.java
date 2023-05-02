package fr.peaceandcube.pacutilities;

import fr.peaceandcube.pacutilities.command.*;
import fr.peaceandcube.pacutilities.listener.PrivateMessageReceived;
import fr.peaceandcube.pacutilities.file.EventsFile;
import fr.peaceandcube.pacutilities.file.PlayersFile;
import org.bukkit.plugin.java.JavaPlugin;

public class PACUtilities extends JavaPlugin {
	public static PlayersFile playersFile;
	public static EventsFile eventsFile;

	@Override
	public void onEnable() {
		this.getCommand("dispatchevent").setExecutor(new DispatchEventCommand());
		this.getCommand("pacutilities").setExecutor(new PacUtilitiesCommand());
		this.getCommand("reglesevent").setExecutor(new ReglesEventCommand());
		this.getCommand("spawndragon").setExecutor(new SpawnDragonCommand());
		this.getCommand("tickettete").setExecutor(new TicketTeteCommand());
		this.getCommand("timer").setExecutor(new TimerCommand());
		this.getCommand("togglemsgsound").setExecutor(new ToggleMsgSoundCommand());

		this.getServer().getPluginManager().registerEvents(new PrivateMessageReceived(), this);

		playersFile = new PlayersFile("players.yml", this);
		eventsFile = new EventsFile("events.yml", this);
	}

	public static void reload() {
		playersFile.reload();
		eventsFile.reload();
	}
}
