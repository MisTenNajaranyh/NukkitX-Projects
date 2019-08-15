package de.depascaldc.nukkitxprojects.moreessentials;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import cn.nukkit.command.Command;
import cn.nukkit.command.SimpleCommandMap;
import cn.nukkit.event.Listener;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.plugin.PluginManager;
import cn.nukkit.utils.Config;
import de.depascaldc.nukkitxprojects.moreessentials.commands.BanItemCommand;
import de.depascaldc.nukkitxprojects.moreessentials.commands.ReloadCommand;
import de.depascaldc.nukkitxprojects.moreessentials.commands.TempBanCommand;
import de.depascaldc.nukkitxprojects.moreessentials.commands.TempMuteCommand;
import de.depascaldc.nukkitxprojects.moreessentials.commands.WhitelistCommand;
import de.depascaldc.nukkitxprojects.moreessentials.events.PlayerEvents;

public class Essentials extends PluginBase implements Listener {

	public static Config config;
	public static Config bannedItemsConfig;
	public static Config whitelistConfig;
	public static Config bannedPlayersConfig;
	public static Config mutedPlayersConfig;

	public static HashMap<String, Boolean> itembandata = new HashMap<String, Boolean>();

	@Override
	public void onEnable() {

		this.saveDefaultConfig();
		this.saveAllResources();
		try {
			this.reloadConfig();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		Essentials.config = this.getConfig();

		if (Essentials.config.getInt("configversion") != 1) {
			this.getLogger().info("§cConfig outdated. Please remove old config file");
		}

		this.registerEvents();
		this.registerCommands();

		this.getLogger().info("More exxentials loaded");
	}

	@Override
	public void onDisable() {
		this.getLogger().info("More exxentials disabled");
	}

	private void registerCommands() {
		SimpleCommandMap map = this.getServer().getCommandMap();
		map.register("MoreEssentials", (Command) new BanItemCommand("banitem"));
		map.register("MoreEssentials", (Command) new ReloadCommand("mesreload"));
		map.register("MoreEssentials", (Command) new TempBanCommand("tempban"));
		map.register("MoreEssentials", (Command) new TempMuteCommand("tempmute"));
		map.register("MoreEssentials", (Command) new WhitelistCommand("meswhitelist"));
	}

	private void registerEvents() {
		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvents((Listener) new PlayerEvents(), this);
	}
	
	private void saveAllResources() {
		this.saveResource("internals/banneditems.yml", false);
		this.saveResource("internals/tempbans.yml", false);
		this.saveResource("internals/tempmutes.yml", false);
		this.saveResource("internals/whitelist.yml", false);
		Essentials.bannedItemsConfig = new Config(new File(this.getDataFolder() + "/internals", "bannedItems.yml"), 2);
		Essentials.bannedItemsConfig.getAll().forEach((id, b) -> Essentials.itembandata.put((String) id, (Boolean) b));
		
		Essentials.whitelistConfig = new Config(new File(this.getDataFolder() + "/internals", "whitelist.yml"), 2);
		Essentials.bannedPlayersConfig = new Config(new File(this.getDataFolder() + "/internals", "tempbans.yml"), 2);
		Essentials.mutedPlayersConfig = new Config(new File(this.getDataFolder() + "/internals", "tempmutes.yml"), 2);
		try {
			Essentials.bannedItemsConfig.reload();
			Essentials.whitelistConfig.reload();
			Essentials.bannedPlayersConfig.reload();
			Essentials.mutedPlayersConfig.reload();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static String getDurationBreakdown(long millis) {
		if (millis < 0) {
			throw new IllegalArgumentException("Duration must be greater than zero!");
		}
		long days = TimeUnit.MILLISECONDS.toDays(millis);
		millis -= TimeUnit.DAYS.toMillis(days);
		long hours = TimeUnit.MILLISECONDS.toHours(millis);
		millis -= TimeUnit.HOURS.toMillis(hours);
		long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
		millis -= TimeUnit.MINUTES.toMillis(minutes);
		long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);
		return config.getString("timeformat_expiesy").replaceAll("%d", String.valueOf(days))
				.replaceAll("%h", String.valueOf(hours)).replaceAll("%m", String.valueOf(minutes))
				.replaceAll("%s", String.valueOf(seconds));
	}

	public static Long dateToMS() {
		Date date = new Date();
		long timeMilli = date.getTime();
		return timeMilli;
	}

	public static String formatDate() {
		LocalDate localDate = LocalDate.now();
		int dd = localDate.getDayOfMonth();
		int mm = localDate.getMonthValue();
		int yyyy = localDate.getYear();
		String date = config.getString("dateformat");
		return date.replaceAll("%d", String.valueOf(dd)).replaceAll("%m", String.valueOf(mm)).replaceAll("%y",
				String.valueOf(yyyy));
	}

	public static String formatTime() {
		LocalTime localtime = LocalTime.now();
		int h = localtime.getHour();
		int m = localtime.getMinute();
		int s = localtime.getSecond();
		String hh = String.valueOf(h);
		String mm = String.valueOf(m);
		String ss = String.valueOf(s);
		if (h < 10)
			hh = "0" + hh;
		if (m < 10)
			mm = "0" + mm;
		if (s < 10)
			ss = "0" + ss;
		String date = config.getString("timeformat_localtime");
		return date.replaceAll("", hh).replaceAll("", mm).replaceAll("", ss);
	}

}
