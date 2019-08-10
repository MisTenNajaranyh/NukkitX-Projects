/*
 * This Plugin was written by depascaldc ( Discord: Pascal#1234 )
 *  
 * Copyright © 2019 | depascaldc - Management | All Rights Reserved
 * Unauthorized copying, distributing, and using of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Pascal Neiße (depascaldc) <support@depascaldc.de> https://discord.gg/dqRMdEk
 *  
 */
package de.depascaldc.nukkitxproject.discordminecraftsync;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import javax.security.auth.login.LoginException;

import cn.nukkit.Server;
import cn.nukkit.command.Command;
import cn.nukkit.command.SimpleCommandMap;
import cn.nukkit.event.Listener;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.plugin.PluginManager;
import cn.nukkit.utils.Config;
import de.depascaldc.nukkitxproject.discordminecraftsync.botevents.GuildMemberEvents;
import de.depascaldc.nukkitxproject.discordminecraftsync.botevents.GuildMessageRecivedEvent;
import de.depascaldc.nukkitxproject.discordminecraftsync.botevents.ReadyClientEvent;
import de.depascaldc.nukkitxproject.discordminecraftsync.ingamecommands.ReloadConfigCommand;
import de.depascaldc.nukkitxproject.discordminecraftsync.ingameevents.ChatEvent;
import de.depascaldc.nukkitxproject.discordminecraftsync.ingameevents.PlayerEvents;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.Game.GameType;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.VoiceChannel;

public class Bot extends PluginBase {

	public static JDA jda;
	public static String prefix;
	public static Config config;

	private int configversion = 1;

	private static Bot instance;

	public static Bot getInstance() {
		return Bot.instance;
	}

	public static Server server;

	// TODO verification command
	public static Map<String, String> verification = new HashMap<String, String>();

	@Override
	public void onEnable() {
		this.saveDefaultConfig();
		this.reloadConfig();
		Bot.config = this.getConfig();
		Bot.prefix = config.getString("bot_prefix");

		this.registerListener();
		this.registerCommands();

		try {
			if (configversion > config.getInt("configversion")) {
				this.getLogger().warning("§ePlease update your config.yml and restart the Server. "
						+ "The old Configfile is outdated. ");
			}
			// build the bot
			jda = new JDABuilder(AccountType.BOT).setToken(config.getString("bot_token")).build();
			jda.getPresence().setGame(Game.of(Game.GameType.DEFAULT, config.getString("gameactivity.text")));
			/**
			 * TTODO add presence option
			 */
//			if (config.getBoolean("gameactivity.enabled")) {
//				Bot.setActivity(config.getString("gameactivity.onlinestatus"), config.getString("gameactivity.type"),
//						config.getString("gameactivity.text"));
//			}
			// add all botevent listener
			jda.addEventListener(new GuildMessageRecivedEvent());
			jda.addEventListener(new ReadyClientEvent());
			jda.addEventListener(new GuildMemberEvents());

			getServer().getLogger().info(">>>>DiscordBot started / logged in.");

		} catch (LoginException e) {
			e.printStackTrace();
		}

		Bot.server = this.getServer();

		// update online counter channel if enabled
		if (config.getBoolean("onlinecounter.enabled")) {
			getServer().getScheduler().scheduleRepeatingTask(this, new Runnable() {
				public void run() {
					Bot.getCounterChannel().getManager().setName(Bot.getOnlinecounter()).queue();
				}
			}, 20 * 60 * 3);
		}

	}

	@Override
	public void onDisable() {
		getServer().getLogger().info(">>>>>DiscordBot stops");
		TextChannel t = getLogChannel();
		EmbedBuilder stopembed = new EmbedBuilder();
		stopembed.setDescription(config.getString("server.stop"));
		stopembed.setColor(0xffae00);
		t.sendMessage(stopembed.build()).queue();
		stopembed.clear();
		jda.shutdown();
		getServer().getScheduler().cancelTask(this);
	}

	@Override
	public void onLoad() {
		getServer().getLogger().info("DiscordBot loading...");
	}

	private void registerListener() {
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents((Listener) new PlayerEvents(), this);
		pm.registerEvents((Listener) new ChatEvent(), this);

	}

	/**
	 * TODO create discord serverinfo commands // verify command
	 */
	private void registerCommands() {
		SimpleCommandMap map = this.getServer().getCommandMap();
		map.register("Better-Minecraft-DiscordSync", (Command) new ReloadConfigCommand("discordchatsyncreload"));
	}

//	private static void setActivity(String onlinestatus, String type, String text) {
//		if (onlinestatus.toLowerCase() == "idle") {
//			if (type.toLowerCase() == "listening") {
//				jda.getPresence().setPresence(OnlineStatus.IDLE, true);
//				jda.getPresence().setGame(Game.of(Game.GameType.LISTENING, text));
//			} else if (type.toLowerCase() == "watching") {
//				jda.getPresence().setPresence(OnlineStatus.IDLE, true);
//				jda.getPresence().setGame(Game.of(Game.GameType.WATCHING, text));
//			} else if (type.toLowerCase() == "playing") {
//				jda.getPresence().setPresence(OnlineStatus.IDLE, true);
//				jda.getPresence().setGame(Game.of(Game.GameType.DEFAULT, text));
//			} else if (type.toLowerCase() == "streaming") {
//				jda.getPresence().setPresence(OnlineStatus.IDLE, true);
//				jda.getPresence().setGame(Game.of(Game.GameType.STREAMING, text));
//			}
//		} else if (onlinestatus.toLowerCase() == "online") {
//			if (type.toLowerCase() == "listening") {
//				jda.getPresence().setPresence(OnlineStatus.ONLINE, true);
//				jda.getPresence().setGame(Game.of(Game.GameType.LISTENING, text));
//			} else if (type.toLowerCase() == "watching") {
//				jda.getPresence().setPresence(OnlineStatus.ONLINE, true);
//				jda.getPresence().setGame(Game.of(Game.GameType.WATCHING, text));
//			} else if (type.toLowerCase() == "playing") {
//				jda.getPresence().setPresence(OnlineStatus.ONLINE, true);
//				jda.getPresence().setGame(Game.of(Game.GameType.DEFAULT, text));
//			} else if (type.toLowerCase() == "streaming") {
//				jda.getPresence().setPresence(OnlineStatus.ONLINE, true);
//				jda.getPresence().setGame(Game.of(Game.GameType.STREAMING, text));
//			}
//		} else if (onlinestatus.toLowerCase() == "dnd") {
//			if (type.toLowerCase() == "listening") {
//				jda.getPresence().setPresence(OnlineStatus.DO_NOT_DISTURB, true);
//				jda.getPresence().setGame(Game.of(Game.GameType.LISTENING, text));
//			} else if (type.toLowerCase() == "watching") {
//				jda.getPresence().setPresence(OnlineStatus.DO_NOT_DISTURB, true);
//				jda.getPresence().setGame(Game.of(Game.GameType.WATCHING, text));
//			} else if (type.toLowerCase() == "playing") {
//				jda.getPresence().setPresence(OnlineStatus.DO_NOT_DISTURB, true);
//				jda.getPresence().setGame(Game.of(Game.GameType.DEFAULT, text));
//			} else if (type.toLowerCase() == "streaming") {
//				jda.getPresence().setPresence(OnlineStatus.DO_NOT_DISTURB, true);
//				jda.getPresence().setGame(Game.of(Game.GameType.STREAMING, text));
//			}
//		}
//	}

	public static String getOnlinecounter() {
		int onlinecounter = 0;
		int maxonline = 0;
		try {
			onlinecounter = Server.getInstance().getOnlinePlayers().size();
			maxonline = Server.getInstance().getMaxPlayers();
		} catch (Exception ex) {
			ex.printStackTrace();
			onlinecounter = 0;
			maxonline = 0;
		}
		String countername = config.getString("onlinecounter.countername");
		String on = String.valueOf(onlinecounter);
		String maxon = String.valueOf(maxonline);
		String toreturn = countername.replaceAll("%on", on).replaceAll("%maxon", maxon);
		return toreturn;
	}

	public static Color getRandomColor() {
		return new Color((int) (Math.random() * 0x1000000));
	}

	public static TextChannel getLogChannel() {
		return jda.getTextChannelById(config.getString("chatchannel.id"));
	}

	public static VoiceChannel getCounterChannel() {
		return jda.getVoiceChannelById(config.getString("onlinecounter.channel_id"));
	}

	public static Guild getDiscordServer() {
		return jda.getGuildById(config.getString("guild_to_sync_id"));
	}

}
