package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.tile.springproj;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.sound.PvZSounds;
import net.minecraft.sound.SoundEvent;
import io.github.GrassyDev.pvzmod.registry.entity.environment.TileEntity;
import io.github.GrassyDev.pvzmod.registry.entity.environment.springtile.SpringTile;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.PvZProjectileEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.GeneralPvZombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombiePropEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombieShieldEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.EndGatewayBlockEntity;
import net.minecraft.entity.*;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.util.GeckoLibUtil;


import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import static io.github.GrassyDev.pvzmod.PvZCubed.PVZCONFIG;

public class SpringProjEntity extends PvZProjectileEntity implements GeoAnimatable {

	private String controllerName = "projectilecontroller";
	private AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

	private LivingEntity target;

	private boolean noDMG;

	public float ownerYaw;


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
		event.getController().setAnimation(RawAnimation.begin().thenLoop("cabbage.idle"));
		return PlayState.CONTINUE;
	}

    public SpringProjEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
		this.setNoGravity(false);
    }

    public SpringProjEntity(World world, LivingEntity owner) {
        super(EntityType.SNOWBALL, owner, world);
    }

    @Environment(EnvType.CLIENT)
    public SpringProjEntity(World world, double x, double y, double z, float yaw, float pitch, int interpolation, boolean interpolate, int id, UUID uuid) {
        super(PvZEntity.CABBAGE, world);
        updatePosition(x, y, z);
        updateTrackedPositionAndAngles(x, y, z, yaw, pitch, interpolation, interpolate);
		setId(id);
        setUuid(uuid);
    }

	public LivingEntity getTarget (LivingEntity target){
		return this.target = target;
	}

    public void tick() {
		if (age <= 1) {
			if (this.getOwner() != null) {
				this.ownerYaw = this.getOwner().getHeadYaw();
			}
		}
        super.tick();
		HitResult hitResult = ProjectileUtil.getCollision(this, this::canHit);
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
		if (!this.getWorld().isClient && this.age > 50 && target != null && !noDMG) {
			if (target.getHealth() > 0) {
				this.setVelocity(0,this.getVelocity().getY(), 0);
				this.setPosition(target.getPos().getX(), this.getY() - 0.0005, target.getZ());
			}
		}
		if (target != null && !noDMG){
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
			if (!noDMG && !getWorld().isClient && entity instanceof Monster monster &&
					!(monster instanceof GeneralPvZombieEntity generalPvZombieEntity && (generalPvZombieEntity.getHypno())) &&
					!(zombiePropEntity2 != null && !(zombiePropEntity2 instanceof ZombieShieldEntity)) &&
					!(zombiePropEntity3 != null && !(zombiePropEntity3 instanceof ZombieShieldEntity)) &&
					!(entity instanceof ZombieShieldEntity zombieShieldEntity && zombieShieldEntity.hasVehicle())) {
					String zombieMaterial = PvZCubed.ZOMBIE_MATERIAL.get(entity.getType()).orElse("flesh");
					SoundEvent sound;
					sound = switch (zombieMaterial) {
						case "metallic", "electronic" -> PvZSounds.BUCKETHITEVENT;
						case "plastic" -> PvZSounds.CONEHITEVENT;
						case "stone", "crystal" -> PvZSounds.STONEHITEVENT;
						default -> PvZSounds.PEAHITEVENT;
					};
					entity.playSound(sound, 0.2F, (float) (0.5F + Math.random()));
					float damage = PVZCONFIG.nestedProjDMG.springDMG();
					if (damage > ((LivingEntity) entity).getHealth() &&
							!(entity instanceof ZombieShieldEntity) &&
							entity.getVehicle() instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity.getHypno())) {
						float damage2 = damage - ((LivingEntity) entity).getHealth();
						entity.damage(getDamageSources().mobProjectile(this, this.getPrimaryPassenger()), damage);
						generalPvZombieEntity.damage(getDamageSources().mobProjectile(this, this.getPrimaryPassenger()), damage2);
					} else {
						entity.damage(getDamageSources().mobProjectile(this, this.getPrimaryPassenger()), damage);
					}
					LivingEntity livingEntity = (LivingEntity) entity;
					if (entity.hasVehicle()){
						livingEntity = (LivingEntity) entity.getVehicle();
					}
					Vec3d vec3d2 = new Vec3d((double) 2.33, -0.5, 1).rotateY(-ownerYaw * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
					if (!(livingEntity instanceof ZombiePropEntity) && !(livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && generalPvZombieEntity.getHypno())) {
						Vec3d vec3d = new Vec3d((double) 0.25, +0.5, 0).rotateY(-ownerYaw * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
						livingEntity.addStatusEffect((new StatusEffectInstance(PvZCubed.BOUNCED, 20, 1)));
						livingEntity.setVelocity(Vec3d.ZERO);
						livingEntity.addVelocity(vec3d.getX(), vec3d.getY(), vec3d.getZ());
					}
					this.setVelocity(this.getVelocity().multiply(vec3d2));
					this.noDMG = true;
			}
		}
    }

	public void createSpringTile(BlockPos blockPos){
		if (this.getWorld() instanceof ServerWorld serverWorld) {
			List<TileEntity> tileCheck = getWorld().getNonSpectatingEntities(TileEntity.class, PvZEntity.PEASHOOTER.getDimensions().getBoxAt(blockPos.getX(), blockPos.getY(), blockPos.getZ()).expand(-0.5f, 0, -0.5f));
			if (tileCheck.isEmpty()) {
				SpringTile tile = (SpringTile) PvZEntity.SPRINGTILE.create(getWorld());
				tile.refreshPositionAndAngles(blockPos.getX(), blockPos.getY(), blockPos.getZ(), 0, 0);
				tile.initialize(serverWorld, getWorld().getLocalDifficulty(blockPos), SpawnReason.SPAWN_EGG, (EntityData) null, (NbtCompound) null);
				tile.setPersistent();
				serverWorld.spawnEntityAndPassengers(tile);
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
		if (noDMG) {
			createSpringTile(blockHitResult.getBlockPos());
			this.discard();
		}
	}

    public boolean collides() {
        return false;
    }
}
