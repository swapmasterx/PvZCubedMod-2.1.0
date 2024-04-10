package io.github.GrassyDev.pvzmod.block;

import io.github.GrassyDev.pvzmod.PvZCubed;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.registry.Registries;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.registry.Registry;
import org.quiltmc.qsl.block.extensions.api.QuiltBlockSettings;

public class ModBlocks {

	public static final Block BOTANY_STATION = new BotanyStation(QuiltBlockSettings.create()
			.mapColor(MapColor.GREEN).pistonBehavior(PistonBehavior.BLOCK)
			.requiresTool()
			.nonOpaque()
			.sounds(BlockSoundGroup.CHERRY_WOOD)
			.strength(1.9f, 1000f));
    public static final Block GRASS_TILE = new Block(QuiltBlockSettings.create()
		.mapColor(MapColor.GRASS).pistonBehavior(PistonBehavior.NORMAL)
		.requiresTool()
		.sounds(BlockSoundGroup.GRASS)
		.strength(1.9f, 1000f));

    public static final Block DARK_GRASS_TILE = new Block(QuiltBlockSettings.create()
		.mapColor(MapColor.GRASS).pistonBehavior(PistonBehavior.NORMAL)
			.requiresTool()
            .sounds(BlockSoundGroup.GRASS)
            .strength(1.9f, 1000f));

	public static final Block NIGHT_TILE = new Block(QuiltBlockSettings.create()
		.mapColor(MapColor.BLUE).pistonBehavior(PistonBehavior.NORMAL)
			.requiresTool()
			.sounds(BlockSoundGroup.GRASS)
			.strength(1.9f, 1000f));

	public static final Block DARK_NIGHT_TILE = new Block(QuiltBlockSettings.create()
		.mapColor(MapColor.BLUE).pistonBehavior(PistonBehavior.NORMAL)
			.requiresTool()
			.sounds(BlockSoundGroup.GRASS)
			.strength(1.9f, 1000f));

	public static final Block ROOF_TILE = new Block(QuiltBlockSettings.create()
		.mapColor(MapColor.BROWN_TERRACOTTA).pistonBehavior(PistonBehavior.NORMAL)
			.requiresTool()
			.sounds(BlockSoundGroup.NETHER_BRICKS)
			.strength(1.9f, 1000f));

	public static final Block DARK_ROOF_TILE = new Block(QuiltBlockSettings.create()
		.mapColor(MapColor.BROWN_TERRACOTTA).pistonBehavior(PistonBehavior.NORMAL)
			.requiresTool()
			.sounds(BlockSoundGroup.NETHER_BRICKS)
			.strength(1.9f, 1000f));


	public static final Block UPGRADE_TILE = new Block(QuiltBlockSettings.create()
		.mapColor(MapColor.DIAMOND).pistonBehavior(PistonBehavior.BLOCK)
			.requiresTool()
			.sounds(BlockSoundGroup.AMETHYST_BLOCK)
			.strength(1.9f, 1000f)
			.luminance(3));


	public static final Block PREMIUM_TILE = new Block(QuiltBlockSettings.create()
			.mapColor(MapColor.DIAMOND).pistonBehavior(PistonBehavior.BLOCK)
			.requiresTool()
			.sounds(BlockSoundGroup.AMETHYST_BLOCK)
			.strength(1.9f, 1000f)
			.luminance(3));

	public static final Block EGYPT_TILE = new Block(QuiltBlockSettings.create()
		.mapColor(MapColor.SAND).pistonBehavior(PistonBehavior.NORMAL)
			.requiresTool()
			.sounds(BlockSoundGroup.STONE)
			.strength(1.9f, 1000f));


	public static final Block DARK_EGYPT_TILE = new Block(QuiltBlockSettings.create()
		.mapColor(MapColor.SAND).pistonBehavior(PistonBehavior.NORMAL)
			.requiresTool()
			.sounds(BlockSoundGroup.STONE)
			.strength(1.9f, 1000f));

	public static final Block PIRATE_TILE = new Block(QuiltBlockSettings.create()
		.mapColor(MapColor.BROWN).pistonBehavior(PistonBehavior.NORMAL)
			.requiresTool()
			.sounds(BlockSoundGroup.WOOD)
			.strength(1.9f, 1000f));


	public static final Block DARK_PIRATE_TILE = new Block(QuiltBlockSettings.create()
		.mapColor(MapColor.BROWN).pistonBehavior(PistonBehavior.NORMAL)
			.requiresTool()
			.sounds(BlockSoundGroup.WOOD)
			.strength(1.9f, 1000f));

	public static final Block WEST_TILE = new Block(QuiltBlockSettings.create()
		.mapColor(MapColor.DIRT).pistonBehavior(PistonBehavior.NORMAL)
			.requiresTool()
			.sounds(BlockSoundGroup.STONE)
			.strength(1.9f, 1000f));


	public static final Block DARK_WEST_TILE = new Block(QuiltBlockSettings.create()
		.mapColor(MapColor.DIRT).pistonBehavior(PistonBehavior.NORMAL)
			.requiresTool()
			.sounds(BlockSoundGroup.STONE)
			.strength(1.9f, 1000f));


	public static final Block FUTURE_TILE = new Block(QuiltBlockSettings.create()
		.mapColor(MapColor.CYAN).pistonBehavior(PistonBehavior.NORMAL)
			.requiresTool()
			.sounds(BlockSoundGroup.AMETHYST_BLOCK)
			.strength(1.9f, 1000f)
			.luminance(3));


	public static final Block DARK_FUTURE_TILE = new Block(QuiltBlockSettings.create()
		.mapColor(MapColor.CYAN).pistonBehavior(PistonBehavior.NORMAL)
			.requiresTool()
			.sounds(BlockSoundGroup.AMETHYST_BLOCK)
			.strength(1.9f, 1000f)
			.luminance(3));

	public static final Block DARKAGES_TILE = new Block(QuiltBlockSettings.create()
		.mapColor(MapColor.DEEPSLATE).pistonBehavior(PistonBehavior.NORMAL)
			.requiresTool()
			.sounds(BlockSoundGroup.STONE)
			.strength(1.9f, 1000f));

	public static final Block DARK_DARKAGES_TILE = new Block(QuiltBlockSettings.create()
		.mapColor(MapColor.DEEPSLATE).pistonBehavior(PistonBehavior.NORMAL)
			.requiresTool()
			.sounds(BlockSoundGroup.STONE)
			.strength(1.9f, 1000f));

	public static final Block SAND_TILE = new Block(QuiltBlockSettings.create()
		.mapColor(MapColor.SAND).pistonBehavior(PistonBehavior.NORMAL)
			.requiresTool()
			.sounds(BlockSoundGroup.SAND)
			.strength(1.9f, 1000f));

	public static final Block DARK_SAND_TILE = new Block(QuiltBlockSettings.create()
		.mapColor(MapColor.SAND).pistonBehavior(PistonBehavior.NORMAL)
			.requiresTool()
			.sounds(BlockSoundGroup.SAND)
			.strength(1.9f, 1000f));

	public static final Block UNDERWATER_TILE = new Block(QuiltBlockSettings.create()
		.mapColor(MapColor.WATER).pistonBehavior(PistonBehavior.NORMAL)
			.requiresTool()
			.sounds(BlockSoundGroup.SAND)
			.strength(1.9f, 1000f));

	public static final Block DARK_UNDERWATER_TILE = new Block(QuiltBlockSettings.create()
		.mapColor(MapColor.WATER).pistonBehavior(PistonBehavior.NORMAL)
			.requiresTool()
			.sounds(BlockSoundGroup.SAND)
			.strength(1.9f, 1000f));


	public static final Block FROST_TILE = new Block(QuiltBlockSettings.create()
		.mapColor(MapColor.ICE).pistonBehavior(PistonBehavior.NORMAL)
			.requiresTool()
			.sounds(BlockSoundGroup.GLASS)
			.strength(1.9f, 1000f));


	public static final Block DARK_FROST_TILE = new Block(QuiltBlockSettings.create()
		.mapColor(MapColor.ICE).pistonBehavior(PistonBehavior.NORMAL)
			.requiresTool()
			.sounds(BlockSoundGroup.GLASS)
			.strength(1.9f, 1000f));

	public static final Block LOSTCITY_TILE = new Block(QuiltBlockSettings.create()
		.mapColor(MapColor.GREEN_TERRACOTTA).pistonBehavior(PistonBehavior.NORMAL)
			.requiresTool()
			.sounds(BlockSoundGroup.STONE)
			.strength(1.9f, 1000f));

	public static final Block DARK_LOSTCITY_TILE = new Block(QuiltBlockSettings.create()
		.mapColor(MapColor.GREEN_TERRACOTTA).pistonBehavior(PistonBehavior.NORMAL)
			.requiresTool()
			.sounds(BlockSoundGroup.STONE)
			.strength(1.9f, 1000f));

	public static final Block SKYCITY_TILE = new Block(QuiltBlockSettings.create()
		.mapColor(MapColor.GRAY).pistonBehavior(PistonBehavior.NORMAL)
			.requiresTool()
			.sounds(BlockSoundGroup.METAL)
			.strength(1.9f, 1000f));

	public static final Block DARK_SKYCITY_TILE = new Block(QuiltBlockSettings.create()
		.mapColor(MapColor.GRAY).pistonBehavior(PistonBehavior.NORMAL)
			.requiresTool()
			.sounds(BlockSoundGroup.METAL)
			.strength(1.9f, 1000f));

	public static final Block FAIRY_TILE = new Block(QuiltBlockSettings.create()
		.mapColor(MapColor.GREEN).pistonBehavior(PistonBehavior.NORMAL)
			.requiresTool()
			.sounds(BlockSoundGroup.GRASS)
			.strength(1.9f, 1000f));

	public static final Block DARK_FAIRY_TILE = new Block(QuiltBlockSettings.create()
		.mapColor(MapColor.GREEN).pistonBehavior(PistonBehavior.NORMAL)
			.requiresTool()
			.sounds(BlockSoundGroup.GRASS)
			.strength(1.9f, 1000f));

	public static final Block MAUSOLEUM_TILE = new Block(QuiltBlockSettings.create()
		.mapColor(MapColor.DEEPSLATE).pistonBehavior(PistonBehavior.NORMAL)
			.requiresTool()
			.sounds(BlockSoundGroup.STONE)
			.strength(1.9f, 1000f));


	public static final Block DARK_MAUSOLEUM_TILE = new Block(QuiltBlockSettings.create()
		.mapColor(MapColor.DEEPSLATE).pistonBehavior(PistonBehavior.NORMAL)
			.requiresTool()
			.sounds(BlockSoundGroup.STONE)
			.strength(1.9f, 1000f));

	public static final Block LEGENDARY_TILE = new Block(QuiltBlockSettings.create()
		.mapColor(MapColor.GOLD).pistonBehavior(PistonBehavior.NORMAL)
			.requiresTool()
			.sounds(BlockSoundGroup.AMETHYST_BLOCK)
			.strength(1.9f, 1000f)
			.luminance(3));



	public static void registerBlocks(){

		Registry.register(Registries.BLOCK, new Identifier(PvZCubed.MOD_ID,"botany_station"), BOTANY_STATION);
        Registry.register(Registries.BLOCK, new Identifier(PvZCubed.MOD_ID,"grass_tile"), GRASS_TILE);
        Registry.register(Registries.BLOCK, new Identifier(PvZCubed.MOD_ID,"dark_grass_tile"), DARK_GRASS_TILE);
		Registry.register(Registries.BLOCK, new Identifier(PvZCubed.MOD_ID,"night_tile"), NIGHT_TILE);
		Registry.register(Registries.BLOCK, new Identifier(PvZCubed.MOD_ID,"dark_night_tile"), DARK_NIGHT_TILE);
		Registry.register(Registries.BLOCK, new Identifier(PvZCubed.MOD_ID,"roof_tile"), ROOF_TILE);
		Registry.register(Registries.BLOCK, new Identifier(PvZCubed.MOD_ID,"dark_roof_tile"), DARK_ROOF_TILE);
		Registry.register(Registries.BLOCK, new Identifier(PvZCubed.MOD_ID,"upgrade_tile"), UPGRADE_TILE);
		Registry.register(Registries.BLOCK, new Identifier(PvZCubed.MOD_ID,"premium_tile"), PREMIUM_TILE);
		Registry.register(Registries.BLOCK, new Identifier(PvZCubed.MOD_ID,"egypt_tile"), EGYPT_TILE);
		Registry.register(Registries.BLOCK, new Identifier(PvZCubed.MOD_ID,"dark_egypt_tile"), DARK_EGYPT_TILE);
		Registry.register(Registries.BLOCK, new Identifier(PvZCubed.MOD_ID,"pirate_tile"), PIRATE_TILE);
		Registry.register(Registries.BLOCK, new Identifier(PvZCubed.MOD_ID,"dark_pirate_tile"), DARK_PIRATE_TILE);
		Registry.register(Registries.BLOCK, new Identifier(PvZCubed.MOD_ID,"west_tile"), WEST_TILE);
		Registry.register(Registries.BLOCK, new Identifier(PvZCubed.MOD_ID,"dark_west_tile"), DARK_WEST_TILE);
		Registry.register(Registries.BLOCK, new Identifier(PvZCubed.MOD_ID,"future_tile"), FUTURE_TILE);
		Registry.register(Registries.BLOCK, new Identifier(PvZCubed.MOD_ID,"dark_future_tile"), DARK_FUTURE_TILE);
		Registry.register(Registries.BLOCK, new Identifier(PvZCubed.MOD_ID,"darkages_tile"), DARKAGES_TILE);
		Registry.register(Registries.BLOCK, new Identifier(PvZCubed.MOD_ID,"dark_darkages_tile"), DARK_DARKAGES_TILE);
		Registry.register(Registries.BLOCK, new Identifier(PvZCubed.MOD_ID,"sand_tile"), SAND_TILE);
		Registry.register(Registries.BLOCK, new Identifier(PvZCubed.MOD_ID,"dark_sand_tile"), DARK_SAND_TILE);
		Registry.register(Registries.BLOCK, new Identifier(PvZCubed.MOD_ID,"underwater_tile"), UNDERWATER_TILE);
		Registry.register(Registries.BLOCK, new Identifier(PvZCubed.MOD_ID,"dark_underwater_tile"), DARK_UNDERWATER_TILE);
		Registry.register(Registries.BLOCK, new Identifier(PvZCubed.MOD_ID,"frost_tile"), FROST_TILE);
		Registry.register(Registries.BLOCK, new Identifier(PvZCubed.MOD_ID,"dark_frost_tile"), DARK_FROST_TILE);
		Registry.register(Registries.BLOCK, new Identifier(PvZCubed.MOD_ID,"lostcity_tile"), LOSTCITY_TILE);
		Registry.register(Registries.BLOCK, new Identifier(PvZCubed.MOD_ID,"dark_lostcity_tile"), DARK_LOSTCITY_TILE);
		Registry.register(Registries.BLOCK, new Identifier(PvZCubed.MOD_ID,"skycity_tile"), SKYCITY_TILE);
		Registry.register(Registries.BLOCK, new Identifier(PvZCubed.MOD_ID,"dark_skycity_tile"), DARK_SKYCITY_TILE);
		Registry.register(Registries.BLOCK, new Identifier(PvZCubed.MOD_ID,"fairy_tile"), FAIRY_TILE);
		Registry.register(Registries.BLOCK, new Identifier(PvZCubed.MOD_ID,"dark_fairy_tile"), DARK_FAIRY_TILE);
		Registry.register(Registries.BLOCK, new Identifier(PvZCubed.MOD_ID,"mausoleum_tile"), MAUSOLEUM_TILE);
		Registry.register(Registries.BLOCK, new Identifier(PvZCubed.MOD_ID,"dark_mausoleum_tile"), DARK_MAUSOLEUM_TILE);
		Registry.register(Registries.BLOCK, new Identifier(PvZCubed.MOD_ID,"legendary_tile"), LEGENDARY_TILE);

    }

}
