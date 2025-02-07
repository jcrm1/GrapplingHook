package com.jcrm1.GrapplingHook;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerFishEvent.State;
import org.bukkit.util.Vector;

public class SubscribePlayerFishEvent implements Listener {
	private static final float HOOK_INPUT_START = 0.0f;
	private static final float HOOK_INPUT_END = 201.0f;
	private static final float HOOK_OUTPUT_START = 2.5f;
	private static final float HOOK_OUTPUT_END = 10.0f;
	private static final float HOOK_SCALAR = (HOOK_OUTPUT_END - HOOK_OUTPUT_START) / (HOOK_INPUT_END - HOOK_INPUT_START);
	private GrapplingHook plugin;
	public SubscribePlayerFishEvent(GrapplingHook plugin) {
		this.plugin = plugin;
	}
	@EventHandler
	public void onInteract(PlayerFishEvent event) {
		if (event.getPlayer().getItemInHand().getItemMeta().hasDisplayName() && event.getPlayer().getItemInHand().getItemMeta().getDisplayName().equals(HookItem.getHook().getItemMeta().getDisplayName()) && event.getPlayer().getItemInHand().getType() == HookItem.getHook().getType() && event.getState() != State.FISHING) {
			if (event.getState() == State.CAUGHT_FISH) {
				event.setCancelled(true);
			} else if (event.getState() == State.CAUGHT_ENTITY & event.getCaught() != null) {
				if (plugin.isGrappleModeTP()) {
					event.getPlayer().setAllowFlight(true);
					if (plugin.isAllowPullEntity() == true) {
						Bukkit.getScheduler().runTask(plugin, new PullTeleporter(event.getCaught(), event.getPlayer(), plugin.getStepSize(), plugin));
					} else {
						Bukkit.getScheduler().runTask(plugin, new PullTeleporter(event.getPlayer(), event.getCaught(), plugin.getStepSize(), plugin));
					}
				} else {
					Entity caught = event.getCaught();
					Location playerLoc = event.getPlayer().getLocation();
					double distance = caught.getLocation().toVector().distance(playerLoc.toVector());
					double finalScalar = getScalar(distance);
					Vector launchVector = playerLoc.toVector().clone().subtract(caught.getLocation().toVector()).normalize().multiply(finalScalar);
					//launchVector.setY(Math.abs(launchVector.getY()));
					caught.setVelocity(launchVector);
				}
			} else {
				if (plugin.isGrappleModeTP()) {
					event.getPlayer().setAllowFlight(true);
					Bukkit.getScheduler().runTask(plugin, new PullTeleporter(event.getPlayer(), event.getHook().getLocation(), plugin.getStepSize(), plugin));
				} else {
					Location hookLoc = event.getHook().getLocation();
					Location playerLoc = event.getPlayer().getLocation();
					double distance = hookLoc.toVector().distance(playerLoc.toVector());
					double finalScalar = getScalar(distance);
					Vector launchVector = hookLoc.toVector().clone().subtract(playerLoc.toVector()).normalize().multiply(finalScalar);
					launchVector.setY(Math.abs(launchVector.getY()));
					event.getPlayer().setVelocity(launchVector);
				}
			}
		}
	}
	public static double getScalar(double input) {
		return HOOK_OUTPUT_START + HOOK_SCALAR * (input - HOOK_INPUT_START);
	}
}
