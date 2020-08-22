package fr.peaceandcube.pacessentials.event;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import fr.peaceandcube.pacessentials.file.PlayersFile;
import net.ess3.api.events.PrivateMessageSentEvent;

public class PrivateMessageReceived implements Listener {

	@EventHandler
	public void onReceived(PrivateMessageSentEvent event) {
		Player recipient = Bukkit.getPlayer(event.getRecipient().getName());
		
		if ((recipient != null) && recipient.isOnline() && PlayersFile.isMsgSoundEnabled(recipient.getUniqueId().toString())) {
			recipient.playSound(recipient.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0f, 1.0f);
		}
	}
}
