package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.sound.PvZSounds;
import io.github.GrassyDev.pvzmod.registry.entity.damage.PvZDamageTypes;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz1.gargantuar.modernday.GargantuarEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import java.util.Objects;

import static io.github.GrassyDev.pvzmod.PvZCubed.ZOMBIE_MATERIAL;

public class ZombiePropEntity extends GeneralPvZombieEntity implements Monster {

	public boolean isHeavy = false;

	public ZombiePropEntity(EntityType<? extends HostileEntity> entityType, World world) {
		super(entityType, world);
		this.noClip = false;
	}

	public EntityGroup getGroup() {
		return EntityGroup.DEFAULT;
	}

//	public boolean isPushable() {
//		return false;
//	}
//
//	protected void pushAway(Entity entity) {
//		if (!this.isSleeping()) {
//			super.pushAwayFrom(entity);
//		}
//	}



	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return PvZSounds.SILENCEVENET;
	}

	@Override
	public void onDeath(DamageSource source) {
		super.onDeath(source);
		super.discard();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return PvZSounds.SILENCEVENET;
	}

	public void tick() {
		if (this instanceof ZombieObstacleEntity ||
				this.getType().equals(PvZEntity.DEFENSIVEENDGEAR) ||
				this.getType().equals(PvZEntity.PYRAMIDGEAR) ||
				this.getType().equals(PvZEntity.SARCOPHAGUS) ||
				this.getType().equals(PvZEntity.SERGEANTSHIELDGEAR)){
			isHeavy = true;
		}
		if (this.getVehicle() != null){
			this.setYaw(this.getVehicle().getYaw());
			this.setHeadYaw(this.getVehicle().getHeadYaw());
		}
		LivingEntity vehicle = (LivingEntity) this.getVehicle();
		if (vehicle instanceof GeneralPvZombieEntity generalPvZombieEntity && generalPvZombieEntity.isFlying()){
			this.setFlying(Flying.TRUE);
		}
		else if (vehicle instanceof GeneralPvZombieEntity generalPvZombieEntity && !generalPvZombieEntity.isFlying()){
			this.setFlying(Flying.FALSE);
		}
		if (vehicle instanceof GeneralPvZombieEntity generalPvZombieEntity && generalPvZombieEntity.isHovering()){
			this.setHover(Hover.TRUE);
		}
		else if (vehicle instanceof GeneralPvZombieEntity generalPvZombieEntity && !generalPvZombieEntity.isHovering()){
			this.setHover(Hover.FALSE);
		}
		if (this.getRecentDamageSource() != null){
			if (this.getRecentDamageSource().isType(PvZDamageTypes.HYPNO_DAMAGE) && !(this instanceof ZombieShieldEntity) &&
			vehicle instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity.getHypno())) {
				this.setHypno(IsHypno.TRUE);
				vehicle.damage(PvZDamageTypes.of(getWorld(), PvZDamageTypes.HYPNO_DAMAGE), 0);
			}
		}
		if (vehicle instanceof GeneralPvZombieEntity generalPvZombieEntity && generalPvZombieEntity.isStealth()){
			this.setStealthTag(Stealth.TRUE);
		}
		if (vehicle instanceof GeneralPvZombieEntity generalPvZombieEntity && generalPvZombieEntity.getHypno()){
			this.setHypno(IsHypno.TRUE);
		}
		if (vehicle != null && this.getCustomName() != vehicle.getCustomName()){
			vehicle.setCustomName(this.getCustomName());
			((HostileEntity) vehicle).setPersistent();
		}
		if (this.hasStatusEffect(PvZCubed.ICE) && vehicle != null && !(this instanceof ZombieShieldEntity)){
			vehicle.addStatusEffect((new StatusEffectInstance(PvZCubed.ICE, Objects.requireNonNull(this.getStatusEffect(PvZCubed.ICE)).getDuration(), Objects.requireNonNull(this.getStatusEffect(PvZCubed.ICE)).getAmplifier())));
		}
		else if (this.hasStatusEffect(PvZCubed.ICE) && vehicle != null && !vehicle.hasStatusEffect(PvZCubed.ICE) && !(this instanceof ZombieShieldEntity)){
			this.removeStatusEffect(PvZCubed.ICE);
		}
		if (this.hasStatusEffect(PvZCubed.ICE) && this instanceof ZombieShieldEntity){
			this.removeStatusEffect(PvZCubed.ICE);
		}
		if (this.hasStatusEffect(PvZCubed.FROZEN) && this instanceof ZombieShieldEntity){
			this.removeStatusEffect(PvZCubed.FROZEN);
		}
		if (this.hasStatusEffect(PvZCubed.SHADOW) && vehicle != null && !(this instanceof ZombieShieldEntity)){
			vehicle.addStatusEffect((new StatusEffectInstance(PvZCubed.SHADOW, Objects.requireNonNull(this.getStatusEffect(PvZCubed.SHADOW)).getDuration(), Objects.requireNonNull(this.getStatusEffect(PvZCubed.SHADOW)).getAmplifier())));
		}
		else if (this.hasStatusEffect(PvZCubed.SHADOW) && vehicle != null && !vehicle.hasStatusEffect(PvZCubed.SHADOW) && !(this instanceof ZombieShieldEntity)){
			this.removeStatusEffect(PvZCubed.SHADOW);
		}
		if (this.hasStatusEffect(PvZCubed.BARK) && vehicle != null && !(this instanceof ZombieShieldEntity)){
			vehicle.addStatusEffect((new StatusEffectInstance(PvZCubed.BARK, Objects.requireNonNull(this.getStatusEffect(PvZCubed.BARK)).getDuration(), Objects.requireNonNull(this.getStatusEffect(PvZCubed.BARK)).getAmplifier())));
		}
		else if (this.hasStatusEffect(PvZCubed.BARK) && vehicle != null && !vehicle.hasStatusEffect(PvZCubed.BARK) && !(this instanceof ZombieShieldEntity)){
			this.removeStatusEffect(PvZCubed.BARK);
		}
		if (this.hasStatusEffect(PvZCubed.CHEESE) && vehicle != null && !(this instanceof ZombieShieldEntity)){
			vehicle.addStatusEffect((new StatusEffectInstance(PvZCubed.CHEESE, Objects.requireNonNull(this.getStatusEffect(PvZCubed.CHEESE)).getDuration(), Objects.requireNonNull(this.getStatusEffect(PvZCubed.CHEESE)).getAmplifier())));
		}
		else if (this.hasStatusEffect(PvZCubed.CHEESE) && vehicle != null && !vehicle.hasStatusEffect(PvZCubed.CHEESE) && !(this instanceof ZombieShieldEntity)){
			this.removeStatusEffect(PvZCubed.CHEESE);
		}
		if (this.hasStatusEffect(PvZCubed.GENERICSLOW) && vehicle != null && !(this instanceof ZombieShieldEntity)){
			vehicle.addStatusEffect((new StatusEffectInstance(PvZCubed.GENERICSLOW, Objects.requireNonNull(this.getStatusEffect(PvZCubed.GENERICSLOW)).getDuration(), Objects.requireNonNull(this.getStatusEffect(PvZCubed.GENERICSLOW)).getAmplifier())));
		}
		else if (this.hasStatusEffect(PvZCubed.GENERICSLOW) && vehicle != null && !vehicle.hasStatusEffect(PvZCubed.GENERICSLOW) && !(this instanceof ZombieShieldEntity)){
			this.removeStatusEffect(PvZCubed.GENERICSLOW);
		}
		if (this.hasStatusEffect(PvZCubed.WET) && vehicle != null && !(this instanceof ZombieShieldEntity)){
			vehicle.addStatusEffect((new StatusEffectInstance(PvZCubed.WET, Objects.requireNonNull(this.getStatusEffect(PvZCubed.WET)).getDuration(), Objects.requireNonNull(this.getStatusEffect(PvZCubed.WET)).getAmplifier())));
		}
		else if (this.hasStatusEffect(PvZCubed.WET) && vehicle != null && !vehicle.hasStatusEffect(PvZCubed.WET) && !(this instanceof ZombieShieldEntity)){
			this.removeStatusEffect(PvZCubed.WET);
		}
		if (this.hasStatusEffect(PvZCubed.FROZEN) && vehicle != null && !(this instanceof ZombieShieldEntity)){
			vehicle.removeStatusEffect(PvZCubed.STUN);
			vehicle.addStatusEffect((new StatusEffectInstance(PvZCubed.FROZEN, Objects.requireNonNull(this.getStatusEffect(PvZCubed.FROZEN)).getDuration(), Objects.requireNonNull(this.getStatusEffect(PvZCubed.FROZEN)).getAmplifier())));
		}
		else if (this.hasStatusEffect(PvZCubed.FROZEN) && vehicle != null && !vehicle.hasStatusEffect(PvZCubed.FROZEN) && !(this instanceof ZombieShieldEntity)){
			this.removeStatusEffect(PvZCubed.FROZEN);
		}
		if (this.hasStatusEffect(PvZCubed.STUN) && vehicle != null && !(this instanceof ZombieShieldEntity)){
			vehicle.addStatusEffect((new StatusEffectInstance(PvZCubed.STUN, Objects.requireNonNull(this.getStatusEffect(PvZCubed.STUN)).getDuration(), Objects.requireNonNull(this.getStatusEffect(PvZCubed.STUN)).getAmplifier())));
		}
		else if (this.hasStatusEffect(PvZCubed.STUN) && vehicle != null && !vehicle.hasStatusEffect(PvZCubed.STUN) && !(this instanceof ZombieShieldEntity)){
			this.removeStatusEffect(PvZCubed.STUN);
		}
		if (this.hasStatusEffect(PvZCubed.DISABLE) && vehicle != null && !(this instanceof ZombieShieldEntity)){
			vehicle.addStatusEffect((new StatusEffectInstance(PvZCubed.DISABLE, Objects.requireNonNull(this.getStatusEffect(PvZCubed.DISABLE)).getDuration(), Objects.requireNonNull(this.getStatusEffect(PvZCubed.DISABLE)).getAmplifier())));
		}
		else if (this.hasStatusEffect(PvZCubed.DISABLE) && vehicle != null && !vehicle.hasStatusEffect(PvZCubed.DISABLE) && !(this instanceof ZombieShieldEntity)){
			this.removeStatusEffect(PvZCubed.DISABLE);
		}
		if (this.hasStatusEffect(PvZCubed.WARM) && vehicle != null && !(this instanceof ZombieShieldEntity)){
			vehicle.addStatusEffect((new StatusEffectInstance(PvZCubed.WARM, Objects.requireNonNull(this.getStatusEffect(PvZCubed.WARM)).getDuration(), Objects.requireNonNull(this.getStatusEffect(PvZCubed.WARM)).getAmplifier())));
		}
		else if (this.hasStatusEffect(PvZCubed.WARM) && vehicle != null && !vehicle.hasStatusEffect(PvZCubed.WARM) && !(this instanceof ZombieShieldEntity)){
			this.removeStatusEffect(PvZCubed.WARM);
		}
		if (this.hasStatusEffect(PvZCubed.PVZPOISON) && vehicle != null && !(this instanceof ZombieShieldEntity)){
			vehicle.addStatusEffect((new StatusEffectInstance(PvZCubed.PVZPOISON, Objects.requireNonNull(this.getStatusEffect(PvZCubed.PVZPOISON)).getDuration(), Objects.requireNonNull(this.getStatusEffect(PvZCubed.PVZPOISON)).getAmplifier())));
			if (!(ZOMBIE_MATERIAL.get(this.getType()).orElse("flesh").equals("flesh")) && !(ZOMBIE_MATERIAL.get(this.getType()).orElse("flesh").equals("plant"))){
				this.removeStatusEffect(PvZCubed.PVZPOISON);
			}
		}
		if (this.hasStatusEffect(StatusEffects.POISON) && vehicle != null && !(this instanceof ZombieShieldEntity)){
			vehicle.addStatusEffect((new StatusEffectInstance(StatusEffects.POISON, Objects.requireNonNull(this.getStatusEffect(StatusEffects.POISON)).getDuration(), Objects.requireNonNull(this.getStatusEffect(StatusEffects.POISON)).getAmplifier())));
			if (!(ZOMBIE_MATERIAL.get(this.getType()).orElse("flesh").equals("flesh")) && !(ZOMBIE_MATERIAL.get(this.getType()).orElse("flesh").equals("plant"))){
				this.removeStatusEffect(StatusEffects.POISON);
			}
		}
		super.tick();
		if (vehicle != null && vehicle.isOnFire() && !(this instanceof ZombieShieldEntity)){
			vehicle.setOnFire(false);
		}
		if (this.getHealth() <= 0 && this.isOnFire() && vehicle != null && !(this instanceof ZombieShieldEntity)){
			vehicle.setOnFireFor(this.getFireTicks() / 20);
		}
	}

	public ActionResult interactMob(PlayerEntity player, Hand hand) {
		if (this.getHypno() && this.getVehicle() instanceof GargantuarEntity gargantuarEntity){
			player.startRiding(gargantuarEntity, true);
			return ActionResult.success(this.getWorld().isClient);
		}
		else {
			return ActionResult.FAIL;
		}
	}
}
