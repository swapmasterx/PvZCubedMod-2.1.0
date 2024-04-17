package io.github.GrassyDev.pvzmod.recipe;

import io.github.GrassyDev.pvzmod.PvZCubed;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModRecipes {
    public static void registerRecipes(){
        Registry.register(Registries.RECIPE_SERIALIZER, new Identifier(PvZCubed.MOD_ID, BotanyStationRecipe.Serializer.ID),
                BotanyStationRecipe.Serializer.INSTANCE);
        Registry.register(Registries.RECIPE_TYPE, new Identifier(PvZCubed.MOD_ID, BotanyStationRecipe.Type.ID),
               BotanyStationRecipe.Type.INSTANCE);
        PvZCubed.LOGGER.info("Registering Recipes for" + PvZCubed.MOD_ID);
    }
}

