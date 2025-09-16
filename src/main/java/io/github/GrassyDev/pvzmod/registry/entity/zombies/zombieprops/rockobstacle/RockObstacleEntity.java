package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieprops.rockobstacle;


import io.github.GrassyDev.pvzmod.config.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.entity.damage.PvZDamageTypes;
import io.github.GrassyDev.pvzmod.registry.entity.gravestones.basicgrave.BasicGraveEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.PlantEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz1.browncoat.modernday.BrowncoatEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz1.flagzombie.modernday.FlagzombieEntity;
import io.github.GrassyDev.pvzmod.sound.PvZSounds;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.night.gravebuster.GravebusterEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz1.gargantuar.modernday.GargantuarEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz1.imp.modernday.ImpEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2.browncoat.mummy.MummyEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.GeneralPvZombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombieObstacleEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.GeoAnimatable;

import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.ArrayList;
import java.util.List;

import static io.github.GrassyDev.pvzmod.PvZCubed.PLANT_LOCATION;
import static io.github.GrassyDev.pvzmod.PvZCubed.PVZCONFIG;

public class RockObstacleEntity extends ZombieObstacleEntity implements GeoEntity {
    private AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);
    private String controllerName = "shieldcontroller";
	@Override
	public boolean isPushable() {
		return false;
	}
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

		protected void initDataTracker(DataTracker.Builder builder) {
		super.initDataTracker(builder);
		builder.add(SUMMON_TIMES, 0);
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



	/** /~*~//~*TICKING*~//~*~/ **/
//	private int zombiespawnrng = 0;
	private int spawnTicks = 100;
	public void difficultySpawnrateMod(){
		if (getWorld().getDifficulty().equals(Difficulty.EASY)){
			spawnTicks = this.random.range(180, 320);
		}
		else if (getWorld().getDifficulty().equals(Difficulty.NORMAL)){
			spawnTicks = this.random.range(140, 280);
		}
		else if (getWorld().getDifficulty().equals(Difficulty.HARD)){
			spawnTicks = this.random.range(100, 200);
		}
		else {
			spawnTicks = this.random.range(120, 300);
		}
	}

	private void healthCostForBasicSpawn(){
		if (getWorld().getDifficulty().equals(Difficulty.EASY)){
			this.damage(PvZDamageTypes.of(getWorld(), PvZDamageTypes.GENERIC_ANTI_IFRAME), 15);
		}
		else if (getWorld().getDifficulty().equals(Difficulty.NORMAL)){
			this.damage(PvZDamageTypes.of(getWorld(), PvZDamageTypes.GENERIC_ANTI_IFRAME), 12);
		}
		else if (getWorld().getDifficulty().equals(Difficulty.HARD)){
			this.damage(PvZDamageTypes.of(getWorld(), PvZDamageTypes.GENERIC_ANTI_IFRAME), 9);
		}
		else {
			this.damage(PvZDamageTypes.of(getWorld(), PvZDamageTypes.GENERIC_ANTI_IFRAME), 12);
		}
	}
	private void healthCostForConeSpawn(){
		if (getWorld().getDifficulty().equals(Difficulty.EASY)){
			this.damage(PvZDamageTypes.of(getWorld(), PvZDamageTypes.GENERIC_ANTI_IFRAME), 30);
		}
		else if (getWorld().getDifficulty().equals(Difficulty.NORMAL)){
			this.damage(PvZDamageTypes.of(getWorld(), PvZDamageTypes.GENERIC_ANTI_IFRAME), 26);
		}
		else if (getWorld().getDifficulty().equals(Difficulty.HARD)){
			this.damage(PvZDamageTypes.of(getWorld(), PvZDamageTypes.GENERIC_ANTI_IFRAME), 22);
		}
		else {
			this.damage(PvZDamageTypes.of(getWorld(), PvZDamageTypes.GENERIC_ANTI_IFRAME), 27);
		}
	}
	private void healthCostForBucketSpawn(){
		if (getWorld().getDifficulty().equals(Difficulty.EASY)){
			this.damage(PvZDamageTypes.of(getWorld(), PvZDamageTypes.GENERIC_ANTI_IFRAME), 45);
		}
		else if (getWorld().getDifficulty().equals(Difficulty.NORMAL)){
			this.damage(PvZDamageTypes.of(getWorld(), PvZDamageTypes.GENERIC_ANTI_IFRAME), 40);
		}
		else if (getWorld().getDifficulty().equals(Difficulty.HARD)){
			this.damage(PvZDamageTypes.of(getWorld(), PvZDamageTypes.GENERIC_ANTI_IFRAME), 35);
		}
		else {
			this.damage(PvZDamageTypes.of(getWorld(), PvZDamageTypes.GENERIC_ANTI_IFRAME), 40);
		}
	}
	private int selfdeathticks = 0;
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
		if (!(this.getType().equals(PvZEntity.GARGOLITHOBSTACLE)) || !(this.getType().equals(PvZEntity.IMPTABLETOBSTACLE)) && this.isAlive()){
//			if (this.getTypeCount() <= 5) {
				if (--spawnTicks <= 0 && !(getWorld().getDifficulty().equals(Difficulty.PEACEFUL))) {
					if (this.getType().equals(PvZEntity.EGYPTTOMBSTONE)) {
						spawnEgypt();
					}
					if (this.getType().equals(PvZEntity.BASICTOMBSTONE)) {
						spawnBasic();
					}

					this.difficultySpawnrateMod();
					this.playSound(PvZSounds.ENTITYRISINGEVENT);
//				}
			}
//			if (this.getTypeCount() > 5) {
//				if (--selfdeathticks <= 0){
//					this.damage(PvZDamageTypes.of(getWorld(), PvZDamageTypes.GENERIC_ANTI_IFRAME), 2);
//					selfdeathticks = 20;
//				}
//			}
		}
	}

	public void spawnEgypt(){
		RandomGenerator randomGenerator = RockObstacleEntity.this.getRandom();
		int zombiespawnrng = MathHelper.nextBetween(randomGenerator, 0, 100);
		if (getWorld() instanceof ServerWorld serverWorld) {
			if (zombiespawnrng >= 0 && zombiespawnrng <= 15){
				BlockPos blockPos = RockObstacleEntity.this.getBlockPos().add(0, 0, 0);
				MummyEntity bucketheadEntity = PvZEntity.MUMMYBUCKET.create(RockObstacleEntity.this.getWorld());
				bucketheadEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
				bucketheadEntity.initialize(serverWorld, this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
				bucketheadEntity.setOwner(RockObstacleEntity.this);
				serverWorld.spawnEntityAndPassengers(bucketheadEntity);
				healthCostForBucketSpawn();

			}
			else if (zombiespawnrng >= 16 && zombiespawnrng <= 50){
				BlockPos blockPos = RockObstacleEntity.this.getBlockPos().add(0, 0, 0);
				MummyEntity bucketheadEntity = PvZEntity.MUMMYCONE.create(RockObstacleEntity.this.getWorld());
				bucketheadEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
				bucketheadEntity.initialize(serverWorld, this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
				bucketheadEntity.setOwner(RockObstacleEntity.this);
				serverWorld.spawnEntityAndPassengers(bucketheadEntity);
				healthCostForBucketSpawn();
			}
			else if (zombiespawnrng >= 51 && zombiespawnrng <= 100){
				BlockPos blockPos = RockObstacleEntity.this.getBlockPos().add(0, 0, 0);
				MummyEntity bucketheadEntity = PvZEntity.MUMMY.create(RockObstacleEntity.this.getWorld());
				bucketheadEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
				bucketheadEntity.initialize(serverWorld, this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
				bucketheadEntity.setOwner(RockObstacleEntity.this);
				serverWorld.spawnEntityAndPassengers(bucketheadEntity);
				healthCostForBucketSpawn();
			}
		}
	}
	public void spawnBasic(){
		RandomGenerator randomGenerator = RockObstacleEntity.this.getRandom();
		float zombiespawnrng = MathHelper.nextBetween(randomGenerator, 0, 100);
//		zombiespawnrng = this.random.range(0, 100);
		if (getWorld() instanceof ServerWorld serverWorld) {
			if (zombiespawnrng >= 0 && zombiespawnrng <= 15){
				BlockPos blockPos = RockObstacleEntity.this.getBlockPos().add(0, 0, 0);
				BrowncoatEntity bucketheadEntity = PvZEntity.BUCKETHEAD.create(RockObstacleEntity.this.getWorld());
				bucketheadEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
				bucketheadEntity.initialize(serverWorld, this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
				bucketheadEntity.setOwner(RockObstacleEntity.this);
				serverWorld.spawnEntityAndPassengers(bucketheadEntity);
				healthCostForBucketSpawn();
			}
			if (zombiespawnrng >= 16 && zombiespawnrng <= 50){
				BlockPos blockPos = RockObstacleEntity.this.getBlockPos().add(0, 0, 0);
				BrowncoatEntity coneheadEntity = PvZEntity.CONEHEAD.create(RockObstacleEntity.this.getWorld());
				coneheadEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
                coneheadEntity.initialize(serverWorld, this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
				coneheadEntity.setOwner(RockObstacleEntity.this);
				serverWorld.spawnEntityAndPassengers(coneheadEntity);
				healthCostForConeSpawn();
			}
			if (zombiespawnrng >= 51 && zombiespawnrng <= 100){
				BlockPos blockPos = RockObstacleEntity.this.getBlockPos().add(0, 0, 0);
				BrowncoatEntity basicEntity = PvZEntity.BROWNCOAT.create(RockObstacleEntity.this.getWorld());
				basicEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
				basicEntity.initialize(serverWorld, this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
				basicEntity.setOwner(RockObstacleEntity.this);
				serverWorld.spawnEntityAndPassengers(basicEntity);
				healthCostForBasicSpawn();
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
        return HostileEntity.createAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 75.0D)

                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, PVZCONFIG.nestedZombieHealth.gargolithObstH());
    }

	public static DefaultAttributeContainer.Builder createImpTabletObstaclesAttributes() {
		return HostileEntity.createAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 75.0D)

				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0D)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.0D)
				.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
				.add(EntityAttributes.GENERIC_MAX_HEALTH, PVZCONFIG.nestedZombieHealth.imptabletObstH());
	}

	public static DefaultAttributeContainer.Builder createEgyptTombstoneAttributes() {
		return HostileEntity.createAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 75.0D)

				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0D)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.0D)
				.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
				.add(EntityAttributes.GENERIC_MAX_HEALTH, PVZCONFIG.nestedZombieHealth.egyptTombstoneH());
	}
	public static DefaultAttributeContainer.Builder createBasicTombstoneAttributes() {
		return HostileEntity.createAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 75.0D)

			.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0D)
			.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.0D)
			.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
			.add(EntityAttributes.GENERIC_MAX_HEALTH, PVZCONFIG.nestedZombieHealth.egyptTombstoneH());
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.BLOCK_BASALT_BREAK;
	}
	protected SoundEvent getHurtSound(DamageSource source) {return SoundEvents.BLOCK_BASALT_HIT;}
	protected SoundEvent getAmbientSound() {
		return PvZSounds.SILENCEVENET;
	}

//	public EntityGroup getGroup() {
//		return EntityGroup.UNDEAD;
//	}

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
				BlockPos blockPos = this.getBlockPos().add((int) this.getX(), 0, (int) this.getZ());
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
				BlockPos blockPos = this.getBlockPos().add((int) this.getX(), 0, (int) this.getZ());
				GargantuarEntity gargantuar = (GargantuarEntity) PvZEntity.CURSEDGARGOLITH.create(getWorld());
				gargantuar.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), 0, 0);
				gargantuar.initialize(serverWorld, getWorld().getLocalDifficulty(blockPos), SpawnReason.SPAWN_EGG, (EntityData) null, (NbtCompound) null);
				gargantuar.setOwner(this);
				serverWorld.spawnEntityAndPassengers(gargantuar);
			}
		}
		else{
			super.onDeath(source);
			super.discard();
		}
	}
}
