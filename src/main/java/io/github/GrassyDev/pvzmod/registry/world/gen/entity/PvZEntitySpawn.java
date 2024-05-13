package io.github.GrassyDev.pvzmod.registry.world.gen.entity;

import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.entity.gravestones.basicgrave.BasicGraveEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.day.peashooter.PeashooterEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.solar.sunflowerseed.SunflowerSeedEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz1.browncoat.modernday.BrowncoatEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz1.snorkel.SnorkelEntity;
import io.github.GrassyDev.pvzmod.util.ModTags;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.mob.DrownedEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import org.quiltmc.qsl.worldgen.biome.api.BiomeModifications;
import org.quiltmc.qsl.worldgen.biome.api.BiomeSelectors;

import static io.github.GrassyDev.pvzmod.PvZCubed.PVZCONFIG;

public class PvZEntitySpawn {

	public static void addEntitySpawn(){

		//Natural Day Zombies
		BiomeModifications.addSpawn(BiomeSelectors.isIn(BiomeTags.OVERWORLD), SpawnGroup.MONSTER, PvZEntity.BROWNCOAT, 70, 1, 2);
		SpawnRestriction.register(PvZEntity.BROWNCOAT, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, HostileEntity::canSpawnIgnoreLightLevel);

		BiomeModifications.addSpawn(BiomeSelectors.isIn(BiomeTags.OVERWORLD), SpawnGroup.MONSTER, PvZEntity.CONEHEAD, 50, 1, 2);
		SpawnRestriction.register(PvZEntity.CONEHEAD, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, HostileEntity::canSpawnInDark);

		BiomeModifications.addSpawn(BiomeSelectors.isIn(BiomeTags.OVERWORLD), SpawnGroup.MONSTER, PvZEntity.POLEVAULTING, 50, 1, 1);
		SpawnRestriction.register(PvZEntity.POLEVAULTING, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, HostileEntity::canSpawnInDark);

		BiomeModifications.addSpawn(BiomeSelectors.isIn(BiomeTags.OVERWORLD), SpawnGroup.MONSTER, PvZEntity.BUCKETHEAD, 35, 1, 1);
		SpawnRestriction.register(PvZEntity.BUCKETHEAD, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, HostileEntity::canSpawnInDark);

		BiomeModifications.addSpawn(BiomeSelectors.isIn(BiomeTags.OVERWORLD), SpawnGroup.MONSTER, PvZEntity.BRICKHEAD, 15, 1, 1);
		SpawnRestriction.register(PvZEntity.BRICKHEAD, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, HostileEntity::canSpawnInDark);

		BiomeModifications.addSpawn(BiomeSelectors.isIn(BiomeTags.OVERWORLD), SpawnGroup.MONSTER, PvZEntity.FLAGZOMBIE, 5, 1, 1);
		SpawnRestriction.register(PvZEntity.FLAGZOMBIE, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, HostileEntity::canSpawnInDark);

		//Natural Night Zombies
		BiomeModifications.addSpawn(BiomeSelectors.isIn(BiomeTags.OVERWORLD), SpawnGroup.MONSTER, PvZEntity.FOOTBALL, 8, 1, 1);
		SpawnRestriction.register(PvZEntity.FOOTBALL, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, HostileEntity::canSpawnInDark);

		BiomeModifications.addSpawn(BiomeSelectors.isIn(BiomeTags.OVERWORLD), SpawnGroup.MONSTER, PvZEntity.BASSIMP, 15, 1, 1);
		SpawnRestriction.register(PvZEntity.BASSIMP, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, HostileEntity::canSpawnInDark);

		//Natural Pool Zombies
		BiomeModifications.addSpawn(BiomeSelectors.isIn(BiomeTags.OVERWORLD), SpawnGroup.MONSTER, PvZEntity.SNORKEL, 30, 1, 1);
		SpawnRestriction.register(PvZEntity.SNORKEL, SpawnRestriction.Location.IN_WATER, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, SnorkelEntity::canSpawn);

		//Natural Roof Zombies
		BiomeModifications.addSpawn(BiomeSelectors.isIn(BiomeTags.OVERWORLD), SpawnGroup.MONSTER, PvZEntity.GARGANTUAR, 2, 1, 1);
		SpawnRestriction.register(PvZEntity.GARGANTUAR, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, HostileEntity::canSpawnInDark);

		//Graves
//		BiomeModifications.addSpawn(BiomeSelectors.all(), SpawnGroup.MONSTER, PvZEntity.BASICGRAVESTONE, PVZCONFIG.nestedSpawns.nestedGraveSpawns.basicGv2(), PVZCONFIG.nestedSpawns.nestedGraveSpawns.basicGmin(), PVZCONFIG.nestedSpawns.nestedGraveSpawns.basicGmax());
//		SpawnRestriction.register(PvZEntity.BASICGRAVESTONE, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, BasicGraveEntity::canBasicGraveSpawn);
//
//		BiomeModifications.addSpawn(BiomeSelectors.all(), SpawnGroup.MONSTER, PvZEntity.NIGHTGRAVESTONE, PVZCONFIG.nestedSpawns.nestedGraveSpawns.nightGv2(), PVZCONFIG.nestedSpawns.nestedGraveSpawns.nightGmin(), PVZCONFIG.nestedSpawns.nestedGraveSpawns.nightGmax());
//		SpawnRestriction.register(PvZEntity.NIGHTGRAVESTONE, SpawnRestriction.Location.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, NightGraveEntity::canNightGraveSpawn);
//
//		BiomeModifications.addSpawn(BiomeSelectors.all(), SpawnGroup.MONSTER, PvZEntity.POOLGRAVESTONE, PVZCONFIG.nestedSpawns.nestedGraveSpawns.poolGv2(), PVZCONFIG.nestedSpawns.nestedGraveSpawns.poolGmin(), PVZCONFIG.nestedSpawns.nestedGraveSpawns.poolGmax());
//		SpawnRestriction.register(PvZEntity.POOLGRAVESTONE, SpawnRestriction.Location.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, PoolGraveEntity::canPoolGraveSpawn);
//
//		BiomeModifications.addSpawn(BiomeSelectors.all(), SpawnGroup.MONSTER, PvZEntity.EGYPTGRAVESTONE, PVZCONFIG.nestedSpawns.nestedGraveSpawns.egyptG(), PVZCONFIG.nestedSpawns.nestedGraveSpawns.egyptGmin(), PVZCONFIG.nestedSpawns.nestedGraveSpawns.egyptGmax());
//		SpawnRestriction.register(PvZEntity.EGYPTGRAVESTONE, SpawnRestriction.Location.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, EgyptGraveEntity::canEgyptGraveSpawn);
//
//		BiomeModifications.addSpawn(BiomeSelectors.all(), SpawnGroup.MONSTER, PvZEntity.ROOFGRAVESTONE, PVZCONFIG.nestedSpawns.nestedGraveSpawns.roofGv2(), PVZCONFIG.nestedSpawns.nestedGraveSpawns.roofGmin(), PVZCONFIG.nestedSpawns.nestedGraveSpawns.roofGmax());
//		SpawnRestriction.register(PvZEntity.ROOFGRAVESTONE, SpawnRestriction.Location.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, RoofGraveEntity::canRoofGraveSpawn);
//
//		BiomeModifications.addSpawn(BiomeSelectors.all(), SpawnGroup.MONSTER, PvZEntity.FUTUREGRAVESTONE, PVZCONFIG.nestedSpawns.nestedGraveSpawns.futureGv2(), PVZCONFIG.nestedSpawns.nestedGraveSpawns.futureGmin(), PVZCONFIG.nestedSpawns.nestedGraveSpawns.futureGmax());
//		SpawnRestriction.register(PvZEntity.FUTUREGRAVESTONE, SpawnRestriction.Location.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, FutureGraveEntity::canFutureGraveSpawn);
//
//		BiomeModifications.addSpawn(BiomeSelectors.all(), SpawnGroup.MONSTER, PvZEntity.DARKAGESGRAVESTONE, PVZCONFIG.nestedSpawns.nestedGraveSpawns.darkagesGv2(), PVZCONFIG.nestedSpawns.nestedGraveSpawns.darkagesGmin(), PVZCONFIG.nestedSpawns.nestedGraveSpawns.darkagesGmax());
//		SpawnRestriction.register(PvZEntity.DARKAGESGRAVESTONE, SpawnRestriction.Location.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DarkAgesGraveEntity::canDarkAgesGraveSpawn);
//
//		BiomeModifications.addSpawn(BiomeSelectors.all(), SpawnGroup.MONSTER, PvZEntity.MAUSOLEUMGRAVESTONE, PVZCONFIG.nestedSpawns.nestedGraveSpawns.mausoleumG(), PVZCONFIG.nestedSpawns.nestedGraveSpawns.mausoleumGmin(), PVZCONFIG.nestedSpawns.nestedGraveSpawns.mausoleumGmax());
//		SpawnRestriction.register(PvZEntity.MAUSOLEUMGRAVESTONE, SpawnRestriction.Location.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MausoleumGraveEntity::canMausolemGraveSpawn);


		//Plants

		BiomeModifications.addSpawn(BiomeSelectors.isIn(BiomeTags.OVERWORLD), SpawnGroup.CREATURE, PvZEntity.SUNFLOWERSEED, 17, 1, 1);
		SpawnRestriction.register(PvZEntity.SUNFLOWERSEED, SpawnRestriction.Location.NO_RESTRICTIONS,  Heightmap.Type.MOTION_BLOCKING, SunflowerSeedEntity::canSunflowerSeedSpawn);

//		BiomeModifications.addSpawn(BiomeSelectors.all(), SpawnGroup.MONSTER, PvZEntity.BELLFLOWER, PVZCONFIG.nestedSpawns.nestedPlantSpawns.bellflowerSP(), PVZCONFIG.nestedSpawns.nestedPlantSpawns.bellflowerSPmin(), PVZCONFIG.nestedSpawns.nestedPlantSpawns.bellflowerSPmax());
//		SpawnRestriction.register(PvZEntity.BELLFLOWER, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, BellflowerEntity::canBellflowerSpawn);
//
//		BiomeModifications.addSpawn(BiomeSelectors.all(), SpawnGroup.MONSTER, PvZEntity.PUFFSHROOM, PVZCONFIG.nestedSpawns.nestedPlantSpawns.puffshroomSP(), PVZCONFIG.nestedSpawns.nestedPlantSpawns.puffshroomSPmin(), PVZCONFIG.nestedSpawns.nestedPlantSpawns.puffshroomSPmax());
//		SpawnRestriction.register(PvZEntity.PUFFSHROOM, SpawnRestriction.Location.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, PuffshroomEntity::canPuffshroomSpawn);
//
//		BiomeModifications.addSpawn(BiomeSelectors.all(), SpawnGroup.MONSTER, PvZEntity.SHADOWSHROOM, PVZCONFIG.nestedSpawns.nestedPlantSpawns.shadowshroomSP(), PVZCONFIG.nestedSpawns.nestedPlantSpawns.shadowshroomSPmin(), PVZCONFIG.nestedSpawns.nestedPlantSpawns.shadowshroomSPmax());
//		SpawnRestriction.register(PvZEntity.SHADOWSHROOM, SpawnRestriction.Location.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, ShadowShroomEntity::canShadowShroomSpawn);
//
//		BiomeModifications.addSpawn(BiomeSelectors.all(), SpawnGroup.MONSTER, PvZEntity.SUNFLOWERSEED, PVZCONFIG.nestedSpawns.nestedPlantSpawns.sunflowerseedSPv2(), PVZCONFIG.nestedSpawns.nestedPlantSpawns.sunflowerseedSPmin(), PVZCONFIG.nestedSpawns.nestedPlantSpawns.sunflowerseedSPmax());
//		SpawnRestriction.register(PvZEntity.SUNFLOWERSEED, SpawnRestriction.Location.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, SunflowerSeedEntity::canSunflowerSeedSpawn);
//
//		BiomeModifications.addSpawn(BiomeSelectors.all(), SpawnGroup.MONSTER, PvZEntity.LILYPAD, PVZCONFIG.nestedSpawns.nestedPlantSpawns.lilypadSP(), PVZCONFIG.nestedSpawns.nestedPlantSpawns.lilypadSPmin(), PVZCONFIG.nestedSpawns.nestedPlantSpawns.lilypadSPmax());
//		SpawnRestriction.register(PvZEntity.LILYPAD, SpawnRestriction.Location.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, LilyPadEntity::canLilyPadSpawn);
//
//		BiomeModifications.addSpawn(BiomeSelectors.all(), SpawnGroup.MONSTER, PvZEntity.OILYOLIVE, PVZCONFIG.nestedSpawns.nestedPlantSpawns.bombseedlingSP(), PVZCONFIG.nestedSpawns.nestedPlantSpawns.bombseedlingSPmin(), PVZCONFIG.nestedSpawns.nestedPlantSpawns.bombseedlingSPmax());
//		SpawnRestriction.register(PvZEntity.OILYOLIVE, SpawnRestriction.Location.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, OilyOliveEntity::canOilyOliveSpawn);
//
//		BiomeModifications.addSpawn(BiomeSelectors.all(), SpawnGroup.MONSTER, PvZEntity.ICEBERGLETTUCE, PVZCONFIG.nestedSpawns.nestedPlantSpawns.icebergSP(), PVZCONFIG.nestedSpawns.nestedPlantSpawns.icebergSPmin(), PVZCONFIG.nestedSpawns.nestedPlantSpawns.icebergSPmax());
//		SpawnRestriction.register(PvZEntity.ICEBERGLETTUCE, SpawnRestriction.Location.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, IcebergLettuceEntity::canIcebergLettuceSpawn);
//
//		BiomeModifications.addSpawn(BiomeSelectors.all(), SpawnGroup.MONSTER, PvZEntity.ZAPRICOT, PVZCONFIG.nestedSpawns.nestedPlantSpawns.zapricotSP(), PVZCONFIG.nestedSpawns.nestedPlantSpawns.zapricotSPmin(), PVZCONFIG.nestedSpawns.nestedPlantSpawns.zapricotSPmax());
//		SpawnRestriction.register(PvZEntity.ZAPRICOT, SpawnRestriction.Location.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, ZapricotEntity::canZapricotSpawn);
//
//		BiomeModifications.addSpawn(BiomeSelectors.all(), SpawnGroup.CREATURE, PvZEntity.SOLARWINDS, PVZCONFIG.nestedSpawns.nestedPlantSpawns.solarwindSP(), PVZCONFIG.nestedSpawns.nestedPlantSpawns.solarwindSPmin(), PVZCONFIG.nestedSpawns.nestedPlantSpawns.solarwindSPmax());
//		SpawnRestriction.register(PvZEntity.SOLARWINDS, SpawnRestriction.Location.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, SolarWinds::canSolarWindsSpawn);


		//BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.FOREST), SpawnGroup.MONSTER, PvZEntity.NIGHTGRAVESTONE, 100, 1, 6);

	}
}
