package io.github.GrassyDev.pvzmod;

import io.github.GrassyDev.pvzmod.block.entity.ModBlockEntities;
import io.github.GrassyDev.pvzmod.config.PvZConfig;
import io.github.GrassyDev.pvzmod.block.ModBlocks;
import io.github.GrassyDev.pvzmod.config.ModItems;
import io.github.GrassyDev.pvzmod.recipe.ModRecipes;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.screen.ModScreenHandlers;
import io.github.GrassyDev.pvzmod.sound.PvZSounds;
import io.github.GrassyDev.pvzmod.registry.entity.statuseffects.*;
import io.github.GrassyDev.pvzmod.registry.world.gen.entity.PvZEntitySpawn;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.registry.Registry;
import net.minecraft.world.GameRules;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.quiltmc.qsl.registry.attachment.api.RegistryEntryAttachment;
import software.bernie.geckolib.GeckoLib;

public class PvZCubed implements ModInitializer {

	public static final PvZConfig PVZCONFIG = PvZConfig.createAndLoad();
	// This logger is used to write text to the console and the log file.
	public static final Logger LOGGER = LoggerFactory.getLogger("Plants vs. Zombies Cubed");

	// Thanks to Ennui Langeweile for the help with Registry Entry Attachments
	public static final RegistryEntryAttachment<EntityType<?>, String> ZOMBIE_MATERIAL =
			RegistryEntryAttachment.stringBuilder(Registries.ENTITY_TYPE, new Identifier("pvzmod", "zombie_material")).build();

	// Thanks to Ennui Langeweile for the help with Registry Entry Attachments
	public static final RegistryEntryAttachment<EntityType<?>, String> ZOMBIE_SIZE =
			RegistryEntryAttachment.stringBuilder(Registries.ENTITY_TYPE, new Identifier("pvzmod", "zombie_size")).build();

	public static final RegistryEntryAttachment<EntityType<?>, String> ZOMBIE_WORLD =
			RegistryEntryAttachment.stringBuilder(Registries.ENTITY_TYPE, new Identifier("pvzmod", "zombie_world")).build();

	public static final RegistryEntryAttachment<EntityType<?>, Boolean> IS_MACHINE =
			RegistryEntryAttachment.boolBuilder(Registries.ENTITY_TYPE, new Identifier("pvzmod", "is_machine")).build();
	public static final RegistryEntryAttachment<EntityType<?>, Integer> ZOMBIE_STRENGTH =
			RegistryEntryAttachment.intBuilder(Registries.ENTITY_TYPE, new Identifier("pvzmod", "zombie_strength")).build();
	public static final RegistryEntryAttachment<EntityType<?>, Boolean> TARGET_GROUND =
			RegistryEntryAttachment.boolBuilder(Registries.ENTITY_TYPE, new Identifier("pvzmod", "target_ground")).build();
	public static final RegistryEntryAttachment<EntityType<?>, Boolean> TARGET_FLY =
			RegistryEntryAttachment.boolBuilder(Registries.ENTITY_TYPE, new Identifier("pvzmod", "target_fly")).build();
	public static final RegistryEntryAttachment<EntityType<?>, String> PLANT_LOCATION =
			RegistryEntryAttachment.stringBuilder(Registries.ENTITY_TYPE, new Identifier("pvzmod", "plant_location")).build();
	public static final RegistryEntryAttachment<EntityType<?>, String> PLANT_TYPE =
			RegistryEntryAttachment.stringBuilder(Registries.ENTITY_TYPE, new Identifier("pvzmod", "plant_type")).build();


	public static final StatusEffect ICE = new Ice();
	public static final StatusEffect FROZEN = new Frozen();
	public static final StatusEffect STUN = new Stun();
	public static final StatusEffect BOUNCED = new Bounced();
	public static final StatusEffect DISABLE = new Disable();
	public static final StatusEffect WARM = new Warm();
	public static final StatusEffect WET = new Wet();
	public static final StatusEffect PVZPOISON = new PvZPoison();
	public static final StatusEffect BARK = new Bark();
	public static final StatusEffect CHEESE = new Cheese();
	public static final StatusEffect GENERICSLOW = new GenericSlow();
	public static final StatusEffect SHADOW = new Shadow();
	public static final StatusEffect MARIGOLD = new Marigold();
	public static final StatusEffect ACID = new Acid();

	public static final GameRules.Key<GameRules.BooleanGameRule> SHOULD_PLANT_SPAWN =
			GameRuleRegistry.register("pvzdoPlantSpawn", GameRules.Category.SPAWNING, GameRuleFactory.createBooleanRule(true));
	public static final GameRules.Key<GameRules.BooleanGameRule> SHOULD_GRAVE_SPAWN =
			GameRuleRegistry.register("pvzdoGraveSpawn", GameRules.Category.SPAWNING, GameRuleFactory.createBooleanRule(true));

	public static final GameRules.Key<GameRules.BooleanGameRule> SHOULD_PLANT_DROP =
			GameRuleRegistry.register("pvzdoPlantDrop", GameRules.Category.DROPS, GameRuleFactory.createBooleanRule(true));
	public static final GameRules.Key<GameRules.BooleanGameRule> SHOULD_SUNFLOWER_DROP =
			GameRuleRegistry.register("pvzdoSunflowerDrop", GameRules.Category.DROPS, GameRuleFactory.createBooleanRule(true));

	public static final GameRules.Key<GameRules.BooleanGameRule> SHOULD_ZOMBIE_DROP =
			GameRuleRegistry.register("pvzdoZombieDrop", GameRules.Category.DROPS, GameRuleFactory.createBooleanRule(true));

	public static final GameRules.Key<GameRules.BooleanGameRule> INFINITE_SEEDS =
			GameRuleRegistry.register("pvzinfiniteSeeds", GameRules.Category.SPAWNING, GameRuleFactory.createBooleanRule(false));
	public static final GameRules.Key<GameRules.BooleanGameRule> INSTANT_RECHARGE =
			GameRuleRegistry.register("pvzinstantRecharge", GameRules.Category.SPAWNING, GameRuleFactory.createBooleanRule(false));
	/**public static final GameRules.Key<GameRules.BooleanRule> COSTS_SUN =
			GameRuleRegistry.register("pvzseedCostSun", GameRules.Category.SPAWNING, GameRuleFactory.createBooleanRule(false));**/

	public static final GameRules.Key<GameRules.BooleanGameRule> PLANTS_GLOW =
			GameRuleRegistry.register("pvzplantsGlow", GameRules.Category.MOBS, GameRuleFactory.createBooleanRule(true));

	public static final GameRules.Key<GameRules.BooleanGameRule> SPECIAL_ZOMBIE =
			GameRuleRegistry.register("pvzspawnSpecialZombies", GameRules.Category.SPAWNING, GameRuleFactory.createBooleanRule(false));

	public static final String MOD_ID = "pvzmod";


	public static final RegistryKey<ItemGroup> PVZPLANTS = RegistryKey.of(RegistryKeys.ITEM_GROUP, new Identifier(MOD_ID, "plants"));
	public static final RegistryKey<ItemGroup> PVZPACKETFABS = RegistryKey.of(RegistryKeys.ITEM_GROUP, new Identifier(MOD_ID, "packet_fab"));
	public static final RegistryKey<ItemGroup> PVZPLANTSPROJ = RegistryKey.of(RegistryKeys.ITEM_GROUP, new Identifier(MOD_ID, "plants_proj"));

	public static final RegistryKey<ItemGroup> PVZZOMBIES = RegistryKey.of(RegistryKeys.ITEM_GROUP, new Identifier(MOD_ID, "zombies"));

	public static final RegistryKey<ItemGroup> PVZGRAVES = RegistryKey.of(RegistryKeys.ITEM_GROUP, new Identifier(MOD_ID, "graves"));

	public static final RegistryKey<ItemGroup> PVZBLOCKS = RegistryKey.of(RegistryKeys.ITEM_GROUP, new Identifier(MOD_ID, "blocks"));

	@Override
	public void onInitialize(ModContainer mod) {
		LOGGER.info("Izzy says: Trans Rights are Human Rights! Also the zombies are coming.");

		ModItems.registerItems();
		ModItems.setSeedPacketList();
		ModItems.setPlantfoodList();
		ModBlocks.registerBlocks();
		PvZEntity.setPlantList();
		PvZEntity.Entities();
		GeckoLib.initialize();
		PvZEntitySpawn.addEntitySpawn();
		ModBlockEntities.registerBlockEntities();
		ModScreenHandlers.registerScreenHandlers();
		ModRecipes.registerRecipes();



		//Entity Initialization


		PvZSounds.registerSounds();
		Registry.register(Registries.ITEM_GROUP, PVZPLANTS, FabricItemGroup.builder()
			.icon(() -> new ItemStack(ModItems.EMPTY_SEED_PACKET))
			.entries((context, entries) -> {
				entries.addStack(new ItemStack(ModItems.EMPTY_SEED_PACKET));
				entries.addStack(new ItemStack(ModItems.PEASHOOTER_SEED_PACKET));
				entries.addStack(new ItemStack(ModItems.SUNFLOWER_SEED_PACKET));
				entries.addStack(new ItemStack(ModItems.CHERRYBOMB_SEED_PACKET));
				entries.addStack(new ItemStack(ModItems.WALLNUT_SEED_PACKET));
				entries.addStack(new ItemStack(ModItems.POTATOMINE_SEED_PACKET));
				entries.addStack(new ItemStack(ModItems.BELLFLOWER_SEED_PACKET));
				entries.addStack(new ItemStack(ModItems.SNOW_PEA_SEED_PACKET));
				entries.addStack(new ItemStack(ModItems.DOGWOOD_SEED_PACKET));
				entries.addStack(new ItemStack(ModItems.SUNFLOWERSEED_SEED_PACKET));
				entries.addStack(new ItemStack(ModItems.CHOMPER_SEED_PACKET));
				entries.addStack(new ItemStack(ModItems.REPEATER_SEED_PACKET));
				entries.addStack(new ItemStack(ModItems.DANDELIONWEED_SEED_PACKET));
				entries.addStack(new ItemStack(ModItems.PUFFSHROOM_SEED_PACKET));
				entries.addStack(new ItemStack(ModItems.SUNSHROOM_SEED_PACKET));
				entries.addStack(new ItemStack(ModItems.FUMESHROOM_SEED_PACKET));
				entries.addStack(new ItemStack(ModItems.MAGICSHROOM_SEED_PACKET));
				entries.addStack(new ItemStack(ModItems.GRAVEBUSTER_SEED_PACKET));
				entries.addStack(new ItemStack(ModItems.HYPNOSHROOM_SEED_PACKET));
				entries.addStack(new ItemStack(ModItems.SCAREDYSHROOM_SEED_PACKET));
				entries.addStack(new ItemStack(ModItems.ICESHROOM_SEED_PACKET));
				entries.addStack(new ItemStack(ModItems.BEAUTYSHROOM_SEED_PACKET));
				entries.addStack(new ItemStack(ModItems.DOOMSHROOM_SEED_PACKET));
				entries.addStack(new ItemStack(ModItems.BREEZESHROOM_SEED_PACKET));
				entries.addStack(new ItemStack(ModItems.BURSTSHROOM_SEED_PACKET));
				entries.addStack(new ItemStack(ModItems.LILYPAD_SEED_PACKET));
				entries.addStack(new ItemStack(ModItems.DROPEA_SEED_PACKET));
				entries.addStack(new ItemStack(ModItems.SEAPEA_SEED_PACKET));
				entries.addStack(new ItemStack(ModItems.SQUASH_SEED_PACKET));
				entries.addStack(new ItemStack(ModItems.THREEPEATER_SEED_PACKET));
				entries.addStack(new ItemStack(ModItems.TANGLEKELP_SEED_PACKET));
				entries.addStack(new ItemStack(ModItems.JALAPENO_SEED_PACKET));
				entries.addStack(new ItemStack(ModItems.SPIKEWEED_SEED_PACKET));
				entries.addStack(new ItemStack(ModItems.NARCISSUS_SEED_PACKET));
				entries.addStack(new ItemStack(ModItems.TORCHWOOD_SEED_PACKET));
				entries.addStack(new ItemStack(ModItems.TALLNUT_SEED_PACKET));
				entries.addStack(new ItemStack(ModItems.DRIPPHYLLEIA_SEED_PACKET));
				entries.addStack(new ItemStack(ModItems.SEASHROOM_SEED_PACKET));
				entries.addStack(new ItemStack(ModItems.ACIDSHROOM_SEED_PACKET));
				entries.addStack(new ItemStack(ModItems.MAGNETSHROOM_SEED_PACKET));
				entries.addStack(new ItemStack(ModItems.CABBAGEPULT_SEED_PACKET));
				entries.addStack(new ItemStack(ModItems.ICEBERGPULT_SEED_PACKET));
				entries.addStack(new ItemStack(ModItems.COFFEEBEAN_SEED_PACKET));
				entries.addStack(new ItemStack(ModItems.ICEBERGLETTUCE_SEED_PACKET));
				entries.addStack(new ItemStack(ModItems.CHILLYPEPPER_SEED_PACKET));
				entries.addStack(new ItemStack(ModItems.FRISBLOOM_SEED_PACKET));
				entries.addStack(new ItemStack(ModItems.SPRINGBEAN_SEED_PACKET));
				entries.addStack(new ItemStack(ModItems.COCONUTCANNON_SEED_PACKET));
				entries.addStack(new ItemStack(ModItems.LIGHTNINGREED_SEED_PACKET));
				entries.addStack(new ItemStack(ModItems.PEAPOD_SEED_PACKET));
				entries.addStack(new ItemStack(ModItems.EMPEACH_SEED_PACKET));
				entries.addStack(new ItemStack(ModItems.PEPPERPULT_SEED_PACKET));
				entries.addStack(new ItemStack(ModItems.ENDURIAN_SEED_PACKET));
				entries.addStack(new ItemStack(ModItems.GOLDLEAF_SEED_PACKET));
				entries.addStack(new ItemStack(ModItems.OLIVEPIT_SEED_PACKET));
				entries.addStack(new ItemStack(ModItems.CHARMSHROOM_SEED_PACKET));
				entries.addStack(new ItemStack(ModItems.GLOOMVINE_SEED_PACKET));
				entries.addStack(new ItemStack(ModItems.VAMPIREFLOWER_SEED_PACKET));
				entries.addStack(new ItemStack(ModItems.PUMPKINWITCH_SEED_PACKET));
				entries.addStack(new ItemStack(ModItems.LOQUAT_SEED_PACKET));
				entries.addStack(new ItemStack(ModItems.SAUCER_SEED_PACKET));
				entries.addStack(new ItemStack(ModItems.SMACKADAMIA_SEED_PACKET));
				entries.addStack(new ItemStack(ModItems.SHADOWSHROOM_SEED_PACKET));
				entries.addStack(new ItemStack(ModItems.GHOSTPEPPER_SEED_PACKET));
				entries.addStack(new ItemStack(ModItems.OXYGAE_SEED_PACKET));
				entries.addStack(new ItemStack(ModItems.ADMIRALNAVYBEAN_SEED_PACKET));
				entries.addStack(new ItemStack(ModItems.NAVYBEAN_SEED_PACKET));
				entries.addStack(new ItemStack(ModItems.SPRINGPRINCESS_SEED_PACKET));
				entries.addStack(new ItemStack(ModItems.OILYOLIVE_SEED_PACKET));
				entries.addStack(new ItemStack(ModItems.PEANUT_SEED_PACKET));
				entries.addStack(new ItemStack(ModItems.HEAVENLYPEACH_SEED_PACKET));
				entries.addStack(new ItemStack(ModItems.BEET_SEED_PACKET));
				entries.addStack(new ItemStack(ModItems.SHAMROCK_SEED_PACKET));
				entries.addStack(new ItemStack(ModItems.JUMPINGBEAN_SEED_PACKET));
				entries.addStack(new ItemStack(ModItems.TULIMPETER_SEED_PACKET));
				entries.addStack(new ItemStack(ModItems.IMPATYENS_SEED_PACKET));
				entries.addStack(new ItemStack(ModItems.HAMMERFLOWER_SEED_PACKET));
				entries.addStack(new ItemStack(ModItems.METEORHAMMER_SEED_PACKET));
				entries.addStack(new ItemStack(ModItems.BOMBSEEDLING_SEED_PACKET));
				entries.addStack(new ItemStack(ModItems.BUTTONSHROOM_SEED_PACKET));
				entries.addStack(new ItemStack(ModItems.ZAPRICOT_SEED_PACKET));
				entries.addStack(new ItemStack(ModItems.SMALLNUT_SEED_PACKET));
				entries.addStack(new ItemStack(ModItems.WEENIEBEANIE_SEED_PACKET));
				entries.addStack(new ItemStack(ModItems.RETROGATLING_SEED_PACKET));
				entries.addStack(new ItemStack(ModItems.TWINSUNFLOWER_SEED_PACKET));
				entries.addStack(new ItemStack(ModItems.SNOW_QUEENPEA_SEED_PACKET));
				entries.addStack(new ItemStack(ModItems.SUPERCHOMPER_SEED_PACKET));
				entries.addStack(new ItemStack(ModItems.GATLINGPEA_SEED_PACKET));
				entries.addStack(new ItemStack(ModItems.GLOOMSHROOM_SEED_PACKET));
				entries.addStack(new ItemStack(ModItems.GAMBLESHROOM_SEED_PACKET));
				entries.addStack(new ItemStack(ModItems.CATTAIL_SEED_PACKET));
				entries.addStack(new ItemStack(ModItems.SPIKEROCK_SEED_PACKET));
				entries.addStack(new ItemStack(ModItems.MAGNETOSHROOM_SEED_PACKET));
				entries.addStack(new ItemStack(ModItems.BLOOMERANG_SEED_PACKET));
				entries.addStack(new ItemStack(ModItems.FIRE_PEA_SEED_PACKET));
				entries.addStack(new ItemStack(ModItems.ELECTROPEA_SEED_PACKET));
				entries.addStack(new ItemStack(ModItems.BEESHOOTER_SEED_PACKET));
				entries.addStack(new ItemStack(ModItems.CHESTER_SEED_PACKET));
				entries.addStack(new ItemStack(ModItems.PERFOOMSHROOM_SEED_PACKET));
				entries.addStack(new ItemStack(ModItems.SMOOSHROOM_SEED_PACKET));
				entries.addStack(new ItemStack(ModItems.KNIGHTPEA_SEED_PACKET));
				entries.addStack(new ItemStack(ModItems.NIGHTCAP_SEED_PACKET));
				entries.addStack(new ItemStack(ModItems.DOOMROSE_SEED_PACKET));
				entries.addStack(new ItemStack(ModItems.BANANASAURUS_SEED_PACKET));
				entries.addStack(new ItemStack(ModItems.MISSILETOE_SEED_PACKET));
			})
			.name(Text.translatable("itemGroup.pvzmod.plants"))
			.build()); // build() no longer registers by itself

		Registry.register(Registries.ITEM_GROUP, PVZPACKETFABS, FabricItemGroup.builder()
			.icon(() -> new ItemStack(ModItems.GARDENINGGLOVE))
			.entries((context, entries) -> {
				entries.addItem(ModBlocks.BOTANY_STATION);
				entries.addStack(new ItemStack(ModItems.DAVES_SHOVEL));
				entries.addStack(new ItemStack(ModItems.GARDEN_SPAWN));
				entries.addStack(new ItemStack(ModItems.GARDENINGGLOVE));
				entries.addStack(new ItemStack(ModItems.PLANTFOOD));
				entries.addStack(new ItemStack(ModItems.PLANTFOOD_AIR));
				entries.addStack(new ItemStack(ModItems.PLANTFOOD_AQUATIC));
				entries.addStack(new ItemStack(ModItems.PLANTFOOD_COLD));
				entries.addStack(new ItemStack(ModItems.PLANTFOOD_ELEC));
				entries.addStack(new ItemStack(ModItems.PLANTFOOD_FIRE));
				entries.addStack(new ItemStack(ModItems.PLANTFOOD_FLOWER));
				entries.addStack(new ItemStack(ModItems.PLANTFOOD_MUSHROOM));
				entries.addStack(new ItemStack(ModItems.PLANTFOOD_SHADOW));
				entries.addStack(new ItemStack(ModItems.PLANTFOOD_TOUGH));
				entries.addStack(new ItemStack(ModItems.SMALLSUN));
				entries.addStack(new ItemStack(ModItems.SUN));
				entries.addStack(new ItemStack(ModItems.LARGESUN));
			})
			.name(Text.translatable("itemGroup.pvzmod.seedfab"))
			.build()); // build() no longer registers by itself

		Registry.register(Registries.ITEM_GROUP, PVZPLANTSPROJ, FabricItemGroup.builder()
			.icon(() -> new ItemStack(ModItems.PEA))
			.entries((context, entries) -> {
				entries.addStack(new ItemStack(ModItems.PEA));
				entries.addStack(new ItemStack(ModItems.SNOWPEAPROJ));
				entries.addStack(new ItemStack(ModItems.SPORE));
				entries.addStack(new ItemStack(ModItems.FUME));
				entries.addStack(new ItemStack(ModItems.HYPNO));
				entries.addStack(new ItemStack(ModItems.FIREPEA));
				entries.addStack(new ItemStack(ModItems.ACIDSPORE));
				entries.addStack(new ItemStack(ModItems.SPIKE));
				entries.addStack(new ItemStack(ModItems.CABBAGE));
				entries.addStack(new ItemStack(ModItems.ICEBERG));
				entries.addStack(new ItemStack(ModItems.RAINBOWBULLET));
				entries.addStack(new ItemStack(ModItems.BEESPIKE));
				entries.addStack(new ItemStack(ModItems.POWERBEESPIKE));
				entries.addStack(new ItemStack(ModItems.SNOWQUEENPEAPROJ));
				entries.addStack(new ItemStack(ModItems.BREEZE));
				entries.addStack(new ItemStack(ModItems.BOOMERANG));
				entries.addStack(new ItemStack(ModItems.COCONUT));
				entries.addStack(new ItemStack(ModItems.PEPPER));
				entries.addStack(new ItemStack(ModItems.PLASMAPEA));
				entries.addStack(new ItemStack(ModItems.POWERSPIKE));
				entries.addStack(new ItemStack(ModItems.ELECTRICPEA));
				entries.addStack(new ItemStack(ModItems.SPRINGHAIR));
				entries.addStack(new ItemStack(ModItems.CARDPROJ));
				entries.addStack(new ItemStack(ModItems.PUMPKINPROJ));
				entries.addStack(new ItemStack(ModItems.HYPNOPROJ));
				entries.addStack(new ItemStack(ModItems.BUBBLES));
				entries.addStack(new ItemStack(ModItems.ARMORBUBBLE));
				entries.addStack(new ItemStack(ModItems.DYEITEM));
				entries.addStack(new ItemStack(ModItems.DROP));
				entries.addStack(new ItemStack(ModItems.ICESPIKE));
				entries.addStack(new ItemStack(ModItems.POWERICESPIKE));
				entries.addStack(new ItemStack(ModItems.FRISBEE));
				entries.addStack(new ItemStack(ModItems.SWORDPROJ));
				entries.addStack(new ItemStack(ModItems.POWERSWORDPROJ));
				entries.addStack(new ItemStack(ModItems.PIERCEPEA));
				entries.addStack(new ItemStack(ModItems.FIREPIERCEPEA));
				entries.addStack(new ItemStack(ModItems.ACIDFUME));
				entries.addStack(new ItemStack(ModItems.SMOOSH));
				entries.addStack(new ItemStack(ModItems.SPIT));
				entries.addStack(new ItemStack(ModItems.JINGLE));
				entries.addStack(new ItemStack(ModItems.PIERCESPORE));
				entries.addStack(new ItemStack(ModItems.PIERCESPORESHADOW));
				entries.addStack(new ItemStack(ModItems.BARK));
				entries.addStack(new ItemStack(ModItems.GOLDENCARDPROJ));
			})
			.name(Text.translatable("itemGroup.pvzmod.plants.proj"))
			.build()); // build() no longer registers by itself
		Registry.register(Registries.ITEM_GROUP, PVZZOMBIES, FabricItemGroup.builder()
			.icon(() -> new ItemStack(ModItems.BRAIN))
			.entries((context, entries) -> {
				entries.addStack(new ItemStack(ModItems.BRAIN));
				entries.addStack(new ItemStack(ModItems.LOCUSTSWARMEGG));
				entries.addStack(new ItemStack(ModItems.BROWNCOATEGG));
				entries.addStack(new ItemStack(ModItems.FLAGZOMBIEEGG));
				entries.addStack(new ItemStack(ModItems.CONEHEADEGG));
				entries.addStack(new ItemStack(ModItems.POLEVAULTINGEGG));
				entries.addStack(new ItemStack(ModItems.BUCKETHEADEGG));
				entries.addStack(new ItemStack(ModItems.NEWSPAPEREGG));
				entries.addStack(new ItemStack(ModItems.SCREENDOOREGG));
				entries.addStack(new ItemStack(ModItems.FOOTBALLEGG));
				entries.addStack(new ItemStack(ModItems.DANCINGZOMBIEEGG));
				entries.addStack(new ItemStack(ModItems.BACKUPDANCEREGG));
				entries.addStack(new ItemStack(ModItems.SNORKELEGG));
				entries.addStack(new ItemStack(ModItems.DOLPHINRIDEREGG));
				entries.addStack(new ItemStack(ModItems.ZOMBONIEGG));
				entries.addStack(new ItemStack(ModItems.BOBSLEDEGG));
				entries.addStack(new ItemStack(ModItems.GARGANTUAREGG));
				entries.addStack(new ItemStack(ModItems.IMPEGG));
				entries.addStack(new ItemStack(ModItems.TRASHCANEGG));
				entries.addStack(new ItemStack(ModItems.BERSERKEREGG));
				entries.addStack(new ItemStack(ModItems.MUMMYEGG));
				entries.addStack(new ItemStack(ModItems.FLAGMUMMYEGG));
				entries.addStack(new ItemStack(ModItems.MUMMYCONEEGG));
				entries.addStack(new ItemStack(ModItems.MUMMYBUCKETEGG));
				entries.addStack(new ItemStack(ModItems.EXPLOREREGG));
				entries.addStack(new ItemStack(ModItems.TOMBRAISEREGG));
				entries.addStack(new ItemStack(ModItems.UNDYINGEGG));
				entries.addStack(new ItemStack(ModItems.PHARAOHEGG));
				entries.addStack(new ItemStack(ModItems.MUMMYGARGANTUAREGG));
				entries.addStack(new ItemStack(ModItems.MUMMYIMPEGG));
				entries.addStack(new ItemStack(ModItems.TORCHLIGHTEGG));
				entries.addStack(new ItemStack(ModItems.PYRAMIDHEADEGG));
				entries.addStack(new ItemStack(ModItems.FUTUREZOMBIEEGG));
				entries.addStack(new ItemStack(ModItems.FLAGFUTUREEGG));
				entries.addStack(new ItemStack(ModItems.FUTURECONEEGG));
				entries.addStack(new ItemStack(ModItems.FUTUREBUCKETEGG));
				entries.addStack(new ItemStack(ModItems.JETPACKEGG));
				entries.addStack(new ItemStack(ModItems.ROBOCONEEGG));
				entries.addStack(new ItemStack(ModItems.HOLOHEADEGG));
				entries.addStack(new ItemStack(ModItems.BLASTRONAUTEGG));
				entries.addStack(new ItemStack(ModItems.PEASANTEGG));
				entries.addStack(new ItemStack(ModItems.FLAGPEASANTEGG));
				entries.addStack(new ItemStack(ModItems.PEASANTCONEEGG));
				entries.addStack(new ItemStack(ModItems.PEASANTBUCKETEGG));
				entries.addStack(new ItemStack(ModItems.PEASANTKNIGHTEGG));
				entries.addStack(new ItemStack(ModItems.ANNOUNCERIMPEGG));
				entries.addStack(new ItemStack(ModItems.ZOMBIEKINGEGG));
				entries.addStack(new ItemStack(ModItems.IMPDRAGONEGG));
				entries.addStack(new ItemStack(ModItems.OCTOEGG));
				entries.addStack(new ItemStack(ModItems.BRICKHEADEGG));
				entries.addStack(new ItemStack(ModItems.SUNDAYEDITIONEGG));
				entries.addStack(new ItemStack(ModItems.SUPERFANIMPEGG));
				entries.addStack(new ItemStack(ModItems.SUMMERBASICEGG));
				entries.addStack(new ItemStack(ModItems.FLAGSUMMEREGG));
				entries.addStack(new ItemStack(ModItems.SUMMERCONEEGG));
				entries.addStack(new ItemStack(ModItems.SUMMERBUCKETEGG));
				entries.addStack(new ItemStack(ModItems.BASSEGG));
				entries.addStack(new ItemStack(ModItems.PUMPKINZOMBIEEGG));
				entries.addStack(new ItemStack(ModItems.NEWYEARIMPEGG));
				entries.addStack(new ItemStack(ModItems.POKEREGG));
				entries.addStack(new ItemStack(ModItems.FLAGPOKEREGG));
				entries.addStack(new ItemStack(ModItems.POKERCONEEGG));
				entries.addStack(new ItemStack(ModItems.POKERBUCKETEGG));
				entries.addStack(new ItemStack(ModItems.POKERPAWNEGG));
				entries.addStack(new ItemStack(ModItems.POKERKNIGHTEGG));
				entries.addStack(new ItemStack(ModItems.POKERTOWEREGG));
				entries.addStack(new ItemStack(ModItems.POKERBISHOPEGG));
				entries.addStack(new ItemStack(ModItems.SARGEANTEGG));
				entries.addStack(new ItemStack(ModItems.FLAGSARGEANTEGG));
				entries.addStack(new ItemStack(ModItems.SARGEANTCONEEGG));
				entries.addStack(new ItemStack(ModItems.SARGEANTBUCKETEGG));
				entries.addStack(new ItemStack(ModItems.SARGEANTSHIELDEGG));
				entries.addStack(new ItemStack(ModItems.HAWKEREGG));
				entries.addStack(new ItemStack(ModItems.PIGGYEGG));
				entries.addStack(new ItemStack(ModItems.SOLDIEREGG));
				entries.addStack(new ItemStack(ModItems.SCIENTISTEGG));
				entries.addStack(new ItemStack(ModItems.HOVERGOATEGG));
				entries.addStack(new ItemStack(ModItems.ZOMBLOBEGG));
				entries.addStack(new ItemStack(ModItems.DEFENSIVEENDEGG));
				entries.addStack(new ItemStack(ModItems.CURSEDGARGOLITHEGG));
				entries.addStack(new ItemStack(ModItems.IMPTHROWEGG));
				entries.addStack(new ItemStack(ModItems.SCRAPMECHEGG));
				entries.addStack(new ItemStack(ModItems.ACTIONHEROEGG));
				entries.addStack(new ItemStack(ModItems.UNICORNGARGANTUAREGG));
				entries.addStack(new ItemStack(ModItems.BASSIMPEGG));
				entries.addStack(new ItemStack(ModItems.BULLYEGG));
				entries.addStack(new ItemStack(ModItems.BASKETBALLCARRIEREGG));
				entries.addStack(new ItemStack(ModItems.REDANNOUNCERIMPEGG));
				entries.addStack(new ItemStack(ModItems.REDZOMBIEKINGEGG));
				entries.addStack(new ItemStack(ModItems.BLACKANNOUNCERIMPEGG));
				entries.addStack(new ItemStack(ModItems.BLACKZOMBIEKINGEGG));
				entries.addStack(new ItemStack(ModItems.PUMPKINCARRIAGEEGG));
				entries.addStack(new ItemStack(ModItems.CRYSTALSHOEIMPEGG));
				entries.addStack(new ItemStack(ModItems.BOOKBURNEREGG));
				entries.addStack(new ItemStack(ModItems.SCRAPIMPEGG));
			})
			.name(Text.translatable("itemGroup.pvzmod.zombies"))
			.build());
		Registry.register(Registries.ITEM_GROUP, PVZGRAVES, FabricItemGroup.builder()
			.icon(() -> new ItemStack(ModItems.ZOMBIEGRAVESPAWN))
			.entries((context, entries) -> {
				entries.addStack(new ItemStack(ModItems.ZOMBIEGRAVESPAWN));
				entries.addStack(new ItemStack(ModItems.EASY));
				entries.addStack(new ItemStack(ModItems.EASYMED));
				entries.addStack(new ItemStack(ModItems.MED));
				entries.addStack(new ItemStack(ModItems.MEDHARD));
				entries.addStack(new ItemStack(ModItems.HARD));
				entries.addStack(new ItemStack(ModItems.SUPERHARD));
				entries.addStack(new ItemStack(ModItems.NIGHTMARE));
				entries.addStack(new ItemStack(ModItems.CRAAAAZY));
				entries.addStack(new ItemStack(ModItems.INFINITE));
				entries.addStack(new ItemStack(ModItems.UNLOCKSPECIAL));
				entries.addStack(new ItemStack(ModItems.UNLOCK));
				entries.addStack(new ItemStack(ModItems.ONEBYONE));
				entries.addStack(new ItemStack(ModItems.HALF));
				entries.addStack(new ItemStack(ModItems.DAY));
				entries.addStack(new ItemStack(ModItems.NIGHT));
				entries.addStack(new ItemStack(ModItems.DROUGHT));
				entries.addStack(new ItemStack(ModItems.BOMB));
				entries.addStack(new ItemStack(ModItems.CLEAR));
				entries.addStack(new ItemStack(ModItems.RAIN));
				entries.addStack(new ItemStack(ModItems.THUNDER));
				entries.addStack(new ItemStack(ModItems.BASICGRAVESPAWN));
				entries.addStack(new ItemStack(ModItems.NIGHTGRAVESPAWN));
				entries.addStack(new ItemStack(ModItems.POOLGRAVESPAWN));
				entries.addStack(new ItemStack(ModItems.ROOFGRAVESPAWN));
				entries.addStack(new ItemStack(ModItems.EGYPTGRAVESPAWN));
				entries.addStack(new ItemStack(ModItems.FUTUREGRAVESPAWN));
				entries.addStack(new ItemStack(ModItems.DARKAGESGRAVESPAWN));
				entries.addStack(new ItemStack(ModItems.FAIRYTALEGRAVESPAWN));
				entries.addStack(new ItemStack(ModItems.MAUSOLEUMGRAVESPAWN));
				entries.addStack(new ItemStack(ModItems.GARDENCHALLENGE_SPAWN));
				entries.addStack(new ItemStack(ModItems.FERTILIZER));
			})
			.name(Text.translatable("itemGroup.pvzmod.graves"))
			.build());
		Registry.register(Registries.ITEM_GROUP, PVZBLOCKS, FabricItemGroup.builder()
			.icon(() -> new ItemStack(ModItems.FAIRY_TILE))
			.entries((context, entries) -> {
				entries.addStack(new ItemStack(ModItems.GRASS_TILE));
				entries.addStack(new ItemStack(ModItems.DARK_GRASS_TILE));
				entries.addStack(new ItemStack(ModItems.NIGHT_TILE));
				entries.addStack(new ItemStack(ModItems.DARK_NIGHT_TILE));
				entries.addStack(new ItemStack(ModItems.ROOF_TILE));
				entries.addStack(new ItemStack(ModItems.DARK_ROOF_TILE));
				entries.addStack(new ItemStack(ModItems.UPGRADE_TILE));
				entries.addStack(new ItemStack(ModItems.PREMIUM_TILE));
				entries.addStack(new ItemStack(ModItems.EGYPT_TILE));
				entries.addStack(new ItemStack(ModItems.DARK_EGYPT_TILE));
				entries.addStack(new ItemStack(ModItems.PIRATE_TILE));
				entries.addStack(new ItemStack(ModItems.DARK_PIRATE_TILE));
				entries.addStack(new ItemStack(ModItems.WEST_TILE));
				entries.addStack(new ItemStack(ModItems.DARK_WEST_TILE));
				entries.addStack(new ItemStack(ModItems.FUTURE_TILE));
				entries.addStack(new ItemStack(ModItems.DARK_FUTURE_TILE));
				entries.addStack(new ItemStack(ModItems.DARKAGES_TILE));
				entries.addStack(new ItemStack(ModItems.DARK_DARKAGES_TILE));
				entries.addStack(new ItemStack(ModItems.SAND_TILE));
				entries.addStack(new ItemStack(ModItems.DARK_SAND_TILE));
				entries.addStack(new ItemStack(ModItems.UNDERWATER_TILE));
				entries.addStack(new ItemStack(ModItems.DARK_UNDERWATER_TILE));
				entries.addStack(new ItemStack(ModItems.FROST_TILE));
				entries.addStack(new ItemStack(ModItems.DARK_FROST_TILE));
				entries.addStack(new ItemStack(ModItems.LOSTCITY_TILE));
				entries.addStack(new ItemStack(ModItems.DARK_LOSTCITY_TILE));
				entries.addStack(new ItemStack(ModItems.SKYCITY_TILE));
				entries.addStack(new ItemStack(ModItems.DARK_SKYCITY_TILE));
				entries.addStack(new ItemStack(ModItems.FAIRY_TILE));
				entries.addStack(new ItemStack(ModItems.DARK_FAIRY_TILE));
				entries.addStack(new ItemStack(ModItems.MAUSOLEUM_TILE));
				entries.addStack(new ItemStack(ModItems.DARK_MAUSOLEUM_TILE));
				entries.addStack(new ItemStack(ModItems.LEGENDARY_TILE));
			})
			.name(Text.translatable("itemGroup.pvzmod.blocks"))
			.build());

		Registry.register(Registries.STATUS_EFFECT, new Identifier("pvzmod", "ice"), ICE);
		Registry.register(Registries.STATUS_EFFECT, new Identifier("pvzmod", "frozen"), FROZEN);
		Registry.register(Registries.STATUS_EFFECT, new Identifier("pvzmod", "stun"), STUN);
		Registry.register(Registries.STATUS_EFFECT, new Identifier("pvzmod", "bounced"), BOUNCED);
		Registry.register(Registries.STATUS_EFFECT, new Identifier("pvzmod", "disable"), DISABLE);
		Registry.register(Registries.STATUS_EFFECT, new Identifier("pvzmod", "warm"), WARM);
		Registry.register(Registries.STATUS_EFFECT, new Identifier("pvzmod", "wet"), WET);
		Registry.register(Registries.STATUS_EFFECT, new Identifier("pvzmod", "pvzpoison"), PVZPOISON);
		Registry.register(Registries.STATUS_EFFECT, new Identifier("pvzmod", "acid"), ACID);
		Registry.register(Registries.STATUS_EFFECT, new Identifier("pvzmod", "bark"), BARK);
		Registry.register(Registries.STATUS_EFFECT, new Identifier("pvzmod", "cheese"), CHEESE);
		Registry.register(Registries.STATUS_EFFECT, new Identifier("pvzmod", "genericslow"), GENERICSLOW);
		Registry.register(Registries.STATUS_EFFECT, new Identifier("pvzmod", "shadow"), SHADOW);
		Registry.register(Registries.STATUS_EFFECT, new Identifier("pvzmod", "marigold"), MARIGOLD);
	}
}
