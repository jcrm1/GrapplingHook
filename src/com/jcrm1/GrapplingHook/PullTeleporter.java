package com.jcrm1.GrapplingHook;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class PullTeleporter implements Runnable {
	private Entity sourceEntity;
	private Entity targetEnt;
	private Location targetLoc;
	private double step;
	private boolean entityBased;
	private JavaPlugin pl;
	public PullTeleporter(Entity entity, Location target,  double stepSize, JavaPlugin plugin) {
		sourceEntity = entity;
		targetLoc = target;
		step = stepSize;
		entityBased = false;
		pl = plugin;
	}
	public PullTeleporter(Entity entity, Entity target, double stepSize, JavaPlugin plugin) {
		sourceEntity = entity;
		step = stepSize;
		targetEnt = target;
		entityBased = true;
		pl = plugin;
	}
	private void activate(Location targetLocation) {
		if (sourceEntity.getLocation().distance(targetLocation) > step) {
			Location toTP = new Location(targetLocation.getWorld(), sourceEntity.getLocation().getX(), sourceEntity.getLocation().getY(), sourceEntity.getLocation().getZ());
			toTP.subtract(targetLocation);
			Vector vector = toTP.clone().toVector().normalize().multiply(step).multiply(-1.0);
			sourceEntity.teleport(sourceEntity.getLocation().clone().add(vector));
			Bukkit.getScheduler().runTaskLater(pl, new PullTeleporter(sourceEntity, targetLocation, step, pl), 1L);
		} else {
			if (sourceEntity instanceof Player) {
				Player player = (Player) sourceEntity;
				player.setAllowFlight(false);
			}
		}
	}
	private void activate(Entity targetEntity) {
		if (sourceEntity.getLocation().distance(targetEntity.getLocation()) > step) {
			Location toTP = new Location(sourceEntity.getLocation().getWorld(), sourceEntity.getLocation().getX(), sourceEntity.getLocation().getY(), sourceEntity.getLocation().getZ());
			toTP.subtract(targetEntity.getLocation());
			Vector vector = toTP.clone().toVector().normalize().multiply(step).multiply(-1.0);
			sourceEntity.teleport(sourceEntity.getLocation().clone().add(vector));
			Bukkit.getScheduler().runTaskLater(pl, new PullTeleporter(sourceEntity, targetEntity, step, pl), 1L);
		} else {
			if (sourceEntity instanceof Player) {
				Player player = (Player) sourceEntity;
				player.setAllowFlight(false);
			}
		}
	}
	@Override
	public void run() {
		if (entityBased == true) {
			this.activate(targetEnt);
		} else {
			this.activate(targetLoc);
		}
	}
}
