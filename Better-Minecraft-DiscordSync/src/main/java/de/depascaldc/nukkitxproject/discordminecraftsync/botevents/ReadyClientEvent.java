/*
 * This Plugin was written by depascaldc ( Discord: Pascal#1234 )
 *  
 * Copyright © 2019 | depascaldc - Management | All Rights Reserved
 * Unauthorized copying, distributing, and using of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Pascal Neiße (depascaldc) <support@depascaldc.de> https://discord.gg/dqRMdEk
 *  
 */
package de.depascaldc.nukkitxproject.discordminecraftsync.botevents;

import de.depascaldc.nukkitxproject.discordminecraftsync.Bot;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class ReadyClientEvent extends ListenerAdapter {

	public void onReady(ReadyEvent e) {

		if (Bot.jda != null) {
			try {
				TextChannel t = Bot.getLogChannel();
				EmbedBuilder startembed = new EmbedBuilder();
				startembed.setDescription(Bot.config.getString("server.start"));
				startembed.setColor(0x00ff00);
				t.sendMessage(startembed.build()).queue();
				startembed.clear();
			} catch (Exception ex) {
				ex.printStackTrace();
			}

		}

	}

}