package io.github.GrassyDev.pvzmod.util;

import io.github.GrassyDev.pvzmod.PvZCubed;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;

public class ModTags {
	public static class Blocks {

		private static TagKey<Block> createTag(String name) {
			return TagKey.of(RegistryKeys.BLOCK, new Identifier(PvZCubed.MOD_ID, name));
		}

	}

	public static class Items {

		private static TagKey<Item> createTag(String name) {
			return TagKey.of(RegistryKeys.ITEM, new Identifier(PvZCubed.MOD_ID, name));
		}
	}

	public static class Biomes {
		public static final TagKey<Biome> FLOWERING = TagKey.of(RegistryKeys.BIOME, new Identifier(PvZCubed.MOD_ID, "is_flowerable"));

	}
}
