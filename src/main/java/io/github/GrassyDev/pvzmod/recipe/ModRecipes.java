package io.github.GrassyDev.pvzmod.recipe;

import io.github.GrassyDev.pvzmod.PvZCubed;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModRecipes {
    public static void registerRecipes(){
		RecipeSerializer<BotanyStationRecipe> BOTANY_BOX_SERIALIZER =
			Registry.register(Registries.RECIPE_SERIALIZER, Identifier.of(PvZCubed.MOD_ID,
				"botany_station"), new BotanyStationRecipe.Serializer());;

		RecipeType<BotanyStationRecipe> BOTANY_BOX_TYPE =
			Registry.register(Registries.RECIPE_TYPE, Identifier.of(PvZCubed.MOD_ID,
				"botany_station"),
				new RecipeType<BotanyStationRecipe>() {
				@Override
				public String toString() {
					return "botany_station";
				}
			});
        PvZCubed.LOGGER.info("Registering Recipes for" + PvZCubed.MOD_ID);
    }
}

