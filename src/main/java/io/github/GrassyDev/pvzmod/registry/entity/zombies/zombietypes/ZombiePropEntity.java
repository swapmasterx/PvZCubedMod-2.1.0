package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.entity.statuseffects.StatusHolder;
import io.github.GrassyDev.pvzmod.sound.PvZSounds;
import io.github.GrassyDev.pvzmod.registry.entity.damage.PvZDamageTypes;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz1.gargantuar.modernday.GargantuarEntity;
import net.minecraft.entity.Entity;

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

	@Override
	public boolean isPushable() {
		return true;
	}

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
		if (this.hasStatusEffect(StatusHolder.ICE_HOLDER) && vehicle != null && !(this instanceof ZombieShieldEntity)){
			vehicle.addStatusEffect((new StatusEffectInstance(StatusHolder.ICE_HOLDER, Objects.requireNonNull(this.getStatusEffect(StatusHolder.ICE_HOLDER)).getDuration(), Objects.requireNonNull(this.getStatusEffect(StatusHolder.ICE_HOLDER)).getAmplifier())));
		}
		else if (this.hasStatusEffect(StatusHolder.ICE_HOLDER) && vehicle != null && !vehicle.hasStatusEffect(StatusHolder.ICE_HOLDER) && !(this instanceof ZombieShieldEntity)){
			this.removeStatusEffect(StatusHolder.ICE_HOLDER);
		}
		if (this.hasStatusEffect(StatusHolder.ICE_HOLDER) && this instanceof ZombieShieldEntity){
			this.removeStatusEffect(StatusHolder.ICE_HOLDER);
		}
		if (this.hasStatusEffect(StatusHolder.FROZEN_HOLDER) && this instanceof ZombieShieldEntity){
			this.removeStatusEffect(StatusHolder.FROZEN_HOLDER);
		}
		if (this.hasStatusEffect(StatusHolder.SHADOW_HOLDER) && vehicle != null && !(this instanceof ZombieShieldEntity)){
			vehicle.addStatusEffect((new StatusEffectInstance(StatusHolder.SHADOW_HOLDER, Objects.requireNonNull(this.getStatusEffect(StatusHolder.SHADOW_HOLDER)).getDuration(), Objects.requireNonNull(this.getStatusEffect(StatusHolder.SHADOW_HOLDER)).getAmplifier())));
		}
		else if (this.hasStatusEffect(StatusHolder.SHADOW_HOLDER) && vehicle != null && !vehicle.hasStatusEffect(StatusHolder.SHADOW_HOLDER) && !(this instanceof ZombieShieldEntity)){
			this.removeStatusEffect(StatusHolder.SHADOW_HOLDER);
		}
		if (this.hasStatusEffect(StatusHolder.BARK_HOLDER) && vehicle != null && !(this instanceof ZombieShieldEntity)){
			vehicle.addStatusEffect((new StatusEffectInstance(StatusHolder.BARK_HOLDER, Objects.requireNonNull(this.getStatusEffect(StatusHolder.BARK_HOLDER)).getDuration(), Objects.requireNonNull(this.getStatusEffect(StatusHolder.BARK_HOLDER)).getAmplifier())));
		}
		else if (this.hasStatusEffect(StatusHolder.BARK_HOLDER) && vehicle != null && !vehicle.hasStatusEffect(StatusHolder.BARK_HOLDER) && !(this instanceof ZombieShieldEntity)){
			this.removeStatusEffect(StatusHolder.BARK_HOLDER);
		}
		if (this.hasStatusEffect(StatusHolder.CHEESE_HOLDER) && vehicle != null && !(this instanceof ZombieShieldEntity)){
			vehicle.addStatusEffect((new StatusEffectInstance(StatusHolder.CHEESE_HOLDER, Objects.requireNonNull(this.getStatusEffect(StatusHolder.CHEESE_HOLDER)).getDuration(), Objects.requireNonNull(this.getStatusEffect(StatusHolder.CHEESE_HOLDER)).getAmplifier())));
		}
		else if (this.hasStatusEffect(StatusHolder.CHEESE_HOLDER) && vehicle != null && !vehicle.hasStatusEffect(StatusHolder.CHEESE_HOLDER) && !(this instanceof ZombieShieldEntity)){
			this.removeStatusEffect(StatusHolder.CHEESE_HOLDER);
		}
		if (this.hasStatusEffect(StatusHolder.GENERICSLOW_HOLDER) && vehicle != null && !(this instanceof ZombieShieldEntity)){
			vehicle.addStatusEffect((new StatusEffectInstance(StatusHolder.GENERICSLOW_HOLDER, Objects.requireNonNull(this.getStatusEffect(StatusHolder.GENERICSLOW_HOLDER)).getDuration(), Objects.requireNonNull(this.getStatusEffect(StatusHolder.GENERICSLOW_HOLDER)).getAmplifier())));
		}
		else if (this.hasStatusEffect(StatusHolder.GENERICSLOW_HOLDER) && vehicle != null && !vehicle.hasStatusEffect(StatusHolder.GENERICSLOW_HOLDER) && !(this instanceof ZombieShieldEntity)){
			this.removeStatusEffect(StatusHolder.GENERICSLOW_HOLDER);
		}
		if (this.hasStatusEffect(StatusHolder.WET_HOLDER) && vehicle != null && !(this instanceof ZombieShieldEntity)){
			vehicle.addStatusEffect((new StatusEffectInstance(StatusHolder.WET_HOLDER, Objects.requireNonNull(this.getStatusEffect(StatusHolder.WET_HOLDER)).getDuration(), Objects.requireNonNull(this.getStatusEffect(StatusHolder.WET_HOLDER)).getAmplifier())));
		}
		else if (this.hasStatusEffect(StatusHolder.WET_HOLDER) && vehicle != null && !vehicle.hasStatusEffect(StatusHolder.WET_HOLDER) && !(this instanceof ZombieShieldEntity)){
			this.removeStatusEffect(StatusHolder.WET_HOLDER);
		}
		if (this.hasStatusEffect(StatusHolder.FROZEN_HOLDER) && vehicle != null && !(this instanceof ZombieShieldEntity)){
			vehicle.removeStatusEffect(StatusHolder.STUN_HOLDER);
			vehicle.addStatusEffect((new StatusEffectInstance(StatusHolder.FROZEN_HOLDER, Objects.requireNonNull(this.getStatusEffect(StatusHolder.FROZEN_HOLDER)).getDuration(), Objects.requireNonNull(this.getStatusEffect(StatusHolder.FROZEN_HOLDER)).getAmplifier())));
		}
		else if (this.hasStatusEffect(StatusHolder.FROZEN_HOLDER) && vehicle != null && !vehicle.hasStatusEffect(StatusHolder.FROZEN_HOLDER) && !(this instanceof ZombieShieldEntity)){
			this.removeStatusEffect(StatusHolder.FROZEN_HOLDER);
		}
		if (this.hasStatusEffect(StatusHolder.STUN_HOLDER) && vehicle != null && !(this instanceof ZombieShieldEntity)){
			vehicle.addStatusEffect((new StatusEffectInstance(StatusHolder.STUN_HOLDER, Objects.requireNonNull(this.getStatusEffect(StatusHolder.STUN_HOLDER)).getDuration(), Objects.requireNonNull(this.getStatusEffect(StatusHolder.STUN_HOLDER)).getAmplifier())));
		}
		else if (this.hasStatusEffect(StatusHolder.STUN_HOLDER) && vehicle != null && !vehicle.hasStatusEffect(StatusHolder.STUN_HOLDER) && !(this instanceof ZombieShieldEntity)){
			this.removeStatusEffect(StatusHolder.STUN_HOLDER);
		}
		if (this.hasStatusEffect(StatusHolder.DISABLE_HOLDER) && vehicle != null && !(this instanceof ZombieShieldEntity)){
			vehicle.addStatusEffect((new StatusEffectInstance(StatusHolder.DISABLE_HOLDER, Objects.requireNonNull(this.getStatusEffect(StatusHolder.DISABLE_HOLDER)).getDuration(), Objects.requireNonNull(this.getStatusEffect(StatusHolder.DISABLE_HOLDER)).getAmplifier())));
		}
		else if (this.hasStatusEffect(StatusHolder.DISABLE_HOLDER) && vehicle != null && !vehicle.hasStatusEffect(StatusHolder.DISABLE_HOLDER) && !(this instanceof ZombieShieldEntity)){
			this.removeStatusEffect(StatusHolder.DISABLE_HOLDER);
		}
		if (this.hasStatusEffect(StatusHolder.WARM_HOLDER) && vehicle != null && !(this instanceof ZombieShieldEntity)){
			vehicle.addStatusEffect((new StatusEffectInstance(StatusHolder.WARM_HOLDER, Objects.requireNonNull(this.getStatusEffect(StatusHolder.WARM_HOLDER)).getDuration(), Objects.requireNonNull(this.getStatusEffect(StatusHolder.WARM_HOLDER)).getAmplifier())));
		}
		else if (this.hasStatusEffect(StatusHolder.WARM_HOLDER) && vehicle != null && !vehicle.hasStatusEffect(StatusHolder.WARM_HOLDER) && !(this instanceof ZombieShieldEntity)){
			this.removeStatusEffect(StatusHolder.WARM_HOLDER);
		}
		if (this.hasStatusEffect(StatusHolder.POISON_HOLDER) && vehicle != null && !(this instanceof ZombieShieldEntity)){
			vehicle.addStatusEffect((new StatusEffectInstance(StatusHolder.POISON_HOLDER, Objects.requireNonNull(this.getStatusEffect(StatusHolder.POISON_HOLDER)).getDuration(), Objects.requireNonNull(this.getStatusEffect(StatusHolder.POISON_HOLDER)).getAmplifier())));
			if (!(ZOMBIE_MATERIAL.get(this.getType()).orElse("flesh").equals("flesh")) && !(ZOMBIE_MATERIAL.get(this.getType()).orElse("flesh").equals("plant"))){
				this.removeStatusEffect(StatusHolder.POISON_HOLDER);
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
