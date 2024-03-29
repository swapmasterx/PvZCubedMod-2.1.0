package io.github.GrassyDev.pvzmod.config;

import io.wispforest.owo.config.Option;
import io.wispforest.owo.config.annotation.*;

@Modmenu(modId = "pvzmod")
@Config(name = "pvz3-config", wrapperName = "PvZConfig")
public class PvZConfigModel {
	@Sync(Option.SyncMode.OVERRIDE_CLIENT)

	@SectionHeader("spawns")
	@Nest
	public PvZSpawnNest nestedSpawns = new PvZSpawnNest();
	public static class PvZSpawnNest {
		@RestartRequired
		public boolean spawnPlants = true;
		public boolean specialZombieSpawn = false;
		public boolean hoeAlternative = false;
		public int hoeBreak = 25;
		@Nest
		public PvZSpawnNestGrave nestedGraveSpawns = new PvZSpawnNestGrave();
		@Nest
		public PvZSpawnNestPlant nestedPlantSpawns = new PvZSpawnNestPlant();
	}

	public static class PvZSpawnNestGrave {
		@RestartRequired
		public int basicGv2 = 10;
		@RestartRequired
		public int basicGmin = 1;
		@RestartRequired
		public int basicGmax = 1;
		@RestartRequired
		public int nightGv2 = 10;
		@RestartRequired
		public int nightGmin = 1;
		@RestartRequired
		public int nightGmax = 1;
		@RestartRequired
		public int poolGv2 = 7;
		@RestartRequired
		public int poolGmin = 1;
		@RestartRequired
		public int poolGmax = 1;
		@RestartRequired
		public int roofGv2 = 7;
		@RestartRequired
		public int roofGmin = 1;
		@RestartRequired
		public int roofGmax = 1;
		@RestartRequired
		public int egyptG = 8;
		@RestartRequired
		public int egyptGmin = 1;
		@RestartRequired
		public int egyptGmax = 1;
		@RestartRequired
		public int futureGv2 = 8;
		@RestartRequired
		public int futureGmin = 1;
		@RestartRequired
		public int futureGmax = 1;
		@RestartRequired
		public int darkagesGv2 = 8;
		@RestartRequired
		public int darkagesGmin = 1;
		@RestartRequired
		public int darkagesGmax = 1;
		@RestartRequired
		public int mausoleumG = 4;
		@RestartRequired
		public int mausoleumGmin = 1;
		@RestartRequired
		public int mausoleumGmax = 1;
	}
	public static class PvZSpawnNestPlant {
		@RestartRequired
		public int peashooterSP = 50;
		@RestartRequired
		public int peashooterSPmin = 1;
		@RestartRequired
		public int peashooterSPmax = 1;
		@RestartRequired
		public int bellflowerSP = 50;
		@RestartRequired
		public int bellflowerSPmin = 2;
		@RestartRequired
		public int bellflowerSPmax = 2;
		@RestartRequired
		public int puffshroomSP = 50;
		@RestartRequired
		public int puffshroomSPmin = 4;
		@RestartRequired
		public int puffshroomSPmax = 8;
		@RestartRequired
		public int shadowshroomSP = 25;
		@RestartRequired
		public int shadowshroomSPmin = 3;
		@RestartRequired
		public int shadowshroomSPmax = 6;
		@RestartRequired
		public int weeniebeanieSP = 19;
		@RestartRequired
		public int weeniebeanieSPmin = 3;
		@RestartRequired
		public int weeniebeanieSPmax = 4;
		@RestartRequired
		public int sunflowerseedSPv2 = 15;
		@RestartRequired
		public int sunflowerseedSPmin = 3;
		@RestartRequired
		public int sunflowerseedSPmax = 4;
		@RestartRequired
		public int lilypadSP = 13;
		@RestartRequired
		public int lilypadSPmin = 4;
		@RestartRequired
		public int lilypadSPmax = 8;
		@RestartRequired
		public int bombseedlingSP = 10;
		@RestartRequired
		public int bombseedlingSPmin = 1;
		@RestartRequired
		public int bombseedlingSPmax = 3;
		@RestartRequired
		public int smallnutSP = 15;
		@RestartRequired
		public int smallnutSPmin = 3;
		@RestartRequired
		public int smallnutSPmax = 4;
		@RestartRequired
		public int loquatSP = 11;
		@RestartRequired
		public int loquatSPmin = 1;
		@RestartRequired
		public int loquatSPmax = 3;
		@RestartRequired
		public int icebergSP = 10;
		@RestartRequired
		public int icebergSPmin = 1;
		@RestartRequired
		public int icebergSPmax = 3;
		@RestartRequired
		public int zapricotSP = 10;
		@RestartRequired
		public int zapricotSPmin = 1;
		@RestartRequired
		public int zapricotSPmax = 3;
		@RestartRequired
		public int buttonshroomSP = 6;
		@RestartRequired
		public int buttonshroomSPmin = 2;
		@RestartRequired
		public int buttonshroomSPmax = 2;
		@RestartRequired
		public int solarwindSP = 6;
		@RestartRequired
		public int solarwindSPmin = 1;
		@RestartRequired
		public int solarwindSPmax = 1;
	}

	@SectionHeader("plants")
	@Nest
	public PvZSeedNest nestedSeeds = new PvZSeedNest();
	public static class PvZSeedNest {
		public boolean infiniteSeeds = false;
		public boolean instantRecharge = false;
		public boolean attractSun = true;
		@Nest
		public PvZMoreSeeds moreSeeds = new PvZMoreSeeds();
	}

	public static class PvZMoreSeeds {
		@RestartRequired
		public float acidshrooomS = 15.0f;
		@RestartRequired
		public float admiralnavybeanS = 10.0f;
		@RestartRequired
		public float bananasaurusS = 20f;
		@RestartRequired
		public float beautyshroomS = 12.5f;
		@RestartRequired
		public float beeshooterS = 15.0f;
		@RestartRequired
		public float beetS = 12.5f;
		@RestartRequired
		public float bellflowerS = 2.5f;
		@RestartRequired
		public float bloomerangS = 25f;
		@RestartRequired
		public float bombseedlingS = 2.5f;
		@RestartRequired
		public float breezesroomS = 15f;
		@RestartRequired
		public float burstshroomS = 20f;
		@RestartRequired
		public float buttonshroomS = 2.5f;
		@RestartRequired
		public float cabbagepultS = 5.0f;
		@RestartRequired
		public float cattailS = 35.0f;
		@RestartRequired
		public float coffeeBeanS = 7.5f;
		@RestartRequired
		public float charmshroomS = 25.0f;
		@RestartRequired
		public float cherrybombS = 35.0f;
		@RestartRequired
		public float chesterS = 25.0f;
		@RestartRequired
		public float chillypepperS = 25.0f;
		@RestartRequired
		public float chomperS = 20.0f;
		@RestartRequired
		public float coconutS = 35.0f;
		@RestartRequired
		public float dandelionweedS = 7.5f;
		@RestartRequired
		public float dogwoodS = 15f;
		@RestartRequired
		public float doomroseS = 20.0f;
		@RestartRequired
		public float doomshroomS = 25.0f;
		@RestartRequired
		public float dripphylleiaS = 15f;
		@RestartRequired
		public float dropeaS = 10f;
		@RestartRequired
		public float electropeaS = 20.0f;
		@RestartRequired
		public float empeachS = 25.0f;
		@RestartRequired
		public float endurianS = 30.0f;
		@RestartRequired
		public float firepeaS = 15.0f;
		@RestartRequired
		public float frisbloomS = 12.5f;
		@RestartRequired
		public float fumeshroomS = 7.5f;
		@RestartRequired
		public float gambleshroomS = 25f;
		@RestartRequired
		public float gatlingpeaS = 35.0f;
		@RestartRequired
		public float ghostpepperS = 30.0f;
		@RestartRequired
		public float gloomvineS = 15.0f;
		@RestartRequired
		public float gloomshroomS = 35.0f;
		@RestartRequired
		public float goldleafS = 20.0f;
		@RestartRequired
		public float gravebusterS = 5.0f;
		@RestartRequired
		public float hammerflowerS = 15.0f;
		@RestartRequired
		public float heavenlypeachS = 25f;
		@RestartRequired
		public float hypnoshroomS = 20.0f;
		@RestartRequired
		public float iceberglettuceS = 20.0f;
		@RestartRequired
		public float icebergpultS = 12.5f;
		@RestartRequired
		public float iceshroomS = 25.0f;
		@RestartRequired
		public float impatyensS = 17.5f;
		@RestartRequired
		public float jalapenoS = 35.0f;
		@RestartRequired
		public float jumpingbeanS = 20f;
		@RestartRequired
		public float knightpeaS = 20.0f;
		@RestartRequired
		public float lightningreedS = 12.5f;
		@RestartRequired
		public float lilypadS = 0f;
		@RestartRequired
		public float loquatS = 2.5f;
		@RestartRequired
		public float locococoS = 12.5f;
		@RestartRequired
		public float magicshroomS = 15f;
		@RestartRequired
		public float magnetshroomS = 15.0f;
		@RestartRequired
		public float magnetoshroomS = 10.0f;
		@RestartRequired
		public float meteorhammerS = 15.0f;
		@RestartRequired
		public float missileToeS = 25.0f;
		@RestartRequired
		public float narcissusS = 7.5f;
		@RestartRequired
		public float navybeanS = 5.0f;
		@RestartRequired
		public float nightcapS = 15.0f;
		@RestartRequired
		public float oilyOliveS = 2.5f;
		@RestartRequired
		public float olivepitS = 12.5f;
		@RestartRequired
		public float oxygaeS = 25.0f;
		@RestartRequired
		public float peanutS = 12.5f;
		@RestartRequired
		public float peapodS = 3.75f;
		@RestartRequired
		public float peashooterS = 5.0f;
		@RestartRequired
		public float pepperpultS = 20.0f;
		@RestartRequired
		public float perfoomshroomS = 25.0f;
		@RestartRequired
		public float potatomineS = 20.0f;
		@RestartRequired
		public float puffshroomS = 2.5f;
		@RestartRequired
		public float pumpkinwitchS = 20.0f;
		@RestartRequired
		public float repeaterS = 7.5f;
		@RestartRequired
		public float retrogatlingS = 35.0f;
		@RestartRequired
		public float saucerS = 30f;
		@RestartRequired
		public float scaredyshroomS = 5.0f;
		@RestartRequired
		public float seapeaS = 12.5f;
		@RestartRequired
		public float seashroomS = 10.0f;
		@RestartRequired
		public float shadowShroomS = 5f;
		@RestartRequired
		public float shamrockS = 5.0f;
		@RestartRequired
		public float smackadamiaSv2 = 30.0f;
		@RestartRequired
		public float smallnutS = 10.0f;
		@RestartRequired
		public float smooshroomS = 25.0f;
		@RestartRequired
		public float snowpeaS = 7.5f;
		@RestartRequired
		public float snowqueenpeaS = 35f;
		@RestartRequired
		public float spikerockS = 35.0f;
		@RestartRequired
		public float spikeweedS = 5.0f;
		@RestartRequired
		public float springbeanS = 15f;
		@RestartRequired
		public float springprincessS = 15.0f;
		@RestartRequired
		public float squashS = 25.0f;
		@RestartRequired
		public float sunflowerS = 2.5f;
		@RestartRequired
		public float sunflowerseedS = 2.5f;
		@RestartRequired
		public float sunshroomS = 5.0f;
		@RestartRequired
		public float superchomperS = 30.0f;
		@RestartRequired
		public float tallnutSv2 = 25.0f;
		@RestartRequired
		public float tanglekelpS = 25.0f;
		@RestartRequired
		public float threepeaterS = 7.5f;
		@RestartRequired
		public float torchwoodS = 37.5f;
		@RestartRequired
		public float tulimpeterS = 20.0f;
		@RestartRequired
		public float vampireflowerS = 15f;
		@RestartRequired
		public float twinsunflowerS = 35.0f;
		@RestartRequired
		public float wallnutSv2 = 25.0f;
		@RestartRequired
		public float weeniebeanieS = 2.5f;
		@RestartRequired
		public float zapricotS = 7.5f;
	}

	@Nest
	public PvZSunNest nestedSun = new PvZSunNest();
	public static class PvZSunNest {
		public float sunflowerSec = 120f;
		public float sunflowerSecInitial = 120f;
		public boolean sunflowerDropSun = true;
		public float twinSunflowerSec = 120f;
		public float sunshroomSec = 120f;
		public float sunshroomSecInitial = 5f;
		public float sunshroomSunChance = 0.45f;
		public float sunshroomSun2ndChance = 0.75f;
		public float goldtileSec = 120f;
		public float sunseedSec = 180f;
		public float zombiegraveSec = 10f;
	}

	@SectionHeader("projDMG")
	@Nest
	public PvZDMGNest nestedProjDMG = new PvZDMGNest();
	public static class PvZDMGNest {
		public float acidFumeDMG = 2.5f;
		public float acidSporeDMG = 3f;
		public float armorBubbleDMG = 2f;
		public float beespikeDMGv2 = 2f;
		public float breezeDMG = 2f;
		public float boomerangDMGv2 = 2.5f;
		public float bubblesDMG = 2f;
		public float cabbageDMG = 4f;
		public float cardDMGv2 = 0.5f;
		public float coconutDMGv2 = 180f;
		public float coconutSDMG = 100f;
		public float dropDMGv2 = 2.5f;
		public float dropSDMG = 2.5f;
		public float dyeDMG = 4f;
		public float electricPeaDMG = 4f;
		public float firepiercepeaDMG = 6f;
		public float firepiercepeaSDMG = 3f;
		public float frisbeeDMG = 3.5f;
		public float flamingPeaDMGv2 = 8f;
		public float flamingPeaSDMG = 4f;
		public float fumeDMG = 2.5f;
		public float goldencardDMG = 1f;
		public float hypnoprojDMGv2 = 8f;
		public float icebergDMGv2 = 3f;
		public float icebergSDMG = 1f;
		public float icespikeDMGv2 = 2f;
		public float iceSpikeMultiplier = 2f;
		public float jingleDMGv2 = 7f;
		public float missileToeDMGNear = 120f;
		public float missileToeDMGFar = 60f;
		public float missileToeDMGRangeNear = 1.5f;
		public float missileToeDMGRangeFar = 2.5f;
		public float peaDMG = 4f;
		public float pepperDMGv2 = 7f;
		public float pepperSDMG = 2.5f;
		public float piercepeaDMG = 3f;
		public float piercesporeDMG = 6f;
		public float plasmaPeaDMG = 12f;
		public float rainbowBulletDMG = 4f;
		public float snowPeaDMG = 4f;
		public float snowQueenPeaDMGv2 = 9f;
		public float snowQueenPeaSDMG = 3f;
		public float springDMG = 4f;
		public float spikeDMGv2 = 4f;
		public float spitDMG = 2f;
		public float smooshProjDMG = 12f;
		public float sporeDMG = 2f;
		public float swordDMG = 6f;

		public float basketBallDMG = 4f;
		public float laserDMG = 32f;
		public float soundwaveDMG = 9f;
		public float zpgDMG = 30f;
		public float rocketDMG = 15f;
	}

	@SectionHeader("zombieVar")
	@Nest
	public PvZZombieGeneral nestedGeneralZombie = new PvZZombieGeneral();
	public static class PvZZombieGeneral {
		public float zombieStep = 3.5F;
		public int zombieBlockJump = 4;
	}

	@Nest
	public PvZZombieHealth nestedZombieHealth = new PvZZombieHealth();
	public static class PvZZombieHealth {
		@RestartRequired
		public double zombieGraveH = 60D;
		@RestartRequired
		public double basicGraveH = 140D;
		@RestartRequired
		public double nightGraveH = 170D;
		@RestartRequired
		public double poolGraveH = 200D;
		@RestartRequired
		public double egyptGraveH = 200D;
		@RestartRequired
		public double roofGraveH = 230D;
		@RestartRequired
		public double futureGraveH = 240D;
		@RestartRequired
		public double darkAgesGraveH = 170D;
		@RestartRequired
		public double fairytaleGraveH = 360D;
		@RestartRequired
		public double mausoleumGraveH = 300D;

		@RestartRequired
		public double backupH = 27D;
		@RestartRequired
		public double bassH = 80D;
		@RestartRequired
		public double browncoatH = 27D;
		@RestartRequired
		public double summerH = 27D;
		@RestartRequired
		public double mummyH = 27D;
		@RestartRequired
		public double futureH = 27D;
		@RestartRequired
		public double peasantH = 27D;
		@RestartRequired
		public double pokerheartH = 65D;
		@RestartRequired
		public double pokerspadeH = 37D;
		@RestartRequired
		public double pokerclubH = 37D;
		@RestartRequired
		public double pokerdiamondH = 50D;
		@RestartRequired
		public double bobsledH = 65D;
		@RestartRequired
		public double sargeantH = 27D;
		@RestartRequired
		public double bullyH = 110D;
		@RestartRequired
		public double actionheroH = 85D;
		@RestartRequired
		public double basketballH = 110D;
		@RestartRequired
		public double dancingH = 50D;
		@RestartRequired
		public double dolphinH = 50D;
		@RestartRequired
		public double explorerH = 50D;
		@RestartRequired
		public double torchlightH = 65D;
		@RestartRequired
		public double flagH = 50D;
		@RestartRequired
		public double flagSummerH = 50D;
		@RestartRequired
		public double flagMummyH = 50D;
		@RestartRequired
		public double flagFuturetH = 50D;
		@RestartRequired
		public double flagPeasantH = 50D;
		@RestartRequired
		public double flagPokerH = 65D;
		@RestartRequired
		public double flagSargeantH = 75D;
		@RestartRequired
		public double footballH = 27D;
		@RestartRequired
		public double berserkerH = 27D;
		@RestartRequired
		public double gargantuarH = 360D;
		@RestartRequired
		public double mummygargantuarH = 420D;
		@RestartRequired
		public double defensiveendH = 360D;
		@RestartRequired
		public double cursedgargolithH = 260D;
		@RestartRequired
		public double unicorngargantuarH = 360D;
		@RestartRequired
		public double hawkerpusherH = 50D;
		@RestartRequired
		public double hoverGoatH = 75D;
		@RestartRequired
		public double impH = 20D;
		@RestartRequired
		public double impdragonH = 65D;
		@RestartRequired
		public double bassimpH = 65D;
		@RestartRequired
		public double scrapimpH = 80D;
		@RestartRequired
		public double superFanH = 20D;
		@RestartRequired
		public double announcerH = 37D;
		@RestartRequired
		public double jetpackH = 50D;
		@RestartRequired
		public double blastronautH = 50D;
		@RestartRequired
		public double newspaperH = 27D;
		@RestartRequired
		public double sundayH = 50D;
		@RestartRequired
		public double octoH = 91D;
		@RestartRequired
		public double poleH = 50D;
		@RestartRequired
		public double pharaohH = 50D;
		@RestartRequired
		public double undyingPharaohH = 65D;
		@RestartRequired
		public double pumpkincarH = 210D;
		@RestartRequired
		public double roboconeH = 250D;
		@RestartRequired
		public double scrapmechH = 290D;
		@RestartRequired
		public double scientistH = 27D;
		@RestartRequired
		public double snorkelH = 50D;
		@RestartRequired
		public double soldierH = 50D;
		@RestartRequired
		public double tombraiserH = 75D;
		@RestartRequired
		public double zombiekingH = 100D;
		@RestartRequired
		public double zombiepigH = 50D;
		@RestartRequired
		public double zomblobH = 64D;
		@RestartRequired
		public double zomblobBH = 128D;
		@RestartRequired
		public double zomblobSH = 32D;
		@RestartRequired
		public double zomboniH = 65D;

		@RestartRequired
		public double coneH = 37D;
		public double pokerpawngearH = 140D;
		public double pokerknightgearH = 212D;
		public double pokertowergearH = 286D;
		public double pokerbishopgearH = 212D;
		public double kingpiecegearH = 65D;

		@RestartRequired
		public double bucketH = 110D;
		@RestartRequired
		public double medallionH = 37D;
		@RestartRequired
		public double footballHelmH = 140D;
		@RestartRequired
		public double berserkerHelmH = 333D;
		@RestartRequired
		public double defensiveendHelmH = 140D;
		@RestartRequired
		public double blastronautHelmH = 37D;
		@RestartRequired
		public double knightHelmH = 152D;
		@RestartRequired
		public double sargeanthelmetH = 140D;
		@RestartRequired
		public double soldierhelmetH = 110D;

		@RestartRequired
		public double brickH = 212D;
		@RestartRequired
		public double coneTowerH = 53D;
		@RestartRequired
		public double pyramidH = 340D;
		@RestartRequired
		public double sarcophagusH = 212D;
		@RestartRequired
		public double bowlH = 27D;

		@RestartRequired
		public double holoHelmetH = 190D;
		@RestartRequired
		public double crystalshoeHelmetH = 85D;

		@RestartRequired
		public double pumpkinH = 84D;

		@RestartRequired
		public double screendoorShieldH = 153D;

		@RestartRequired
		public double sergeantShieldH = 180D;

		@RestartRequired
		public double newspaperShieldH = 15D;
		@RestartRequired
		public double sundayShieldH = 115D;
		@RestartRequired
		public double bookShieldH = 65D;

		@RestartRequired
		public double trashcanObstH = 225D;
		@RestartRequired
		public double basketballObstH = 120D;
		@RestartRequired
		public double healstationObstH = 60D;

		@RestartRequired
		public double gargolithObstH = 360D;
		@RestartRequired
		public double imptabletObstH = 65D;

		@RestartRequired
		public double egyptTombstoneH = 90D;

		@RestartRequired
		public double hawkerObstH = 90D;

		@RestartRequired
		public double octoObstH = 65;

		@RestartRequired
		public double zomboniVH = 135D;
		@RestartRequired
		public double bobsledVH = 65D;
		@RestartRequired
		public double speakerVH = 110D;
	}
}
