package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.oc.pumpkincar;

import io.github.GrassyDev.pvzmod.items.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.sound.PvZSounds;
import io.github.GrassyDev.pvzmod.registry.entity.plants.miscentity.garden.GardenEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.miscentity.gardenchallenge.GardenChallengeEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.PlantEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.day.sunflower.SunflowerEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.night.sunshroom.SunshroomEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.spikeweed.SpikeweedEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.upgrades.spikerock.SpikerockEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.upgrades.twinsunflower.TwinSunflowerEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.PvZProjectileEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.PvZombieAttackGoal;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz1.imp.modernday.ImpEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombieVehicleEntity;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.goal.TargetGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.registry.tag.FluidTags;;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
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


import java.util.List;

import static io.github.GrassyDev.pvzmod.PvZCubed.PLANT_LOCATION;
import static io.github.GrassyDev.pvzmod.PvZCubed.PVZCONFIG;

public class PumpkinCarEntity extends ZombieVehicleEntity implements GeoEntity {

    private AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);
    private String controllerName = "walkingcontroller";

	public PumpkinCarEntity(EntityType<? extends PumpkinCarEntity> entityType, World world) {
        super(entityType, world);

        this.experiencePoints = 3;
		this.setCoveredTag(Covered.TRUE);
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
		if (!(event.getLimbSwingAmount() > -0.01F && event.getLimbSwingAmount() < 0.01F)) {
			event.getController().setAnimation(RawAnimation.begin().thenLoop("pumpkincar.walking"));
		} else {
			event.getController().setAnimation(RawAnimation.begin().thenLoop("pumpkincar.idle"));
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
		initCustomGoals();
    }

    protected void initCustomGoals() {

		this.goalSelector.add(8, new LookAroundGoal(this));
		this.targetSelector.add(6, new RevengeGoal(this, new Class[0]));
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

	@Override
	public boolean tryAttack(Entity target) {
		return false;
	}

	/** /~*~//~*TICKING*~//~*~/ **/

	public void tick() {
		super.tick();
		for (float x = -1; x <= 1; ++x) {
			Vec3d vec3d = new Vec3d((double) x , 0.0, 0.0).rotateY(-this.getYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
			List<PvZProjectileEntity> list = getWorld().getNonSpectatingEntities(PvZProjectileEntity.class, entityBox.getDimensions().getBoxAt(this.getX() + vec3d.x, this.getY(), this.getZ() + vec3d.z));
			for (PvZProjectileEntity projectileEntity : list) {
				projectileEntity.moreEntities.add(this);
				projectileEntity.hitEntities();
			}
			if (this.CollidesWithPlant(x, 0f) != null) {
				if (this.CollidesWithPlant(x, 0f) instanceof SpikerockEntity) {
					this.CollidesWithPlant(x, 0f).damage(getDamageSources().mobProjectile(this, this), 90);
					this.kill();
				} else if (this.CollidesWithPlant(x, 0f) instanceof SpikeweedEntity) {
					this.CollidesWithPlant(x, 0f).kill();
					this.kill();
				} else if (this.CollidesWithPlant(x, 0f) != null) {
					this.CollidesWithPlant(x, 0f).kill();
				}
			}
		}
	}

	/** /~*~//~*INTERACTION*~//~*~/ **/

	@Nullable
	@Override
	public ItemStack getPickBlockStack() {
		return ModItems.BROWNCOATEGG.getDefaultStack();
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

	public static DefaultAttributeContainer.Builder createPumpkinCarAttributes() {
		return HostileEntity.createAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 100.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.16D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, PVZCONFIG.nestedZombieHealth.pumpkincarH());
    }

	protected SoundEvent getAmbientSound() {
		return null;
	}

	public EntityGroup getGroup() {
		return EntityGroup.UNDEAD;
	}

	protected SoundEvent getStepSound() {
		return PvZSounds.SILENCEVENET;
	}
	protected void playStepSound(BlockPos pos, BlockState state) {
		this.playSound(this.getStepSound(), 0.15F, 1.0F);
	}



	/** /~*~//~*DAMAGE HANDLER*~//~*~/ **/

	@Override
	public void onDeath(DamageSource source) {
		if (this.getWorld() instanceof ServerWorld serverWorld) {
			BlockPos blockPos = this.getBlockPos().add((int) this.getX(), 0, (int) this.getZ());
			ImpEntity zombie = (ImpEntity) PvZEntity.CINDERELLAIMP.create(getWorld());
			zombie.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), 0, 0);
			zombie.initialize(serverWorld, getWorld().getLocalDifficulty(blockPos), SpawnReason.SPAWN_EGG, (EntityData) null, (NbtCompound) null);
			zombie.setOwner(this);
			zombie.setRainbowTag(Rainbow.TRUE);
			zombie.rainbowTicks = 60;
			serverWorld.spawnEntityAndPassengers(zombie);
		}
	}
}
