package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.straight.rainbowbullet;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.PvZSounds;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.PvZProjectileEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz1.snorkel.SnorkelEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.EndGatewayBlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;



import java.util.Iterator;
import java.util.UUID;

import static io.github.GrassyDev.pvzmod.PvZCubed.PVZCONFIG;

public class RainbowBulletEntity extends PvZProjectileEntity implements IAnimatable {

	private String controllerName = "projectilecontroller";
	private AnimationFactory factory = GeckoLibUtil.createFactory(this);


	@Override
	public void registerControllers(AnimationData animationData) {
		AnimationController controller = new AnimationController(this, controllerName, 0, this::predicate);

		animationData.addAnimationController(controller);
	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}

	private <P extends IAnimatable> PlayState predicate(AnimationEvent<P> event) {
		event.getController().setAnimation(new AnimationBuilder().loop("peashot.idle"));
		return PlayState.CONTINUE;
	}

    public RainbowBulletEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
		this.setNoGravity(true);
    }

    public RainbowBulletEntity(World world, LivingEntity owner) {
        super(EntityType.SNOWBALL, owner, world);
    }

    @Environment(EnvType.CLIENT)
    public RainbowBulletEntity(World world, double x, double y, double z, float yaw, float pitch, int interpolation, boolean interpolate, int id, UUID uuid) {
        super(PvZEntity.PEA, world);
        updatePosition(x, y, z);
        updateTrackedPositionAndAngles(x, y, z, yaw, pitch, interpolation, interpolate);
		setId(id);
        setUuid(uuid);
    }

    public void tick() {
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

        if (!this.getWorld().isClient && this.age >= 58) {
            this.getWorld().sendEntityStatus(this, (byte) 3);
            this.remove(RemovalReason.DISCARDED);
        }

		for(int i = 0; i < 1; ++i) {
			ParticleEffect particleEffect = new BlockStateParticleEffect(ParticleTypes.BLOCK, Blocks.RED_STAINED_GLASS.getDefaultState());
			this.getWorld().addParticle(particleEffect, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
			ParticleEffect particleEffect2 = new BlockStateParticleEffect(ParticleTypes.BLOCK, Blocks.ORANGE_STAINED_GLASS.getDefaultState());
			this.getWorld().addParticle(particleEffect2, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
			ParticleEffect particleEffect3 = new BlockStateParticleEffect(ParticleTypes.BLOCK, Blocks.YELLOW_STAINED_GLASS.getDefaultState());
			this.getWorld().addParticle(particleEffect3, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
			ParticleEffect particleEffect4 = new BlockStateParticleEffect(ParticleTypes.BLOCK, Blocks.GREEN_STAINED_GLASS.getDefaultState());
			this.getWorld().addParticle(particleEffect4, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
			ParticleEffect particleEffect5 = new BlockStateParticleEffect(ParticleTypes.BLOCK, Blocks.BLUE_STAINED_GLASS.getDefaultState());
			this.getWorld().addParticle(particleEffect5, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
			ParticleEffect particleEffect6 = new BlockStateParticleEffect(ParticleTypes.BLOCK, Blocks.PURPLE_STAINED_GLASS.getDefaultState());
			this.getWorld().addParticle(particleEffect6, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
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
			if (!world.isClient && entity instanceof Monster monster &&
					!(monster instanceof GeneralPvZombieEntity generalPvZombieEntity && (generalPvZombieEntity.getHypno())) &&
					!(zombiePropEntity2 != null && !(zombiePropEntity2 instanceof ZombieShieldEntity)) &&
					!(zombiePropEntity3 != null && !(zombiePropEntity3 instanceof ZombieShieldEntity)) &&
					!(entity instanceof SnorkelEntity snorkelEntity && snorkelEntity.isInvisibleSnorkel()) && !(entity instanceof GeneralPvZombieEntity generalPvZombieEntity3 && generalPvZombieEntity3.isStealth())
					&& !(entity instanceof ZombieVehicleEntity && (zombiePropEntity2 instanceof ZombieObstacleEntity))) {
				String zombieMaterial = PvZCubed.ZOMBIE_MATERIAL.get(entity.getType()).orElse("flesh");
				SoundEvent sound;
				sound = switch (zombieMaterial) {
					case "metallic", "electronic" -> PvZSounds.BUCKETHITEVENT;
					case "plastic" -> PvZSounds.CONEHITEVENT;
					case "stone", "crystal" -> PvZSounds.STONEHITEVENT;
					default -> PvZSounds.PEAHITEVENT;
				};
				entity.playSound(sound, 0.2F, (float) (0.5F + Math.random()));
				float damage = PVZCONFIG.nestedProjDMG.rainbowBulletDMG();
				if (damage > ((LivingEntity) entity).getHealth() &&
						!(entity instanceof ZombieShieldEntity) &&
						entity.getVehicle() instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity.getHypno())) {
					float damage2 = damage - ((LivingEntity) entity).getHealth();
					entity.damage(DamageSource.thrownProjectile(this, this.getOwner()), damage);
					generalPvZombieEntity.damage(DamageSource.thrownProjectile(this, this.getOwner()), damage2);
				} else {
					entity.damage(DamageSource.thrownProjectile(this, this.getOwner()), damage);
				}
				this.getWorld().sendEntityStatus(this, (byte) 3);
				this.remove(RemovalReason.DISCARDED);
				break;
			}
		}
	}


    @Environment(EnvType.CLIENT)
    public void handleStatus(byte status) {
		if (status != 2 && status != 60){
			super.handleStatus(status);
		}
        if (status == 3) {
            ParticleEffect particleEffect = new BlockStateParticleEffect(ParticleTypes.BLOCK, Blocks.RED_STAINED_GLASS.getDefaultState());

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
