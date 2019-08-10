/*
 * This Plugin was written by depascaldc ( Discord: Pascal#1234 )
 *  
 * Copyright © 2019 | depascaldc - Management | All Rights Reserved
 * Unauthorized copying, distributing, and using of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Pascal Neiﬂe (depascaldc) <support@depascaldc.de> https://discord.gg/dqRMdEk
 *  
 */
package de.depascaldc.nukkitxproject.discordminecraftsync.botevents;

import cn.nukkit.Server;
import de.depascaldc.nukkitxproject.discordminecraftsync.Bot;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class GuildMemberEvents extends ListenerAdapter {

	@Override
	public void onGuildMemberJoin(GuildMemberJoinEvent e) {
		if (!e.getGuild().getId().equals(Bot.getDiscordServer().getId()))
			return;
		Member member = e.getMember();
		Server.getInstance().broadcastMessage(Bot.config.getString("bot_name_ingame") + " "
				+ Bot.config.getString("member_join_discord").replaceAll("%p", member.getUser().getName())
						.replaceAll("%d", member.getUser().getDiscriminator()));
	}

	@Override
	public void onGuildMemberLeave(GuildMemberLeaveEvent e) {
		if (!e.getGuild().getId().equals(Bot.getDiscordServer().getId()))
			return;
		Member member = e.getMember();
		Server.getInstance().broadcastMessage(Bot.config.getString("bot_name_ingame") + " "
				+ Bot.config.getString("member_leave_discord").replaceAll("%p", member.getUser().getName())
						.replaceAll("%d", member.getUser().getDiscriminator()));
	}

}