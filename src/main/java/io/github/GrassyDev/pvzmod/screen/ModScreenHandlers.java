package io.github.GrassyDev.pvzmod.screen;

import io.github.GrassyDev.pvzmod.PvZCubed;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ModScreenHandlers {
	public static final ScreenHandlerType<BotanyStationScreenHandler>  BOTANY_STATION_SCREEN_HANDLER =
		Registry.register(Registries.SCREEN_HANDLER_TYPE, new Identifier(PvZCubed.MOD_ID, "botany_station"),
			new ExtendedScreenHandlerType<>(BotanyStationScreenHandler::new));

		public static void registerScreenHandlers() {
			PvZCubed.LOGGER.info("Registering Screen Handlers for" + PvZCubed.MOD_ID);
		}
}
