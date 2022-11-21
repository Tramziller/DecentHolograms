package eu.decentsoftware.holograms.plugin;

import com.github.puregero.multilib.MultiLib;
import eu.decentsoftware.holograms.api.DecentHolograms;
import eu.decentsoftware.holograms.api.DecentHologramsAPI;
import eu.decentsoftware.holograms.api.Settings;
import eu.decentsoftware.holograms.api.commands.CommandManager;
import eu.decentsoftware.holograms.api.commands.DecentCommand;
import eu.decentsoftware.holograms.plugin.commands.HologramsCommand;
import eu.decentsoftware.holograms.plugin.features.DamageDisplayFeature;
import eu.decentsoftware.holograms.plugin.features.HealingDisplayFeature;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.nio.file.*;
import java.util.concurrent.TimeUnit;

@Getter
public class DecentHologramsPlugin extends JavaPlugin {

	private WatchService service;

	@Override
	public void onLoad() {
		DecentHologramsAPI.onLoad(this);
	}

	@Override
	public void onEnable() {
		DecentHologramsAPI.onEnable();

		DecentHolograms decentHolograms = DecentHologramsAPI.get();
		decentHolograms.getFeatureManager().registerFeature(new DamageDisplayFeature());
		decentHolograms.getFeatureManager().registerFeature(new HealingDisplayFeature());

		CommandManager commandManager = decentHolograms.getCommandManager();
		DecentCommand mainCommand = new HologramsCommand();
		commandManager.setMainCommand(mainCommand);
		commandManager.registerCommand(mainCommand);

		registerMultliLib();
	}

	@Override
	public void onDisable() {
		DecentHologramsAPI.onDisable();
	}

	public void registerMultliLib() {
		MultiLib.onString(this, "eu.decentsoftware.holograms:reload", data -> {
			if(!MultiLib.getLocalServerName().equals(data))
				DecentHologramsAPI.get().reload(true);
		});
	}

}
