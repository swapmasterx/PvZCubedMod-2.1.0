package io.github.GrassyDev.pvzmod.config;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.block.ModBlocks;
import io.github.GrassyDev.pvzmod.items.*;
import io.github.GrassyDev.pvzmod.items.seedpackets.*;
import io.github.GrassyDev.pvzmod.items.spawneggs.*;
import io.github.GrassyDev.pvzmod.items.targets.MissileToeTargetItem;
import io.github.GrassyDev.pvzmod.items.toolclasses.PlantKillingMaterial;
import io.github.GrassyDev.pvzmod.items.toolclasses.PlantKillingShovel;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ToolItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.registry.Registry;
import net.minecraft.registry.Registries;

import java.util.ArrayList;
import java.util.List;

public class ModItems {

	// REMEMBER TO REGISTER NEW PLANT SEEDS HERE TOO
	public static void setSeedPacketList(){
		SEED_PACKET_LIST.add(ModItems.GARDENINGGLOVE);
		SEED_PACKET_LIST.add(ModItems.FERTILIZER);
		SEED_PACKET_LIST.add(ModItems.SUNFLOWER_SEED_PACKET);
		SEED_PACKET_LIST.add(ModItems.PEASHOOTER_SEED_PACKET);
		SEED_PACKET_LIST.add(ModItems.CHERRYBOMB_SEED_PACKET);
		SEED_PACKET_LIST.add(ModItems.WALLNUT_SEED_PACKET);
		SEED_PACKET_LIST.add(ModItems.POTATOMINE_SEED_PACKET);
		SEED_PACKET_LIST.add(ModItems.SNOW_PEA_SEED_PACKET);
		SEED_PACKET_LIST.add(ModItems.CHOMPER_SEED_PACKET);
		SEED_PACKET_LIST.add(ModItems.REPEATER_SEED_PACKET);

		SEED_PACKET_LIST.add(ModItems.SUNSHROOM_SEED_PACKET);
		NIGHT_SEED_LIST.add(ModItems.SUNSHROOM_SEED_PACKET);
		SEED_PACKET_LIST.add(ModItems.PUFFSHROOM_SEED_PACKET);
		NIGHT_SEED_LIST.add(ModItems.PUFFSHROOM_SEED_PACKET);

		SEED_PACKET_LIST.add(ModItems.FUMESHROOM_SEED_PACKET);
		DARKAGES_SEED_LIST.add(ModItems.FUMESHROOM_SEED_PACKET);

		SEED_PACKET_LIST.add(ModItems.GRAVEBUSTER_SEED_PACKET);
		EGYPT_SEED_LIST.add(ModItems.GRAVEBUSTER_SEED_PACKET);

		SEED_PACKET_LIST.add(ModItems.HYPNOSHROOM_SEED_PACKET);
		DARKAGES_SEED_LIST.add(ModItems.HYPNOSHROOM_SEED_PACKET);

		SEED_PACKET_LIST.add(ModItems.SCAREDYSHROOM_SEED_PACKET);
		NIGHT_SEED_LIST.add(ModItems.SCAREDYSHROOM_SEED_PACKET);
		SEED_PACKET_LIST.add(ModItems.ICESHROOM_SEED_PACKET);
		NIGHT_SEED_LIST.add(ModItems.ICESHROOM_SEED_PACKET);
		SEED_PACKET_LIST.add(ModItems.DOOMSHROOM_SEED_PACKET);
		NIGHT_SEED_LIST.add(ModItems.DOOMSHROOM_SEED_PACKET);

		SEED_PACKET_LIST.add(ModItems.LILYPAD_SEED_PACKET);
		POOL_SEED_LIST.add(ModItems.LILYPAD_SEED_PACKET);
		SEED_PACKET_LIST.add(ModItems.SQUASH_SEED_PACKET);
		POOL_SEED_LIST.add(ModItems.SQUASH_SEED_PACKET);

		SEED_PACKET_LIST.add(ModItems.THREEPEATER_SEED_PACKET);
		PIRATE_SEED_LIST.add(ModItems.THREEPEATER_SEED_PACKET);

		SEED_PACKET_LIST.add(ModItems.TANGLEKELP_SEED_PACKET);
		BEACH_SEED_LIST.add(ModItems.TANGLEKELP_SEED_PACKET);

		SEED_PACKET_LIST.add(ModItems.JALAPENO_SEED_PACKET);
		WILDWEST_SEED_LIST.add(ModItems.JALAPENO_SEED_PACKET);

		SEED_PACKET_LIST.add(ModItems.SPIKEWEED_SEED_PACKET);
		PIRATE_SEED_LIST.add(ModItems.SPIKEWEED_SEED_PACKET);

		SEED_PACKET_LIST.add(ModItems.TORCHWOOD_SEED_PACKET);
		WILDWEST_SEED_LIST.add(ModItems.TORCHWOOD_SEED_PACKET);

		SEED_PACKET_LIST.add(ModItems.TALLNUT_SEED_PACKET);
		POOL_SEED_LIST.add(ModItems.TALLNUT_SEED_PACKET);

		SEED_PACKET_LIST.add(ModItems.SEASHROOM_SEED_PACKET);
		FOG_SEED_LIST.add(ModItems.SEASHROOM_SEED_PACKET);
		SEED_PACKET_LIST.add(ModItems.MAGNETSHROOM_SEED_PACKET);
		FOG_SEED_LIST.add(ModItems.MAGNETSHROOM_SEED_PACKET);

		SEED_PACKET_LIST.add(ModItems.CABBAGEPULT_SEED_PACKET);
		ROOF_SEED_LIST.add(ModItems.CABBAGEPULT_SEED_PACKET);

		SEED_PACKET_LIST.add(ModItems.COFFEEBEAN_SEED_PACKET);
		ROOF_SEED_LIST.add(ModItems.COFFEEBEAN_SEED_PACKET);

		SEED_PACKET_LIST.add(ModItems.GATLINGPEA_SEED_PACKET);
		PREMIUM_SEED_LIST.add(ModItems.GATLINGPEA_SEED_PACKET);
		SEED_PACKET_LIST.add(ModItems.RETROGATLING_SEED_PACKET);
		PREMIUM_SEED_LIST.add(ModItems.RETROGATLING_SEED_PACKET);
		SEED_PACKET_LIST.add(ModItems.GLOOMSHROOM_SEED_PACKET);
		PREMIUM_SEED_LIST.add(ModItems.GLOOMSHROOM_SEED_PACKET);
		SEED_PACKET_LIST.add(ModItems.CATTAIL_SEED_PACKET);
		PREMIUM_SEED_LIST.add(ModItems.CATTAIL_SEED_PACKET);
		SEED_PACKET_LIST.add(ModItems.TWINSUNFLOWER_SEED_PACKET);
		PREMIUM_SEED_LIST.add(ModItems.TWINSUNFLOWER_SEED_PACKET);
		SEED_PACKET_LIST.add(ModItems.SPIKEROCK_SEED_PACKET);
		PREMIUM_SEED_LIST.add(ModItems.SPIKEROCK_SEED_PACKET);

		SEED_PACKET_LIST.add(ModItems.ICEBERGPULT_SEED_PACKET);
		ROOF_SEED_LIST.add(ModItems.ICEBERGPULT_SEED_PACKET);

		SEED_PACKET_LIST.add(ModItems.BEET_SEED_PACKET);
		SEED_PACKET_LIST.add(ModItems.SHAMROCK_SEED_PACKET);

		SEED_PACKET_LIST.add(ModItems.CHILLYPEPPER_SEED_PACKET);
		EGYPT_SEED_LIST.add(ModItems.CHILLYPEPPER_SEED_PACKET);

		SEED_PACKET_LIST.add(ModItems.BEESHOOTER_SEED_PACKET);
		PREMIUM_SEED_LIST.add(ModItems.BEESHOOTER_SEED_PACKET);
		SEED_PACKET_LIST.add(ModItems.SNOW_QUEENPEA_SEED_PACKET);
		PREMIUM_SEED_LIST.add(ModItems.SNOW_QUEENPEA_SEED_PACKET);

		SEED_PACKET_LIST.add(ModItems.OXYGAE_SEED_PACKET);
		BEACH_SEED_LIST.add(ModItems.OXYGAE_SEED_PACKET);

		SEED_PACKET_LIST.add(ModItems.BREEZESHROOM_SEED_PACKET);
		DARKAGES_SEED_LIST.add(ModItems.BREEZESHROOM_SEED_PACKET);

		SEED_PACKET_LIST.add(ModItems.BURSTSHROOM_SEED_PACKET);
		NIGHT_SEED_LIST.add(ModItems.BURSTSHROOM_SEED_PACKET);

		SEED_PACKET_LIST.add(ModItems.SUPERCHOMPER_SEED_PACKET);

		SEED_PACKET_LIST.add(ModItems.CHESTER_SEED_PACKET);
		PREMIUM_SEED_LIST.add(ModItems.CHESTER_SEED_PACKET);

		SEED_PACKET_LIST.add(ModItems.CHESTER_SEED_PACKET);
		DARKAGES_SEED_LIST.add(ModItems.VAMPIREFLOWER_SEED_PACKET);

		SEED_PACKET_LIST.add(ModItems.ACIDSHROOM_SEED_PACKET);
		FOG_SEED_LIST.add(ModItems.ACIDSHROOM_SEED_PACKET);

		SEED_PACKET_LIST.add(ModItems.DANDELIONWEED_SEED_PACKET);

		SEED_PACKET_LIST.add(ModItems.PERFOOMSHROOM_SEED_PACKET);
		PREMIUM_SEED_LIST.add(ModItems.PERFOOMSHROOM_SEED_PACKET);

		SEED_PACKET_LIST.add(ModItems.BLOOMERANG_SEED_PACKET);
		PREMIUM_SEED_LIST.add(ModItems.BLOOMERANG_SEED_PACKET);

		SEED_PACKET_LIST.add(ModItems.ICEBERGLETTUCE_SEED_PACKET);
		EGYPT_SEED_LIST.add(ModItems.ICEBERGLETTUCE_SEED_PACKET);

		SEED_PACKET_LIST.add(ModItems.SPRINGBEAN_SEED_PACKET);
		PIRATE_SEED_LIST.add(ModItems.SPRINGBEAN_SEED_PACKET);
		SEED_PACKET_LIST.add(ModItems.COCONUTCANNON_SEED_PACKET);
		PIRATE_SEED_LIST.add(ModItems.COCONUTCANNON_SEED_PACKET);

		SEED_PACKET_LIST.add(ModItems.LIGHTNINGREED_SEED_PACKET);
		WILDWEST_SEED_LIST.add(ModItems.LIGHTNINGREED_SEED_PACKET);
		SEED_PACKET_LIST.add(ModItems.PEAPOD_SEED_PACKET);
		WILDWEST_SEED_LIST.add(ModItems.PEAPOD_SEED_PACKET);

		SEED_PACKET_LIST.add(ModItems.EMPEACH_SEED_PACKET);
		FUTURE_SEED_LIST.add(ModItems.EMPEACH_SEED_PACKET);

		SEED_PACKET_LIST.add(ModItems.PEPPERPULT_SEED_PACKET);
		FROSTBITE_SEED_LIST.add(ModItems.PEPPERPULT_SEED_PACKET);

		SEED_PACKET_LIST.add(ModItems.FIRE_PEA_SEED_PACKET);
		PREMIUM_SEED_LIST.add(ModItems.FIRE_PEA_SEED_PACKET);

		SEED_PACKET_LIST.add(ModItems.ENDURIAN_SEED_PACKET);
		LOSTCITY_SEED_LIST.add(ModItems.ENDURIAN_SEED_PACKET);
		SEED_PACKET_LIST.add(ModItems.GOLDLEAF_SEED_PACKET);
		LOSTCITY_SEED_LIST.add(ModItems.GOLDLEAF_SEED_PACKET);

		SEED_PACKET_LIST.add(ModItems.SHADOWSHROOM_SEED_PACKET);
		NIGHT_SEED_LIST.add(ModItems.SHADOWSHROOM_SEED_PACKET);

		SEED_PACKET_LIST.add(ModItems.MISSILETOE_SEED_PACKET);
		LEGENDARY_SEED_LIST.add(ModItems.MISSILETOE_SEED_PACKET);

		SEED_PACKET_LIST.add(ModItems.ELECTROPEA_SEED_PACKET);
		PREMIUM_SEED_LIST.add(ModItems.ELECTROPEA_SEED_PACKET);

		SEED_PACKET_LIST.add(ModItems.PEANUT_SEED_PACKET);

		SEED_PACKET_LIST.add(ModItems.GHOSTPEPPER_SEED_PACKET);
		NIGHT_SEED_LIST.add(ModItems.GHOSTPEPPER_SEED_PACKET);

		SEED_PACKET_LIST.add(ModItems.OLIVEPIT_SEED_PACKET);
		LOSTCITY_SEED_LIST.add(ModItems.OLIVEPIT_SEED_PACKET);

		SEED_PACKET_LIST.add(ModItems.GLOOMVINE_SEED_PACKET);
		DARKAGES_SEED_LIST.add(ModItems.GLOOMVINE_SEED_PACKET);

		SEED_PACKET_LIST.add(ModItems.HEAVENLYPEACH_SEED_PACKET);

		SEED_PACKET_LIST.add(ModItems.MAGICSHROOM_SEED_PACKET);
		NIGHT_SEED_LIST.add(ModItems.MAGICSHROOM_SEED_PACKET);

		SEED_PACKET_LIST.add(ModItems.SAUCER_SEED_PACKET);
		SKYCITY_SEED_LIST.add(ModItems.SAUCER_SEED_PACKET);

		SEED_PACKET_LIST.add(ModItems.SPRINGPRINCESS_SEED_PACKET);
		FAIRYTALE_SEED_LIST.add(ModItems.SPRINGPRINCESS_SEED_PACKET);

		SEED_PACKET_LIST.add(ModItems.DRIPPHYLLEIA_SEED_PACKET);
		POOL_SEED_LIST.add(ModItems.DRIPPHYLLEIA_SEED_PACKET);

		SEED_PACKET_LIST.add(ModItems.PUMPKINWITCH_SEED_PACKET);
		DARKAGES_SEED_LIST.add(ModItems.PUMPKINWITCH_SEED_PACKET);

		SEED_PACKET_LIST.add(ModItems.TULIMPETER_SEED_PACKET);

		SEED_PACKET_LIST.add(ModItems.NARCISSUS_SEED_PACKET);
		POOL_SEED_LIST.add(ModItems.NARCISSUS_SEED_PACKET);

		SEED_PACKET_LIST.add(ModItems.IMPATYENS_SEED_PACKET);

		SEED_PACKET_LIST.add(ModItems.HAMMERFLOWER_SEED_PACKET);
		SEED_PACKET_LIST.add(ModItems.METEORHAMMER_SEED_PACKET);

		SEED_PACKET_LIST.add(ModItems.FRISBLOOM_SEED_PACKET);
		EGYPT_SEED_LIST.add(ModItems.FRISBLOOM_SEED_PACKET);

		SEED_PACKET_LIST.add(ModItems.BEAUTYSHROOM_SEED_PACKET);
		NIGHT_SEED_LIST.add(ModItems.BEAUTYSHROOM_SEED_PACKET);

		SEED_PACKET_LIST.add(ModItems.CHARMSHROOM_SEED_PACKET);
		DARKAGES_SEED_LIST.add(ModItems.CHARMSHROOM_SEED_PACKET);

		SEED_PACKET_LIST.add(ModItems.MAGNETOSHROOM_SEED_PACKET);
		PREMIUM_SEED_LIST.add(ModItems.MAGNETOSHROOM_SEED_PACKET);

		SEED_PACKET_LIST.add(ModItems.SMACKADAMIA_SEED_PACKET);

		SEED_PACKET_LIST.add(ModItems.SMACKADAMIA_SEED_PACKET);

		SEED_PACKET_LIST.add(ModItems.BOMBSEEDLING_SEED_PACKET);
		SEED_PACKET_LIST.add(ModItems.OILYOLIVE_SEED_PACKET);
		SEED_PACKET_LIST.add(ModItems.ZAPRICOT_SEED_PACKET);

		SEED_PACKET_LIST.add(ModItems.BANANASAURUS_SEED_PACKET);
		LEGENDARY_SEED_LIST.add(ModItems.BANANASAURUS_SEED_PACKET);

		SEED_PACKET_LIST.add(ModItems.WEENIEBEANIE_SEED_PACKET);

		SEED_PACKET_LIST.add(ModItems.SMOOSHROOM_SEED_PACKET);
		PREMIUM_SEED_LIST.add(ModItems.SMOOSHROOM_SEED_PACKET);

		SEED_PACKET_LIST.add(ModItems.NAVYBEAN_SEED_PACKET);
		BEACH_SEED_LIST.add(ModItems.NAVYBEAN_SEED_PACKET);
		SEED_PACKET_LIST.add(ModItems.ADMIRALNAVYBEAN_SEED_PACKET);
		BEACH_SEED_LIST.add(ModItems.ADMIRALNAVYBEAN_SEED_PACKET);

		SEED_PACKET_LIST.add(ModItems.JUMPINGBEAN_SEED_PACKET);
		SEED_PACKET_LIST.add(ModItems.SUNFLOWERSEED_SEED_PACKET);
		SEED_PACKET_LIST.add(ModItems.BELLFLOWER_SEED_PACKET);

		SEED_PACKET_LIST.add(ModItems.KNIGHTPEA_SEED_PACKET);
		PREMIUM_SEED_LIST.add(ModItems.KNIGHTPEA_SEED_PACKET);

		SEED_PACKET_LIST.add(ModItems.NIGHTCAP_SEED_PACKET);
		PREMIUM_SEED_LIST.add(ModItems.NIGHTCAP_SEED_PACKET);

		SEED_PACKET_LIST.add(ModItems.DOOMROSE_SEED_PACKET);
		PREMIUM_SEED_LIST.add(ModItems.DOOMROSE_SEED_PACKET);

		SEED_PACKET_LIST.add(ModItems.DOGWOOD_SEED_PACKET);

		SEED_PACKET_LIST.add(ModItems.GAMBLESHROOM_SEED_PACKET);
		PREMIUM_SEED_LIST.add(ModItems.GAMBLESHROOM_SEED_PACKET);
	}
	public static void setPlantfoodList(){
		PLANTFOOD_LIST.add(ModItems.PLANTFOOD);
		PLANTFOOD_LIST.add(ModItems.PLANTFOOD_AIR);
		PLANTFOOD_LIST.add(ModItems.PLANTFOOD_AQUATIC);
		PLANTFOOD_LIST.add(ModItems.PLANTFOOD_COLD);
		PLANTFOOD_LIST.add(ModItems.PLANTFOOD_FIRE);
		PLANTFOOD_LIST.add(ModItems.PLANTFOOD_FLOWER);
		PLANTFOOD_LIST.add(ModItems.PLANTFOOD_ELEC);
		PLANTFOOD_LIST.add(ModItems.PLANTFOOD_MUSHROOM);
		PLANTFOOD_LIST.add(ModItems.PLANTFOOD_TOUGH);
		PLANTFOOD_LIST.add(ModItems.PLANTFOOD_SHADOW);
	}

    public static final Item ALMANAC = new Item(new Item.Settings().maxCount(1));

    //Items
    public static final Item PLANTFOOD = new Item(new Item.Settings().maxCount(8));
	public static final Item PLANTFOOD_AIR = new Item(new Item.Settings().maxCount(8));
	public static final Item PLANTFOOD_AQUATIC = new Item(new Item.Settings().maxCount(8));
	public static final Item PLANTFOOD_COLD = new Item(new Item.Settings().maxCount(8));
	public static final Item PLANTFOOD_ELEC = new Item(new Item.Settings().maxCount(8));
	public static final Item PLANTFOOD_FIRE = new Item(new Item.Settings().maxCount(8));
	public static final Item PLANTFOOD_FLOWER = new Item(new Item.Settings().maxCount(8));
	public static final Item PLANTFOOD_MUSHROOM = new Item(new Item.Settings().maxCount(8));
	public static final Item PLANTFOOD_TOUGH = new Item(new Item.Settings().maxCount(8));
	public static final Item PLANTFOOD_SHADOW = new Item(new Item.Settings().maxCount(8));


    public static final Item PEA = new PeaItem(new Item.Settings().maxCount(32).rarity(Rarity.COMMON));
    public static final Item SPORE = new SporeItem(new Item.Settings().maxCount(64).rarity(Rarity.COMMON));
    public static final Item FUME = new FumeItem(new Item.Settings().maxCount(32).rarity(Rarity.UNCOMMON));
	public static final Item BREEZE = new BreezeItem(new Item.Settings().maxCount(32).rarity(Rarity.UNCOMMON));
	public static final Item ACIDFUME = new AcidFumeItem(new Item.Settings().maxCount(32).rarity(Rarity.UNCOMMON));
	public static final Item PIERCEPEA = new PiercePeaItem(new Item.Settings().maxCount(16).rarity(Rarity.EPIC));
	public static final Item FIREPIERCEPEA = new FirePiercePeaItem(new Item.Settings().maxCount(16).rarity(Rarity.EPIC));
	public static final Item HYPNO = new HypnoItem(new Item.Settings().maxCount(32).rarity(Rarity.EPIC));
	public static final Item ACIDSPORE = new AcidSporeItem(new Item.Settings().maxCount(32).rarity(Rarity.UNCOMMON));
    public static final Item SNOWPEAPROJ = new SnowPeaItem(new Item.Settings().maxCount(32).rarity(Rarity.UNCOMMON));
    public static final Item FIREPEA = new FirepeaItem(new Item.Settings().maxCount(16).rarity(Rarity.RARE));
	public static final Item CABBAGE = new CabbageItem(new Item.Settings().maxCount(32).rarity(Rarity.COMMON));
	public static final Item ICEBERG = new IcebergItem(new Item.Settings().maxCount(16).rarity(Rarity.UNCOMMON));
	public static final Item SNOWQUEENPEAPROJ = new SnowqueenpeaItem(new Item.Settings().maxCount(16).rarity(Rarity.EPIC));
	public static final Item BOOMERANG = new BoomerangItem(new Item.Settings().maxCount(32).rarity(Rarity.UNCOMMON));
	public static final Item FRISBEE = new FrisbeeItem(new Item.Settings().maxCount(32).rarity(Rarity.UNCOMMON));
	public static final Item COCONUT = new CoconutItem(new Item.Settings().maxCount(16).rarity(Rarity.RARE));
	public static final Item PEPPER = new PepperItem(new Item.Settings().maxCount(32).rarity(Rarity.UNCOMMON));
	public static final Item PLASMAPEA = new PlasmapeaItem(new Item.Settings().maxCount(16).rarity(Rarity.EPIC));
	public static final Item ELECTRICPEA = new ElectricpeaItem(new Item.Settings().maxCount(16).rarity(Rarity.RARE));
	public static final Item SPIKE = new SpikeItem(new Item.Settings().maxCount(32).rarity(Rarity.COMMON));
	public static final Item POWERSPIKE = new ElecSpikeItem(new Item.Settings().maxCount(16).rarity(Rarity.RARE));
	public static final Item SPRINGHAIR = new SpringHairItem(new Item.Settings().maxCount(24).rarity(Rarity.UNCOMMON));
	public static final Item ICESPIKE = new IcespikeItem(new Item.Settings().maxCount(16).rarity(Rarity.EPIC));
	public static final Item POWERICESPIKE = new PowerIceSpikeItem(new Item.Settings().maxCount(8).rarity(Rarity.EPIC));
	public static final Item RAINBOWBULLET = new RainbowBulletItem(new Item.Settings().maxCount(32).rarity(Rarity.UNCOMMON));
	public static final Item BEESPIKE = new BeeSpikeItem(new Item.Settings().maxCount(16).rarity(Rarity.EPIC));
	public static final Item POWERBEESPIKE = new PowerBeeSpikeItem(new Item.Settings().maxCount(8).rarity(Rarity.EPIC));
	public static final Item PUMPKINPROJ = new PumpkinItem(new Item.Settings().maxCount(8).rarity(Rarity.RARE));
	public static final Item HYPNOPROJ = new HypnoprojItem(new Item.Settings().maxCount(16).rarity(Rarity.RARE));
	public static final Item CARDPROJ = new CardItem(new Item.Settings().maxCount(32).rarity(Rarity.RARE));
	public static final Item GOLDENCARDPROJ = new GoldenCardItem(new Item.Settings().maxCount(32).rarity(Rarity.RARE));
	public static final Item BUBBLES = new BubblesItem(new Item.Settings().maxCount(16).rarity(Rarity.COMMON));
	public static final Item ARMORBUBBLE = new ArmorBubbleItem(new Item.Settings().maxCount(8).rarity(Rarity.UNCOMMON));
	public static final Item DYEITEM = new DyeItem(new Item.Settings().maxCount(16).rarity(Rarity.RARE));
	public static final Item SMOOSH = new HammerItem(new Item.Settings().maxCount(8).rarity(Rarity.EPIC));
	public static final Item SPIT = new SpitItem(new Item.Settings().maxCount(32).rarity(Rarity.COMMON));
	public static final Item JINGLE = new JingleItem(new Item.Settings().maxCount(32).rarity(Rarity.COMMON));
	public static final Item SWORDPROJ = new SwordItem(new Item.Settings().maxCount(16).rarity(Rarity.UNCOMMON));
	public static final Item POWERSWORDPROJ = new PowerSwordItem(new Item.Settings().maxCount(8).rarity(Rarity.UNCOMMON));
	public static final Item PIERCESPORE = new PierceSporeItem(new Item.Settings().maxCount(16).rarity(Rarity.UNCOMMON));
	public static final Item PIERCESPORESHADOW = new PierceSporeShadowItem(new Item.Settings().maxCount(8).rarity(Rarity.RARE));
	public static final Item BARK = new BarkItem(new Item.Settings().maxCount(32).rarity(Rarity.COMMON));
	public static final Item BRAIN = new Item(new Item.Settings());
    public static final Item SUN = new Item(new Item.Settings());
    public static final Item SMALLSUN = new Item(new Item.Settings());
    public static final Item LARGESUN = new Item(new Item.Settings());

    //Plant Spawn
	public static final Item GARDENINGGLOVE = new GardeningGloves(new Item.Settings().maxCount(16).rarity(Rarity.RARE));
	public static final Item FERTILIZER = new Fertilizer(new Item.Settings().maxCount(64).rarity(Rarity.EPIC));
	public static final Item EMPTY_SEED_PACKET = new Item(new Item.Settings().maxCount(64).rarity(Rarity.COMMON));
    public static final Item SUNFLOWER_SEED_PACKET = new SunflowerSeeds(new Item.Settings().maxCount(8).rarity(Rarity.COMMON));
    public static final Item PEASHOOTER_SEED_PACKET = new PeashooterSeeds(new Item.Settings().maxCount(8).rarity(Rarity.COMMON));
    public static final Item CHERRYBOMB_SEED_PACKET = new CherryBombSeeds(new Item.Settings().maxCount(8).rarity(Rarity.UNCOMMON));
    public static final Item WALLNUT_SEED_PACKET = new WallnutSeeds(new Item.Settings().maxCount(8).rarity(Rarity.COMMON));
    public static final Item POTATOMINE_SEED_PACKET = new PotatoMineSeeds(new Item.Settings().maxCount(8).rarity(Rarity.COMMON));
    public static final Item SNOW_PEA_SEED_PACKET = new SnowpeaSeeds(new Item.Settings().maxCount(8).rarity(Rarity.UNCOMMON));
    public static final Item CHOMPER_SEED_PACKET = new ChomperSeeds(new Item.Settings().maxCount(8).rarity(Rarity.UNCOMMON));
    public static final Item REPEATER_SEED_PACKET = new RepeaterSeeds(new Item.Settings().maxCount(8).rarity(Rarity.UNCOMMON));
    public static final Item SUNSHROOM_SEED_PACKET = new SunshroomSeeds(new Item.Settings().maxCount(8).rarity(Rarity.COMMON));
    public static final Item PUFFSHROOM_SEED_PACKET = new PuffshroomSeeds(new Item.Settings().maxCount(8).rarity(Rarity.COMMON));
    public static final Item FUMESHROOM_SEED_PACKET = new FumeshroomSeeds(new Item.Settings().maxCount(8).rarity(Rarity.UNCOMMON));
    public static final Item GRAVEBUSTER_SEED_PACKET = new GraveBusterSeeds(new Item.Settings().maxCount(8).rarity(Rarity.COMMON));
    public static final Item HYPNOSHROOM_SEED_PACKET = new HypnoshroomSeeds(new Item.Settings().maxCount(8).rarity(Rarity.COMMON));
    public static final Item SCAREDYSHROOM_SEED_PACKET = new ScaredyshroomSeeds(new Item.Settings().maxCount(8).rarity(Rarity.COMMON));
    public static final Item ICESHROOM_SEED_PACKET = new IceshroomSeeds(new Item.Settings().maxCount(8).rarity(Rarity.UNCOMMON));
    public static final Item DOOMSHROOM_SEED_PACKET = new DoomshroomSeeds(new Item.Settings().maxCount(8).rarity(Rarity.RARE));
	public static final Item LILYPAD_SEED_PACKET = new LilyPadSeeds(new Item.Settings().maxCount(8).rarity(Rarity.COMMON));
	public static final Item SQUASH_SEED_PACKET = new SquashSeeds(new Item.Settings().maxCount(8).rarity(Rarity.COMMON));
	public static final Item THREEPEATER_SEED_PACKET = new ThreepeaterSeeds(new Item.Settings().maxCount(8).rarity(Rarity.RARE));
	public static final Item TANGLEKELP_SEED_PACKET = new TangleKelpSeeds(new Item.Settings().maxCount(8).rarity(Rarity.COMMON));
	public static final Item JALAPENO_SEED_PACKET = new JalapenoSeeds(new Item.Settings().maxCount(8).rarity(Rarity.UNCOMMON));
	public static final Item SPIKEWEED_SEED_PACKET = new SpikeweedSeeds(new Item.Settings().maxCount(8).rarity(Rarity.COMMON));
	public static final Item TORCHWOOD_SEED_PACKET = new TorchwoodSeeds(new Item.Settings().maxCount(8).rarity(Rarity.RARE));
	public static final Item TALLNUT_SEED_PACKET = new TallnutSeeds(new Item.Settings().maxCount(8).rarity(Rarity.UNCOMMON));
	public static final Item SEASHROOM_SEED_PACKET = new SeashroomSeeds(new Item.Settings().maxCount(8).rarity(Rarity.COMMON));
	public static final Item MAGNETSHROOM_SEED_PACKET = new MagnetshroomSeeds(new Item.Settings().maxCount(8).rarity(Rarity.UNCOMMON));
	public static final Item CABBAGEPULT_SEED_PACKET = new CabbagepultSeeds(new Item.Settings().maxCount(8).rarity(Rarity.COMMON));
	public static final Item COFFEEBEAN_SEED_PACKET = new CoffeeBeanSeeds(new Item.Settings().maxCount(8).rarity(Rarity.COMMON));
	public static final Item GATLINGPEA_SEED_PACKET = new GatlingpeaSeeds(new Item.Settings().maxCount(8).rarity(Rarity.EPIC));
	public static final Item TWINSUNFLOWER_SEED_PACKET = new TwinSunflowerSeeds(new Item.Settings().maxCount(8).rarity(Rarity.EPIC));
	public static final Item GLOOMSHROOM_SEED_PACKET = new GloomshroomSeeds(new Item.Settings().maxCount(8).rarity(Rarity.EPIC));
	public static final Item CATTAIL_SEED_PACKET = new CattailSeeds(new Item.Settings().maxCount(8).rarity(Rarity.EPIC));
	public static final Item SPIKEROCK_SEED_PACKET = new SpikerockSeeds(new Item.Settings().maxCount(8).rarity(Rarity.EPIC));
	public static final Item ICEBERGPULT_SEED_PACKET = new IcebergpultSeeds(new Item.Settings().maxCount(8).rarity(Rarity.UNCOMMON));
	public static final Item BEET_SEED_PACKET = new BeetSeeds(new Item.Settings().maxCount(8).rarity(Rarity.COMMON));
	public static final Item SHAMROCK_SEED_PACKET = new ShamrockSeeds(new Item.Settings().maxCount(8).rarity(Rarity.UNCOMMON));
	public static final Item CHILLYPEPPER_SEED_PACKET = new ChillyPepperSeeds(new Item.Settings().maxCount(8).rarity(Rarity.UNCOMMON));
	public static final Item BEESHOOTER_SEED_PACKET = new BeeshooterSeeds(new Item.Settings().maxCount(8).rarity(Rarity.EPIC));
	public static final Item SNOW_QUEENPEA_SEED_PACKET = new SnowqueenpeaSeeds(new Item.Settings().maxCount(8).rarity(Rarity.EPIC));
	public static final Item OXYGAE_SEED_PACKET = new OxygaeSeeds(new Item.Settings().maxCount(8).rarity(Rarity.UNCOMMON));
	public static final Item BREEZESHROOM_SEED_PACKET = new BreezeshroomSeeds(new Item.Settings().maxCount(8).rarity(Rarity.UNCOMMON));
	public static final Item BURSTSHROOM_SEED_PACKET = new BurstShroomSeeds(new Item.Settings().maxCount(8).rarity(Rarity.UNCOMMON));
	public static final Item SUPERCHOMPER_SEED_PACKET = new SuperChomperSeeds(new Item.Settings().maxCount(8).rarity(Rarity.RARE));
	public static final Item CHESTER_SEED_PACKET = new ChesterSeeds(new Item.Settings().maxCount(8).rarity(Rarity.EPIC));
	public static final Item VAMPIREFLOWER_SEED_PACKET = new VampireSunflowerSeeds(new Item.Settings().maxCount(8).rarity(Rarity.UNCOMMON));
	public static final Item RETROGATLING_SEED_PACKET = new RetroGatlingSeeds(new Item.Settings().maxCount(8).rarity(Rarity.EPIC));
	public static final Item ACIDSHROOM_SEED_PACKET = new AcidshroomSeeds(new Item.Settings().maxCount(8).rarity(Rarity.UNCOMMON));
	public static final Item DANDELIONWEED_SEED_PACKET = new DandelionWeedSeeds(new Item.Settings().maxCount(8).rarity(Rarity.UNCOMMON));
	public static final Item PERFOOMSHROOM_SEED_PACKET = new PerfoomshroomSeeds(new Item.Settings().maxCount(8).rarity(Rarity.EPIC));
	public static final Item BLOOMERANG_SEED_PACKET = new BloomerangSeeds(new Item.Settings().maxCount(8).rarity(Rarity.EPIC));
	public static final Item ICEBERGLETTUCE_SEED_PACKET = new IcebergLettuceSeeds(new Item.Settings().maxCount(8).rarity(Rarity.COMMON));
	public static final Item SPRINGBEAN_SEED_PACKET = new SpringbeanSeeds(new Item.Settings().maxCount(8).rarity(Rarity.COMMON));
	public static final Item COCONUTCANNON_SEED_PACKET = new CoconutCannonSeeds(new Item.Settings().maxCount(8).rarity(Rarity.RARE));
	public static final Item LIGHTNINGREED_SEED_PACKET = new LightningReedSeeds(new Item.Settings().maxCount(8).rarity(Rarity.COMMON));
	public static final Item PEAPOD_SEED_PACKET = new PeaPodSeeds(new Item.Settings().maxCount(8).rarity(Rarity.UNCOMMON));
	public static final Item EMPEACH_SEED_PACKET = new EMPeachSeeds(new Item.Settings().maxCount(8).rarity(Rarity.UNCOMMON));
	public static final Item PEPPERPULT_SEED_PACKET = new PepperpultSeeds(new Item.Settings().maxCount(8).rarity(Rarity.UNCOMMON));
	public static final Item FIRE_PEA_SEED_PACKET = new FirepeaSeeds(new Item.Settings().maxCount(8).rarity(Rarity.EPIC));
	public static final Item ENDURIAN_SEED_PACKET = new EndurianSeeds(new Item.Settings().maxCount(8).rarity(Rarity.UNCOMMON));
	public static final Item GOLDLEAF_SEED_PACKET = new GoldLeafSeeds(new Item.Settings().maxCount(8).rarity(Rarity.UNCOMMON));
	public static final Item SHADOWSHROOM_SEED_PACKET = new ShadowShroomSeeds(new Item.Settings().maxCount(8).rarity(Rarity.COMMON));
	public static final Item MISSILETOE_SEED_PACKET = new MissileToeSeeds(new Item.Settings().maxCount(8).rarity(Rarity.EPIC));
	public static final Item ELECTROPEA_SEED_PACKET = new ElectropeaSeeds(new Item.Settings().maxCount(8).rarity(Rarity.EPIC));
	public static final Item PEANUT_SEED_PACKET = new PeanutSeeds(new Item.Settings().maxCount(8).rarity(Rarity.UNCOMMON));
	public static final Item GHOSTPEPPER_SEED_PACKET = new GhostPepperSeeds(new Item.Settings().maxCount(8).rarity(Rarity.UNCOMMON));
	public static final Item OLIVEPIT_SEED_PACKET = new OlivePitSeeds(new Item.Settings().maxCount(8).rarity(Rarity.UNCOMMON));
	public static final Item GLOOMVINE_SEED_PACKET = new GloomVineSeeds(new Item.Settings().maxCount(8).rarity(Rarity.UNCOMMON));
	public static final Item HEAVENLYPEACH_SEED_PACKET = new HeavenlyPeachSeeds(new Item.Settings().maxCount(8).rarity(Rarity.UNCOMMON));
	public static final Item MAGICSHROOM_SEED_PACKET = new MagicshroomSeeds(new Item.Settings().maxCount(8).rarity(Rarity.RARE));
	public static final Item SAUCER_SEED_PACKET = new SaucerSeeds(new Item.Settings().maxCount(8).rarity(Rarity.UNCOMMON));
	public static final Item SPRINGPRINCESS_SEED_PACKET = new SpringPrincessSeeds(new Item.Settings().maxCount(8).rarity(Rarity.UNCOMMON));
	public static final Item DRIPPHYLLEIA_SEED_PACKET = new DripphylleiaSeeds(new Item.Settings().maxCount(8).rarity(Rarity.UNCOMMON));
	public static final Item PUMPKINWITCH_SEED_PACKET = new PumpkinWitchSeeds(new Item.Settings().maxCount(8).rarity(Rarity.RARE));
	public static final Item TULIMPETER_SEED_PACKET = new TulimpeterSeeds(new Item.Settings().maxCount(8).rarity(Rarity.RARE));
	public static final Item NARCISSUS_SEED_PACKET = new NarcissusSeeds(new Item.Settings().maxCount(8).rarity(Rarity.UNCOMMON));
	public static final Item IMPATYENS_SEED_PACKET = new ImpatyensSeeds(new Item.Settings().maxCount(8).rarity(Rarity.RARE));
	public static final Item HAMMERFLOWER_SEED_PACKET = new HammerFlowerSeeds(new Item.Settings().maxCount(8).rarity(Rarity.UNCOMMON));
	public static final Item METEORHAMMER_SEED_PACKET = new MeteorHammerSeeds(new Item.Settings().maxCount(8).rarity(Rarity.UNCOMMON));
	public static final Item FRISBLOOM_SEED_PACKET = new FrisbloomSeeds(new Item.Settings().maxCount(8).rarity(Rarity.UNCOMMON));
	public static final Item BEAUTYSHROOM_SEED_PACKET = new BeautyshroomSeeds(new Item.Settings().maxCount(8).rarity(Rarity.UNCOMMON));
	public static final Item CHARMSHROOM_SEED_PACKET = new CharmshroomSeeds(new Item.Settings().maxCount(8).rarity(Rarity.RARE));
	public static final Item MAGNETOSHROOM_SEED_PACKET = new MagnetoShroomSeeds(new Item.Settings().maxCount(8).rarity(Rarity.EPIC));
	public static final Item SMACKADAMIA_SEED_PACKET = new SmackadamiaSeeds(new Item.Settings().maxCount(8).rarity(Rarity.UNCOMMON));
	public static final Item BOMBSEEDLING_SEED_PACKET = new BombSeedlingSeeds(new Item.Settings().maxCount(8).rarity(Rarity.COMMON));
	public static final Item OILYOLIVE_SEED_PACKET = new OilyOliveSeeds(new Item.Settings().maxCount(8).rarity(Rarity.COMMON));
	public static final Item ZAPRICOT_SEED_PACKET = new ZapricotSeeds(new Item.Settings().maxCount(8).rarity(Rarity.COMMON));
	public static final Item BANANASAURUS_SEED_PACKET = new BananasaurusSeeds(new Item.Settings().maxCount(8).rarity(Rarity.EPIC));
	public static final Item WEENIEBEANIE_SEED_PACKET = new WeenieBeanieSeeds(new Item.Settings().maxCount(8).rarity(Rarity.COMMON));
	public static final Item SMOOSHROOM_SEED_PACKET = new SmooshroomSeeds(new Item.Settings().maxCount(8).rarity(Rarity.EPIC));
	public static final Item ADMIRALNAVYBEAN_SEED_PACKET = new AdmiralNavyBeanSeeds(new Item.Settings().maxCount(8).rarity(Rarity.UNCOMMON));
	public static final Item NAVYBEAN_SEED_PACKET = new NavyBeanSeeds(new Item.Settings().maxCount(8).rarity(Rarity.COMMON));
	public static final Item JUMPINGBEAN_SEED_PACKET = new JumpingbeanSeeds(new Item.Settings().maxCount(8).rarity(Rarity.RARE));
	public static final Item SUNFLOWERSEED_SEED_PACKET = new SunflowerSeedSeeds(new Item.Settings().maxCount(8).rarity(Rarity.COMMON));
	public static final Item BELLFLOWER_SEED_PACKET = new BellflowerSeeds(new Item.Settings().maxCount(8).rarity(Rarity.COMMON));
	public static final Item KNIGHTPEA_SEED_PACKET = new KnightPeaSeeds(new Item.Settings().maxCount(8).rarity(Rarity.EPIC));
	public static final Item NIGHTCAP_SEED_PACKET = new NightcapSeeds(new Item.Settings().maxCount(8).rarity(Rarity.EPIC));
	public static final Item DOOMROSE_SEED_PACKET = new DoomRoseSeeds(new Item.Settings().maxCount(8).rarity(Rarity.EPIC));
	public static final Item DOGWOOD_SEED_PACKET = new DogwoodSeeds(new Item.Settings().maxCount(8).rarity(Rarity.UNCOMMON));
	public static final Item GAMBLESHROOM_SEED_PACKET = new GambleshroomSeeds(new Item.Settings().maxCount(8).rarity(Rarity.EPIC));
	public static final Item GARDEN_SPAWN = new GardenSpawn(new Item.Settings().rarity(Rarity.EPIC));
	public static final Item GARDENCHALLENGE_SPAWN = new GardenChallengeSpawn(new Item.Settings().rarity(Rarity.EPIC));
	public static final Item MISSILETOE_TARGET = new MissileToeTargetItem(new Item.Settings().rarity(Rarity.EPIC).maxCount(1));

    //Zombie Spawn
    public static final Item BROWNCOATEGG = new BrowncoatEgg(new Item.Settings().rarity(Rarity.COMMON));
    public static final Item FLAGZOMBIEEGG = new FlagzombieEgg(new Item.Settings().rarity(Rarity.RARE));
    public static final Item CONEHEADEGG = new ConeheadEgg(new Item.Settings().rarity(Rarity.UNCOMMON));
    public static final Item POLEVAULTINGEGG = new PoleVaultingEgg(new Item.Settings().rarity(Rarity.UNCOMMON));
    public static final Item BUCKETHEADEGG = new BucketheadEgg(new Item.Settings().rarity(Rarity.UNCOMMON));
    public static final Item SCREENDOOREGG = new ScreendoorEgg(new Item.Settings().rarity(Rarity.UNCOMMON));
    public static final Item NEWSPAPEREGG = new NewspaperEgg(new Item.Settings().rarity(Rarity.UNCOMMON));
    public static final Item FOOTBALLEGG = new FootballEgg(new Item.Settings().rarity(Rarity.RARE));
    public static final Item DANCINGZOMBIEEGG = new DancingZombieEgg(new Item.Settings().rarity(Rarity.RARE));
    public static final Item BACKUPDANCEREGG = new BackupDancerEgg(new Item.Settings().rarity(Rarity.UNCOMMON));
	public static final Item SNORKELEGG = new SnorkelEgg(new Item.Settings().rarity(Rarity.UNCOMMON));
	public static final Item DOLPHINRIDEREGG = new DolphinRiderEgg(new Item.Settings().rarity(Rarity.RARE));
	public static final Item ZOMBONIEGG = new ZomboniEgg(new Item.Settings().rarity(Rarity.RARE));
	public static final Item BOBSLEDEGG = new BobsledEgg(new Item.Settings().rarity(Rarity.RARE));
	public static final Item GARGANTUAREGG = new GargantuarEgg(new Item.Settings().rarity(Rarity.EPIC));
	public static final Item MUMMYGARGANTUAREGG = new MummyGargantuarEgg(new Item.Settings().rarity(Rarity.EPIC));
	public static final Item UNICORNGARGANTUAREGG = new UnicornGargantuarEgg(new Item.Settings().rarity(Rarity.EPIC));
	public static final Item IMPEGG = new ImpEgg(new Item.Settings().rarity(Rarity.RARE));
	public static final Item MUMMYIMPEGG = new MummyImpEgg(new Item.Settings().rarity(Rarity.RARE));
	public static final Item SCRAPMECHEGG = new ScrapMechEgg(new Item.Settings().rarity(Rarity.EPIC));
	public static final Item SCRAPIMPEGG = new ScrapImpEgg(new Item.Settings().rarity(Rarity.RARE));
	public static final Item BASSIMPEGG = new RainbowBassImpEgg(new Item.Settings().rarity(Rarity.RARE));
	public static final Item TRASHCANEGG = new TrashcanEgg(new Item.Settings().rarity(Rarity.RARE));
	public static final Item BERSERKEREGG = new BerserkerEgg(new Item.Settings().rarity(Rarity.EPIC));
	public static final Item MUMMYEGG = new MummyEgg(new Item.Settings().rarity(Rarity.COMMON));
	public static final Item FLAGMUMMYEGG = new FlagMummyEgg(new Item.Settings().rarity(Rarity.RARE));
	public static final Item MUMMYCONEEGG = new MummyConeEgg(new Item.Settings().rarity(Rarity.UNCOMMON));
	public static final Item MUMMYBUCKETEGG = new MummyBucketEgg(new Item.Settings().rarity(Rarity.UNCOMMON));
	public static final Item EXPLOREREGG = new ExplorerEgg(new Item.Settings().rarity(Rarity.UNCOMMON));
	public static final Item TOMBRAISEREGG = new TombRaiserEgg(new Item.Settings().rarity(Rarity.RARE));
	public static final Item TORCHLIGHTEGG = new TorchlightEgg(new Item.Settings().rarity(Rarity.RARE));
	public static final Item PYRAMIDHEADEGG = new PyramidHeadEgg(new Item.Settings().rarity(Rarity.RARE));
	public static final Item UNDYINGEGG = new UndyingEgg(new Item.Settings().rarity(Rarity.EPIC));
	public static final Item PHARAOHEGG = new PharaohEgg(new Item.Settings().rarity(Rarity.RARE));
	public static final Item PEASANTEGG = new PeasantEgg(new Item.Settings().rarity(Rarity.COMMON));
	public static final Item FLAGPEASANTEGG = new FlagPeasantEgg(new Item.Settings().rarity(Rarity.RARE));
	public static final Item PEASANTCONEEGG = new PeasantConeEgg(new Item.Settings().rarity(Rarity.UNCOMMON));
	public static final Item PEASANTBUCKETEGG = new PeasantBucketEgg(new Item.Settings().rarity(Rarity.UNCOMMON));
	public static final Item PEASANTKNIGHTEGG = new PeasantKnightEgg(new Item.Settings().rarity(Rarity.RARE));
	public static final Item FUTUREZOMBIEEGG = new FutureZombieEgg(new Item.Settings().rarity(Rarity.COMMON));
	public static final Item FLAGFUTUREEGG = new FlagFutureEgg(new Item.Settings().rarity(Rarity.RARE));
	public static final Item FUTURECONEEGG = new FutureConeEgg(new Item.Settings().rarity(Rarity.UNCOMMON));
	public static final Item FUTUREBUCKETEGG = new FutureBucketEgg(new Item.Settings().rarity(Rarity.UNCOMMON));
	public static final Item HOLOHEADEGG = new HoloHeadEgg(new Item.Settings().rarity(Rarity.RARE));
	public static final Item ANNOUNCERIMPEGG = new AnnouncerImpEgg(new Item.Settings().rarity(Rarity.UNCOMMON));
	public static final Item REDANNOUNCERIMPEGG = new RedAnnouncerImpEgg(new Item.Settings().rarity(Rarity.UNCOMMON));
	public static final Item BLACKANNOUNCERIMPEGG = new BlackAnnouncerImpEgg(new Item.Settings().rarity(Rarity.UNCOMMON));
	public static final Item ZOMBIEKINGEGG = new ZombieKingEgg(new Item.Settings().rarity(Rarity.RARE));
	public static final Item REDZOMBIEKINGEGG = new RedZombieKingEgg(new Item.Settings().rarity(Rarity.RARE));
	public static final Item BLACKZOMBIEKINGEGG = new BlackZombieKingEgg(new Item.Settings().rarity(Rarity.RARE));
	public static final Item IMPDRAGONEGG = new ImpDragonEgg(new Item.Settings().rarity(Rarity.RARE));
	public static final Item JETPACKEGG = new JetpackEgg(new Item.Settings().rarity(Rarity.UNCOMMON));
	public static final Item ROBOCONEEGG = new RoboConeEgg(new Item.Settings().rarity(Rarity.UNCOMMON));
	public static final Item BLASTRONAUTEGG = new BlastronautEgg(new Item.Settings().rarity(Rarity.RARE));
	public static final Item OCTOEGG = new OctoEgg(new Item.Settings().rarity(Rarity.RARE));
	public static final Item BRICKHEADEGG = new BrickheadEgg(new Item.Settings().rarity(Rarity.RARE));
	public static final Item SUNDAYEDITIONEGG = new SundayEditionEgg(new Item.Settings().rarity(Rarity.EPIC));
	public static final Item SUPERFANIMPEGG = new SuperFanImpEgg(new Item.Settings().rarity(Rarity.RARE));
	public static final Item SUMMERBASICEGG = new SummerBasicEgg(new Item.Settings().rarity(Rarity.COMMON));
	public static final Item FLAGSUMMEREGG = new FlagSummerEgg(new Item.Settings().rarity(Rarity.RARE));
	public static final Item SUMMERCONEEGG = new SummerConeEgg(new Item.Settings().rarity(Rarity.UNCOMMON));
	public static final Item SUMMERBUCKETEGG = new SummerBucketEgg(new Item.Settings().rarity(Rarity.UNCOMMON));
	public static final Item BASSEGG = new BassEgg(new Item.Settings().rarity(Rarity.EPIC));
	public static final Item PUMPKINZOMBIEEGG = new PumpkinZombieEgg(new Item.Settings().rarity(Rarity.RARE));
	public static final Item NEWYEARIMPEGG = new NewYearImpEgg(new Item.Settings().rarity(Rarity.EPIC));
	public static final Item POKEREGG = new PokerZombieEgg(new Item.Settings().rarity(Rarity.COMMON));
	public static final Item FLAGPOKEREGG = new FlagPokerEgg(new Item.Settings().rarity(Rarity.RARE));
	public static final Item POKERCONEEGG = new PokerConeheadZombieEgg(new Item.Settings().rarity(Rarity.UNCOMMON));
	public static final Item POKERBUCKETEGG = new PokerBucketheadZombieEgg(new Item.Settings().rarity(Rarity.UNCOMMON));
	public static final Item POKERPAWNEGG = new PokerPawnZombieEgg(new Item.Settings().rarity(Rarity.RARE));
	public static final Item POKERKNIGHTEGG = new PokerKnightZombieEgg(new Item.Settings().rarity(Rarity.RARE));
	public static final Item POKERTOWEREGG = new PokerTowerZombieEgg(new Item.Settings().rarity(Rarity.RARE));
	public static final Item POKERBISHOPEGG = new PokerBishopZombieEgg(new Item.Settings().rarity(Rarity.RARE));
	public static final Item SARGEANTEGG = new SargeantEgg(new Item.Settings().rarity(Rarity.COMMON));
	public static final Item FLAGSARGEANTEGG = new FlagSargeantEgg(new Item.Settings().rarity(Rarity.RARE));
	public static final Item SARGEANTCONEEGG = new SargeantConeEgg(new Item.Settings().rarity(Rarity.UNCOMMON));
	public static final Item SARGEANTBUCKETEGG = new SargeantBucketEgg(new Item.Settings().rarity(Rarity.UNCOMMON));
	public static final Item SARGEANTSHIELDEGG = new SargeantShieldEgg(new Item.Settings().rarity(Rarity.RARE));
	public static final Item BOOKBURNEREGG = new BookBurnerEgg(new Item.Settings().rarity(Rarity.UNCOMMON));
	public static final Item PIGGYEGG = new ZombiePiggyEgg(new Item.Settings().rarity(Rarity.UNCOMMON));
	public static final Item HAWKEREGG = new HawkerCartEgg(new Item.Settings().rarity(Rarity.UNCOMMON));
	public static final Item SOLDIEREGG = new SoldierEgg(new Item.Settings().rarity(Rarity.RARE));
	public static final Item SCIENTISTEGG = new ScientistEgg(new Item.Settings().rarity(Rarity.RARE));
	public static final Item HOVERGOATEGG = new HovergoatEgg(new Item.Settings().rarity(Rarity.UNCOMMON));
	public static final Item ZOMBLOBEGG = new ZomblobEgg(new Item.Settings().rarity(Rarity.RARE));
	public static final Item DEFENSIVEENDEGG = new DefensiveEndEgg(new Item.Settings().rarity(Rarity.EPIC));
	public static final Item CURSEDGARGOLITHEGG = new CursedGargolithEgg(new Item.Settings().rarity(Rarity.EPIC));
	public static final Item IMPTHROWEGG = new ImpThrowEgg(new Item.Settings().rarity(Rarity.RARE));
	public static final Item ACTIONHEROEGG = new ActionheroEgg(new Item.Settings().rarity(Rarity.RARE));
	public static final Item BULLYEGG = new BullyEgg(new Item.Settings().rarity(Rarity.UNCOMMON));
	public static final Item BASKETBALLCARRIEREGG = new BasketballCarrierEgg(new Item.Settings().rarity(Rarity.RARE));
	public static final Item PUMPKINCARRIAGEEGG = new PumpkinCarEgg(new Item.Settings().rarity(Rarity.RARE));
	public static final Item CRYSTALSHOEIMPEGG = new CinderellaImpEgg(new Item.Settings().rarity(Rarity.RARE));
	public static final Item LOCUSTSWARMEGG = new LocustswarmEgg(new Item.Settings().rarity(Rarity.EPIC));

    //Grave Spawn
	public static final Item EASY = new Item(new Item.Settings().rarity(Rarity.EPIC).maxCount(1));
	public static final Item EASYMED = new Item(new Item.Settings().rarity(Rarity.EPIC).maxCount(1));
	public static final Item MED = new Item(new Item.Settings().rarity(Rarity.EPIC).maxCount(1));
	public static final Item MEDHARD = new Item(new Item.Settings().rarity(Rarity.EPIC).maxCount(1));
	public static final Item HARD = new Item(new Item.Settings().rarity(Rarity.EPIC).maxCount(1));
	public static final Item SUPERHARD = new Item(new Item.Settings().rarity(Rarity.EPIC).maxCount(1));
	public static final Item NIGHTMARE = new Item(new Item.Settings().rarity(Rarity.EPIC).maxCount(1));
	public static final Item CRAAAAZY = new Item(new Item.Settings().rarity(Rarity.EPIC).maxCount(1));
	public static final Item ONEBYONE = new Item(new Item.Settings().rarity(Rarity.EPIC).maxCount(1));
	public static final Item HALF = new Item(new Item.Settings().rarity(Rarity.EPIC).maxCount(1));
	public static final Item INFINITE = new Item(new Item.Settings().rarity(Rarity.EPIC).maxCount(1));
	public static final Item UNLOCK = new Item(new Item.Settings().rarity(Rarity.EPIC).maxCount(1));

	public static final Item DAY = new Item(new Item.Settings().rarity(Rarity.EPIC).maxCount(1));
	public static final Item NIGHT = new Item(new Item.Settings().rarity(Rarity.EPIC).maxCount(1));
	public static final Item BOMB = new Item(new Item.Settings().rarity(Rarity.EPIC).maxCount(1));
	public static final Item DROUGHT = new Item(new Item.Settings().rarity(Rarity.EPIC).maxCount(1));

	public static final Item CLEAR = new Item(new Item.Settings().rarity(Rarity.EPIC).maxCount(1));
	public static final Item RAIN = new Item(new Item.Settings().rarity(Rarity.EPIC).maxCount(1));
	public static final Item THUNDER = new Item(new Item.Settings().rarity(Rarity.EPIC).maxCount(1));
	public static final Item UNLOCKSPECIAL = new Item(new Item.Settings().rarity(Rarity.EPIC).maxCount(1));
	public static final Item ZOMBIEGRAVESPAWN = new ZombieGraveSpawn(new Item.Settings().rarity(Rarity.COMMON).maxCount(8));
    public static final Item BASICGRAVESPAWN = new BasicGraveSpawn(new Item.Settings().rarity(Rarity.COMMON));
    public static final Item NIGHTGRAVESPAWN = new NightGraveSpawn(new Item.Settings().rarity(Rarity.UNCOMMON));
	public static final Item POOLGRAVESPAWN = new PoolGraveSpawn(new Item.Settings().rarity(Rarity.RARE));
	public static final Item ROOFGRAVESPAWN = new RoofGraveSpawn(new Item.Settings().rarity(Rarity.EPIC));
	public static final Item EGYPTGRAVESPAWN = new EgyptGraveSpawn(new Item.Settings().rarity(Rarity.UNCOMMON));
	public static final Item FUTUREGRAVESPAWN = new FutureGraveSpawn(new Item.Settings().rarity(Rarity.EPIC));
	public static final Item DARKAGESGRAVESPAWN = new DarkAgesGraveSpawn(new Item.Settings().rarity(Rarity.UNCOMMON));
	public static final Item FAIRYTALEGRAVESPAWN = new FairyTaleGraveSpawn(new Item.Settings().rarity(Rarity.EPIC));
	public static final Item MAUSOLEUMGRAVESPAWN = new MausoleumGraveSpawn(new Item.Settings().rarity(Rarity.EPIC));

    //Blocks

	public static final BlockItem BOTANY_STATION = new BlockItem(ModBlocks.BOTANY_STATION, new Item.Settings());
    public static final BlockItem GRASS_TILE = new BlockItem(ModBlocks.GRASS_TILE, new Item.Settings());
    public static final BlockItem DARK_GRASS_TILE = new BlockItem(ModBlocks.DARK_GRASS_TILE, new Item.Settings());
	public static final BlockItem NIGHT_TILE = new BlockItem(ModBlocks.NIGHT_TILE, new Item.Settings());
	public static final BlockItem DARK_NIGHT_TILE = new BlockItem(ModBlocks.DARK_NIGHT_TILE, new Item.Settings());
	public static final BlockItem ROOF_TILE = new BlockItem(ModBlocks.ROOF_TILE, new Item.Settings());
	public static final BlockItem DARK_ROOF_TILE = new BlockItem(ModBlocks.DARK_ROOF_TILE, new Item.Settings());
	public static final BlockItem UPGRADE_TILE = new BlockItem(ModBlocks.UPGRADE_TILE, new Item.Settings());
	public static final BlockItem PREMIUM_TILE = new BlockItem(ModBlocks.PREMIUM_TILE, new Item.Settings());
	public static final BlockItem EGYPT_TILE = new BlockItem(ModBlocks.EGYPT_TILE, new Item.Settings());
	public static final BlockItem DARK_EGYPT_TILE = new BlockItem(ModBlocks.DARK_EGYPT_TILE, new Item.Settings());
	public static final BlockItem PIRATE_TILE = new BlockItem(ModBlocks.PIRATE_TILE, new Item.Settings());
	public static final BlockItem DARK_PIRATE_TILE = new BlockItem(ModBlocks.DARK_PIRATE_TILE, new Item.Settings());
	public static final BlockItem WEST_TILE = new BlockItem(ModBlocks.WEST_TILE, new Item.Settings());
	public static final BlockItem DARK_WEST_TILE = new BlockItem(ModBlocks.DARK_WEST_TILE, new Item.Settings());
	public static final BlockItem FUTURE_TILE = new BlockItem(ModBlocks.FUTURE_TILE, new Item.Settings());
	public static final BlockItem DARK_FUTURE_TILE = new BlockItem(ModBlocks.DARK_FUTURE_TILE, new Item.Settings());
	public static final BlockItem DARKAGES_TILE = new BlockItem(ModBlocks.DARKAGES_TILE, new Item.Settings());
	public static final BlockItem DARK_DARKAGES_TILE = new BlockItem(ModBlocks.DARK_DARKAGES_TILE, new Item.Settings());
	public static final BlockItem SAND_TILE = new BlockItem(ModBlocks.SAND_TILE, new Item.Settings());
	public static final BlockItem DARK_SAND_TILE = new BlockItem(ModBlocks.DARK_SAND_TILE, new Item.Settings());
	public static final BlockItem UNDERWATER_TILE = new BlockItem(ModBlocks.UNDERWATER_TILE, new Item.Settings());
	public static final BlockItem DARK_UNDERWATER_TILE = new BlockItem(ModBlocks.DARK_UNDERWATER_TILE, new Item.Settings());
	public static final BlockItem FROST_TILE = new BlockItem(ModBlocks.FROST_TILE, new Item.Settings());
	public static final BlockItem DARK_FROST_TILE = new BlockItem(ModBlocks.DARK_FROST_TILE, new Item.Settings());
	public static final BlockItem LOSTCITY_TILE = new BlockItem(ModBlocks.LOSTCITY_TILE, new Item.Settings());
	public static final BlockItem DARK_LOSTCITY_TILE = new BlockItem(ModBlocks.DARK_LOSTCITY_TILE, new Item.Settings());
	public static final BlockItem SKYCITY_TILE = new BlockItem(ModBlocks.SKYCITY_TILE, new Item.Settings());
	public static final BlockItem DARK_SKYCITY_TILE = new BlockItem(ModBlocks.DARK_SKYCITY_TILE, new Item.Settings());
	public static final BlockItem FAIRY_TILE = new BlockItem(ModBlocks.FAIRY_TILE, new Item.Settings());
	public static final BlockItem DARK_FAIRY_TILE = new BlockItem(ModBlocks.DARK_FAIRY_TILE, new Item.Settings());
	public static final BlockItem MAUSOLEUM_TILE = new BlockItem(ModBlocks.MAUSOLEUM_TILE, new Item.Settings());
	public static final BlockItem DARK_MAUSOLEUM_TILE = new BlockItem(ModBlocks.DARK_MAUSOLEUM_TILE, new Item.Settings());
	public static final BlockItem LEGENDARY_TILE = new BlockItem(ModBlocks.LEGENDARY_TILE, new Item.Settings());

    //Tools
    public static ToolItem DAVES_SHOVEL = new PlantKillingShovel(PlantKillingMaterial.INSTANCE, 2, 0F, new Item.Settings().rarity(Rarity.EPIC));

	public static List<Item> SEED_PACKET_LIST = new ArrayList<>();
	public static List<Item> NIGHT_SEED_LIST = new ArrayList<>();
	public static List<Item> POOL_SEED_LIST = new ArrayList<>();
	public static List<Item> FOG_SEED_LIST = new ArrayList<>();
	public static List<Item> ROOF_SEED_LIST = new ArrayList<>();
	public static List<Item> EGYPT_SEED_LIST = new ArrayList<>();
	public static List<Item> PIRATE_SEED_LIST = new ArrayList<>();
	public static List<Item> WILDWEST_SEED_LIST = new ArrayList<>();
	public static List<Item> FUTURE_SEED_LIST = new ArrayList<>();
	public static List<Item> DARKAGES_SEED_LIST = new ArrayList<>();
	public static List<Item> BEACH_SEED_LIST = new ArrayList<>();
	public static List<Item> FROSTBITE_SEED_LIST = new ArrayList<>();
	public static List<Item> LOSTCITY_SEED_LIST = new ArrayList<>();
	public static List<Item> MIXTAPE_SEED_LIST = new ArrayList<>();
	public static List<Item> JURASSIC_SEED_LIST = new ArrayList<>();
	public static List<Item> SKYCITY_SEED_LIST = new ArrayList<>();
	public static List<Item> FAIRYTALE_SEED_LIST = new ArrayList<>();
	public static List<Item> MAUSOLEUM_SEED_LIST = new ArrayList<>();

	public static List<Item> PREMIUM_SEED_LIST = new ArrayList<>();
	public static List<Item> LEGENDARY_SEED_LIST = new ArrayList<>();
	public static List<Item> PLANTFOOD_LIST = new ArrayList<>();

    //addItem
    //addBlock
    public static void registerItems() {
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"botany_station"), BOTANY_STATION);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"empty_seed_packet"), EMPTY_SEED_PACKET);
        Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"icon_almanac"), ALMANAC);
        Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"plantfood"), PLANTFOOD);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"plantfood_air"), PLANTFOOD_AIR);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"plantfood_aquatic"), PLANTFOOD_AQUATIC);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"plantfood_cold"), PLANTFOOD_COLD);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"plantfood_elec"), PLANTFOOD_ELEC);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"plantfood_fire"), PLANTFOOD_FIRE);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"plantfood_flower"), PLANTFOOD_FLOWER);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"plantfood_mushroom"), PLANTFOOD_MUSHROOM);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"plantfood_tough"), PLANTFOOD_TOUGH);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"plantfood_shadow"), PLANTFOOD_SHADOW);
        Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"pea"), PEA);
        Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"spore"), SPORE);
        Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"fume"), FUME);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"breeze"), BREEZE);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"piercepea"), PIERCEPEA);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"firepiercepea"), FIREPIERCEPEA);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"acidfume"), ACIDFUME);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"hypno"), HYPNO);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"acidspore"), ACIDSPORE);
        Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"snowpeaproj"), SNOWPEAPROJ);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"firepea"), FIREPEA);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"cabbage"), CABBAGE);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"iceberg"), ICEBERG);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"snowqueenpeaproj"), SNOWQUEENPEAPROJ);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"boomerangproj"), BOOMERANG);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"frisbee"), FRISBEE);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"coconut"), COCONUT);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"pepper"), PEPPER);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"plasmapea"), PLASMAPEA);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"electricpea"), ELECTRICPEA);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"rainbowbullet"), RAINBOWBULLET);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"beespike"), BEESPIKE);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"spike"), SPIKE);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"icespike"), ICESPIKE);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"powerbeespike"), POWERBEESPIKE);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"elecspike"), POWERSPIKE);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"springhair"), SPRINGHAIR);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"powericespike"), POWERICESPIKE);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"cardproj"), CARDPROJ);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"goldencardproj"), GOLDENCARDPROJ);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"hypnoproj"), HYPNOPROJ);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"pumpkinproj"), PUMPKINPROJ);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"bubblesitem"), BUBBLES);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"armorbubbleitem"), ARMORBUBBLE);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"dyeitem"), DYEITEM);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"hammer"), SMOOSH);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"spit"), SPIT);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"jingle"), JINGLE);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"swordproj"), SWORDPROJ);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"powerswordproj"), POWERSWORDPROJ);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"piercespore"), PIERCESPORE);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"piercespore_shadow"), PIERCESPORESHADOW);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"bark"), BARK);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"brain"), BRAIN);
        Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"sun"), SUN);
        Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"smallsun"), SMALLSUN);
        Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"largesun"), LARGESUN);
        Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"grass_tile"), GRASS_TILE);
        Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"dark_grass_tile"), DARK_GRASS_TILE);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"roof_tile"), ROOF_TILE);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"dark_roof_tile"), DARK_ROOF_TILE);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"upgrade_tile"), UPGRADE_TILE);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"premium_tile"), PREMIUM_TILE);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"egypt_tile"), EGYPT_TILE);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"dark_egypt_tile"), DARK_EGYPT_TILE);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"pirate_tile"), PIRATE_TILE);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"dark_pirate_tile"), DARK_PIRATE_TILE);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"west_tile"), WEST_TILE);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"dark_west_tile"), DARK_WEST_TILE);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"future_tile"), FUTURE_TILE);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"dark_future_tile"), DARK_FUTURE_TILE);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"night_tile"), NIGHT_TILE);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"dark_night_tile"), DARK_NIGHT_TILE);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"darkages_tile"), DARKAGES_TILE);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"dark_darkages_tile"), DARK_DARKAGES_TILE);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"sand_tile"), SAND_TILE);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"dark_sand_tile"), DARK_SAND_TILE);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"underwater_tile"), UNDERWATER_TILE);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"dark_underwater_tile"), DARK_UNDERWATER_TILE);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"frost_tile"), FROST_TILE);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"dark_frost_tile"), DARK_FROST_TILE);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"lostcity_tile"), LOSTCITY_TILE);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"dark_lostcity_tile"), DARK_LOSTCITY_TILE);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"skycity_tile"), SKYCITY_TILE);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"dark_skycity_tile"), DARK_SKYCITY_TILE);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"fairy_tile"), FAIRY_TILE);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"dark_fairy_tile"), DARK_FAIRY_TILE);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"mausoleum_tile"), MAUSOLEUM_TILE);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"dark_mausoleum_tile"), DARK_MAUSOLEUM_TILE);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"legendary_tile"), LEGENDARY_TILE);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"gardeningglove"), GARDENINGGLOVE);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"fertilizer"), FERTILIZER);
        Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"sunflower_seed_packet"), SUNFLOWER_SEED_PACKET);
        Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"peashooter_seed_packet"), PEASHOOTER_SEED_PACKET);
        Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"cherrybomb_seed_packet"), CHERRYBOMB_SEED_PACKET);
        Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"wallnut_seed_packet"), WALLNUT_SEED_PACKET);
        Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"potatomine_seed_packet"), POTATOMINE_SEED_PACKET);
        Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"snowpea_seed_packet"), SNOW_PEA_SEED_PACKET);
        Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"chomper_seed_packet"), CHOMPER_SEED_PACKET);
        Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"repeater_seed_packet"), REPEATER_SEED_PACKET);
        Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"sunshroom_seed_packet"), SUNSHROOM_SEED_PACKET);
        Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"puffshroom_seed_packet"), PUFFSHROOM_SEED_PACKET);
        Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"fumeshroom_seed_packet"), FUMESHROOM_SEED_PACKET);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"oxygae_seed_packet"), OXYGAE_SEED_PACKET);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"breezeshroom_seed_packet"), BREEZESHROOM_SEED_PACKET);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"burstshroom_seed_packet"), BURSTSHROOM_SEED_PACKET);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"superchomper_seed_packet"), SUPERCHOMPER_SEED_PACKET);
        Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"gravebuster_seed_packet"), GRAVEBUSTER_SEED_PACKET);
        Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"hypnoshroom_seed_packet"), HYPNOSHROOM_SEED_PACKET);
        Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"scaredyshroom_seed_packet"), SCAREDYSHROOM_SEED_PACKET);
        Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"iceshroom_seed_packet"), ICESHROOM_SEED_PACKET);
        Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"doomshroom_seed_packet"), DOOMSHROOM_SEED_PACKET);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"lilypad_seed_packet"), LILYPAD_SEED_PACKET);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"squash_seed_packet"), SQUASH_SEED_PACKET);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"tanglekelp_seed_packet"), TANGLEKELP_SEED_PACKET);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"threepeater_seed_packet"), THREEPEATER_SEED_PACKET);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"jalapeno_seed_packet"), JALAPENO_SEED_PACKET);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"spikeweed_seed_packet"), SPIKEWEED_SEED_PACKET);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"torchwood_seed_packet"), TORCHWOOD_SEED_PACKET);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"tallnut_seed_packet"), TALLNUT_SEED_PACKET);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"seashroom_seed_packet"), SEASHROOM_SEED_PACKET);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"magnetshroom_seed_packet"), MAGNETSHROOM_SEED_PACKET);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"cabbagepult_seed_packet"), CABBAGEPULT_SEED_PACKET);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"coffeebean_seed_packet"), COFFEEBEAN_SEED_PACKET);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"gatlingpea_seed_packet"), GATLINGPEA_SEED_PACKET);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"retrogatling_seed_packet"), RETROGATLING_SEED_PACKET);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"gloomshroom_seed_packet"), GLOOMSHROOM_SEED_PACKET);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"cattail_seed_packet"), CATTAIL_SEED_PACKET);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"twinsunflower_seed_packet"), TWINSUNFLOWER_SEED_PACKET);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"spikerock_seed_packet"), SPIKEROCK_SEED_PACKET);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"bloomerang_seed_packet"), BLOOMERANG_SEED_PACKET);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"icebergpult_seed_packet"), ICEBERGPULT_SEED_PACKET);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"beet_seed_packet"), BEET_SEED_PACKET);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"shamrock_seed_packet"), SHAMROCK_SEED_PACKET);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"chillypepper_seed_packet"), CHILLYPEPPER_SEED_PACKET);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"beeshooter_seed_packet"), BEESHOOTER_SEED_PACKET);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"snowqueenpea_seed_packet"), SNOW_QUEENPEA_SEED_PACKET);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"chester_seed_packet"), CHESTER_SEED_PACKET);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"vampireflower_seed_packet"), VAMPIREFLOWER_SEED_PACKET);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"acidshroom_seed_packet"), ACIDSHROOM_SEED_PACKET);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"dandelionweed_seed_packet"), DANDELIONWEED_SEED_PACKET);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"perfoomshroom_seed_packet"), PERFOOMSHROOM_SEED_PACKET);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"iceberglettuce_seed_packet"), ICEBERGLETTUCE_SEED_PACKET);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"springbean_seed_packet"), SPRINGBEAN_SEED_PACKET);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"coconutcannon_seed_packet"), COCONUTCANNON_SEED_PACKET);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"lightningreed_seed_packet"), LIGHTNINGREED_SEED_PACKET);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"peapod_seed_packet"), PEAPOD_SEED_PACKET);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"empeach_seed_packet"), EMPEACH_SEED_PACKET);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"pepperpult_seed_packet"), PEPPERPULT_SEED_PACKET);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"firepea_seed_packet"), FIRE_PEA_SEED_PACKET);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"endurian_seed_packet"), ENDURIAN_SEED_PACKET);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"goldleaf_seed_packet"), GOLDLEAF_SEED_PACKET);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"shadowshroom_seed_packet"), SHADOWSHROOM_SEED_PACKET);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"missiletoe_seed_packet"), MISSILETOE_SEED_PACKET);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"electropea_seed_packet"), ELECTROPEA_SEED_PACKET);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"peanut_seed_packet"), PEANUT_SEED_PACKET);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"ghostpepper_seed_packet"), GHOSTPEPPER_SEED_PACKET);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"olivepit_seed_packet"), OLIVEPIT_SEED_PACKET);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"gloomvine_seed_packet"), GLOOMVINE_SEED_PACKET);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"heavenlypeach_seed_packet"), HEAVENLYPEACH_SEED_PACKET);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"magicshroom_seed_packet"), MAGICSHROOM_SEED_PACKET);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"saucer_seed_packet"), SAUCER_SEED_PACKET);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"springprincess_seed_packet"), SPRINGPRINCESS_SEED_PACKET);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"dripphylleia_seed_packet"), DRIPPHYLLEIA_SEED_PACKET);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"pumpkinwitch_seed_packet"), PUMPKINWITCH_SEED_PACKET);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"tulimpeter_seed_packet"), TULIMPETER_SEED_PACKET);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"narcissus_seed_packet"), NARCISSUS_SEED_PACKET);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"impatyens_seed_packet"), IMPATYENS_SEED_PACKET);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"hammerflower_seed_packet"), HAMMERFLOWER_SEED_PACKET);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"meteorhammer_seed_packet"), METEORHAMMER_SEED_PACKET);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"frisbloom_seed_packet"), FRISBLOOM_SEED_PACKET);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"beautyshroom_seed_packet"), BEAUTYSHROOM_SEED_PACKET);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"charmshroom_seed_packet"), CHARMSHROOM_SEED_PACKET);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"magnetoshroom_seed_packet"), MAGNETOSHROOM_SEED_PACKET);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"smackadamia_seed_packet"), SMACKADAMIA_SEED_PACKET);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"bombseedling_seed_packet"), BOMBSEEDLING_SEED_PACKET);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"oilyolive_seed_packet"), OILYOLIVE_SEED_PACKET);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"zapricot_seed_packet"), ZAPRICOT_SEED_PACKET);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"bananasaurus_seed_packet"), BANANASAURUS_SEED_PACKET);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"smooshroom_seed_packet"), SMOOSHROOM_SEED_PACKET);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"weeniebeanie_seed_packet"), WEENIEBEANIE_SEED_PACKET);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"navybean_seed_packet"), NAVYBEAN_SEED_PACKET);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"admiralnavybean_seed_packet"), ADMIRALNAVYBEAN_SEED_PACKET);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"jumpingbean_seed_packet"), JUMPINGBEAN_SEED_PACKET);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"sunflowerseed_seed_packet"), SUNFLOWERSEED_SEED_PACKET);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"bellflower_seed_packet"), BELLFLOWER_SEED_PACKET);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"knightpea_seed_packet"), KNIGHTPEA_SEED_PACKET);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"nightcap_seed_packet"), NIGHTCAP_SEED_PACKET);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"doomrose_seed_packet"), DOOMROSE_SEED_PACKET);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"dogwood_seed_packet"), DOGWOOD_SEED_PACKET);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"gambleshroom_seed_packet"), GAMBLESHROOM_SEED_PACKET);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"missiletoetarget"), MISSILETOE_TARGET);
        Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"browncoat_egg"), BROWNCOATEGG);
        Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"flagzombie_egg"), FLAGZOMBIEEGG);
        Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"conehead_egg"), CONEHEADEGG);
        Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"polevaulting_egg"), POLEVAULTINGEGG);
        Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"buckethead_egg"), BUCKETHEADEGG);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"summerbasic_egg"), SUMMERBASICEGG);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"flagsummer_egg"), FLAGSUMMEREGG);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"summercone_egg"), SUMMERCONEEGG);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"summerbucket_egg"), SUMMERBUCKETEGG);
        Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"screendoor_egg"), SCREENDOOREGG);
        Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"newspaper_egg"), NEWSPAPEREGG);
        Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"football_egg"), FOOTBALLEGG);
        Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"dancingzombie_egg"), DANCINGZOMBIEEGG);
        Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"backupdancer_egg"), BACKUPDANCEREGG);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"snorkel_egg"), SNORKELEGG);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"dolphinrider_egg"), DOLPHINRIDEREGG);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"zomboni_egg"), ZOMBONIEGG);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"bobsled_egg"), BOBSLEDEGG);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"gargantuar_egg"), GARGANTUAREGG);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"mummygargantuar_egg"), MUMMYGARGANTUAREGG);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"unicorngargantuar_egg"), UNICORNGARGANTUAREGG);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"imp_egg"), IMPEGG);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"mummyimp_egg"), MUMMYIMPEGG);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"rainbowbassimp_egg"), BASSIMPEGG);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"scrapmech_egg"), SCRAPMECHEGG);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"scrapimp_egg"), SCRAPIMPEGG);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"trashcan_egg"), TRASHCANEGG);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"berserker_egg"), BERSERKEREGG);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"mummy_egg"), MUMMYEGG);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"flagmummy_egg"), FLAGMUMMYEGG);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"mummycone_egg"), MUMMYCONEEGG);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"mummybucket_egg"), MUMMYBUCKETEGG);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"explorer_egg"), EXPLOREREGG);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"tombraiser_egg"), TOMBRAISEREGG);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"torchlight_egg"), TORCHLIGHTEGG);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"pyramidhead_egg"), PYRAMIDHEADEGG);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"undying_egg"), UNDYINGEGG);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"pharaoh_egg"), PHARAOHEGG);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"peasant_egg"), PEASANTEGG);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"flagpeasant_egg"), FLAGPEASANTEGG);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"peasantcone_egg"), PEASANTCONEEGG);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"peasantbucket_egg"), PEASANTBUCKETEGG);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"peasantknight_egg"), PEASANTKNIGHTEGG);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"futurezombie_egg"), FUTUREZOMBIEEGG);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"flagfuture_egg"), FLAGFUTUREEGG);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"futurecone_egg"), FUTURECONEEGG);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"futurebucket_egg"), FUTUREBUCKETEGG);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"holohead_egg"), HOLOHEADEGG);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"announcerimp_egg"), ANNOUNCERIMPEGG);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"redannouncerimp_egg"), REDANNOUNCERIMPEGG);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"blackannouncerimp_egg"), BLACKANNOUNCERIMPEGG);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"zombieking_egg"), ZOMBIEKINGEGG);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"redzombieking_egg"), REDZOMBIEKINGEGG);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"blackzombieking_egg"), BLACKZOMBIEKINGEGG);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"impdragon_egg"), IMPDRAGONEGG);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"jetpack_egg"), JETPACKEGG);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"robocone_egg"), ROBOCONEEGG);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"blastronaut_egg"), BLASTRONAUTEGG);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"octo_egg"), OCTOEGG);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"brickhead_egg"), BRICKHEADEGG);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"sundayedition_egg"), SUNDAYEDITIONEGG);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"superfanimp_egg"), SUPERFANIMPEGG);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"bass_egg"), BASSEGG);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"pumpkinzombie_egg"), PUMPKINZOMBIEEGG);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"newyearimp_egg"), NEWYEARIMPEGG);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"pokerzombie_egg"), POKEREGG);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"flagpoker_egg"), FLAGPOKEREGG);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"pokerconehead_egg"), POKERCONEEGG);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"pokerbuckethead_egg"), POKERBUCKETEGG);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"pokerpawn_egg"), POKERPAWNEGG);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"pokerknight_egg"), POKERKNIGHTEGG);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"pokertower_egg"), POKERTOWEREGG);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"pokerbishop_egg"), POKERBISHOPEGG);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"sargeant_egg"), SARGEANTEGG);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"flagsargeant_egg"), FLAGSARGEANTEGG);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"sargeantcone_egg"), SARGEANTCONEEGG);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"sargeantbucket_egg"), SARGEANTBUCKETEGG);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"sargeantshield_egg"), SARGEANTSHIELDEGG);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"bookburner_egg"), BOOKBURNEREGG);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"zombiepiggy_egg"), PIGGYEGG);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"hawkercart_egg"), HAWKEREGG);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"soldier_egg"), SOLDIEREGG);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"scientist_egg"), SCIENTISTEGG);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"hovergoat_egg"), HOVERGOATEGG);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"zomblob_egg"), ZOMBLOBEGG);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"defensiveend_egg"), DEFENSIVEENDEGG);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"cursedgargolith_egg"), CURSEDGARGOLITHEGG);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"impthrow_egg"), IMPTHROWEGG);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"80sactionhero_egg"), ACTIONHEROEGG);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"bully_egg"), BULLYEGG);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"basketballcarrier_egg"), BASKETBALLCARRIEREGG);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"crystalshoeimp_egg"), CRYSTALSHOEIMPEGG);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"pumpkincarriage_egg"), PUMPKINCARRIAGEEGG);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"locustswarm_egg"), LOCUSTSWARMEGG);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"easy"), EASY);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"easymed"), EASYMED);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"med"), MED);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"medhard"), MEDHARD);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"hard"), HARD);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"superhard"), SUPERHARD);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"nightmare"), NIGHTMARE);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"crazy"), CRAAAAZY);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"1x1"), ONEBYONE);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"half"), HALF);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"infinite"), INFINITE);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"unlock"), UNLOCK);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"unlockspecial"), UNLOCKSPECIAL);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"day"), DAY);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"night"), NIGHT);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"bomb"), BOMB);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"drought"), DROUGHT);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"clear"), CLEAR);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"rain"), RAIN);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"thunder"), THUNDER);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"zombiegrave_spawn"), ZOMBIEGRAVESPAWN);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"basicgrave_spawn"), BASICGRAVESPAWN);
        Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"nightgrave_spawn"), NIGHTGRAVESPAWN);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"poolgrave_spawn"), POOLGRAVESPAWN);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"roofgrave_spawn"), ROOFGRAVESPAWN);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"egyptgrave_spawn"), EGYPTGRAVESPAWN);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"futuregrave_spawn"), FUTUREGRAVESPAWN);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"darkagesgrave_spawn"), DARKAGESGRAVESPAWN);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"fairytalegrave_spawn"), FAIRYTALEGRAVESPAWN);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"mausoleumgrave_spawn"), MAUSOLEUMGRAVESPAWN);
        Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"daves_shovel"), DAVES_SHOVEL);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"garden_spawn"), GARDEN_SPAWN);
		Registry.register(Registries.ITEM, new Identifier(PvZCubed.MOD_ID,"gardenchallenge_spawn"), GARDENCHALLENGE_SPAWN);
    }
}
