package io.github.GrassyDev.pvzmod.registry.entity.gravestones.nightgrave;


import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.config.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.sound.PvZSounds;
import io.github.GrassyDev.pvzmod.registry.entity.gravestones.GraveEntity;
import io.github.GrassyDev.pvzmod.registry.entity.variants.graves.GraveDifficulty;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz1.browncoat.modernday.BrowncoatEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz1.dancingzombie.DancingZombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz1.football.FootballEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz1.gargantuar.modernday.GargantuarEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz1.imp.modernday.ImpEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz1.newspaper.NewspaperEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2.flagzombie.summer.FlagSummerEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2.imp.superfan.SuperFanImpEntity;
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
import software.bernie.geckolib.animatable.GeoEntity;
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

public class NightGraveEntity extends GraveEntity implements GeoEntity {

	private String controllerName = "walkingcontroller";

	private int spawnCounter;



	double tiltchance = this.random.nextDouble();

    private AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);


    public NightGraveEntity(EntityType<NightGraveEntity> entityType, World world) {
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
        this.goalSelector.add(1, new NightGraveEntity.summonZombieGoal(this));
    }


	/** /~*~//~*TICKING*~//~*~/ **/

	public void tick() {
		super.tick();
		this.setTarget(this.getWorld().getClosestPlayer(this.getX(), this.getY(), this.getZ(), 40, true));
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
		else if (this.spawnCounter > 4){
			if (!this.isInfinite()) {
				this.kill();
			}
		}
		if (this.getWorld().isClient && this.isSpellcasting()) {
			GraveEntity.Spell spell = this.getSpell();
			float g = this.bodyYaw * 0.017453292F + MathHelper.cos((float)this.age * 0.6662F) * 0.25F;
			float h = MathHelper.cos(g);
			float i = MathHelper.sin(g);
			this.getWorld().addParticle(ParticleTypes.SMOKE, this.getX() + (double)h * 0.6, this.getY(), this.getZ() + (double)i * 0.6, 0, 0.0125, 0);
			this.getWorld().addParticle(ParticleTypes.SMOKE, this.getX() - (double)h * 0.6, this.getY(), this.getZ() - (double)i * 0.6, 0, 0.0125, 0);
		}
	}


	/** /~*~//~*ATTRIBUTES*~//~*~/ **/

	public static DefaultAttributeContainer.Builder createNightGraveAttributes() {
        return HostileEntity.createAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 100.0D)

                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, PVZCONFIG.nestedZombieHealth.nightGraveH());
    }

	protected SoundEvent getDeathSound() {
		return SoundEvents.BLOCK_ANCIENT_DEBRIS_BREAK;
	}

	protected SoundEvent getHurtSound(DamageSource source) {
		return SoundEvents.BLOCK_BASALT_HIT;
	}






	/** /~*~//~*SPAWNING*~//~*~/ **/

	public static boolean canNightGraveSpawn(EntityType<? extends NightGraveEntity> type, ServerWorldAccess world, SpawnReason spawnReason, BlockPos pos, RandomGenerator random) {
		BlockPos blockPos = pos.down();
		float cavespawn = random.nextFloat();
		if (cavespawn <= 0.66) {
			return world.getDifficulty() != Difficulty.PEACEFUL &&
					!world.getFluidState(pos).isSource() &&
					world.toServerWorld().getTime() > 12000 &&
					pos.getY() > 50 &&
					(world.getAmbientDarkness() >= 2 ||
							world.getLightLevel(LightType.SKY, pos) < 2) &&
					!world.getBlockState(blockPos).getBlock().hasDynamicBounds() &&
					!checkVillager(Vec3d.ofCenter(pos), world) &&
					!checkPlant(Vec3d.ofCenter(pos), world) && Objects.requireNonNull(world.getServer()).getGameRules().getBooleanValue(PvZCubed.SHOULD_GRAVE_SPAWN);
		}
		else {
			return world.getDifficulty() != Difficulty.PEACEFUL &&
					!world.getFluidState(pos).isSource() &&
					world.toServerWorld().getTime() > 12000 &&
					(world.getAmbientDarkness() >= 2 ||
							world.getLightLevel(LightType.SKY, pos) < 2) &&
					!world.getBlockState(blockPos).getBlock().hasDynamicBounds() &&
					!checkVillager(Vec3d.ofCenter(pos), world) &&
					!checkPlant(Vec3d.ofCenter(pos), world) && Objects.requireNonNull(world.getServer()).getGameRules().getBooleanValue(PvZCubed.SHOULD_GRAVE_SPAWN);
		}
	}

	/** /~*~//~*GOALS*~//~*~/ **/

	protected SoundEvent getCastSpellSound() {
        return PvZSounds.ENTITYRISINGEVENT;
    }

	protected abstract class CastSpellGoal extends Goal {
		protected int spellCooldown;
		protected int startTime;

		protected CastSpellGoal() {
		}

		public boolean canStart() {
			LivingEntity livingEntity = NightGraveEntity.this.getTarget();
			if (livingEntity != null && livingEntity.isAlive()) {
				if (NightGraveEntity.this.isSpellcasting()) {
					return false;
				} else {
					return NightGraveEntity.this.age >= this.startTime;
				}
			} else {
				return false;
			}
		}

		public boolean shouldContinue() {
			LivingEntity livingEntity = NightGraveEntity.this.getTarget();
			return livingEntity != null && livingEntity.isAlive() && this.spellCooldown > 0;
		}

		public void start() {
			this.spellCooldown = this.getTickCount(this.getInitialCooldown());
			NightGraveEntity.this.spellTicks = this.getSpellTicks();
			this.startTime = NightGraveEntity.this.age + this.startTimeDelay();
			SoundEvent soundEvent = this.getSoundPrepare();
			if (soundEvent != null) {
				NightGraveEntity.this.playSound(soundEvent, 0.9F, 1.0F);
			}

			NightGraveEntity.this.setSpell(this.getSpell());
		}

		public void tick() {
			--this.spellCooldown;
			if (this.spellCooldown == 0) {
				this.castSpell();
				NightGraveEntity.this.addStatusEffect((new StatusEffectInstance(StatusEffects.GLOWING, 100, 1)));
				NightGraveEntity.this.playSound(NightGraveEntity.this.getCastSpellSound(), 0.9F, 1.0F);
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

		protected abstract GraveEntity.Spell getSpell();
	}


	/** /~*~//~*INTERACTION*~//~*~/ **/

	@Nullable
	@Override
	public ItemStack getPickBlockStack() {
		return ModItems.NIGHTGRAVESPAWN.getDefaultStack();
	}

    class summonZombieGoal extends CastSpellGoal {
        private final TargetPredicate closeZombiePredicate;

		private final NightGraveEntity nightGraveEntity;

        private summonZombieGoal(NightGraveEntity nightGraveEntity) {
            super();
			this.nightGraveEntity = nightGraveEntity;
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
			ServerWorld serverWorld = (ServerWorld) NightGraveEntity.this.getWorld();
			LocalDifficulty localDifficulty = getWorld().getLocalDifficulty(this.nightGraveEntity.getBlockPos());
			double difficulty = 0;
			if (this.nightGraveEntity.getVariant().equals(GraveDifficulty.NONE)) {
				difficulty = localDifficulty.getLocalDifficulty();
				if (difficulty >= 2.1) {
					difficulty = 2.1;
					if (getWorld().getDifficulty().equals(Difficulty.HARD)) {
						difficulty = difficulty + difficultymodifier;
					}
				}
			} else if (this.nightGraveEntity.getVariant().equals(GraveDifficulty.EASY)) {
				difficulty = 1.0;
			} else if (this.nightGraveEntity.getVariant().equals(GraveDifficulty.EASYMED)) {
				difficulty = 1.5;
			} else if (this.nightGraveEntity.getVariant().equals(GraveDifficulty.MED)) {
				difficulty = 1.6;
			} else if (this.nightGraveEntity.getVariant().equals(GraveDifficulty.MEDHARD)) {
				difficulty = 1.8;
			} else if (this.nightGraveEntity.getVariant().equals(GraveDifficulty.HARD)) {
				difficulty = 2.1;
			} else if (this.nightGraveEntity.getVariant().equals(GraveDifficulty.SUPERHARD)) {
				difficulty = 3;
			} else if (this.nightGraveEntity.getVariant().equals(GraveDifficulty.NIGHTMARE)) {
				difficulty = 4;
			} else if (this.nightGraveEntity.getVariant().equals(GraveDifficulty.CRAAAZY)) {
				difficulty = 5;
			}
			double probability = random.nextDouble() * Math.pow(difficulty / 2, -1 * (difficulty / 2));
			double probability11 = random.nextDouble() * Math.pow(difficulty / 2, -1 * (difficulty / 2));
			double probability2 = random.nextDouble() * Math.pow(difficulty / 2, -1 * (difficulty / 2));
			double probability21 = random.nextDouble() * Math.pow(difficulty / 2, -1 * (difficulty / 2));
			double probability22 = random.nextDouble() * Math.pow(difficulty / 2, -1 * (difficulty / 2));
			double probability23 = random.nextDouble() * Math.pow(difficulty / 2, -1 * (difficulty / 2));
			double probability231 = random.nextDouble() * Math.pow(difficulty / 2, -1 * (difficulty / 2));
			double probability4 = random.nextDouble() * Math.pow(difficulty / 2, -1 * (difficulty / 2));
			double probability5 = random.nextDouble() * Math.pow(difficulty / 2, -1 * (difficulty / 2));
			double probability6 = random.nextDouble() * Math.pow(difficulty / 2, -1 * (difficulty / 2));
			double probability24 = random.nextDouble() * Math.pow(difficulty / 2, -1 * (difficulty / 2));
			double probability8 = random.nextDouble() * Math.pow(difficulty / 2, -1 * (difficulty / 2));
			double probability9 = random.nextDouble() * Math.pow(difficulty / 2, -1 * (difficulty / 2));
			double probability12 = random.nextDouble() * Math.pow(difficulty / 2, -1 * (difficulty / 2));
			double probability121 = random.nextDouble() * Math.pow(difficulty / 2, -1 * (difficulty / 2));
			double probability13 = random.nextDouble() * Math.pow(difficulty / 2, -1 * (difficulty / 2));
			double probability14 = random.nextDouble() * Math.pow(difficulty / 2, -1 * (difficulty / 2));

			int zombiePos = -2 + NightGraveEntity.this.random.nextInt(5);
			int zombiePosZ = -2 + NightGraveEntity.this.random.nextInt(5);
			if (NightGraveEntity.this.is1x1()) {
				zombiePos = 0;
				zombiePosZ = 0;
			}

			for(int b = 0; b < 1; ++b) { // 100% x1 Browncoat
				if (!NightGraveEntity.this.is1x1()) {
					zombiePosZ = NightGraveEntity.this.random.range(-1, 1);
					zombiePos = NightGraveEntity.this.random.range(-1, 1);
				}
				if (NightGraveEntity.this.isChallengeGrave()) {
					zombiePosZ = NightGraveEntity.this.random.range(-3, 3);
					zombiePos = NightGraveEntity.this.random.range(-3, 3);
				}
				BlockPos blockPos = NightGraveEntity.this.getBlockPos().add(zombiePos, 0, zombiePosZ);
				BrowncoatEntity browncoatEntity = (BrowncoatEntity)PvZEntity.SUMMERBASIC.create(NightGraveEntity.this.getWorld());
				browncoatEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
				browncoatEntity.initialize(serverWorld, NightGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData)null, (NbtCompound)null);
				browncoatEntity.setOwner(NightGraveEntity.this);
				browncoatEntity.defenseMultiplier = NightGraveEntity.this.defenseMultiplier;
					serverWorld.spawnEntityAndPassengers(browncoatEntity);
			}
			if (probability <= 0.25 / halfModifier * survChance) { // 25% x1 Conehead
				for (int c = 0; c < 1; ++c) {
					if (!NightGraveEntity.this.is1x1()) {
						zombiePosZ = NightGraveEntity.this.random.range(-1, 1);
						zombiePos = NightGraveEntity.this.random.range(-1, 1);
					}
					if (NightGraveEntity.this.isChallengeGrave()) {
						zombiePosZ = NightGraveEntity.this.random.range(-3, 3);
						zombiePos = NightGraveEntity.this.random.range(-3, 3);
					}
					BlockPos blockPos = NightGraveEntity.this.getBlockPos().add(zombiePos, 0, zombiePosZ);
					BrowncoatEntity coneheadEntity = (BrowncoatEntity) PvZEntity.SUMMERCONEHEAD.create(NightGraveEntity.this.getWorld());
					coneheadEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
					coneheadEntity.initialize(serverWorld, NightGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
					coneheadEntity.setOwner(NightGraveEntity.this);
					coneheadEntity.defenseMultiplier = NightGraveEntity.this.defenseMultiplier;
					serverWorld.spawnEntityAndPassengers(coneheadEntity);
				}
				for(int b = 0; b < 1; ++b) { // 100% x1 Browncoat
					if (!NightGraveEntity.this.is1x1()) {
						zombiePosZ = NightGraveEntity.this.random.range(-1, 1);
						zombiePos = NightGraveEntity.this.random.range(-1, 1);
					}
					if (NightGraveEntity.this.isChallengeGrave()) {
						zombiePosZ = NightGraveEntity.this.random.range(-3, 3);
						zombiePos = NightGraveEntity.this.random.range(-3, 3);
					}
					BlockPos blockPos = NightGraveEntity.this.getBlockPos().add(zombiePos, 0, zombiePosZ);
					BrowncoatEntity browncoatEntity = (BrowncoatEntity)PvZEntity.SUMMERBASIC.create(NightGraveEntity.this.getWorld());
					browncoatEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
					browncoatEntity.initialize(serverWorld, NightGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData)null, (NbtCompound)null);
					browncoatEntity.setOwner(NightGraveEntity.this);
					browncoatEntity.defenseMultiplier = NightGraveEntity.this.defenseMultiplier;
					serverWorld.spawnEntityAndPassengers(browncoatEntity);
				}
			}
			if (serverWorld.toServerWorld().getTime() > 24000) {
				if (probability2 <= 0.15 / halfModifier * survChance) { // 15% x1 Screendoor
					for (int u = 0; u < 1; ++u) {
						if (!NightGraveEntity.this.is1x1()) {
							zombiePosZ = NightGraveEntity.this.random.range(-1, 1);
							zombiePos = NightGraveEntity.this.random.range(-1, 1);
						}
						if (NightGraveEntity.this.isChallengeGrave()) {
							zombiePosZ = NightGraveEntity.this.random.range(-3, 3);
							zombiePos = NightGraveEntity.this.random.range(-3, 3);
						}
						BlockPos blockPos = NightGraveEntity.this.getBlockPos().add(zombiePos, 0, zombiePosZ);
						BrowncoatEntity screendoorEntity = (BrowncoatEntity) PvZEntity.SCREENDOOR.create(NightGraveEntity.this.getWorld());
						screendoorEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
						screendoorEntity.initialize(serverWorld, NightGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
						screendoorEntity.setOwner(NightGraveEntity.this);
						screendoorEntity.defenseMultiplier = NightGraveEntity.this.defenseMultiplier;
						serverWorld.spawnEntityAndPassengers(screendoorEntity);
					}
					for (int c = 0; c < 1; ++c) { // 100% x1 Conehead
						if (!NightGraveEntity.this.is1x1()) {
							zombiePosZ = NightGraveEntity.this.random.range(-1, 1);
							zombiePos = NightGraveEntity.this.random.range(-1, 1);
						}
						if (NightGraveEntity.this.isChallengeGrave()) {
							zombiePosZ = NightGraveEntity.this.random.range(-3, 3);
							zombiePos = NightGraveEntity.this.random.range(-3, 3);
						}
						BlockPos blockPos = NightGraveEntity.this.getBlockPos().add(zombiePos, 0, zombiePosZ);
						BrowncoatEntity coneheadEntity = (BrowncoatEntity) PvZEntity.SUMMERCONEHEAD.create(NightGraveEntity.this.getWorld());
						coneheadEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
						coneheadEntity.initialize(serverWorld, NightGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
						coneheadEntity.setOwner(NightGraveEntity.this);
						coneheadEntity.defenseMultiplier = NightGraveEntity.this.defenseMultiplier;
					serverWorld.spawnEntityAndPassengers(coneheadEntity);
					}
				}
				if (difficulty >= 1.519 + difficultymodifier || isUnlock()) {
					if (probability11 <= 0.05 / halfModifier * survChance) { // 5% x2 Conehead
						for (int c = 0; c < 2 / halfModifier; ++c) {
							if (!NightGraveEntity.this.is1x1()) {
								zombiePosZ = NightGraveEntity.this.random.range(-1, 1);
								zombiePos = NightGraveEntity.this.random.range(-1, 1);
							}
							if (NightGraveEntity.this.isChallengeGrave()) {
								zombiePosZ = NightGraveEntity.this.random.range(-3, 3);
								zombiePos = NightGraveEntity.this.random.range(-3, 3);
							}
							BlockPos blockPos = NightGraveEntity.this.getBlockPos().add(zombiePos, 0, zombiePosZ);
							BrowncoatEntity coneheadEntity = (BrowncoatEntity) PvZEntity.SUMMERCONEHEAD.create(NightGraveEntity.this.getWorld());
							coneheadEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
							coneheadEntity.initialize(serverWorld, NightGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							coneheadEntity.setOwner(NightGraveEntity.this);
							coneheadEntity.defenseMultiplier = NightGraveEntity.this.defenseMultiplier;
					serverWorld.spawnEntityAndPassengers(coneheadEntity);
						}
						for(int b = 0; b < 2 / halfModifier; ++b) { // 100% x2 Browncoat
							if (!NightGraveEntity.this.is1x1()) {
								zombiePosZ = NightGraveEntity.this.random.range(-1, 1);
								zombiePos = NightGraveEntity.this.random.range(-1, 1);
							}
							if (NightGraveEntity.this.isChallengeGrave()) {
								zombiePosZ = NightGraveEntity.this.random.range(-3, 3);
								zombiePos = NightGraveEntity.this.random.range(-3, 3);
							}
							BlockPos blockPos = NightGraveEntity.this.getBlockPos().add(zombiePos, 0, zombiePosZ);
							BrowncoatEntity browncoatEntity = (BrowncoatEntity)PvZEntity.SUMMERBASIC.create(NightGraveEntity.this.getWorld());
							browncoatEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
							browncoatEntity.initialize(serverWorld, NightGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData)null, (NbtCompound)null);
							browncoatEntity.setOwner(NightGraveEntity.this);
							browncoatEntity.defenseMultiplier = NightGraveEntity.this.defenseMultiplier;
					serverWorld.spawnEntityAndPassengers(browncoatEntity);
						}
					}
				}
			}
			if (difficulty >= 1.599 + difficultymodifier || isUnlock()) {
				if (probability22 <= 0.15 / halfModifier * survChance) { // 15% x1 Buckethead
					for (int u = 0; u < 2 / halfModifier; ++u) {
						if (!NightGraveEntity.this.is1x1()) {
							zombiePosZ = NightGraveEntity.this.random.range(-1, 1);
							zombiePos = NightGraveEntity.this.random.range(-1, 1);
						}
						if (NightGraveEntity.this.isChallengeGrave()) {
							zombiePosZ = NightGraveEntity.this.random.range(-3, 3);
							zombiePos = NightGraveEntity.this.random.range(-3, 3);
						}
						BlockPos blockPos = NightGraveEntity.this.getBlockPos().add(zombiePos, 0, zombiePosZ);
						BrowncoatEntity bucketheadEntity = (BrowncoatEntity) PvZEntity.SUMMERBUCKETHEAD.create(NightGraveEntity.this.getWorld());
						bucketheadEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
						bucketheadEntity.initialize(serverWorld, NightGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
						bucketheadEntity.setOwner(NightGraveEntity.this);
						bucketheadEntity.defenseMultiplier = NightGraveEntity.this.defenseMultiplier;
						serverWorld.spawnEntityAndPassengers(bucketheadEntity);
					}
					for (int b = 0; b < 1; ++b) { // 100% x1 Browncoat
						if (!NightGraveEntity.this.is1x1()) {
							zombiePosZ = NightGraveEntity.this.random.range(-1, 1);
							zombiePos = NightGraveEntity.this.random.range(-1, 1);
						}
						if (NightGraveEntity.this.isChallengeGrave()) {
							zombiePosZ = NightGraveEntity.this.random.range(-3, 3);
							zombiePos = NightGraveEntity.this.random.range(-3, 3);
						}
						BlockPos blockPos = NightGraveEntity.this.getBlockPos().add(zombiePos, 0, zombiePosZ);
						BrowncoatEntity browncoatEntity = (BrowncoatEntity) PvZEntity.SUMMERBASIC.create(NightGraveEntity.this.getWorld());
						browncoatEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
						browncoatEntity.initialize(serverWorld, NightGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
						browncoatEntity.setOwner(NightGraveEntity.this);
						browncoatEntity.defenseMultiplier = NightGraveEntity.this.defenseMultiplier;
					serverWorld.spawnEntityAndPassengers(browncoatEntity);
					}
				}
			}
			if (serverWorld.toServerWorld().getTime() > 24000) {
				if (extraGraveWeight <= 2.5) {
					if (difficulty >= 1.599 + difficultymodifier || isUnlock() || isUnlockSpecial()) {
						if (probability4 <= 0.1 / halfModifier * survChance) { // 10% x1 Flag Zombie
							for (int f = 0; f < 1; ++f) {
								if (!NightGraveEntity.this.is1x1()) {
									zombiePosZ = NightGraveEntity.this.random.range(-1, 1);
									zombiePos = NightGraveEntity.this.random.range(-1, 1);
								}
								if (NightGraveEntity.this.isChallengeGrave()) {
									zombiePosZ = NightGraveEntity.this.random.range(-3, 3);
									zombiePos = NightGraveEntity.this.random.range(-3, 3);
								}
								BlockPos blockPos = NightGraveEntity.this.getBlockPos().add(zombiePos, 0, zombiePosZ);
								FlagSummerEntity flagzombieEntity = (FlagSummerEntity) PvZEntity.FLAGSUMMER.create(NightGraveEntity.this.getWorld());
								flagzombieEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
								flagzombieEntity.initialize(serverWorld, NightGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
								flagzombieEntity.setOwner(NightGraveEntity.this);
								flagzombieEntity.defenseMultiplier = NightGraveEntity.this.defenseMultiplier;
								serverWorld.spawnEntityAndPassengers(flagzombieEntity);
							}
							extraGraveWeight += 1;
						}
					}
				}
				if (extraGraveWeight <= 2.5) {
					if (difficulty >= 1.549 + difficultymodifier || isUnlock()) {
						if (probability13 <= 0.15 / halfModifier * survChance) { // 15% x2 Newspaper Zombie
							for (int p = 0; p < 2 / halfModifier; ++p) {
								if (!NightGraveEntity.this.is1x1()) {
									zombiePosZ = NightGraveEntity.this.random.range(-1, 1);
									zombiePos = NightGraveEntity.this.random.range(-1, 1);
								}
								if (NightGraveEntity.this.isChallengeGrave()) {
									zombiePosZ = NightGraveEntity.this.random.range(-3, 3);
									zombiePos = NightGraveEntity.this.random.range(-3, 3);
								}
								BlockPos blockPos = NightGraveEntity.this.getBlockPos().add(zombiePos, 0, zombiePosZ);
								NewspaperEntity newspaperEntity = (NewspaperEntity) PvZEntity.NEWSPAPER.create(NightGraveEntity.this.getWorld());
								newspaperEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
								newspaperEntity.initialize(serverWorld, NightGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
								newspaperEntity.setOwner(NightGraveEntity.this);
								newspaperEntity.defenseMultiplier = NightGraveEntity.this.defenseMultiplier;
								serverWorld.spawnEntityAndPassengers(newspaperEntity);
							}
							for (int b = 0; b < 2 / halfModifier; ++b) { // 100% x2 Conehead
								if (!NightGraveEntity.this.is1x1()) {
									zombiePosZ = NightGraveEntity.this.random.range(-1, 1);
									zombiePos = NightGraveEntity.this.random.range(-1, 1);
								}
								if (NightGraveEntity.this.isChallengeGrave()) {
									zombiePosZ = NightGraveEntity.this.random.range(-3, 3);
									zombiePos = NightGraveEntity.this.random.range(-3, 3);
								}
								BlockPos blockPos = NightGraveEntity.this.getBlockPos().add(zombiePos, 0, zombiePosZ);
								BrowncoatEntity browncoatEntity = (BrowncoatEntity) PvZEntity.SUMMERCONEHEAD.create(NightGraveEntity.this.getWorld());
								browncoatEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
								browncoatEntity.initialize(serverWorld, NightGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
								browncoatEntity.setOwner(NightGraveEntity.this);
								browncoatEntity.defenseMultiplier = NightGraveEntity.this.defenseMultiplier;
					serverWorld.spawnEntityAndPassengers(browncoatEntity);
							}
							extraGraveWeight += 1;
						}
					}
				}
				if (extraGraveWeight <= 2.5) {
					if ((difficulty >= 2.009 + difficultymodifier) && (isUnlock() || isUnlockSpecial())) {
						if (probability8 <= 0.05 / halfModifier * survChance) { // 15% x2 Football Zombie
							for (int g = 0; g < 2 / halfModifier; ++g) {
								if (!NightGraveEntity.this.is1x1()) {
									zombiePosZ = NightGraveEntity.this.random.range(-1, 1);
									zombiePos = NightGraveEntity.this.random.range(-1, 1);
								}
								if (NightGraveEntity.this.isChallengeGrave()) {
									zombiePosZ = NightGraveEntity.this.random.range(-3, 3);
									zombiePos = NightGraveEntity.this.random.range(-3, 3);
								}
								BlockPos blockPos = NightGraveEntity.this.getBlockPos().add(zombiePos, 0, zombiePosZ);
								FootballEntity footballEntity = (FootballEntity) PvZEntity.FOOTBALL.create(NightGraveEntity.this.getWorld());
								footballEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
								footballEntity.initialize(serverWorld, NightGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
								footballEntity.setOwner(NightGraveEntity.this);
								footballEntity.defenseMultiplier = NightGraveEntity.this.defenseMultiplier;
								serverWorld.spawnEntityAndPassengers(footballEntity);
							}
							for (int b = 0; b < 2 / halfModifier; ++b) { // 100% x2 Screendoor
								if (!NightGraveEntity.this.is1x1()) {
									zombiePosZ = NightGraveEntity.this.random.range(-1, 1);
									zombiePos = NightGraveEntity.this.random.range(-1, 1);
								}
								if (NightGraveEntity.this.isChallengeGrave()) {
									zombiePosZ = NightGraveEntity.this.random.range(-3, 3);
									zombiePos = NightGraveEntity.this.random.range(-3, 3);
								}
								BlockPos blockPos = NightGraveEntity.this.getBlockPos().add(zombiePos, 0, zombiePosZ);
								BrowncoatEntity screendoor = (BrowncoatEntity) PvZEntity.SCREENDOOR.create(NightGraveEntity.this.getWorld());
								screendoor.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
								screendoor.initialize(serverWorld, NightGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
								screendoor.setOwner(NightGraveEntity.this);
								screendoor.defenseMultiplier = NightGraveEntity.this.defenseMultiplier;
								serverWorld.spawnEntityAndPassengers(screendoor);
							}
							extraGraveWeight += 1.25;
						}
					}
				}
				if (extraGraveWeight <= 2.5) {
					if ((difficulty >= 2.009 + difficultymodifier) && (isUnlock() || isUnlockSpecial())) {
						if (probability6 <= 0.05 / halfModifier * survChance) { // 5% x3 Dancing Zombie
							for (int j = 0; j < 3/ halfModifier; ++j) {
								if (!NightGraveEntity.this.is1x1()) {
									zombiePosZ = NightGraveEntity.this.random.range(-1, 1);
									zombiePos = NightGraveEntity.this.random.range(-1, 1);
								}
								if (NightGraveEntity.this.isChallengeGrave()) {
									zombiePosZ = NightGraveEntity.this.random.range(-3, 3);
									zombiePos = NightGraveEntity.this.random.range(-3, 3);
								}
								BlockPos blockPos = NightGraveEntity.this.getBlockPos().add(zombiePos, 0, zombiePosZ);
								DancingZombieEntity brickhead = (DancingZombieEntity) PvZEntity.DANCINGZOMBIE.create(NightGraveEntity.this.getWorld());
								brickhead.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
								brickhead.initialize(serverWorld, NightGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
								brickhead.setOwner(NightGraveEntity.this);
								brickhead.defenseMultiplier = NightGraveEntity.this.defenseMultiplier;
							serverWorld.spawnEntityAndPassengers(brickhead);
							}
							extraGraveWeight += 1.25;
						}
					}
				}
				if (extraGraveWeight <= 2.5) {
					if (difficulty >= 1.549 + difficultymodifier || isUnlock()) {
						if (probability23 <= 0.15 / halfModifier * survChance) { // 15% x2 Super-Fan Imp
							for (int p = 0; p < 2 / halfModifier; ++p) {
								if (!NightGraveEntity.this.is1x1()) {
									zombiePosZ = NightGraveEntity.this.random.range(-1, 1);
									zombiePos = NightGraveEntity.this.random.range(-1, 1);
								}
								if (NightGraveEntity.this.isChallengeGrave()) {
									zombiePosZ = NightGraveEntity.this.random.range(-3, 3);
									zombiePos = NightGraveEntity.this.random.range(-3, 3);
								}
								BlockPos blockPos = NightGraveEntity.this.getBlockPos().add(zombiePos, 0, zombiePosZ);
								SuperFanImpEntity superFanImpEntity = (SuperFanImpEntity) PvZEntity.SUPERFANIMP.create(NightGraveEntity.this.getWorld());
								superFanImpEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
								superFanImpEntity.initialize(serverWorld, NightGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
								superFanImpEntity.setOwner(NightGraveEntity.this);
								superFanImpEntity.defenseMultiplier = NightGraveEntity.this.defenseMultiplier;
								serverWorld.spawnEntityAndPassengers(superFanImpEntity);
							}
							for (int b = 0; b < 3 / halfModifier; ++b) { // 100% x3 Browncoat
								if (!NightGraveEntity.this.is1x1()) {
									zombiePosZ = NightGraveEntity.this.random.range(-1, 1);
									zombiePos = NightGraveEntity.this.random.range(-1, 1);
								}
								if (NightGraveEntity.this.isChallengeGrave()) {
									zombiePosZ = NightGraveEntity.this.random.range(-3, 3);
									zombiePos = NightGraveEntity.this.random.range(-3, 3);
								}
								BlockPos blockPos = NightGraveEntity.this.getBlockPos().add(zombiePos, 0, zombiePosZ);
								BrowncoatEntity browncoatEntity = (BrowncoatEntity) PvZEntity.SUMMERBASIC.create(NightGraveEntity.this.getWorld());
								browncoatEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
								browncoatEntity.initialize(serverWorld, NightGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
								browncoatEntity.setOwner(NightGraveEntity.this);
								browncoatEntity.defenseMultiplier = NightGraveEntity.this.defenseMultiplier;
					serverWorld.spawnEntityAndPassengers(browncoatEntity);
							}
							extraGraveWeight += 0.75;
						}
					}
				}
				if (extraGraveWeight <= 2.5) {
					if (difficulty >= 1.549 + difficultymodifier || isUnlock()) {
						if (probability231 <= 0.15 / halfModifier * survChance) { // 15% x2 Rainbow Bass Imp
							for (int p = 0; p < 2 / halfModifier; ++p) {
								if (!NightGraveEntity.this.is1x1()) {
									zombiePosZ = NightGraveEntity.this.random.range(-1, 1);
									zombiePos = NightGraveEntity.this.random.range(-1, 1);
								}
								if (NightGraveEntity.this.isChallengeGrave()) {
									zombiePosZ = NightGraveEntity.this.random.range(-3, 3);
									zombiePos = NightGraveEntity.this.random.range(-3, 3);
								}
								BlockPos blockPos = NightGraveEntity.this.getBlockPos().add(zombiePos, 0, zombiePosZ);
								ImpEntity bassImp = (ImpEntity) PvZEntity.BASSIMP.create(NightGraveEntity.this.getWorld());
								bassImp.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
								bassImp.initialize(serverWorld, NightGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
								bassImp.setOwner(NightGraveEntity.this);
								bassImp.defenseMultiplier = NightGraveEntity.this.defenseMultiplier;
								serverWorld.spawnEntityAndPassengers(bassImp);
							}
							for (int b = 0; b < 2 / halfModifier; ++b) { // 100% x2 Conehead
								if (!NightGraveEntity.this.is1x1()) {
									zombiePosZ = NightGraveEntity.this.random.range(-1, 1);
									zombiePos = NightGraveEntity.this.random.range(-1, 1);
								}
								if (NightGraveEntity.this.isChallengeGrave()) {
									zombiePosZ = NightGraveEntity.this.random.range(-3, 3);
									zombiePos = NightGraveEntity.this.random.range(-3, 3);
								}
								BlockPos blockPos = NightGraveEntity.this.getBlockPos().add(zombiePos, 0, zombiePosZ);
								BrowncoatEntity browncoatEntity = (BrowncoatEntity) PvZEntity.SUMMERCONEHEAD.create(NightGraveEntity.this.getWorld());
								browncoatEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
								browncoatEntity.initialize(serverWorld, NightGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
								browncoatEntity.setOwner(NightGraveEntity.this);
								browncoatEntity.defenseMultiplier = NightGraveEntity.this.defenseMultiplier;
					serverWorld.spawnEntityAndPassengers(browncoatEntity);
							}
							extraGraveWeight += 0.75;
						}
					}
				}
				if (extraGraveWeight <= 2.5) {
					if (difficulty >= 1.599 + difficultymodifier || isUnlock()) {
						if (probability9 <= 0.2 / halfModifier * survChance) { // 20% x1 Football Zombie
							for (int g = 0; g < 1; ++g) {
								if (!NightGraveEntity.this.is1x1()) {
									zombiePosZ = NightGraveEntity.this.random.range(-1, 1);
									zombiePos = NightGraveEntity.this.random.range(-1, 1);
								}
								if (NightGraveEntity.this.isChallengeGrave()) {
									zombiePosZ = NightGraveEntity.this.random.range(-3, 3);
									zombiePos = NightGraveEntity.this.random.range(-3, 3);
								}
								BlockPos blockPos = NightGraveEntity.this.getBlockPos().add(zombiePos, 0, zombiePosZ);
								FootballEntity footballEntity = (FootballEntity) PvZEntity.FOOTBALL.create(NightGraveEntity.this.getWorld());
								footballEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
								footballEntity.initialize(serverWorld, NightGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
								footballEntity.setOwner(NightGraveEntity.this);
								footballEntity.defenseMultiplier = NightGraveEntity.this.defenseMultiplier;
								serverWorld.spawnEntityAndPassengers(footballEntity);
							}
							for (int b = 0; b < 2 / halfModifier; ++b) { // 100% x2 Browncoat
								if (!NightGraveEntity.this.is1x1()) {
									zombiePosZ = NightGraveEntity.this.random.range(-1, 1);
									zombiePos = NightGraveEntity.this.random.range(-1, 1);
								}
								if (NightGraveEntity.this.isChallengeGrave()) {
									zombiePosZ = NightGraveEntity.this.random.range(-3, 3);
									zombiePos = NightGraveEntity.this.random.range(-3, 3);
								}
								BlockPos blockPos = NightGraveEntity.this.getBlockPos().add(zombiePos, 0, zombiePosZ);
								BrowncoatEntity browncoatEntity = (BrowncoatEntity) PvZEntity.SUMMERBASIC.create(NightGraveEntity.this.getWorld());
								browncoatEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
								browncoatEntity.initialize(serverWorld, NightGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
								browncoatEntity.setOwner(NightGraveEntity.this);
								browncoatEntity.defenseMultiplier = NightGraveEntity.this.defenseMultiplier;
					serverWorld.spawnEntityAndPassengers(browncoatEntity);
							}
							extraGraveWeight += 0.75;
						}
					}
				}
				if (extraGraveWeight <= 2.5) {
					if (probability24 <= 0.3 / halfModifier * survChance) { // 30% x1 Newspaper Zombie
						for (int p = 0; p < 1; ++p) {
							if (!NightGraveEntity.this.is1x1()) {
								zombiePosZ = NightGraveEntity.this.random.range(-1, 1);
								zombiePos = NightGraveEntity.this.random.range(-1, 1);
							}
							if (NightGraveEntity.this.isChallengeGrave()) {
								zombiePosZ = NightGraveEntity.this.random.range(-3, 3);
								zombiePos = NightGraveEntity.this.random.range(-3, 3);
							}
							BlockPos blockPos = NightGraveEntity.this.getBlockPos().add(zombiePos, 0, zombiePosZ);
							NewspaperEntity newspaperEntity = (NewspaperEntity) PvZEntity.NEWSPAPER.create(NightGraveEntity.this.getWorld());
							newspaperEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
							newspaperEntity.initialize(serverWorld, NightGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							newspaperEntity.setOwner(NightGraveEntity.this);
							newspaperEntity.defenseMultiplier = NightGraveEntity.this.defenseMultiplier;
							serverWorld.spawnEntityAndPassengers(newspaperEntity);
						}
						extraGraveWeight += 0.5;
					}
				}
				if (extraGraveWeight <= 2.5) {
					if (probability21 <= 0.25 / halfModifier * survChance) {
						if (difficulty >= 1.599 + difficultymodifier || isUnlock()) { // 25% x1 Dancing Zombie
							for (int j = 0; j < 1; ++j) {
								if (!NightGraveEntity.this.is1x1()) {
									zombiePosZ = NightGraveEntity.this.random.range(-1, 1);
									zombiePos = NightGraveEntity.this.random.range(-1, 1);
								}
								if (NightGraveEntity.this.isChallengeGrave()) {
									zombiePosZ = NightGraveEntity.this.random.range(-3, 3);
									zombiePos = NightGraveEntity.this.random.range(-3, 3);
								}
								BlockPos blockPos = NightGraveEntity.this.getBlockPos().add(zombiePos, 0, zombiePosZ);
								DancingZombieEntity dancingZombieEntity = (DancingZombieEntity) PvZEntity.DANCINGZOMBIE.create(NightGraveEntity.this.getWorld());
								dancingZombieEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
								dancingZombieEntity.initialize(serverWorld, NightGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
								dancingZombieEntity.setOwner(NightGraveEntity.this);
								dancingZombieEntity.defenseMultiplier = NightGraveEntity.this.defenseMultiplier;
								serverWorld.spawnEntityAndPassengers(dancingZombieEntity);
							}
							for (int b = 0; b < 2 / halfModifier; ++b) { // 100% x2 Screendoor
								if (!NightGraveEntity.this.is1x1()) {
									zombiePosZ = NightGraveEntity.this.random.range(-1, 1);
									zombiePos = NightGraveEntity.this.random.range(-1, 1);
								}
								if (NightGraveEntity.this.isChallengeGrave()) {
									zombiePosZ = NightGraveEntity.this.random.range(-3, 3);
									zombiePos = NightGraveEntity.this.random.range(-3, 3);
								}
								BlockPos blockPos = NightGraveEntity.this.getBlockPos().add(zombiePos, 0, zombiePosZ);
								BrowncoatEntity screendoor = (BrowncoatEntity) PvZEntity.SCREENDOOR.create(NightGraveEntity.this.getWorld());
								screendoor.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
								screendoor.initialize(serverWorld, NightGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
								screendoor.setOwner(NightGraveEntity.this);
								screendoor.defenseMultiplier = NightGraveEntity.this.defenseMultiplier;
								serverWorld.spawnEntityAndPassengers(screendoor);
							}
							extraGraveWeight += 0.75;
						}
					}
				}
				if (specialGraveWeight <= 3) {
					if (difficulty >= 2.009 + difficultymodifier || isUnlock()) {
						if (probability12 <= 0.1 / halfModifier * survChance) { // 10% x1 Defensive End
							for (int j = 0; j < 1; ++j) {
								if (!NightGraveEntity.this.is1x1()) {
									zombiePosZ = NightGraveEntity.this.random.range(-1, 1);
									zombiePos = NightGraveEntity.this.random.range(-1, 1);
								}
								if (NightGraveEntity.this.isChallengeGrave()) {
									zombiePosZ = NightGraveEntity.this.random.range(-3, 3);
									zombiePos = NightGraveEntity.this.random.range(-3, 3);
								}
								BlockPos blockPos = NightGraveEntity.this.getBlockPos().add(zombiePos, 0, zombiePosZ);
								GargantuarEntity defensiveEnd = (GargantuarEntity) PvZEntity.DEFENSIVEEND.create(NightGraveEntity.this.getWorld());
								defensiveEnd.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
								defensiveEnd.initialize(serverWorld, NightGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
								defensiveEnd.setOwner(NightGraveEntity.this);
								defensiveEnd.defenseMultiplier = NightGraveEntity.this.defenseMultiplier;
								serverWorld.spawnEntityAndPassengers(defensiveEnd);
							}
							for (int p = 0; p < 2 / halfModifier; ++p) { // 100% x2 Super-Fan Imp
								if (!NightGraveEntity.this.is1x1()) {
									zombiePosZ = NightGraveEntity.this.random.range(-1, 1);
									zombiePos = NightGraveEntity.this.random.range(-1, 1);
								}
								if (NightGraveEntity.this.isChallengeGrave()) {
									zombiePosZ = NightGraveEntity.this.random.range(-3, 3);
									zombiePos = NightGraveEntity.this.random.range(-3, 3);
								}
								BlockPos blockPos = NightGraveEntity.this.getBlockPos().add(zombiePos, 0, zombiePosZ);
								SuperFanImpEntity superFanImpEntity = (SuperFanImpEntity) PvZEntity.SUPERFANIMP.create(NightGraveEntity.this.getWorld());
								superFanImpEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
								superFanImpEntity.initialize(serverWorld, NightGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
								superFanImpEntity.setOwner(NightGraveEntity.this);
								superFanImpEntity.defenseMultiplier = NightGraveEntity.this.defenseMultiplier;
								serverWorld.spawnEntityAndPassengers(superFanImpEntity);
							}
							double random = Math.random();
							if (random <= 0.33){
								for (int j = 0; j < 1; ++j) { // 50% x1 Defensive End
									if (!NightGraveEntity.this.is1x1()) {
										zombiePosZ = NightGraveEntity.this.random.range(-1, 1);
										zombiePos = NightGraveEntity.this.random.range(-1, 1);
									}
									if (NightGraveEntity.this.isChallengeGrave()) {
										zombiePosZ = NightGraveEntity.this.random.range(-3, 3);
										zombiePos = NightGraveEntity.this.random.range(-3, 3);
									}
									BlockPos blockPos = NightGraveEntity.this.getBlockPos().add(zombiePos, 0, zombiePosZ);
									GargantuarEntity defensiveEnd = (GargantuarEntity) PvZEntity.DEFENSIVEEND.create(NightGraveEntity.this.getWorld());
									defensiveEnd.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
									defensiveEnd.initialize(serverWorld, NightGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
									defensiveEnd.setOwner(NightGraveEntity.this);
									defensiveEnd.defenseMultiplier = NightGraveEntity.this.defenseMultiplier;
									serverWorld.spawnEntityAndPassengers(defensiveEnd);
								}
							}
							specialGraveWeight += 1.25;
						}
					}
				}
				if (specialGraveWeight <= 3) {
					if ((difficulty >= 2.009 + difficultymodifier) && (isUnlock() || isUnlockSpecial())) {
						if (probability14 <= 0.15 / halfModifier * survChance) { // 15% x1 Sunday Edition
							for (int p = 0; p < 1; ++p) {
								if (!NightGraveEntity.this.is1x1()) {
									zombiePosZ = NightGraveEntity.this.random.range(-1, 1);
									zombiePos = NightGraveEntity.this.random.range(-1, 1);
								}
								if (NightGraveEntity.this.isChallengeGrave()) {
									zombiePosZ = NightGraveEntity.this.random.range(-3, 3);
									zombiePos = NightGraveEntity.this.random.range(-3, 3);
								}
								BlockPos blockPos = NightGraveEntity.this.getBlockPos().add(zombiePos, 0, zombiePosZ);
								NewspaperEntity sundayEdition = (NewspaperEntity) PvZEntity.SUNDAYEDITION.create(NightGraveEntity.this.getWorld());
								sundayEdition.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
								sundayEdition.initialize(serverWorld, NightGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
								sundayEdition.setOwner(NightGraveEntity.this);
								sundayEdition.defenseMultiplier = NightGraveEntity.this.defenseMultiplier;
								serverWorld.spawnEntityAndPassengers(sundayEdition);
							}
							for (int p = 0; p < 2 / halfModifier; ++p) { // 15% x2 Newspaper
								if (!NightGraveEntity.this.is1x1()) {
									zombiePosZ = NightGraveEntity.this.random.range(-1, 1);
									zombiePos = NightGraveEntity.this.random.range(-1, 1);
								}
								if (NightGraveEntity.this.isChallengeGrave()) {
									zombiePosZ = NightGraveEntity.this.random.range(-3, 3);
									zombiePos = NightGraveEntity.this.random.range(-3, 3);
								}
								BlockPos blockPos = NightGraveEntity.this.getBlockPos().add(zombiePos, 0, zombiePosZ);
								NewspaperEntity sundayEdition = (NewspaperEntity) PvZEntity.NEWSPAPER.create(NightGraveEntity.this.getWorld());
								sundayEdition.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
								sundayEdition.initialize(serverWorld, NightGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
								sundayEdition.setOwner(NightGraveEntity.this);
								sundayEdition.defenseMultiplier = NightGraveEntity.this.defenseMultiplier;
								serverWorld.spawnEntityAndPassengers(sundayEdition);
							}
							for (int b = 0; b < 1; ++b) { // 100% x1 Screendoor
								if (!NightGraveEntity.this.is1x1()) {
									zombiePosZ = NightGraveEntity.this.random.range(-1, 1);
									zombiePos = NightGraveEntity.this.random.range(-1, 1);
								}
								if (NightGraveEntity.this.isChallengeGrave()) {
									zombiePosZ = NightGraveEntity.this.random.range(-3, 3);
									zombiePos = NightGraveEntity.this.random.range(-3, 3);
								}
								BlockPos blockPos = NightGraveEntity.this.getBlockPos().add(zombiePos, 0, zombiePosZ);
								BrowncoatEntity browncoatEntity = (BrowncoatEntity) PvZEntity.SCREENDOOR.create(NightGraveEntity.this.getWorld());
								browncoatEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
								browncoatEntity.initialize(serverWorld, NightGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
								browncoatEntity.setOwner(NightGraveEntity.this);
								browncoatEntity.defenseMultiplier = NightGraveEntity.this.defenseMultiplier;
					serverWorld.spawnEntityAndPassengers(browncoatEntity);
							}
							specialGraveWeight += 1.5;
						}
					}
				}
				if (specialGraveWeight <= 3) {
					if (difficulty >= 2.009 + difficultymodifier || isUnlock()) {
						if (probability121 <= 0.1 / halfModifier * survChance) { // 10% x1 Unicorn Gargantuar
							for (int j = 0; j < 1; ++j) {
								if (!NightGraveEntity.this.is1x1()) {
									zombiePosZ = NightGraveEntity.this.random.range(-1, 1);
									zombiePos = NightGraveEntity.this.random.range(-1, 1);
								}
								if (NightGraveEntity.this.isChallengeGrave()) {
									zombiePosZ = NightGraveEntity.this.random.range(-3, 3);
									zombiePos = NightGraveEntity.this.random.range(-3, 3);
								}
								BlockPos blockPos = NightGraveEntity.this.getBlockPos().add(zombiePos, 0, zombiePosZ);
								GargantuarEntity defensiveEnd = (GargantuarEntity) PvZEntity.UNICORNGARGANTUAR.create(NightGraveEntity.this.getWorld());
								defensiveEnd.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
								defensiveEnd.initialize(serverWorld, NightGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
								defensiveEnd.setOwner(NightGraveEntity.this);
								defensiveEnd.defenseMultiplier = NightGraveEntity.this.defenseMultiplier;
								serverWorld.spawnEntityAndPassengers(defensiveEnd);
							}
							for (int p = 0; p < 2 / halfModifier; ++p) { // 100% x2 Rainbow Bass Imp
								if (!NightGraveEntity.this.is1x1()) {
									zombiePosZ = NightGraveEntity.this.random.range(-1, 1);
									zombiePos = NightGraveEntity.this.random.range(-1, 1);
								}
								if (NightGraveEntity.this.isChallengeGrave()) {
									zombiePosZ = NightGraveEntity.this.random.range(-3, 3);
									zombiePos = NightGraveEntity.this.random.range(-3, 3);
								}
								BlockPos blockPos = NightGraveEntity.this.getBlockPos().add(zombiePos, 0, zombiePosZ);
								ImpEntity bassImp = (ImpEntity) PvZEntity.BASSIMP.create(NightGraveEntity.this.getWorld());
								bassImp.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
								bassImp.initialize(serverWorld, NightGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
								bassImp.setOwner(NightGraveEntity.this);
								bassImp.defenseMultiplier = NightGraveEntity.this.defenseMultiplier;
								serverWorld.spawnEntityAndPassengers(bassImp);
							}
							double random = Math.random();
							if (random <= 0.33){
								for (int j = 0; j < 1; ++j) { // 50% x1 Unicorn Gargantuar
									if (!NightGraveEntity.this.is1x1()) {
										zombiePosZ = NightGraveEntity.this.random.range(-1, 1);
										zombiePos = NightGraveEntity.this.random.range(-1, 1);
									}
									if (NightGraveEntity.this.isChallengeGrave()) {
										zombiePosZ = NightGraveEntity.this.random.range(-3, 3);
										zombiePos = NightGraveEntity.this.random.range(-3, 3);
									}
									BlockPos blockPos = NightGraveEntity.this.getBlockPos().add(zombiePos, 0, zombiePosZ);
									GargantuarEntity defensiveEnd = (GargantuarEntity) PvZEntity.UNICORNGARGANTUAR.create(NightGraveEntity.this.getWorld());
									defensiveEnd.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
									defensiveEnd.initialize(serverWorld, NightGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
									defensiveEnd.setOwner(NightGraveEntity.this);
									defensiveEnd.defenseMultiplier = NightGraveEntity.this.defenseMultiplier;
									serverWorld.spawnEntityAndPassengers(defensiveEnd);
								}
							}
							specialGraveWeight += 1.25;
						}
					}
				}
				if (specialGraveWeight <= 3) {
					if ((difficulty >= 2.009 + difficultymodifier) && (isUnlock() || isUnlockSpecial())) {
						if (probability5 <= 0.15 / halfModifier * survChance) { // 15% x1 Berserker
							for (int p = 0; p < 1; ++p) {
								if (!NightGraveEntity.this.is1x1()) {
									zombiePosZ = NightGraveEntity.this.random.range(-1, 1);
									zombiePos = NightGraveEntity.this.random.range(-1, 1);
								}
								if (NightGraveEntity.this.isChallengeGrave()) {
									zombiePosZ = NightGraveEntity.this.random.range(-3, 3);
									zombiePos = NightGraveEntity.this.random.range(-3, 3);
								}
								BlockPos blockPos = NightGraveEntity.this.getBlockPos().add(zombiePos, 0, zombiePosZ);
								FootballEntity football = (FootballEntity) PvZEntity.BERSERKER.create(NightGraveEntity.this.getWorld());
								football.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
								football.initialize(serverWorld, NightGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
								football.setOwner(NightGraveEntity.this);
								football.defenseMultiplier = NightGraveEntity.this.defenseMultiplier;
								serverWorld.spawnEntityAndPassengers(football);
							}
							for (int b = 0; b < 1; ++b) { // 100% x1 Conehead
								if (!NightGraveEntity.this.is1x1()) {
									zombiePosZ = NightGraveEntity.this.random.range(-1, 1);
									zombiePos = NightGraveEntity.this.random.range(-1, 1);
								}
								if (NightGraveEntity.this.isChallengeGrave()) {
									zombiePosZ = NightGraveEntity.this.random.range(-3, 3);
									zombiePos = NightGraveEntity.this.random.range(-3, 3);
								}
								BlockPos blockPos = NightGraveEntity.this.getBlockPos().add(zombiePos, 0, zombiePosZ);
								BrowncoatEntity browncoatEntity = (BrowncoatEntity) PvZEntity.SUMMERCONEHEAD.create(NightGraveEntity.this.getWorld());
								browncoatEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
								browncoatEntity.initialize(serverWorld, NightGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
								browncoatEntity.setOwner(NightGraveEntity.this);
								browncoatEntity.defenseMultiplier = NightGraveEntity.this.defenseMultiplier;
					serverWorld.spawnEntityAndPassengers(browncoatEntity);
							}
							for (int b = 0; b < 1; ++b) { // 100% x1 Buckethead
								if (!NightGraveEntity.this.is1x1()) {
									zombiePosZ = NightGraveEntity.this.random.range(-1, 1);
									zombiePos = NightGraveEntity.this.random.range(-1, 1);
								}
								if (NightGraveEntity.this.isChallengeGrave()) {
									zombiePosZ = NightGraveEntity.this.random.range(-3, 3);
									zombiePos = NightGraveEntity.this.random.range(-3, 3);
								}
								BlockPos blockPos = NightGraveEntity.this.getBlockPos().add(zombiePos, 0, zombiePosZ);
								BrowncoatEntity browncoatEntity = (BrowncoatEntity) PvZEntity.SUMMERBUCKETHEAD.create(NightGraveEntity.this.getWorld());
								browncoatEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
								browncoatEntity.initialize(serverWorld, NightGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
								browncoatEntity.setOwner(NightGraveEntity.this);
								browncoatEntity.defenseMultiplier = NightGraveEntity.this.defenseMultiplier;
					serverWorld.spawnEntityAndPassengers(browncoatEntity);
							}
							specialGraveWeight += 1.75;
						}
					}
				}
			}
			++this.nightGraveEntity.spawnCounter;
        }

        protected SoundEvent getSoundPrepare() {
            return PvZSounds.GRAVERISINGEVENT;
        }

        protected Spell getSpell() {
            return Spell.SUMMON_VEX;
        }
    }
}
