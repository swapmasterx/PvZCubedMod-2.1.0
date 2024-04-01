package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2.zombieking;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.PvZSounds;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import io.github.GrassyDev.pvzmod.registry.entity.variants.zombies.BrowncoatVariants;
import io.github.GrassyDev.pvzmod.registry.entity.variants.zombies.DefaultAndHypnoVariants;
import io.github.GrassyDev.pvzmod.registry.entity.variants.zombies.PokerVariants;
import io.github.GrassyDev.pvzmod.registry.entity.variants.zombies.ZombieKingVariants;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2.browncoat.darkages.PeasantEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2c.browncoat.fairytale.PokerEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieprops.plastichelmet.PlasticHelmetEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.PvZombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombiePropEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.TargetGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.mob.ZombieVillagerEntity;
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
import net.minecraft.registry.tag.FluidTags
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.util.GeckoLibUtil;

import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.util.GeckoLibUtil;


import static io.github.GrassyDev.pvzmod.PvZCubed.FROZEN;
import static io.github.GrassyDev.pvzmod.PvZCubed.PVZCONFIG;

public class ZombieKingEntity extends PvZombieEntity implements GeoAnimatable {

    private AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);
    private String controllerName = "walkingcontroller";

	public int spawningTicks;
	public boolean startSpawn;
	public int convertTicks = 0;
	public boolean convertIs;

	public ZombieKingEntity(EntityType<? extends ZombieKingEntity> entityType, World world) {
        super(entityType, world);

        this.experiencePoints = 12;
	}

	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(DATA_ID_TYPE_VARIANT, 0);
		this.dataTracker.startTracking(COLOR, 0);
	}

	@Override
	public void writeCustomDataToNbt(NbtCompound tag) {
		super.writeCustomDataToNbt(tag);
		tag.putInt("Variant", this.getTypeVariant());
		tag.putInt("Color", this.getColorVariant());
	}

	public void readCustomDataFromNbt(NbtCompound tag) {
		super.readCustomDataFromNbt(tag);
		this.dataTracker.set(DATA_ID_TYPE_VARIANT, tag.getInt("Variant"));
		this.dataTracker.set(COLOR, tag.getInt("Color"));
	}

	static {
	}

	@Environment(EnvType.CLIENT)
	public void handleStatus(byte status) {
		if (status != 2 && status != 60){
			super.handleStatus(status);
		}
		if (status == 113){
			this.startSpawn = true;
		}
		else if (status == 114){
			this.startSpawn = false;
		}
		else if (status == 115){
			this.convertIs = true;
		}
		else if (status == 116){
			this.convertIs = false;
		}

	}

	@Override
	public void setHypno(IsHypno hypno) {
		super.setHypno(hypno);
	}


	/** /~*~//~*VARIANTS*~//~*~/ **/

	private static final TrackedData<Integer> DATA_ID_TYPE_VARIANT =
			DataTracker.registerData(ZombieKingEntity.class, TrackedDataHandlerRegistry.INTEGER);

	private static final TrackedData<Integer> COLOR =
			DataTracker.registerData(ZombieKingEntity.class, TrackedDataHandlerRegistry.INTEGER);

	public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty,
								 SpawnReason spawnReason, @Nullable EntityData entityData,
								 @Nullable NbtCompound entityNbt) {
		if (this.getType().equals(PvZEntity.ZOMBIEKINGHYPNO)){
			setVariant(DefaultAndHypnoVariants.HYPNO);
			this.setHypno(IsHypno.TRUE);
		}
		else if (this.getType().equals(PvZEntity.REDZOMBIEKINGHYPNO)) {
			setVariant(DefaultAndHypnoVariants.HYPNO);
			this.setColor(ZombieKingVariants.RED);
			this.setHypno(IsHypno.TRUE);
		}else if (this.getType().equals(PvZEntity.BLACKZOMBIEKINGHYPNO)) {
			setVariant(DefaultAndHypnoVariants.HYPNO);
			this.setColor(ZombieKingVariants.BLACK);
			this.setHypno(IsHypno.TRUE);
		}else if (this.getType().equals(PvZEntity.REDZOMBIEKING)) {
			this.setColor(ZombieKingVariants.RED);
			this.createKingPieceProp();
		}else if (this.getType().equals(PvZEntity.BLACKZOMBIEKING)) {
			this.setColor(ZombieKingVariants.BLACK);
			this.createKingPieceProp();
		}
		else {
			setVariant(DefaultAndHypnoVariants.DEFAULT);
		}
		return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
	}

	private int getTypeVariant() {
		return this.dataTracker.get(DATA_ID_TYPE_VARIANT);
	}

	public DefaultAndHypnoVariants getVariant() {
		return DefaultAndHypnoVariants.byId(this.getTypeVariant() & 255);
	}

	public void setVariant(DefaultAndHypnoVariants variant) {
		this.dataTracker.set(DATA_ID_TYPE_VARIANT, variant.getId() & 255);
	}

	private int getColorVariant() {
		return this.dataTracker.get(COLOR);
	}

	public ZombieKingVariants getColor() {
		return ZombieKingVariants.byId(this.getColorVariant() & 255);
	}

	public void setColor(ZombieKingVariants variant) {
		this.dataTracker.set(COLOR, variant.getId() & 255);
	}

	public void createKingPieceProp(){
		if (world instanceof ServerWorld serverWorld) {
			PlasticHelmetEntity propentity = new PlasticHelmetEntity(PvZEntity.KINGPIECEGEAR, this.getWorld());
			propentity.initialize(serverWorld, this.getWorld().getLocalDifficulty(this.getBlockPos()), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
			propentity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.bodyYaw, 0.0F);
			propentity.startRiding(this);
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

	@Override
	public double getTick(Object object) {
		return 0;
	}

	private <P extends GeoAnimatable> PlayState predicate(AnimationState<P> event) {
		if (this.isInsideWaterOrBubbleColumn()) {
			if (this.startSpawn) {
				event.getController().setAnimation(RawAnimation.begin().thenLoop("zombieking.fallingwater"));
			} else if (this.convertIs) {
				event.getController().setAnimation(RawAnimation.begin().thenLoop("zombieking.crowningwater"));
			} else {
				event.getController().setAnimation(RawAnimation.begin().thenLoop("zombieking.idlewater"));
			}
		}
		else {
			if (this.startSpawn) {
				event.getController().setAnimation(RawAnimation.begin().thenLoop("zombieking.falling"));
			} else if (this.convertIs) {
				event.getController().setAnimation(RawAnimation.begin().thenLoop("zombieking.crowning"));
			} else {
				event.getController().setAnimation(RawAnimation.begin().thenLoop("zombieking.idle"));
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
		return PlayState.CONTINUE;
	}


	/** /~*~//~*AI*~//~*~/ **/

	protected void initGoals() {
		if (this.getType().equals(PvZEntity.ZOMBIEKINGHYPNO)) {
			initHypnoGoals();
		}
		else if (this.getType().equals(PvZEntity.REDZOMBIEKINGHYPNO)) {
			initRedHypnoGoals();
		}else if (this.getType().equals(PvZEntity.BLACKZOMBIEKINGHYPNO)) {
			initBlackHypnoGoals();
		}else if (this.getType().equals(PvZEntity.REDZOMBIEKING)) {
			initRedGoals();
		}else if (this.getType().equals(PvZEntity.BLACKZOMBIEKING)) {
			initBlackGoals();
		}
		else {
			initCustomGoals();
		}
    }

    protected void initCustomGoals() {
		////////// Zombie targets ///////
		this.targetSelector.add(1, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return (livingEntity instanceof PeasantEntity peasantEntity && !(peasantEntity.getHypno()) && peasantEntity.getVariant().equals(BrowncoatVariants.BROWNCOAT));
		}));
    }

	protected void initRedGoals() {
		////////// Zombie targets ///////
		this.targetSelector.add(1, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return (livingEntity instanceof PokerEntity pokerEntity && !(pokerEntity.getHypno()) && (pokerEntity.getPoker().equals(PokerVariants.SPADE) || pokerEntity.getPoker().equals(PokerVariants.CLUB)));
		}));
	}

	protected void initBlackGoals() {
		////////// Zombie targets ///////
		this.targetSelector.add(1, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return (livingEntity instanceof PokerEntity pokerEntity && !(pokerEntity.getHypno()) && (pokerEntity.getPoker().equals(PokerVariants.HEART) || pokerEntity.getPoker().equals(PokerVariants.DIAMOND)));
		}));
	}

	protected void initHypnoGoals(){
		////////// Hypnotized Zombie targets ///////
		this.targetSelector.add(1, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return (livingEntity instanceof PeasantEntity peasantEntity && peasantEntity.getVariant().equals(BrowncoatVariants.BROWNCOAT));
		}));
	}

	protected void initRedHypnoGoals(){
		////////// Hypnotized Zombie targets ///////
		this.targetSelector.add(1, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return (livingEntity instanceof PokerEntity pokerEntity && (pokerEntity.getPoker().equals(PokerVariants.SPADE) || pokerEntity.getPoker().equals(PokerVariants.CLUB)));
		}));
	}


	protected void initBlackHypnoGoals(){
		////////// Hypnotized Zombie targets ///////
		this.targetSelector.add(1, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return (livingEntity instanceof PokerEntity pokerEntity && (pokerEntity.getPoker().equals(PokerVariants.HEART) || pokerEntity.getPoker().equals(PokerVariants.DIAMOND)));
		}));
	}


	public void upgradeKnight(LivingEntity livingEntity) {
		if (this.getWorld() instanceof ServerWorld) {
			livingEntity.playSound(PvZSounds.KNIGHTTRANSFORMEVENT, 1F, 1.0F);
			ServerWorld serverWorld = (ServerWorld) this.getWorld();
			PeasantEntity knightEntity;
			if (this.getType().equals(PvZEntity.ZOMBIEKINGHYPNO)){
				knightEntity = (PeasantEntity) PvZEntity.PEASANTKNIGHTHYPNO.create(getWorld());
			}
			else {
				knightEntity = (PeasantEntity) PvZEntity.PEASANTKNIGHT.create(getWorld());
			}
			knightEntity.refreshPositionAndAngles(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), livingEntity.getYaw(), livingEntity.getPitch());
			knightEntity.initialize(serverWorld, this.getWorld().getLocalDifficulty(knightEntity.getBlockPos()), SpawnReason.SPAWN_EGG, (EntityData)null, (NbtCompound) null);

			if (knightEntity.getHypno() && this.getHypno()){
				knightEntity.createKnightProp().setHypno(IsHypno.TRUE);
				for (Entity entity1 : livingEntity.getPassengerList()) {
					if (entity1 instanceof ZombiePropEntity zpe) {
						zpe.setHypno(IsHypno.TRUE);
						zpe.startRiding(knightEntity);
					}
				}
			}
			knightEntity.setHealth(livingEntity.getHealth());
			if (livingEntity.hasCustomName()) {
				knightEntity.setCustomName(livingEntity.getCustomName());
				knightEntity.setCustomNameVisible(livingEntity.isCustomNameVisible());
			}

			knightEntity.setPersistent();
			knightEntity.setRainbowTag(Rainbow.TRUE);
			knightEntity.rainbowTicks = 70;
			serverWorld.spawnEntityAndPassengers(knightEntity);
			knightEntity.setVelocity(livingEntity.getVelocity());
			knightEntity.copyPositionAndRotation(livingEntity);
			livingEntity.remove(RemovalReason.DISCARDED);
		}
	}


	/** /~*~//~*TICKING*~//~*~/ **/

	public void tick() {
		if (this.getTarget() instanceof PeasantEntity peasantEntity && (peasantEntity.getVariant().equals(BrowncoatVariants.PEASANTKNIGHT) || peasantEntity.getVariant().equals(BrowncoatVariants.PEASANTKNIGHTHYPNO))) {
			this.setTarget(null);
		}
		if (this.getColor().equals(ZombieKingVariants.RED)){
			if (this.getTarget() instanceof PokerEntity pokerEntity && (pokerEntity.getPoker().equals(PokerVariants.HEART) || pokerEntity.getPoker().equals(PokerVariants.DIAMOND))){
				this.setTarget(null);
			}
		}
		if (this.getColor().equals(ZombieKingVariants.BLACK)){
			if (this.getTarget() instanceof PokerEntity pokerEntity && (pokerEntity.getPoker().equals(PokerVariants.SPADE) || pokerEntity.getPoker().equals(PokerVariants.CLUB))){
				this.setTarget(null);
			}
		}
		super.tick();
		double random = Math.random();
		if (--spawningTicks <= 0){
			--convertTicks;
		}
		if (convertTicks == 25 * animationMultiplier && this.getTarget() instanceof PeasantEntity peasantEntity && (peasantEntity.getVariant().equals(BrowncoatVariants.BROWNCOAT) || peasantEntity.getVariant().equals(BrowncoatVariants.BROWNCOATHYPNO)) && !this.hasStatusEffect(FROZEN) && !this.hasStatusEffect(PvZCubed.STUN) && !this.hasStatusEffect(PvZCubed.DISABLE)){
			this.upgradeKnight(peasantEntity);
		}
		if (convertTicks == 25 * animationMultiplier && this.getTarget() instanceof PokerEntity pokerEntity && !this.hasStatusEffect(FROZEN) && !this.hasStatusEffect(PvZCubed.STUN) && !this.hasStatusEffect(PvZCubed.DISABLE)){
			double random2 = Math.random();
			if (this.getColor().equals(ZombieKingVariants.RED)){
				if (random2 <= 0.5) {
					pokerEntity.setVariant(PokerVariants.HEART);
				}
				else {
					pokerEntity.setVariant(PokerVariants.DIAMOND);
				}
			}
			else {
				if (random2 <= 0.5) {
					pokerEntity.setVariant(PokerVariants.SPADE);
				}
				else {
					pokerEntity.setVariant(PokerVariants.CLUB);
				}
			}
			pokerEntity.playSound(PvZSounds.KNIGHTTRANSFORMEVENT, 1F, 1.0F);
			pokerEntity.setRainbowTag(Rainbow.TRUE);
			pokerEntity.rainbowTicks = 40;
			pokerEntity.setHealth(pokerEntity.getMaxHealth());
			if (this.getHypno()){
				pokerEntity.damage(PvZCubed.HYPNO_DAMAGE, 0f);
			}
		}
		if (convertTicks <= 0 && this.getTarget() instanceof PeasantEntity peasantEntity && random <= 0.01 && (peasantEntity.getVariant().equals(BrowncoatVariants.BROWNCOAT) || peasantEntity.getVariant().equals(BrowncoatVariants.BROWNCOATHYPNO)) && !this.hasStatusEffect(PvZCubed.FROZEN) && !this.hasStatusEffect(PvZCubed.STUN) && !this.hasStatusEffect(PvZCubed.DISABLE)) {
			this.convertTicks = 45 * animationMultiplier;
		}
		if (convertTicks <= 0 && this.getTarget() instanceof PokerEntity && random <= 0.01 && !this.hasStatusEffect(PvZCubed.FROZEN) && !this.hasStatusEffect(PvZCubed.STUN) && !this.hasStatusEffect(PvZCubed.DISABLE)) {
			this.convertTicks = 45 * animationMultiplier;
		}
		if (this.hasStatusEffect(PvZCubed.FROZEN) || this.hasStatusEffect(PvZCubed.STUN) || this.hasStatusEffect(PvZCubed.DISABLE)){
			this.convertTicks = 0;
		}
	}

	protected void mobTick() {
		super.mobTick();
		if (spawningTicks > 0){
			this.getWorld().sendEntityStatus(this, (byte) 113);
		}
		else {
			this.getWorld().sendEntityStatus(this, (byte) 114);
		}
		if (convertTicks > 0) {
			this.getWorld().sendEntityStatus(this, (byte) 115);
		}
		else {
			this.getWorld().sendEntityStatus(this, (byte) 116);
		}
		if (this.hasStatusEffect(PvZCubed.ICE)){
			this.animationMultiplier = 2;
		}
		else {
			this.animationMultiplier = 1;
		}
	}

	/** /~*~//~*INTERACTION*~//~*~/ **/

	@Nullable
	@Override
	public ItemStack getPickBlockStack() {
		if (this.getColor().equals(ZombieKingVariants.RED)) {
			return ModItems.REDZOMBIEKINGEGG.getDefaultStack();
		} else if (this.getColor().equals(ZombieKingVariants.BLACK)) {
			return ModItems.BLACKZOMBIEKINGEGG.getDefaultStack();
		} else {
			return ModItems.ZOMBIEKINGEGG.getDefaultStack();
		}
	}


	/** /~*~//~*ATTRIBUTES*~//~*~/ **/


	@Override
	public double getMountedHeightOffset() {
		return 0;
	}

	public boolean canWalkOnFluid(FluidState state) {
		return state.isIn(FluidTags.WATER);
	}

	protected boolean shouldSwimInFluids() {
		return true;
	}

	public static DefaultAttributeContainer.Builder createZombieKingAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 15.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, PVZCONFIG.nestedZombieHealth.zombiekingH());
    }

	protected SoundEvent getAmbientSound() {
		if (!this.getHypno() && !this.hasStatusEffect(PvZCubed.FROZEN) && !this.isFrozen && !this.isStunned && !this.hasStatusEffect(PvZCubed.DISABLE)) {
			return PvZSounds.ZOMBIEMOANEVENT;
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
	protected void checkHypno() {
		if (this.getType().equals(PvZEntity.REDZOMBIEKING)) {
			hypnoType = PvZEntity.REDZOMBIEKINGHYPNO;
		} else if (this.getType().equals(PvZEntity.BLACKZOMBIEKING)) {
			hypnoType = PvZEntity.BLACKZOMBIEKINGHYPNO;
		} else {
			hypnoType = PvZEntity.ZOMBIEKINGHYPNO;
		}
	}

	public boolean damage(DamageSource source, float amount) {
        if (!super.damage(source, amount)) {
            return false;
        } else if (!(this.getWorld() instanceof ServerWorld)) {
            return false;
        } else {
            ServerWorld serverWorld = (ServerWorld)this.getWorld();
            LivingEntity livingEntity = this.getTarget();
            if (livingEntity == null && source.getAttacker() instanceof LivingEntity) {
                livingEntity = (LivingEntity)source.getAttacker();
            }

            if (this.getRecentDamageSource() == PvZCubed.HYPNO_DAMAGE && !(this.getHypno())) {
				checkHypno();
                this.playSound(PvZSounds.HYPNOTIZINGEVENT, 1.5F, 1.0F);
                ZombieKingEntity hypnotizedZombie = (ZombieKingEntity) PvZEntity.ZOMBIEKINGHYPNO.create(getWorld());
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
						zpe.setHypno(IsHypno.TRUE);
						zpe.startRiding(hypnotizedZombie);
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

	public boolean onKilledOther(ServerWorld serverWorld, LivingEntity livingEntity) {
		super.onKilledOther(serverWorld, livingEntity);
		boolean bl = super.onKilledOther(serverWorld, livingEntity);
		if ((serverWorld.getDifficulty() == Difficulty.NORMAL || serverWorld.getDifficulty() == Difficulty.HARD) && livingEntity instanceof VillagerEntity) {
			if (serverWorld.getDifficulty() != Difficulty.HARD && this.random.nextBoolean()) {
				return bl;
			}

			VillagerEntity villagerEntity = (VillagerEntity) livingEntity;
			ZombieVillagerEntity zombieVillagerEntity = (ZombieVillagerEntity) villagerEntity.convertTo(EntityType.ZOMBIE_VILLAGER, false);
			zombieVillagerEntity.initialize(serverWorld, servergetWorld().getLocalDifficulty(zombieVillagerEntity.getBlockPos()), SpawnReason.SPAWN_EGG, new ZombieEntity.ZombieData(false, true), (NbtCompound) null);
			zombieVillagerEntity.setVillagerData(villagerEntity.getVillagerData());
			zombieVillagerEntity.setGossipData((NbtElement) villagerEntity.getGossip().serialize(NbtOps.INSTANCE).getValue());
			zombieVillagerEntity.setOfferData(villagerEntity.getOffers().toNbt());
			zombieVillagerEntity.setXp(villagerEntity.getExperience());
			if (!this.isSilent()) {
				serverWorld.syncWorldEvent((PlayerEntity) null, 1026, this.getBlockPos(), 0);
			}
		}

		return bl;
	}
}
