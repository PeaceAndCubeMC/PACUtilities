package fr.peaceandcube.pacutilities.listener;

import fr.peaceandcube.pacutilities.PACUtilities;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import net.ess3.api.events.PrivateMessageSentEvent;

public class PrivateMessageReceived implements Listener {

	@EventHandler
	public void onReceived(PrivateMessageSentEvent event) {
		Player recipient = Bukkit.getPlayer(event.getRecipient().getName());
		
		if ((recipient != null) && recipient.isOnline() && PACUtilities.playersFile.isMsgSoundEnabled(recipient.getUniqueId().toString())) {
			recipient.playSound(recipient.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0f, 1.0f);
		}
	}
}
