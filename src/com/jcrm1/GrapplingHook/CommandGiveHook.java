package com.jcrm1.GrapplingHook;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandGiveHook implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length == 0) {
			Player player = (Player) sender;
			player.getInventory().addItem(HookItem.getHook());
			return true;
		} else {
			return false;
		}
	}

}
