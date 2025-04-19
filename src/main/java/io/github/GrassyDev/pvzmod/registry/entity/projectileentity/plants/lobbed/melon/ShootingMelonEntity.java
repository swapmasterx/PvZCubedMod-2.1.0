 package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.lobbed.melon;

 import io.github.GrassyDev.pvzmod.PvZCubed;
 import io.github.GrassyDev.pvzmod.registry.PvZEntity;
 import io.github.GrassyDev.pvzmod.registry.entity.damage.PvZDamageTypes;
 import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.PvZProjectileEntity;
 import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.GeneralPvZombieEntity;
 import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombiePropEntity;
 import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombieShieldEntity;
 import io.github.GrassyDev.pvzmod.sound.PvZSounds;
 import net.fabricmc.api.EnvType;
 import net.fabricmc.api.Environment;
 import net.minecraft.block.BlockState;
 import net.minecraft.block.Blocks;
 import net.minecraft.block.entity.BlockEntity;
 import net.minecraft.block.entity.EndGatewayBlockEntity;
 import net.minecraft.entity.Entity;
 import net.minecraft.entity.EntityType;
 import net.minecraft.entity.LivingEntity;
 import net.minecraft.entity.mob.Monster;
 import net.minecraft.entity.projectile.ProjectileUtil;
 import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
 import net.minecraft.item.Item;
 import net.minecraft.item.ItemStack;
 import net.minecraft.particle.ItemStackParticleEffect;
 import net.minecraft.particle.ParticleEffect;
 import net.minecraft.particle.ParticleTypes;
 import net.minecraft.sound.SoundEvent;
 import net.minecraft.util.hit.BlockHitResult;
 import net.minecraft.util.hit.HitResult;
 import net.minecraft.util.math.BlockPos;
 import net.minecraft.util.math.Vec3d;
 import net.minecraft.world.World;
 import software.bernie.geckolib.animatable.GeoEntity;
 import software.bernie.geckolib.core.animatable.GeoAnimatable;
 import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
 import software.bernie.geckolib.core.animation.AnimatableManager;
 import software.bernie.geckolib.core.animation.AnimationController;
 import software.bernie.geckolib.core.animation.AnimationState;
 import software.bernie.geckolib.core.animation.RawAnimation;
 import software.bernie.geckolib.core.object.PlayState;
 import software.bernie.geckolib.util.GeckoLibUtil;

 import java.util.Iterator;
 import java.util.List;
 import java.util.UUID;

 import static io.github.GrassyDev.pvzmod.PvZCubed.PVZCONFIG;
 public class ShootingMelonEntity extends PvZProjectileEntity implements GeoEntity {

	 private String controllerName = "projectilecontroller";
	 private AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

	 private LivingEntity target;

	 @Override
	 public void registerControllers(AnimatableManager.ControllerRegistrar controllers){
		 controllers.add(new AnimationController<>(this, controllerName, 0, this::predicate));
	 }

	 @Override
	 public AnimatableInstanceCache getAnimatableInstanceCache() {
		 return this.factory;
	 }


	 private <P extends GeoAnimatable> PlayState predicate(AnimationState<P> event) {
		 event.getController().setAnimation(RawAnimation.begin().thenLoop("cabbage.idle"));
		 return PlayState.CONTINUE;
	 }

	 public ShootingMelonEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
		 super(entityType, world);
		 this.setNoGravity(false);
	 }

	 public ShootingMelonEntity(World world, LivingEntity owner) {
		 super(EntityType.SNOWBALL, owner, world);
	 }

	 @Environment(EnvType.CLIENT)
	 public ShootingMelonEntity(World world, double x, double y, double z, float yaw, float pitch, int interpolation, boolean interpolate, int id, UUID uuid) {
		 super(PvZEntity.MELON, world);
		 updatePosition(x, y, z);
		 updateTrackedPositionAndAngles(x, y, z, yaw, pitch, interpolation);
		 setId(id);
		 setUuid(uuid);
	 }

	 public LivingEntity getTarget (LivingEntity target){
		 return this.target = target;
	 }

	 public void tick() {
		 super.tick();
		 HitResult hitResult = ProjectileUtil.method_49997(this, this::canHit);
		 boolean bl = false;
		 if (hitResult.getType() == HitResult.Type.BLOCK) {
			 BlockPos blockPos = ((BlockHitResult)hitResult).getBlockPos();
			 BlockState blockState = this.getWorld().getBlockState(blockPos);
			 if (blockState.isOf(Blocks.NETHER_PORTAL)) {
				 this.setInNetherPortal(blockPos);
				 bl = true;
			 } else if (blockState.isOf(Blocks.END_GATEWAY)) {
				 BlockEntity blockEntity = this.getWorld().getBlockEntity(blockPos);
				 if (blockEntity instanceof EndGatewayBlockEntity && EndGatewayBlockEntity.canTeleport(this)) {
					 EndGatewayBlockEntity.tryTeleportingEntity(this.getWorld(), blockPos, blockState, this, (EndGatewayBlockEntity)blockEntity);
				 }

				 bl = true;
			 }
		 }

		 if (hitResult.getType() != HitResult.Type.MISS && !bl) {
			 this.onCollision(hitResult);
		 }

		 if (!this.getWorld().isClient && this.isInsideWaterOrBubbleColumn()) {
			 this.getWorld().sendEntityStatus(this, (byte) 3);
			 this.remove(RemovalReason.DISCARDED);
		 }

		 if (!this.getWorld().isClient && this.age >= 120) {
			 this.getWorld().sendEntityStatus(this, (byte) 3);
			 this.remove(RemovalReason.DISCARDED);
		 }
		 if (!this.getWorld().isClient && this.age > 50 && target != null) {
			 if (target.getHealth() > 0) {
				 this.setVelocity(0,this.getVelocity().getY(), 0);
				 this.setPosition(target.getPos().getX(), this.getY() - 0.0005, target.getZ());
			 }
		 }
		 if (target != null){
			 if ((target.getHealth() > 0 && (this.getPos().getX() <= target.getPos().getX() + 0.3 && this.getPos().getX() >= target.getPos().getX() - 0.3) &&
				 this.getPos().getZ() <= target.getPos().getZ() + 0.3 && this.getPos().getZ() >= target.getPos().getZ() - 0.3)){
				 this.setVelocity(0,this.getVelocity().getY(), 0);
				 this.setPosition(target.getPos().getX(), this.getY() - 0.0005, target.getZ());
			 }
		 }
	 }

	 @Override
	 protected Item getDefaultItem() {
		 return null;
	 }


	 @Override
	 public void hitEntities() {
		 super.hitEntities();
		 boolean hit = false;
		 Iterator var9 = hitEntities.iterator();
		 while (true) {
			 Entity entity;
			 do {
				 if (!var9.hasNext()) {
					 return;
				 }

				 entity = (Entity) var9.next();
			 } while (entity == this.getOwner());
			 ZombiePropEntity zombiePropEntity2 = null;
			 ZombiePropEntity zombiePropEntity3 = null;
			 for (Entity entity1 : entity.getPassengerList()) {
				 if (entity1 instanceof ZombiePropEntity zpe && zombiePropEntity2 == null) {
					 zombiePropEntity2 = zpe;
				 }
				 if (entity1 instanceof ZombiePropEntity zpe) {
					 zombiePropEntity3 = zpe;
				 }
			 }
			 if (!getWorld().isClient && entity instanceof Monster monster &&
				 !(monster instanceof GeneralPvZombieEntity generalPvZombieEntity && (generalPvZombieEntity.getHypno())) &&
				 !(zombiePropEntity2 instanceof ZombiePropEntity && !(zombiePropEntity2 instanceof ZombieShieldEntity)) &&
				 !(zombiePropEntity3 != null && !(zombiePropEntity3 instanceof ZombieShieldEntity)) &&
				 !(entity instanceof ZombieShieldEntity zombieShieldEntity && zombieShieldEntity.hasVehicle()) &&
				 !(entity instanceof GeneralPvZombieEntity generalPvZombieEntity3 && generalPvZombieEntity3.isStealth()) && !hit) {
				 String zombieMaterial = PvZCubed.ZOMBIE_MATERIAL.get(entity.getType()).orElse("flesh");
				 SoundEvent sound;
				 sound = switch (zombieMaterial) {
					 case "metallic", "electronic" -> PvZSounds.PEAHITEVENT;
					 case "plastic" -> PvZSounds.PEAHITEVENT;
					 case "stone", "crystal" -> PvZSounds.PEAHITEVENT;
					 default -> PvZSounds.PEAHITEVENT;
				 };
				 entity.playSound(sound, 0.2F, 1F);
				 float damage = PVZCONFIG.nestedProjDMG.melonDMG() * damageMultiplier;
				 if (damage > ((LivingEntity) entity).getHealth() &&
					 !(entity instanceof ZombieShieldEntity) &&
					 entity.getVehicle() instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity.getHypno())) {
					 float damage2 = damage - ((LivingEntity) entity).getHealth();
					 if (!(entity instanceof ZombiePropEntity zombiePropEntity)){
						 entity.damage(getDamageSources().mobProjectile(this, (LivingEntity) this.getOwner()), 0);
					 }
					 entity.damage(PvZDamageTypes.of(getWorld(), PvZDamageTypes.GENERIC_ANTI_IFRAME), damage);
					 generalPvZombieEntity.damage(getDamageSources().mobProjectile(this, (LivingEntity) this.getOwner()), damage2);
				 } else {
					 if (!(entity instanceof ZombiePropEntity zombiePropEntity)){
						 entity.damage(getDamageSources().mobProjectile(this, (LivingEntity) this.getOwner()), 0);
					 }
					 entity.damage(PvZDamageTypes.of(getWorld(), PvZDamageTypes.GENERIC_ANTI_IFRAME), damage);
				 }
				 hit = true;
				 Vec3d vec3d = this.getPos();
				 List<LivingEntity> list = this.getWorld().getNonSpectatingEntities(LivingEntity.class, this.getBoundingBox().expand(5.0));
				 Iterator var10 = list.iterator();
				 while (true) {
					 LivingEntity livingEntity;
					 do {
						 do {
							 if (!var10.hasNext()) {
								 return;
							 }

							 livingEntity = (LivingEntity) var10.next();
						 } while (livingEntity == this.getOwner());
					 } while (entity.squaredDistanceTo(livingEntity) > 4);

					 if (livingEntity instanceof Monster &&
						 !(livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity
							 && (generalPvZombieEntity.getHypno()))) {
						 if (livingEntity != entity) {
							 float damage3 = PVZCONFIG.nestedProjDMG.melonSDMG() * damageMultiplier;
							 ZombiePropEntity zombiePropEntity4 = null;
							 for (Entity entity1 : livingEntity.getPassengerList()) {
								 if (entity1 instanceof ZombiePropEntity zpe && zombiePropEntity4 == null) {
									 zombiePropEntity4 = zpe;
								 }
							 }
							 ZombiePropEntity zombiePropEntity6 = null;
							 if (livingEntity.hasVehicle()) {
								 for (Entity entity1 : livingEntity.getVehicle().getPassengerList()) {
									 if (entity1 instanceof ZombieShieldEntity zpe && zpe != livingEntity) {
										 zombiePropEntity6 = zpe;
									 }
								 }
							 }
							 if (!(zombiePropEntity4 instanceof ZombieShieldEntity)) {
								 if (zombiePropEntity4 == null && zombiePropEntity6 == null) {
									 if (damage3 > livingEntity.getHealth() &&
										 !(livingEntity instanceof ZombieShieldEntity) &&
										 livingEntity.getVehicle() instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity.getHypno())) {
										 float damage2 = damage3 - livingEntity.getHealth();
										 entity.damage(PvZDamageTypes.of(getWorld(), PvZDamageTypes.GENERIC_ANTI_IFRAME), damage3);
										 generalPvZombieEntity.damage(getDamageSources().mobProjectile(this, (LivingEntity) this.getOwner()), damage2);
									 } else {
										 if (!(entity instanceof ZombiePropEntity zombiePropEntity)){
											 entity.damage(getDamageSources().mobProjectile(this, (LivingEntity) this.getOwner()), 0);
										 }
										 entity.damage(PvZDamageTypes.of(getWorld(), PvZDamageTypes.GENERIC_ANTI_IFRAME), damage3);
									 }
								 }
							 }
						 }
						 this.getWorld().sendEntityStatus(this, (byte) 3);
						 this.remove(RemovalReason.DISCARDED);
					 }
				 }
			 }
		 }
	 }

	 @Environment(EnvType.CLIENT)
	 private ParticleEffect getParticleParameters() {
		 ItemStack itemStack = this.getItem();
		 return (ParticleEffect)(itemStack.isEmpty() ? ParticleTypes.ITEM_SLIME : new ItemStackParticleEffect(ParticleTypes.ITEM, itemStack));
	 }


	 @Environment(EnvType.CLIENT)
	 public void handleStatus(byte status) {
		 if (status != 2 && status != 60){
			 super.handleStatus(status);
		 }
		 if (status == 3) {
			 ParticleEffect particleEffect = this.getParticleParameters();

			 for(int i = 0; i < 8; ++i) {
				 this.getWorld().addParticle(particleEffect, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
			 }
		 }

	 }
	 protected void onBlockHit(BlockHitResult blockHitResult) {
		 super.onBlockHit(blockHitResult);
		 if (!this.getWorld().isClient) {
			 this.getWorld().sendEntityStatus(this, (byte)3);
			 this.remove(RemovalReason.DISCARDED);
		 }
	 }

	 public boolean collides() {
		 return false;
	 }
 }
