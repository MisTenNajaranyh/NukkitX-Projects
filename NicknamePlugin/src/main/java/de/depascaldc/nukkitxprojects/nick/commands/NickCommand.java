/*
 * This Plugin was written by depascaldc ( Discord: Pascal#1234 )
 *  
 * Copyright � 2019 | depascaldc - Management | All Rights Reserved
 * Unauthorized copying, distributing, and using of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Pascal Nei�e (depascaldc) <support@depascaldc.de> https://discord.gg/dqRMdEk
 *  
 */
package de.depascaldc.nukkitxprojects.nick.commands;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;

public class NickCommand extends Command {

	public NickCommand(String name) {
		super(name);
		setPermission("nickname.nick");
		setDescription("Set your nickname (for OP's set your or other nicknames)");
		setAliases(new String[] { "nick" });
	}

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		if (sender.hasPermission("nickname.nick")) {
			String usageop = "§cUse: §6/nickname (username / your nickname) (nickname)";
			String usageplayer = "§cUse: §6/nickname (nickname)";
			Player player = (Player) sender;
			String success = "§aThe player §e%player's §aname is now §6%newname";
			if (args.length > 1) {
				String target = args[0];
				String nameToSetForTarget = args[1];
				if (sender.isOp()) {
					Player targetPlayer = player.getServer().getPlayer(target);
					if (targetPlayer != null) {
						targetPlayer.setDisplayName(nameToSetForTarget);
						sender.sendMessage(success.replaceAll("%player", targetPlayer.getName()).replaceAll("%newname",
								nameToSetForTarget));
					} else
						sender.sendMessage("§cPlayer seems to be not online.");
				} else {
					sender.sendMessage(usageplayer);
					return true;
				}
			} else if (args.length > 0) {
				String nameToSet = args[0];
				player.setDisplayName(nameToSet);
				sender.sendMessage(success.replaceAll("%player", player.getName()).replaceAll("%newname",
						nameToSet));
			} else {
				if (sender.isOp()) {
					sender.sendMessage(usageop);
				} else {
					sender.sendMessage(usageplayer);
				}
			}
		}
		return false;
	}

}
