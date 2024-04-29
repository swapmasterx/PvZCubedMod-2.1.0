package io.github.GrassyDev.pvzmod.compat;

import io.github.GrassyDev.pvzmod.block.ModBlocks;
import io.github.GrassyDev.pvzmod.recipe.BotanyStationRecipe;
import io.github.GrassyDev.pvzmod.screen.BotanyStationScreen;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.client.registry.screen.ScreenRegistry;
import me.shedaniel.rei.api.common.util.EntryStacks;

public class PvZModJEIClientPlugin implements REIClientPlugin {

	@Override
	public void registerCategories(CategoryRegistry registry) {

		registry.add(new BotanyStationCatagory());

		registry.addWorkstations(BotanyStationCatagory.BOTANY_STATION, EntryStacks.of(ModBlocks.BOTANY_STATION));
	}

	@Override
	public void registerDisplays(DisplayRegistry registry) {

		registry.registerRecipeFiller(BotanyStationRecipe.class, BotanyStationRecipe.Type.INSTANCE,
			BotanyStationDisplay::new);
	}

	@Override
	public void registerScreens(ScreenRegistry registry) {
		registry.registerClickArea(screen -> new Rectangle(75, 30, 20, 30),
			BotanyStationScreen.class, BotanyStationCatagory.BOTANY_STATION);
	}
}
