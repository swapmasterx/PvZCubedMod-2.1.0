package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.tallnut;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.config.ModItems;
import io.github.GrassyDev.pvzmod.sound.PvZSounds;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.PlantEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz1.snorkel.SnorkelEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.GeneralPvZombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombiePropEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombieShieldEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static io.github.GrassyDev.pvzmod.PvZCubed.PVZCONFIG;

public class TallnutEntity extends PlantEntity implements GeoEntity {
    private String controllerName = "wallcontroller";


	private AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

    public TallnutEntity(EntityType<? extends TallnutEntity> entityType, World world) {
        super(entityType, world);

    }

	static {
	}

	@Environment(EnvType.CLIENT)
	public void handleStatus(byte status) {
		if (status != 2 && status != 60){
			super.handleStatus(status);
		}
		if (status == 101){
			fall = true;
		}
		if (status == 102){
			fall = false;
		}
	}

	/** /~*~//~*VARIANTS*~//~*~/ **/

	//Health Stage Counter

	public enum Crack {
		NONE(1.0F),
		LOW(0.75F),
		MEDIUM(0.5F),
		HIGH(0.25F);

		private static final List<TallnutEntity.Crack> VALUES = (List) Stream.of(values()).sorted(Comparator.comparingDouble((crack) -> {
			return (double)crack.maxHealthFraction;
		})).collect(ImmutableList.toImmutableList());
		private final float maxHealthFraction;

		Crack(float maxHealthFraction) {
			this.maxHealthFraction = maxHealthFraction;
		}

		public static TallnutEntity.Crack from(float healthFraction) {
			Iterator var1 = VALUES.iterator();

			TallnutEntity.Crack crack;
			do {
				if (!var1.hasNext()) {
					return NONE;
				}

				crack = (TallnutEntity.Crack)var1.next();
			} while(!(healthFraction < crack.maxHealthFraction));

			return crack;
		}
	}

	public TallnutEntity.Crack getCrack() {
		return TallnutEntity.Crack.from(this.getHealth() / this.getMaxHealth());
	}

	public static final Map<Crack, Identifier> LOCATION_BY_VARIANT =
			Util.make(Maps.newEnumMap(Crack.class), (map) -> {
				map.put(Crack.NONE,
						new Identifier(PvZCubed.MOD_ID, "textures/entity/wallnut/wallnut.png"));
				map.put(Crack.LOW,
						new Identifier(PvZCubed.MOD_ID, "textures/entity/wallnut/wallnut.png"));
				map.put(Crack.MEDIUM,
						new Identifier(PvZCubed.MOD_ID, "textures/entity/wallnut/wallnut_dmg1.png"));
				map.put(Crack.HIGH,
						new Identifier(PvZCubed.MOD_ID, "textures/entity/wallnut/wallnut_dmg2.png"));
			});


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
		if (fall){
			event.getController().setAnimation(RawAnimation.begin().thenPlay("tallnut.fall"));
		}
		else if (this.getCrack().equals(Crack.NONE)){
            event.getController().setAnimation(RawAnimation.begin().thenLoop("wallnut.idle"));
        }
        else {
			event.getController().setAnimation(RawAnimation.begin().thenLoop("wallnut.damage"));
        }
        return PlayState.CONTINUE;
    }


	/** /~*~//~*AI*~//~*~/ **/

	protected void initGoals() {
		this.goalSelector.add(1, new LookAtEntityGoal(this, HostileEntity.class, 50.0F));
	}


	/** /~*~//~*POSITION*~//~*~/ **/

	public void setPosition(double x, double y, double z) {
		BlockPos blockPos = this.getBlockPos();
		if (this.hasVehicle()) {
			super.setPosition(x, y, z);
		} else {
			super.setPosition((double)MathHelper.floor(x) + 0.5, (double)MathHelper.floor(y + 0.5), (double)MathHelper.floor(z) + 0.5);
		}
	}


	/** /~*~//~*TICKING*~//~*~/ **/

	public void tick() {
		super.tick();
		this.targetZombies(this.getPos(), 3, false, false, false);
		if (!this.getWorld().isClient()) {
			if (fall) {
				this.getWorld().sendEntityStatus(this, (byte) 101);
				if (fallTicks == 60) {
					Vec3d vec3d = new Vec3d((double) +1.5, 0, 0).rotateY(-this.getHeadYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
					Vec3d targetPos = new Vec3d(this.getX() + vec3d.x, this.getY(), this.getZ() + vec3d.z);
					splashDamage(targetPos);
				}
				--fallTicks;
				if (fallTicks <= 0) {
					fallTicks = 60;
					fall = false;
					this.getWorld().sendEntityStatus(this, (byte) 102);
				}
			}
		}
		BlockPos blockPos = this.getBlockPos();
		if (tickDelay <= 1) {
			BlockPos blockPos2 = this.getBlockPos();
			BlockState blockState = this.getLandingBlockState();
			if ((!blockPos2.equals(blockPos) || !blockState.hasSolidTopSurface(getWorld(), this.getBlockPos(), this)) && !this.hasVehicle()) {
				if (!this.getWorld().isClient && this.getWorld().getGameRules().getBooleanValue(GameRules.DO_MOB_LOOT) && !this.naturalSpawn && this.age <= 10 && !this.dead){
					this.dropItem(ModItems.TALLNUT_SEED_PACKET);
				}
				this.discard();
			}
		}
    }

	public void tickMovement() {
		super.tickMovement();
		if (!this.getWorld().isClient && this.isAlive() && this.isInsideWaterOrBubbleColumn() && this.deathTime == 0) {
			this.discard();
		}
	}

	public float fallTicks = 60;
	public boolean fall = false;

	protected void splashDamage(Vec3d vec3d) {
		List<LivingEntity> list = this.getWorld().getNonSpectatingEntities(LivingEntity.class, this.getBoundingBox().expand(6));
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
			} while (livingEntity.squaredDistanceTo(vec3d) > 6.25);

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
							!(livingEntity instanceof SnorkelEntity snorkelEntity && snorkelEntity.isInvisibleSnorkel()) &&
							!(livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && generalPvZombieEntity.isFlying()) && !(livingEntity instanceof GeneralPvZombieEntity zombie && zombie.isHovering())) {
						float damage = 20;
						String zombieMaterial = PvZCubed.ZOMBIE_MATERIAL.get(livingEntity.getType()).orElse("flesh");
						SoundEvent sound;
						sound = switch (zombieMaterial) {
							case "metallic", "electronic" -> PvZSounds.PEAHITEVENT;
							case "plastic" -> PvZSounds.PEAHITEVENT;
							case "stone", "crystal" -> PvZSounds.PEAHITEVENT;
							default -> PvZSounds.PEAHITEVENT;
						};
						livingEntity.playSound(sound, 0.2F, (float) (0.5F + Math.random()));
						if ("metallic".equals(zombieMaterial) || "stone".equals(zombieMaterial) || "electronic".equals(zombieMaterial) || "crystal".equals(zombieMaterial) || "gold".equals(zombieMaterial)) {
							damage = damage * 2;
						}
						if ("paper".equals(zombieMaterial) || "rubber".equals(zombieMaterial) || "cloth".equals(zombieMaterial)) {
							damage = damage / 2;
						}
						if (damage > livingEntity.getHealth() &&
								!(livingEntity instanceof ZombieShieldEntity) &&
								livingEntity.getVehicle() instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity.getHypno())) {
							float damage2 = damage - ((LivingEntity) livingEntity).getHealth();
							livingEntity.damage(getDamageSources().mobProjectile(this, this), damage);
							generalPvZombieEntity.damage(getDamageSources().mobProjectile(this, this), damage2);
						} else {
							livingEntity.damage(getDamageSources().mobProjectile(this, this), damage);
						}
					}
				}
			}
		}
	}


	/** /~*~//~*INTERACTION*~//~*~/ **/

	@Nullable
	@Override
	public ItemStack getPickBlockStack() {
		return ModItems.TALLNUT_SEED_PACKET.getDefaultStack();
	}


	public ActionResult interactMob(PlayerEntity player, Hand hand) {
		ItemStack itemStack = player.getStackInHand(hand);
		if (itemStack.isOf(ModItems.GARDENINGGLOVE)) {
			dropItem(ModItems.TALLNUT_SEED_PACKET);
			if (!player.getAbilities().creativeMode) {
				if (!PVZCONFIG.nestedSeeds.infiniteSeeds() && !getWorld().getGameRules().getBooleanValue(PvZCubed.INFINITE_SEEDS)) {
					itemStack.decrement(1);
				}
			}
			this.discard();
			return ActionResult.SUCCESS;
		}
		return super.interactMob(player, hand);
	}


	/** /~*~//~*ATTRIBUTES*~//~*~/ **/

	public static DefaultAttributeContainer.Builder createTallnutAttributes() {
        return MobEntity.createAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 180D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 5.0);
    }

	protected boolean canClimb() {
		return false;
	}

	public boolean collides() {
		return true;
	}

	protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
		return 0.60F;
	}

	@Nullable
	protected SoundEvent getHurtSound(DamageSource source) {
		return PvZSounds.SILENCEVENET;
	}

	@Nullable
	protected SoundEvent getDeathSound() {
		return PvZSounds.PLANTPLANTEDEVENT;
	}

	public boolean hurtByWater() {
		return false;
	}

	public boolean isPushable() {
		return false;
	}

	protected void pushAway(Entity entity) {
	}

	public boolean startRiding(Entity entity, boolean force) {
		return super.startRiding(entity, force);
	}

	public void stopRiding() {
		super.stopRiding();
		this.prevBodyYaw = 0.0F;
		this.bodyYaw = 0.0F;
	}


	/** /~*~//~*DAMAGE HANDLER*~//~*~/ **/



	public boolean handleFallDamage(float fallDistance, float damageMultiplier) {
		if (fallDistance > 0F) {
			this.playSound(PvZSounds.PLANTPLANTEDEVENT, 0.4F, 1.0F);
			this.discard();
		}
		this.playBlockFallSound();
		return true;
	}
}
