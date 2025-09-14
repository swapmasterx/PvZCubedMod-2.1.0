package io.github.GrassyDev.pvzmod.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeInput;

public class BotanyStationRecipeInput implements RecipeInput {

	@Override
	public ItemStack get(int slot) {
		return input;
	}
	@Override
	public int getSize() {
		return 7;
	}
}
