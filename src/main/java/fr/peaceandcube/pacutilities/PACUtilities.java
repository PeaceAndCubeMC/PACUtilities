package fr.peaceandcube.pacutilities;

import fr.peaceandcube.pacutilities.command.PacUtilitiesCommand;
import fr.peaceandcube.pacutilities.command.ReglesEventCommand;
import fr.peaceandcube.pacutilities.command.TicketTeteCommand;
import fr.peaceandcube.pacutilities.command.ToggleMsgSoundCommand;
import fr.peaceandcube.pacutilities.event.PrivateMessageReceived;
import fr.peaceandcube.pacutilities.file.EventsFile;
import fr.peaceandcube.pacutilities.file.PlayersFile;
import org.bukkit.plugin.java.JavaPlugin;

public class PACUtilities extends JavaPlugin {
	public static PlayersFile playersFile;
	public static EventsFile eventsFile;

	@Override
	public void onEnable() {
		this.getCommand("pacutilities").setExecutor(new PacUtilitiesCommand());
		this.getCommand("togglemsgsound").setExecutor(new ToggleMsgSoundCommand());
		this.getCommand("tickettete").setExecutor(new TicketTeteCommand());
		this.getCommand("reglesevent").setExecutor(new ReglesEventCommand());

		this.getServer().getPluginManager().registerEvents(new PrivateMessageReceived(), this);

		playersFile = new PlayersFile("players.yml", this);
		eventsFile = new EventsFile("events.yml", this);
	}

	public static void reload() {
		playersFile.reload();
		eventsFile.reload();
	}
}
