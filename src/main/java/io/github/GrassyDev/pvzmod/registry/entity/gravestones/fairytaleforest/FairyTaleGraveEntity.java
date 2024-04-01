package io.github.GrassyDev.pvzmod.registry.entity.gravestones.fairytaleforest;


import io.github.GrassyDev.pvzmod.registry.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.PvZSounds;
import io.github.GrassyDev.pvzmod.registry.entity.gravestones.GraveEntity;
import io.github.GrassyDev.pvzmod.registry.entity.variants.graves.GraveDifficulty;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.oc.pumpkincar.PumpkinCarEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2.imp.announcer.AnnouncerImpEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2c.browncoat.fairytale.PokerEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2c.flagzombie.fairytale.FlagPokerEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvzh.zomblob.ZomblobEntity;
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
import net.minecraft.world.Difficulty;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.util.GeckoLibUtil;
import io.github.GrassyDev.pvzmod.registry.entity.damage.PvZDamageTypes;

import static io.github.GrassyDev.pvzmod.PvZCubed.PVZCONFIG;

public class FairyTaleGraveEntity extends GraveEntity implements GeoAnimatable {

	private String controllerName = "walkingcontroller";

	private int spawnCounter;



	double tiltchance = this.random.nextDouble();

    private AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

    public FairyTaleGraveEntity(EntityType<FairyTaleGraveEntity> entityType, World world) {
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
        this.goalSelector.add(1, new FairyTaleGraveEntity.summonZombieGoal(this));
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
		else if (this.spawnCounter == 2 && difficulty <= 1.599 + difficultymodifier){
			if (!this.isInfinite()) {
				this.kill();
			}
		}
		else if (this.spawnCounter == 3 && difficulty <= 1.809 + difficultymodifier){
			if (!this.isInfinite()) {
				this.kill();
			}
		}
		else if (this.spawnCounter == 4 && difficulty <= 2.009 + difficultymodifier){
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
			float g = this.bodyYaw * 0.017453292F + MathHelper.cos((float)this.age * 0.6662F) * 0.25F;
			float h = MathHelper.cos(g);
			float i = MathHelper.sin(g);
			this.getWorld().addParticle(ParticleTypes.SMOKE, this.getX() + (double)h * 0.6, this.getY(), this.getZ() + (double)i * 0.6, 0, 0.0125, 0);
			this.getWorld().addParticle(ParticleTypes.SMOKE, this.getX() - (double)h * 0.6, this.getY(), this.getZ() - (double)i * 0.6, 0, 0.0125, 0);
		}
	}


	/** /~*~//~*ATTRIBUTES*~//~*~/ **/

	public static DefaultAttributeContainer.Builder createFairyGraveAttributes() {
        return HostileEntity.createAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 100.0D)

                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, PVZCONFIG.nestedZombieHealth.fairytaleGraveH());
    }

	protected SoundEvent getDeathSound() {
		return SoundEvents.BLOCK_ANCIENT_DEBRIS_BREAK;
	}

	protected SoundEvent getHurtSound(DamageSource source) {
		return SoundEvents.BLOCK_BASALT_HIT;
	}

	@Override
	public double getTick(Object object) {
		return 0;
	}


	/** /~*~//~*GOALS*~//~*~/ **/

	protected abstract class CastSpellGoal extends Goal {
		protected int spellCooldown;
		protected int startTime;

		protected CastSpellGoal() {
		}

		public boolean canStart() {
			LivingEntity livingEntity = FairyTaleGraveEntity.this.getTarget();
			if (livingEntity != null && livingEntity.isAlive()) {
				if (FairyTaleGraveEntity.this.isSpellcasting()) {
					return false;
				} else {
					return FairyTaleGraveEntity.this.age >= this.startTime;
				}
			} else {
				return false;
			}
		}

		public boolean shouldContinue() {
			LivingEntity livingEntity = FairyTaleGraveEntity.this.getTarget();
			return livingEntity != null && livingEntity.isAlive() && this.spellCooldown > 0;
		}

		public void start() {
			this.spellCooldown = this.getTickCount(this.getInitialCooldown());
			FairyTaleGraveEntity.this.spellTicks = this.getSpellTicks();
			this.startTime = FairyTaleGraveEntity.this.age + this.startTimeDelay();
			SoundEvent soundEvent = this.getSoundPrepare();
			if (soundEvent != null) {
				FairyTaleGraveEntity.this.playSound(soundEvent, 0.9F, 1.0F);
			}

			FairyTaleGraveEntity.this.setSpell(this.getSpell());
		}

		public void tick() {
			--this.spellCooldown;
			if (this.spellCooldown == 0) {
				this.castSpell();
				FairyTaleGraveEntity.this.addStatusEffect((new StatusEffectInstance(StatusEffects.GLOWING, 100, 1)));
				FairyTaleGraveEntity.this.playSound(FairyTaleGraveEntity.this.getCastSpellSound(), 0.9F, 1.0F);
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
		return ModItems.FAIRYTALEGRAVESPAWN.getDefaultStack();
	}

	class summonZombieGoal extends FairyTaleGraveEntity.CastSpellGoal {
        private final TargetPredicate closeZombiePredicate;

		private final FairyTaleGraveEntity fairyTaleGraveEntity;

		private summonZombieGoal(FairyTaleGraveEntity fairyTaleGraveEntity) {
            super();
			this.fairyTaleGraveEntity = fairyTaleGraveEntity;
			this.closeZombiePredicate = (TargetPredicate.createNonAttackable().setBaseMaxDistance(16.0D).ignoreVisibility().ignoreDistanceScalingFactor());
		}

		public boolean canStart() {
			return super.canStart();
		}

        protected int getSpellTicks() {
            return 100;
        }

        protected int startTimeDelay() {
            return 300;
        }

        protected void castSpell() {
			extraGraveWeight = 0;
			specialGraveWeight = 0;
			ServerWorld serverWorld = (ServerWorld) FairyTaleGraveEntity.this.getWorld();
			LocalDifficulty localDifficulty = getWorld().getLocalDifficulty(this.fairyTaleGraveEntity.getBlockPos());
			double difficulty = 0;
			if (this.fairyTaleGraveEntity.getVariant().equals(GraveDifficulty.NONE)) {
				difficulty = localDifficulty.getLocalDifficulty();
				if (difficulty >= 2.1) {
					difficulty = 2.1;
					if (getWorld().getDifficulty().equals(Difficulty.HARD)) {
						difficulty = difficulty + difficultymodifier;
					}
				}
			} else if (this.fairyTaleGraveEntity.getVariant().equals(GraveDifficulty.EASY)) {
				difficulty = 1.0;
			} else if (this.fairyTaleGraveEntity.getVariant().equals(GraveDifficulty.EASYMED)) {
				difficulty = 1.5;
			} else if (this.fairyTaleGraveEntity.getVariant().equals(GraveDifficulty.MED)) {
				difficulty = 1.6;
			} else if (this.fairyTaleGraveEntity.getVariant().equals(GraveDifficulty.MEDHARD)) {
				difficulty = 1.8;
			} else if (this.fairyTaleGraveEntity.getVariant().equals(GraveDifficulty.HARD)) {
				difficulty = 2.1;
			} else if (this.fairyTaleGraveEntity.getVariant().equals(GraveDifficulty.SUPERHARD)) {
				difficulty = 3;
			} else if (this.fairyTaleGraveEntity.getVariant().equals(GraveDifficulty.NIGHTMARE)) {
				difficulty = 4;
			} else if (this.fairyTaleGraveEntity.getVariant().equals(GraveDifficulty.CRAAAZY)) {
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

			int zombiePos = -2 + FairyTaleGraveEntity.this.random.nextInt(5);
			int zombiePosZ = -2 + FairyTaleGraveEntity.this.random.nextInt(5);
			if (FairyTaleGraveEntity.this.is1x1()) {
				zombiePos = 0;
				zombiePosZ = 0;
			}

			for (int b = 0; b < 1; ++b) { // 100% x1 Browncoat
				if (!FairyTaleGraveEntity.this.is1x1()) {
					zombiePosZ = FairyTaleGraveEntity.this.random.range(-1, 1);
					zombiePos = FairyTaleGraveEntity.this.random.range(-1, 1);
				}
				if (FairyTaleGraveEntity.this.isChallengeGrave()) {
					zombiePosZ = FairyTaleGraveEntity.this.random.range(-3, 3);
					zombiePos = FairyTaleGraveEntity.this.random.range(-3, 3);
				}
				BlockPos blockPos = FairyTaleGraveEntity.this.getBlockPos().add(zombiePos, 0, zombiePosZ);
				PokerEntity browncoatEntity = (PokerEntity) PvZEntity.POKER.create(FairyTaleGraveEntity.this.getWorld());
				browncoatEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
				browncoatEntity.initialize(serverWorld, FairyTaleGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
				browncoatEntity.setOwner(FairyTaleGraveEntity.this);
				browncoatEntity.defenseMultiplier = FairyTaleGraveEntity.this.defenseMultiplier;
					serverWorld.spawnEntityAndPassengers(browncoatEntity);
			}
			if (probability <= 0.25 / halfModifier * survChance) { // 25% x1 Conehead
				for (int c = 0; c < 1; ++c) {
					if (!FairyTaleGraveEntity.this.is1x1()) {
						zombiePosZ = FairyTaleGraveEntity.this.random.range(-1, 1);
						zombiePos = FairyTaleGraveEntity.this.random.range(-1, 1);
					}
					if (FairyTaleGraveEntity.this.isChallengeGrave()) {
						zombiePosZ = FairyTaleGraveEntity.this.random.range(-3, 3);
						zombiePos = FairyTaleGraveEntity.this.random.range(-3, 3);
					}
					BlockPos blockPos = FairyTaleGraveEntity.this.getBlockPos().add(zombiePos, 0, zombiePosZ);
					PokerEntity coneheadEntity = (PokerEntity) PvZEntity.POKERCONE.create(FairyTaleGraveEntity.this.getWorld());
					coneheadEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
					coneheadEntity.initialize(serverWorld, FairyTaleGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
					coneheadEntity.setOwner(FairyTaleGraveEntity.this);
					coneheadEntity.defenseMultiplier = FairyTaleGraveEntity.this.defenseMultiplier;
					serverWorld.spawnEntityAndPassengers(coneheadEntity);
				}
				for (int b = 0; b < 1; ++b) { // 100% x1 Browncoat
					if (!FairyTaleGraveEntity.this.is1x1()) {
						zombiePosZ = FairyTaleGraveEntity.this.random.range(-1, 1);
						zombiePos = FairyTaleGraveEntity.this.random.range(-1, 1);
					}
					if (FairyTaleGraveEntity.this.isChallengeGrave()) {
						zombiePosZ = FairyTaleGraveEntity.this.random.range(-3, 3);
						zombiePos = FairyTaleGraveEntity.this.random.range(-3, 3);
					}
					BlockPos blockPos = FairyTaleGraveEntity.this.getBlockPos().add(zombiePos, 0, zombiePosZ);
					PokerEntity browncoatEntity = (PokerEntity) PvZEntity.POKER.create(FairyTaleGraveEntity.this.getWorld());
					browncoatEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
					browncoatEntity.initialize(serverWorld, FairyTaleGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
					browncoatEntity.setOwner(FairyTaleGraveEntity.this);
					browncoatEntity.defenseMultiplier = FairyTaleGraveEntity.this.defenseMultiplier;
					serverWorld.spawnEntityAndPassengers(browncoatEntity);
				}
			}
			if (serverWorld.toServerWorld().getTime() > 24000) {
				if (probability2 <= 0.05 / halfModifier * survChance) { // 5% x1 Buckethead
					for (int u = 0; u < 1; ++u) {
						if (!FairyTaleGraveEntity.this.is1x1()) {
							zombiePosZ = FairyTaleGraveEntity.this.random.range(-1, 1);
							zombiePos = FairyTaleGraveEntity.this.random.range(-1, 1);
						}
						if (FairyTaleGraveEntity.this.isChallengeGrave()) {
							zombiePosZ = FairyTaleGraveEntity.this.random.range(-3, 3);
							zombiePos = FairyTaleGraveEntity.this.random.range(-3, 3);
						}
						BlockPos blockPos = FairyTaleGraveEntity.this.getBlockPos().add(zombiePos, 0, zombiePosZ);
						PokerEntity bucketheadEntity = (PokerEntity) PvZEntity.POKERBUCKET.create(FairyTaleGraveEntity.this.getWorld());
						bucketheadEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
						bucketheadEntity.initialize(serverWorld, FairyTaleGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
						bucketheadEntity.setOwner(FairyTaleGraveEntity.this);
						bucketheadEntity.defenseMultiplier = FairyTaleGraveEntity.this.defenseMultiplier;
						serverWorld.spawnEntityAndPassengers(bucketheadEntity);
					}
					for (int c = 0; c < 1; ++c) { // 100% x1 Conehead
						if (!FairyTaleGraveEntity.this.is1x1()) {
							zombiePosZ = FairyTaleGraveEntity.this.random.range(-1, 1);
							zombiePos = FairyTaleGraveEntity.this.random.range(-1, 1);
						}
						if (FairyTaleGraveEntity.this.isChallengeGrave()) {
							zombiePosZ = FairyTaleGraveEntity.this.random.range(-3, 3);
							zombiePos = FairyTaleGraveEntity.this.random.range(-3, 3);
						}
						BlockPos blockPos = FairyTaleGraveEntity.this.getBlockPos().add(zombiePos, 0, zombiePosZ);
						PokerEntity coneheadEntity = (PokerEntity) PvZEntity.POKERCONE.create(FairyTaleGraveEntity.this.getWorld());
						coneheadEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
						coneheadEntity.initialize(serverWorld, FairyTaleGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
						coneheadEntity.setOwner(FairyTaleGraveEntity.this);
						coneheadEntity.defenseMultiplier = FairyTaleGraveEntity.this.defenseMultiplier;
					serverWorld.spawnEntityAndPassengers(coneheadEntity);
					}
				}
				if (difficulty >= 1.519 + difficultymodifier || isUnlock()) {
					if (probability11 <= 0.05 / halfModifier * survChance) { // 5% x2 Conehead
						for (int c = 0; c < 2 / halfModifier; ++c) {
							if (!FairyTaleGraveEntity.this.is1x1()) {
								zombiePosZ = FairyTaleGraveEntity.this.random.range(-1, 1);
								zombiePos = FairyTaleGraveEntity.this.random.range(-1, 1);
							}
							if (FairyTaleGraveEntity.this.isChallengeGrave()) {
								zombiePosZ = FairyTaleGraveEntity.this.random.range(-3, 3);
								zombiePos = FairyTaleGraveEntity.this.random.range(-3, 3);
							}
							BlockPos blockPos = FairyTaleGraveEntity.this.getBlockPos().add(zombiePos, 0, zombiePosZ);
							PokerEntity coneheadEntity = (PokerEntity) PvZEntity.POKERCONE.create(FairyTaleGraveEntity.this.getWorld());
							coneheadEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
							coneheadEntity.initialize(serverWorld, FairyTaleGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							coneheadEntity.setOwner(FairyTaleGraveEntity.this);
							coneheadEntity.defenseMultiplier = FairyTaleGraveEntity.this.defenseMultiplier;
					serverWorld.spawnEntityAndPassengers(coneheadEntity);
						}
						for (int b = 0; b < 1; ++b) { // 100% x1 Browncoat
							if (!FairyTaleGraveEntity.this.is1x1()) {
								zombiePosZ = FairyTaleGraveEntity.this.random.range(-1, 1);
								zombiePos = FairyTaleGraveEntity.this.random.range(-1, 1);
							}
							if (FairyTaleGraveEntity.this.isChallengeGrave()) {
								zombiePosZ = FairyTaleGraveEntity.this.random.range(-3, 3);
								zombiePos = FairyTaleGraveEntity.this.random.range(-3, 3);
							}
							BlockPos blockPos = FairyTaleGraveEntity.this.getBlockPos().add(zombiePos, 0, zombiePosZ);
							PokerEntity browncoatEntity = (PokerEntity) PvZEntity.POKER.create(FairyTaleGraveEntity.this.getWorld());
							browncoatEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
							browncoatEntity.initialize(serverWorld, FairyTaleGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							browncoatEntity.setOwner(FairyTaleGraveEntity.this);
							browncoatEntity.defenseMultiplier = FairyTaleGraveEntity.this.defenseMultiplier;
					serverWorld.spawnEntityAndPassengers(browncoatEntity);
						}
					}
				}
			}
			if (difficulty >= 1.599 + difficultymodifier || isUnlock()) {
				if (probability22 <= 0.15 / halfModifier * survChance) { // 15% x1 Buckethead
					for (int u = 0; u < 2 / halfModifier; ++u) {
						if (!FairyTaleGraveEntity.this.is1x1()) {
							zombiePosZ = FairyTaleGraveEntity.this.random.range(-1, 1);
							zombiePos = FairyTaleGraveEntity.this.random.range(-1, 1);
						}
						if (FairyTaleGraveEntity.this.isChallengeGrave()) {
							zombiePosZ = FairyTaleGraveEntity.this.random.range(-3, 3);
							zombiePos = FairyTaleGraveEntity.this.random.range(-3, 3);
						}
						BlockPos blockPos = FairyTaleGraveEntity.this.getBlockPos().add(zombiePos, 0, zombiePosZ);
						PokerEntity bucketheadEntity = (PokerEntity) PvZEntity.POKERBUCKET.create(FairyTaleGraveEntity.this.getWorld());
						bucketheadEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
						bucketheadEntity.initialize(serverWorld, FairyTaleGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
						bucketheadEntity.setOwner(FairyTaleGraveEntity.this);
						bucketheadEntity.defenseMultiplier = FairyTaleGraveEntity.this.defenseMultiplier;
						serverWorld.spawnEntityAndPassengers(bucketheadEntity);
					}
					for (int b = 0; b < 1; ++b) { // 100% x1 Browncoat
						if (!FairyTaleGraveEntity.this.is1x1()) {
							zombiePosZ = FairyTaleGraveEntity.this.random.range(-1, 1);
							zombiePos = FairyTaleGraveEntity.this.random.range(-1, 1);
						}
						if (FairyTaleGraveEntity.this.isChallengeGrave()) {
							zombiePosZ = FairyTaleGraveEntity.this.random.range(-3, 3);
							zombiePos = FairyTaleGraveEntity.this.random.range(-3, 3);
						}
						BlockPos blockPos = FairyTaleGraveEntity.this.getBlockPos().add(zombiePos, 0, zombiePosZ);
						PokerEntity browncoatEntity = (PokerEntity) PvZEntity.POKER.create(FairyTaleGraveEntity.this.getWorld());
						browncoatEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
						browncoatEntity.initialize(serverWorld, FairyTaleGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
						browncoatEntity.setOwner(FairyTaleGraveEntity.this);
						browncoatEntity.defenseMultiplier = FairyTaleGraveEntity.this.defenseMultiplier;
					serverWorld.spawnEntityAndPassengers(browncoatEntity);
					}
				}
			}
			if (difficulty >= 1.599 + difficultymodifier || isUnlock()) {
				if (probability10 <= 0.3 / halfModifier * survChance) { // 5% x1 Brickhead Zombie
					for (int j = 0; j < 1; ++j) {
						if (!FairyTaleGraveEntity.this.is1x1()) {
							zombiePosZ = FairyTaleGraveEntity.this.random.range(-1, 1);
							zombiePos = FairyTaleGraveEntity.this.random.range(-1, 1);
						}
						if (FairyTaleGraveEntity.this.isChallengeGrave()) {
							zombiePosZ = FairyTaleGraveEntity.this.random.range(-3, 3);
							zombiePos = FairyTaleGraveEntity.this.random.range(-3, 3);
						}
						EntityType<?> pokerType = PvZEntity.POKERPAWN;
						double random = Math.random();
						if (random <= 0.25) {
							pokerType = PvZEntity.POKERBISHOP;
						} else if (random <= 0.5) {
							pokerType = PvZEntity.POKERTOWER;
						} else if (random <= 0.75) {
							pokerType = PvZEntity.POKERKNIGHT;
						}
						BlockPos blockPos = FairyTaleGraveEntity.this.getBlockPos().add(zombiePos, 0, zombiePosZ);
						PokerEntity brickhead = (PokerEntity) pokerType.create(FairyTaleGraveEntity.this.getWorld());
						brickhead.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
						brickhead.initialize(serverWorld, FairyTaleGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
						brickhead.setOwner(FairyTaleGraveEntity.this);
						brickhead.defenseMultiplier = FairyTaleGraveEntity.this.defenseMultiplier;
							serverWorld.spawnEntityAndPassengers(brickhead);
					}
				}
			}
			if (serverWorld.toServerWorld().getTime() > 24000) {
				if (extraGraveWeight <= 2.5) {
					if (difficulty >= 1.599 + difficultymodifier || isUnlock() || isUnlockSpecial()) {
						if (probability4 <= 0.1 / halfModifier * survChance) { // 10% x1 Flag Zombie
							for (int f = 0; f < 1; ++f) {
								if (!FairyTaleGraveEntity.this.is1x1()) {
									zombiePosZ = FairyTaleGraveEntity.this.random.range(-1, 1);
									zombiePos = FairyTaleGraveEntity.this.random.range(-1, 1);
								}
								if (FairyTaleGraveEntity.this.isChallengeGrave()) {
									zombiePosZ = FairyTaleGraveEntity.this.random.range(-3, 3);
									zombiePos = FairyTaleGraveEntity.this.random.range(-3, 3);
								}
								BlockPos blockPos = FairyTaleGraveEntity.this.getBlockPos().add(zombiePos, 0, zombiePosZ);
								FlagPokerEntity flagzombieEntity = (FlagPokerEntity) PvZEntity.FLAGPOKER.create(FairyTaleGraveEntity.this.getWorld());
								flagzombieEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
								flagzombieEntity.initialize(serverWorld, FairyTaleGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
								flagzombieEntity.setOwner(FairyTaleGraveEntity.this);
								flagzombieEntity.defenseMultiplier = FairyTaleGraveEntity.this.defenseMultiplier;
								serverWorld.spawnEntityAndPassengers(flagzombieEntity);
							}
							extraGraveWeight += 1;
						}
					}
				}
				if (extraGraveWeight <= 2.5) {
					if (difficulty >= 1.549 + difficultymodifier || isUnlock()) {
						if (probability3 <= 0.15 / halfModifier * survChance) { // 15% x2 Zomblob
							for (int p = 0; p < 2 / halfModifier; ++p) {
								if (!FairyTaleGraveEntity.this.is1x1()) {
									zombiePosZ = FairyTaleGraveEntity.this.random.range(-1, 1);
									zombiePos = FairyTaleGraveEntity.this.random.range(-1, 1);
								}
								if (FairyTaleGraveEntity.this.isChallengeGrave()) {
									zombiePosZ = FairyTaleGraveEntity.this.random.range(-3, 3);
									zombiePos = FairyTaleGraveEntity.this.random.range(-3, 3);
								}
								BlockPos blockPos = FairyTaleGraveEntity.this.getBlockPos().add(zombiePos, 0, zombiePosZ);
								ZomblobEntity zomblob = (ZomblobEntity) PvZEntity.ZOMBLOB.create(FairyTaleGraveEntity.this.getWorld());
								zomblob.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
								zomblob.initialize(serverWorld, FairyTaleGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
								zomblob.setOwner(FairyTaleGraveEntity.this);
								zomblob.defenseMultiplier = FairyTaleGraveEntity.this.defenseMultiplier;
								serverWorld.spawnEntityAndPassengers(zomblob);
							}
							extraGraveWeight += 1;
						}
					}
				}
				if (extraGraveWeight <= 2.5) {
					if ((difficulty >= 2.009 + difficultymodifier) && (isUnlock() || isUnlockSpecial())) {
						if (probability6 <= 0.05 / halfModifier * survChance) { // 5% x3 Brickhead
							for (int j = 0; j < 3/ halfModifier ; ++j) {
								if (!FairyTaleGraveEntity.this.is1x1()) {
									zombiePosZ = FairyTaleGraveEntity.this.random.range(-1, 1);
									zombiePos = FairyTaleGraveEntity.this.random.range(-1, 1);
								}
								if (FairyTaleGraveEntity.this.isChallengeGrave()) {
									zombiePosZ = FairyTaleGraveEntity.this.random.range(-3, 3);
									zombiePos = FairyTaleGraveEntity.this.random.range(-3, 3);
								}
								EntityType<?> pokerType = PvZEntity.POKERPAWN;
								double random = Math.random();
								if (random <= 0.25){
									pokerType = PvZEntity.POKERBISHOP;
								}
								else if (random <= 0.5){
									pokerType = PvZEntity.POKERTOWER;
								}
								else if (random <= 0.75){
									pokerType = PvZEntity.POKERKNIGHT;
								}
								BlockPos blockPos = FairyTaleGraveEntity.this.getBlockPos().add(zombiePos, 0, zombiePosZ);
								PokerEntity brickhead = (PokerEntity) pokerType.create(FairyTaleGraveEntity.this.getWorld());
								brickhead.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
								brickhead.initialize(serverWorld, FairyTaleGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
								brickhead.setOwner(FairyTaleGraveEntity.this);
								brickhead.defenseMultiplier = FairyTaleGraveEntity.this.defenseMultiplier;
							serverWorld.spawnEntityAndPassengers(brickhead);
							}
							for (int b = 0; b < 1; ++b) { // 100% x1 Browncoat
								if (!FairyTaleGraveEntity.this.is1x1()) {
									zombiePosZ = FairyTaleGraveEntity.this.random.range(-1, 1);
									zombiePos = FairyTaleGraveEntity.this.random.range(-1, 1);
								}
								if (FairyTaleGraveEntity.this.isChallengeGrave()) {
									zombiePosZ = FairyTaleGraveEntity.this.random.range(-3, 3);
									zombiePos = FairyTaleGraveEntity.this.random.range(-3, 3);
								}
								BlockPos blockPos = FairyTaleGraveEntity.this.getBlockPos().add(zombiePos, 0, zombiePosZ);
								PokerEntity browncoatEntity = (PokerEntity) PvZEntity.POKER.create(FairyTaleGraveEntity.this.getWorld());
								browncoatEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
								browncoatEntity.initialize(serverWorld, FairyTaleGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
								browncoatEntity.setOwner(FairyTaleGraveEntity.this);
								browncoatEntity.defenseMultiplier = FairyTaleGraveEntity.this.defenseMultiplier;
					serverWorld.spawnEntityAndPassengers(browncoatEntity);
							}
							for (int b = 0; b < 1; ++b) { // 100% x1 Conehead
								if (!FairyTaleGraveEntity.this.is1x1()) {
									zombiePosZ = FairyTaleGraveEntity.this.random.range(-1, 1);
									zombiePos = FairyTaleGraveEntity.this.random.range(-1, 1);
								}
								if (FairyTaleGraveEntity.this.isChallengeGrave()) {
									zombiePosZ = FairyTaleGraveEntity.this.random.range(-3, 3);
									zombiePos = FairyTaleGraveEntity.this.random.range(-3, 3);
								}
								BlockPos blockPos = FairyTaleGraveEntity.this.getBlockPos().add(zombiePos, 0, zombiePosZ);
								PokerEntity browncoatEntity = (PokerEntity) PvZEntity.POKERCONE.create(FairyTaleGraveEntity.this.getWorld());
								browncoatEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
								browncoatEntity.initialize(serverWorld, FairyTaleGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
								browncoatEntity.setOwner(FairyTaleGraveEntity.this);
								browncoatEntity.defenseMultiplier = FairyTaleGraveEntity.this.defenseMultiplier;
					serverWorld.spawnEntityAndPassengers(browncoatEntity);
							}
							extraGraveWeight += 1.75;
						}
					}
				}
				if (extraGraveWeight <= 2.5) {
					if (probability13 <= 0.3 / halfModifier * survChance) { // 30% x1 Zomblob
						for (int p = 0; p < 1; ++p) {
							if (!FairyTaleGraveEntity.this.is1x1()) {
								zombiePosZ = FairyTaleGraveEntity.this.random.range(-1, 1);
								zombiePos = FairyTaleGraveEntity.this.random.range(-1, 1);
							}
							if (FairyTaleGraveEntity.this.isChallengeGrave()) {
								zombiePosZ = FairyTaleGraveEntity.this.random.range(-3, 3);
								zombiePos = FairyTaleGraveEntity.this.random.range(-3, 3);
							}
							BlockPos blockPos = FairyTaleGraveEntity.this.getBlockPos().add(zombiePos, 0, zombiePosZ);
							ZomblobEntity zomblob = (ZomblobEntity) PvZEntity.ZOMBLOB.create(FairyTaleGraveEntity.this.getWorld());
							zomblob.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
							zomblob.initialize(serverWorld, FairyTaleGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							zomblob.setOwner(FairyTaleGraveEntity.this);
							zomblob.defenseMultiplier = FairyTaleGraveEntity.this.defenseMultiplier;
							serverWorld.spawnEntityAndPassengers(zomblob);
						}
						extraGraveWeight += 0.5;
					}
				}
				if (extraGraveWeight <= 2.5) {
					if (probability21 <= 0.25 / halfModifier * survChance) {
						if (difficulty >= 1.599 + difficultymodifier || isUnlock()) { // 25% x1 Brickhead
							for (int j = 0; j < 1; ++j) {
								if (!FairyTaleGraveEntity.this.is1x1()) {
									zombiePosZ = FairyTaleGraveEntity.this.random.range(-1, 1);
									zombiePos = FairyTaleGraveEntity.this.random.range(-1, 1);
								}
								if (FairyTaleGraveEntity.this.isChallengeGrave()) {
									zombiePosZ = FairyTaleGraveEntity.this.random.range(-3, 3);
									zombiePos = FairyTaleGraveEntity.this.random.range(-3, 3);
								}
								EntityType<?> pokerType = PvZEntity.POKERPAWN;
								double random = Math.random();
								if (random <= 0.25){
									pokerType = PvZEntity.POKERBISHOP;
								}
								else if (random <= 0.5){
									pokerType = PvZEntity.POKERTOWER;
								}
								else if (random <= 0.75){
									pokerType = PvZEntity.POKERKNIGHT;
								}
								BlockPos blockPos = FairyTaleGraveEntity.this.getBlockPos().add(zombiePos, 0, zombiePosZ);
								PokerEntity brickhead = (PokerEntity) pokerType.create(FairyTaleGraveEntity.this.getWorld());
								brickhead.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
								brickhead.initialize(serverWorld, FairyTaleGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
								brickhead.setOwner(FairyTaleGraveEntity.this);
								brickhead.defenseMultiplier = FairyTaleGraveEntity.this.defenseMultiplier;
							serverWorld.spawnEntityAndPassengers(brickhead);
							}
							for (int h = 0; h < 1; ++h) { // 100% x1 Buckethead
								if (!FairyTaleGraveEntity.this.is1x1()) {
									zombiePosZ = FairyTaleGraveEntity.this.random.range(-1, 1);
									zombiePos = FairyTaleGraveEntity.this.random.range(-1, 1);
								}
								if (FairyTaleGraveEntity.this.isChallengeGrave()) {
									zombiePosZ = FairyTaleGraveEntity.this.random.range(-3, 3);
									zombiePos = FairyTaleGraveEntity.this.random.range(-3, 3);
								}
								BlockPos blockPos = FairyTaleGraveEntity.this.getBlockPos().add(zombiePos, 0, zombiePosZ);
								PokerEntity buckethead = (PokerEntity) PvZEntity.POKERBUCKET.create(FairyTaleGraveEntity.this.getWorld());
								buckethead.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
								buckethead.initialize(serverWorld, FairyTaleGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
								buckethead.setOwner(FairyTaleGraveEntity.this);
								buckethead.defenseMultiplier = FairyTaleGraveEntity.this.defenseMultiplier;
								serverWorld.spawnEntityAndPassengers(buckethead);
							}
							extraGraveWeight += 0.75;
						}
					}
				}
				if (specialGraveWeight <= 3) {
					if ((difficulty >= 2.009 + difficultymodifier) && (isUnlock() || isUnlockSpecial())) {
						if (probability12 <= 0.25 / halfModifier * survChance) { // 15% x1 Pumpkin Car
							for (int p = 0; p < 1; ++p) {
								if (!FairyTaleGraveEntity.this.is1x1()) {
									zombiePosZ = FairyTaleGraveEntity.this.random.range(-1, 1);
									zombiePos = FairyTaleGraveEntity.this.random.range(-1, 1);
								}
								if (FairyTaleGraveEntity.this.isChallengeGrave()) {
									zombiePosZ = FairyTaleGraveEntity.this.random.range(-3, 3);
									zombiePos = FairyTaleGraveEntity.this.random.range(-3, 3);
								}
								BlockPos blockPos = FairyTaleGraveEntity.this.getBlockPos().add(zombiePos, 0, zombiePosZ);
								PumpkinCarEntity pumpkinCarEntity = (PumpkinCarEntity) PvZEntity.PUMPKINCAR.create(FairyTaleGraveEntity.this.getWorld());
								pumpkinCarEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
								pumpkinCarEntity.initialize(serverWorld, FairyTaleGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
								pumpkinCarEntity.setOwner(FairyTaleGraveEntity.this);
								pumpkinCarEntity.defenseMultiplier = FairyTaleGraveEntity.this.defenseMultiplier;
								serverWorld.spawnEntityAndPassengers(pumpkinCarEntity);
							}
							for (int b = 0; b < 3 / halfModifier; ++b) { // 100% x2 Brickhead
								if (!FairyTaleGraveEntity.this.is1x1()) {
									zombiePosZ = FairyTaleGraveEntity.this.random.range(-1, 1);
									zombiePos = FairyTaleGraveEntity.this.random.range(-1, 1);
								}
								if (FairyTaleGraveEntity.this.isChallengeGrave()) {
									zombiePosZ = FairyTaleGraveEntity.this.random.range(-3, 3);
									zombiePos = FairyTaleGraveEntity.this.random.range(-3, 3);
								}
								EntityType<?> pokerType = PvZEntity.POKERPAWN;
								double random = Math.random();
								if (random <= 0.25){
									pokerType = PvZEntity.POKERBISHOP;
								}
								else if (random <= 0.5){
									pokerType = PvZEntity.POKERTOWER;
								}
								else if (random <= 0.75){
									pokerType = PvZEntity.POKERKNIGHT;
								}
								BlockPos blockPos = FairyTaleGraveEntity.this.getBlockPos().add(zombiePos, 0, zombiePosZ);
								PokerEntity brickhead = (PokerEntity) pokerType.create(FairyTaleGraveEntity.this.getWorld());
								brickhead.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
								brickhead.initialize(serverWorld, FairyTaleGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
								brickhead.setOwner(FairyTaleGraveEntity.this);
								brickhead.defenseMultiplier = FairyTaleGraveEntity.this.defenseMultiplier;
							serverWorld.spawnEntityAndPassengers(brickhead);
							}
							specialGraveWeight += 1.5;
						}
					}
				}
				if (specialGraveWeight <= 3) {
					if ((difficulty >= 2.009 + difficultymodifier) && (isUnlock() || isUnlockSpecial())) {
						if (probability12 <= 0.25 / halfModifier * survChance) { // 25% x1 Red/Black Announcer Imp
							for (int p = 0; p < 1; ++p) {
								if (!FairyTaleGraveEntity.this.is1x1()) {
									zombiePosZ = FairyTaleGraveEntity.this.random.range(-1, 1);
									zombiePos = FairyTaleGraveEntity.this.random.range(-1, 1);
								}
								if (FairyTaleGraveEntity.this.isChallengeGrave()) {
									zombiePosZ = FairyTaleGraveEntity.this.random.range(-3, 3);
									zombiePos = FairyTaleGraveEntity.this.random.range(-3, 3);
								}
								EntityType<?> pokerType = PvZEntity.REDANNOUNCERIMP;
								double random = Math.random();
								if (random <= 0.5){
									pokerType = PvZEntity.BLACKANNOUNCERIMP;
								}
								BlockPos blockPos = FairyTaleGraveEntity.this.getBlockPos().add(zombiePos, 0, zombiePosZ);
								AnnouncerImpEntity announcerImpEntity = (AnnouncerImpEntity) pokerType.create(FairyTaleGraveEntity.this.getWorld());
								announcerImpEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
								announcerImpEntity.initialize(serverWorld, FairyTaleGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
								announcerImpEntity.setOwner(FairyTaleGraveEntity.this);
								announcerImpEntity.defenseMultiplier = FairyTaleGraveEntity.this.defenseMultiplier;
								serverWorld.spawnEntityAndPassengers(announcerImpEntity);
							}
							for (int b = 0; b < 3 / halfModifier; ++b) { // 100% x3 Conehead
								if (!FairyTaleGraveEntity.this.is1x1()) {
									zombiePosZ = FairyTaleGraveEntity.this.random.range(-1, 1);
									zombiePos = FairyTaleGraveEntity.this.random.range(-1, 1);
								}
								if (FairyTaleGraveEntity.this.isChallengeGrave()) {
									zombiePosZ = FairyTaleGraveEntity.this.random.range(-3, 3);
									zombiePos = FairyTaleGraveEntity.this.random.range(-3, 3);
								}
								BlockPos blockPos = FairyTaleGraveEntity.this.getBlockPos().add(zombiePos, 0, zombiePosZ);
								PokerEntity browncoatEntity = (PokerEntity) PvZEntity.POKERCONE.create(FairyTaleGraveEntity.this.getWorld());
								browncoatEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
								browncoatEntity.initialize(serverWorld, FairyTaleGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
								browncoatEntity.setOwner(FairyTaleGraveEntity.this);
								browncoatEntity.defenseMultiplier = FairyTaleGraveEntity.this.defenseMultiplier;
					serverWorld.spawnEntityAndPassengers(browncoatEntity);
							}
							specialGraveWeight += 1.25;
						}
					}
				}
			}
			++this.fairyTaleGraveEntity.spawnCounter;
        }

        protected SoundEvent getSoundPrepare() {
            return PvZSounds.GRAVERISINGEVENT;
        }

        protected Spell getSpell() {
            return Spell.SUMMON_VEX;
        }
    }
}
