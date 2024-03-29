package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombiemachines.metallicvehicle.speakervehicle;

import com.jamieswhiteshirt.reachentityattributes.ReachEntityAttributes;
import io.github.GrassyDev.pvzmod.registry.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.PvZSounds;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.day.potatomine.PotatomineEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.night.gravebuster.GravebusterEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.spikeweed.SpikeweedEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.upgrades.spikerock.SpikerockEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.ancientegypt.iceberglettuce.IcebergLettuceEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.gemium.olivepit.OlivePitEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.pirateseas.springbean.SpringbeanEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.zombies.soundwave.SoundwaveEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.PvZombieAttackGoal;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2c.bass.BassZombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.GeneralPvZombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombieVehicleEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import java.util.List;

import static io.github.GrassyDev.pvzmod.PvZCubed.*;

public class SpeakerVehicleEntity extends ZombieVehicleEntity implements IAnimatable {
    private AnimationFactory factory = GeckoLibUtil.createFactory(this);
    private String controllerName = "shieldcontroller";

    public SpeakerVehicleEntity(EntityType<? extends SpeakerVehicleEntity> entityType, World world) {
        super(entityType, world);

        this.experiencePoints = 3;
	}

	private int launchTicks = 60;

	static {

	}

	protected void initDataTracker() {
		super.initDataTracker();
	}

	@Override
	public void writeCustomDataToNbt(NbtCompound tag) {
		super.writeCustomDataToNbt(tag);
	}

	public void readCustomDataFromNbt(NbtCompound tag) {
		super.readCustomDataFromNbt(tag);
	}

	@Environment(EnvType.CLIENT)
	public void handleStatus(byte status) {
		if (status != 2 && status != 60){
			super.handleStatus(status);
		}
		RandomGenerator randomGenerator = this.getRandom();
		if (status == 106) {
			for(int i = 0; i < 1; ++i) {
				double d = this.random.nextDouble() / 8 * this.random.range(-1, 1);
				double e = this.random.nextDouble() / 8 * this.random.range(0, 1);
				double f = this.random.nextDouble() / 8 * this.random.range(-1, 1);
				this.getWorld().addParticle(ParticleTypes.LARGE_SMOKE, this.getX() + (this.random.range(-1, 1)), this.getY() + (this.random.range(-1, 1)), this.getZ() + (this.random.range(-1, 1)), d, e, f);
				this.getWorld().addParticle(ParticleTypes.LARGE_SMOKE, this.getX() + (this.random.range(-1, 1)), this.getY() + (this.random.range(-1, 1)), this.getZ() + (this.random.range(-1, 1)), d, e, f);
				this.getWorld().addParticle(ParticleTypes.SMOKE, this.getX() + (this.random.range(-1, 1)), this.getY() + (this.random.range(-1, 1)), this.getZ() + (this.random.range(-1, 1)), d, e, f);
				this.getWorld().addParticle(ParticleTypes.FLAME, this.getX() + (this.random.range(-1, 1)), this.getY() + (this.random.range(-1, 1)), this.getZ() + (this.random.range(-1, 1)), d, e, f);
			}
			for(int i = 0; i < 1; ++i) {
				double e = this.random.nextDouble() / 2 * (this.random.range(0, 1));
				this.getWorld().addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, this.getX() + (double) MathHelper.nextBetween(randomGenerator, -0.5F, 0.5F),
						this.getY() + (this.random.range(-1, 1)),
						this.getZ()  + (double)MathHelper.nextBetween(randomGenerator,
								-0.5F, 0.5F), 0, e, 0);
			}
		}
	}


	/** /~*~//~*AI*~//~*~/ **/

	protected void initGoals() {
		this.goalSelector.add(1, new PvZombieAttackGoal(this, 0D, true));
	}

	/** /~*~//~*TICKING*~//~*~/ **/

	boolean beingEaten;

	@Override
	public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
		this.playSound(PvZSounds.SPEAKERCRASHEVENT);
		return super.handleFallDamage(fallDistance, damageMultiplier, damageSource);
	}

	public void tick() {
		super.tick();
		List<GravebusterEntity> list = world.getNonSpectatingEntities(GravebusterEntity.class, entityBox.getDimensions().getBoxAt(this.getX(), this.getY(), this.getZ()));
		this.beingEaten = !list.isEmpty();
		if (age <= 60) {
			this.setVelocity(0, -0.0125, 0);
		} else {
			this.setVelocity(0, -1, 0);
		}
		this.getNavigation().stop();
		if (this.hasStatusEffect(DISABLE)) {
			this.getWorld().sendEntityStatus(this, (byte) 106);
		}
		if (this.hasPassengers() && this.getFirstPassenger() instanceof GeneralPvZombieEntity generalPvZombieEntity && generalPvZombieEntity.getHypno()) {
			this.setHypno(IsHypno.TRUE);
		}
		if (!this.getHypno()) {
			if (this.CollidesWithPlant(0f, 0f) != null) {
				if (!(this.CollidesWithPlant(0f, 0f) instanceof PotatomineEntity ||
						this.CollidesWithPlant(0f, 0f) instanceof SpringbeanEntity ||
						this.CollidesWithPlant(0f, 0f) instanceof IcebergLettuceEntity ||
						this.CollidesWithPlant(0f, 0f) instanceof OlivePitEntity)) {
					if (this.CollidesWithPlant(0f, 0f) instanceof SpikerockEntity) {
						if (this.getType().equals(PvZEntity.TRASHCANBIN) && !this.hasVehicle()) {
							this.CollidesWithPlant(0f, 0f).damage(DamageSource.thrownProjectile(this, this), 90);
							this.kill();
						} else if (!(this.getType().equals(PvZEntity.TRASHCANBIN))) {
							this.CollidesWithPlant(0f, 0f).damage(DamageSource.thrownProjectile(this, this), 90);
							this.kill();
						}
					} else if (this.CollidesWithPlant(0f, 0f) instanceof SpikeweedEntity) {
						if (this.getType().equals(PvZEntity.TRASHCANBIN) && !this.hasVehicle()) {
							this.CollidesWithPlant(0f, 0f).kill();
							this.kill();
						} else if (!(this.getType().equals(PvZEntity.TRASHCANBIN))) {
							this.CollidesWithPlant(0f, 0f).kill();
							this.kill();
						}
					} else if (this.CollidesWithPlant(0f, 0f) != null && !(this.CollidesWithPlant(0f, 0f) instanceof GravebusterEntity)) {
						if (this.getType().equals(PvZEntity.TRASHCANBIN) && !this.hasVehicle()) {
							this.CollidesWithPlant(0f, 0f).kill();
						} else if (!(this.getType().equals(PvZEntity.TRASHCANBIN))) {
							this.CollidesWithPlant(0f, 0f).kill();
						}
					}
				}
			}
		}
		if (this.beingEaten){
			this.removeAllPassengers();
		}
		if (this.isOnGround() && this.getFirstPassenger() instanceof BassZombieEntity bassZombieEntity &&
				(!bassZombieEntity.hasStatusEffect(FROZEN) && !bassZombieEntity.hasStatusEffect(STUN) && !bassZombieEntity.hasStatusEffect(DISABLE)) &&
				!this.beingEaten &&
				(!this.hasStatusEffect(FROZEN) && !this.hasStatusEffect(STUN) && !this.hasStatusEffect(DISABLE))) {
			if (--this.launchTicks <= 0) {
				double time = 1;
				Vec3d noZombie = new Vec3d((double) +1, 0, 0).rotateY(-this.getHeadYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
				Vec3d predictedPos = new Vec3d(this.getX() + noZombie.x, this.getY() + noZombie.y, this.getZ() + noZombie.z);
				double d = this.squaredDistanceTo(predictedPos);
				float df = (float) d;
				double e = predictedPos.getX() - this.getX();
				double f = 0;
				double g = predictedPos.getZ() - this.getZ();
				float h = MathHelper.sqrt(MathHelper.sqrt(df)) * 0.5F;
				SoundwaveEntity proj = new SoundwaveEntity(PvZEntity.SOUNDWAVE, this.getWorld());
				proj.damageMultiplier = this.damageMultiplier;
				proj.setVelocity(e * (double) h, f * (double) h, g * (double) h, 0.85F, 0F);
				proj.updatePosition(this.getX(), this.getY() + 0.5D, this.getZ());
				proj.setOwner(this);
				this.launchTicks = 60;
				this.playSound(PvZSounds.BASSPLAYEVENT, 0.4F, 1f);
				this.getWorld().spawnEntity(proj);
			}
		}
		if (this.onGround) {
			this.setFlying(Flying.FALSE);
		} else {
			this.setFlying(Flying.TRUE);
		}
	}

	@Override
	public double getMountedHeightOffset() {
		return 1f;
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
		if (this.beingEaten){
			event.getController().setAnimation(new AnimationBuilder().loop("obstacle.eating"));
		}
		else if (this.isStunned || this.isFrozen) {
			event.getController().setAnimation(new AnimationBuilder().loop("gravestone.idle"));
		} else {
			event.getController().setAnimation(new AnimationBuilder().loop("speaker.idle"));
		}
		if (this.isIced) {
			event.getController().setAnimationSpeed(0.5);
		} else {
			event.getController().setAnimationSpeed(1);
		}
        return PlayState.CONTINUE;
    }


	/** /~*~//~*VARIANTS*~//~*~/ **/

	@Override
	public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
		createBassPassenger();
		return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
	}

	public void createBassPassenger() {
		if (world instanceof ServerWorld serverWorld) {
			BassZombieEntity zombie2 = new BassZombieEntity(PvZEntity.BASS, this.getWorld());
			zombie2.initialize(serverWorld, this.getWorld().getLocalDifficulty(this.getBlockPos()), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
			zombie2.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.bodyYaw, 0.0F);
			zombie2.startRiding(this);
		}
	}

	//////

	@Override
	public void onDeath(DamageSource source) {
		for (int x = 0; x <= 32; ++x) {
			this.getWorld().sendEntityStatus(this, (byte) 106);
		}
		this.playSound(PvZSounds.CHERRYBOMBEXPLOSIONEVENT, 1F, 1F);
		super.onDeath(source);
	}

	/** /~*~//~*ATTRIBUTES*~//~*~/ **/

	public boolean canWalkOnFluid(FluidState state) {
		return state.isIn(FluidTags.WATER);
	}

	protected boolean shouldSwimInFluids() {
		return true;
	}

	public static DefaultAttributeContainer.Builder createSpeakerVehicleAttributes() {
		return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 100.0D)
				.add(ReachEntityAttributes.ATTACK_RANGE, 1.5D)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.12D)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 0.0D)
				.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
				.add(EntityAttributes.GENERIC_MAX_HEALTH, PVZCONFIG.nestedZombieHealth.speakerVH());
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.ITEM_SHIELD_BREAK;
	}

	protected SoundEvent getAmbientSound() {
		return PvZSounds.SILENCEVENET;
	}

	public EntityGroup getGroup() {
		return EntityGroup.UNDEAD;
	}

	protected SoundEvent getStepSound() {
		return PvZSounds.SILENCEVENET;
	}

	@Nullable
	@Override
	public ItemStack getPickBlockStack() {
		return ModItems.BASSEGG.getDefaultStack();
	}
}
