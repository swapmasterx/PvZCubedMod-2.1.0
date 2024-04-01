package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvzbfn.actionhero;


import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.PvZSounds;
import io.github.GrassyDev.pvzmod.registry.entity.plants.miscentity.gardenchallenge.GardenChallengeEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.night.gravebuster.GravebusterEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.lilypad.LilyPadEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.zombies.rocket.RocketEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.oc.bully.basic.BullyEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
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

import java.nio.charset.StandardCharsets;
import java.util.UUID;

import static io.github.GrassyDev.pvzmod.PvZCubed.*;

public class ActionheroEntity extends BullyEntity implements GeoAnimatable {


    private AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);
    private String controllerName = "walkingcontroller";

	public boolean speedSwitch;
	private boolean riding = false;
	private int rocketTicks = 5;
	private int ridingTicks = 0;
	public static final UUID MAX_SPEED_UUID = UUID.nameUUIDFromBytes(MOD_ID.getBytes(StandardCharsets.UTF_8));

	public ActionheroEntity(EntityType<? extends ActionheroEntity> entityType, World world) {
        super(entityType, world);

        this.experiencePoints = 3;
		this.getNavigation().setCanSwim(true);
		this.speedSwitch = false;
		this.setPathfindingPenalty(PathNodeType.WATER_BORDER, 0.0F);
		this.setPathfindingPenalty(PathNodeType.WATER, 0.0F);
		this.setPathfindingPenalty(PathNodeType.LAVA, -1.0F);
		this.setPathfindingPenalty(PathNodeType.DAMAGE_OTHER, 8.0F);
		this.setPathfindingPenalty(PathNodeType.POWDER_SNOW, 8.0F);
		this.setPathfindingPenalty(PathNodeType.DAMAGE_FIRE, 0.0F);
		this.setPathfindingPenalty(PathNodeType.DANGER_FIRE, 0.0F);
	}

	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(DATA_ID_TYPE_COUNT, true);
	}

	@Override
	public void writeCustomDataToNbt(NbtCompound tag) {
		super.writeCustomDataToNbt(tag);
		tag.putBoolean("Pole", this.getPoleStage());
	}

	public void readCustomDataFromNbt(NbtCompound tag) {
		super.readCustomDataFromNbt(tag);
		this.dataTracker.set(DATA_ID_TYPE_COUNT, tag.getBoolean("Pole"));
	}

	@Override
	public void handleStatus(byte status) {
		if (status != 2 && status != 60) {
			super.handleStatus(status);
		}
		if (status == 101) {
			this.riding = true;
		}
		if (status == 102) {
			this.riding = false;
		}
	}

	@Override
	public void setHypno(IsHypno hypno) {
		super.setHypno(hypno);
	}


	/** /~*~//~*VARIANTS*~//~*~/ **/

	private static final TrackedData<Boolean> DATA_ID_TYPE_COUNT =
			DataTracker.registerData(ActionheroEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

	@Override
	public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
		this.setPoleStage(PoleStage.POLE);
		return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
	}

	public enum PoleStage {
		POLE(true),
		NOPOLE	(false);

		PoleStage(boolean id) {
			this.id = id;
		}

		private final boolean id;

		public boolean getId() {
			return this.id;
		}
	}

	private Boolean getPoleStage() {
		return this.dataTracker.get(DATA_ID_TYPE_COUNT);
	}

	private void setPoleStage(PoleStage poleStage) {
		this.dataTracker.set(DATA_ID_TYPE_COUNT, poleStage.getId());
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
		Entity entity = this.getFirstPassenger();
		if (this.riding){
			event.getController().setAnimation(RawAnimation.begin().thenLoop("bully.rocketride"));
		}
		else if (this.isInsideWaterOrBubbleColumn()) {
			if (this.getPoleStage()) {
				event.getController().setAnimation(RawAnimation.begin().thenLoop("bully.ducky.run"));
			}
			else {
				event.getController().setAnimation(RawAnimation.begin().thenLoop("bully.ducky"));
			}
			if (this.isIced) {
				event.getController().setAnimationSpeed(0.5);
			}
			else {
				event.getController().setAnimationSpeed(1);
			}
		} else {
			if (!(event.getLimbSwingAmount() > -0.01F && event.getLimbSwingAmount() < 0.01F)) {
				if (this.getPoleStage()){
					event.getController().setAnimation(RawAnimation.begin().thenLoop("bully.run"));
				}
				else {
					event.getController().setAnimation(RawAnimation.begin().thenLoop("bully.walk"));
				}
			} else {
				event.getController().setAnimation(RawAnimation.begin().thenLoop("bully.idle"));
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
		}
		return PlayState.CONTINUE;
	}


	/** /~*~//~*TICKING*~//~*~/ **/

	public void tick() {
		super.tick();
		if (this.getAttacking() == null && !(this.getHypno())){
			if (this.CollidesWithPlant(0.1f, 0f) != null && !this.hasStatusEffect(PvZCubed.BOUNCED) && !(this.CollidesWithPlant(0.1f, 0f) instanceof LilyPadEntity) && this.getPoleStage() && !this.isFlying()){
				this.riding = true;
				this.getWorld().sendEntityStatus(this, (byte) 101);
				this.ridingTicks = 160;
				this.setFlying(Flying.TRUE);
				this.setPoleStage(PoleStage.NOPOLE);
				this.playSound(PvZSounds.ROCKETRIDEJINGLEEVENT, 0.75f, 1);
			}
			if (this.CollidesWithPlant(0.1f, 0f) instanceof GardenChallengeEntity){
				this.setTarget(CollidesWithPlant(0.1f, 0f));
				this.setStealthTag(Stealth.FALSE);
			}
			else if (this.CollidesWithPlant(0.1f, 0f) != null && this.riding && !this.hasStatusEffect(PvZCubed.BOUNCED) && (PLANT_LOCATION.get(this.CollidesWithPlant(0.1f, 0f).getType()).orElse("normal").equals("maintarget") ||
					PLANT_LOCATION.get(this.CollidesWithPlant(0.1f, 0f).getType()).orElse("normal").equals("tall") || PLANT_LOCATION.get(this.CollidesWithPlant(0.1f, 0f).getType()).orElse("normal").equals("flying"))) {
				this.setVelocity(0, -0.3, 0);
				this.getNavigation().stop();
				this.setTarget(CollidesWithPlant(0.1f, 0f));
				this.setStealthTag(Stealth.FALSE);
			}
			else if (this.CollidesWithPlant(0.1f, 0f) != null && !this.riding && !this.hasStatusEffect(PvZCubed.BOUNCED) && !(this.CollidesWithPlant(0.1f, 0f) instanceof GravebusterEntity)){
				this.setVelocity(0, -0.3, 0);
				this.getNavigation().stop();
				this.setTarget(CollidesWithPlant(0.1f, 0f));
				this.setStealthTag(Stealth.FALSE);
			}
			else if (this.CollidesWithPlayer(1.5f) != null && !this.CollidesWithPlayer(1.5f).isCreative()){
				this.setTarget(CollidesWithPlayer(1.5f));
				this.setStealthTag(Stealth.FALSE);
			}
		}
		if (!this.getWorld().isClient() && --ridingTicks <= 0){
			this.riding = false;
			this.setFlying(Flying.FALSE);
			this.getWorld().sendEntityStatus(this, (byte) 102);
		}
		if (!this.getWorld().isClient() && this.riding && this.isAlive()){
			if (--rocketTicks <= 0){
				Vec3d vec3d2 = new Vec3d((double) 0.2, 0.0, 0).rotateY(-this.getHeadYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
				rocketTicks = 10;
				RocketEntity proj = new RocketEntity(PvZEntity.ROCKETPROJ, this.getWorld());
				proj.damageMultiplier = this.damageMultiplier;
				proj.setVelocity(0, -0.1, 0, 0F, 0F);
				proj.updatePosition(this.getX() + vec3d2.x, this.getY() + 2D, this.getZ() + vec3d2.z);
				proj.setOwner(this);
				this.playSound(PvZSounds.ZPGAMBIENTEVENT, 1F, 1);
				this.getWorld().spawnEntity(proj);
			}
		}
	}

	/** /~*~//~*INTERACTION*~//~*~/ **/

	@Nullable
	@Override
	public ItemStack getPickBlockStack() {
		ItemStack itemStack;
		itemStack = ModItems.ACTIONHEROEGG.getDefaultStack();
		return itemStack;
	}


	/** /~*~//~*ATTRIBUTES*~//~*~/ **/

	public static DefaultAttributeContainer.Builder createActionheroAttributes() {
        return HostileEntity.createAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 100.0D)

                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.16D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 8.0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, PVZCONFIG.nestedZombieHealth.actionheroH());
    }
}
