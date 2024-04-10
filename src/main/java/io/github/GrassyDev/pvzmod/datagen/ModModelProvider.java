package io.github.GrassyDev.pvzmod.datagen;

import io.github.GrassyDev.pvzmod.block.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.model.BlockStateModelGenerator;

public class ModModelProvider extends FabricModelProvider {
	public ModModelProvider(FabricDataOutput output) {

		super(output);
	}
	@Override
	public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
		blockStateModelGenerator.registerSimpleState(ModBlocks.BOTANY_STATION);
	}

	@Override
	public void generateItemModels(ItemModelGenerator itemModelGenerator) {

	}
}
