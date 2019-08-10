package de.depascaldc.nukkitxproject.discordminecraftsync.ingamecommands;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import de.depascaldc.nukkitxproject.discordminecraftsync.Bot;

public class ReloadConfigCommand extends Command {

	public ReloadConfigCommand(String name) {
		super(name);
		setPermission("discordbot.reloadconfig");
		setDescription("DiscordchatSync config reload");
		setAliases(new String[] {"dcsreload"});
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		if(sender.isOp()) {
			try {
				Bot.config.reload();
				sender.sendMessage("§aConfig reloaded (If you want change the bot token restart the server.)");
			}
			catch (Exception ex) {
				sender.sendMessage("There was an error... Infos in Console");
				ex.printStackTrace();
			}
		}
		return false;
	}

}
