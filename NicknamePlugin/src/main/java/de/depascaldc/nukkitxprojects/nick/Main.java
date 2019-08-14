/*
 * This Plugin was written by depascaldc ( Discord: Pascal#1234 )
 *  
 * Copyright � 2019 | depascaldc - Management | All Rights Reserved
 * Unauthorized copying, distributing, and using of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Pascal Nei�e (depascaldc) <support@depascaldc.de> https://discord.gg/dqRMdEk
 *  
 */
package de.depascaldc.nukkitxprojects.nick;

import cn.nukkit.plugin.PluginBase;
import de.depascaldc.nukkitxprojects.nick.commands.NickCommand;

public class Main extends PluginBase {
	
	@Override
	public void onEnable() {
		this.getServer().getCommandMap().register("NicknamePlugin", new NickCommand("nickname"));
		this.getLogger().info("Nickname Plugin loaded");
	}
	@Override
	public void onDisable() {
		this.getLogger().info("Nickname Plugin disabled");
	}
}