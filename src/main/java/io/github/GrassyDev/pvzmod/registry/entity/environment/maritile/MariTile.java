package io.github.GrassyDev.pvzmod.registry.entity.environment.maritile;

import blue.endless.jankson.annotation.Nullable;
import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.entity.environment.TileEntity;
import io.github.GrassyDev.pvzmod.registry.entity.variants.plants.PeapodCountVariants;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.GeneralPvZombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombiePropEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombieShieldEntity;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.Monster;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MariTile extends TileEntity {

	public MariTile(EntityType<? extends MariTile> entityType, World world) {
		super(entityType, world);

	}

	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(DATA_ID_TYPE_COUNT, 0);
	}

	@Override
	public void writeCustomDataToNbt(NbtCompound tag) {
		super.writeCustomDataToNbt(tag);
		tag.putInt("Count", this.getTypeCount());
	}

	public void readCustomDataFromNbt(NbtCompound tag) {
		super.readCustomDataFromNbt(tag);
		this.dataTracker.set(DATA_ID_TYPE_COUNT, tag.getInt("Count"));
	}

	/** /~*~//~*VARIANTS*~//~*~/ **/

	//Pea Pod Counter

	private static final TrackedData<Integer> DATA_ID_TYPE_COUNT =
			DataTracker.registerData(MariTile.class, TrackedDataHandlerRegistry.INTEGER);

	public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty,
								 SpawnReason spawnReason, @Nullable EntityData entityData,
								 @Nullable NbtCompound entityNbt) {

		return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
	}

	private int getTypeCount() {
		return this.dataTracker.get(DATA_ID_TYPE_COUNT);
	}

	public PeapodCountVariants getCount() {
		return PeapodCountVariants.byId(this.getTypeCount() & 255);
	}

	public void setCount(PeapodCountVariants count) {
		this.dataTracker.set(DATA_ID_TYPE_COUNT, count.getId() & 255);
	}

	public void addCount(){
		PeapodCountVariants count = this.getCount();
		this.dataTracker.set(DATA_ID_TYPE_COUNT, count.getId() + 1 & 255);
	}

	public void minusCount(){
		PeapodCountVariants count = this.getCount();
		this.dataTracker.set(DATA_ID_TYPE_COUNT, count.getId() - 1 & 255);
	}


	@Override
	protected void applyDamage(DamageSource source, float amount) {
		super.applyDamage(source, 555);
	}

	List<LivingEntity> checkList = new ArrayList<>();

	private void damageEntity() {
		List<LivingEntity> list = this.getWorld().getNonSpectatingEntities(LivingEntity.class, this.getBoundingBox().expand(1));
		Iterator var9 = list.iterator();
		while (true) {
			LivingEntity livingEntity;
			do {
				do {
					if (!var9.hasNext()) {
						return;
					}

					livingEntity = (LivingEntity) var9.next();
				} while (livingEntity == this);
			} while (this.squaredDistanceTo(livingEntity) > 1);

			ZombiePropEntity zombiePropEntity4 = null;
			if (livingEntity.hasVehicle()) {
				for (Entity entity1 : livingEntity.getVehicle().getPassengerList()) {
					if (entity1 instanceof ZombieShieldEntity zpe && zpe != livingEntity) {
						zombiePropEntity4 = zpe;
					}
				}
			}
			for (Entity entity1 : livingEntity.getPassengerList()) {
				if (entity1 instanceof ZombieShieldEntity zpe && zpe != livingEntity) {
					zombiePropEntity4 = zpe;
				}
			}
			if (((livingEntity instanceof Monster &&
					zombiePropEntity4 == null &&
					!(livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity &&
							generalPvZombieEntity.isFlying()) && !(livingEntity instanceof GeneralPvZombieEntity zombie && zombie.isHovering())) &&
					!(livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity2 && checkList.contains(generalPvZombieEntity2.getOwner())) &&
					!(livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity
							&& (generalPvZombieEntity.getHypno()))) && !checkList.contains(livingEntity) && !(livingEntity instanceof ZombiePropEntity) && livingEntity.isAlive()) {
				if (this.getShadowPowered()){
					livingEntity.addStatusEffect((new StatusEffectInstance(PvZCubed.SHADOW, 200, 1)));
				}
				else if (livingEntity.hasStatusEffect(PvZCubed.MARIGOLD)){
					livingEntity.damage(PvZCubed.HYPNO_DAMAGE, 0f);
				}
				else {
					livingEntity.addStatusEffect((new StatusEffectInstance(PvZCubed.MARIGOLD, 200, 1)));
				}
				checkList.add(livingEntity);
				for (Entity entity : getPassengerList()){
					if (entity instanceof LivingEntity livingEntity1){
						if (this.getShadowPowered()){
							livingEntity.addStatusEffect((new StatusEffectInstance(PvZCubed.SHADOW, 200, 1)));
						}
						else if (livingEntity1.hasStatusEffect(PvZCubed.MARIGOLD)) {
							livingEntity1.damage(PvZCubed.HYPNO_DAMAGE, 0f);
						}
						else {
							livingEntity1.addStatusEffect((new StatusEffectInstance(PvZCubed.MARIGOLD, 200, 1)));
						}
						checkList.add(livingEntity1);
					}
				}
				if (livingEntity.hasVehicle()){
					if (this.getShadowPowered()){
						livingEntity.addStatusEffect((new StatusEffectInstance(PvZCubed.SHADOW, 200, 1)));
					}
					else if (((LivingEntity) livingEntity.getVehicle()).hasStatusEffect(PvZCubed.MARIGOLD)){
						livingEntity.getVehicle().damage(PvZCubed.HYPNO_DAMAGE, 0f);
					}
					else {
						((LivingEntity) livingEntity.getVehicle()).addStatusEffect((new StatusEffectInstance(PvZCubed.MARIGOLD, 200, 1)));
					}
					checkList.add((LivingEntity) livingEntity.getVehicle());
				}
				if (this.getCount().equals(PeapodCountVariants.ONE)){
					this.playSound(SoundEvents.BLOCK_GRASS_BREAK, 0.5f, 1f);
					this.discard();
				}
				else {
					this.playSound(SoundEvents.BLOCK_MOSS_BREAK, 1f, 1f);
					minusCount();
				}
			}
		}
	}

	@Override
	public void tick() {
		List<LivingEntity> list = this.getWorld().getNonSpectatingEntities(LivingEntity.class, this.getBoundingBox().expand(1));
		for (LivingEntity livingEntity : list){
			if (livingEntity instanceof MariTile mariTile && this.squaredDistanceTo(livingEntity) <= 0.5f && livingEntity != this){
				if (mariTile.getTypeCount() <= this.getTypeCount()){
					mariTile.discard();
				}
				else {
					this.discard();
				}
			}
		}
		super.tick();

		if (this.age >= 1200){
			this.discard();
		}
		BlockPos blockPos = this.getBlockPos();
		if (!this.getWorld().isClient()) {
			this.damageEntity();
		}
	}
}
