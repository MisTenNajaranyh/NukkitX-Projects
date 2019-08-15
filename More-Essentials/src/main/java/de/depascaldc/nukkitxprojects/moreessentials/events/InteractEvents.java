package de.depascaldc.nukkitxprojects.moreessentials.events;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockPlaceEvent;
import cn.nukkit.event.player.PlayerDropItemEvent;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.item.Item;
import de.depascaldc.nukkitxprojects.moreessentials.Essentials;

public class InteractEvents implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onEvent(PlayerInteractEvent e) {
		if (e != null) {
			Player player = e.getPlayer();
	        Item item = player.getInventory().getItemInHand();
	        String id = String.valueOf(item.getId());
	        if (Essentials.itembandata.containsKey(id)) {
	            e.setCancelled();
	            player.sendPopup(Essentials.config.getString("messages.itemban_popup"));
	        }
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onEvent(BlockPlaceEvent e) {
		if (e != null) {
			Player player = e.getPlayer();
	        Item item = player.getInventory().getItemInHand();
	        String id = String.valueOf(item.getId());
	        if (Essentials.itembandata.containsKey(id)) {
	            e.setCancelled();
	            player.sendPopup(Essentials.config.getString("messages.itemban_popup"));
	        }
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onEvent(PlayerDropItemEvent e) {
		if (e != null) {
			Player player = e.getPlayer();
	        Item item = player.getInventory().getItemInHand();
	        String id = String.valueOf(item.getId());
	        if (Essentials.itembandata.containsKey(id)) {
	            e.setCancelled();
	            player.sendPopup(Essentials.config.getString("messages.itemban_popup"));
	        }
		}
	}

}
