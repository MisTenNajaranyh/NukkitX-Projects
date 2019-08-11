/*
 * This Plugin was written by depascaldc ( Discord: Pascal#1234 )
 *  
 * Copyright © 2019 | depascaldc - Management | All Rights Reserved
 * Unauthorized copying, distributing, and using of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Pascal Neiﬂe (depascaldc) <support@depascaldc.de> https://discord.gg/dqRMdEk
 *  
 */
package de.depascaldc.nukkitxprojects.customjoinleave.commands;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import de.depascaldc.nukkitxprojects.customjoinleave.Main;

public class ReloadCommand extends Command {

	public ReloadCommand(String name) {
		super(name);
		setPermission("customjoin.reload");
		setDescription("Reload PluginConfig");
		setAliases(new String [] {"cjlreload", "cjlr"});
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		
		if(sender.isOp()) {
			
			try {
				Main.config.reload();
				sender.sendMessage("ßaConfig successfully reloaded");
			} catch (Exception ex) {
				sender.sendMessage("ßcConfig error see additional informations in console.");
			}
			
		}
		
		return false;
	}
	
	

}
