/*
 * This Plugin was written by depascaldc ( Discord: Pascal#1234 )
 *  
 * Copyright © 2019 | depascaldc - Management | All Rights Reserved
 * Unauthorized copying, distributing, and using of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Pascal Neiﬂe (depascaldc) <support@depascaldc.de> https://discord.gg/dqRMdEk
 *  
 */
package de.depascaldc.nukkitxprojects.customjoinleave;

import cn.nukkit.command.Command;
import cn.nukkit.event.Listener;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import de.depascaldc.nukkitxprojects.customjoinleave.commands.ReloadCommand;
import de.depascaldc.nukkitxprojects.customjoinleave.events.JoinLeaveEvent;

public class Main extends PluginBase {

	public static Config config;

	@Override
	public void onEnable() {

		this.saveDefaultConfig();
		try {
			this.reloadConfig();
			this.getLogger().info("Custom-Join/Leave CONFIG loaded");
			Main.config = this.getConfig();
		} catch (Exception ex) {
			this.getLogger().warning("Custom-Join/Leave CONFIG could not be loaded");
			ex.printStackTrace();
		}
		this.getServer().getCommandMap().register("CustomJoinLeave", (Command) new ReloadCommand("customjoinreload"));
		this.getServer().getPluginManager().registerEvents((Listener) new JoinLeaveEvent(), this);
		this.getLogger().info("Custom-Join/Leave messages enabled");
	}

	@Override
	public void onDisable() {
		this.getLogger().info("Custom-Join/Leave messages disabled");
	}

}
