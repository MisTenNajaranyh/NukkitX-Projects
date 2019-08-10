/*
 * This Plugin was written by depascaldc ( Discord: Pascal#1234 )
 *  
 * Copyright © 2019 | depascaldc - Management | All Rights Reserved
 * Unauthorized copying, distributing, and using of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Pascal Neiﬂe (depascaldc) <support@depascaldc.de> https://discord.gg/dqRMdEk
 *  
 */
package de.depascaldc.nukkitxproject.discordminecraftsync.ingameevents;

import java.awt.Color;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerDeathEvent;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.event.player.PlayerQuitEvent;
import de.depascaldc.nukkitxproject.discordminecraftsync.Bot;
import net.dv8tion.jda.core.EmbedBuilder;

public class PlayerEvents implements Listener {

	@EventHandler(priority = EventPriority.HIGH)
	public void onEvent(PlayerJoinEvent e) {
		if (e != null && Bot.config.getBoolean("chatchannel.ingame_playerevents")) {
			try {
				Color color = new Color(0x00ff00);
				EmbedBuilder embed = new EmbedBuilder();
				embed.setTitle(e.getPlayer().getName());
				embed.setDescription(
						Bot.config.getString("player.join_message").replaceAll("%p", e.getPlayer().getName()));
				embed.setColor(color);
				Bot.getLogChannel().sendMessage(embed.build()).queue();
				embed.clear();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onEvent(PlayerQuitEvent e) {
		if (e != null && Bot.config.getBoolean("chatchannel.ingame_playerevents")) {
			try {
				Color color = new Color(0xff0000);
				EmbedBuilder embed = new EmbedBuilder();
				embed.setTitle(e.getPlayer().getName());
				embed.setDescription(
						Bot.config.getString("player.leave_message").replaceAll("%p", e.getPlayer().getName()));
				embed.setColor(color);
				Bot.getLogChannel().sendMessage(embed.build()).queue();
				embed.clear();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onEvent(PlayerDeathEvent e) {
		if (e != null && Bot.config.getBoolean("chatchannel.ingame_playerevents")) {
			String killer;
			if (e.getEntity().getKiller() == null) {
				killer = "Magic";
			} else
				killer = e.getEntity().getKiller().getName().toString();

			try {
				Color color = new Color(0xff5500);
				EmbedBuilder embed = new EmbedBuilder();
				embed.setDescription(Bot.config.getString("player.death_message").replaceAll("%p", e.getEntity().getPlayer().getName())
						.replaceAll("%killer", killer));
				embed.setColor(color);
				Bot.getLogChannel().sendMessage(embed.build()).queue();
				embed.clear();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

}
