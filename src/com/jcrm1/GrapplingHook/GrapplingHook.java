package com.jcrm1.GrapplingHook;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class GrapplingHook extends JavaPlugin {
	private static final String CONFIG_STEP_SIZE = "step-size";
	private static final String CONFIG_ALLOW_PULL_ENTITY = "drag-entity-to-player";
	private static final String CONFIG_GRAPPLE_TP = "grapple-mode-pull-tp";
	private FileConfiguration config = this.getConfig();
	private double stepSize;
	private boolean allowPullEntity;
	private boolean grappleModeTP;
	@Override
	public void onEnable() {
		// CONFIG
		config.addDefault(CONFIG_STEP_SIZE, 0.5D);
		config.addDefault(CONFIG_ALLOW_PULL_ENTITY, false);
		config.addDefault(CONFIG_GRAPPLE_TP, true);
    	config.options().copyDefaults(true);
    	saveConfig();
    	this.stepSize = config.getDouble(CONFIG_STEP_SIZE);
    	this.allowPullEntity = config.getBoolean(CONFIG_ALLOW_PULL_ENTITY);
    	this.grappleModeTP = config.getBoolean(CONFIG_GRAPPLE_TP);
    	
    	// EVENTS
		getServer().getPluginManager().registerEvents(new SubscribePlayerFishEvent(this), this);
		
		// COMMANDS
		this.getCommand("givehook").setExecutor(new CommandGiveHook());
	}
	
	public double getStepSize() {
		return stepSize;
	}

	public boolean isAllowPullEntity() {
		return allowPullEntity;
	}
	
	public boolean isGrappleModeTP() {
		return grappleModeTP;
	}
	
	public void onDisable() {
		
	}
}
