/*
 * This Plugin was written by depascaldc ( Discord: Pascal#1234 )
 *  
 * Copyright © 2019 | depascaldc - Management | All Rights Reserved
 * Unauthorized copying, distributing, and using of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Pascal Neiﬂe (depascaldc) <support@depascaldc.de> https://discord.gg/dqRMdEk
 *  
 */
package de.depascaldc.nukkitxprojects.luckyblocks;

import cn.nukkit.event.Listener;
import cn.nukkit.plugin.PluginBase;

public class Main extends PluginBase {
	
	@Override
	public void onEnable() {
		this.getLogger().info("Luckyblocks enabled");
		this.getServer().getPluginManager().registerEvents((Listener) new BlockDestroy(), this);
	}
	
	@Override
	public void onDisable() {
		this.getLogger().info("Luckyblocks disabled");
	}

}
