package io.github.GrassyDev.pvzmod.compat;

import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.client.registry.screen.ScreenRegistry;

public class PvZModJEIClientPlugin implements REIClientPlugin {

	@Override
	public void registerCategories(CategoryRegistry registry) {
		REIClientPlugin.super.registerCategories(registry);
	}

	@Override
	public void registerDisplays(DisplayRegistry registry) {
		REIClientPlugin.super.registerDisplays(registry);
	}

	@Override
	public void registerScreens(ScreenRegistry registry) {
		REIClientPlugin.super.registerScreens(registry);
	}
}
