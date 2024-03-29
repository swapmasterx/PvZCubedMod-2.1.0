package io.github.GrassyDev.pvzmod.registry.entity.gravestones.roofgrave;

import com.jamieswhiteshirt.reachentityattributes.ReachEntityAttributes;
import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.PvZSounds;
import io.github.GrassyDev.pvzmod.registry.entity.gravestones.GraveEntity;
import io.github.GrassyDev.pvzmod.registry.entity.variants.graves.GraveDifficulty;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.oc.bully.basketballcarrier.BasketballCarrierEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz1.browncoat.modernday.BrowncoatEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz1.flagzombie.modernday.FlagzombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz1.gargantuar.modernday.GargantuarEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz1.imp.modernday.ImpEntity;
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
import net.minecraft.world.Difficulty;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;


import java.util.Objects;

import static io.github.GrassyDev.pvzmod.PvZCubed.PVZCONFIG;

public class RoofGraveEntity extends GraveEntity implements IAnimatable {

	private String controllerName = "walkingcontroller";

	private int spawnCounter;



	double tiltchance = this.random.nextDouble();

    private AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public RoofGraveEntity(EntityType<RoofGraveEntity> entityType, World world) {
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
	public void registerControllers(AnimationData data) {
		AnimationController controller = new AnimationController(this, controllerName, 0, this::predicate);

		data.addAnimationController(controller);
	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}

	private <P extends IAnimatable> PlayState predicate(AnimationEvent<P> event) {
		if (beingEaten){
			event.getController().setAnimation(new AnimationBuilder().loop("obstacle.eating"));
		}
		else if (tiltchance <= 0.5) {
			event.getController().setAnimation(new AnimationBuilder().loop("gravestone.idle"));
		}
		else {
			event.getController().setAnimation(new AnimationBuilder().loop("gravestone.idle2"));
		}
        return PlayState.CONTINUE;
    }


	/** /~*~//~*AI*~//~*~/ **/

	protected void initGoals() {
		this.targetSelector.add(1, new RevengeGoal(this, new Class[0]));
        this.initCustomGoals();
    }

    protected void initCustomGoals() {
        this.goalSelector.add(1, new RoofGraveEntity.summonZombieGoal(this));
    }


	/** /~*~//~*TICKING*~//~*~/ **/

	public void tick() {
		super.tick();
		if (this.getTarget() == null) {
			this.setTarget(this.getWorld().getClosestPlayer(this.getX(), this.getY(), this.getZ(), 100, true));
		}
		LocalDifficulty localDifficulty = world.getLocalDifficulty(this.getBlockPos());
		double difficulty = 0;
		if (this.getVariant().equals(GraveDifficulty.NONE)){
			difficulty = localDifficulty.getLocalDifficulty();
				if (difficulty >= 2.1){
					difficulty = 2.1;
					if (world.getDifficulty().equals(Difficulty.HARD)){
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
		if (this.spawnCounter == 1 && world.getTime() < 24000) {
			this.kill();
		}
		if (this.spawnCounter == 1 && world.getTime() < 24000) {
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
			float g = this.bodyYaw * 0.017453292F + MathHelper.cos((float)this.age * 0.6662F) * 0.25F;
			float h = MathHelper.cos(g);
			float i = MathHelper.sin(g);
			this.getWorld().addParticle(ParticleTypes.SMOKE, this.getX() + (double)h * 0.6, this.getY(), this.getZ() + (double)i * 0.6, 0, 0.0125, 0);
			this.getWorld().addParticle(ParticleTypes.SMOKE, this.getX() - (double)h * 0.6, this.getY(), this.getZ() - (double)i * 0.6, 0, 0.0125, 0);
		}
	}


	/** /~*~//~*ATTRIBUTES*~//~*~/ **/

	public static DefaultAttributeContainer.Builder createRoofGraveAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 100.0D)
				.add(ReachEntityAttributes.ATTACK_RANGE, 1.5D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, PVZCONFIG.nestedZombieHealth.poolGraveH());
    }

	protected SoundEvent getDeathSound() {
		return SoundEvents.BLOCK_ANCIENT_DEBRIS_BREAK;
	}

	protected SoundEvent getHurtSound(DamageSource source) {
		return SoundEvents.BLOCK_BASALT_HIT;
	}






	/** /~*~//~*SPAWNING*~//~*~/ **/

	public static boolean canRoofGraveSpawn(EntityType<? extends RoofGraveEntity> type, ServerWorldAccess world, SpawnReason spawnReason, BlockPos pos, RandomGenerator random) {
		BlockPos blockPos = pos.down();
		float cavespawn = random.nextFloat();
		if (cavespawn <= 0.66) {
			return world.getDifficulty() != Difficulty.PEACEFUL &&
					!world.getBlockState(pos).getMaterial().isLiquid() &&
					world.toServerWorld().getTime() > 48000 &&
					pos.getY() > 50 &&
					world.getLocalDifficulty(pos).getLocalDifficulty() >= 1.8 &&
					!world.getBlockState(blockPos).getBlock().hasDynamicBounds() &&
					!checkVillager(Vec3d.ofCenter(pos), world) &&
					!checkPlant(Vec3d.ofCenter(pos), world) && Objects.requireNonNull(world.getServer()).getGameRules().getBoolean(PvZCubed.SHOULD_GRAVE_SPAWN);
		}
		else {
			return world.getDifficulty() != Difficulty.PEACEFUL &&
					!world.getBlockState(pos).getMaterial().isLiquid() &&
					world.toServerWorld().getTime() > 42000 &&
					world.getLocalDifficulty(pos).getLocalDifficulty() >= 1.8 &&
					!world.getBlockState(blockPos).getBlock().hasDynamicBounds() &&
					!checkVillager(Vec3d.ofCenter(pos), world) &&
					!checkPlant(Vec3d.ofCenter(pos), world) && Objects.requireNonNull(world.getServer()).getGameRules().getBoolean(PvZCubed.SHOULD_GRAVE_SPAWN);
		}
	}


	/** /~*~//~*GOALS*~//~*~/ **/

	protected abstract class CastSpellGoal extends Goal {
		protected int spellCooldown;
		protected int startTime;

		protected CastSpellGoal() {
		}

		public boolean canStart() {
			LivingEntity livingEntity = RoofGraveEntity.this.getTarget();
			if (livingEntity != null && livingEntity.isAlive()) {
				if (RoofGraveEntity.this.isSpellcasting()) {
					return false;
				} else {
					return RoofGraveEntity.this.age >= this.startTime;
				}
			} else {
				return false;
			}
		}

		public boolean shouldContinue() {
			LivingEntity livingEntity = RoofGraveEntity.this.getTarget();
			return livingEntity != null && livingEntity.isAlive() && this.spellCooldown > 0;
		}

		public void start() {
			this.spellCooldown = this.getTickCount(this.getInitialCooldown());
			RoofGraveEntity.this.spellTicks = this.getSpellTicks();
			this.startTime = RoofGraveEntity.this.age + this.startTimeDelay();
			SoundEvent soundEvent = this.getSoundPrepare();
			if (soundEvent != null) {
				RoofGraveEntity.this.playSound(soundEvent, 1.0F, 1.0F);
			}

			RoofGraveEntity.this.setSpell(this.getSpell());
		}

		public void tick() {
			--this.spellCooldown;
			if (this.spellCooldown == 0) {
				this.castSpell();
				RoofGraveEntity.this.addStatusEffect((new StatusEffectInstance(StatusEffects.GLOWING, 70, 1)));
				RoofGraveEntity.this.playSound(RoofGraveEntity.this.getCastSpellSound(), 1.0F, 1.0F);
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
		return ModItems.ROOFGRAVESPAWN.getDefaultStack();
	}

	class summonZombieGoal extends RoofGraveEntity.CastSpellGoal {
        private final TargetPredicate closeZombiePredicate;

		private final RoofGraveEntity roofGraveEntity;

		private summonZombieGoal(RoofGraveEntity roofGraveEntity) {
            super();
			this.roofGraveEntity = roofGraveEntity;
			this.closeZombiePredicate = (TargetPredicate.createNonAttackable().setBaseMaxDistance(16.0D).ignoreVisibility().ignoreDistanceScalingFactor());
		}

        public boolean canStart() {
            return super.canStart();
        }

        protected int getSpellTicks() {
            return 100;
        }

        protected int startTimeDelay() {
            return 600;
        }

        protected void castSpell() {
			ServerWorld serverWorld = (ServerWorld) RoofGraveEntity.this.getWorld();
			LocalDifficulty localDifficulty = world.getLocalDifficulty(this.roofGraveEntity.getBlockPos());
			double difficulty = 0;
			if (this.roofGraveEntity.getVariant().equals(GraveDifficulty.NONE)) {
				difficulty = localDifficulty.getLocalDifficulty();
				if (difficulty >= 2.1) {
					difficulty = 2.1;
					if (world.getDifficulty().equals(Difficulty.HARD)) {
						difficulty = difficulty + difficultymodifier;
					}
				}
			} else if (this.roofGraveEntity.getVariant().equals(GraveDifficulty.EASY)) {
				difficulty = 1.0;
			} else if (this.roofGraveEntity.getVariant().equals(GraveDifficulty.EASYMED)) {
				difficulty = 1.5;
			} else if (this.roofGraveEntity.getVariant().equals(GraveDifficulty.MED)) {
				difficulty = 1.6;
			} else if (this.roofGraveEntity.getVariant().equals(GraveDifficulty.MEDHARD)) {
				difficulty = 1.8;
			} else if (this.roofGraveEntity.getVariant().equals(GraveDifficulty.HARD)) {
				difficulty = 2.1;
			} else if (this.roofGraveEntity.getVariant().equals(GraveDifficulty.SUPERHARD)) {
				difficulty = 3;
			} else if (this.roofGraveEntity.getVariant().equals(GraveDifficulty.NIGHTMARE)) {
				difficulty = 4;
			} else if (this.roofGraveEntity.getVariant().equals(GraveDifficulty.CRAAAZY)) {
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

			int zombiePos = -2 + RoofGraveEntity.this.random.nextInt(5);
			int zombiePosZ = -2 + RoofGraveEntity.this.random.nextInt(5);
			if (RoofGraveEntity.this.is1x1()) {
				zombiePos = 0;
				zombiePosZ = 0;
			}

			for(int b = 0; b < 1; ++b) { // 100% x1 Browncoat
				if (!RoofGraveEntity.this.is1x1()) {
					zombiePosZ = RoofGraveEntity.this.random.range(-1, 1);
					zombiePos = RoofGraveEntity.this.random.range(-1, 1);
				}
				if (RoofGraveEntity.this.isChallengeGrave()) {
					zombiePosZ = RoofGraveEntity.this.random.range(-3, 3);
					zombiePos = RoofGraveEntity.this.random.range(-3, 3);
				}
				BlockPos blockPos = RoofGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
				BrowncoatEntity browncoatEntity = (BrowncoatEntity)PvZEntity.BROWNCOAT.create(RoofGraveEntity.this.getWorld());
				browncoatEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
				browncoatEntity.initialize(serverWorld, RoofGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData)null, (NbtCompound)null);
				browncoatEntity.setOwner(RoofGraveEntity.this);
				browncoatEntity.defenseMultiplier = RoofGraveEntity.this.defenseMultiplier;
					serverWorld.spawnEntityAndPassengers(browncoatEntity);
			}
			if (probability <= 0.25 / halfModifier * survChance) { // 25% x1 Conehead
				for (int c = 0; c < 1; ++c) {
					if (!RoofGraveEntity.this.is1x1()) {
						zombiePosZ = RoofGraveEntity.this.random.range(-1, 1);
						zombiePos = RoofGraveEntity.this.random.range(-1, 1);
					}
					if (RoofGraveEntity.this.isChallengeGrave()) {
						zombiePosZ = RoofGraveEntity.this.random.range(-3, 3);
						zombiePos = RoofGraveEntity.this.random.range(-3, 3);
					}
					BlockPos blockPos = RoofGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
					BrowncoatEntity coneheadEntity = (BrowncoatEntity) PvZEntity.CONEHEAD.create(RoofGraveEntity.this.getWorld());
					coneheadEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
					coneheadEntity.initialize(serverWorld, RoofGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
					coneheadEntity.setOwner(RoofGraveEntity.this);
					coneheadEntity.defenseMultiplier = RoofGraveEntity.this.defenseMultiplier;
					serverWorld.spawnEntityAndPassengers(coneheadEntity);
				}
				for(int b = 0; b < 1; ++b) { // 100% x1 Browncoat
					if (!RoofGraveEntity.this.is1x1()) {
						zombiePosZ = RoofGraveEntity.this.random.range(-1, 1);
						zombiePos = RoofGraveEntity.this.random.range(-1, 1);
					}
					if (RoofGraveEntity.this.isChallengeGrave()) {
						zombiePosZ = RoofGraveEntity.this.random.range(-3, 3);
						zombiePos = RoofGraveEntity.this.random.range(-3, 3);
					}
					BlockPos blockPos = RoofGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
					BrowncoatEntity browncoatEntity = (BrowncoatEntity)PvZEntity.BROWNCOAT.create(RoofGraveEntity.this.getWorld());
					browncoatEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
					browncoatEntity.initialize(serverWorld, RoofGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData)null, (NbtCompound)null);
					browncoatEntity.setOwner(RoofGraveEntity.this);
					browncoatEntity.defenseMultiplier = RoofGraveEntity.this.defenseMultiplier;
					serverWorld.spawnEntityAndPassengers(browncoatEntity);
				}
			}
			if (serverWorld.toServerWorld().getTime() > 24000) {
				if (probability2 <= 0.05 / halfModifier * survChance) { // 5% x1 Buckethead
					for (int u = 0; u < 1; ++u) {
						if (!RoofGraveEntity.this.is1x1()) {
							zombiePosZ = RoofGraveEntity.this.random.range(-1, 1);
							zombiePos = RoofGraveEntity.this.random.range(-1, 1);
						}
						if (RoofGraveEntity.this.isChallengeGrave()) {
							zombiePosZ = RoofGraveEntity.this.random.range(-3, 3);
							zombiePos = RoofGraveEntity.this.random.range(-3, 3);
						}
						BlockPos blockPos = RoofGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
						BrowncoatEntity bucketheadEntity = (BrowncoatEntity) PvZEntity.BUCKETHEAD.create(RoofGraveEntity.this.getWorld());
						bucketheadEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
						bucketheadEntity.initialize(serverWorld, RoofGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
						bucketheadEntity.setOwner(RoofGraveEntity.this);
						bucketheadEntity.defenseMultiplier = RoofGraveEntity.this.defenseMultiplier;
						serverWorld.spawnEntityAndPassengers(bucketheadEntity);
					}
					for (int c = 0; c < 2 / halfModifier; ++c) { // 100% x2 Conehead
						if (!RoofGraveEntity.this.is1x1()) {
							zombiePosZ = RoofGraveEntity.this.random.range(-1, 1);
							zombiePos = RoofGraveEntity.this.random.range(-1, 1);
						}
						if (RoofGraveEntity.this.isChallengeGrave()) {
							zombiePosZ = RoofGraveEntity.this.random.range(-3, 3);
							zombiePos = RoofGraveEntity.this.random.range(-3, 3);
						}
						BlockPos blockPos = RoofGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
						BrowncoatEntity coneheadEntity = (BrowncoatEntity) PvZEntity.CONEHEAD.create(RoofGraveEntity.this.getWorld());
						coneheadEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
						coneheadEntity.initialize(serverWorld, RoofGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
						coneheadEntity.setOwner(RoofGraveEntity.this);
						coneheadEntity.defenseMultiplier = RoofGraveEntity.this.defenseMultiplier;
					serverWorld.spawnEntityAndPassengers(coneheadEntity);
					}
				}
				if (difficulty >= 1.519 + difficultymodifier || isUnlock()) {
					if (probability11 <= 0.1 / halfModifier * survChance) { // 10% x2 Conehead
						for (int c = 0; c < 2 / halfModifier; ++c) {
							if (!RoofGraveEntity.this.is1x1()) {
								zombiePosZ = RoofGraveEntity.this.random.range(-1, 1);
								zombiePos = RoofGraveEntity.this.random.range(-1, 1);
							}
							if (RoofGraveEntity.this.isChallengeGrave()) {
								zombiePosZ = RoofGraveEntity.this.random.range(-3, 3);
								zombiePos = RoofGraveEntity.this.random.range(-3, 3);
							}
							BlockPos blockPos = RoofGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							BrowncoatEntity coneheadEntity = (BrowncoatEntity) PvZEntity.CONEHEAD.create(RoofGraveEntity.this.getWorld());
							coneheadEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
							coneheadEntity.initialize(serverWorld, RoofGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							coneheadEntity.setOwner(RoofGraveEntity.this);
							coneheadEntity.defenseMultiplier = RoofGraveEntity.this.defenseMultiplier;
					serverWorld.spawnEntityAndPassengers(coneheadEntity);
						}
						for(int b = 0; b < 2 / halfModifier; ++b) { // 100% x2 Browncoat
							if (!RoofGraveEntity.this.is1x1()) {
								zombiePosZ = RoofGraveEntity.this.random.range(-1, 1);
								zombiePos = RoofGraveEntity.this.random.range(-1, 1);
							}
							if (RoofGraveEntity.this.isChallengeGrave()) {
								zombiePosZ = RoofGraveEntity.this.random.range(-3, 3);
								zombiePos = RoofGraveEntity.this.random.range(-3, 3);
							}
							BlockPos blockPos = RoofGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							BrowncoatEntity browncoatEntity = (BrowncoatEntity)PvZEntity.BROWNCOAT.create(RoofGraveEntity.this.getWorld());
							browncoatEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
							browncoatEntity.initialize(serverWorld, RoofGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData)null, (NbtCompound)null);
							browncoatEntity.setOwner(RoofGraveEntity.this);
							browncoatEntity.defenseMultiplier = RoofGraveEntity.this.defenseMultiplier;
					serverWorld.spawnEntityAndPassengers(browncoatEntity);
						}
					}
				}
			}
			if (difficulty >= 1.599 + difficultymodifier || isUnlock()) {
				if (probability22 <= 0.15 / halfModifier * survChance) { // 15% x1 Buckethead
					for (int u = 0; u < 2 / halfModifier; ++u) {
						if (!RoofGraveEntity.this.is1x1()) {
							zombiePosZ = RoofGraveEntity.this.random.range(-1, 1);
							zombiePos = RoofGraveEntity.this.random.range(-1, 1);
						}
						if (RoofGraveEntity.this.isChallengeGrave()) {
							zombiePosZ = RoofGraveEntity.this.random.range(-3, 3);
							zombiePos = RoofGraveEntity.this.random.range(-3, 3);
						}
						BlockPos blockPos = RoofGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
						BrowncoatEntity bucketheadEntity = (BrowncoatEntity) PvZEntity.BUCKETHEAD.create(RoofGraveEntity.this.getWorld());
						bucketheadEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
						bucketheadEntity.initialize(serverWorld, RoofGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
						bucketheadEntity.setOwner(RoofGraveEntity.this);
						bucketheadEntity.defenseMultiplier = RoofGraveEntity.this.defenseMultiplier;
						serverWorld.spawnEntityAndPassengers(bucketheadEntity);
					}
					for (int b = 0; b < 1; ++b) { // 100% x1 Browncoat
						if (!RoofGraveEntity.this.is1x1()) {
							zombiePosZ = RoofGraveEntity.this.random.range(-1, 1);
							zombiePos = RoofGraveEntity.this.random.range(-1, 1);
						}
						if (RoofGraveEntity.this.isChallengeGrave()) {
							zombiePosZ = RoofGraveEntity.this.random.range(-3, 3);
							zombiePos = RoofGraveEntity.this.random.range(-3, 3);
						}
						BlockPos blockPos = RoofGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
						BrowncoatEntity browncoatEntity = (BrowncoatEntity) PvZEntity.BROWNCOAT.create(RoofGraveEntity.this.getWorld());
						browncoatEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
						browncoatEntity.initialize(serverWorld, RoofGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
						browncoatEntity.setOwner(RoofGraveEntity.this);
						browncoatEntity.defenseMultiplier = RoofGraveEntity.this.defenseMultiplier;
					serverWorld.spawnEntityAndPassengers(browncoatEntity);
					}
				}
			}
			if (serverWorld.toServerWorld().getTime() > 24000) {
				if (difficulty >= 1.599 + difficultymodifier || isUnlock()) {
					if (probability7 <= 0.1 / halfModifier * survChance) { // 15% x2 Buckethead
						for (int u = 0; u < 2 / halfModifier; ++u) {
							if (!RoofGraveEntity.this.is1x1()) {
								zombiePosZ = RoofGraveEntity.this.random.range(-1, 1);
								zombiePos = RoofGraveEntity.this.random.range(-1, 1);
							}
							if (RoofGraveEntity.this.isChallengeGrave()) {
								zombiePosZ = RoofGraveEntity.this.random.range(-3, 3);
								zombiePos = RoofGraveEntity.this.random.range(-3, 3);
							}
							BlockPos blockPos = RoofGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							BrowncoatEntity bucketheadEntity = (BrowncoatEntity) PvZEntity.BUCKETHEAD.create(RoofGraveEntity.this.getWorld());
							bucketheadEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
							bucketheadEntity.initialize(serverWorld, RoofGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							bucketheadEntity.setOwner(RoofGraveEntity.this);
							bucketheadEntity.defenseMultiplier = RoofGraveEntity.this.defenseMultiplier;
						serverWorld.spawnEntityAndPassengers(bucketheadEntity);
						}
						for (int c = 0; c < 2 / halfModifier; ++c) { // 100% x2 Conehead
							if (!RoofGraveEntity.this.is1x1()) {
								zombiePosZ = RoofGraveEntity.this.random.range(-1, 1);
								zombiePos = RoofGraveEntity.this.random.range(-1, 1);
							}
							if (RoofGraveEntity.this.isChallengeGrave()) {
								zombiePosZ = RoofGraveEntity.this.random.range(-3, 3);
								zombiePos = RoofGraveEntity.this.random.range(-3, 3);
							}
							BlockPos blockPos = RoofGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							BrowncoatEntity coneheadEntity = (BrowncoatEntity) PvZEntity.CONEHEAD.create(RoofGraveEntity.this.getWorld());
							coneheadEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
							coneheadEntity.initialize(serverWorld, RoofGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							coneheadEntity.setOwner(RoofGraveEntity.this);
							coneheadEntity.defenseMultiplier = RoofGraveEntity.this.defenseMultiplier;
					serverWorld.spawnEntityAndPassengers(coneheadEntity);
						}
					}
				}
				if (difficulty >= 1.599 + difficultymodifier || isUnlock()) {
					if (probability10 <= 0.3 / halfModifier * survChance) { // 5% x3 Buckethead Zombie
						for (int u = 0; u < 2 / halfModifier; ++u) {
							if (!RoofGraveEntity.this.is1x1()) {
								zombiePosZ = RoofGraveEntity.this.random.range(-1, 1);
								zombiePos = RoofGraveEntity.this.random.range(-1, 1);
							}
							if (RoofGraveEntity.this.isChallengeGrave()) {
								zombiePosZ = RoofGraveEntity.this.random.range(-3, 3);
								zombiePos = RoofGraveEntity.this.random.range(-3, 3);
							}
							BlockPos blockPos = RoofGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							BrowncoatEntity bucketheadEntity = (BrowncoatEntity) PvZEntity.BUCKETHEAD.create(RoofGraveEntity.this.getWorld());
							bucketheadEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
							bucketheadEntity.initialize(serverWorld, RoofGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							bucketheadEntity.setOwner(RoofGraveEntity.this);
							bucketheadEntity.defenseMultiplier = RoofGraveEntity.this.defenseMultiplier;
						serverWorld.spawnEntityAndPassengers(bucketheadEntity);
						}
						for (int c = 0; c < 2 / halfModifier; ++c) { // 100% x2 Conehead
							if (!RoofGraveEntity.this.is1x1()) {
								zombiePosZ = RoofGraveEntity.this.random.range(-1, 1);
								zombiePos = RoofGraveEntity.this.random.range(-1, 1);
							}
							if (RoofGraveEntity.this.isChallengeGrave()) {
								zombiePosZ = RoofGraveEntity.this.random.range(-3, 3);
								zombiePos = RoofGraveEntity.this.random.range(-3, 3);
							}
							BlockPos blockPos = RoofGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							BrowncoatEntity coneheadEntity = (BrowncoatEntity) PvZEntity.CONEHEAD.create(RoofGraveEntity.this.getWorld());
							coneheadEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
							coneheadEntity.initialize(serverWorld, RoofGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							coneheadEntity.setOwner(RoofGraveEntity.this);
							coneheadEntity.defenseMultiplier = RoofGraveEntity.this.defenseMultiplier;
					serverWorld.spawnEntityAndPassengers(coneheadEntity);
						}
					}
				}
			}
			if (serverWorld.toServerWorld().getTime() > 24000) {
				if (extraGraveWeight <= 2.5) {
					if (difficulty >= 1.599 + difficultymodifier || isUnlock() || isUnlockSpecial()) {
						if (probability4 <= 0.1 / halfModifier * survChance) { // 10% x1 Flag Zombie
							for (int f = 0; f < 1; ++f) {
								if (!RoofGraveEntity.this.is1x1()) {
									zombiePosZ = RoofGraveEntity.this.random.range(-1, 1);
									zombiePos = RoofGraveEntity.this.random.range(-1, 1);
								}
								if (RoofGraveEntity.this.isChallengeGrave()) {
									zombiePosZ = RoofGraveEntity.this.random.range(-3, 3);
									zombiePos = RoofGraveEntity.this.random.range(-3, 3);
								}
								double random = Math.random();
								EntityType<?> flagType;
								if (random <= 0.125) {
									flagType = PvZEntity.FLAGZOMBIE_G;
								} else if (random <= 0.25) {
									flagType = PvZEntity.FLAGZOMBIE_T;
								} else {
									flagType = PvZEntity.FLAGZOMBIE;
								}
								BlockPos blockPos = RoofGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
								FlagzombieEntity flagzombieEntity = (FlagzombieEntity) flagType.create(RoofGraveEntity.this.getWorld());
								flagzombieEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
								flagzombieEntity.initialize(serverWorld, RoofGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
								flagzombieEntity.setOwner(RoofGraveEntity.this);
								flagzombieEntity.defenseMultiplier = RoofGraveEntity.this.defenseMultiplier;
								serverWorld.spawnEntityAndPassengers(flagzombieEntity);
							}
							extraGraveWeight += 1;
						}
					}
				}
				if (extraGraveWeight <= 2.5) {
					if ((difficulty >= 2.009 + difficultymodifier) && (isUnlock() || isUnlockSpecial())) {
						if (probability8 <= 0.15 / halfModifier * survChance) { // 15% x2 Basketball Carrier
							for (int g = 0; g < 2 / halfModifier; ++g) {
								if (!RoofGraveEntity.this.is1x1()) {
									zombiePosZ = RoofGraveEntity.this.random.range(-1, 1);
									zombiePos = RoofGraveEntity.this.random.range(-1, 1);
								}
								if (RoofGraveEntity.this.isChallengeGrave()) {
									zombiePosZ = RoofGraveEntity.this.random.range(-3, 3);
									zombiePos = RoofGraveEntity.this.random.range(-3, 3);
								}
								BlockPos blockPos = RoofGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
								BasketballCarrierEntity basketball = (BasketballCarrierEntity) PvZEntity.BASKETBALLCARRIER.create(RoofGraveEntity.this.getWorld());
								basketball.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
								basketball.initialize(serverWorld, RoofGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
								basketball.setOwner(RoofGraveEntity.this);
								basketball.defenseMultiplier = RoofGraveEntity.this.defenseMultiplier;
								serverWorld.spawnEntityAndPassengers(basketball);
							}
							for (int c = 0; c < 2 / halfModifier; ++c) { // 100% x2 Conehead
								if (!RoofGraveEntity.this.is1x1()) {
									zombiePosZ = RoofGraveEntity.this.random.range(-1, 1);
									zombiePos = RoofGraveEntity.this.random.range(-1, 1);
								}
								if (RoofGraveEntity.this.isChallengeGrave()) {
									zombiePosZ = RoofGraveEntity.this.random.range(-3, 3);
									zombiePos = RoofGraveEntity.this.random.range(-3, 3);
								}
								BlockPos blockPos = RoofGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
								BrowncoatEntity coneheadEntity = (BrowncoatEntity) PvZEntity.CONEHEAD.create(RoofGraveEntity.this.getWorld());
								coneheadEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
								coneheadEntity.initialize(serverWorld, RoofGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
								coneheadEntity.setOwner(RoofGraveEntity.this);
								coneheadEntity.defenseMultiplier = RoofGraveEntity.this.defenseMultiplier;
					serverWorld.spawnEntityAndPassengers(coneheadEntity);
							}
							extraGraveWeight += 1.25;
						}
					}
				}
				if (extraGraveWeight <= 2.5) {
					if (difficulty >= 1.549 + difficultymodifier || isUnlock()) {
						if (probability3 <= 0.15 / halfModifier * survChance) { // 15% x2 Imp
							for (int p = 0; p < 2 / halfModifier; ++p) {
								if (!RoofGraveEntity.this.is1x1()) {
									zombiePosZ = RoofGraveEntity.this.random.range(-1, 1);
									zombiePos = RoofGraveEntity.this.random.range(-1, 1);
								}
								if (RoofGraveEntity.this.isChallengeGrave()) {
									zombiePosZ = RoofGraveEntity.this.random.range(-3, 3);
									zombiePos = RoofGraveEntity.this.random.range(-3, 3);
								}
								BlockPos blockPos = RoofGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
								ImpEntity imp = (ImpEntity) PvZEntity.IMP.create(RoofGraveEntity.this.getWorld());
								imp.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
								imp.initialize(serverWorld, RoofGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
								imp.setOwner(RoofGraveEntity.this);
								imp.defenseMultiplier = RoofGraveEntity.this.defenseMultiplier;
								serverWorld.spawnEntityAndPassengers(imp);
							}
							for (int b = 0; b < 2 / halfModifier; ++b) { // 100% x2 Conehead
								if (!RoofGraveEntity.this.is1x1()) {
									zombiePosZ = RoofGraveEntity.this.random.range(-1, 1);
									zombiePos = RoofGraveEntity.this.random.range(-1, 1);
								}
								if (RoofGraveEntity.this.isChallengeGrave()) {
									zombiePosZ = RoofGraveEntity.this.random.range(-3, 3);
									zombiePos = RoofGraveEntity.this.random.range(-3, 3);
								}
								BlockPos blockPos = RoofGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
								BrowncoatEntity browncoatEntity = (BrowncoatEntity) PvZEntity.CONEHEAD.create(RoofGraveEntity.this.getWorld());
								browncoatEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
								browncoatEntity.initialize(serverWorld, RoofGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
								browncoatEntity.setOwner(RoofGraveEntity.this);
								browncoatEntity.defenseMultiplier = RoofGraveEntity.this.defenseMultiplier;
					serverWorld.spawnEntityAndPassengers(browncoatEntity);
							}
							extraGraveWeight += 0.75;
						}
					}
				}
				if (extraGraveWeight <= 2.5) {
					if (difficulty >= 1.599 + difficultymodifier || isUnlock()) {
						if (probability9 <= 0.2 / halfModifier * survChance) { // 20% x1 Basketball Carrier
							for (int g = 0; g < 1; ++g) {
								if (!RoofGraveEntity.this.is1x1()) {
									zombiePosZ = RoofGraveEntity.this.random.range(-1, 1);
									zombiePos = RoofGraveEntity.this.random.range(-1, 1);
								}
								if (RoofGraveEntity.this.isChallengeGrave()) {
									zombiePosZ = RoofGraveEntity.this.random.range(-3, 3);
									zombiePos = RoofGraveEntity.this.random.range(-3, 3);
								}
								BlockPos blockPos = RoofGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
								BasketballCarrierEntity basketball = (BasketballCarrierEntity) PvZEntity.BASKETBALLCARRIER.create(RoofGraveEntity.this.getWorld());
								basketball.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
								basketball.initialize(serverWorld, RoofGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
								basketball.setOwner(RoofGraveEntity.this);
								basketball.defenseMultiplier = RoofGraveEntity.this.defenseMultiplier;
								serverWorld.spawnEntityAndPassengers(basketball);
							}
							for (int c = 0; c < 2 / halfModifier; ++c) { // 100% x2 Browncoat
								if (!RoofGraveEntity.this.is1x1()) {
									zombiePosZ = RoofGraveEntity.this.random.range(-1, 1);
									zombiePos = RoofGraveEntity.this.random.range(-1, 1);
								}
								if (RoofGraveEntity.this.isChallengeGrave()) {
									zombiePosZ = RoofGraveEntity.this.random.range(-3, 3);
									zombiePos = RoofGraveEntity.this.random.range(-3, 3);
								}
								BlockPos blockPos = RoofGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
								BrowncoatEntity coneheadEntity = (BrowncoatEntity) PvZEntity.BROWNCOAT.create(RoofGraveEntity.this.getWorld());
								coneheadEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
								coneheadEntity.initialize(serverWorld, RoofGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
								coneheadEntity.setOwner(RoofGraveEntity.this);
								coneheadEntity.defenseMultiplier = RoofGraveEntity.this.defenseMultiplier;
					serverWorld.spawnEntityAndPassengers(coneheadEntity);
							}
							extraGraveWeight += 0.75;
						}
					}
				}
				if (specialGraveWeight <= 3) {
					if (difficulty >= 2.009 + difficultymodifier || isUnlock()) {
						if (probability6 <= 0.15 / halfModifier * survChance) { // 15% x1 Gargantuar
							for (int j = 0; j < 1; ++j) {
								if (!RoofGraveEntity.this.is1x1()) {
									zombiePosZ = RoofGraveEntity.this.random.range(-1, 1);
									zombiePos = RoofGraveEntity.this.random.range(-1, 1);
								}
								if (RoofGraveEntity.this.isChallengeGrave()) {
									zombiePosZ = RoofGraveEntity.this.random.range(-3, 3);
									zombiePos = RoofGraveEntity.this.random.range(-3, 3);
								}
								BlockPos blockPos = RoofGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
								GargantuarEntity gargantuar = (GargantuarEntity) PvZEntity.GARGANTUAR.create(RoofGraveEntity.this.getWorld());
								gargantuar.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
								gargantuar.initialize(serverWorld, RoofGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
								gargantuar.setOwner(RoofGraveEntity.this);
								gargantuar.defenseMultiplier = RoofGraveEntity.this.defenseMultiplier;
								serverWorld.spawnEntityAndPassengers(gargantuar);
							}
							for (int p = 0; p < 2 / halfModifier; ++p) { // 100% x2 Imp
								if (!RoofGraveEntity.this.is1x1()) {
									zombiePosZ = RoofGraveEntity.this.random.range(-1, 1);
									zombiePos = RoofGraveEntity.this.random.range(-1, 1);
								}
								if (RoofGraveEntity.this.isChallengeGrave()) {
									zombiePosZ = RoofGraveEntity.this.random.range(-3, 3);
									zombiePos = RoofGraveEntity.this.random.range(-3, 3);
								}
								BlockPos blockPos = RoofGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
								ImpEntity imp = (ImpEntity) PvZEntity.IMP.create(RoofGraveEntity.this.getWorld());
								imp.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
								imp.initialize(serverWorld, RoofGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
								imp.setOwner(RoofGraveEntity.this);
								imp.defenseMultiplier = RoofGraveEntity.this.defenseMultiplier;
								serverWorld.spawnEntityAndPassengers(imp);
							}
							double random = Math.random();
							if (random <= 0.33){
								for (int j = 0; j < 1; ++j) { // 50% x1 Gargantuar
									if (!RoofGraveEntity.this.is1x1()) {
										zombiePosZ = RoofGraveEntity.this.random.range(-1, 1);
										zombiePos = RoofGraveEntity.this.random.range(-1, 1);
									}
									if (RoofGraveEntity.this.isChallengeGrave()) {
										zombiePosZ = RoofGraveEntity.this.random.range(-3, 3);
										zombiePos = RoofGraveEntity.this.random.range(-3, 3);
									}
									BlockPos blockPos = RoofGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
									GargantuarEntity gargantuar = (GargantuarEntity) PvZEntity.GARGANTUAR.create(RoofGraveEntity.this.getWorld());
									gargantuar.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
									gargantuar.initialize(serverWorld, RoofGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
									gargantuar.setOwner(RoofGraveEntity.this);
									gargantuar.defenseMultiplier = RoofGraveEntity.this.defenseMultiplier;
									serverWorld.spawnEntityAndPassengers(gargantuar);
								}
							}
							specialGraveWeight += 1.25;
						}
					}
				}
			}

			++this.roofGraveEntity.spawnCounter;
        }

        protected SoundEvent getSoundPrepare() {
            return PvZSounds.GRAVERISINGEVENT;
        }

        protected Spell getSpell() {
            return Spell.SUMMON_VEX;
        }
    }
}
