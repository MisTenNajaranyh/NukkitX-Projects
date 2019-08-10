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

import cn.nukkit.Player;
import cn.nukkit.Server;
import de.depascaldc.nukkitxproject.discordminecraftsync.Bot;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class GuildMessageRecivedEvent extends ListenerAdapter {

	private String prefix = Bot.prefix;

	public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
		// do nothing when author BOT
		if (e.getAuthor().isBot())
			return;
		// do nothing when author sends DM
		if (e.getChannel().getType().equals(ChannelType.PRIVATE))
			return;

		if (!e.getMessage().getContentRaw().startsWith(prefix)) {
			try {
				// broadcast chat ingame
				if (!e.getChannel().equals(Bot.getLogChannel()))
					return;
				if (!Bot.config.getBoolean("chatchannel.discordchat_sync_enabled"))
					return;
				String message = Bot.config.getString("chatformatter").replaceAll("%player", e.getAuthor().getName())
								.replaceAll("%prefix", Bot.config.getString("bot_name_ingame"))
								.replaceAll("%message", e.getMessage().getContentStripped());
				if (message.length() == 0 && e.getMessage().getAttachments().size() == 0)
					return;
				for (Player player : Server.getInstance().getOnlinePlayers().values()) {
					player.sendMessage(message);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		try {
			String[] args = e.getMessage().getContentRaw().split("\\s+");
			String cmd = args[0].replaceFirst(prefix, "");
			if (cmd == null)
				return;

			if ((!args[0].startsWith(prefix)))
				return;
			// MAIN Command section
			if (cmd.equalsIgnoreCase("server") && Bot.config.getBoolean("command_enabled.server")) {
				try {
					String onlinecounter = Bot.getOnlinecounter();
					EmbedBuilder pingembed = new EmbedBuilder();
					pingembed.setTitle("Online Spieler " + onlinecounter);
					pingembed.setDescription("✔ Der Server ist erreichbar.");
					pingembed.setColor(0x00ffff);
					e.getChannel().sendTyping().queue();
					// e.getChannel().sendMessage(pingembed.build()).queue();
					pingembed.clear();
					e.getChannel().sendMessage(Bot.config.getString("command_messages.server.success")).queue();
				} catch (Exception ex) {
					ex.printStackTrace();
					e.getChannel().sendTyping().queue();
					e.getChannel().sendMessage(
							Bot.config.getString("command_messages.verify.error").replaceAll("%e", ex.getMessage()))
							.queue();
				}
			}
			if (cmd.equalsIgnoreCase("verify") && Bot.config.getBoolean("command_enabled.server")) {
				try {
					e.getChannel().sendTyping().queue();
					e.getChannel().sendMessage(Bot.config.getString("command_messages.server.success")).queue();
				} catch (Exception ex) {
					ex.printStackTrace();
					e.getChannel().sendTyping().queue();
					e.getChannel().sendMessage(
							Bot.config.getString("command_messages.server.error").replaceAll("%e", ex.getMessage()))
							.queue();
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	/**
	 * TODO add verify command with role adding
	 */
//	private Role getRole(Member m) {
//        for (Role role : m.getRoles()) {
//            return role;
//        }
//        return null;
//    }
//	private String getEndRole(Role role) {
//        if (role == null) return "";
//        String hex = role.getColor() != null ? Integer.toHexString(role.getColor().getRGB()).toUpperCase() : "99AAB5";
//        if (hex.length() == 8) hex = hex.substring(2);
//        String color = colors.get(hex);
//        if(color == null)color = "Â§c";
//        return color + role.getName();
//    }

}
