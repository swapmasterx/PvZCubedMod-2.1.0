package io.github.GrassyDev.pvzmod.compat;

import io.github.GrassyDev.pvzmod.recipe.BotanyStationRecipe;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.recipe.RecipeHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BotanyStationDisplay extends BasicDisplay {


	public BotanyStationDisplay(List<EntryIngredient> inputs, List<EntryIngredient> outputs) {
		super(inputs, outputs);
	}

	public BotanyStationDisplay(RecipeHolder<BotanyStationRecipe> recipe){
		super(getInputList(recipe.value()), List.of(EntryIngredient
			.of(EntryStacks.of(recipe.value().getResult(null)))));

	}
	private static List<EntryIngredient> getInputList(BotanyStationRecipe recipe) {
		if(recipe == null) return Collections.emptyList();
		List<EntryIngredient> list = new ArrayList<>();
		list.add(EntryIngredients.ofIngredient(recipe.getIngredients().get(0)));
		list.add(EntryIngredients.ofIngredient(recipe.getIngredients().get(1)));
		list.add(EntryIngredients.ofIngredient(recipe.getIngredients().get(2)));
		list.add(EntryIngredients.ofIngredient(recipe.getIngredients().get(3)));
		list.add(EntryIngredients.ofIngredient(recipe.getIngredients().get(4)));
		list.add(EntryIngredients.ofIngredient(recipe.getIngredients().get(5)));
		list.add(EntryIngredients.ofIngredient(recipe.getIngredients().get(6)));
		list.add(EntryIngredients.ofIngredient(recipe.getIngredients().get(7)));
		return list;
	}
	@Override
	public CategoryIdentifier<?> getCategoryIdentifier() {
		return BotanyStationCatagory.BOTANY_STATION;
	}
}
