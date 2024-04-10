package io.github.GrassyDev.pvzmod.datagen;

import io.github.GrassyDev.pvzmod.block.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.HolderLookup;
import net.minecraft.registry.tag.BlockTags;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends FabricTagProvider.BlockTagProvider {
	public ModBlockTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	protected void configure(HolderLookup.Provider arg) {

		getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE)
			.add(ModBlocks.ROOF_TILE)
			.add(ModBlocks.DARK_ROOF_TILE)
			.add(ModBlocks.UPGRADE_TILE)
			.add(ModBlocks.PREMIUM_TILE)
			.add(ModBlocks.LEGENDARY_TILE)
			.add(ModBlocks.EGYPT_TILE)
			.add(ModBlocks.DARK_EGYPT_TILE)
			.add(ModBlocks.WEST_TILE)
			.add(ModBlocks.DARK_WEST_TILE)
			.add(ModBlocks.FUTURE_TILE)
			.add(ModBlocks.DARK_FUTURE_TILE)
			.add(ModBlocks.DARKAGES_TILE)
			.add(ModBlocks.DARK_DARKAGES_TILE)
			.add(ModBlocks.FROST_TILE)
			.add(ModBlocks.DARK_FROST_TILE)
			.add(ModBlocks.LOSTCITY_TILE)
			.add(ModBlocks.DARK_LOSTCITY_TILE)
			.add(ModBlocks.SKYCITY_TILE)
			.add(ModBlocks.DARK_SKYCITY_TILE)
			.add(ModBlocks.MAUSOLEUM_TILE)
			.add(ModBlocks.DARK_MAUSOLEUM_TILE);

		getOrCreateTagBuilder(BlockTags.AXE_MINEABLE)
			.add(ModBlocks.BOTANY_STATION)
			.add(ModBlocks.PIRATE_TILE)
			.add(ModBlocks.DARK_PIRATE_TILE);

		getOrCreateTagBuilder(BlockTags.HOE_MINEABLE)
			.add(ModBlocks.GRASS_TILE)
			.add(ModBlocks.DARK_GRASS_TILE)
			.add(ModBlocks.FAIRY_TILE)
			.add(ModBlocks.DARK_FAIRY_TILE);

		getOrCreateTagBuilder(BlockTags.SHOVEL_MINEABLE)
			.add(ModBlocks.GRASS_TILE)
			.add(ModBlocks.DARK_GRASS_TILE)
			.add(ModBlocks.NIGHT_TILE)
			.add(ModBlocks.DARK_NIGHT_TILE)
			.add(ModBlocks.FAIRY_TILE)
			.add(ModBlocks.DARK_FAIRY_TILE)
			.add(ModBlocks.SAND_TILE)
			.add(ModBlocks.DARK_SAND_TILE)
			.add(ModBlocks.UNDERWATER_TILE)
			.add(ModBlocks.DARK_UNDERWATER_TILE);

		getOrCreateTagBuilder(BlockTags.NEEDS_STONE_TOOL)
			.add(ModBlocks.BOTANY_STATION)
			.add(ModBlocks.GRASS_TILE)
			.add(ModBlocks.DARK_GRASS_TILE)
			.add(ModBlocks.NIGHT_TILE)
			.add(ModBlocks.DARK_NIGHT_TILE)
			.add(ModBlocks.FAIRY_TILE)
			.add(ModBlocks.DARK_FAIRY_TILE)
			.add(ModBlocks.SAND_TILE)
			.add(ModBlocks.DARK_SAND_TILE)
			.add(ModBlocks.UNDERWATER_TILE)
			.add(ModBlocks.DARK_UNDERWATER_TILE)
			.add(ModBlocks.PIRATE_TILE)
			.add(ModBlocks.DARK_PIRATE_TILE)
			.add(ModBlocks.GRASS_TILE)
			.add(ModBlocks.DARK_GRASS_TILE)
			.add(ModBlocks.NIGHT_TILE)
			.add(ModBlocks.DARK_NIGHT_TILE)
			.add(ModBlocks.FAIRY_TILE)
			.add(ModBlocks.DARK_FAIRY_TILE)
			.add(ModBlocks.ROOF_TILE)
			.add(ModBlocks.DARK_ROOF_TILE)
			.add(ModBlocks.UPGRADE_TILE)
			.add(ModBlocks.PREMIUM_TILE)
			.add(ModBlocks.LEGENDARY_TILE)
			.add(ModBlocks.EGYPT_TILE)
			.add(ModBlocks.DARK_EGYPT_TILE)
			.add(ModBlocks.WEST_TILE)
			.add(ModBlocks.DARK_WEST_TILE)
			.add(ModBlocks.FUTURE_TILE)
			.add(ModBlocks.DARK_FUTURE_TILE)
			.add(ModBlocks.DARKAGES_TILE)
			.add(ModBlocks.DARK_DARKAGES_TILE)
			.add(ModBlocks.FROST_TILE)
			.add(ModBlocks.DARK_FROST_TILE)
			.add(ModBlocks.LOSTCITY_TILE)
			.add(ModBlocks.DARK_LOSTCITY_TILE)
			.add(ModBlocks.SKYCITY_TILE)
			.add(ModBlocks.DARK_SKYCITY_TILE)
			.add(ModBlocks.MAUSOLEUM_TILE)
			.add(ModBlocks.DARK_MAUSOLEUM_TILE);
	}
}
