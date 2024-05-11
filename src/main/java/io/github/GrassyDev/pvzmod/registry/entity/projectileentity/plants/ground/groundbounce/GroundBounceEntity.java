package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.ground.groundbounce;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.entity.damage.PvZDamageTypes;
import io.github.GrassyDev.pvzmod.sound.PvZSounds;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.PvZProjectileEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz1.snorkel.SnorkelEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.GeneralPvZombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombiePropEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombieShieldEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoEntity;
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

public class GroundBounceEntity extends PvZProjectileEntity implements GeoEntity {

	private String controllerName = "projectilecontroller";
	private AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);


	@Override
	public void registerControllers(AnimatableManager.ControllerRegistrar controllers){
		controllers.add(new AnimationController<>(this, controllerName, 0, this::predicate));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.factory;
	}


	private <P extends GeoAnimatable> PlayState predicate(AnimationState<P> event) {
		event.getController().setAnimation(RawAnimation.begin().thenLoop("peashot.idle"));
		return PlayState.CONTINUE;
	}

    public GroundBounceEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
		this.noClip = false;
    }

    public GroundBounceEntity(World world, LivingEntity owner) {
        super(EntityType.SNOWBALL, owner, world);
    }

    @Environment(EnvType.CLIENT)
    public GroundBounceEntity(World world, double x, double y, double z, float yaw, float pitch, int interpolation, int id, UUID uuid) {
        super(PvZEntity.GROUNDBOUNCE, world);
        updatePosition(x, y, z);
        updateTrackedPositionAndAngles(x, y, z, yaw, pitch, interpolation);
		setId(id);
        setUuid(uuid);
    }

	private boolean canBounce;
	private int airTicks = 10;

	@Override
	protected float getGravity() {
		return (this.age > 1)? 1 : super.getGravity();
	}

	public void tick() {
        super.tick();
		if (this.isInsideWaterOrBubbleColumn()){
			this.remove(RemovalReason.DISCARDED);
		}
		RandomGenerator randomGenerator = this.getWorld().getRandom();
		int l = MathHelper.floor(this.getPos().x);
		int m = MathHelper.floor(this.getPos().y - (double)0.25);
		int n = MathHelper.floor(this.getPos().z);
		BlockPos blockPos2 = new BlockPos(l, m, n);
		if (!this.getWorld().getFluidState(blockPos2).isSource() && !this.getWorld().getFluidState(blockPos2).isSource()) {
			this.setVelocity(this.getVelocity().getX(), 0, this.getVelocity().getZ());
			this.canBounce = true;
			this.airTicks = 10;
		}
		else {
			if (--airTicks <= 0) {
				this.canBounce = false;
			}
		}
		for(int i = 0; i < 4; ++i) {
			double d = this.getX() + (double) MathHelper.nextBetween(randomGenerator, -0.7F, 0.7F);
			double e = this.getY() + (double) MathHelper.nextBetween(randomGenerator, 0F, 0.5F);
			double f = this.getZ() + (double) MathHelper.nextBetween(randomGenerator, -0.7F, 0.7F);
			this.getWorld().addParticle(new BlockStateParticleEffect(ParticleTypes.BLOCK, this.getWorld().getBlockState(blockPos2)), d, e, f, 0.0, 2, 0.0);
		}

        if (!this.getWorld().isClient && this.isInsideWaterOrBubbleColumn()) {
            this.getWorld().sendEntityStatus(this, (byte) 3);
            this.remove(RemovalReason.DISCARDED);
        }

        if (!this.getWorld().isClient && this.age >= 40) {
            this.getWorld().sendEntityStatus(this, (byte) 3);
            this.remove(RemovalReason.DISCARDED);
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
					!(zombiePropEntity2 != null && !(zombiePropEntity2 instanceof ZombieShieldEntity)) &&
					!(zombiePropEntity3 != null && !(zombiePropEntity3 instanceof ZombieShieldEntity)) &&
					!(entity instanceof SnorkelEntity snorkelEntity && snorkelEntity.isInvisibleSnorkel()) && !(entity instanceof GeneralPvZombieEntity generalPvZombieEntity3 && generalPvZombieEntity3.isStealth()) &&
					(!(entity instanceof GeneralPvZombieEntity generalPvZombieEntity1 && generalPvZombieEntity1.isFlying()) && !(entity instanceof GeneralPvZombieEntity zombie && zombie.isHovering())) && this.canBounce && !hit) {
				splashDamage();
				bounceZombies(entity.getPos());
				hit = true;
				this.remove(RemovalReason.DISCARDED);
			}
		}
	}

	protected void onBlockHit(BlockHitResult blockHitResult) {
		super.onBlockHit(blockHitResult);
		if (!this.getWorld().isClient) {
			this.remove(RemovalReason.DISCARDED);
		}
	}

	protected void splashDamage() {
		List<LivingEntity> list = this.getWorld().getNonSpectatingEntities(LivingEntity.class, this.getBoundingBox().expand(2));
		Iterator var9 = list.iterator();
		while (true) {
			LivingEntity livingEntity;
			do {
				if (!var9.hasNext()) {
					return;
				}
				livingEntity = (LivingEntity) var9.next();
			} while (this.squaredDistanceTo(livingEntity) > 3.0625);

			if (livingEntity instanceof Monster &&
					!(livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity
							&& (generalPvZombieEntity.getHypno()))) {
				ZombiePropEntity zombiePropEntity2 = null;
				ZombiePropEntity zombiePropEntity3 = null;
				for (Entity entity1 : livingEntity.getPassengerList()) {
					if (entity1 instanceof ZombiePropEntity zpe && zombiePropEntity2 == null) {
						zombiePropEntity2 = zpe;
					}
					else if (entity1 instanceof ZombiePropEntity zpe) {
						zombiePropEntity3 = zpe;
					}
				}
				if (livingEntity.getY() < (this.getY() + 1.5) && livingEntity.getY() > (this.getY() - 1.5)) {
					if (!getWorld().isClient &&
							!(zombiePropEntity2 != null && !(zombiePropEntity2 instanceof ZombieShieldEntity)) &&
					!(zombiePropEntity3 != null && !(zombiePropEntity3 instanceof ZombieShieldEntity)) &&
							!(livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && generalPvZombieEntity.isFlying()) && !(livingEntity instanceof GeneralPvZombieEntity zombie && zombie.isHovering())) {
						livingEntity.playSound(PvZSounds.PEAHITEVENT, 0.2F, 1F);
						float damage = 10F;
						if (damage > livingEntity.getHealth() &&
								!(livingEntity instanceof ZombieShieldEntity) &&
								livingEntity.getVehicle() instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity.getHypno())) {
							float damage2 = damage - ((LivingEntity) livingEntity).getHealth();
							if (!(livingEntity instanceof ZombiePropEntity zombiePropEntity)){
								livingEntity.damage(getDamageSources().mobProjectile(this, (LivingEntity) this.getOwner()), 0);
							}
							livingEntity.damage(PvZDamageTypes.of(getWorld(), PvZDamageTypes.GENERIC_ANTI_IFRAME), damage);
							generalPvZombieEntity.damage(getDamageSources().mobProjectile(this, (LivingEntity) this.getOwner()), damage2);
						} else {
							if (!(livingEntity instanceof ZombiePropEntity zombiePropEntity)){
								livingEntity.damage(getDamageSources().mobProjectile(this, (LivingEntity) this.getOwner()), 0);
							}
							livingEntity.damage(PvZDamageTypes.of(getWorld(), PvZDamageTypes.GENERIC_ANTI_IFRAME), damage);
						}
					}
				}
			}
		}
	}

	protected void bounceZombies(Vec3d pos) {
		List<HostileEntity> list = this.getWorld().getNonSpectatingEntities(HostileEntity.class, this.getBoundingBox().expand(3));
		Iterator var9 = list.iterator();
		while (true) {
			HostileEntity hostileEntity;
			do {
				if (!var9.hasNext()) {
					return;
				}
				hostileEntity = (HostileEntity) var9.next();
			} while (pos.squaredDistanceTo(hostileEntity.getPos()) > 3.0625);

			if (!(hostileEntity instanceof ZombiePropEntity) && !(hostileEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && generalPvZombieEntity.getHypno())) {
				if (hostileEntity.getY() < (this.getY() + 2) && hostileEntity.getY() > (this.getY() - 2) &&
						!(hostileEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && generalPvZombieEntity.isFlying()) && !(hostileEntity instanceof GeneralPvZombieEntity zombie && zombie.isHovering())) {
					Vec3d vec3d = new Vec3d((double) -1, +0.5, 0).rotateY(-hostileEntity.getHeadYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
					hostileEntity.addStatusEffect((new StatusEffectInstance(PvZCubed.BOUNCED, 20, 1)));
					hostileEntity.setVelocity(Vec3d.ZERO);
					hostileEntity.addVelocity(vec3d.getX(), vec3d.getY(), vec3d.getZ());
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
    }

    public boolean collides() {
        return false;
    }
}
