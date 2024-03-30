package io.github.GrassyDev.pvzmod.registry.entity.gravestones.basicgrave;

import com.jamieswhiteshirt.reachentityattributes.ReachEntityAttributes;
import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.PvZSounds;
import io.github.GrassyDev.pvzmod.registry.entity.gravestones.GraveEntity;
import io.github.GrassyDev.pvzmod.registry.entity.variants.graves.GraveDifficulty;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.oc.bully.basic.BullyEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz1.browncoat.modernday.BrowncoatEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz1.flagzombie.modernday.FlagzombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz1.imp.modernday.ImpEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz1.polevaulting.PoleVaultingEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvzbfn.actionhero.ActionheroEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvzgw.scientist.ScientistEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvzgw.soldier.SoldierEntity;
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


import java.util.Objects;

import static io.github.GrassyDev.pvzmod.PvZCubed.PVZCONFIG;

public class BasicGraveEntity extends GraveEntity implements GeoAnimatable {

	private String controllerName = "walkingcontroller";

	private int spawnCounter;



	double tiltchance = this.random.nextDouble();

    private AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

    public BasicGraveEntity(EntityType<BasicGraveEntity> entityType, World world) {
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
	public void registerControllers(AnimatableManager data) {
		AnimationController controller = new AnimationController(this, controllerName, 0, this::predicate);

		data.addAnimationController(controller);
	}

	@Override
	public AnimatableInstanceCache getFactory() {
		return this.factory;
	}

	private <P extends GeoAnimatable> PlayState predicate(AnimationState<P> event) {
		if (beingEaten){
			event.getController().setAnimation(new RawAnimation().loop("obstacle.eating"));
		}
        else if (tiltchance <= 0.5) {
            event.getController().setAnimation(new RawAnimation().loop("gravestone.idle"));
        }
        else {
            event.getController().setAnimation(new RawAnimation().loop("gravestone.idle2"));
        }
        return PlayState.CONTINUE;
    }


	/** /~*~//~*AI*~//~*~/ **/

	protected void initGoals() {
		this.targetSelector.add(1, new RevengeGoal(this, new Class[0]));
        this.initCustomGoals();
    }

    protected void initCustomGoals() {
        this.goalSelector.add(1, new BasicGraveEntity.summonZombieGoal(this));
    }


	/** /~*~//~*TICKING*~//~*~/ **/

	public void tick() {
		super.tick();
		this.setTarget(this.getWorld().getClosestPlayer(this.getX(), this.getY(), this.getZ(), 100, true));
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

	public static DefaultAttributeContainer.Builder createBasicGraveAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 100.0D)
				.add(ReachEntityAttributes.ATTACK_RANGE, 1.5D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, PVZCONFIG.nestedZombieHealth.basicGraveH());
    }

	protected SoundEvent getDeathSound() {
		return SoundEvents.BLOCK_ANCIENT_DEBRIS_BREAK;
	}

	protected SoundEvent getHurtSound(DamageSource source) {
		return SoundEvents.BLOCK_BASALT_HIT;
	}


	/** /~*~//~*SPAWNING*~//~*~/ **/

	public static boolean canBasicGraveSpawn(EntityType<? extends BasicGraveEntity> type, ServerWorldAccess world, SpawnReason spawnReason, BlockPos pos, RandomGenerator random) {
		BlockPos blockPos = pos.down();
		float cavespawn = random.nextFloat();
		if (cavespawn <= 0.66) {
			return world.getDifficulty() != Difficulty.PEACEFUL &&
					!world.getBlockState(pos).getMaterial().isLiquid() &&
					world.toServerWorld().getTime() > 6000  &&
					pos.getY() > 50 &&
					!world.getBlockState(blockPos).getBlock().hasDynamicBounds() &&
					!checkVillager(Vec3d.ofCenter(pos), world) &&
					!checkPlant(Vec3d.ofCenter(pos), world) && Objects.requireNonNull(world.getServer()).getGameRules().getBoolean(PvZCubed.SHOULD_GRAVE_SPAWN);
		}
		else {
			return world.getDifficulty() != Difficulty.PEACEFUL &&
					!world.getBlockState(pos).getMaterial().isLiquid() &&
					world.toServerWorld().getTime() > 6000 &&
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
			LivingEntity livingEntity = BasicGraveEntity.this.getTarget();
			if (livingEntity != null && livingEntity.isAlive()) {
				if (BasicGraveEntity.this.isSpellcasting()) {
					return false;
				} else {
					return BasicGraveEntity.this.age >= this.startTime;
				}
			} else {
				return false;
			}
		}

		public boolean shouldContinue() {
			LivingEntity livingEntity = BasicGraveEntity.this.getTarget();
			return livingEntity != null && livingEntity.isAlive() && this.spellCooldown > 0;
		}

		public void start() {
			this.spellCooldown = this.getTickCount(this.getInitialCooldown());
			BasicGraveEntity.this.spellTicks = this.getSpellTicks();
			this.startTime = BasicGraveEntity.this.age + this.startTimeDelay();
			SoundEvent soundEvent = this.getSoundPrepare();
			if (soundEvent != null) {
				BasicGraveEntity.this.playSound(soundEvent, 1.0F, 1.0F);
			}

			BasicGraveEntity.this.setSpell(this.getSpell());
		}

		public void tick() {
			--this.spellCooldown;
			if (this.spellCooldown == 0) {
				this.castSpell();
				BasicGraveEntity.this.addStatusEffect((new StatusEffectInstance(StatusEffects.GLOWING, 70, 1)));
				BasicGraveEntity.this.playSound(BasicGraveEntity.this.getCastSpellSound(), 1.0F, 1.0F);
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
		return ModItems.BASICGRAVESPAWN.getDefaultStack();
	}

	class summonZombieGoal extends BasicGraveEntity.CastSpellGoal {
        private final TargetPredicate closeZombiePredicate;

		private final BasicGraveEntity basicGraveEntity;

		private summonZombieGoal(BasicGraveEntity basicGraveEntity) {
            super();
			this.basicGraveEntity = basicGraveEntity;
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
            ServerWorld serverWorld = (ServerWorld)BasicGraveEntity.this.getWorld();
			LocalDifficulty localDifficulty = world.getLocalDifficulty(this.basicGraveEntity.getBlockPos());
			double difficulty = 0;
			if (this.basicGraveEntity.getVariant().equals(GraveDifficulty.NONE)){
				difficulty = localDifficulty.getLocalDifficulty();
				if (difficulty >= 2.1){
					difficulty = 2.1;
					if (world.getDifficulty().equals(Difficulty.HARD)){
						difficulty = difficulty + difficultymodifier;
					}
				}
			}
			else if (this.basicGraveEntity.getVariant().equals(GraveDifficulty.EASY)){
				difficulty = 1.0;
			}
			else if (this.basicGraveEntity.getVariant().equals(GraveDifficulty.EASYMED)){
				difficulty = 1.5;
			}
			else if (this.basicGraveEntity.getVariant().equals(GraveDifficulty.MED)){
				difficulty = 1.6;
			}
			else if (this.basicGraveEntity.getVariant().equals(GraveDifficulty.MEDHARD)){
				difficulty = 1.8;
			}
			else if (this.basicGraveEntity.getVariant().equals(GraveDifficulty.HARD)){
				difficulty = 2.1;
			}
			else if (this.basicGraveEntity.getVariant().equals(GraveDifficulty.SUPERHARD)){
				difficulty = 3;
			}
			else if (this.basicGraveEntity.getVariant().equals(GraveDifficulty.NIGHTMARE)){
				difficulty = 4;
			}
			else if (this.basicGraveEntity.getVariant().equals(GraveDifficulty.CRAAAZY)){
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
			double probability15 = random.nextDouble() * Math.pow(difficulty / 2, -1 * (difficulty / 2));

			int zombiePos = -2 + BasicGraveEntity.this.random.nextInt(5);
			int zombiePosZ = -2 + BasicGraveEntity.this.random.nextInt(5);
			if (BasicGraveEntity.this.is1x1()){
				zombiePos = 0;
				zombiePosZ = 0;
			}

            for(int b = 0; b < 1; ++b) { // 100% x1 Browncoat
				if (!BasicGraveEntity.this.is1x1()) {
					zombiePosZ = BasicGraveEntity.this.random.range(-1, 1);
					zombiePos = BasicGraveEntity.this.random.range(-1, 1);
				}
				if (BasicGraveEntity.this.isChallengeGrave()) {
					zombiePosZ = BasicGraveEntity.this.random.range(-3, 3);
					zombiePos = BasicGraveEntity.this.random.range(-3, 3);
				}
                BlockPos blockPos = BasicGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
                BrowncoatEntity browncoatEntity = (BrowncoatEntity)PvZEntity.BROWNCOAT.create(BasicGraveEntity.this.getWorld());
                browncoatEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
                browncoatEntity.initialize(serverWorld, BasicGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData)null, (NbtCompound)null);
                browncoatEntity.setOwner(BasicGraveEntity.this);
				browncoatEntity.defenseMultiplier = BasicGraveEntity.this.defenseMultiplier;
					serverWorld.spawnEntityAndPassengers(browncoatEntity);
            }
			if (probability <= 0.25 / halfModifier * survChance) { // 25% x1 Conehead
				for (int c = 0; c < 1; ++c) {
					if (!BasicGraveEntity.this.is1x1()) {
						zombiePosZ = BasicGraveEntity.this.random.range(-1, 1);
						zombiePos = BasicGraveEntity.this.random.range(-1, 1);
					}
					if (BasicGraveEntity.this.isChallengeGrave()) {
						zombiePosZ = BasicGraveEntity.this.random.range(-3, 3);
						zombiePos = BasicGraveEntity.this.random.range(-3, 3);
					}
					BlockPos blockPos = BasicGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
					BrowncoatEntity coneheadEntity = (BrowncoatEntity) PvZEntity.CONEHEAD.create(BasicGraveEntity.this.getWorld());
					coneheadEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
					coneheadEntity.initialize(serverWorld, BasicGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
					coneheadEntity.setOwner(BasicGraveEntity.this);
					coneheadEntity.defenseMultiplier = BasicGraveEntity.this.defenseMultiplier;
					serverWorld.spawnEntityAndPassengers(coneheadEntity);
				}
				for(int b = 0; b < 1; ++b) { // 100% x1 Browncoat
					if (!BasicGraveEntity.this.is1x1()) {
						zombiePosZ = BasicGraveEntity.this.random.range(-1, 1);
						zombiePos = BasicGraveEntity.this.random.range(-1, 1);
					}
					if (BasicGraveEntity.this.isChallengeGrave()) {
						zombiePosZ = BasicGraveEntity.this.random.range(-3, 3);
						zombiePos = BasicGraveEntity.this.random.range(-3, 3);
					}
					BlockPos blockPos = BasicGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
					BrowncoatEntity browncoatEntity = (BrowncoatEntity)PvZEntity.BROWNCOAT.create(BasicGraveEntity.this.getWorld());
					browncoatEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
					browncoatEntity.initialize(serverWorld, BasicGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData)null, (NbtCompound)null);
					browncoatEntity.setOwner(BasicGraveEntity.this);
					browncoatEntity.defenseMultiplier = BasicGraveEntity.this.defenseMultiplier;
					serverWorld.spawnEntityAndPassengers(browncoatEntity);
				}
			}
			if (serverWorld.toServerWorld().getTime() > 24000) {
				if (probability2 <= 0.05 / halfModifier * survChance) { // 5% x1 Buckethead
					for (int u = 0; u < 1; ++u) {
						if (!BasicGraveEntity.this.is1x1()) {
							zombiePosZ = BasicGraveEntity.this.random.range(-1, 1);
							zombiePos = BasicGraveEntity.this.random.range(-1, 1);
						}
						if (BasicGraveEntity.this.isChallengeGrave()) {
							zombiePosZ = BasicGraveEntity.this.random.range(-3, 3);
							zombiePos = BasicGraveEntity.this.random.range(-3, 3);
						}
						BlockPos blockPos = BasicGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
						BrowncoatEntity bucketheadEntity = (BrowncoatEntity) PvZEntity.BUCKETHEAD.create(BasicGraveEntity.this.getWorld());
						bucketheadEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
						bucketheadEntity.initialize(serverWorld, BasicGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
						bucketheadEntity.setOwner(BasicGraveEntity.this);
						bucketheadEntity.defenseMultiplier = BasicGraveEntity.this.defenseMultiplier;
						serverWorld.spawnEntityAndPassengers(bucketheadEntity);
					}
					for (int c = 0; c < 2 / halfModifier; ++c) { // 100% x2 Conehead
						if (!BasicGraveEntity.this.is1x1()) {
							zombiePosZ = BasicGraveEntity.this.random.range(-1, 1);
							zombiePos = BasicGraveEntity.this.random.range(-1, 1);
						}
						if (BasicGraveEntity.this.isChallengeGrave()) {
							zombiePosZ = BasicGraveEntity.this.random.range(-3, 3);
							zombiePos = BasicGraveEntity.this.random.range(-3, 3);
						}
						BlockPos blockPos = BasicGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
						BrowncoatEntity coneheadEntity = (BrowncoatEntity) PvZEntity.CONEHEAD.create(BasicGraveEntity.this.getWorld());
						coneheadEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
						coneheadEntity.initialize(serverWorld, BasicGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
						coneheadEntity.setOwner(BasicGraveEntity.this);
						coneheadEntity.defenseMultiplier = BasicGraveEntity.this.defenseMultiplier;
					serverWorld.spawnEntityAndPassengers(coneheadEntity);
					}
				}
				if (difficulty >= 1.519 + difficultymodifier || isUnlock()) {
					if (probability11 <= 0.05 / halfModifier * survChance) { // 5% x2 Conehead
						for (int c = 0; c < 2 / halfModifier; ++c) {
							if (!BasicGraveEntity.this.is1x1()) {
								zombiePosZ = BasicGraveEntity.this.random.range(-1, 1);
								zombiePos = BasicGraveEntity.this.random.range(-1, 1);
							}
							if (BasicGraveEntity.this.isChallengeGrave()) {
								zombiePosZ = BasicGraveEntity.this.random.range(-3, 3);
								zombiePos = BasicGraveEntity.this.random.range(-3, 3);
							}
							BlockPos blockPos = BasicGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							BrowncoatEntity coneheadEntity = (BrowncoatEntity) PvZEntity.CONEHEAD.create(BasicGraveEntity.this.getWorld());
							coneheadEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
							coneheadEntity.initialize(serverWorld, BasicGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							coneheadEntity.setOwner(BasicGraveEntity.this);
							coneheadEntity.defenseMultiplier = BasicGraveEntity.this.defenseMultiplier;
					serverWorld.spawnEntityAndPassengers(coneheadEntity);
						}
						for(int b = 0; b < 2 / halfModifier; ++b) { // 100% x2 Browncoat
							if (!BasicGraveEntity.this.is1x1()) {
								zombiePosZ = BasicGraveEntity.this.random.range(-1, 1);
								zombiePos = BasicGraveEntity.this.random.range(-1, 1);
							}
							if (BasicGraveEntity.this.isChallengeGrave()) {
								zombiePosZ = BasicGraveEntity.this.random.range(-3, 3);
								zombiePos = BasicGraveEntity.this.random.range(-3, 3);
							}
							BlockPos blockPos = BasicGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							BrowncoatEntity browncoatEntity = (BrowncoatEntity)PvZEntity.BROWNCOAT.create(BasicGraveEntity.this.getWorld());
							browncoatEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
							browncoatEntity.initialize(serverWorld, BasicGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData)null, (NbtCompound)null);
							browncoatEntity.setOwner(BasicGraveEntity.this);
							browncoatEntity.defenseMultiplier = BasicGraveEntity.this.defenseMultiplier;
					serverWorld.spawnEntityAndPassengers(browncoatEntity);
						}
					}
				}
			}
			if (difficulty >= 1.599 + difficultymodifier || isUnlock()) {
				if (probability22 <= 0.15 / halfModifier * survChance) { // 15% x1 Buckethead
					for (int u = 0; u < 2 / halfModifier; ++u) {
						if (!BasicGraveEntity.this.is1x1()) {
							zombiePosZ = BasicGraveEntity.this.random.range(-1, 1);
							zombiePos = BasicGraveEntity.this.random.range(-1, 1);
						}
						if (BasicGraveEntity.this.isChallengeGrave()) {
							zombiePosZ = BasicGraveEntity.this.random.range(-3, 3);
							zombiePos = BasicGraveEntity.this.random.range(-3, 3);
						}
						BlockPos blockPos = BasicGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
						BrowncoatEntity bucketheadEntity = (BrowncoatEntity) PvZEntity.BUCKETHEAD.create(BasicGraveEntity.this.getWorld());
						bucketheadEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
						bucketheadEntity.initialize(serverWorld, BasicGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
						bucketheadEntity.setOwner(BasicGraveEntity.this);
						bucketheadEntity.defenseMultiplier = BasicGraveEntity.this.defenseMultiplier;
						serverWorld.spawnEntityAndPassengers(bucketheadEntity);
					}
					for (int b = 0; b < 1; ++b) { // 100% x1 Browncoat
						if (!BasicGraveEntity.this.is1x1()) {
							zombiePosZ = BasicGraveEntity.this.random.range(-1, 1);
							zombiePos = BasicGraveEntity.this.random.range(-1, 1);
						}
						if (BasicGraveEntity.this.isChallengeGrave()) {
							zombiePosZ = BasicGraveEntity.this.random.range(-3, 3);
							zombiePos = BasicGraveEntity.this.random.range(-3, 3);
						}
						BlockPos blockPos = BasicGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
						BrowncoatEntity browncoatEntity = (BrowncoatEntity) PvZEntity.BROWNCOAT.create(BasicGraveEntity.this.getWorld());
						browncoatEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
						browncoatEntity.initialize(serverWorld, BasicGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
						browncoatEntity.setOwner(BasicGraveEntity.this);
						browncoatEntity.defenseMultiplier = BasicGraveEntity.this.defenseMultiplier;
					serverWorld.spawnEntityAndPassengers(browncoatEntity);
					}
				}
			}
			if (serverWorld.toServerWorld().getTime() > 24000) {
				if (difficulty >= 1.599 + difficultymodifier || isUnlock()) {
					if (probability7 <= 0.1 / halfModifier * survChance) { // 10% x2 Bully Zombie
						for (int h = 0; h < 2 / halfModifier; ++h) {
							if (!BasicGraveEntity.this.is1x1()) {
								zombiePosZ = BasicGraveEntity.this.random.range(-1, 1);
								zombiePos = BasicGraveEntity.this.random.range(-1, 1);
							}
							if (BasicGraveEntity.this.isChallengeGrave()) {
								zombiePosZ = BasicGraveEntity.this.random.range(-3, 3);
								zombiePos = BasicGraveEntity.this.random.range(-3, 3);
							}
							BlockPos blockPos = BasicGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							BullyEntity bullyEntity = (BullyEntity) PvZEntity.BULLY.create(BasicGraveEntity.this.getWorld());
							bullyEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
							bullyEntity.initialize(serverWorld, BasicGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							bullyEntity.setOwner(BasicGraveEntity.this);
							bullyEntity.defenseMultiplier = BasicGraveEntity.this.defenseMultiplier;
							serverWorld.spawnEntityAndPassengers(bullyEntity);
						}
						for (int b = 0; b < 1; ++b) { // 100% x1 Buckethead
							if (!BasicGraveEntity.this.is1x1()) {
								zombiePosZ = BasicGraveEntity.this.random.range(-1, 1);
								zombiePos = BasicGraveEntity.this.random.range(-1, 1);
							}
							if (BasicGraveEntity.this.isChallengeGrave()) {
								zombiePosZ = BasicGraveEntity.this.random.range(-3, 3);
								zombiePos = BasicGraveEntity.this.random.range(-3, 3);
							}
							BlockPos blockPos = BasicGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							BrowncoatEntity buckethead = (BrowncoatEntity) PvZEntity.BUCKETHEAD.create(BasicGraveEntity.this.getWorld());
							buckethead.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
							buckethead.initialize(serverWorld, BasicGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							buckethead.setOwner(BasicGraveEntity.this);
							buckethead.defenseMultiplier = BasicGraveEntity.this.defenseMultiplier;
							serverWorld.spawnEntityAndPassengers(buckethead);
						}
					}
				}
				if (difficulty >= 1.599 + difficultymodifier || isUnlock()) {
					if (probability10 <= 0.3 / halfModifier * survChance) { // 5% x1 Brickhead Zombie
						for (int j = 0; j < 1; ++j) {
							if (!BasicGraveEntity.this.is1x1()) {
								zombiePosZ = BasicGraveEntity.this.random.range(-1, 1);
								zombiePos = BasicGraveEntity.this.random.range(-1, 1);
							}
							if (BasicGraveEntity.this.isChallengeGrave()) {
								zombiePosZ = BasicGraveEntity.this.random.range(-3, 3);
								zombiePos = BasicGraveEntity.this.random.range(-3, 3);
							}
							BlockPos blockPos = BasicGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							BrowncoatEntity brickhead = (BrowncoatEntity) PvZEntity.BRICKHEAD.create(BasicGraveEntity.this.getWorld());
							brickhead.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
							brickhead.initialize(serverWorld, BasicGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							brickhead.setOwner(BasicGraveEntity.this);
							brickhead.defenseMultiplier = BasicGraveEntity.this.defenseMultiplier;
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
								if (!BasicGraveEntity.this.is1x1()) {
									zombiePosZ = BasicGraveEntity.this.random.range(-1, 1);
									zombiePos = BasicGraveEntity.this.random.range(-1, 1);
								}
								if (BasicGraveEntity.this.isChallengeGrave()) {
									zombiePosZ = BasicGraveEntity.this.random.range(-3, 3);
									zombiePos = BasicGraveEntity.this.random.range(-3, 3);
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
								BlockPos blockPos = BasicGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
								FlagzombieEntity flagzombieEntity = (FlagzombieEntity) flagType.create(BasicGraveEntity.this.getWorld());
								flagzombieEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
								flagzombieEntity.initialize(serverWorld, BasicGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
								flagzombieEntity.setOwner(BasicGraveEntity.this);
								flagzombieEntity.defenseMultiplier = BasicGraveEntity.this.defenseMultiplier;
								serverWorld.spawnEntityAndPassengers(flagzombieEntity);
							}
							extraGraveWeight += 1;
						}
					}
				}
				if (extraGraveWeight <= 2.5) {
					if (difficulty >= 1.549 + difficultymodifier || isUnlock()) {
						if (probability3 <= 0.15 / halfModifier * survChance) { // 15% x2 Pole Vaulting Zombie
							for (int p = 0; p < 2 / halfModifier; ++p) {
								if (!BasicGraveEntity.this.is1x1()) {
									zombiePosZ = BasicGraveEntity.this.random.range(-1, 1);
									zombiePos = BasicGraveEntity.this.random.range(-1, 1);
								}
								if (BasicGraveEntity.this.isChallengeGrave()) {
									zombiePosZ = BasicGraveEntity.this.random.range(-3, 3);
									zombiePos = BasicGraveEntity.this.random.range(-3, 3);
								}
								BlockPos blockPos = BasicGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
								PoleVaultingEntity poleVaultingEntity = (PoleVaultingEntity) PvZEntity.POLEVAULTING.create(BasicGraveEntity.this.getWorld());
								poleVaultingEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
								poleVaultingEntity.initialize(serverWorld, BasicGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
								poleVaultingEntity.setOwner(BasicGraveEntity.this);
								poleVaultingEntity.defenseMultiplier = BasicGraveEntity.this.defenseMultiplier;
								serverWorld.spawnEntityAndPassengers(poleVaultingEntity);
							}
							for (int b = 0; b < 2 / halfModifier; ++b) { // 100% x2 Browncoat
								if (!BasicGraveEntity.this.is1x1()) {
									zombiePosZ = BasicGraveEntity.this.random.range(-1, 1);
									zombiePos = BasicGraveEntity.this.random.range(-1, 1);
								}
								if (BasicGraveEntity.this.isChallengeGrave()) {
									zombiePosZ = BasicGraveEntity.this.random.range(-3, 3);
									zombiePos = BasicGraveEntity.this.random.range(-3, 3);
								}
								BlockPos blockPos = BasicGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
								BrowncoatEntity browncoatEntity = (BrowncoatEntity) PvZEntity.BROWNCOAT.create(BasicGraveEntity.this.getWorld());
								browncoatEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
								browncoatEntity.initialize(serverWorld, BasicGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
								browncoatEntity.setOwner(BasicGraveEntity.this);
								browncoatEntity.defenseMultiplier = BasicGraveEntity.this.defenseMultiplier;
					serverWorld.spawnEntityAndPassengers(browncoatEntity);
							}
							extraGraveWeight += 1;
						}
					}
				}
				if (extraGraveWeight <= 2.5) {
					if ((difficulty >= 2.009 + difficultymodifier) && (isUnlock() || isUnlockSpecial())) {
						if (probability8 <= 0.15 / halfModifier * survChance) { // 15% x2 Trash Can
							for (int g = 0; g < 2 / halfModifier; ++g) {
								if (!BasicGraveEntity.this.is1x1()) {
									zombiePosZ = BasicGraveEntity.this.random.range(-1, 1);
									zombiePos = BasicGraveEntity.this.random.range(-1, 1);
								}
								if (BasicGraveEntity.this.isChallengeGrave()) {
									zombiePosZ = BasicGraveEntity.this.random.range(-3, 3);
									zombiePos = BasicGraveEntity.this.random.range(-3, 3);
								}
								BlockPos blockPos = BasicGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
								BrowncoatEntity trashCan = (BrowncoatEntity) PvZEntity.TRASHCAN.create(BasicGraveEntity.this.getWorld());
								trashCan.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
								trashCan.initialize(serverWorld, BasicGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
								trashCan.setOwner(BasicGraveEntity.this);
								trashCan.defenseMultiplier = BasicGraveEntity.this.defenseMultiplier;
								serverWorld.spawnEntityAndPassengers(trashCan);
							}
							for (int b = 0; b < 4 / halfModifier; ++b) { // 100% x4 Browncoat
								if (!BasicGraveEntity.this.is1x1()) {
									zombiePosZ = BasicGraveEntity.this.random.range(-1, 1);
									zombiePos = BasicGraveEntity.this.random.range(-1, 1);
								}
								if (BasicGraveEntity.this.isChallengeGrave()) {
									zombiePosZ = BasicGraveEntity.this.random.range(-3, 3);
									zombiePos = BasicGraveEntity.this.random.range(-3, 3);
								}
								BlockPos blockPos = BasicGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
								BrowncoatEntity browncoatEntity = (BrowncoatEntity) PvZEntity.BROWNCOAT.create(BasicGraveEntity.this.getWorld());
								browncoatEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
								browncoatEntity.initialize(serverWorld, BasicGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
								browncoatEntity.setOwner(BasicGraveEntity.this);
								browncoatEntity.defenseMultiplier = BasicGraveEntity.this.defenseMultiplier;
					serverWorld.spawnEntityAndPassengers(browncoatEntity);
							}
							extraGraveWeight += 1.25;
						}
					}
				}
				if (extraGraveWeight <= 2.5) {
					if ((difficulty >= 2.009 + difficultymodifier) && (isUnlock() || isUnlockSpecial())) {
						if (probability6 <= 0.05 / halfModifier * survChance) { // 5% x3 Brickhead
							for (int j = 0; j < 3/ halfModifier ; ++j) {
								if (!BasicGraveEntity.this.is1x1()) {
									zombiePosZ = BasicGraveEntity.this.random.range(-1, 1);
									zombiePos = BasicGraveEntity.this.random.range(-1, 1);
								}
								if (BasicGraveEntity.this.isChallengeGrave()) {
									zombiePosZ = BasicGraveEntity.this.random.range(-3, 3);
									zombiePos = BasicGraveEntity.this.random.range(-3, 3);
								}
								BlockPos blockPos = BasicGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
								BrowncoatEntity brickhead = (BrowncoatEntity) PvZEntity.BRICKHEAD.create(BasicGraveEntity.this.getWorld());
								brickhead.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
								brickhead.initialize(serverWorld, BasicGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
								brickhead.setOwner(BasicGraveEntity.this);
								brickhead.defenseMultiplier = BasicGraveEntity.this.defenseMultiplier;
							serverWorld.spawnEntityAndPassengers(brickhead);
							}
							for (int b = 0; b < 2 / halfModifier; ++b) { // 100% x2 Browncoat
								if (!BasicGraveEntity.this.is1x1()) {
									zombiePosZ = BasicGraveEntity.this.random.range(-1, 1);
									zombiePos = BasicGraveEntity.this.random.range(-1, 1);
								}
								if (BasicGraveEntity.this.isChallengeGrave()) {
									zombiePosZ = BasicGraveEntity.this.random.range(-3, 3);
									zombiePos = BasicGraveEntity.this.random.range(-3, 3);
								}
								BlockPos blockPos = BasicGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
								BrowncoatEntity browncoatEntity = (BrowncoatEntity) PvZEntity.BROWNCOAT.create(BasicGraveEntity.this.getWorld());
								browncoatEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
								browncoatEntity.initialize(serverWorld, BasicGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
								browncoatEntity.setOwner(BasicGraveEntity.this);
								browncoatEntity.defenseMultiplier = BasicGraveEntity.this.defenseMultiplier;
					serverWorld.spawnEntityAndPassengers(browncoatEntity);
							}
							for (int b = 0; b < 2 / halfModifier; ++b) { // 100% x2 Conehead
								if (!BasicGraveEntity.this.is1x1()) {
									zombiePosZ = BasicGraveEntity.this.random.range(-1, 1);
									zombiePos = BasicGraveEntity.this.random.range(-1, 1);
								}
								if (BasicGraveEntity.this.isChallengeGrave()) {
									zombiePosZ = BasicGraveEntity.this.random.range(-3, 3);
									zombiePos = BasicGraveEntity.this.random.range(-3, 3);
								}
								BlockPos blockPos = BasicGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
								BrowncoatEntity browncoatEntity = (BrowncoatEntity) PvZEntity.CONEHEAD.create(BasicGraveEntity.this.getWorld());
								browncoatEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
								browncoatEntity.initialize(serverWorld, BasicGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
								browncoatEntity.setOwner(BasicGraveEntity.this);
								browncoatEntity.defenseMultiplier = BasicGraveEntity.this.defenseMultiplier;
					serverWorld.spawnEntityAndPassengers(browncoatEntity);
							}
							for (int b = 0; b < 1; ++b) { // 100% x1 Buckethead
								if (!BasicGraveEntity.this.is1x1()) {
									zombiePosZ = BasicGraveEntity.this.random.range(-1, 1);
									zombiePos = BasicGraveEntity.this.random.range(-1, 1);
								}
								if (BasicGraveEntity.this.isChallengeGrave()) {
									zombiePosZ = BasicGraveEntity.this.random.range(-3, 3);
									zombiePos = BasicGraveEntity.this.random.range(-3, 3);
								}
								BlockPos blockPos = BasicGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
								BrowncoatEntity browncoatEntity = (BrowncoatEntity) PvZEntity.BUCKETHEAD.create(BasicGraveEntity.this.getWorld());
								browncoatEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
								browncoatEntity.initialize(serverWorld, BasicGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
								browncoatEntity.setOwner(BasicGraveEntity.this);
								browncoatEntity.defenseMultiplier = BasicGraveEntity.this.defenseMultiplier;
					serverWorld.spawnEntityAndPassengers(browncoatEntity);
							}
							extraGraveWeight += 1.75;
						}
					}
				}
				if (extraGraveWeight <= 2.5) {
					if (difficulty >= 1.599 + difficultymodifier || isUnlock()) {
						if (probability9 <= 0.2 / halfModifier * survChance) { // 20% x1 Trash Can Zombie
							for (int g = 0; g < 1; ++g) {
								if (!BasicGraveEntity.this.is1x1()) {
									zombiePosZ = BasicGraveEntity.this.random.range(-1, 1);
									zombiePos = BasicGraveEntity.this.random.range(-1, 1);
								}
								if (BasicGraveEntity.this.isChallengeGrave()) {
									zombiePosZ = BasicGraveEntity.this.random.range(-3, 3);
									zombiePos = BasicGraveEntity.this.random.range(-3, 3);
								}
								BlockPos blockPos = BasicGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
								BrowncoatEntity trashCan = (BrowncoatEntity) PvZEntity.TRASHCAN.create(BasicGraveEntity.this.getWorld());
								trashCan.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
								trashCan.initialize(serverWorld, BasicGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
								trashCan.setOwner(BasicGraveEntity.this);
								trashCan.defenseMultiplier = BasicGraveEntity.this.defenseMultiplier;
								serverWorld.spawnEntityAndPassengers(trashCan);
							}
							for (int b = 0; b < 2 / halfModifier; ++b) { // 100% x2 Browncoat
								if (!BasicGraveEntity.this.is1x1()) {
									zombiePosZ = BasicGraveEntity.this.random.range(-1, 1);
									zombiePos = BasicGraveEntity.this.random.range(-1, 1);
								}
								if (BasicGraveEntity.this.isChallengeGrave()) {
									zombiePosZ = BasicGraveEntity.this.random.range(-3, 3);
									zombiePos = BasicGraveEntity.this.random.range(-3, 3);
								}
								BlockPos blockPos = BasicGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
								BrowncoatEntity browncoatEntity = (BrowncoatEntity) PvZEntity.BROWNCOAT.create(BasicGraveEntity.this.getWorld());
								browncoatEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
								browncoatEntity.initialize(serverWorld, BasicGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
								browncoatEntity.setOwner(BasicGraveEntity.this);
								browncoatEntity.defenseMultiplier = BasicGraveEntity.this.defenseMultiplier;
					serverWorld.spawnEntityAndPassengers(browncoatEntity);
							}
							extraGraveWeight += 0.75;
						}
					}
				}
				if (extraGraveWeight <= 2.5) {
					if (probability13 <= 0.3 / halfModifier * survChance) { // 30% x1 Pole Vaulting Zombie
						for (int p = 0; p < 1; ++p) {
							if (!BasicGraveEntity.this.is1x1()) {
								zombiePosZ = BasicGraveEntity.this.random.range(-1, 1);
								zombiePos = BasicGraveEntity.this.random.range(-1, 1);
							}
							if (BasicGraveEntity.this.isChallengeGrave()) {
								zombiePosZ = BasicGraveEntity.this.random.range(-3, 3);
								zombiePos = BasicGraveEntity.this.random.range(-3, 3);
							}
							BlockPos blockPos = BasicGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							PoleVaultingEntity poleVaultingEntity = (PoleVaultingEntity) PvZEntity.POLEVAULTING.create(BasicGraveEntity.this.getWorld());
							poleVaultingEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
							poleVaultingEntity.initialize(serverWorld, BasicGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							poleVaultingEntity.setOwner(BasicGraveEntity.this);
							poleVaultingEntity.defenseMultiplier = BasicGraveEntity.this.defenseMultiplier;
							serverWorld.spawnEntityAndPassengers(poleVaultingEntity);
						}
						extraGraveWeight += 0.5;
					}
				}
				if (extraGraveWeight <= 2.5) {
					if (probability21 <= 0.25 / halfModifier * survChance) {
						if (difficulty >= 1.599 + difficultymodifier || isUnlock()) { // 25% x1 Brickhead
							for (int j = 0; j < 1; ++j) {
								if (!BasicGraveEntity.this.is1x1()) {
									zombiePosZ = BasicGraveEntity.this.random.range(-1, 1);
									zombiePos = BasicGraveEntity.this.random.range(-1, 1);
								}
								if (BasicGraveEntity.this.isChallengeGrave()) {
									zombiePosZ = BasicGraveEntity.this.random.range(-3, 3);
									zombiePos = BasicGraveEntity.this.random.range(-3, 3);
								}
								BlockPos blockPos = BasicGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
								BrowncoatEntity brickhead = (BrowncoatEntity) PvZEntity.BRICKHEAD.create(BasicGraveEntity.this.getWorld());
								brickhead.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
								brickhead.initialize(serverWorld, BasicGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
								brickhead.setOwner(BasicGraveEntity.this);
								brickhead.defenseMultiplier = BasicGraveEntity.this.defenseMultiplier;
							serverWorld.spawnEntityAndPassengers(brickhead);
							}
							for (int h = 0; h < 1; ++h) { // 100% x1 Bully
								if (!BasicGraveEntity.this.is1x1()) {
									zombiePosZ = BasicGraveEntity.this.random.range(-1, 1);
									zombiePos = BasicGraveEntity.this.random.range(-1, 1);
								}
								if (BasicGraveEntity.this.isChallengeGrave()) {
									zombiePosZ = BasicGraveEntity.this.random.range(-3, 3);
									zombiePos = BasicGraveEntity.this.random.range(-3, 3);
								}
								BlockPos blockPos = BasicGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
								BullyEntity bullyEntity = (BullyEntity) PvZEntity.BULLY.create(BasicGraveEntity.this.getWorld());
								bullyEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
								bullyEntity.initialize(serverWorld, BasicGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
								bullyEntity.setOwner(BasicGraveEntity.this);
								bullyEntity.defenseMultiplier = BasicGraveEntity.this.defenseMultiplier;
							serverWorld.spawnEntityAndPassengers(bullyEntity);
							}
							extraGraveWeight += 0.75;
						}
					}
				}
				if (specialGraveWeight <= 3) {
					if ((difficulty >= 2.009 + difficultymodifier) && (isUnlock() || isUnlockSpecial())) {
						if (probability12 <= 0.10 / halfModifier * survChance) { // 10% x1 Foot Soldier
							for (int p = 0; p < 1; ++p) {
								if (!BasicGraveEntity.this.is1x1()) {
									zombiePosZ = BasicGraveEntity.this.random.range(-1, 1);
									zombiePos = BasicGraveEntity.this.random.range(-1, 1);
								}
								if (BasicGraveEntity.this.isChallengeGrave()) {
									zombiePosZ = BasicGraveEntity.this.random.range(-3, 3);
									zombiePos = BasicGraveEntity.this.random.range(-3, 3);
								}
								BlockPos blockPos = BasicGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
								SoldierEntity soldier = (SoldierEntity) PvZEntity.SOLDIER.create(BasicGraveEntity.this.getWorld());
								soldier.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
								soldier.initialize(serverWorld, BasicGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
								soldier.setOwner(BasicGraveEntity.this);
								soldier.defenseMultiplier = BasicGraveEntity.this.defenseMultiplier;
								serverWorld.spawnEntityAndPassengers(soldier);
							}
							for (int b = 0; b < 3 / halfModifier; ++b) { // 100% x3 Conehead
								if (!BasicGraveEntity.this.is1x1()) {
									zombiePosZ = BasicGraveEntity.this.random.range(-1, 1);
									zombiePos = BasicGraveEntity.this.random.range(-1, 1);
								}
								if (BasicGraveEntity.this.isChallengeGrave()) {
									zombiePosZ = BasicGraveEntity.this.random.range(-3, 3);
									zombiePos = BasicGraveEntity.this.random.range(-3, 3);
								}
								BlockPos blockPos = BasicGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
								BrowncoatEntity browncoatEntity = (BrowncoatEntity) PvZEntity.CONEHEAD.create(BasicGraveEntity.this.getWorld());
								browncoatEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
								browncoatEntity.initialize(serverWorld, BasicGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
								browncoatEntity.setOwner(BasicGraveEntity.this);
								browncoatEntity.defenseMultiplier = BasicGraveEntity.this.defenseMultiplier;
					serverWorld.spawnEntityAndPassengers(browncoatEntity);
							}
							for (int h = 0; h <2 / halfModifier; ++h) { // 100% x2 Bully
								if (!BasicGraveEntity.this.is1x1()) {
									zombiePosZ = BasicGraveEntity.this.random.range(-1, 1);
									zombiePos = BasicGraveEntity.this.random.range(-1, 1);
								}
								if (BasicGraveEntity.this.isChallengeGrave()) {
									zombiePosZ = BasicGraveEntity.this.random.range(-3, 3);
									zombiePos = BasicGraveEntity.this.random.range(-3, 3);
								}
								BlockPos blockPos = BasicGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
								BullyEntity bullyEntity = (BullyEntity) PvZEntity.BULLY.create(BasicGraveEntity.this.getWorld());
								bullyEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
								bullyEntity.initialize(serverWorld, BasicGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
								bullyEntity.setOwner(BasicGraveEntity.this);
								bullyEntity.defenseMultiplier = BasicGraveEntity.this.defenseMultiplier;
							serverWorld.spawnEntityAndPassengers(bullyEntity);
							}
							specialGraveWeight += 1.5;
						}
					}
				}
				if (specialGraveWeight <= 3) {
					if ((difficulty >= 2.009 + difficultymodifier) && (isUnlock() || isUnlockSpecial())) {
						if (probability14 <= 0.15 / halfModifier * survChance) { // 15% x1 Scientist
							for (int p = 0; p < 1; ++p) {
								if (!BasicGraveEntity.this.is1x1()) {
									zombiePosZ = BasicGraveEntity.this.random.range(-1, 1);
									zombiePos = BasicGraveEntity.this.random.range(-1, 1);
								}
								if (BasicGraveEntity.this.isChallengeGrave()) {
									zombiePosZ = BasicGraveEntity.this.random.range(-3, 3);
									zombiePos = BasicGraveEntity.this.random.range(-3, 3);
								}
								BlockPos blockPos = BasicGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
								ScientistEntity scientistEntity = (ScientistEntity) PvZEntity.SCIENTIST.create(BasicGraveEntity.this.getWorld());
								scientistEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
								scientistEntity.initialize(serverWorld, BasicGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
								scientistEntity.setOwner(BasicGraveEntity.this);
								scientistEntity.defenseMultiplier = BasicGraveEntity.this.defenseMultiplier;
								serverWorld.spawnEntityAndPassengers(scientistEntity);
							}
							for (int b = 0; b < 1; ++b) { // 100% x1 Conehead
								if (!BasicGraveEntity.this.is1x1()) {
									zombiePosZ = BasicGraveEntity.this.random.range(-1, 1);
									zombiePos = BasicGraveEntity.this.random.range(-1, 1);
								}
								if (BasicGraveEntity.this.isChallengeGrave()) {
									zombiePosZ = BasicGraveEntity.this.random.range(-3, 3);
									zombiePos = BasicGraveEntity.this.random.range(-3, 3);
								}
								BlockPos blockPos = BasicGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
								BrowncoatEntity browncoatEntity = (BrowncoatEntity) PvZEntity.CONEHEAD.create(BasicGraveEntity.this.getWorld());
								browncoatEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
								browncoatEntity.initialize(serverWorld, BasicGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
								browncoatEntity.setOwner(BasicGraveEntity.this);
								browncoatEntity.defenseMultiplier = BasicGraveEntity.this.defenseMultiplier;
					serverWorld.spawnEntityAndPassengers(browncoatEntity);
							}
							for (int b = 0; b < 1; ++b) { // 100% x1 Buckethead
								if (!BasicGraveEntity.this.is1x1()) {
									zombiePosZ = BasicGraveEntity.this.random.range(-1, 1);
									zombiePos = BasicGraveEntity.this.random.range(-1, 1);
								}
								if (BasicGraveEntity.this.isChallengeGrave()) {
									zombiePosZ = BasicGraveEntity.this.random.range(-3, 3);
									zombiePos = BasicGraveEntity.this.random.range(-3, 3);
								}
								BlockPos blockPos = BasicGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
								BrowncoatEntity browncoatEntity = (BrowncoatEntity) PvZEntity.BUCKETHEAD.create(BasicGraveEntity.this.getWorld());
								browncoatEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
								browncoatEntity.initialize(serverWorld, BasicGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
								browncoatEntity.setOwner(BasicGraveEntity.this);
								browncoatEntity.defenseMultiplier = BasicGraveEntity.this.defenseMultiplier;
					serverWorld.spawnEntityAndPassengers(browncoatEntity);
							}
							specialGraveWeight += 1.25;
						}
					}
				}
				if (specialGraveWeight <= 3) {
					if ((difficulty >= 2.009 + difficultymodifier) && (isUnlock() || isUnlockSpecial())) {
						if (probability15 <= 0.10 / halfModifier * survChance) { // 10% x1 80s Action Hero
							for (int p = 0; p < 1; ++p) {
								if (!BasicGraveEntity.this.is1x1()) {
									zombiePosZ = BasicGraveEntity.this.random.range(-1, 1);
									zombiePos = BasicGraveEntity.this.random.range(-1, 1);
								}
								if (BasicGraveEntity.this.isChallengeGrave()) {
									zombiePosZ = BasicGraveEntity.this.random.range(-3, 3);
									zombiePos = BasicGraveEntity.this.random.range(-3, 3);
								}
								BlockPos blockPos = BasicGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
								ActionheroEntity actionheroEntity = (ActionheroEntity) PvZEntity.ACTIONHERO.create(BasicGraveEntity.this.getWorld());
								actionheroEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
								actionheroEntity.initialize(serverWorld, BasicGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
								actionheroEntity.setOwner(BasicGraveEntity.this);
								actionheroEntity.defenseMultiplier = BasicGraveEntity.this.defenseMultiplier;
								serverWorld.spawnEntityAndPassengers(actionheroEntity);
							}
							for (int h = 0; h <4 / halfModifier; ++h) { // 100% x4 Bully
								if (!BasicGraveEntity.this.is1x1()) {
									zombiePosZ = BasicGraveEntity.this.random.range(-1, 1);
									zombiePos = BasicGraveEntity.this.random.range(-1, 1);
								}
								if (BasicGraveEntity.this.isChallengeGrave()) {
									zombiePosZ = BasicGraveEntity.this.random.range(-3, 3);
									zombiePos = BasicGraveEntity.this.random.range(-3, 3);
								}
								BlockPos blockPos = BasicGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
								BullyEntity bullyEntity = (BullyEntity) PvZEntity.BULLY.create(BasicGraveEntity.this.getWorld());
								bullyEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
								bullyEntity.initialize(serverWorld, BasicGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
								bullyEntity.setOwner(BasicGraveEntity.this);
								bullyEntity.defenseMultiplier = BasicGraveEntity.this.defenseMultiplier;
							serverWorld.spawnEntityAndPassengers(bullyEntity);
							}
							specialGraveWeight += 1.5;
						}
					}
				}
				if (specialGraveWeight <= 3) {
					if ((difficulty >= 1.599 + difficultymodifier) && (isUnlock() || isUnlockSpecial())) {
						if (probability5 <= 0.15 / halfModifier * survChance) { // 15% x2 Imp-Throwing Imp
							for (int p = 0; p < 2 / halfModifier; ++p) {
								if (!BasicGraveEntity.this.is1x1()) {
									zombiePosZ = BasicGraveEntity.this.random.range(-1, 1);
									zombiePos = BasicGraveEntity.this.random.range(-1, 1);
								}
								if (BasicGraveEntity.this.isChallengeGrave()) {
									zombiePosZ = BasicGraveEntity.this.random.range(-3, 3);
									zombiePos = BasicGraveEntity.this.random.range(-3, 3);
								}
								BlockPos blockPos = BasicGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
								ImpEntity imp = (ImpEntity) PvZEntity.IMPTHROWER.create(BasicGraveEntity.this.getWorld());
								imp.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
								imp.initialize(serverWorld, BasicGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
								imp.setOwner(BasicGraveEntity.this);
								imp.defenseMultiplier = BasicGraveEntity.this.defenseMultiplier;
								serverWorld.spawnEntityAndPassengers(imp);
							}
							specialGraveWeight += 0.5;
						}
					}
				}
			}
			++this.basicGraveEntity.spawnCounter;
        }

        protected SoundEvent getSoundPrepare() {
            return PvZSounds.GRAVERISINGEVENT;
        }

        protected GraveEntity.Spell getSpell() {
            return GraveEntity.Spell.SUMMON_VEX;
        }
    }
}
