package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz1.browncoat.modernday;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.config.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.sound.PvZSounds;
import io.github.GrassyDev.pvzmod.registry.entity.damage.PvZDamageTypes;
import io.github.GrassyDev.pvzmod.registry.entity.gravestones.GraveEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.miscentity.garden.GardenEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.miscentity.gardenchallenge.GardenChallengeEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.PlantEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.day.sunflower.SunflowerEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.night.sunshroom.SunshroomEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.upgrades.twinsunflower.TwinSunflowerEntity;
import io.github.GrassyDev.pvzmod.registry.entity.variants.zombies.BrowncoatVariants;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.PvZombieAttackGoal;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2.browncoat.mummy.MummyEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2o.browncoat.sargeant.SargeantEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieprops.crystalhelmet.CrystalHelmetEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieprops.metallichelmet.MetalHelmetEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieprops.metallicobstacle.MetalObstacleEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieprops.metallicshield.MetalShieldEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieprops.papershield.NewspaperShieldEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieprops.plastichelmet.PlasticHelmetEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieprops.stonehelmet.StoneHelmetEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.*;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.goal.TargetGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtOps;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Difficulty;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.util.GeckoLibUtil;

import org.jetbrains.annotations.Nullable;


import java.nio.charset.StandardCharsets;
import java.util.UUID;

import static io.github.GrassyDev.pvzmod.PvZCubed.*;

public class BrowncoatEntity extends PvZombieEntity implements GeoEntity {

    private AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);
    private String controllerName = "walkingcontroller";
	public static final UUID MAX_SPEED_UUID = UUID.nameUUIDFromBytes(MOD_ID.getBytes(StandardCharsets.ISO_8859_1));

	public int launchAnimation;
	public boolean inLaunchAnimation;

	public BrowncoatEntity(EntityType<? extends BrowncoatEntity> entityType, World world) {
        super(entityType, world);

        this.experiencePoints = 3;
	}

	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(DATA_ID_TYPE_VARIANT, 0);
	}

	public void writeCustomDataToNbt(NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);
		nbt.putInt("Variant", this.getTypeVariant());
	}

	public void readCustomDataFromNbt(NbtCompound nbt) {
		super.readCustomDataFromNbt(nbt);
		this.dataTracker.set(DATA_ID_TYPE_VARIANT, nbt.getInt("Variant"));
	}

	@Override
	public void setHypno(IsHypno hypno) {
		super.setHypno(hypno);
	}


	/** /~*~//~*VARIANTS*~//~*~/ **/

	private static final TrackedData<Integer> DATA_ID_TYPE_VARIANT =
			DataTracker.registerData(BrowncoatEntity.class, TrackedDataHandlerRegistry.INTEGER);

	public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty,
								 SpawnReason spawnReason, @Nullable EntityData entityData,
								 @Nullable NbtCompound entityNbt) {
		if (this.getType().equals(PvZEntity.CONEHEAD) ||
				this.getType().equals(PvZEntity.SUMMERCONEHEAD) ||
				this.getType().equals(PvZEntity.MUMMYCONE) ||
				this.getType().equals(PvZEntity.POKERCONE) ||
				this.getType().equals(PvZEntity.FUTURECONE)){
			setVariant(BrowncoatVariants.CONEHEAD);
			createConeheadProp();
			this.initCustomGoals();
		}
		else if (this.getType().equals(PvZEntity.PEASANTCONE)){
			setVariant(BrowncoatVariants.CONEHEAD);
			createTowerProp();
			this.initCustomGoals();
		}
		else if (this.getType().equals(PvZEntity.SARGEANTBOWL)){
			setVariant(BrowncoatVariants.CONEHEAD);
			createBowlProp();
			this.initCustomGoals();
		}
		else if (this.getType().equals(PvZEntity.BUCKETHEAD) ||
				this.getType().equals(PvZEntity.MUMMYBUCKET) ||
				this.getType().equals(PvZEntity.POKERBUCKET) ||
				this.getType().equals(PvZEntity.SUMMERBUCKETHEAD) ||
				this.getType().equals(PvZEntity.PEASANTBUCKET) ||
				this.getType().equals(PvZEntity.FUTUREBUCKET)){
			setVariant(BrowncoatVariants.BUCKETHEAD);
			createBucketProp();
			this.initCustomGoals();
		}
		else if (this.getType().equals(PvZEntity.SARGEANTHELMET)){
			setVariant(BrowncoatVariants.BUCKETHEAD);
			createHelmetProp();
			this.initCustomGoals();
		}
		else if (this.getType().equals(PvZEntity.SARGEANTSHIELD)){
			setVariant(BrowncoatVariants.SCREENDOOR);
			createHelmetProp();
			createSergeantShield();
			this.initCustomGoals();
		}
		else if (this.getType().equals(PvZEntity.PYRAMIDHEAD)){
			this.setCanHypno(CanHypno.FALSE);
			setVariant(BrowncoatVariants.PYRAMIDHEAD);
			createPyramidProp();
			this.initCustomGoals();
		}
		else if (this.getType().equals(PvZEntity.TOMBRAISER)){
			setVariant(BrowncoatVariants.TOMB);
			this.initCustomGoals();
		}
		else if (this.getType().equals(PvZEntity.POKERPAWN)){
			this.setCanHypno(CanHypno.FALSE);
			setVariant(BrowncoatVariants.POKERPAWN);
			createPokerPawnProp();
			this.initCustomGoals();
		}
		else if (this.getType().equals(PvZEntity.POKERKNIGHT)){
			this.setCanHypno(CanHypno.FALSE);
			setVariant(BrowncoatVariants.POKERKNIGHT);
			createPokerKnightProp();
			this.initCustomGoals();
		}
		else if (this.getType().equals(PvZEntity.POKERTOWER)){
			this.setCanHypno(CanHypno.FALSE);
			setVariant(BrowncoatVariants.POKERTOWER);
			createPokerTowerProp();
			this.initCustomGoals();
		}
		else if (this.getType().equals(PvZEntity.POKERBISHOP)){
			this.setCanHypno(CanHypno.FALSE);
			setVariant(BrowncoatVariants.POKERBISHOP);
			createPokerBishopProp();
			this.initCustomGoals();
		}
		else if (this.getType().equals(PvZEntity.PEASANTKNIGHT)){
			setVariant(BrowncoatVariants.PEASANTKNIGHT);
			createKnightProp();
			this.initCustomGoals();
		}
		else if (this.getType().equals(PvZEntity.BRICKHEAD)){
			setVariant(BrowncoatVariants.BRICKHEAD);
			createBrickProp();
			this.initCustomGoals();
		}
		else if (this.getType().equals(PvZEntity.HOLOHEAD)){
			setVariant(BrowncoatVariants.HOLOHEAD);
			createHoloProp();
			this.initCustomGoals();
		}
		else if (this.getType().equals(PvZEntity.SCREENDOOR)){
			createShield();
			setVariant(BrowncoatVariants.SCREENDOOR);
			this.initCustomGoals();
		}
		else if (this.getType().equals(PvZEntity.BOOKBURNER)){
			createPaperShield();
			setVariant(BrowncoatVariants.BOOKBURN);
			this.initCustomGoals();
		}
		else if (this.getType().equals(PvZEntity.TRASHCAN)){
			createObstacle();
			setVariant(BrowncoatVariants.TRASHCAN);
			this.initCustomGoals();
		}
		else if (this.getType().equals(PvZEntity.BROWNCOATHYPNO) ||
				this.getType().equals(PvZEntity.MUMMYHYPNO) ||
				this.getType().equals(PvZEntity.POKERHYPNO) ||
				this.getType().equals(PvZEntity.SUMMERBASICHYPNO) ||
				this.getType().equals(PvZEntity.PEASANTHYPNO) ||
				this.getType().equals(PvZEntity.SARGEANTHYPNO) ||
				this.getType().equals(PvZEntity.FUTUREHYPNO)){
			setVariant(BrowncoatVariants.BROWNCOATHYPNO);
			this.setHypno(IsHypno.TRUE);
		}
		else if (this.getType().equals(PvZEntity.CONEHEADHYPNO) ||
				this.getType().equals(PvZEntity.MUMMYCONEHYPNO) ||
				this.getType().equals(PvZEntity.POKERCONEHYPNO) ||
				this.getType().equals(PvZEntity.SUMMERCONEHEADHYPNO) ||
				this.getType().equals(PvZEntity.PEASANTCONEHYPNO) ||
				this.getType().equals(PvZEntity.FUTURECONEHYPNO) ||
				this.getType().equals(PvZEntity.SARGEANTBOWLHYPNO) ||
				this.getType().equals(PvZEntity.FUTUREHYPNO)){
			setVariant(BrowncoatVariants.CONEHEADHYPNO);
			this.setHypno(IsHypno.TRUE);
		}
		else if (this.getType().equals(PvZEntity.BUCKETHEADHYPNO) ||
				this.getType().equals(PvZEntity.MUMMYBUCKETHYPNO) ||
				this.getType().equals(PvZEntity.POKERBUCKETHYPNO) ||
				this.getType().equals(PvZEntity.SUMMERBUCKETHEADHYPNO) ||
				this.getType().equals(PvZEntity.PEASANTBUCKETHYPNO) ||
				this.getType().equals(PvZEntity.SARGEANTHELMETHYPNO) ||
				this.getType().equals(PvZEntity.FUTUREBUCKETHYPNO)){
			setVariant(BrowncoatVariants.BUCKETHEADHYPNO);
			this.setHypno(IsHypno.TRUE);
		}
		else if (this.getType().equals(PvZEntity.PYRAMIDHEADHYPNO)){
			setVariant(BrowncoatVariants.PYRAMIDHEADHYPNO);
			this.setHypno(IsHypno.TRUE);
		}
		else if (this.getType().equals(PvZEntity.TOMBRAISERHYPNO)){
			setVariant(BrowncoatVariants.TOMBHYPNO);
			this.setHypno(IsHypno.TRUE);
		}
		else if (this.getType().equals(PvZEntity.POKERPAWNHYPNO)){
			setVariant(BrowncoatVariants.POKERPAWNHYPNO);
			this.setHypno(IsHypno.TRUE);
		}
		else if (this.getType().equals(PvZEntity.POKERKNIGHTHYPNO)){
			setVariant(BrowncoatVariants.POKERKNIGHTHYPNO);
			this.setHypno(IsHypno.TRUE);
		}
		else if (this.getType().equals(PvZEntity.POKERTOWERHYPNO)){
			setVariant(BrowncoatVariants.POKERTOWERHYPNO);
			this.setHypno(IsHypno.TRUE);
		}
		else if (this.getType().equals(PvZEntity.POKERBISHOPHYPNO)){
			setVariant(BrowncoatVariants.POKERBISHOPHYPNO);
			this.setHypno(IsHypno.TRUE);
		}
		else if (this.getType().equals(PvZEntity.PEASANTKNIGHTHYPNO)){
			setVariant(BrowncoatVariants.PEASANTKNIGHTHYPNO);
			this.setHypno(IsHypno.TRUE);
		}
		else if (this.getType().equals(PvZEntity.BRICKHEADHYPNO)){
			setVariant(BrowncoatVariants.BRICKHEADHYPNO);
			this.setHypno(IsHypno.TRUE);
		}
		else if (this.getType().equals(PvZEntity.HOLOHEADHYPNO)){
			setVariant(BrowncoatVariants.HOLOHEADHYPNO);
			this.setHypno(IsHypno.TRUE);
		}
		else if (this.getType().equals(PvZEntity.SCREENDOORHYPNO) ||
				this.getType().equals(PvZEntity.SARGEANTSHIELDHYPNO)){
			setVariant(BrowncoatVariants.SCREENDOORHYPNO);
			this.setHypno(IsHypno.TRUE);
		}
		else if (this.getType().equals(PvZEntity.BOOKBURNERHYPNO)){
			setVariant(BrowncoatVariants.BOOKBURNHYPNO);
			this.setHypno(IsHypno.TRUE);
		}
		else if (this.getType().equals(PvZEntity.TRASHCANHYPNO)){
			setVariant(BrowncoatVariants.TRASHCANHYPNO);
			this.setHypno(IsHypno.TRUE);
		}
		else {
			setVariant(BrowncoatVariants.BROWNCOAT);
		}
		return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
	}

	private int getTypeVariant() {
		return this.dataTracker.get(DATA_ID_TYPE_VARIANT);
	}

	public BrowncoatVariants getVariant() {
		return BrowncoatVariants.byId(this.getTypeVariant() & 255);
	}

	public void setVariant(BrowncoatVariants variant) {
		this.dataTracker.set(DATA_ID_TYPE_VARIANT, variant.getId() & 255);
	}

	public void createConeheadProp(){
		if (getWorld() instanceof ServerWorld serverWorld) {
			PlasticHelmetEntity propentity = new PlasticHelmetEntity(PvZEntity.CONEHEADGEAR, this.getWorld());
			propentity.initialize(serverWorld, this.getWorld().getLocalDifficulty(this.getBlockPos()), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
			propentity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.bodyYaw, 0.0F);
			propentity.startRiding(this);
		}
	}

	public void createPokerPawnProp(){
		if (getWorld() instanceof ServerWorld serverWorld) {
			PlasticHelmetEntity propentity = new PlasticHelmetEntity(PvZEntity.POKERPAWNGEAR, this.getWorld());
			propentity.initialize(serverWorld, this.getWorld().getLocalDifficulty(this.getBlockPos()), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
			propentity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.bodyYaw, 0.0F);
			propentity.startRiding(this);
		}
	}

	public void createPokerKnightProp(){
		if (getWorld() instanceof ServerWorld serverWorld) {
			PlasticHelmetEntity propentity = new PlasticHelmetEntity(PvZEntity.POKERKNIGHTGEAR, this.getWorld());
			propentity.initialize(serverWorld, this.getWorld().getLocalDifficulty(this.getBlockPos()), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
			propentity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.bodyYaw, 0.0F);
			propentity.startRiding(this);
		}
	}

	public void createPokerTowerProp(){
		if (getWorld() instanceof ServerWorld serverWorld) {
			PlasticHelmetEntity propentity = new PlasticHelmetEntity(PvZEntity.POKERTOWERGEAR, this.getWorld());
			propentity.initialize(serverWorld, this.getWorld().getLocalDifficulty(this.getBlockPos()), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
			propentity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.bodyYaw, 0.0F);
			propentity.startRiding(this);
		}
	}

	public void createPokerBishopProp(){
		if (getWorld() instanceof ServerWorld serverWorld) {
			PlasticHelmetEntity propentity = new PlasticHelmetEntity(PvZEntity.POKERBISHOPGEAR, this.getWorld());
			propentity.initialize(serverWorld, this.getWorld().getLocalDifficulty(this.getBlockPos()), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
			propentity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.bodyYaw, 0.0F);
			propentity.startRiding(this);
		}
	}

	public void createBucketProp(){
		if (getWorld() instanceof ServerWorld serverWorld) {
			MetalHelmetEntity propentity = new MetalHelmetEntity(PvZEntity.BUCKETGEAR, this.getWorld());
			propentity.initialize(serverWorld, this.getWorld().getLocalDifficulty(this.getBlockPos()), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
			propentity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.bodyYaw, 0.0F);
			propentity.startRiding(this);
		}
	}

	public void createHelmetProp(){
		if (getWorld() instanceof ServerWorld serverWorld) {
			MetalHelmetEntity propentity = new MetalHelmetEntity(PvZEntity.HELMETGEAR, this.getWorld());
			propentity.initialize(serverWorld, this.getWorld().getLocalDifficulty(this.getBlockPos()), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
			propentity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.bodyYaw, 0.0F);
			propentity.startRiding(this);
		}
	}

	public ZombiePropEntity createKnightProp() {
		if (getWorld() instanceof ServerWorld serverWorld) {
			MetalHelmetEntity propentity = new MetalHelmetEntity(PvZEntity.KNIGHTGEAR, this.getWorld());
			propentity.initialize(serverWorld, this.getWorld().getLocalDifficulty(this.getBlockPos()), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
			propentity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.bodyYaw, 0.0F);
			propentity.startRiding(this);
			return propentity;
		} else {
			return null;
		}
	}

	public void createBrickProp() {
		if (getWorld() instanceof ServerWorld serverWorld) {
			StoneHelmetEntity propentity = new StoneHelmetEntity(PvZEntity.BRICKGEAR, this.getWorld());
			propentity.initialize(serverWorld, this.getWorld().getLocalDifficulty(this.getBlockPos()), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
			propentity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.bodyYaw, 0.0F);
			propentity.startRiding(this);
		}
	}

	public void createHoloProp() {
		if (getWorld() instanceof ServerWorld serverWorld) {
			CrystalHelmetEntity propentity = new CrystalHelmetEntity(PvZEntity.HOLOGEAR, this.getWorld());
			propentity.initialize(serverWorld, this.getWorld().getLocalDifficulty(this.getBlockPos()), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
			propentity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.bodyYaw, 0.0F);
			propentity.startRiding(this);
		}
	}

	public void createPyramidProp() {
		if (getWorld() instanceof ServerWorld serverWorld) {
			StoneHelmetEntity propentity = new StoneHelmetEntity(PvZEntity.PYRAMIDGEAR, this.getWorld());
			propentity.initialize(serverWorld, this.getWorld().getLocalDifficulty(this.getBlockPos()), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
			propentity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.bodyYaw, 0.0F);
			propentity.startRiding(this);
		}
	}

	public void createTowerProp() {
		if (getWorld() instanceof ServerWorld serverWorld) {
			StoneHelmetEntity propentity = new StoneHelmetEntity(PvZEntity.TOWERGEAR, this.getWorld());
			propentity.initialize(serverWorld, this.getWorld().getLocalDifficulty(this.getBlockPos()), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
			propentity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.bodyYaw, 0.0F);
			propentity.startRiding(this);
		}
	}

	public void createBowlProp() {
		if (getWorld() instanceof ServerWorld serverWorld) {
			StoneHelmetEntity propentity = new StoneHelmetEntity(PvZEntity.BOWLGEAR, this.getWorld());
			propentity.initialize(serverWorld, this.getWorld().getLocalDifficulty(this.getBlockPos()), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
			propentity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.bodyYaw, 0.0F);
			propentity.startRiding(this);
		}
	}

	public void createShield() {
		MetalShieldEntity metalShieldEntity = new MetalShieldEntity(PvZEntity.SCREENDOORSHIELD, this.getWorld());
		if (getWorld()instanceof ServerWorld serverWorld) {
			metalShieldEntity.initialize(serverWorld, this.getWorld().getLocalDifficulty(this.getBlockPos()), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
		}
		metalShieldEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.bodyYaw, 0.0F);
		metalShieldEntity.startRiding(this);
	}

	public void createPaperShield(){
		if (getWorld() instanceof ServerWorld serverWorld) {
			NewspaperShieldEntity newspaperShieldEntity = new NewspaperShieldEntity(PvZEntity.BOOKSHIELD, this.getWorld());
			newspaperShieldEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.bodyYaw, 0.0F);
			newspaperShieldEntity.initialize(serverWorld, this.getWorld().getLocalDifficulty(this.getBlockPos()), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
			newspaperShieldEntity.startRiding(this);
		}
	}

	public void createSergeantShield() {
		if (getWorld() instanceof ServerWorld serverWorld) {
			MetalShieldEntity metalShieldEntity = new MetalShieldEntity(PvZEntity.SERGEANTSHIELDGEAR, this.getWorld());
			metalShieldEntity.initialize(serverWorld, this.getWorld().getLocalDifficulty(this.getBlockPos()), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
			metalShieldEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.bodyYaw, 0.0F);
			metalShieldEntity.startRiding(this);
		}
	}

	public void createObstacle() {
		if (getWorld() instanceof ServerWorld serverWorld) {
			MetalObstacleEntity metalObstacleEntity = new MetalObstacleEntity(PvZEntity.TRASHCANBIN, this.getWorld());
			metalObstacleEntity.initialize(serverWorld, this.getWorld().getLocalDifficulty(this.getBlockPos()), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
			metalObstacleEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.bodyYaw, 0.0F);
			metalObstacleEntity.startRiding(this);
		}
	}


	/** /~*~//~*GECKOLIB ANIMATION*~//~*~/ **/

	@Override
	public void registerControllers(AnimatableManager.ControllerRegistrar controllers){
		controllers.add(new AnimationController<>(this, controllerName, 0, this::predicate));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.factory;
	}


	protected  <P extends GeoAnimatable> PlayState predicate(AnimationState<P> event) {
		ZombieShieldEntity zombieShieldEntity = null;
		for (Entity entity : this.getPassengerList()){
			if (entity instanceof ZombieShieldEntity) {
				zombieShieldEntity = (ZombieShieldEntity) entity;
			}
		}
		if (this.isInsideWaterOrBubbleColumn()) {
			if (inLaunchAnimation) {
				event.getController().setAnimation(RawAnimation.begin().thenLoop("screendoor.ducky.throw"));
				if (this.isIced) {
					event.getController().setAnimationSpeed(0.5);
				}
				else {
					event.getController().setAnimationSpeed(1);
				}
			}
			else if (zombieShieldEntity != null){
				event.getController().setAnimation(RawAnimation.begin().thenLoop("screendoor.ducky"));
			}
			else if (this.getVariant().equals(BrowncoatVariants.SCREENDOOR) || this.getVariant().equals(BrowncoatVariants.SCREENDOORHYPNO) ||
					this.getVariant().equals(BrowncoatVariants.BOOKBURN) || this.getVariant().equals(BrowncoatVariants.BOOKBURNHYPNO) ||
					this.getVariant().equals(BrowncoatVariants.TRASHCAN) || this.getVariant().equals(BrowncoatVariants.TRASHCANHYPNO) ) {
				event.getController().setAnimation(RawAnimation.begin().thenLoop("screendoor.ducky2"));
			}
			else {
				event.getController().setAnimation(RawAnimation.begin().thenLoop("headwear.ducky"));
			}
			if (this.isIced) {
				event.getController().setAnimationSpeed(0.5);
			}
			else {
				event.getController().setAnimationSpeed(1);
			}
		} else {
			if (inLaunchAnimation) {
				event.getController().setAnimation(RawAnimation.begin().thenLoop("screendoor.throw"));
				if (this.isIced) {
					event.getController().setAnimationSpeed(0.5);
				} else {
					event.getController().setAnimationSpeed(1);
				}
			}
			else if (!(event.getLimbSwingAmount() > -0.01F && event.getLimbSwingAmount() < 0.01F)) {
				if (zombieShieldEntity != null){
					event.getController().setAnimation(RawAnimation.begin().thenLoop("screendoor.walking"));
				}
				else {
					event.getController().setAnimation(RawAnimation.begin().thenLoop("headwear.walking"));
				}
			} else {
				if (zombieShieldEntity != null){
					event.getController().setAnimation(RawAnimation.begin().thenLoop("screendoor.idle"));
				}
				else {
					event.getController().setAnimation(RawAnimation.begin().thenLoop("headwear.idle"));
				}
			}
			if (this.isFrozen || this.isStunned) {
				event.getController().setAnimationSpeed(0);
			}
			else if (this.isIced) {
				event.getController().setAnimationSpeed(0.5);
			}
			else {
				event.getController().setAnimationSpeed(1);
			}
		}
		return PlayState.CONTINUE;
	}


	/** /~*~//~*AI*~//~*~/ **/

	protected void initGoals() {
		if (this.getType().equals(PvZEntity.BROWNCOATHYPNO) ||
				this.getType().equals(PvZEntity.CONEHEADHYPNO) ||
				this.getType().equals(PvZEntity.BUCKETHEADHYPNO) ||
				this.getType().equals(PvZEntity.BRICKHEADHYPNO) ||
				this.getType().equals(PvZEntity.SCREENDOORHYPNO) ||
				this.getType().equals(PvZEntity.TRASHCANHYPNO) ||
				this.getType().equals(PvZEntity.SUMMERBASICHYPNO) ||
				this.getType().equals(PvZEntity.SUMMERCONEHEADHYPNO) ||
				this.getType().equals(PvZEntity.SUMMERBUCKETHEADHYPNO) ||
				this.getType().equals(PvZEntity.MUMMYHYPNO) ||
				this.getType().equals(PvZEntity.MUMMYCONEHYPNO) ||
				this.getType().equals(PvZEntity.MUMMYBUCKETHYPNO) ||
				this.getType().equals(PvZEntity.PYRAMIDHEADHYPNO) ||
				this.getType().equals(PvZEntity.TOMBRAISERHYPNO) ||
				this.getType().equals(PvZEntity.POKERHYPNO) ||
				this.getType().equals(PvZEntity.POKERCONEHYPNO) ||
				this.getType().equals(PvZEntity.POKERBUCKETHYPNO) ||
				this.getType().equals(PvZEntity.POKERPAWNHYPNO) ||
				this.getType().equals(PvZEntity.POKERKNIGHTHYPNO) ||
				this.getType().equals(PvZEntity.POKERTOWERHYPNO) ||
				this.getType().equals(PvZEntity.POKERBISHOPHYPNO) ||
				this.getType().equals(PvZEntity.PEASANTHYPNO) ||
				this.getType().equals(PvZEntity.PEASANTCONEHYPNO) ||
				this.getType().equals(PvZEntity.PEASANTBUCKETHYPNO) ||
				this.getType().equals(PvZEntity.PEASANTKNIGHTHYPNO) ||
				this.getType().equals(PvZEntity.FUTUREHYPNO) ||
				this.getType().equals(PvZEntity.FUTURECONEHYPNO) ||
				this.getType().equals(PvZEntity.FUTUREBUCKETHYPNO) ||
				this.getType().equals(PvZEntity.HOLOHEADHYPNO) ||
				this.getType().equals(PvZEntity.SARGEANTHYPNO) ||
				this.getType().equals(PvZEntity.SARGEANTBOWLHYPNO) ||
				this.getType().equals(PvZEntity.SARGEANTHELMETHYPNO) ||
				this.getType().equals(PvZEntity.SARGEANTSHIELDHYPNO) ||
				this.getType().equals(PvZEntity.BOOKBURNERHYPNO)) {
			initHypnoGoals();
		}
		else {
			initCustomGoals();
		}
    }

    protected void initCustomGoals() {

		this.goalSelector.add(8, new LookAroundGoal(this));
		this.targetSelector.add(6, new RevengeGoal(this, new Class[0]));
		this.goalSelector.add(7, new WanderAroundFarGoal(this, 1.0));
		this.goalSelector.add(1, new PvZombieAttackGoal(this, 1.0D, true));

		this.targetSelector.add(5, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return livingEntity instanceof PlantEntity plantEntity && !(PLANT_LOCATION.get(plantEntity.getType()).orElse("normal").equals("ground")) && !(plantEntity.getLowProfile()) && !(PLANT_LOCATION.get(plantEntity.getType()).orElse("normal").equals("flying"));
		}));

		this.targetSelector.add(4, new TargetGoal<>(this, MerchantEntity.class, false, true));
		this.targetSelector.add(2, new TargetGoal<>(this, IronGolemEntity.class, false, true));

		////////// Must-Protect Plants ///////
		this.targetSelector.add(3, new TargetGoal<>(this, GardenChallengeEntity.class, false, true));
		this.targetSelector.add(3, new TargetGoal<>(this, GardenEntity.class, false, true));
		this.targetSelector.add(4, new TargetGoal<>(this, SunflowerEntity.class, false, true));
		this.targetSelector.add(4, new TargetGoal<>(this, TwinSunflowerEntity.class, false, true));
		this.targetSelector.add(4, new TargetGoal<>(this, SunshroomEntity.class, false, true));
    }

	public void initHypnoGoals(){
		this.goalSelector.add(8, new LookAroundGoal(this));
		this.goalSelector.add(1, new HypnoPvZombieAttackGoal(this, 1.0D, true));
		this.goalSelector.add(7, new WanderAroundFarGoal(this, 1.0));
		////////// Hypnotized Zombie targets ///////
		this.targetSelector.add(1, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return (livingEntity instanceof ZombiePropEntity zombiePropEntity && !(zombiePropEntity.getHypno()));
		}));
		this.targetSelector.add(2, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return (livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity.getHypno())) &&
					(!(livingEntity instanceof ZombiePropEntity) || (livingEntity instanceof ZombieObstacleEntity));
		}));
		this.targetSelector.add(2, new TargetGoal<>(this, MobEntity.class, 0, true, true, (livingEntity) -> {
			return livingEntity instanceof Monster && !(livingEntity instanceof GeneralPvZombieEntity) && !(livingEntity instanceof GraveEntity);
		}));
	}

	/** /~*~//~*TICKING*~//~*~/ **/

	public void tick() {
		if (this.getType().equals(PvZEntity.PYRAMIDHEAD)) {
			this.setCanHypno(CanHypno.FALSE);
		}
		if (this.getType().equals(PvZEntity.SUMMERBASIC) ||
				this.getType().equals(PvZEntity.SUMMERBASICHYPNO) ||
				this.getType().equals(PvZEntity.SUMMERCONEHEAD) ||
				this.getType().equals(PvZEntity.SUMMERCONEHEADHYPNO) ||
				this.getType().equals(PvZEntity.SUMMERBUCKETHEAD) ||
				this.getType().equals(PvZEntity.SUMMERBUCKETHEADHYPNO)){
			this.removeStatusEffect(ICE);
		}
		super.tick();
		if (this.getAttacking() != null){
			this.setVelocity(0, -0.3, 0);
						this.getNavigation().stop();
			this.getNavigation().stop();
		}
		if (!(this.getHypno()) && !this.getWorld().isClient()){
			if (this.getType().equals(PvZEntity.SARGEANT) ||
					this.getType().equals(PvZEntity.SARGEANTBOWL) ||
					this.getType().equals(PvZEntity.SARGEANTHELMET) ||
			this.getType().equals(PvZEntity.SARGEANTSHIELD)){
				for (float x = 0; x <= 2f; ++x) {
					if (this.CollidesWithPlant(x, 0f) instanceof GardenChallengeEntity){
						this.setTarget(CollidesWithPlant(0.1f, 0f));
						this.setStealthTag(Stealth.FALSE);
					}
					else if (this.CollidesWithPlant(x, 0f) != null && !this.hasStatusEffect(PvZCubed.BOUNCED)) {
						this.setVelocity(0, -0.3, 0);
						this.getNavigation().stop();
						this.setTarget(CollidesWithPlant(x, 0f));
						this.setStealthTag(Stealth.FALSE);
					} else if (this.CollidesWithPlayer(x + 0.5f) != null && !this.CollidesWithPlayer(x + 0.5f).isCreative()) {
						this.setTarget(CollidesWithPlayer(1.5f));
						this.setStealthTag(Stealth.FALSE);
					}
				}
			}
			else {
				if (this.CollidesWithPlant(0.1f, 0f) instanceof GardenChallengeEntity){
					this.setTarget(CollidesWithPlant(0.1f, 0f));
					this.setStealthTag(Stealth.FALSE);
				}
				else if (this.CollidesWithPlant(0.1f, 0f) != null && !this.hasStatusEffect(PvZCubed.BOUNCED)) {
					this.setVelocity(0, -0.3, 0);
						this.getNavigation().stop();
					this.setTarget(CollidesWithPlant(0.1f, 0f));
					this.setStealthTag(Stealth.FALSE);
				} else if (this.CollidesWithPlayer(1.5f) != null && !this.CollidesWithPlayer(1.5f).isCreative()) {
					this.setTarget(CollidesWithPlayer(1.5f));
					this.setStealthTag(Stealth.FALSE);
				}
			}
		}
	}

	protected void mobTick() {
		super.mobTick();


		var zombieObstacleEntity = this.getPassengerList()
				.stream()
				.filter(e -> e instanceof ZombieObstacleEntity)
				.map(e -> (ZombieObstacleEntity) e)
				.findFirst();

		var zombieShieldEntity = this.getPassengerList()
				.stream()
				.filter(e -> e instanceof ZombieShieldEntity)
				.map(e -> (ZombieShieldEntity) e)
				.findFirst();

		ZombiePropEntity pyramidPropEntity = null;
		ZombiePropEntity sergeantShieldEntity = null;
		ZombiePropEntity burningBooks = null;
		for (Entity entity : this.getPassengerList()) {
			if (entity.getType().equals(PvZEntity.PYRAMIDGEAR)) {
				pyramidPropEntity = (ZombiePropEntity) entity;
			}
			if (entity.getType().equals(PvZEntity.SERGEANTSHIELDGEAR)) {
				sergeantShieldEntity = (ZombiePropEntity) entity;
			}
			if (entity.getType().equals(PvZEntity.BOOKSHIELD)) {
				sergeantShieldEntity = (ZombiePropEntity) entity;
			}
		}

		if (this.isInsideWaterOrBubbleColumn() && zombieObstacleEntity.isPresent()){
			zombieObstacleEntity.get().stopRiding();
		}
		if (zombieObstacleEntity.isEmpty() && zombieShieldEntity.isEmpty() && this.CollidesWithObstacle(1f) != null && this.CollidesWithObstacle(1f).getType().equals(PvZEntity.TRASHCANBIN) && !this.CollidesWithObstacle(1f).hasVehicle() && !this.CollidesWithObstacle(1f).beingEaten && !this.isInsideWaterOrBubbleColumn()
		&& (this.getVariant().equals(BrowncoatVariants.TRASHCAN) ||
				this.getVariant().equals(BrowncoatVariants.TRASHCANHYPNO) ||
				this.getType().equals(PvZEntity.BROWNCOAT) ||
				this.getType().equals(PvZEntity.BROWNCOATHYPNO))){
			this.CollidesWithObstacle(1f).startRiding(this, true);
		}

		EntityAttributeInstance maxSpeedAttribute = this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
		if (this instanceof MummyEntity) {
			if (pyramidPropEntity == null &&this.getAttributes().hasModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, MAX_SPEED_UUID) &&
					!this.hasStatusEffect(ICE) && !this.hasStatusEffect(CHEESE) && !this.hasStatusEffect(GENERICSLOW) &&
					!this.hasStatusEffect(FROZEN) && !this.hasStatusEffect(BARK) && !this.hasStatusEffect(SHADOW) &&
					!this.hasStatusEffect(DISABLE) && !this.hasStatusEffect(STUN)) {
				assert maxSpeedAttribute != null;
				maxSpeedAttribute.removeModifier(MAX_SPEED_UUID);
			} else if (pyramidPropEntity != null) {
				if (!this.getAttributes().hasModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, MAX_SPEED_UUID)) {
					assert maxSpeedAttribute != null;
					maxSpeedAttribute.addPersistentModifier(createSpeedModifier(-0.03));
				}
			}
		}
		else if (this instanceof SargeantEntity && !this.getVariant().equals(BrowncoatVariants.BOOKBURN) && !this.getVariant().equals(BrowncoatVariants.BOOKBURNHYPNO)) {
			if (sergeantShieldEntity == null && this.getAttributes().hasModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, MAX_SPEED_UUID) &&
					!this.hasStatusEffect(ICE) && !this.hasStatusEffect(CHEESE) && !this.hasStatusEffect(GENERICSLOW) &&
					!this.hasStatusEffect(FROZEN) && !this.hasStatusEffect(BARK) && !this.hasStatusEffect(SHADOW) &&
					!this.hasStatusEffect(DISABLE) && !this.hasStatusEffect(STUN)) {
				assert maxSpeedAttribute != null;
				maxSpeedAttribute.removeModifier(MAX_SPEED_UUID);
			} else if (sergeantShieldEntity != null)  {
				if (!this.getAttributes().hasModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, MAX_SPEED_UUID)) {
					assert maxSpeedAttribute != null;
					maxSpeedAttribute.addPersistentModifier(createSpeedModifier(-0.03));
				}
			}
		}
		else {
			if (zombieObstacleEntity.isEmpty() &&
					this.getAttributes().hasModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, MAX_SPEED_UUID) &&
					!this.hasStatusEffect(ICE) && !this.hasStatusEffect(CHEESE) && !this.hasStatusEffect(GENERICSLOW) &&
					!this.hasStatusEffect(FROZEN) && !this.hasStatusEffect(BARK) && !this.hasStatusEffect(SHADOW) &&
					!this.hasStatusEffect(DISABLE) && !this.hasStatusEffect(STUN)) {
				assert maxSpeedAttribute != null;
				maxSpeedAttribute.removeModifier(MAX_SPEED_UUID);
			} else if (zombieObstacleEntity.isPresent())  {
				if (!this.getAttributes().hasModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, MAX_SPEED_UUID)) {
					assert maxSpeedAttribute != null;
					maxSpeedAttribute.addPersistentModifier(createSpeedModifier(-0.03));
				}
			}
		}
	}
	@Override
	protected void updatePassengerPosition(Entity passenger, PositionUpdater positionUpdater){

		if (this.hasPassenger(passenger) && passenger instanceof ZombieObstacleEntity) {
			float g = (float) ((this.isRemoved() ? 0.01F : this.method_52537(passenger)) + passenger.getHeightOffset(passenger));
			float f = 0.9F;

			Vec3d vec3d = new Vec3d((double) f, 0.0, 0.0).rotateY(-this.getYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
			passenger.setPosition(this.getX() + vec3d.x, this.getY() + (double) g, this.getZ() + vec3d.z);
			passenger.setBodyYaw(this.bodyYaw);
		}
		else if (this.hasPassenger(passenger) && passenger instanceof ZombieShieldEntity) {
			float g = (float) ((this.isRemoved() ? 0.01F : this.method_52537(passenger)) + passenger.getHeightOffset(passenger));
			float f = 0.6F;

			Vec3d vec3d = new Vec3d((double) f, 0.0, 0.0).rotateY(-this.getYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
			passenger.setPosition(this.getX() + vec3d.x, this.getY() + (double) g, this.getZ() + vec3d.z);
			passenger.setBodyYaw(this.bodyYaw);
		}
		else {
			float g = (float) ((this.isRemoved() ? 0.01F : this.method_52537(passenger)) + passenger.getHeightOffset(passenger));
			float f = 0.0F;

			Vec3d vec3d = new Vec3d((double) f, 0.0, 0.0).rotateY(-this.getYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
			passenger.setPosition(this.getX() + vec3d.x, this.getY() + (double) g, this.getZ() + vec3d.z);
			passenger.setBodyYaw(this.bodyYaw);
		}
	}

	@Override
	protected float method_52537(Entity entity) {
		return 0.0F;
	}
	/** /~*~//~*INTERACTION*~//~*~/ **/

	@Nullable
	@Override
	public ItemStack getPickBlockStack() {
		ItemStack itemStack;
		if (this.getType().equals(PvZEntity.SUMMERBASIC) ||
				this.getType().equals(PvZEntity.SUMMERBASICHYPNO) ||
				this.getType().equals(PvZEntity.SUMMERCONEHEAD) ||
				this.getType().equals(PvZEntity.SUMMERCONEHEADHYPNO) ||
				this.getType().equals(PvZEntity.SUMMERBUCKETHEAD) ||
				this.getType().equals(PvZEntity.SUMMERBUCKETHEADHYPNO)){
			if (this.getVariant().equals(BrowncoatVariants.CONEHEAD) || this.getVariant().equals(BrowncoatVariants.CONEHEADHYPNO)){
				itemStack = ModItems.SUMMERCONEEGG.getDefaultStack();
			}
			else if (this.getVariant().equals(BrowncoatVariants.BUCKETHEAD) || this.getVariant().equals(BrowncoatVariants.BUCKETHEADHYPNO)){
				itemStack = ModItems.SUMMERBUCKETEGG.getDefaultStack();
			}
			else{
				itemStack = ModItems.SUMMERBASICEGG.getDefaultStack();
			}
		}
		else {
			if (this.getVariant().equals(BrowncoatVariants.CONEHEAD) || this.getVariant().equals(BrowncoatVariants.CONEHEADHYPNO)) {
				itemStack = ModItems.CONEHEADEGG.getDefaultStack();
			} else if (this.getVariant().equals(BrowncoatVariants.BUCKETHEAD) || this.getVariant().equals(BrowncoatVariants.BUCKETHEADHYPNO)) {
				itemStack = ModItems.BUCKETHEADEGG.getDefaultStack();
			} else if (this.getVariant().equals(BrowncoatVariants.BRICKHEAD) || this.getVariant().equals(BrowncoatVariants.BRICKHEADHYPNO)) {
				itemStack = ModItems.BRICKHEADEGG.getDefaultStack();
			} else if (this.getVariant().equals(BrowncoatVariants.SCREENDOOR) || this.getVariant().equals(BrowncoatVariants.SCREENDOORHYPNO)) {
				itemStack = ModItems.SCREENDOOREGG.getDefaultStack();
			} else if (this.getVariant().equals(BrowncoatVariants.TRASHCAN) || this.getVariant().equals(BrowncoatVariants.TRASHCANHYPNO)) {
				itemStack = ModItems.TRASHCANEGG.getDefaultStack();
			} else {
				itemStack = ModItems.BROWNCOATEGG.getDefaultStack();
			}
		}
		return itemStack;
	}


	/** /~*~//~*ATTRIBUTES*~//~*~/ **/

	public static EntityAttributeModifier createSpeedModifier(double amount) {
		return new EntityAttributeModifier(
				MAX_SPEED_UUID,
				MOD_ID,
				amount,
				EntityAttributeModifier.Operation.ADDITION
		);
	}


	public boolean canWalkOnFluid(FluidState state) {
		return state.isIn(FluidTags.WATER);
	}

	protected boolean shouldSwimInFluids() {
		return true;
	}

	public static DefaultAttributeContainer.Builder createBrowncoatAttributes() {
		return HostileEntity.createAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 100.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.14D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, PVZCONFIG.nestedZombieHealth.browncoatH());
    }

	public static DefaultAttributeContainer.Builder createSummerAttributes() {
		return HostileEntity.createAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 100.0D)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.14D)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.0D)
				.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
				.add(EntityAttributes.GENERIC_MAX_HEALTH, PVZCONFIG.nestedZombieHealth.summerH());
	}

	protected SoundEvent getAmbientSound() {
		if (!this.getHypno() && !this.hasStatusEffect(PvZCubed.FROZEN) && !this.isFrozen && !this.isStunned && !this.hasStatusEffect(PvZCubed.DISABLE)) {
			return PvZSounds.PVZOMBIEMOANEVENT;
		}
		else {
			return null;
		}
	}

	public EntityGroup getGroup() {
		return EntityGroup.UNDEAD;
	}



	protected SoundEvent getStepSound() {
		return SoundEvents.ENTITY_ZOMBIE_STEP;
	}
	protected void playStepSound(BlockPos pos, BlockState state) {
		this.playSound(this.getStepSound(), 0.15F, 1.0F);
	}




	/** /~*~//~*DAMAGE HANDLER*~//~*~/ **/

	protected EntityType<?> hypnoType;
	protected void checkHypno(){
		if (this.getType().equals(PvZEntity.CONEHEAD)){
			hypnoType = PvZEntity.CONEHEADHYPNO;
		}
		else if (this.getType().equals(PvZEntity.BUCKETHEAD)){
			hypnoType = PvZEntity.BUCKETHEADHYPNO;
		}
		else if (this.getType().equals(PvZEntity.BRICKHEAD)){
			hypnoType = PvZEntity.BRICKHEADHYPNO;
		}
		else if (this.getType().equals(PvZEntity.SCREENDOOR)){
			hypnoType = PvZEntity.SCREENDOORHYPNO;
		}
		else if (this.getType().equals(PvZEntity.SARGEANTSHIELD)){
			hypnoType = PvZEntity.SARGEANTSHIELDHYPNO;
		}
		else if (this.getType().equals(PvZEntity.BOOKBURNER)){
			hypnoType = PvZEntity.BOOKBURNERHYPNO;
		}
		else if (this.getType().equals(PvZEntity.TRASHCAN)){
			hypnoType = PvZEntity.TRASHCANHYPNO;
		}
		else if (this.getType().equals(PvZEntity.SUMMERBASIC)){
			hypnoType = PvZEntity.SUMMERBASICHYPNO;
		}
		else if (this.getType().equals(PvZEntity.SUMMERCONEHEAD)){
			hypnoType = PvZEntity.SUMMERCONEHEADHYPNO;
		}
		else if (this.getType().equals(PvZEntity.SUMMERBUCKETHEAD)){
			hypnoType = PvZEntity.SUMMERBUCKETHEADHYPNO;
		}
		else if (this.getType().equals(PvZEntity.MUMMY)){
			hypnoType = PvZEntity.MUMMYHYPNO;
		}
		else if (this.getType().equals(PvZEntity.MUMMYCONE)){
			hypnoType = PvZEntity.MUMMYCONEHYPNO;
		}
		else if (this.getType().equals(PvZEntity.MUMMYBUCKET)){
			hypnoType = PvZEntity.MUMMYBUCKETHYPNO;
		}
		else if (this.getType().equals(PvZEntity.PYRAMIDHEAD)){
			hypnoType = PvZEntity.PYRAMIDHEADHYPNO;
		}
		else if (this.getType().equals(PvZEntity.TOMBRAISER)){
			hypnoType = PvZEntity.TOMBRAISERHYPNO;
		}
		else if (this.getType().equals(PvZEntity.POKER)){
			hypnoType = PvZEntity.POKERHYPNO;
		}
		else if (this.getType().equals(PvZEntity.POKERCONE)){
			hypnoType = PvZEntity.POKERCONEHYPNO;
		}
		else if (this.getType().equals(PvZEntity.POKERBUCKET)){
			hypnoType = PvZEntity.POKERBUCKETHYPNO;
		}
		else if (this.getType().equals(PvZEntity.POKERPAWN)){
			hypnoType = PvZEntity.POKERPAWNHYPNO;
		}
		else if (this.getType().equals(PvZEntity.POKERKNIGHT)){
			hypnoType = PvZEntity.POKERKNIGHTHYPNO;
		}
		else if (this.getType().equals(PvZEntity.POKERTOWER)){
			hypnoType = PvZEntity.POKERTOWERHYPNO;
		}
		else if (this.getType().equals(PvZEntity.POKERBISHOP)){
			hypnoType = PvZEntity.POKERBISHOPHYPNO;
		}
		else if (this.getType().equals(PvZEntity.PEASANTKNIGHT)){
			hypnoType = PvZEntity.PEASANTKNIGHTHYPNO;
		}
		else if (this.getType().equals(PvZEntity.FUTUREZOMBIE)){
			hypnoType = PvZEntity.FUTUREHYPNO;
		}
		else if (this.getType().equals(PvZEntity.FUTURECONE)){
			hypnoType = PvZEntity.FUTURECONEHYPNO;
		}
		else if (this.getType().equals(PvZEntity.FUTUREBUCKET)){
			hypnoType = PvZEntity.FUTUREBUCKETHYPNO;
		}
		else if (this.getType().equals(PvZEntity.HOLOHEAD)){
			hypnoType = PvZEntity.HOLOHEADHYPNO;
		}
		else if (this.getType().equals(PvZEntity.SARGEANT)){
			hypnoType = PvZEntity.SARGEANTHYPNO;
		}
		else if (this.getType().equals(PvZEntity.SARGEANTBOWL)){
			hypnoType = PvZEntity.SARGEANTBOWLHYPNO;
		}
		else if (this.getType().equals(PvZEntity.SARGEANTHELMET)){
			hypnoType = PvZEntity.SARGEANTHELMETHYPNO;
		}
		else {
			hypnoType = PvZEntity.BROWNCOATHYPNO;
		}
	}

	public boolean damage(DamageSource source, float amount) {
        if (!super.damage(source, amount)) {
            return false;
        } else if (!(this.getWorld() instanceof ServerWorld)) {
            return false;
        }
		else {
            ServerWorld serverWorld = (ServerWorld)this.getWorld();
            LivingEntity livingEntity = this.getTarget();
            if (livingEntity == null && source.getAttacker() instanceof LivingEntity) {
                livingEntity = (LivingEntity)source.getAttacker();
            }

            if (this.getRecentDamageSource().isType(PvZDamageTypes.HYPNO_DAMAGE) && !(this.getHypno())) {
				checkHypno();
                this.playSound(PvZSounds.HYPNOTIZINGEVENT, 1.5F, 1.0F);
                BrowncoatEntity hypnotizedZombie = (BrowncoatEntity) hypnoType.create(getWorld());
                hypnotizedZombie.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), this.getPitch());
                hypnotizedZombie.initialize(serverWorld, getWorld().getLocalDifficulty(hypnotizedZombie.getBlockPos()), SpawnReason.SPAWN_EGG, (EntityData)null, (NbtCompound) null);
                hypnotizedZombie.setAiDisabled(this.isAiDisabled());
				hypnotizedZombie.setHealth(this.getHealth());
                if (this.hasCustomName()) {
                    hypnotizedZombie.setCustomName(this.getCustomName());
                    hypnotizedZombie.setCustomNameVisible(this.isCustomNameVisible());
                }
				for (Entity entity1 : this.getPassengerList()) {
					if (entity1 instanceof ZombiePropEntity zpe) {
						ZombiePropEntity zombiePropEntity = new ZombiePropEntity((EntityType<? extends ZombiePropEntity>) zpe.getType(), this.getWorld());
						zombiePropEntity.initialize(serverWorld, this.getWorld().getLocalDifficulty(this.getBlockPos()), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
						zombiePropEntity.setHypno(IsHypno.TRUE);
						zombiePropEntity.setHealth(zpe.getHealth());
						zombiePropEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.bodyYaw, 0.0F);
						zombiePropEntity.startRiding(hypnotizedZombie);
					}
				}

				hypnotizedZombie.setPersistent();

				hypnotizedZombie.setHeadYaw(this.getHeadYaw());
                serverWorld.spawnEntityAndPassengers(hypnotizedZombie);
                this.remove(RemovalReason.DISCARDED);
            }

            return true;
        }
    }

	public boolean killedEntity(ServerWorld world, LivingEntity entity) {
		boolean bl = super.killedEntity(world, entity);
		if ((world.getDifficulty() == Difficulty.NORMAL || world.getDifficulty() == Difficulty.HARD) && entity instanceof VillagerEntity villagerEntity) {
			if (world.getDifficulty() != Difficulty.HARD && this.random.nextBoolean()) {
				return bl;
			}

			ZombieVillagerEntity zombieVillagerEntity = (ZombieVillagerEntity)villagerEntity.convertTo(EntityType.ZOMBIE_VILLAGER, false);
			if (zombieVillagerEntity != null) {
				zombieVillagerEntity.initialize(world, world.getLocalDifficulty(zombieVillagerEntity.getBlockPos()), SpawnReason.CONVERSION, new ZombieEntity.ZombieData(false, true), (NbtCompound)null);
				zombieVillagerEntity.setVillagerData(villagerEntity.getVillagerData());
				zombieVillagerEntity.setGossipData((NbtElement)villagerEntity.getGossip().serialize(NbtOps.INSTANCE));
				zombieVillagerEntity.setOfferData(villagerEntity.getOffers().toNbt());
				zombieVillagerEntity.setXp(villagerEntity.getExperience());
				if (!this.isSilent()) {
					world.syncWorldEvent((PlayerEntity)null, 1026, this.getBlockPos(), 0);
				}

				bl = false;
			}
		}

		return bl;
	}
}
