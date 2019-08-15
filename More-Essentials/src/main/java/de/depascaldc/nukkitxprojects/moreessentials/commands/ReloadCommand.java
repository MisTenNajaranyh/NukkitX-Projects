package de.depascaldc.nukkitxprojects.moreessentials.commands;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import de.depascaldc.nukkitxprojects.moreessentials.Essentials;

public class ReloadCommand extends Command {
	
	public ReloadCommand(String name) {
		super(name);
		setPermission("moreessentials.reload");
		setDescription("Reload More-Essentials Config");
		setAliases(new String[] {"mesrel"});
	}

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		
		if(sender.hasPermission("moreessentials.reload")) {
			
			try {
				Essentials.config.reload();
				sender.sendMessage("§aConfig reloaded.");
			} catch (Exception ex) {
				ex.printStackTrace();
				sender.sendMessage("§cConfig could not be reloaded see additional infos in console");
			}
			
		}
		
		return false;
	}

}
