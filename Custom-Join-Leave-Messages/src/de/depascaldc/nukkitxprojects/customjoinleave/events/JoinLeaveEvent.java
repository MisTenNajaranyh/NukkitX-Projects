/*
 * This Plugin was written by depascaldc ( Discord: Pascal#1234 )
 *  
 * Copyright © 2019 | depascaldc - Management | All Rights Reserved
 * Unauthorized copying, distributing, and using of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Pascal Neiﬂe (depascaldc) <support@depascaldc.de> https://discord.gg/dqRMdEk
 *  
 */
package de.depascaldc.nukkitxprojects.customjoinleave.events;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.event.player.PlayerQuitEvent;
import de.depascaldc.nukkitxprojects.customjoinleave.Main;

public class JoinLeaveEvent implements Listener {

	@EventHandler
	public void atEvent(PlayerJoinEvent e) {
		if (e.getPlayer().isOp()) {
			e.setJoinMessage("");
			if (Main.config.getBoolean("silentjoin_enabled"))
				return;
			e.getPlayer().getServer().broadcastMessage(
					Main.config.getString("messages.join.as_op").replaceAll("%p", e.getPlayer().getName()));
			return;
		}
		if (!e.getPlayer().playedBefore) {
			e.setJoinMessage("");
			e.getPlayer().getServer().broadcastMessage(
					Main.config.getString("messages.join.first_join").replaceAll("%p", e.getPlayer().getName()));
			return;
		}
		e.setJoinMessage("");
		e.getPlayer().getServer().broadcastMessage(
				Main.config.getString("messages.join.as_player").replaceAll("%p", e.getPlayer().getName()));
	}

	@EventHandler
	public void atEvent(PlayerQuitEvent e) {
		if (e.getPlayer().isOp()) {
			e.setQuitMessage("");
			if (Main.config.getBoolean("silentjoin_enabled"))
				return;
			e.getPlayer().getServer().broadcastMessage(
					Main.config.getString("messages.leave.as_op").replaceAll("%p", e.getPlayer().getName()));
			return;
		}
		e.setQuitMessage("");
		e.getPlayer().getServer().broadcastMessage(
				Main.config.getString("messages.leave.as_player").replaceAll("%p", e.getPlayer().getName()));
	}

}
