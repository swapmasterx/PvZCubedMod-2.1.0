package io.github.GrassyDev.pvzmod.registry.entity.gravestones.egyptgravestone;

import com.jamieswhiteshirt.reachentityattributes.ReachEntityAttributes;
import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.PvZSounds;
import io.github.GrassyDev.pvzmod.registry.entity.gravestones.GraveEntity;
import io.github.GrassyDev.pvzmod.registry.entity.variants.graves.GraveDifficulty;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz1.browncoat.modernday.BrowncoatEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz1.gargantuar.modernday.GargantuarEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz1.imp.modernday.ImpEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2.browncoat.mummy.MummyEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2.explorer.ExplorerEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2.flagzombie.mummy.FlagMummyEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2.pharaoh.PharaohEntity;
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

public class EgyptGraveEntity extends GraveEntity implements GeoAnimatable {

	private String controllerName = "walkingcontroller";

	private int spawnCounter;



	double tiltchance = this.random.nextDouble();

    private AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

    public EgyptGraveEntity(EntityType<EgyptGraveEntity> entityType, World world) {
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
        this.goalSelector.add(1, new EgyptGraveEntity.summonZombieGoal(this));
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

	public static DefaultAttributeContainer.Builder createEgyptGraveAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 100.0D)
				.add(ReachEntityAttributes.ATTACK_RANGE, 1.5D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, PVZCONFIG.nestedZombieHealth.egyptGraveH());
    }

	protected SoundEvent getDeathSound() {
		return SoundEvents.BLOCK_ANCIENT_DEBRIS_BREAK;
	}

	protected SoundEvent getHurtSound(DamageSource source) {
		return SoundEvents.BLOCK_BASALT_HIT;
	}






	/** /~*~//~*SPAWNING*~//~*~/ **/

	public static boolean canEgyptGraveSpawn(EntityType<? extends EgyptGraveEntity> type, ServerWorldAccess world, SpawnReason spawnReason, BlockPos pos, RandomGenerator random) {
		BlockPos blockPos = pos.down();
		float cavespawn = random.nextFloat();
		if (cavespawn <= 0.66) {
			return world.getDifficulty() != Difficulty.PEACEFUL &&
					!world.getBlockState(pos).getMaterial().isLiquid() &&
					world.toServerWorld().getTime() > 48000  &&
					pos.getY() > 50 &&
					!world.getBlockState(blockPos).getBlock().hasDynamicBounds() &&
					!checkVillager(Vec3d.ofCenter(pos), world) &&
					!checkPlant(Vec3d.ofCenter(pos), world) && Objects.requireNonNull(world.getServer()).getGameRules().getBoolean(PvZCubed.SHOULD_GRAVE_SPAWN);
		}
		else {
			return world.getDifficulty() != Difficulty.PEACEFUL &&
					!world.getBlockState(pos).getMaterial().isLiquid() &&
					world.toServerWorld().getTime() > 48000 &&
					!world.getBlockState(blockPos).getBlock().hasDynamicBounds() &&
					!checkVillager(Vec3d.ofCenter(pos), world) &&
					!checkPlant(Vec3d.ofCenter(pos), world) && Objects.requireNonNull(world.getServer()).getGameRules().getBoolean(PvZCubed.SHOULD_GRAVE_SPAWN);
		}
	}

<<<<<<< Updated upstream
=======
	@Override
	public double getTick(Object object) {
		return 0;
	}

>>>>>>> Stashed changes

	/** /~*~//~*GOALS*~//~*~/ **/

	protected abstract class CastSpellGoal extends Goal {
		protected int spellCooldown;
		protected int startTime;

		protected CastSpellGoal() {
		}

		public boolean canStart() {
			LivingEntity livingEntity = EgyptGraveEntity.this.getTarget();
			if (livingEntity != null && livingEntity.isAlive()) {
				if (EgyptGraveEntity.this.isSpellcasting()) {
					return false;
				} else {
					return EgyptGraveEntity.this.age >= this.startTime;
				}
			} else {
				return false;
			}
		}

		public boolean shouldContinue() {
			LivingEntity livingEntity = EgyptGraveEntity.this.getTarget();
			return livingEntity != null && livingEntity.isAlive() && this.spellCooldown > 0;
		}

		public void start() {
			this.spellCooldown = this.getTickCount(this.getInitialCooldown());
			EgyptGraveEntity.this.spellTicks = this.getSpellTicks();
			this.startTime = EgyptGraveEntity.this.age + this.startTimeDelay();
			SoundEvent soundEvent = this.getSoundPrepare();
			if (soundEvent != null) {
				EgyptGraveEntity.this.playSound(soundEvent, 0.9F, 1.0F);
			}

			EgyptGraveEntity.this.setSpell(this.getSpell());
		}

		public void tick() {
			--this.spellCooldown;
			if (this.spellCooldown == 0) {
				this.castSpell();
				EgyptGraveEntity.this.addStatusEffect((new StatusEffectInstance(StatusEffects.GLOWING, 100, 1)));
				EgyptGraveEntity.this.playSound(EgyptGraveEntity.this.getCastSpellSound(), 0.9F, 1.0F);
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
		return ModItems.EGYPTGRAVESPAWN.getDefaultStack();
	}

	class summonZombieGoal extends EgyptGraveEntity.CastSpellGoal {
        private final TargetPredicate closeZombiePredicate;

		private final EgyptGraveEntity egyptGraveEntity;

		private summonZombieGoal(EgyptGraveEntity egyptGraveEntity) {
            super();
			this.egyptGraveEntity = egyptGraveEntity;
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
			graveWeight = 0;
            ServerWorld serverWorld = (ServerWorld) EgyptGraveEntity.this.getWorld();
			LocalDifficulty localDifficulty = world.getLocalDifficulty(this.egyptGraveEntity.getBlockPos());
			double difficulty = 0;
			if (this.egyptGraveEntity.getVariant().equals(GraveDifficulty.NONE)){
				difficulty = localDifficulty.getLocalDifficulty();
				if (difficulty >= 2.1){
					difficulty = 2.1;
					if (world.getDifficulty().equals(Difficulty.HARD)){
						difficulty = difficulty + difficultymodifier;
					}
				}
			}
			else if (this.egyptGraveEntity.getVariant().equals(GraveDifficulty.EASY)){
				difficulty = 1.0;
			}
			else if (this.egyptGraveEntity.getVariant().equals(GraveDifficulty.EASYMED)){
				difficulty = 1.5;
			}
			else if (this.egyptGraveEntity.getVariant().equals(GraveDifficulty.MED)){
				difficulty = 1.6;
			}
			else if (this.egyptGraveEntity.getVariant().equals(GraveDifficulty.MEDHARD)){
				difficulty = 1.8;
			}
			else if (this.egyptGraveEntity.getVariant().equals(GraveDifficulty.HARD)){
				difficulty = 2.1;
			}
			else if (this.egyptGraveEntity.getVariant().equals(GraveDifficulty.SUPERHARD)){
				difficulty = 3;
			}
			else if (this.egyptGraveEntity.getVariant().equals(GraveDifficulty.NIGHTMARE)){
				difficulty = 4;
			}
			else if (this.egyptGraveEntity.getVariant().equals(GraveDifficulty.CRAAAZY)){
				difficulty = 5;
			}
            double probability = random.nextDouble() * Math.pow(difficulty / 2, -1 * (difficulty / 2));
			double probability11 = random.nextDouble() * Math.pow(difficulty / 2, -1 * (difficulty / 2));
            double probability2 = random.nextDouble() * Math.pow(difficulty / 2, -1 * (difficulty / 2));
			double probability21 = random.nextDouble() * Math.pow(difficulty / 2, -1 * (difficulty / 2));
            double probability3 = random.nextDouble() * Math.pow(difficulty / 2, -1 * (difficulty / 2));
            double probability4 = random.nextDouble() * Math.pow(difficulty / 2, -1 * (difficulty / 2));
            double probability5 = random.nextDouble() * Math.pow(difficulty / 2, -1 * (difficulty / 2));
			double probability6 = random.nextDouble() * Math.pow(difficulty / 2, -1 * (difficulty / 2));
			double probability23 = random.nextDouble() * Math.pow(difficulty / 2, -1 * (difficulty / 2));
			double probability8 = random.nextDouble() * Math.pow(difficulty / 2, -1 * (difficulty / 2));
			double probability9 = random.nextDouble() * Math.pow(difficulty / 2, -1 * (difficulty / 2));
			double probability10 = random.nextDouble() * Math.pow(difficulty / 2, -1 * (difficulty / 2));
			double probability12 = random.nextDouble() * Math.pow(difficulty / 2, -1 * (difficulty / 2));
			double probability14 = random.nextDouble() * Math.pow(difficulty / 2, -1 * (difficulty / 2));
			double probability13 = random.nextDouble() * Math.pow(difficulty / 2, -1 * (difficulty / 2));
			double probability22 = random.nextDouble() * Math.pow(difficulty / 2, -1 * (difficulty / 2));
			double probability24 = random.nextDouble() * Math.pow(difficulty / 2, -1 * (difficulty / 2));
			double probability34 = random.nextDouble() * Math.pow(difficulty / 2, -1 * (difficulty / 2));
			double probability35 = random.nextDouble() * Math.pow(difficulty / 2, -1 * (difficulty / 2));

			int zombiePos = -2 + EgyptGraveEntity.this.random.nextInt(5);
			int zombiePosZ = -2 + EgyptGraveEntity.this.random.nextInt(5);
			if (EgyptGraveEntity.this.is1x1()){
				zombiePos = 0;
				zombiePosZ = 0;
			}

			for(int b = 0; b < 1; ++b) { // 100% x1 Browncoat
				if (!EgyptGraveEntity.this.is1x1()) {
					zombiePosZ = EgyptGraveEntity.this.random.range(-1, 1);
					zombiePos = EgyptGraveEntity.this.random.range(-1, 1);
				}
				if (EgyptGraveEntity.this.isChallengeGrave()) {
					zombiePosZ = EgyptGraveEntity.this.random.range(-3, 3);
					zombiePos = EgyptGraveEntity.this.random.range(-3, 3);
				}
				BlockPos blockPos = EgyptGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
				MummyEntity browncoatEntity = (MummyEntity)PvZEntity.MUMMY.create(EgyptGraveEntity.this.getWorld());
				browncoatEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
				browncoatEntity.initialize(serverWorld, EgyptGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData)null, (NbtCompound)null);
				browncoatEntity.setOwner(EgyptGraveEntity.this);
				browncoatEntity.defenseMultiplier = EgyptGraveEntity.this.defenseMultiplier;
					serverWorld.spawnEntityAndPassengers(browncoatEntity);
			}
			if (probability <= 0.25 / halfModifier * survChance) { // 25% x1 Conehead
				for (int c = 0; c < 1; ++c) {
					if (!EgyptGraveEntity.this.is1x1()) {
						zombiePosZ = EgyptGraveEntity.this.random.range(-1, 1);
						zombiePos = EgyptGraveEntity.this.random.range(-1, 1);
					}
					if (EgyptGraveEntity.this.isChallengeGrave()) {
						zombiePosZ = EgyptGraveEntity.this.random.range(-3, 3);
						zombiePos = EgyptGraveEntity.this.random.range(-3, 3);
					}
					BlockPos blockPos = EgyptGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
					MummyEntity coneheadEntity = (MummyEntity) PvZEntity.MUMMYCONE.create(EgyptGraveEntity.this.getWorld());
					coneheadEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
					coneheadEntity.initialize(serverWorld, EgyptGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
					coneheadEntity.setOwner(EgyptGraveEntity.this);
					coneheadEntity.defenseMultiplier = EgyptGraveEntity.this.defenseMultiplier;
					serverWorld.spawnEntityAndPassengers(coneheadEntity);
				}
				for(int b = 0; b < 1; ++b) { // 100% x1 Browncoat
					if (!EgyptGraveEntity.this.is1x1()) {
						zombiePosZ = EgyptGraveEntity.this.random.range(-1, 1);
						zombiePos = EgyptGraveEntity.this.random.range(-1, 1);
					}
					if (EgyptGraveEntity.this.isChallengeGrave()) {
						zombiePosZ = EgyptGraveEntity.this.random.range(-3, 3);
						zombiePos = EgyptGraveEntity.this.random.range(-3, 3);
					}
					BlockPos blockPos = EgyptGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
					MummyEntity browncoatEntity = (MummyEntity)PvZEntity.MUMMY.create(EgyptGraveEntity.this.getWorld());
					browncoatEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
					browncoatEntity.initialize(serverWorld, EgyptGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData)null, (NbtCompound)null);
					browncoatEntity.setOwner(EgyptGraveEntity.this);
					browncoatEntity.defenseMultiplier = EgyptGraveEntity.this.defenseMultiplier;
					serverWorld.spawnEntityAndPassengers(browncoatEntity);
				}
			}
			if (serverWorld.toServerWorld().getTime() > 24000) {
				if (probability2 <= 0.05 / halfModifier * survChance) { // 5% x1 Buckethead
					for (int u = 0; u < 1; ++u) {
						if (!EgyptGraveEntity.this.is1x1()) {
							zombiePosZ = EgyptGraveEntity.this.random.range(-1, 1);
							zombiePos = EgyptGraveEntity.this.random.range(-1, 1);
						}
						if (EgyptGraveEntity.this.isChallengeGrave()) {
							zombiePosZ = EgyptGraveEntity.this.random.range(-3, 3);
							zombiePos = EgyptGraveEntity.this.random.range(-3, 3);
						}
						BlockPos blockPos = EgyptGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
						MummyEntity bucketheadEntity = (MummyEntity) PvZEntity.MUMMYBUCKET.create(EgyptGraveEntity.this.getWorld());
						bucketheadEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
						bucketheadEntity.initialize(serverWorld, EgyptGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
						bucketheadEntity.setOwner(EgyptGraveEntity.this);
						bucketheadEntity.defenseMultiplier = EgyptGraveEntity.this.defenseMultiplier;
						serverWorld.spawnEntityAndPassengers(bucketheadEntity);
					}
					for (int c = 0; c < 2 / halfModifier; ++c) { // 100% x2 Conehead
						if (!EgyptGraveEntity.this.is1x1()) {
							zombiePosZ = EgyptGraveEntity.this.random.range(-1, 1);
							zombiePos = EgyptGraveEntity.this.random.range(-1, 1);
						}
						if (EgyptGraveEntity.this.isChallengeGrave()) {
							zombiePosZ = EgyptGraveEntity.this.random.range(-3, 3);
							zombiePos = EgyptGraveEntity.this.random.range(-3, 3);
						}
						BlockPos blockPos = EgyptGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
						MummyEntity coneheadEntity = (MummyEntity) PvZEntity.MUMMYCONE.create(EgyptGraveEntity.this.getWorld());
						coneheadEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
						coneheadEntity.initialize(serverWorld, EgyptGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
						coneheadEntity.setOwner(EgyptGraveEntity.this);
						coneheadEntity.defenseMultiplier = EgyptGraveEntity.this.defenseMultiplier;
					serverWorld.spawnEntityAndPassengers(coneheadEntity);
					}
				}
				if (difficulty >= 1.519 + difficultymodifier || isUnlock()) {
					if (probability11 <= 0.05 / halfModifier * survChance) { // 5% x2 Conehead
						for (int c = 0; c < 2 / halfModifier; ++c) {
							if (!EgyptGraveEntity.this.is1x1()) {
								zombiePosZ = EgyptGraveEntity.this.random.range(-1, 1);
								zombiePos = EgyptGraveEntity.this.random.range(-1, 1);
							}
							if (EgyptGraveEntity.this.isChallengeGrave()) {
								zombiePosZ = EgyptGraveEntity.this.random.range(-3, 3);
								zombiePos = EgyptGraveEntity.this.random.range(-3, 3);
							}
							BlockPos blockPos = EgyptGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							MummyEntity coneheadEntity = (MummyEntity) PvZEntity.MUMMYCONE.create(EgyptGraveEntity.this.getWorld());
							coneheadEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
							coneheadEntity.initialize(serverWorld, EgyptGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							coneheadEntity.setOwner(EgyptGraveEntity.this);
							coneheadEntity.defenseMultiplier = EgyptGraveEntity.this.defenseMultiplier;
					serverWorld.spawnEntityAndPassengers(coneheadEntity);
						}
						for(int b = 0; b < 2 / halfModifier; ++b) { // 100% x2 Browncoat
							if (!EgyptGraveEntity.this.is1x1()) {
								zombiePosZ = EgyptGraveEntity.this.random.range(-1, 1);
								zombiePos = EgyptGraveEntity.this.random.range(-1, 1);
							}
							if (EgyptGraveEntity.this.isChallengeGrave()) {
								zombiePosZ = EgyptGraveEntity.this.random.range(-3, 3);
								zombiePos = EgyptGraveEntity.this.random.range(-3, 3);
							}
							BlockPos blockPos = EgyptGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							MummyEntity browncoatEntity = (MummyEntity)PvZEntity.MUMMY.create(EgyptGraveEntity.this.getWorld());
							browncoatEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
							browncoatEntity.initialize(serverWorld, EgyptGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData)null, (NbtCompound)null);
							browncoatEntity.setOwner(EgyptGraveEntity.this);
							browncoatEntity.defenseMultiplier = EgyptGraveEntity.this.defenseMultiplier;
					serverWorld.spawnEntityAndPassengers(browncoatEntity);
						}
					}
				}
			}
			if (difficulty >= 1.599 + difficultymodifier || isUnlock()) {
				if (probability22 <= 0.15 / halfModifier * survChance) { // 15% x1 Buckethead
					for (int u = 0; u < 2 / halfModifier; ++u) {
						if (!EgyptGraveEntity.this.is1x1()) {
							zombiePosZ = EgyptGraveEntity.this.random.range(-1, 1);
							zombiePos = EgyptGraveEntity.this.random.range(-1, 1);
						}
						if (EgyptGraveEntity.this.isChallengeGrave()) {
							zombiePosZ = EgyptGraveEntity.this.random.range(-3, 3);
							zombiePos = EgyptGraveEntity.this.random.range(-3, 3);
						}
						BlockPos blockPos = EgyptGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
						MummyEntity bucketheadEntity = (MummyEntity) PvZEntity.MUMMYBUCKET.create(EgyptGraveEntity.this.getWorld());
						bucketheadEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
						bucketheadEntity.initialize(serverWorld, EgyptGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
						bucketheadEntity.setOwner(EgyptGraveEntity.this);
						bucketheadEntity.defenseMultiplier = EgyptGraveEntity.this.defenseMultiplier;
						serverWorld.spawnEntityAndPassengers(bucketheadEntity);
					}
					for (int b = 0; b < 1; ++b) { // 100% x1 Browncoat
						if (!EgyptGraveEntity.this.is1x1()) {
							zombiePosZ = EgyptGraveEntity.this.random.range(-1, 1);
							zombiePos = EgyptGraveEntity.this.random.range(-1, 1);
						}
						if (EgyptGraveEntity.this.isChallengeGrave()) {
							zombiePosZ = EgyptGraveEntity.this.random.range(-3, 3);
							zombiePos = EgyptGraveEntity.this.random.range(-3, 3);
						}
						BlockPos blockPos = EgyptGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
						MummyEntity browncoatEntity = (MummyEntity) PvZEntity.MUMMY.create(EgyptGraveEntity.this.getWorld());
						browncoatEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
						browncoatEntity.initialize(serverWorld, EgyptGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
						browncoatEntity.setOwner(EgyptGraveEntity.this);
						browncoatEntity.defenseMultiplier = EgyptGraveEntity.this.defenseMultiplier;
					serverWorld.spawnEntityAndPassengers(browncoatEntity);
					}
				}
			}
			if (serverWorld.toServerWorld().getTime() > 24000) {
				if (difficulty >= 1.599 + difficultymodifier || isUnlock()) {
					if (probability10 <= 0.3 / halfModifier * survChance) { // 5% x1 Pyramidhead Zombie
						for (int j = 0; j < 1; ++j) {
							if (!EgyptGraveEntity.this.is1x1()) {
								zombiePosZ = EgyptGraveEntity.this.random.range(-1, 1);
								zombiePos = EgyptGraveEntity.this.random.range(-1, 1);
							}
							if (EgyptGraveEntity.this.isChallengeGrave()) {
								zombiePosZ = EgyptGraveEntity.this.random.range(-3, 3);
								zombiePos = EgyptGraveEntity.this.random.range(-3, 3);
							}
							BlockPos blockPos = EgyptGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							MummyEntity brickhead = (MummyEntity) PvZEntity.PYRAMIDHEAD.create(EgyptGraveEntity.this.getWorld());
							brickhead.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
							brickhead.initialize(serverWorld, EgyptGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							brickhead.setOwner(EgyptGraveEntity.this);
							brickhead.defenseMultiplier = EgyptGraveEntity.this.defenseMultiplier;
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
								if (!EgyptGraveEntity.this.is1x1()) {
									zombiePosZ = EgyptGraveEntity.this.random.range(-1, 1);
									zombiePos = EgyptGraveEntity.this.random.range(-1, 1);
								}
								if (EgyptGraveEntity.this.isChallengeGrave()) {
									zombiePosZ = EgyptGraveEntity.this.random.range(-3, 3);
									zombiePos = EgyptGraveEntity.this.random.range(-3, 3);
								}
								BlockPos blockPos = EgyptGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
								FlagMummyEntity flagzombieEntity = (FlagMummyEntity) PvZEntity.FLAGMUMMY.create(EgyptGraveEntity.this.getWorld());
								flagzombieEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
								flagzombieEntity.initialize(serverWorld, EgyptGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
								flagzombieEntity.setOwner(EgyptGraveEntity.this);
								flagzombieEntity.defenseMultiplier = EgyptGraveEntity.this.defenseMultiplier;
								serverWorld.spawnEntityAndPassengers(flagzombieEntity);
							}
							extraGraveWeight += 1;
						}
					}
				}
				if (extraGraveWeight <= 2.5) {
					if (difficulty >= 1.549 + difficultymodifier || isUnlock()) {
						if (probability3 <= 0.15 / halfModifier * survChance) { // 15% x2 Explorer
							for (int p = 0; p < 2 / halfModifier; ++p) {
								if (!EgyptGraveEntity.this.is1x1()) {
									zombiePosZ = EgyptGraveEntity.this.random.range(-1, 1);
									zombiePos = EgyptGraveEntity.this.random.range(-1, 1);
								}
								if (EgyptGraveEntity.this.isChallengeGrave()) {
									zombiePosZ = EgyptGraveEntity.this.random.range(-3, 3);
									zombiePos = EgyptGraveEntity.this.random.range(-3, 3);
								}
								BlockPos blockPos = EgyptGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
								ExplorerEntity explorerEntity = (ExplorerEntity) PvZEntity.EXPLORER.create(EgyptGraveEntity.this.getWorld());
								explorerEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
								explorerEntity.initialize(serverWorld, EgyptGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
								explorerEntity.setOwner(EgyptGraveEntity.this);
								explorerEntity.defenseMultiplier = EgyptGraveEntity.this.defenseMultiplier;
								serverWorld.spawnEntityAndPassengers(explorerEntity);
							}
							for (int b = 0; b < 2 / halfModifier; ++b) { // 100% x2 Browncoat
								if (!EgyptGraveEntity.this.is1x1()) {
									zombiePosZ = EgyptGraveEntity.this.random.range(-1, 1);
									zombiePos = EgyptGraveEntity.this.random.range(-1, 1);
								}
								if (EgyptGraveEntity.this.isChallengeGrave()) {
									zombiePosZ = EgyptGraveEntity.this.random.range(-3, 3);
									zombiePos = EgyptGraveEntity.this.random.range(-3, 3);
								}
								BlockPos blockPos = EgyptGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
								MummyEntity browncoatEntity = (MummyEntity) PvZEntity.MUMMY.create(EgyptGraveEntity.this.getWorld());
								browncoatEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
								browncoatEntity.initialize(serverWorld, EgyptGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
								browncoatEntity.setOwner(EgyptGraveEntity.this);
								browncoatEntity.defenseMultiplier = EgyptGraveEntity.this.defenseMultiplier;
					serverWorld.spawnEntityAndPassengers(browncoatEntity);
							}
							extraGraveWeight += 1;
						}
					}
				}
				if (extraGraveWeight <= 2.5) {
					if ((difficulty >= 2.009 + difficultymodifier) && (isUnlock() || isUnlockSpecial())) {
						if (probability8 <= 0.15 / halfModifier * survChance) { // 15% x2 Pharaoh
							for (int g = 0; g < 2 / halfModifier; ++g) {
								if (!EgyptGraveEntity.this.is1x1()) {
									zombiePosZ = EgyptGraveEntity.this.random.range(-1, 1);
									zombiePos = EgyptGraveEntity.this.random.range(-1, 1);
								}
								if (EgyptGraveEntity.this.isChallengeGrave()) {
									zombiePosZ = EgyptGraveEntity.this.random.range(-3, 3);
									zombiePos = EgyptGraveEntity.this.random.range(-3, 3);
								}
								BlockPos blockPos = EgyptGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
								PharaohEntity pharaoh = (PharaohEntity) PvZEntity.PHARAOH.create(EgyptGraveEntity.this.getWorld());
								pharaoh.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
								pharaoh.initialize(serverWorld, EgyptGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
								pharaoh.setOwner(EgyptGraveEntity.this);
								pharaoh.defenseMultiplier = EgyptGraveEntity.this.defenseMultiplier;
								serverWorld.spawnEntityAndPassengers(pharaoh);
							}
							for (int b = 0; b < 4 / halfModifier; ++b) { // 100% x4 Browncoat
								if (!EgyptGraveEntity.this.is1x1()) {
									zombiePosZ = EgyptGraveEntity.this.random.range(-1, 1);
									zombiePos = EgyptGraveEntity.this.random.range(-1, 1);
								}
								if (EgyptGraveEntity.this.isChallengeGrave()) {
									zombiePosZ = EgyptGraveEntity.this.random.range(-3, 3);
									zombiePos = EgyptGraveEntity.this.random.range(-3, 3);
								}
								BlockPos blockPos = EgyptGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
								MummyEntity browncoatEntity = (MummyEntity) PvZEntity.MUMMY.create(EgyptGraveEntity.this.getWorld());
								browncoatEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
								browncoatEntity.initialize(serverWorld, EgyptGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
								browncoatEntity.setOwner(EgyptGraveEntity.this);
								browncoatEntity.defenseMultiplier = EgyptGraveEntity.this.defenseMultiplier;
					serverWorld.spawnEntityAndPassengers(browncoatEntity);
							}
							extraGraveWeight += 1.25;
						}
					}
				}
				if (extraGraveWeight <= 2.5) {
					if ((difficulty >= 2.009 + difficultymodifier) && (isUnlock() || isUnlockSpecial())) {
						if (probability6 <= 0.05 / halfModifier * survChance) { // 5% x3 Pyramidhead
							for (int j = 0; j < 3 / halfModifier; ++j) {
								if (!EgyptGraveEntity.this.is1x1()) {
									zombiePosZ = EgyptGraveEntity.this.random.range(-1, 1);
									zombiePos = EgyptGraveEntity.this.random.range(-1, 1);
								}
								if (EgyptGraveEntity.this.isChallengeGrave()) {
									zombiePosZ = EgyptGraveEntity.this.random.range(-3, 3);
									zombiePos = EgyptGraveEntity.this.random.range(-3, 3);
								}
								BlockPos blockPos = EgyptGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
								MummyEntity brickhead = (MummyEntity) PvZEntity.PYRAMIDHEAD.create(EgyptGraveEntity.this.getWorld());
								brickhead.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
								brickhead.initialize(serverWorld, EgyptGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
								brickhead.setOwner(EgyptGraveEntity.this);
								brickhead.defenseMultiplier = EgyptGraveEntity.this.defenseMultiplier;
							serverWorld.spawnEntityAndPassengers(brickhead);
							}
							for (int b = 0; b < 2 / halfModifier; ++b) { // 100% x2 Browncoat
								if (!EgyptGraveEntity.this.is1x1()) {
									zombiePosZ = EgyptGraveEntity.this.random.range(-1, 1);
									zombiePos = EgyptGraveEntity.this.random.range(-1, 1);
								}
								if (EgyptGraveEntity.this.isChallengeGrave()) {
									zombiePosZ = EgyptGraveEntity.this.random.range(-3, 3);
									zombiePos = EgyptGraveEntity.this.random.range(-3, 3);
								}
								BlockPos blockPos = EgyptGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
								MummyEntity browncoatEntity = (MummyEntity) PvZEntity.MUMMY.create(EgyptGraveEntity.this.getWorld());
								browncoatEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
								browncoatEntity.initialize(serverWorld, EgyptGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
								browncoatEntity.setOwner(EgyptGraveEntity.this);
								browncoatEntity.defenseMultiplier = EgyptGraveEntity.this.defenseMultiplier;
					serverWorld.spawnEntityAndPassengers(browncoatEntity);
							}
							for (int b = 0; b < 2 / halfModifier; ++b) { // 100% x2 Conehead
								if (!EgyptGraveEntity.this.is1x1()) {
									zombiePosZ = EgyptGraveEntity.this.random.range(-1, 1);
									zombiePos = EgyptGraveEntity.this.random.range(-1, 1);
								}
								if (EgyptGraveEntity.this.isChallengeGrave()) {
									zombiePosZ = EgyptGraveEntity.this.random.range(-3, 3);
									zombiePos = EgyptGraveEntity.this.random.range(-3, 3);
								}
								BlockPos blockPos = EgyptGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
								MummyEntity browncoatEntity = (MummyEntity) PvZEntity.MUMMYCONE.create(EgyptGraveEntity.this.getWorld());
								browncoatEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
								browncoatEntity.initialize(serverWorld, EgyptGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
								browncoatEntity.setOwner(EgyptGraveEntity.this);
								browncoatEntity.defenseMultiplier = EgyptGraveEntity.this.defenseMultiplier;
					serverWorld.spawnEntityAndPassengers(browncoatEntity);
							}
							for (int b = 0; b < 1; ++b) { // 100% x1 Buckethead
								if (!EgyptGraveEntity.this.is1x1()) {
									zombiePosZ = EgyptGraveEntity.this.random.range(-1, 1);
									zombiePos = EgyptGraveEntity.this.random.range(-1, 1);
								}
								if (EgyptGraveEntity.this.isChallengeGrave()) {
									zombiePosZ = EgyptGraveEntity.this.random.range(-3, 3);
									zombiePos = EgyptGraveEntity.this.random.range(-3, 3);
								}
								BlockPos blockPos = EgyptGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
								MummyEntity browncoatEntity = (MummyEntity) PvZEntity.MUMMYBUCKET.create(EgyptGraveEntity.this.getWorld());
								browncoatEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
								browncoatEntity.initialize(serverWorld, EgyptGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
								browncoatEntity.setOwner(EgyptGraveEntity.this);
								browncoatEntity.defenseMultiplier = EgyptGraveEntity.this.defenseMultiplier;
					serverWorld.spawnEntityAndPassengers(browncoatEntity);
							}
							extraGraveWeight += 1.75;
						}
					}
				}
				if (extraGraveWeight <= 2.5) {
					if (difficulty >= 1.549 + difficultymodifier || isUnlock()) {
						if (probability23 <= 0.15 / halfModifier * survChance) { // 15% x3 Mummy Imp
							for (int p = 0; p < 3 / halfModifier; ++p) {
								if (!EgyptGraveEntity.this.is1x1()) {
									zombiePosZ = EgyptGraveEntity.this.random.range(-1, 1);
									zombiePos = EgyptGraveEntity.this.random.range(-1, 1);
								}
								if (EgyptGraveEntity.this.isChallengeGrave()) {
									zombiePosZ = EgyptGraveEntity.this.random.range(-3, 3);
									zombiePos = EgyptGraveEntity.this.random.range(-3, 3);
								}
								BlockPos blockPos = EgyptGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
								ImpEntity imp = (ImpEntity) PvZEntity.MUMMYIMP.create(EgyptGraveEntity.this.getWorld());
								imp.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
								imp.initialize(serverWorld, EgyptGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
								imp.setOwner(EgyptGraveEntity.this);
								imp.defenseMultiplier = EgyptGraveEntity.this.defenseMultiplier;
								serverWorld.spawnEntityAndPassengers(imp);
							}
							for (int b = 0; b < 3 / halfModifier; ++b) { // 100% x3 Browncoat
								if (!EgyptGraveEntity.this.is1x1()) {
									zombiePosZ = EgyptGraveEntity.this.random.range(-1, 1);
									zombiePos = EgyptGraveEntity.this.random.range(-1, 1);
								}
								if (EgyptGraveEntity.this.isChallengeGrave()) {
									zombiePosZ = EgyptGraveEntity.this.random.range(-3, 3);
									zombiePos = EgyptGraveEntity.this.random.range(-3, 3);
								}
								BlockPos blockPos = EgyptGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
								BrowncoatEntity browncoatEntity = (BrowncoatEntity) PvZEntity.MUMMY.create(EgyptGraveEntity.this.getWorld());
								browncoatEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
								browncoatEntity.initialize(serverWorld, EgyptGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
								browncoatEntity.setOwner(EgyptGraveEntity.this);
								browncoatEntity.defenseMultiplier = EgyptGraveEntity.this.defenseMultiplier;
								serverWorld.spawnEntityAndPassengers(browncoatEntity);
							}
							extraGraveWeight += 0.75;
						}
					}
				}
				if (extraGraveWeight <= 2.5) {
					if (difficulty >= 1.599 + difficultymodifier || isUnlock()) {
						if (probability9 <= 0.2 / halfModifier * survChance) { // 20% x1 Pharaoh
							for (int g = 0; g < 1; ++g) {
								if (!EgyptGraveEntity.this.is1x1()) {
									zombiePosZ = EgyptGraveEntity.this.random.range(-1, 1);
									zombiePos = EgyptGraveEntity.this.random.range(-1, 1);
								}
								if (EgyptGraveEntity.this.isChallengeGrave()) {
									zombiePosZ = EgyptGraveEntity.this.random.range(-3, 3);
									zombiePos = EgyptGraveEntity.this.random.range(-3, 3);
								}
								BlockPos blockPos = EgyptGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
								PharaohEntity pharaoh = (PharaohEntity) PvZEntity.PHARAOH.create(EgyptGraveEntity.this.getWorld());
								pharaoh.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
								pharaoh.initialize(serverWorld, EgyptGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
								pharaoh.setOwner(EgyptGraveEntity.this);
								pharaoh.defenseMultiplier = EgyptGraveEntity.this.defenseMultiplier;
								serverWorld.spawnEntityAndPassengers(pharaoh);
							}
							for (int b = 0; b < 2 / halfModifier; ++b) { // 100% x2 Browncoat
								if (!EgyptGraveEntity.this.is1x1()) {
									zombiePosZ = EgyptGraveEntity.this.random.range(-1, 1);
									zombiePos = EgyptGraveEntity.this.random.range(-1, 1);
								}
								if (EgyptGraveEntity.this.isChallengeGrave()) {
									zombiePosZ = EgyptGraveEntity.this.random.range(-3, 3);
									zombiePos = EgyptGraveEntity.this.random.range(-3, 3);
								}
								BlockPos blockPos = EgyptGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
								MummyEntity browncoatEntity = (MummyEntity) PvZEntity.MUMMY.create(EgyptGraveEntity.this.getWorld());
								browncoatEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
								browncoatEntity.initialize(serverWorld, EgyptGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
								browncoatEntity.setOwner(EgyptGraveEntity.this);
								browncoatEntity.defenseMultiplier = EgyptGraveEntity.this.defenseMultiplier;
					serverWorld.spawnEntityAndPassengers(browncoatEntity);
							}
							extraGraveWeight += 0.75;
						}
					}
				}
				if (extraGraveWeight <= 2.5) {
					if (probability13 <= 0.3 / halfModifier * survChance) { // 30% x1 Explorer
						for (int p = 0; p < 1; ++p) {
							if (!EgyptGraveEntity.this.is1x1()) {
								zombiePosZ = EgyptGraveEntity.this.random.range(-1, 1);
								zombiePos = EgyptGraveEntity.this.random.range(-1, 1);
							}
							if (EgyptGraveEntity.this.isChallengeGrave()) {
								zombiePosZ = EgyptGraveEntity.this.random.range(-3, 3);
								zombiePos = EgyptGraveEntity.this.random.range(-3, 3);
							}
							BlockPos blockPos = EgyptGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
							ExplorerEntity explorerEntity = (ExplorerEntity) PvZEntity.EXPLORER.create(EgyptGraveEntity.this.getWorld());
							explorerEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
							explorerEntity.initialize(serverWorld, EgyptGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
							explorerEntity.setOwner(EgyptGraveEntity.this);
							explorerEntity.defenseMultiplier = EgyptGraveEntity.this.defenseMultiplier;
								serverWorld.spawnEntityAndPassengers(explorerEntity);
						}
						extraGraveWeight += 0.5;
					}
				}
				if (extraGraveWeight <= 2.5) {
					if (probability21 <= 0.25 / halfModifier * survChance) {
						if (difficulty >= 1.599 + difficultymodifier || isUnlock()) { // 25% x1 Pyramidhead
							for (int j = 0; j < 1; ++j) {
								if (!EgyptGraveEntity.this.is1x1()) {
									zombiePosZ = EgyptGraveEntity.this.random.range(-1, 1);
									zombiePos = EgyptGraveEntity.this.random.range(-1, 1);
								}
								if (EgyptGraveEntity.this.isChallengeGrave()) {
									zombiePosZ = EgyptGraveEntity.this.random.range(-3, 3);
									zombiePos = EgyptGraveEntity.this.random.range(-3, 3);
								}
								BlockPos blockPos = EgyptGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
								MummyEntity brickhead = (MummyEntity) PvZEntity.PYRAMIDHEAD.create(EgyptGraveEntity.this.getWorld());
								brickhead.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
								brickhead.initialize(serverWorld, EgyptGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
								brickhead.setOwner(EgyptGraveEntity.this);
								brickhead.defenseMultiplier = EgyptGraveEntity.this.defenseMultiplier;
							serverWorld.spawnEntityAndPassengers(brickhead);
							}
							for (int b = 0; b < 1; ++b) { // 100% x1 Buckethead
								if (!EgyptGraveEntity.this.is1x1()) {
									zombiePosZ = EgyptGraveEntity.this.random.range(-1, 1);
									zombiePos = EgyptGraveEntity.this.random.range(-1, 1);
								}
								if (EgyptGraveEntity.this.isChallengeGrave()) {
									zombiePosZ = EgyptGraveEntity.this.random.range(-3, 3);
									zombiePos = EgyptGraveEntity.this.random.range(-3, 3);
								}
								BlockPos blockPos = EgyptGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
								MummyEntity browncoatEntity = (MummyEntity) PvZEntity.MUMMYBUCKET.create(EgyptGraveEntity.this.getWorld());
								browncoatEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
								browncoatEntity.initialize(serverWorld, EgyptGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
								browncoatEntity.setOwner(EgyptGraveEntity.this);
								browncoatEntity.defenseMultiplier = EgyptGraveEntity.this.defenseMultiplier;
					serverWorld.spawnEntityAndPassengers(browncoatEntity);
							}
							extraGraveWeight += 0.75;
						}
					}
				}
				if (specialGraveWeight <= 3) {
					if (difficulty >= 2.009 + difficultymodifier || isUnlock()) {
						if (probability24 <= 0.1 / halfModifier * survChance) { // 10% x1 Mummified Gargantuar
							for (int j = 0; j < 1; ++j) {
								if (!EgyptGraveEntity.this.is1x1()) {
									zombiePosZ = EgyptGraveEntity.this.random.range(-1, 1);
									zombiePos = EgyptGraveEntity.this.random.range(-1, 1);
								}
								if (EgyptGraveEntity.this.isChallengeGrave()) {
									zombiePosZ = EgyptGraveEntity.this.random.range(-3, 3);
									zombiePos = EgyptGraveEntity.this.random.range(-3, 3);
								}
								BlockPos blockPos = EgyptGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
								GargantuarEntity mummyGarg = (GargantuarEntity) PvZEntity.MUMMYGARGANTUAR.create(EgyptGraveEntity.this.getWorld());
								mummyGarg.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
								mummyGarg.initialize(serverWorld, EgyptGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
								mummyGarg.setOwner(EgyptGraveEntity.this);
								mummyGarg.defenseMultiplier = EgyptGraveEntity.this.defenseMultiplier;
								serverWorld.spawnEntityAndPassengers(mummyGarg);
							}
							for (int p = 0; p < 2 / halfModifier; ++p) { // 100% x2 Mummy Imp
								if (!EgyptGraveEntity.this.is1x1()) {
									zombiePosZ = EgyptGraveEntity.this.random.range(-1, 1);
									zombiePos = EgyptGraveEntity.this.random.range(-1, 1);
								}
								if (EgyptGraveEntity.this.isChallengeGrave()) {
									zombiePosZ = EgyptGraveEntity.this.random.range(-3, 3);
									zombiePos = EgyptGraveEntity.this.random.range(-3, 3);
								}
								BlockPos blockPos = EgyptGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
								ImpEntity imp = (ImpEntity) PvZEntity.MUMMYIMP.create(EgyptGraveEntity.this.getWorld());
								imp.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
								imp.initialize(serverWorld, EgyptGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
								imp.setOwner(EgyptGraveEntity.this);
								imp.defenseMultiplier = EgyptGraveEntity.this.defenseMultiplier;
								serverWorld.spawnEntityAndPassengers(imp);
							}
							double random = Math.random();
							if (random <= 0.33){
								for (int j = 0; j < 1; ++j) { // 50% x1 Mummified Gargantuar
									if (!EgyptGraveEntity.this.is1x1()) {
										zombiePosZ = EgyptGraveEntity.this.random.range(-1, 1);
										zombiePos = EgyptGraveEntity.this.random.range(-1, 1);
									}
									if (EgyptGraveEntity.this.isChallengeGrave()) {
										zombiePosZ = EgyptGraveEntity.this.random.range(-3, 3);
										zombiePos = EgyptGraveEntity.this.random.range(-3, 3);
									}
									BlockPos blockPos = EgyptGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
									GargantuarEntity mummyGarg = (GargantuarEntity) PvZEntity.MUMMYGARGANTUAR.create(EgyptGraveEntity.this.getWorld());
									mummyGarg.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
									mummyGarg.initialize(serverWorld, EgyptGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
									mummyGarg.setOwner(EgyptGraveEntity.this);
									mummyGarg.defenseMultiplier = EgyptGraveEntity.this.defenseMultiplier;
									serverWorld.spawnEntityAndPassengers(mummyGarg);
								}
							}
							specialGraveWeight += 1.25;
						}
					}
				}
				if (specialGraveWeight <= 3) {
					if (difficulty >= 2.009 + difficultymodifier || isUnlock()) {
						if (probability12 <= 0.10 / halfModifier * survChance) { // 15% x1 Undying Pharaoh
							for (int p = 0; p < 1; ++p) {
								if (!EgyptGraveEntity.this.is1x1()) {
									zombiePosZ = EgyptGraveEntity.this.random.range(-1, 1);
									zombiePos = EgyptGraveEntity.this.random.range(-1, 1);
								}
								if (EgyptGraveEntity.this.isChallengeGrave()) {
									zombiePosZ = EgyptGraveEntity.this.random.range(-3, 3);
									zombiePos = EgyptGraveEntity.this.random.range(-3, 3);
								}
								BlockPos blockPos = EgyptGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
								PharaohEntity undying = (PharaohEntity) PvZEntity.UNDYINGPHARAOH.create(EgyptGraveEntity.this.getWorld());
								undying.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
								undying.initialize(serverWorld, EgyptGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
								undying.setOwner(EgyptGraveEntity.this);
								undying.defenseMultiplier = EgyptGraveEntity.this.defenseMultiplier;
								serverWorld.spawnEntityAndPassengers(undying);
							}
							for (int b = 0; b < 3 / halfModifier; ++b) { // 100% x3 Conehead
								if (!EgyptGraveEntity.this.is1x1()) {
									zombiePosZ = EgyptGraveEntity.this.random.range(-1, 1);
									zombiePos = EgyptGraveEntity.this.random.range(-1, 1);
								}
								if (EgyptGraveEntity.this.isChallengeGrave()) {
									zombiePosZ = EgyptGraveEntity.this.random.range(-3, 3);
									zombiePos = EgyptGraveEntity.this.random.range(-3, 3);
								}
								BlockPos blockPos = EgyptGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
								MummyEntity browncoatEntity = (MummyEntity) PvZEntity.MUMMYCONE.create(EgyptGraveEntity.this.getWorld());
								browncoatEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
								browncoatEntity.initialize(serverWorld, EgyptGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
								browncoatEntity.setOwner(EgyptGraveEntity.this);
								browncoatEntity.defenseMultiplier = EgyptGraveEntity.this.defenseMultiplier;
					serverWorld.spawnEntityAndPassengers(browncoatEntity);
							}
							for (int h = 0; h < 2 / halfModifier; ++h) { // 100% x2 Buckethead
								if (!EgyptGraveEntity.this.is1x1()) {
									zombiePosZ = EgyptGraveEntity.this.random.range(-1, 1);
									zombiePos = EgyptGraveEntity.this.random.range(-1, 1);
								}
								if (EgyptGraveEntity.this.isChallengeGrave()) {
									zombiePosZ = EgyptGraveEntity.this.random.range(-3, 3);
									zombiePos = EgyptGraveEntity.this.random.range(-3, 3);
								}
								BlockPos blockPos = EgyptGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
								MummyEntity browncoatEntity = (MummyEntity) PvZEntity.MUMMYBUCKET.create(EgyptGraveEntity.this.getWorld());
								browncoatEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
								browncoatEntity.initialize(serverWorld, EgyptGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
								browncoatEntity.setOwner(EgyptGraveEntity.this);
								browncoatEntity.defenseMultiplier = EgyptGraveEntity.this.defenseMultiplier;
					serverWorld.spawnEntityAndPassengers(browncoatEntity);
							}
							specialGraveWeight += 1.5;
						}
					}
				}
				if (specialGraveWeight <= 3) {
					if ((difficulty >= 2.009 + difficultymodifier) && (isUnlock() || isUnlockSpecial())) {
						if (probability14 <= 0.15 / halfModifier * survChance) { // 15% x2 Torchlight
							for (int p = 0; p < 2 / halfModifier; ++p) {
								if (!EgyptGraveEntity.this.is1x1()) {
									zombiePosZ = EgyptGraveEntity.this.random.range(-1, 1);
									zombiePos = EgyptGraveEntity.this.random.range(-1, 1);
								}
								if (EgyptGraveEntity.this.isChallengeGrave()) {
									zombiePosZ = EgyptGraveEntity.this.random.range(-3, 3);
									zombiePos = EgyptGraveEntity.this.random.range(-3, 3);
								}
								BlockPos blockPos = EgyptGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
								ExplorerEntity torchlight = (ExplorerEntity) PvZEntity.TORCHLIGHT.create(EgyptGraveEntity.this.getWorld());
								torchlight.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
								torchlight.initialize(serverWorld, EgyptGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
								torchlight.setOwner(EgyptGraveEntity.this);
								torchlight.defenseMultiplier = EgyptGraveEntity.this.defenseMultiplier;
								serverWorld.spawnEntityAndPassengers(torchlight);
							}
							for (int b = 0; b < 1; ++b) { // 100% x1 Conehead
								if (!EgyptGraveEntity.this.is1x1()) {
									zombiePosZ = EgyptGraveEntity.this.random.range(-1, 1);
									zombiePos = EgyptGraveEntity.this.random.range(-1, 1);
								}
								if (EgyptGraveEntity.this.isChallengeGrave()) {
									zombiePosZ = EgyptGraveEntity.this.random.range(-3, 3);
									zombiePos = EgyptGraveEntity.this.random.range(-3, 3);
								}
								BlockPos blockPos = EgyptGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
								MummyEntity browncoatEntity = (MummyEntity) PvZEntity.MUMMYCONE.create(EgyptGraveEntity.this.getWorld());
								browncoatEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
								browncoatEntity.initialize(serverWorld, EgyptGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
								browncoatEntity.setOwner(EgyptGraveEntity.this);
								browncoatEntity.defenseMultiplier = EgyptGraveEntity.this.defenseMultiplier;
					serverWorld.spawnEntityAndPassengers(browncoatEntity);
							}
							for (int b = 0; b < 1; ++b) { // 100% x1 Buckethead
								if (!EgyptGraveEntity.this.is1x1()) {
									zombiePosZ = EgyptGraveEntity.this.random.range(-1, 1);
									zombiePos = EgyptGraveEntity.this.random.range(-1, 1);
								}
								if (EgyptGraveEntity.this.isChallengeGrave()) {
									zombiePosZ = EgyptGraveEntity.this.random.range(-3, 3);
									zombiePos = EgyptGraveEntity.this.random.range(-3, 3);
								}
								BlockPos blockPos = EgyptGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
								MummyEntity browncoatEntity = (MummyEntity) PvZEntity.MUMMYBUCKET.create(EgyptGraveEntity.this.getWorld());
								browncoatEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
								browncoatEntity.initialize(serverWorld, EgyptGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
								browncoatEntity.setOwner(EgyptGraveEntity.this);
								browncoatEntity.defenseMultiplier = EgyptGraveEntity.this.defenseMultiplier;
					serverWorld.spawnEntityAndPassengers(browncoatEntity);
							}
							specialGraveWeight += 1.25;
						}
					}
				}
				if (specialGraveWeight <= 3) {
					if ((difficulty >= 2.009 + difficultymodifier) && (isUnlock() || isUnlockSpecial())) {
						if (probability34 <= 0.20 / halfModifier * survChance) { // 20% x2 Tomb Raiser
							for (int p = 0; p < 2 / halfModifier; ++p) {
								if (!EgyptGraveEntity.this.is1x1()) {
									zombiePosZ = EgyptGraveEntity.this.random.range(-1, 1);
									zombiePos = EgyptGraveEntity.this.random.range(-1, 1);
								}
								if (EgyptGraveEntity.this.isChallengeGrave()) {
									zombiePosZ = EgyptGraveEntity.this.random.range(-3, 3);
									zombiePos = EgyptGraveEntity.this.random.range(-3, 3);
								}
								BlockPos blockPos = EgyptGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
								MummyEntity tombraiser = (MummyEntity) PvZEntity.TOMBRAISER.create(EgyptGraveEntity.this.getWorld());
								tombraiser.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
								tombraiser.initialize(serverWorld, EgyptGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
								tombraiser.setOwner(EgyptGraveEntity.this);
								tombraiser.defenseMultiplier = EgyptGraveEntity.this.defenseMultiplier;
								serverWorld.spawnEntityAndPassengers(tombraiser);
							}
							for (int b = 0; b < 2 / halfModifier; ++b) { // 100% x2 Conehead
								if (!EgyptGraveEntity.this.is1x1()) {
									zombiePosZ = EgyptGraveEntity.this.random.range(-1, 1);
									zombiePos = EgyptGraveEntity.this.random.range(-1, 1);
								}
								if (EgyptGraveEntity.this.isChallengeGrave()) {
									zombiePosZ = EgyptGraveEntity.this.random.range(-3, 3);
									zombiePos = EgyptGraveEntity.this.random.range(-3, 3);
								}
								BlockPos blockPos = EgyptGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
								MummyEntity browncoatEntity = (MummyEntity) PvZEntity.MUMMYCONE.create(EgyptGraveEntity.this.getWorld());
								browncoatEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
								browncoatEntity.initialize(serverWorld, EgyptGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
								browncoatEntity.setOwner(EgyptGraveEntity.this);
								browncoatEntity.defenseMultiplier = EgyptGraveEntity.this.defenseMultiplier;
								serverWorld.spawnEntityAndPassengers(browncoatEntity);
							}
							specialGraveWeight += 1.75;
						}
					}
				}
				if (specialGraveWeight <= 3) {
					if ((difficulty >= 1.599 + difficultymodifier) && (isUnlock() || isUnlockSpecial())) {
						if (probability5 <= 0.15 / halfModifier * survChance) { // 20% x1 Torchlight
							for (int p = 0; p < 1; ++p) {
								if (!EgyptGraveEntity.this.is1x1()) {
									zombiePosZ = EgyptGraveEntity.this.random.range(-1, 1);
									zombiePos = EgyptGraveEntity.this.random.range(-1, 1);
								}
								if (EgyptGraveEntity.this.isChallengeGrave()) {
									zombiePosZ = EgyptGraveEntity.this.random.range(-3, 3);
									zombiePos = EgyptGraveEntity.this.random.range(-3, 3);
								}
								BlockPos blockPos = EgyptGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
								ExplorerEntity torchlight = (ExplorerEntity) PvZEntity.TORCHLIGHT.create(EgyptGraveEntity.this.getWorld());
								torchlight.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
								torchlight.initialize(serverWorld, EgyptGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
								torchlight.setOwner(EgyptGraveEntity.this);
								torchlight.defenseMultiplier = EgyptGraveEntity.this.defenseMultiplier;
								serverWorld.spawnEntityAndPassengers(torchlight);
							}
							for (int b = 0; b < 1; ++b) { // 100% x1 Conehead
								if (!EgyptGraveEntity.this.is1x1()) {
									zombiePosZ = EgyptGraveEntity.this.random.range(-1, 1);
									zombiePos = EgyptGraveEntity.this.random.range(-1, 1);
								}
								if (EgyptGraveEntity.this.isChallengeGrave()) {
									zombiePosZ = EgyptGraveEntity.this.random.range(-3, 3);
									zombiePos = EgyptGraveEntity.this.random.range(-3, 3);
								}
								BlockPos blockPos = EgyptGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
								MummyEntity browncoatEntity = (MummyEntity) PvZEntity.MUMMYCONE.create(EgyptGraveEntity.this.getWorld());
								browncoatEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
								browncoatEntity.initialize(serverWorld, EgyptGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
								browncoatEntity.setOwner(EgyptGraveEntity.this);
								browncoatEntity.defenseMultiplier = EgyptGraveEntity.this.defenseMultiplier;
					serverWorld.spawnEntityAndPassengers(browncoatEntity);
							}
							specialGraveWeight += 0.5;
						}
					}
				}
				if (specialGraveWeight <= 3) {
					if ((difficulty >= 2.009 + difficultymodifier) && (isUnlock() || isUnlockSpecial())) {
						if (probability34 <= 0.20 / halfModifier * survChance) { // 20% x1 Tomb Raiser
							for (int p = 0; p < 1; ++p) {
								if (!EgyptGraveEntity.this.is1x1()) {
									zombiePosZ = EgyptGraveEntity.this.random.range(-1, 1);
									zombiePos = EgyptGraveEntity.this.random.range(-1, 1);
								}
								if (EgyptGraveEntity.this.isChallengeGrave()) {
									zombiePosZ = EgyptGraveEntity.this.random.range(-3, 3);
									zombiePos = EgyptGraveEntity.this.random.range(-3, 3);
								}
								BlockPos blockPos = EgyptGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
								MummyEntity tombraiser = (MummyEntity) PvZEntity.TOMBRAISER.create(EgyptGraveEntity.this.getWorld());
								tombraiser.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
								tombraiser.initialize(serverWorld, EgyptGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
								tombraiser.setOwner(EgyptGraveEntity.this);
								tombraiser.defenseMultiplier = EgyptGraveEntity.this.defenseMultiplier;
								serverWorld.spawnEntityAndPassengers(tombraiser);
							}
							for (int b = 0; b < 1; ++b) { // 100% x1 Conehead
								if (!EgyptGraveEntity.this.is1x1()) {
									zombiePosZ = EgyptGraveEntity.this.random.range(-1, 1);
									zombiePos = EgyptGraveEntity.this.random.range(-1, 1);
								}
								if (EgyptGraveEntity.this.isChallengeGrave()) {
									zombiePosZ = EgyptGraveEntity.this.random.range(-3, 3);
									zombiePos = EgyptGraveEntity.this.random.range(-3, 3);
								}
								BlockPos blockPos = EgyptGraveEntity.this.getBlockPos().add(zombiePos, 0.1, zombiePosZ);
								MummyEntity browncoatEntity = (MummyEntity) PvZEntity.MUMMYCONE.create(EgyptGraveEntity.this.getWorld());
								browncoatEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
								browncoatEntity.initialize(serverWorld, EgyptGraveEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
								browncoatEntity.setOwner(EgyptGraveEntity.this);
								browncoatEntity.defenseMultiplier = EgyptGraveEntity.this.defenseMultiplier;
								serverWorld.spawnEntityAndPassengers(browncoatEntity);
							}
							specialGraveWeight += 1;
						}
					}
				}
			}
			++this.egyptGraveEntity.spawnCounter;
        }

        protected SoundEvent getSoundPrepare() {
            return PvZSounds.GRAVERISINGEVENT;
        }

        protected Spell getSpell() {
            return Spell.SUMMON_VEX;
        }
    }
}
