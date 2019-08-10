/*
 * This Plugin was written by depascaldc ( Discord: Pascal#1234 )
 *  
 * Copyright © 2019 | depascaldc - Management | All Rights Reserved
 * Unauthorized copying, distributing, and using of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Pascal Neiße (depascaldc) <support@depascaldc.de> https://discord.gg/dqRMdEk
 *  
 */
package de.depascaldc.nukkitxproject.discordminecraftsync.ingamecommands;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import de.depascaldc.nukkitxproject.discordminecraftsync.Bot;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;

public class DiscordCommand extends Command {

	public DiscordCommand(String name) {
		super(name);
		setAliases(new String[] { "dc", "disc" });
		setDescription("Infos über unseren Discordserver");
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		/**
		 * TODO add custom names for vars
		 */
		Guild g = Bot.getDiscordServer();
		sender.sendMessage("§6Servername: §a" + g.getName());
		sender.sendMessage("§6User count: §a" + g.getMembers().size());
		sender.sendMessage("§Online: §a" + getOnlineUser(g));
		sender.sendMessage("§6Channel count: §a" + g.getChannels().size());
		sender.sendMessage("§l§aInviteLink: §b" + "");

		return false;
	}

	private int getOnlineUser(Guild g) {
		int counter = 0;
		for (Member member : g.getMembers()) {
			if (member.getOnlineStatus().equals(OnlineStatus.ONLINE)
					|| member.getOnlineStatus().equals(OnlineStatus.IDLE)
					|| member.getOnlineStatus().equals(OnlineStatus.DO_NOT_DISTURB)) {
				counter++;
			}
		}
		return counter;
	}

}
