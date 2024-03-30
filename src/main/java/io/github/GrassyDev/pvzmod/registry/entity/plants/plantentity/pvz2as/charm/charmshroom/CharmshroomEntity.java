package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2as.charm.charmshroom;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.PvZSounds;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import io.github.GrassyDev.pvzmod.registry.entity.environment.shadowtile.ShadowFullTile;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.PlantEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.GeneralPvZombieEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.*;
import net.minecraft.world.biome.BiomeKeys;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.util.GeckoLibUtil;


import java.util.EnumSet;
import java.util.List;
import java.util.Optional;

import static io.github.GrassyDev.pvzmod.PvZCubed.PVZCONFIG;
import static io.github.GrassyDev.pvzmod.PvZCubed.ZOMBIE_SIZE;

public class CharmshroomEntity extends PlantEntity implements GeoAnimatable, RangedAttackMob {

    private AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);
    private String controllerName = "charmcontroller";


    private static final TrackedData<Integer> HYPNO_BEAM_TARGET_ID;
    private LivingEntity cachedBeamTarget;
    private int beamTicks;

    public CharmshroomEntity(EntityType<? extends CharmshroomEntity> entityType, World world) {
        super(entityType, world);

		this.targetStrength = true;
		this.targetNotCovered = true;
		this.isBurst = true;
		this.nocturnal = true;
    }

	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(DATA_ID_TYPE_COUNT, 0);
		this.dataTracker.startTracking(HYPNO_BEAM_TARGET_ID, 0);
	}

	@Override
	public void writeCustomDataToNbt(NbtCompound tag) {
		super.writeCustomDataToNbt(tag);
		tag.putInt("Count", this.getTypeCount());
	}

	public void onTrackedDataSet(TrackedData<?> data) {
		if (HYPNO_BEAM_TARGET_ID.equals(data)) {
			this.beamTicks = 0;
			this.cachedBeamTarget = null;
		}

		super.onTrackedDataSet(data);
	}

	public void readCustomDataFromNbt(NbtCompound tag) {
		super.readCustomDataFromNbt(tag);
		this.dataTracker.set(DATA_ID_TYPE_COUNT, tag.getInt("Count"));
	}

	static {
		HYPNO_BEAM_TARGET_ID = DataTracker.registerData(CharmshroomEntity.class, TrackedDataHandlerRegistry.INTEGER);
	}

	@Environment(EnvType.CLIENT)
	public void handleStatus(byte status) {
		if (status != 2 && status != 60){
			super.handleStatus(status);
		}
	}

	/** /~*~//~*VARIANTS*~//~*~/ **/

	//Charm Counter

	private static final TrackedData<Integer> DATA_ID_TYPE_COUNT =
			DataTracker.registerData(CharmshroomEntity.class, TrackedDataHandlerRegistry.INTEGER);

	public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty,
								 SpawnReason spawnReason, @Nullable EntityData entityData,
								 @Nullable NbtCompound entityNbt) {
		//Set Count
		setCount(0);
		return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
	}

	private int getTypeCount() {
		return this.dataTracker.get(DATA_ID_TYPE_COUNT);
	}

	private void setCount(Integer count) {
		this.dataTracker.set(DATA_ID_TYPE_COUNT, count);
	}

	private void addCount(){
		int count = getTypeCount();
		this.dataTracker.set(DATA_ID_TYPE_COUNT, count + 1);
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
        if (this.getIsAsleep()) {
            event.getController().setAnimation(new RawAnimation().loop("charmshroom.idle.asleep"));
        }
        else {
            event.getController().setAnimation(new RawAnimation().loop("charmshroom.idle"));
        }
        return PlayState.CONTINUE;
    }


	/** /~*~//~*AI*~//~*~/ **/

	protected void initGoals() {
		this.goalSelector.add(1, new CharmshroomEntity.FireBeamGoal(this));
	}

	@Override
	public void attack(LivingEntity target, float pullProgress) {
	}


	/** /~*~//~*POSITION*~//~*~/ **/

	public void setPosition(double x, double y, double z) {
		BlockPos blockPos = this.getBlockPos();
		if (this.hasVehicle()) {
			super.setPosition(x, y, z);
		} else {
			super.setPosition((double) MathHelper.floor(x) + 0.5, (double)MathHelper.floor(y + 0.5), (double)MathHelper.floor(z) + 0.5);
		}
	}


	/** /~*~//~*TICKING*~//~*~/ **/

	public void tick() {
		if (this.getWorld() instanceof ServerWorld serverWorld) {
			Vec3d vec3d = Vec3d.ofCenter(this.getBlockPos()).add(0, -0.5, 0);
			List<ShadowFullTile> fullCheck = world.getNonSpectatingEntities(ShadowFullTile.class, PvZEntity.PEASHOOTER.getDimensions().getBoxAt(vec3d.getX(), vec3d.getY(), vec3d.getZ()));
			if (fullCheck.isEmpty()) {
				if (this.getWorld().getMoonSize() > 0.9 && this.getWorld().isSkyVisible(this.getBlockPos())) {
					if (serverWorld.isNight()) {
						this.setMoonPowered(Moon.TRUE);
					}
				} else {
					this.setMoonPowered(Moon.FALSE);
				}
			}
			if (!fullCheck.isEmpty()) {
				this.setMoonPowered(Moon.TRUE);
			}
		}
		if (this.getMoonPowered()){
			if (this.getTypeCount() >= 4) {
				this.remove(RemovalReason.DISCARDED);
			}
		}
		else {
			if (this.getTypeCount() >= 2) {
				this.remove(RemovalReason.DISCARDED);
			}
		}
		if (!this.getWorld().isClient && !this.getCofee()) {
			if ((this.getWorld().getAmbientDarkness() >= 2 ||
					this.getWorld().getLightLevel(LightType.SKY, this.getBlockPos()) < 2 ||
					this.getWorld().getBiome(this.getBlockPos()).getKey().equals(Optional.ofNullable(BiomeKeys.MUSHROOM_FIELDS)))) {
				this.setIsAsleep(IsAsleep.FALSE);
			} else if (this.getWorld().getAmbientDarkness() < 2 &&
					this.getWorld().getLightLevel(LightType.SKY, this.getBlockPos()) >= 2 &&
					!this.getWorld().getBiome(this.getBlockPos()).getKey().equals(Optional.ofNullable(BiomeKeys.MUSHROOM_FIELDS))) {
				this.setIsAsleep(IsAsleep.TRUE);
			}
		}
		if (this.getIsAsleep()){
			this.setTarget(null);
		}
		else {
			this.targetZombies(this.getPos(), 5, true, true, true);
		}
		super.tick();
		BlockPos blockPos = this.getBlockPos();
		if (tickDelay <= 1) {
			BlockPos blockPos2 = this.getBlockPos();
			BlockState blockState = this.getLandingBlockState();
			if ((!blockPos2.equals(blockPos) || !blockState.hasSolidTopSurface(world, this.getBlockPos(), this)) && !this.hasVehicle()) {
				if (!this.getWorld().isClient && this.getWorld().getGameRules().getBoolean(GameRules.DO_MOB_LOOT) && !this.naturalSpawn && this.age <= 10 && !this.dead){
					this.dropItem(ModItems.CHARMSHROOM_SEED_PACKET);
				}
				this.discard();
			}
		}
	}

	public void tickMovement() {
		super.tickMovement();
		if (!this.getWorld().isClient && this.isAlive() && this.isInsideWaterOrBubbleColumn() && this.deathTime == 0) {
			this.discard();
		}

		if (this.hasBeamTarget()) {
			if (this.beamTicks < this.getWarmupTime()) {
				++this.beamTicks;
			}

			LivingEntity livingEntity = this.getBeamTarget();
			if (livingEntity != null) {
				this.getLookControl().lookAt(livingEntity, 90.0F, 90.0F);
				this.getLookControl().tick();
				double d = (double)this.getBeamProgress(0.0F);
				double e = livingEntity.getX() - this.getX();
				double f = livingEntity.getBodyY(0.5D) - this.getEyeY();
				double g = livingEntity.getZ() - this.getZ();
				double h = Math.sqrt(e * e + f * f + g * g);
				e /= h;
				f /= h;
				g /= h;
				double j = this.random.nextDouble();

				while(j < h) {
					j += 1.8D - d + this.random.nextDouble() * (1.7D - d);
					double rd = (double)(this.random.range(160, 255) & 255) / 255.0;
					double gr = (double) (170 & 255) / 255.0;
					double bl = (double)(this.random.range(200, 255) & 255) / 255.0;
					this.getWorld().addParticle(ParticleTypes.ENTITY_EFFECT, this.getX() + e * j, this.getEyeY() + f * j, this.getZ() + g * j, rd, gr, bl);
				}
			}
		}
	}


	/** /~*~//~*INTERACTION*~//~*~/ **/

	public ActionResult interactMob(PlayerEntity player, Hand hand) {
		ItemStack itemStack = player.getStackInHand(hand);
		if (itemStack.isOf(ModItems.GARDENINGGLOVE)) {
			dropItem(ModItems.CHARMSHROOM_SEED_PACKET);
			if (!player.getAbilities().creativeMode) {
				if (!PVZCONFIG.nestedSeeds.infiniteSeeds() && !world.getGameRules().getBoolean(PvZCubed.INFINITE_SEEDS)) {
					itemStack.decrement(1);
				}
			}
			this.discard();
			return ActionResult.SUCCESS;
		}
		return super.interactMob(player, hand);
	}

	@Nullable
	@Override
	public ItemStack getPickBlockStack() {
		return ModItems.CHARMSHROOM_SEED_PACKET.getDefaultStack();
	}


	/** /~*~//~*ATTRIBUTES*~//~*~/ **/

	public static DefaultAttributeContainer.Builder createCharmshroomAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 12.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 10D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 1D);
    }

	protected boolean canClimb() {
		return false;
	}

	public boolean collides() {
		return true;
	}

	protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
		return 0.5F;
	}

	@Nullable
	protected SoundEvent getHurtSound(DamageSource source) {
		return PvZSounds.SILENCEVENET;
	}

	@Nullable
	protected SoundEvent getDeathSound() {
		return PvZSounds.PLANTPLANTEDEVENT;
	}

	public boolean hurtByWater() {
		return false;
	}

	public boolean isPushable() {
		return false;
	}

	protected void pushAway(Entity entity) {
	}

	public boolean startRiding(Entity entity, boolean force) {
		return super.startRiding(entity, force);
	}

	public void stopRiding() {
		super.stopRiding();
		this.prevBodyYaw = 0.0F;
		this.bodyYaw = 0.0F;
	}


	/** /~*~//~*DAMAGE HANDLER*~//~*~/ **/



	public boolean handleFallDamage(float fallDistance, float damageMultiplier) {
		if (fallDistance > 0F) {
			this.playSound(PvZSounds.PLANTPLANTEDEVENT, 0.4F, 1.0F);
			this.discard();
		}
		this.playBlockFallSound();
		return true;
	}


	/** /~*~//~*GOALS*~//~*~/ **/

	public int getWarmupTime() {
		return 20;
	}

	private void setHypnoBeamTarget(int entityId) {
		this.dataTracker.set(HYPNO_BEAM_TARGET_ID, entityId);
	}

	public boolean hasBeamTarget() {
		return (Integer)this.dataTracker.get(HYPNO_BEAM_TARGET_ID) != 0;
	}

	@Nullable
	public LivingEntity getBeamTarget() {
		if (!this.hasBeamTarget()) {
			return null;
		} else if (this.getWorld().isClient) {
			if (this.cachedBeamTarget != null) {
				return this.cachedBeamTarget;
			} else {
				Entity entity = this.getWorld().getEntityById((Integer)this.dataTracker.get(HYPNO_BEAM_TARGET_ID));
				if (entity instanceof LivingEntity) {
					this.cachedBeamTarget = (LivingEntity)entity;
					return this.cachedBeamTarget;
				} else {
					return null;
				}
			}
		} else {
			return this.getTarget();
		}
	}

	public float getBeamProgress(float tickDelta) {
		return ((float)this.beamTicks + tickDelta) / (float)this.getWarmupTime();
	}

    static class FireBeamGoal extends Goal {
        private final CharmshroomEntity plantEntity;
        private int beamTicks;

        public FireBeamGoal(CharmshroomEntity plantEntity) {
            this.plantEntity = plantEntity;
            this.setControls(EnumSet.of(Control.MOVE, Control.LOOK));
        }

        public boolean canStart() {
            LivingEntity livingEntity = this.plantEntity.getTarget();
            return livingEntity != null && livingEntity.isAlive();
        }

        public boolean shouldContinue() {
            return super.shouldContinue() && (this.plantEntity.squaredDistanceTo(this.plantEntity.getTarget()) > 9.0D);
        }

        public void start() {
            this.beamTicks = -10;
            this.plantEntity.getNavigation().stop();
            this.plantEntity.getLookControl().lookAt(this.plantEntity.getTarget(), 90.0F, 90.0F);
            this.plantEntity.velocityDirty = true;
        }

        public void stop() {
            this.plantEntity.setHypnoBeamTarget(0);
            this.plantEntity.setTarget((LivingEntity)null);
        }

        public void tick() {
            LivingEntity livingEntity = this.plantEntity.getTarget();
            this.plantEntity.getNavigation().stop();
            this.plantEntity.getLookControl().lookAt(livingEntity, 90.0F, 90.0F);
            if (!this.plantEntity.canSee(livingEntity) || this.plantEntity.getIsAsleep()) {
                this.plantEntity.setTarget((LivingEntity) null);
            } else {
                ++this.beamTicks;
                if (this.beamTicks == 0) {
					if (plantEntity.getTarget() != null) {
						this.plantEntity.setHypnoBeamTarget(this.plantEntity.getTarget().getId());
					}
                } else if (this.beamTicks >= this.plantEntity.getWarmupTime()) {
					if (livingEntity != null && livingEntity.isAlive()) {
						if (livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && !generalPvZombieEntity.isCovered()) {
							livingEntity.damage(PvZCubed.HYPNO_DAMAGE, 0);
						}
						this.plantEntity.setTarget((LivingEntity) null);
						if (ZOMBIE_SIZE.get(livingEntity.getType()).orElse("medium").equals("gargantuar") && !this.plantEntity.getMoonPowered()){
							this.plantEntity.setCount(2);
						}
						else {
							this.plantEntity.addCount();
						}
						this.beamTicks = -10;
						this.stop();
					}
                }
                super.tick();
            }
        }
    }
}
