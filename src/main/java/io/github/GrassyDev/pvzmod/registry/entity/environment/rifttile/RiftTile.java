package io.github.GrassyDev.pvzmod.registry.entity.environment.rifttile;

import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.entity.environment.TileEntity;
import io.github.GrassyDev.pvzmod.registry.entity.variants.graves.RiftVariants;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz1.browncoat.modernday.BrowncoatEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2c.bass.BassZombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombiemachines.metallicvehicle.speakervehicle.SpeakerVehicleEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieprops.rockobstacle.RockObstacleEntity;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
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

public class RiftTile extends TileEntity {

	private static final TrackedData<Integer> DATA_ID_TYPE_VARIANT =
			DataTracker.registerData(RiftTile.class, TrackedDataHandlerRegistry.INTEGER);

	public RiftTile(EntityType<? extends TileEntity> entityType, World world) {
		super(entityType, world);
	}

	@Override
	public void tick() {
		super.tick();
		if (this.age >= 2400){
			this.discard();
		}
		addCount();
		if (this.getVariant().equals(RiftVariants.BASS)){
			if (this.getTypeCount() >= 300) {
				this.createBass();
				this.discard();
			}
		}
		else if (this.getVariant().equals(RiftVariants.GARGOLITH)){
			if (this.getTypeCount() >= 300) {
				this.createGargolith();
				this.discard();
			}
		}
		else if (this.getTypeCount() >= 75){
			this.createBrowncoat();
			this.discard();
		}
	}

	public void createBass(){
		if (world instanceof ServerWorld serverWorld) {
			SpeakerVehicleEntity zombie = new SpeakerVehicleEntity(PvZEntity.SPEAKER, this.getWorld());
			zombie.refreshPositionAndAngles(this.getX(), this.getY() + 3, this.getZ(), this.getYaw(), 0.0F);
			serverWorld.spawnEntityAndPassengers(zombie);
			BassZombieEntity zombie2 = new BassZombieEntity(PvZEntity.BASS, this.getWorld());
			zombie2.initialize(serverWorld, this.getWorld().getLocalDifficulty(this.getBlockPos()), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
			zombie2.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.bodyYaw, 0.0F);
			zombie2.startRiding(zombie);
			serverWorld.spawnEntityAndPassengers(zombie2);
		}
	}

	public void createGargolith(){
		if (world instanceof ServerWorld serverWorld) {
			RockObstacleEntity zombie = new RockObstacleEntity(PvZEntity.GARGOLITHOBSTACLE, this.getWorld());
			zombie.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), 0.0F);
			zombie.initialize(serverWorld, this.getWorld().getLocalDifficulty(this.getBlockPos()), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
			serverWorld.spawnEntityAndPassengers(zombie);
		}
	}

	public void createBrowncoat(){
		if (world instanceof ServerWorld serverWorld) {
			BrowncoatEntity zombie = new BrowncoatEntity(PvZEntity.BROWNCOAT, this.getWorld());
			zombie.initialize(serverWorld, this.getWorld().getLocalDifficulty(this.getBlockPos()), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
			zombie.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), 0.0F);
			serverWorld.spawnEntityAndPassengers(zombie);
		}
	}

	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(DATA_ID_TYPE_VARIANT, 0);
		this.dataTracker.startTracking(SPAWNTIME, 0);
	}
	public void readCustomDataFromNbt(NbtCompound tag) {
		super.readCustomDataFromNbt(tag);
		//Variant//
		this.dataTracker.set(DATA_ID_TYPE_VARIANT, tag.getInt("Variant"));
		this.dataTracker.set(SPAWNTIME, tag.getInt("Count"));
	}

	public void writeCustomDataToNbt(NbtCompound tag) {
		super.writeCustomDataToNbt(tag);
		//Variant//
		tag.putInt("Variant", this.getTypeVariant());
		tag.putInt("Count", this.getTypeCount());
	}


	/** /~*~//~*VARIANTS~//~*~// **/

	public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty,
								 SpawnReason spawnReason, @Nullable EntityData entityData,
								 @Nullable NbtCompound entityNbt) {
		return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
	}

	private int getTypeVariant() {
		return this.dataTracker.get(DATA_ID_TYPE_VARIANT);
	}

	public RiftVariants getVariant() {
		return RiftVariants.byId(this.getTypeVariant() & 255);
	}

	public void setVariant(RiftVariants variant) {
		this.dataTracker.set(DATA_ID_TYPE_VARIANT, variant.getId() & 255);
	}

	//Counter

	private static final TrackedData<Integer> SPAWNTIME =
			DataTracker.registerData(RiftTile.class, TrackedDataHandlerRegistry.INTEGER);

	public int getTypeCount() {
		return this.dataTracker.get(SPAWNTIME);
	}

	public void setCount(Integer count) {
		this.dataTracker.set(SPAWNTIME, count);
	}

	public void addCount(){
		int count = getTypeCount();
		this.dataTracker.set(SPAWNTIME, count + 1);
	}
}
