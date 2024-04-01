package io.github.GrassyDev.pvzmod.registry.entity.gravestones.darkagesgrave;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.PvZSounds;
import io.github.GrassyDev.pvzmod.registry.entity.gravestones.GraveEntity;
import io.github.GrassyDev.pvzmod.registry.entity.variants.graves.GraveDifficulty;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz1.imp.modernday.ImpEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2.browncoat.darkages.PeasantEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2.flagzombie.darkages.FlagPeasantEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2.imp.announcer.AnnouncerImpEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2c.pumpkinzombie.PumpkinZombieEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.*;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.Objects;

import static io.github.GrassyDev.pvzmod.PvZCubed.PVZCONFIG;

public class DarkAgesGraveEntity extends GraveEntity implements GeoAnimatable {

	private String controllerName = "walkingcontroller";

	private int spawnCounter;



	double tiltchance = this.random.nextDouble();

    private AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);


    public DarkAgesGraveEntity(EntityType<DarkAgesGraveEntity> entityType, World world) {
        super(entityType, world);

        this.experiencePoints = 25;
    }

	static {
	}

	@Environment(EnvType.CLIENT)
	public void handleStatus(byte status) {
		if (status != 2 && status != 60){
			super.handleStatus(status);
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
	private <P extends GeoAnimatable> PlayState predicate(AnimationState<P> event) {
		if (beingEaten){

			event.getController().setAnimation(RawAnimation.begin().thenLoop("obstacle.eating"));
		}
		else if (tiltchance <= 0.5) {
			event.getController().setAnimation(RawAnimation.begin().thenLoop("gravestone.idle"));
		}
		else {
			event.getController().setAnimation(RawAnimation.begin().thenLoop("gravestone.idle2"));
		}
		return PlayState.CONTINUE;
	}



	/** /~*~//~*AI*~//~*~/ **/

	protected void initGoals() {
        this.targetSelector.add(1, new RevengeGoal(this, new Class[0]));
        this.initCustomGoals();
    }

    protected void initCustomGoals() {
        this.goalSelector.add(1, new DarkAgesGraveEntity.summonZombieGoal(this));
    }


	/** /~*~//~*TICKING*~//~*~/ **/

	public void tick() {
		super.tick();
		this.setTarget(this.getWorld().getClosestPlayer(this.getX(), this.getY(), this.getZ(), 100, true));
		LocalDifficulty localDifficulty = getWorld().getLocalDifficulty(this.getBlockPos());
		double difficulty = 0;
		if (this.getVariant().equals(GraveDifficulty.NONE)){
			difficulty = localDifficulty.getLocalDifficulty();
				if (difficulty >= 2.1){
					difficulty = 2.1;
					if (getWorld().getDifficulty().equals(Difficulty.HARD)){
						difficulty = difficulty + difficultymodifier;
					}
				}
		}
		else if (this.getVariant().equals(GraveDifficulty.EASY)){
			difficulty = 1.0;
		}
		else if (this.getVariant().equals(GraveDifficulty.EASYMED)){
			difficulty = 1.5;
		}
		else if (this.getVariant().equals(GraveDifficulty.MED)){
			difficulty = 1.6;
		}
		else if (this.getVariant().equals(GraveDifficulty.MEDHARD)){
			difficulty = 1.8;
		}
		else if (this.getVariant().equals(GraveDifficulty.HARD)){
			difficulty = 2.1;
		}
		else if (this.getVariant().equals(GraveDifficulty.SUPERHARD)){
			difficulty = 3;
		}
		else if (this.getVariant().equals(GraveDifficulty.NIGHTMARE)){
			difficulty = 4;
		}
		else if (this.getVariant().equals(GraveDifficulty.CRAAAZY)){
			difficulty = 5;
		}
		if (this.spawnCounter == 1 && getWorld().getTime() < 24000) {
			this.kill();
		}
		if (this.spawnCounter == 1 && getWorld().getTime() < 24000) {
			this.kill();
		}
		else if (this.spawnCounter == 2 && difficulty <= 1.509 + difficultymodifier){
			if (!this.isInfinite()) {
				this.kill();
			}
		}
		else if (this.spawnCounter == 3 && difficulty <= 1.709 + difficultymodifier){
			if (!this.isInfinite()) {
				this.kill();
			}
		}
		else if (this.spawnCounter == 4 && difficulty <= 1.909 + difficultymodifier){
			if (!this.isInfinite()) {
				this.kill();
			}
		}
		else if (this.spawnCounter == 5 && difficulty <= 2.109 + difficultymodifier){
			if (!this.isInfinite()) {
				this.kill();
			}
		}
		else if (this.spawnCounter == 6 && difficulty >= 2.309 + difficultymodifier){
			if (!this.isInfinite()) {
				this.kill();
			}
		}
		else if (this.spawnCounter > 7){
			if (!this.isInfinite()) {
				this.kill();
			}
		}
		if (this.getWorld().isClient && this.isSpellcasting()) {
			Spell spell = this.getSpell();
			float g = this.bodyYaw * 0.017453292F + MathHelper.cos((float)this.age * 0.6662F) * 0.25F;
			float h = MathHelper.cos(g);
			float i = MathHelper.sin(g);
			this.getWorld().addParticle(ParticleTypes.SMOKE, this.getX() + (double)h * 0.6, this.getY(), this.getZ() + (double)i * 0.6, 0, 0.0125, 0);
			this.getWorld().addParticle(ParticleTypes.SMOKE, this.getX() - (double)h * 0.6, this.getY(), this.getZ() - (double)i * 0.6, 0, 0.0125, 0);
		}
	}


	/** /~*~//~*ATTRIBUTES*~//~*~/ **/

	public static DefaultAttributeContainer.Builder createDarkAgesGraveAttributes() {
        return HostileEntity.createAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 100.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, PVZCONFIG.nestedZombieHealth.darkAgesGraveH());
    }

	protected SoundEvent getDeathSound() {
		return SoundEvents.BLOCK_ANCIENT_DEBRIS_BREAK;
	}

	protected SoundEvent getHurtSound(DamageSource source) {
		return SoundEvents.BLOCK_BASALT_HIT;
	}






	/** /~*~//~*SPAWNING*~//~*~/ **/

	public static boolean canDarkAgesGraveSpawn(EntityType<? extends DarkAgesGraveEntity> type, ServerWorldAccess world, SpawnReason spawnReason, BlockPos pos, RandomGenerator random) {
		BlockPos blockPos = pos.down();
		float cavespawn = random.nextFloat();
		if (cavespawn <= 0.66) {
			return world.getDifficulty() != Difficulty.PEACEFUL &&
					!world.getBlockState(pos).getMaterial().isLiquid() &&
					world.toServerWorld().getTime() > 60000  &&
					pos.getY() > 50 &&
					(world.getAmbientDarkness() >= 2 ||
							world.getLightLevel(LightType.SKY, pos) < 2) &&
					!world.getBlockState(blockPos).getBlock().hasDynamicBounds() &&
					!checkVillager(Vec3d.ofCenter(pos), world) &&
					!checkPlant(Vec3d.ofCenter(pos), world) && Objects.requireNonNull(world.getServer()).getGameRules().getBoolean(PvZCubed.SHOULD_GRAVE_SPAWN);
		}
		else {
			return world.getDifficulty() != Difficulty.PEACEFUL &&
					!world.getBlockState(pos).getMaterial().isLiquid() &&
					world.toServerWorld().getTime() > 60000 &&
					(world.getAmbientDarkness() >= 2 ||
							world.getLightLevel(LightType.SKY, pos) < 2) &&
					!world.getBlockState(blockPos).getBlock().hasDynamicBounds() &&
					!checkVillager(Vec3d.ofCenter(pos), world) &&
					!checkPlant(Vec3d.ofCenter(pos), world) && Objects.requireNonNull(world.getServer()).getGameRules().getBoolean(PvZCubed.SHOULD_GRAVE_SPAWN);
		}
	}

	/** /~*~//~*GOALS*~//~*~/ **/

	protected SoundEvent getCastSpellSound() {
        return PvZSounds.ENTITYRISINGEVENT;
    }

<<<<<<< Updated upstream
=======

	@Override
	public double getTick(Object object) {
		return 0;
	}

>>>>>>> Stashed changes
	protected abstract class CastSpellGoal extends Goal {
		protected int spellCooldown;
		protected int startTime;

		protected CastSpellGoal() {
		}

		public boolean canStart() {
			LivingEntity livingEntity = DarkAgesGraveEntity.this.getTarget();
			if (livingEntity != null && livingEntity.isAlive()) {
				if (DarkAgesGraveEntity.this.isSpellcasting()) {
					return false;
				} else {
					return DarkAgesGraveEntity.this.age >= this.startTime;
				}
			} else {
				return false;
			}
		}

		public boolean shouldContinue() {
			LivingEntity livingEntity = DarkAgesGraveEntity.this.getTarget();
			return livingEntity != null && livingEntity.isAlive() && this.spellCooldown > 0;
		}

		public void start() {
			this.spellCooldown = this.getTickCount(this.getInitialCooldown());
			DarkAgesGraveEntity.this.spellTicks = this.getSpellTicks();
			this.startTime = DarkAgesGraveEntity.this.age + this.startTimeDelay();
			SoundEvent soundEvent = this.getSoundPrepare();
			if (soundEvent != null) {
				DarkAgesGraveEntity.this.playSound(soundEvent, 0.9F, 1.0F);
			}

			DarkAgesGraveEntity.this.setSpell(this.getSpell());
		}

		public void tick() {
			--this.spellCooldown;
			if (this.spellCooldown == 0) {
				this.castSpell();
				DarkAgesGraveEntity.this.addStatusEffect((new StatusEffectInstance(StatusEffects.GLOWING, 100, 1)));
				DarkAgesGraveEntity.this.playSound(DarkAgesGraveEntity.this.getCastSpellSound(), 0.9F, 1.0F);
			}

		}

		protected abstract void castSpell();

		protected int getInitialCooldown() {
			return 20;
		}

		protected abstract int getSpellTicks();

		protected abstract int startTimeDelay();

		@Nullable
		protected abstract SoundEvent getSoundPrepare();

		protected abstract Spell getSpell();
	}


	/** /~*~//~*INTERACTION*~//~*~/ **/

	@Nullable
	@Override
	public ItemStack getPickBlockStack() {
		return ModItems.DARKAGESGRAVESPAWN.getDefaultStack();
	}

    class summonZombieGoal extends CastSpellGoal {
        private final TargetPredicate closeZombiePredicate;

		private final DarkAgesGraveEntity darkAgesGraveEntity;

        private summonZombieGoal(DarkAgesGraveEntity darkAgesGraveEntity) {
            super();
			this.darkAgesGraveEntity = darkAgesGraveEntity;
			this.closeZombiePredicate = (TargetPredicate.createNonAttackable().setBaseMaxDistance(16.0D).ignoreVisibility().ignoreDistanceScalingFactor());
		}

		public boolean canStart() {
			return super.canStart();
		}

        protected int getSpellTicks() {
            return 100;
        }

        protected int startTimeDelay() {
            return 400;
        }

        protected void castSpell() {
			graveWeight = 0;
            ServerWorld serverWorld = (ServerWorld) DarkAgesGraveEntity.this.getWorld();
			LocalDifficulty localDifficulty = world.getLocalDifficulty(this.darkAgesGraveEntity.getBlockPos());
			double difficulty = 0;
			if (this.darkAgesGraveEntity.getVariant().equals(GraveDifficulty.NONE)){
				difficulty = localDifficulty.getLocalDifficulty();
				if (difficulty >= 2.1){
					difficulty = 2.1;
					if (world.getDifficulty().equals(Difficulty.HARD)){
						difficulty = difficulty + difficultymodifier;
					}
				}
			}
			else if (this.darkAgesGraveEntity.getVariant().equals(GraveDifficulty.EASY)){
				difficulty = 1.0;
			}
			else if (this.darkAgesGraveEntity.getVariant().equals(GraveDifficulty.EASYMED)){
				difficulty = 1.5;
			}
			else if (this.darkAgesGraveEntity.getVariant().equals(GraveDifficulty.MED)){
				difficulty = 1.6;
			}
			else if (this.darkAgesGraveEntity.getVariant().equals(GraveDifficulty.MEDHARD)){
				difficulty = 1.8;
			}
			else if (this.darkAgesGraveEntity.getVariant().equals(GraveDifficulty.HARD)){
				difficulty = 2.1;
			}
			else if (this.darkAgesGraveEntity.getVariant().equals(GraveDifficulty.SUPERHARD)){
				difficulty = 3;
			}
			else if (this.darkAgesGraveEntity.getVariant().equals(GraveDifficulty.NIGHTMARE)){
				difficulty = 4;
			}
			else if (this.darkAgesGraveEntity.getVariant().equals(GraveDifficulty.CRAAAZY)){
				difficulty = 5;
			}
			double probability = random.nextDouble() * Math.pow(difficulty / 2, -1 * (difficulty / 2));
			double probability11 = random.nextDouble() * Math.pow(difficulty / 2, -1 * (difficulty / 2));
			double probability2 = random.nextDouble() * Math.pow(difficulty / 2, -1 * (difficulty / 2));
			double probability21 = random.nextDouble() * Math.pow(difficulty / 2, -1 * (difficulty / 2));
			double probability22 = random.nextDouble() * Math.pow(difficulty / 2, -1 * (difficulty / 2));
			double probability3 = random.nextDouble() * Math.pow(difficulty / 2, -1 * (difficulty / 2));
			double probability4 = random.nextDouble() * Math.pow(difficulty / 2, -1 * (difficulty / 2));
			double probability5 = random.nextDouble() * Math.pow(difficulty / 2, -1 * (difficulty / 2));
			double probability6 = random.nextDouble() * Math.pow(difficulty / 2, -1 * (difficulty / 2));
			double probability7 = random.nextDouble() * Math.pow(difficulty / 2, -1 * (difficulty / 2));
			double probability8 = random.nextDouble() * Math.pow(difficulty / 2, -1 * (difficulty / 2));
			double probability9 = random.nextDouble() * Math.pow(difficulty / 2, -1 * (difficulty / 2));
			double probability10 = random.nextDouble() * Math.pow(difficulty / 2, -1 * (difficulty / 2));
			double probability12 = random.nextDouble() * Math.pow(difficulty / 2, -1 * (difficulty / 2));
			double probability13 = random.nextDouble() * Math.pow(difficulty / 2, -1 * (difficulty / 2));
			double probability14 = random.nextDouble() * Math.pow(difficulty / 2, -1 * (difficulty / 2));

			int zombiePos = -2 + DarkAgesGraveEntity.this.random.nextInt(5);
			int zombiePosZ = -2 + DarkAgesGraveEntity.this.random.nextInt(5);
			if (DarkAgesGraveEntity.this.is1x1()){
				zombiePos = 0;
				zombiePosZ = 0;
			}

			for(int b = 0; b < 1; ++b) { // 100% x1 Browncoat
				if (!DarkAgesGraveEntity.this.is1x1()) {
					zombiePosZ = DarkAgesGraveEntity.this.random.range(-1, 1);
					zombiePos = DarkAgesGraveEntity.this.random.range(-1, 1);
				}
				if (DarkAgesGraveEntity.this.isChallengeGrave()) {
					zombiePosZ = DarkAgesGraveEntity.this.random.range(-3, 3);
					zombiePos = DarkAgesGraveEntity.this.random.range(-3, 3);
				}
				BlockPos blockPos = DarkAgesGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
				PeasantEntity PeasantEntity = (PeasantEntity)PvZEntity.PEASANT.create(DarkAgesGraveEntity.this.getWorld());
				PeasantEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
				PeasantEntity.initialize(serverWorld, DarkAgesGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData)null, (NbtCompound)null);
				PeasantEntity.setOwner(DarkAgesGraveEntity.this);
				PeasantEntity.defenseMultiplier = DarkAgesGraveEntity.this.defenseMultiplier;
				serverWorld.spawnEntityAndPassengers(PeasantEntity);
			}
			if (probability <= 0.25 / halfModifier * survChance) { // 25% x1 Conehead
				for (int c = 0; c < 1; ++c) {
					if (!DarkAgesGraveEntity.this.is1x1()) {
						zombiePosZ = DarkAgesGraveEntity.this.random.range(-1, 1);
						zombiePos = DarkAgesGraveEntity.this.random.range(-1, 1);
					}
					if (DarkAgesGraveEntity.this.isChallengeGrave()) {
						zombiePosZ = DarkAgesGraveEntity.this.random.range(-3, 3);
						zombiePos = DarkAgesGraveEntity.this.random.range(-3, 3);
					}
					BlockPos blockPos = DarkAgesGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
					PeasantEntity coneheadEntity = (PeasantEntity) PvZEntity.PEASANTCONE.create(DarkAgesGraveEntity.this.getWorld());
					coneheadEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
					coneheadEntity.initialize(serverWorld, DarkAgesGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
					coneheadEntity.setOwner(DarkAgesGraveEntity.this);
					coneheadEntity.defenseMultiplier = DarkAgesGraveEntity.this.defenseMultiplier;
					serverWorld.spawnEntityAndPassengers(coneheadEntity);
				}
				for(int b = 0; b < 1; ++b) { // 100% x1 Browncoat
					if (!DarkAgesGraveEntity.this.is1x1()) {
						zombiePosZ = DarkAgesGraveEntity.this.random.range(-1, 1);
						zombiePos = DarkAgesGraveEntity.this.random.range(-1, 1);
					}
					if (DarkAgesGraveEntity.this.isChallengeGrave()) {
						zombiePosZ = DarkAgesGraveEntity.this.random.range(-3, 3);
						zombiePos = DarkAgesGraveEntity.this.random.range(-3, 3);
					}
					BlockPos blockPos = DarkAgesGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
					PeasantEntity PeasantEntity = (PeasantEntity)PvZEntity.PEASANT.create(DarkAgesGraveEntity.this.getWorld());
					PeasantEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
					PeasantEntity.initialize(serverWorld, DarkAgesGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData)null, (NbtCompound)null);
					PeasantEntity.setOwner(DarkAgesGraveEntity.this);
					PeasantEntity.defenseMultiplier = DarkAgesGraveEntity.this.defenseMultiplier;
				serverWorld.spawnEntityAndPassengers(PeasantEntity);
				}
			}
			if (serverWorld.toServerWorld().getTime() > 24000) {
				if (probability2 <= 0.05 / halfModifier * survChance) { // 5% x1 Buckethead
					for (int u = 0; u < 1; ++u) {
						if (!DarkAgesGraveEntity.this.is1x1()) {
							zombiePosZ = DarkAgesGraveEntity.this.random.range(-1, 1);
							zombiePos = DarkAgesGraveEntity.this.random.range(-1, 1);
						}
						if (DarkAgesGraveEntity.this.isChallengeGrave()) {
							zombiePosZ = DarkAgesGraveEntity.this.random.range(-3, 3);
							zombiePos = DarkAgesGraveEntity.this.random.range(-3, 3);
						}
						BlockPos blockPos = DarkAgesGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
						PeasantEntity bucketheadEntity = (PeasantEntity) PvZEntity.PEASANTBUCKET.create(DarkAgesGraveEntity.this.getWorld());
						bucketheadEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
						bucketheadEntity.initialize(serverWorld, DarkAgesGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
						bucketheadEntity.setOwner(DarkAgesGraveEntity.this);
						bucketheadEntity.defenseMultiplier = DarkAgesGraveEntity.this.defenseMultiplier;
						serverWorld.spawnEntityAndPassengers(bucketheadEntity);
					}
					for (int c = 0; c < 2 / halfModifier; ++c) { // 100% x2 Conehead
						if (!DarkAgesGraveEntity.this.is1x1()) {
							zombiePosZ = DarkAgesGraveEntity.this.random.range(-1, 1);
							zombiePos = DarkAgesGraveEntity.this.random.range(-1, 1);
						}
						if (DarkAgesGraveEntity.this.isChallengeGrave()) {
							zombiePosZ = DarkAgesGraveEntity.this.random.range(-3, 3);
							zombiePos = DarkAgesGraveEntity.this.random.range(-3, 3);
						}
						BlockPos blockPos = DarkAgesGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
						PeasantEntity coneheadEntity = (PeasantEntity) PvZEntity.PEASANTCONE.create(DarkAgesGraveEntity.this.getWorld());
						coneheadEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
						coneheadEntity.initialize(serverWorld, DarkAgesGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
						coneheadEntity.setOwner(DarkAgesGraveEntity.this);
						coneheadEntity.defenseMultiplier = DarkAgesGraveEntity.this.defenseMultiplier;
					serverWorld.spawnEntityAndPassengers(coneheadEntity);
					}
				}
				if (difficulty >= 1.519 + difficultymodifier || isUnlock()) {
					if (probability11 <= 0.05 / halfModifier * survChance) { // 5% x2 Conehead
						for (int c = 0; c < 2 / halfModifier; ++c) {
							if (!DarkAgesGraveEntity.this.is1x1()) {
								zombiePosZ = DarkAgesGraveEntity.this.random.range(-1, 1);
								zombiePos = DarkAgesGraveEntity.this.random.range(-1, 1);
							}
							if (DarkAgesGraveEntity.this.isChallengeGrave()) {
								zombiePosZ = DarkAgesGraveEntity.this.random.range(-3, 3);
								zombiePos = DarkAgesGraveEntity.this.random.range(-3, 3);
							}
							BlockPos blockPos = DarkAgesGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							PeasantEntity coneheadEntity = (PeasantEntity) PvZEntity.PEASANTCONE.create(DarkAgesGraveEntity.this.getWorld());
							coneheadEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
							coneheadEntity.initialize(serverWorld, DarkAgesGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							coneheadEntity.setOwner(DarkAgesGraveEntity.this);
							coneheadEntity.defenseMultiplier = DarkAgesGraveEntity.this.defenseMultiplier;
					serverWorld.spawnEntityAndPassengers(coneheadEntity);
						}
						for(int b = 0; b < 2 / halfModifier; ++b) { // 100% x2 Browncoat
							if (!DarkAgesGraveEntity.this.is1x1()) {
								zombiePosZ = DarkAgesGraveEntity.this.random.range(-1, 1);
								zombiePos = DarkAgesGraveEntity.this.random.range(-1, 1);
							}
							if (DarkAgesGraveEntity.this.isChallengeGrave()) {
								zombiePosZ = DarkAgesGraveEntity.this.random.range(-3, 3);
								zombiePos = DarkAgesGraveEntity.this.random.range(-3, 3);
							}
							BlockPos blockPos = DarkAgesGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							PeasantEntity PeasantEntity = (PeasantEntity)PvZEntity.PEASANT.create(DarkAgesGraveEntity.this.getWorld());
							PeasantEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
							PeasantEntity.initialize(serverWorld, DarkAgesGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData)null, (NbtCompound)null);
							PeasantEntity.setOwner(DarkAgesGraveEntity.this);
							PeasantEntity.defenseMultiplier = DarkAgesGraveEntity.this.defenseMultiplier;
				serverWorld.spawnEntityAndPassengers(PeasantEntity);
						}
					}
				}
			}
			if (difficulty >= 1.599 + difficultymodifier || isUnlock()) {
				if (probability22 <= 0.15 / halfModifier * survChance) { // 15% x1 Knight Zombie
					for (int u = 0; u < 1; ++u) {
						if (!DarkAgesGraveEntity.this.is1x1()) {
							zombiePosZ = DarkAgesGraveEntity.this.random.range(-1, 1);
							zombiePos = DarkAgesGraveEntity.this.random.range(-1, 1);
						}
						if (DarkAgesGraveEntity.this.isChallengeGrave()) {
							zombiePosZ = DarkAgesGraveEntity.this.random.range(-3, 3);
							zombiePos = DarkAgesGraveEntity.this.random.range(-3, 3);
						}
						BlockPos blockPos = DarkAgesGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
						PeasantEntity PeasantEntity = (PeasantEntity)PvZEntity.PEASANTKNIGHT.create(DarkAgesGraveEntity.this.getWorld());
						PeasantEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
						PeasantEntity.initialize(serverWorld, DarkAgesGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData)null, (NbtCompound)null);
						PeasantEntity.setOwner(DarkAgesGraveEntity.this);
						PeasantEntity.defenseMultiplier = DarkAgesGraveEntity.this.defenseMultiplier;
				serverWorld.spawnEntityAndPassengers(PeasantEntity);
					}
					for (int b = 0; b < 1; ++b) { // 100% x1 Browncoat
						if (!DarkAgesGraveEntity.this.is1x1()) {
							zombiePosZ = DarkAgesGraveEntity.this.random.range(-1, 1);
							zombiePos = DarkAgesGraveEntity.this.random.range(-1, 1);
						}
						if (DarkAgesGraveEntity.this.isChallengeGrave()) {
							zombiePosZ = DarkAgesGraveEntity.this.random.range(-3, 3);
							zombiePos = DarkAgesGraveEntity.this.random.range(-3, 3);
						}
						BlockPos blockPos = DarkAgesGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
						PeasantEntity PeasantEntity = (PeasantEntity) PvZEntity.PEASANT.create(DarkAgesGraveEntity.this.getWorld());
						PeasantEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
						PeasantEntity.initialize(serverWorld, DarkAgesGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
						PeasantEntity.setOwner(DarkAgesGraveEntity.this);
						PeasantEntity.defenseMultiplier = DarkAgesGraveEntity.this.defenseMultiplier;
				serverWorld.spawnEntityAndPassengers(PeasantEntity);
					}
				}
			}
			if (serverWorld.toServerWorld().getTime() > 24000) {
				if (difficulty >= 1.599 + difficultymodifier || isUnlock()) {
					if (probability7 <= 0.1 / halfModifier * survChance) { // 10% x2 Conehead
						for (int h = 0; h < 2 / halfModifier; ++h) {
							if (!DarkAgesGraveEntity.this.is1x1()) {
								zombiePosZ = DarkAgesGraveEntity.this.random.range(-1, 1);
								zombiePos = DarkAgesGraveEntity.this.random.range(-1, 1);
							}
							if (DarkAgesGraveEntity.this.isChallengeGrave()) {
								zombiePosZ = DarkAgesGraveEntity.this.random.range(-3, 3);
								zombiePos = DarkAgesGraveEntity.this.random.range(-3, 3);
							}
							BlockPos blockPos = DarkAgesGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							PeasantEntity coneheadEntity = (PeasantEntity) PvZEntity.PEASANTCONE.create(DarkAgesGraveEntity.this.getWorld());
							coneheadEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
							coneheadEntity.initialize(serverWorld, DarkAgesGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							coneheadEntity.setOwner(DarkAgesGraveEntity.this);
							coneheadEntity.defenseMultiplier = DarkAgesGraveEntity.this.defenseMultiplier;
					serverWorld.spawnEntityAndPassengers(coneheadEntity);
						}
					}
				}
				if (difficulty >= 1.599 + difficultymodifier || isUnlock()) {
					if (probability10 <= 0.3 / halfModifier * survChance) { // 5% x1 Knight Zombie
						for (int j = 0; j < 1; ++j) {
							if (!DarkAgesGraveEntity.this.is1x1()) {
								zombiePosZ = DarkAgesGraveEntity.this.random.range(-1, 1);
								zombiePos = DarkAgesGraveEntity.this.random.range(-1, 1);
							}
							if (DarkAgesGraveEntity.this.isChallengeGrave()) {
								zombiePosZ = DarkAgesGraveEntity.this.random.range(-3, 3);
								zombiePos = DarkAgesGraveEntity.this.random.range(-3, 3);
							}
							BlockPos blockPos = DarkAgesGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							PeasantEntity brickhead = (PeasantEntity) PvZEntity.PEASANTKNIGHT.create(DarkAgesGraveEntity.this.getWorld());
							brickhead.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
							brickhead.initialize(serverWorld, DarkAgesGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							brickhead.setOwner(DarkAgesGraveEntity.this);
							brickhead.defenseMultiplier = DarkAgesGraveEntity.this.defenseMultiplier;
							serverWorld.spawnEntityAndPassengers(brickhead);
						}
					}
				}
			}
			if (serverWorld.toServerWorld().getTime() > 24000) {
				if (extraGraveWeight <= 2.5) {
					if (difficulty >= 1.599 + difficultymodifier || isUnlock() || isUnlockSpecial()) {
						if (probability4 <= 0.1 / halfModifier * survChance) { // 10% x1 Flag Zombie
							for (int f = 0; f < 1; ++f) {
								if (!DarkAgesGraveEntity.this.is1x1()) {
									zombiePosZ = DarkAgesGraveEntity.this.random.range(-1, 1);
									zombiePos = DarkAgesGraveEntity.this.random.range(-1, 1);
								}
								if (DarkAgesGraveEntity.this.isChallengeGrave()) {
									zombiePosZ = DarkAgesGraveEntity.this.random.range(-3, 3);
									zombiePos = DarkAgesGraveEntity.this.random.range(-3, 3);
								}
								double random = Math.random();
								BlockPos blockPos = DarkAgesGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
								FlagPeasantEntity flagzombieEntity = (FlagPeasantEntity) PvZEntity.FLAGPEASANT.create(DarkAgesGraveEntity.this.getWorld());
								flagzombieEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
								flagzombieEntity.initialize(serverWorld, DarkAgesGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
								flagzombieEntity.setOwner(DarkAgesGraveEntity.this);
								flagzombieEntity.defenseMultiplier = DarkAgesGraveEntity.this.defenseMultiplier;
								serverWorld.spawnEntityAndPassengers(flagzombieEntity);
							}
							extraGraveWeight += 1;
						}
					}
				}
				if (extraGraveWeight <= 2.5) {
					if (difficulty >= 1.549 + difficultymodifier || isUnlock()) {
						if (probability3 <= 0.15 / halfModifier * survChance) { // 15% x2 Imp Dragon
							for (int p = 0; p < 2 / halfModifier; ++p) {
								if (!DarkAgesGraveEntity.this.is1x1()) {
									zombiePosZ = DarkAgesGraveEntity.this.random.range(-1, 1);
									zombiePos = DarkAgesGraveEntity.this.random.range(-1, 1);
								}
								if (DarkAgesGraveEntity.this.isChallengeGrave()) {
									zombiePosZ = DarkAgesGraveEntity.this.random.range(-3, 3);
									zombiePos = DarkAgesGraveEntity.this.random.range(-3, 3);
								}
								BlockPos blockPos = DarkAgesGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
								ImpEntity impdragon = (ImpEntity) PvZEntity.IMPDRAGON.create(DarkAgesGraveEntity.this.getWorld());
								impdragon.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
								impdragon.initialize(serverWorld, DarkAgesGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
								impdragon.setOwner(DarkAgesGraveEntity.this);
								impdragon.defenseMultiplier = DarkAgesGraveEntity.this.defenseMultiplier;
								serverWorld.spawnEntityAndPassengers(impdragon);
							}
							for (int b = 0; b < 2 / halfModifier; ++b) { // 100% x2 Browncoat
								if (!DarkAgesGraveEntity.this.is1x1()) {
									zombiePosZ = DarkAgesGraveEntity.this.random.range(-1, 1);
									zombiePos = DarkAgesGraveEntity.this.random.range(-1, 1);
								}
								if (DarkAgesGraveEntity.this.isChallengeGrave()) {
									zombiePosZ = DarkAgesGraveEntity.this.random.range(-3, 3);
									zombiePos = DarkAgesGraveEntity.this.random.range(-3, 3);
								}
								BlockPos blockPos = DarkAgesGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
								PeasantEntity PeasantEntity = (PeasantEntity) PvZEntity.PEASANT.create(DarkAgesGraveEntity.this.getWorld());
								PeasantEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
								PeasantEntity.initialize(serverWorld, DarkAgesGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
								PeasantEntity.setOwner(DarkAgesGraveEntity.this);
								PeasantEntity.defenseMultiplier = DarkAgesGraveEntity.this.defenseMultiplier;
				serverWorld.spawnEntityAndPassengers(PeasantEntity);
							}
							extraGraveWeight += 1;
						}
					}
				}
				if (extraGraveWeight <= 2.5) {
					if ((difficulty >= 2.009 + difficultymodifier) && (isUnlock() || isUnlockSpecial())) {
						if (probability8 <= 0.15 / halfModifier * survChance) { // 15% x2 Announcer Imp
							for (int g = 0; g < 2 / halfModifier; ++g) {
								if (!DarkAgesGraveEntity.this.is1x1()) {
									zombiePosZ = DarkAgesGraveEntity.this.random.range(-1, 1);
									zombiePos = DarkAgesGraveEntity.this.random.range(-1, 1);
								}
								if (DarkAgesGraveEntity.this.isChallengeGrave()) {
									zombiePosZ = DarkAgesGraveEntity.this.random.range(-3, 3);
									zombiePos = DarkAgesGraveEntity.this.random.range(-3, 3);
								}
								BlockPos blockPos = DarkAgesGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
								AnnouncerImpEntity announcerImpEntity = (AnnouncerImpEntity) PvZEntity.ANNOUNCERIMP.create(DarkAgesGraveEntity.this.getWorld());
								announcerImpEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
								announcerImpEntity.initialize(serverWorld, DarkAgesGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
								announcerImpEntity.setOwner(DarkAgesGraveEntity.this);
								announcerImpEntity.defenseMultiplier = DarkAgesGraveEntity.this.defenseMultiplier;
								serverWorld.spawnEntityAndPassengers(announcerImpEntity);
							}
							for (int b = 0; b < 4 / halfModifier; ++b) { // 100% x4 Browncoat
								if (!DarkAgesGraveEntity.this.is1x1()) {
									zombiePosZ = DarkAgesGraveEntity.this.random.range(-1, 1);
									zombiePos = DarkAgesGraveEntity.this.random.range(-1, 1);
								}
								if (DarkAgesGraveEntity.this.isChallengeGrave()) {
									zombiePosZ = DarkAgesGraveEntity.this.random.range(-3, 3);
									zombiePos = DarkAgesGraveEntity.this.random.range(-3, 3);
								}
								BlockPos blockPos = DarkAgesGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
								PeasantEntity PeasantEntity = (PeasantEntity) PvZEntity.PEASANT.create(DarkAgesGraveEntity.this.getWorld());
								PeasantEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
								PeasantEntity.initialize(serverWorld, DarkAgesGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
								PeasantEntity.setOwner(DarkAgesGraveEntity.this);
								PeasantEntity.defenseMultiplier = DarkAgesGraveEntity.this.defenseMultiplier;
				serverWorld.spawnEntityAndPassengers(PeasantEntity);
							}
							extraGraveWeight += 1.25;
						}
					}
				}
				if (extraGraveWeight <= 2.5) {
					if ((difficulty >= 2.009 + difficultymodifier) && (isUnlock() || isUnlockSpecial())) {
						if (probability6 <= 0.05 / halfModifier * survChance) { // 5% x3 Knight Zombie
							for (int j = 0; j < 3/ halfModifier ; ++j) {
								if (!DarkAgesGraveEntity.this.is1x1()) {
									zombiePosZ = DarkAgesGraveEntity.this.random.range(-1, 1);
									zombiePos = DarkAgesGraveEntity.this.random.range(-1, 1);
								}
								if (DarkAgesGraveEntity.this.isChallengeGrave()) {
									zombiePosZ = DarkAgesGraveEntity.this.random.range(-3, 3);
									zombiePos = DarkAgesGraveEntity.this.random.range(-3, 3);
								}
								BlockPos blockPos = DarkAgesGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
								PeasantEntity brickhead = (PeasantEntity) PvZEntity.PEASANTKNIGHT.create(DarkAgesGraveEntity.this.getWorld());
								brickhead.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
								brickhead.initialize(serverWorld, DarkAgesGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
								brickhead.setOwner(DarkAgesGraveEntity.this);
								brickhead.defenseMultiplier = DarkAgesGraveEntity.this.defenseMultiplier;
							serverWorld.spawnEntityAndPassengers(brickhead);
							}
							for (int b = 0; b < 2 / halfModifier; ++b) { // 100% x2 Browncoat
								if (!DarkAgesGraveEntity.this.is1x1()) {
									zombiePosZ = DarkAgesGraveEntity.this.random.range(-1, 1);
									zombiePos = DarkAgesGraveEntity.this.random.range(-1, 1);
								}
								if (DarkAgesGraveEntity.this.isChallengeGrave()) {
									zombiePosZ = DarkAgesGraveEntity.this.random.range(-3, 3);
									zombiePos = DarkAgesGraveEntity.this.random.range(-3, 3);
								}
								BlockPos blockPos = DarkAgesGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
								PeasantEntity PeasantEntity = (PeasantEntity) PvZEntity.PEASANT.create(DarkAgesGraveEntity.this.getWorld());
								PeasantEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
								PeasantEntity.initialize(serverWorld, DarkAgesGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
								PeasantEntity.setOwner(DarkAgesGraveEntity.this);
								PeasantEntity.defenseMultiplier = DarkAgesGraveEntity.this.defenseMultiplier;
				serverWorld.spawnEntityAndPassengers(PeasantEntity);
							}
							for (int b = 0; b < 2 / halfModifier; ++b) { // 100% x2 Conehead
								if (!DarkAgesGraveEntity.this.is1x1()) {
									zombiePosZ = DarkAgesGraveEntity.this.random.range(-1, 1);
									zombiePos = DarkAgesGraveEntity.this.random.range(-1, 1);
								}
								if (DarkAgesGraveEntity.this.isChallengeGrave()) {
									zombiePosZ = DarkAgesGraveEntity.this.random.range(-3, 3);
									zombiePos = DarkAgesGraveEntity.this.random.range(-3, 3);
								}
								BlockPos blockPos = DarkAgesGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
								PeasantEntity PeasantEntity = (PeasantEntity) PvZEntity.PEASANTCONE.create(DarkAgesGraveEntity.this.getWorld());
								PeasantEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
								PeasantEntity.initialize(serverWorld, DarkAgesGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
								PeasantEntity.setOwner(DarkAgesGraveEntity.this);
								PeasantEntity.defenseMultiplier = DarkAgesGraveEntity.this.defenseMultiplier;
				serverWorld.spawnEntityAndPassengers(PeasantEntity);
							}
							for (int b = 0; b < 1; ++b) { // 100% x1 Buckethead
								if (!DarkAgesGraveEntity.this.is1x1()) {
									zombiePosZ = DarkAgesGraveEntity.this.random.range(-1, 1);
									zombiePos = DarkAgesGraveEntity.this.random.range(-1, 1);
								}
								if (DarkAgesGraveEntity.this.isChallengeGrave()) {
									zombiePosZ = DarkAgesGraveEntity.this.random.range(-3, 3);
									zombiePos = DarkAgesGraveEntity.this.random.range(-3, 3);
								}
								BlockPos blockPos = DarkAgesGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
								PeasantEntity PeasantEntity = (PeasantEntity) PvZEntity.PEASANTBUCKET.create(DarkAgesGraveEntity.this.getWorld());
								PeasantEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
								PeasantEntity.initialize(serverWorld, DarkAgesGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
								PeasantEntity.setOwner(DarkAgesGraveEntity.this);
								PeasantEntity.defenseMultiplier = DarkAgesGraveEntity.this.defenseMultiplier;
				serverWorld.spawnEntityAndPassengers(PeasantEntity);
							}
							extraGraveWeight += 1.75;
						}
					}
				}
				if (extraGraveWeight <= 2.5) {
					if (difficulty >= 1.599 + difficultymodifier || isUnlock()) {
						if (probability9 <= 0.2 / halfModifier * survChance) { // 20% x1 Announcer Imp
							for (int g = 0; g < 1; ++g) {
								if (!DarkAgesGraveEntity.this.is1x1()) {
									zombiePosZ = DarkAgesGraveEntity.this.random.range(-1, 1);
									zombiePos = DarkAgesGraveEntity.this.random.range(-1, 1);
								}
								if (DarkAgesGraveEntity.this.isChallengeGrave()) {
									zombiePosZ = DarkAgesGraveEntity.this.random.range(-3, 3);
									zombiePos = DarkAgesGraveEntity.this.random.range(-3, 3);
								}
								BlockPos blockPos = DarkAgesGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
								AnnouncerImpEntity announcerImpEntity = (AnnouncerImpEntity) PvZEntity.ANNOUNCERIMP.create(DarkAgesGraveEntity.this.getWorld());
								announcerImpEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
								announcerImpEntity.initialize(serverWorld, DarkAgesGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
								announcerImpEntity.setOwner(DarkAgesGraveEntity.this);
								announcerImpEntity.defenseMultiplier = DarkAgesGraveEntity.this.defenseMultiplier;
								serverWorld.spawnEntityAndPassengers(announcerImpEntity);
							}
							for (int b = 0; b < 2 / halfModifier; ++b) { // 100% x2 Browncoat
								if (!DarkAgesGraveEntity.this.is1x1()) {
									zombiePosZ = DarkAgesGraveEntity.this.random.range(-1, 1);
									zombiePos = DarkAgesGraveEntity.this.random.range(-1, 1);
								}
								if (DarkAgesGraveEntity.this.isChallengeGrave()) {
									zombiePosZ = DarkAgesGraveEntity.this.random.range(-3, 3);
									zombiePos = DarkAgesGraveEntity.this.random.range(-3, 3);
								}
								BlockPos blockPos = DarkAgesGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
								PeasantEntity PeasantEntity = (PeasantEntity) PvZEntity.PEASANT.create(DarkAgesGraveEntity.this.getWorld());
								PeasantEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
								PeasantEntity.initialize(serverWorld, DarkAgesGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
								PeasantEntity.setOwner(DarkAgesGraveEntity.this);
								PeasantEntity.defenseMultiplier = DarkAgesGraveEntity.this.defenseMultiplier;
				serverWorld.spawnEntityAndPassengers(PeasantEntity);
							}
							extraGraveWeight += 0.75;
						}
					}
				}
				if (extraGraveWeight <= 2.5) {
					if (probability13 <= 0.3 / halfModifier * survChance) { // 30% x1 Imp Dragon
						for (int p = 0; p < 1; ++p) {
							if (!DarkAgesGraveEntity.this.is1x1()) {
								zombiePosZ = DarkAgesGraveEntity.this.random.range(-1, 1);
								zombiePos = DarkAgesGraveEntity.this.random.range(-1, 1);
							}
							if (DarkAgesGraveEntity.this.isChallengeGrave()) {
								zombiePosZ = DarkAgesGraveEntity.this.random.range(-3, 3);
								zombiePos = DarkAgesGraveEntity.this.random.range(-3, 3);
							}
							BlockPos blockPos = DarkAgesGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							ImpEntity impdragon = (ImpEntity) PvZEntity.IMPDRAGON.create(DarkAgesGraveEntity.this.getWorld());
							impdragon.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
							impdragon.initialize(serverWorld, DarkAgesGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							impdragon.setOwner(DarkAgesGraveEntity.this);
							impdragon.defenseMultiplier = DarkAgesGraveEntity.this.defenseMultiplier;
							serverWorld.spawnEntityAndPassengers(impdragon);
						}
						extraGraveWeight += 0.5;
					}
				}
				if (extraGraveWeight <= 2.5) {
					if (probability21 <= 0.25 / halfModifier * survChance) {
						if (difficulty >= 1.599 + difficultymodifier || isUnlock()) { // 25% x1 Knight Zombie
							for (int j = 0; j < 1; ++j) {
								if (!DarkAgesGraveEntity.this.is1x1()) {
									zombiePosZ = DarkAgesGraveEntity.this.random.range(-1, 1);
									zombiePos = DarkAgesGraveEntity.this.random.range(-1, 1);
								}
								if (DarkAgesGraveEntity.this.isChallengeGrave()) {
									zombiePosZ = DarkAgesGraveEntity.this.random.range(-3, 3);
									zombiePos = DarkAgesGraveEntity.this.random.range(-3, 3);
								}
								BlockPos blockPos = DarkAgesGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
								PeasantEntity brickhead = (PeasantEntity) PvZEntity.PEASANTKNIGHT.create(DarkAgesGraveEntity.this.getWorld());
								brickhead.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
								brickhead.initialize(serverWorld, DarkAgesGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
								brickhead.setOwner(DarkAgesGraveEntity.this);
								brickhead.defenseMultiplier = DarkAgesGraveEntity.this.defenseMultiplier;
							serverWorld.spawnEntityAndPassengers(brickhead);
							}
							for (int h = 0; h < 1; ++h) { // 100% x1 Buckethead
								if (!DarkAgesGraveEntity.this.is1x1()) {
									zombiePosZ = DarkAgesGraveEntity.this.random.range(-1, 1);
									zombiePos = DarkAgesGraveEntity.this.random.range(-1, 1);
								}
								if (DarkAgesGraveEntity.this.isChallengeGrave()) {
									zombiePosZ = DarkAgesGraveEntity.this.random.range(-3, 3);
									zombiePos = DarkAgesGraveEntity.this.random.range(-3, 3);
								}
								BlockPos blockPos = DarkAgesGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
								PeasantEntity PeasantEntity = (PeasantEntity) PvZEntity.PEASANTBUCKET.create(DarkAgesGraveEntity.this.getWorld());
								PeasantEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
								PeasantEntity.initialize(serverWorld, DarkAgesGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
								PeasantEntity.setOwner(DarkAgesGraveEntity.this);
								PeasantEntity.defenseMultiplier = DarkAgesGraveEntity.this.defenseMultiplier;
				serverWorld.spawnEntityAndPassengers(PeasantEntity);
							}
							extraGraveWeight += 0.75;
						}
					}
				}
				if (specialGraveWeight <= 3) {
					if ((difficulty >= 2.009 + difficultymodifier) && (isUnlock() || isUnlockSpecial())) {
						if (probability12 <= 0.15 / halfModifier * survChance) { // 15% x3 Pumpkin Zombie
							for (int p = 0; p < 3 / halfModifier; ++p) {
								if (!DarkAgesGraveEntity.this.is1x1()) {
									zombiePosZ = DarkAgesGraveEntity.this.random.range(-1, 1);
									zombiePos = DarkAgesGraveEntity.this.random.range(-1, 1);
								}
								if (DarkAgesGraveEntity.this.isChallengeGrave()) {
									zombiePosZ = DarkAgesGraveEntity.this.random.range(-3, 3);
									zombiePos = DarkAgesGraveEntity.this.random.range(-3, 3);
								}
								BlockPos blockPos = DarkAgesGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
								PumpkinZombieEntity pumpkinZombie = (PumpkinZombieEntity) PvZEntity.PUMPKINZOMBIE.create(DarkAgesGraveEntity.this.getWorld());
								pumpkinZombie.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
								pumpkinZombie.initialize(serverWorld, DarkAgesGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
								pumpkinZombie.setOwner(DarkAgesGraveEntity.this);
								pumpkinZombie.defenseMultiplier = DarkAgesGraveEntity.this.defenseMultiplier;
								serverWorld.spawnEntityAndPassengers(pumpkinZombie);
							}
							for (int b = 0; b < 2 / halfModifier; ++b) { // 100% x2 Conehead
								if (!DarkAgesGraveEntity.this.is1x1()) {
									zombiePosZ = DarkAgesGraveEntity.this.random.range(-1, 1);
									zombiePos = DarkAgesGraveEntity.this.random.range(-1, 1);
								}
								if (DarkAgesGraveEntity.this.isChallengeGrave()) {
									zombiePosZ = DarkAgesGraveEntity.this.random.range(-3, 3);
									zombiePos = DarkAgesGraveEntity.this.random.range(-3, 3);
								}
								BlockPos blockPos = DarkAgesGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
								PeasantEntity PeasantEntity = (PeasantEntity) PvZEntity.PEASANTCONE.create(DarkAgesGraveEntity.this.getWorld());
								PeasantEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
								PeasantEntity.initialize(serverWorld, DarkAgesGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
								PeasantEntity.setOwner(DarkAgesGraveEntity.this);
								PeasantEntity.defenseMultiplier = DarkAgesGraveEntity.this.defenseMultiplier;
				serverWorld.spawnEntityAndPassengers(PeasantEntity);
							}
							for (int b = 0; b < 1; ++b) { // 100% x1 Buckethead
								if (!DarkAgesGraveEntity.this.is1x1()) {
									zombiePosZ = DarkAgesGraveEntity.this.random.range(-1, 1);
									zombiePos = DarkAgesGraveEntity.this.random.range(-1, 1);
								}
								if (DarkAgesGraveEntity.this.isChallengeGrave()) {
									zombiePosZ = DarkAgesGraveEntity.this.random.range(-3, 3);
									zombiePos = DarkAgesGraveEntity.this.random.range(-3, 3);
								}
								BlockPos blockPos = DarkAgesGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
								PeasantEntity PeasantEntity = (PeasantEntity) PvZEntity.PEASANTBUCKET.create(DarkAgesGraveEntity.this.getWorld());
								PeasantEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
								PeasantEntity.initialize(serverWorld, DarkAgesGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
								PeasantEntity.setOwner(DarkAgesGraveEntity.this);
								PeasantEntity.defenseMultiplier = DarkAgesGraveEntity.this.defenseMultiplier;
				serverWorld.spawnEntityAndPassengers(PeasantEntity);
							}
							specialGraveWeight += 1.5;
						}
					}
				}
				if (specialGraveWeight <= 3) {
					if ((difficulty >= 1.599 + difficultymodifier) && (isUnlock() || isUnlockSpecial())) {
						if (probability5 <= 0.20 / halfModifier * survChance) { // 15% x1 Pumpkin Zombie
							for (int p = 0; p < 2 / halfModifier; ++p) {
								if (!DarkAgesGraveEntity.this.is1x1()) {
									zombiePosZ = DarkAgesGraveEntity.this.random.range(-1, 1);
									zombiePos = DarkAgesGraveEntity.this.random.range(-1, 1);
								}
								if (DarkAgesGraveEntity.this.isChallengeGrave()) {
									zombiePosZ = DarkAgesGraveEntity.this.random.range(-3, 3);
									zombiePos = DarkAgesGraveEntity.this.random.range(-3, 3);
								}
								BlockPos blockPos = DarkAgesGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
								PumpkinZombieEntity pumpkinZombie = (PumpkinZombieEntity) PvZEntity.PUMPKINZOMBIE.create(DarkAgesGraveEntity.this.getWorld());
								pumpkinZombie.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
								pumpkinZombie.initialize(serverWorld, DarkAgesGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
								pumpkinZombie.setOwner(DarkAgesGraveEntity.this);
								pumpkinZombie.defenseMultiplier = DarkAgesGraveEntity.this.defenseMultiplier;
								serverWorld.spawnEntityAndPassengers(pumpkinZombie);
							}
							specialGraveWeight += 0.5;
						}
					}
				}
			}
			++this.darkAgesGraveEntity.spawnCounter;
        }

        protected SoundEvent getSoundPrepare() {
            return PvZSounds.GRAVERISINGEVENT;
        }

        protected Spell getSpell() {
            return Spell.SUMMON_VEX;
        }
    }
}
