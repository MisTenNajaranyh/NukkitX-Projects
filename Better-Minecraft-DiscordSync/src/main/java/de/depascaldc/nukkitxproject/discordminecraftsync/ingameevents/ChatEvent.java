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
import cn.nukkit.event.player.PlayerChatEvent;
import de.depascaldc.nukkitxproject.discordminecraftsync.Bot;
import net.dv8tion.jda.core.EmbedBuilder;

public class ChatEvent implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onChat(PlayerChatEvent e) {
		System.out.println("playerchatevent");
		if (Bot.config.getBoolean("chatchannel.ingamechat_sync_enabled")) {
			if (e == null)
				return;
			try {
				Color color = new Color(0x00ffff);
				EmbedBuilder chat = new EmbedBuilder();
				chat.setTitle(e.getPlayer().getName());
				if (Bot.config.getString("chatname") != "") {
					chat.setAuthor(Bot.config.getString("chatname"));
				}
				chat.setDescription(e.getMessage());
				if (Bot.config.getBoolean("")) {
					chat.setColor(Bot.getRandomColor());
				} else {
					chat.setColor(color);
				}
				Bot.getLogChannel().sendMessage(chat.build()).queue();
				chat.clear();
			} catch (Exception ex) {
				System.out.println("err");
				ex.printStackTrace();
			}
		}

	}

}
