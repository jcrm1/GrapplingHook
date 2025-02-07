package com.jcrm1.GrapplingHook;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class HookItem {
	public static ItemStack getHook() {
		ItemStack hookItem = new ItemStack(Material.FISHING_ROD);
		ItemMeta hookMeta = hookItem.getItemMeta();
		List<String> lore = new ArrayList<String>();
		lore.add("§7Grappling Hook for all your Grappling Needs");
		lore.add("§7Right-click to use");
		hookMeta.setLore(lore);
		hookMeta.setDisplayName("§rGrappling Hook");
		hookMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		hookMeta.spigot().setUnbreakable(true);
		hookItem.setItemMeta(hookMeta);
		return hookItem;
	}
}
