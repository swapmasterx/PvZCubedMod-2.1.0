package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieprops.rockobstacle;

import com.jamieswhiteshirt.reachentityattributes.ReachEntityAttributes;
import io.github.GrassyDev.pvzmod.registry.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.PvZSounds;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.night.gravebuster.GravebusterEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz1.gargantuar.modernday.GargantuarEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz1.imp.modernday.ImpEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2.browncoat.mummy.MummyEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.GeneralPvZombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombieObstacleEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
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

import static io.github.GrassyDev.pvzmod.PvZCubed.PVZCONFIG;

public class RockObstacleEntity extends ZombieObstacleEntity implements GeoAnimatable {
    private AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);
    private String controllerName = "shieldcontroller";

    public RockObstacleEntity(EntityType<? extends RockObstacleEntity> entityType, World world) {
        super(entityType, world);

        this.experiencePoints = 27;
	}


	static {

	}

	@Environment(EnvType.CLIENT)
	public void handleStatus(byte status) {
		if (status != 2 && status != 60){
			super.handleStatus(status);
		}
	}

	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(SUMMON_TIMES, 0);
	}

	public void writeCustomDataToNbt(NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);
		nbt.putInt("Count", this.getTypeCount());
	}

	public void readCustomDataFromNbt(NbtCompound nbt) {
		super.readCustomDataFromNbt(nbt);
		this.dataTracker.set(SUMMON_TIMES, nbt.getInt("Count"));
	}

	//Charm Counter

	private static final TrackedData<Integer> SUMMON_TIMES =
			DataTracker.registerData(RockObstacleEntity.class, TrackedDataHandlerRegistry.INTEGER);

	public int getTypeCount() {
		return this.dataTracker.get(SUMMON_TIMES);
	}

	public void setCount(Integer count) {
		this.dataTracker.set(SUMMON_TIMES, count);
	}

	public void addCount(){
		int count = getTypeCount();
		this.dataTracker.set(SUMMON_TIMES, count + 1);
	}

	/** /~*~//~*TICKING*~//~*~/ **/

	private int spawnTicks = 80;

	public void tick() {
		super.tick();
		if (this.hasVehicle() && this.getVehicle() instanceof GeneralPvZombieEntity generalPvZombieEntity && (generalPvZombieEntity.getHealth() <= 0 || generalPvZombieEntity.isDead())){
			this.dismountVehicle();
		}
		if (!this.getHypno()) {
			if (this.CollidesWithPlant(0f, 0f) != null) {
				if (this.CollidesWithPlant(0f, 0f) != null && !(this.CollidesWithPlant(0f, 0f) instanceof GravebusterEntity)) {
					this.CollidesWithPlant(0f, 0f).kill();
				}
			}
		}
		if (this.getType().equals(PvZEntity.EGYPTTOMBSTONE) && this.isAlive() && this.getTypeCount() <= 6){
			if (--spawnTicks <= 0){
				this.addCount();
				spawnTicks = this.random.range(60, 240);
				spawnEgypt();
				this.playSound(PvZSounds.ENTITYRISINGEVENT);
			}
		}
	}

	public void spawnEgypt(){
		if (world instanceof ServerWorld serverWorld) {
			double random = Math.random();
			if (random <= 0.10){
				MummyEntity zombie = new MummyEntity(PvZEntity.MUMMYBUCKET, this.getWorld());
				zombie.initialize(serverWorld, this.getWorld().getLocalDifficulty(this.getBlockPos()), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
				zombie.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), 0.0F);
				serverWorld.spawnEntityAndPassengers(zombie);
			}
			else if (random <= 0.30){
				MummyEntity zombie = new MummyEntity(PvZEntity.MUMMYCONE, this.getWorld());
				zombie.initialize(serverWorld, this.getWorld().getLocalDifficulty(this.getBlockPos()), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
				zombie.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), 0.0F);
				serverWorld.spawnEntityAndPassengers(zombie);
			}
			else {
				double random2 = Math.random();
				if (random2 <= 0.5){
					MummyEntity zombie = new MummyEntity(PvZEntity.MUMMY, this.getWorld());
					zombie.initialize(serverWorld, this.getWorld().getLocalDifficulty(this.getBlockPos()), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
					zombie.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), 0.0F);
					serverWorld.spawnEntityAndPassengers(zombie);
				}
				else {
					ImpEntity zombie = new ImpEntity(PvZEntity.MUMMYIMP, this.getWorld());
					zombie.initialize(serverWorld, this.getWorld().getLocalDifficulty(this.getBlockPos()), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
					zombie.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), 0.0F);
					serverWorld.spawnEntityAndPassengers(zombie);
				}
			}
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

	@Override
	public double getTick(Object object) {
		return 0;
	}

	private <P extends GeoAnimatable> PlayState predicate(AnimationState<P> event) {
		if (this.getType().equals(PvZEntity.GARGOLITHOBSTACLE)){
			event.getController().setAnimation(RawAnimation.begin().thenLoop("gargantuar.gargolith"));
		}
		else if (beingEaten){
			event.getController().setAnimation(RawAnimation.begin().thenLoop("obstacle.eating"));
		}
		else {
			event.getController().setAnimation(RawAnimation.begin().thenLoop("gravestone.idle"));
		}
        return PlayState.CONTINUE;
    }


	/** /~*~//~*ATTRIBUTES*~//~*~/ **/

	public static DefaultAttributeContainer.Builder createGargolithObstacleAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 100.0D)
				.add(ReachEntityAttributes.ATTACK_RANGE, 1.5D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, PVZCONFIG.nestedZombieHealth.gargolithObstH());
    }

	public static DefaultAttributeContainer.Builder createImpTabletObstaclesAttributes() {
		return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 100.0D)
				.add(ReachEntityAttributes.ATTACK_RANGE, 1.5D)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0D)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.0D)
				.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
				.add(EntityAttributes.GENERIC_MAX_HEALTH, PVZCONFIG.nestedZombieHealth.imptabletObstH());
	}

	public static DefaultAttributeContainer.Builder createEgyptTombstoneAttributes() {
		return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 100.0D)
				.add(ReachEntityAttributes.ATTACK_RANGE, 1.5D)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0D)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.0D)
				.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
				.add(EntityAttributes.GENERIC_MAX_HEALTH, PVZCONFIG.nestedZombieHealth.egyptTombstoneH());
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
		ItemStack itemStack;
		itemStack = ModItems.CURSEDGARGOLITHEGG.getDefaultStack();
		return itemStack;
	}

	/** /~*~//~*DAMAGE HANDLER*~//~*~/ **/

	@Override
	public void onDeath(DamageSource source) {
		if (this.getType().equals(PvZEntity.GARGOLITHOBSTACLE)) {
			if (this.getWorld() instanceof ServerWorld serverWorld) {
				BlockPos blockPos = this.getBlockPos().add(this.getX(), 0, this.getZ());
				RockObstacleEntity rockObstacle = (RockObstacleEntity) PvZEntity.IMPTABLETOBSTACLE.create(getWorld());
				rockObstacle.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), 0, 0);
				rockObstacle.initialize(serverWorld, getWorld().getLocalDifficulty(blockPos), SpawnReason.SPAWN_EGG, (EntityData) null, (NbtCompound) null);
				rockObstacle.setOwner(this);
				rockObstacle.setRainbowTag(Rainbow.TRUE);
				rockObstacle.rainbowTicks = 200;
				serverWorld.spawnEntityAndPassengers(rockObstacle);
			}
		}
		if (this.getType().equals(PvZEntity.IMPTABLETOBSTACLE) && !(source.getSource() instanceof GravebusterEntity)) {
			if (this.getWorld() instanceof ServerWorld serverWorld) {
				BlockPos blockPos = this.getBlockPos().add(this.getX(), 0, this.getZ());
				GargantuarEntity gargantuar = (GargantuarEntity) PvZEntity.CURSEDGARGOLITH.create(getWorld());
				gargantuar.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), 0, 0);
				gargantuar.initialize(serverWorld, getWorld().getLocalDifficulty(blockPos), SpawnReason.SPAWN_EGG, (EntityData) null, (NbtCompound) null);
				gargantuar.setOwner(this);
				serverWorld.spawnEntityAndPassengers(gargantuar);
			}
		}
	}
}
