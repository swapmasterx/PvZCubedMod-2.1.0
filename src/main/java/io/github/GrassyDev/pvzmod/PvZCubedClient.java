package io.github.GrassyDev.pvzmod;

import io.github.GrassyDev.pvzmod.screen.BotanyStationScreen;
import io.github.GrassyDev.pvzmod.screen.ModScreenHandlers;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;

public class PvZCubedClient implements ClientModInitializer {

	@Override
	public void onInitializeClient(ModContainer mod) {
		HandledScreens.register(ModScreenHandlers.BOTANY_STATION_SCREEN_HANDLER, BotanyStationScreen::new);
	}
}
