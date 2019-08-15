package de.depascaldc.nukkitxprojects.moreessentials.commands;

import java.util.List;
import java.util.UUID;

import cn.nukkit.IPlayer;
import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.Config;
import de.depascaldc.nukkitxprojects.moreessentials.Essentials;

public class TempBanCommand extends Command  {

	public TempBanCommand(String name) {
		super(name);
		setPermission("moreessentials.tempban");
		setDescription("Type /tempban for help");
		setAliases(new String[] {"tban", "punish"});
	}

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		
		if(sender.hasPermission("moreessentials.tempban")) {
			
			
			
			printUsage(sender);
		}
		
		return false;
	}
	
	private void letsBanThePlayer(CommandSender sender, IPlayer target, String theReason) {
		List reasons = Essentials.config.getList("tempban_reasons");
		for(int i = 0; i < reasons.size(); i++) {
			String reason = String.valueOf(reasons.toArray()[i]);
			String reasonFinal = Essentials.config.getString("tempban_reasons." + reason + ".reason");
			if(theReason.equalsIgnoreCase(reason)) {
				
				
				
				final Config ban = Essentials.config;
				ban.set("players."+target.getUniqueId().toString()+".keyatplayer", "");
				ban.set("players."+target.getUniqueId().toString()+".keyatplayer", "");
				ban.set("players."+target.getUniqueId().toString()+".keyatplayer", "");
				ban.set("players."+target.getUniqueId().toString()+".keyatplayer", "");
				ban.set("players."+target.getUniqueId().toString()+".keyatplayer", "");
				ban.set("players."+target.getUniqueId().toString()+".keyatplayer", "");
				ban.save();
				ban.reload();
				if(target.isOnline())
					target.getPlayer().kick(reasonFinal.replaceAll("", "").replaceAll("", ""), false);
			} else {
				printUsage(sender);
			}
		}
	}
	
	private void printUsage(CommandSender sender) {
		sender.sendMessage("§cUSAGE >> §aPlayers are bannable for:");
		List reasons = Essentials.config.getList("tempban_reasons");
		for(int i = 0; i < reasons.size(); i++) {
			String reason = String.valueOf(reasons.toArray()[i]);
			String reasonFinal = Essentials.config.getString("tempban_reasons." + reason + ".reason");
			sender.sendMessage("§a/tempban §eplayername §6" + reason + " §abans the Player for: §6" + reasonFinal);
		}
		sender.sendMessage("§aUse: §6/tempban §e(reasonkey) §b(playername)");
	}

}
