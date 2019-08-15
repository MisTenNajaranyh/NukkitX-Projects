package de.depascaldc.nukkitxprojects.moreessentials.commands;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;

public class WhitelistCommand extends Command {

	public WhitelistCommand(String name) {
		super(name);
		setPermission("moreessentials.whitelist.admin");
		setDescription("Settings Command whitelist");
		setAliases(new String[] {"wlist", "wl"});
	}

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		
		if(sender.hasPermission("moreessentials.whitelist.admin")) {
			
			
			
		}
		
		return false;
	}

}
