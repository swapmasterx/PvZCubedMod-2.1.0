package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.lostcity.endurian;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.config.ModItems;
import io.github.GrassyDev.pvzmod.registry.entity.damage.PvZDamageTypes;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzgw.summon.plants.dandelionweed.DandelionWeedEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz1.snorkel.SnorkelEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.GeneralPvZombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombiePropEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombieShieldEntity;
import io.github.GrassyDev.pvzmod.sound.PvZSounds;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.Monster;
import net.minecraft.sound.SoundEvent;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.PlantEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameRules;
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

import org.jetbrains.annotations.Nullable;


import java.util.*;
import java.util.stream.Stream;

import static io.github.GrassyDev.pvzmod.PvZCubed.PVZCONFIG;

public class EndurianEntity extends PlantEntity implements GeoEntity {
    private String controllerName = "wallcontroller";


	private AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

    public EndurianEntity(EntityType<? extends EndurianEntity> entityType, World world) {
        super(entityType, world);

    }

	static {
	}

	@Environment(EnvType.CLIENT)
	public void handleStatus(byte status) {
		if (status != 2 && status != 60){
			super.handleStatus(status);
		}
	}

	/** /~*~//~*VARIANTS*~//~*~/ **/

	//Health Stage Counter

	public enum Crack {
		NONE(1.0F),
		LOW(0.75F),
		MEDIUM(0.5F),
		HIGH(0.25F);

		private static final List<Crack> VALUES = Stream.of(values()).sorted(Comparator.comparingDouble((crack) -> {
			return (double)crack.maxHealthFraction;
		})).collect(ImmutableList.toImmutableList());
		private final float maxHealthFraction;

		Crack(float maxHealthFraction) {
			this.maxHealthFraction = maxHealthFraction;
		}

		public static EndurianEntity.Crack from(float healthFraction) {
			Iterator<Crack> var1 = VALUES.iterator();

			EndurianEntity.Crack crack;
			do {
				if (!var1.hasNext()) {
					return NONE;
				}

				crack = (EndurianEntity.Crack)var1.next();
			} while(!(healthFraction < crack.maxHealthFraction));

			return crack;
		}
	}

	public EndurianEntity.Crack getCrack() {
		return EndurianEntity.Crack.from(this.getHealth() / this.getMaxHealth());
	}

	public static final Map<Crack, Identifier> LOCATION_BY_VARIANT =
			Util.make(Maps.newEnumMap(Crack.class), (map) -> {
				map.put(Crack.NONE,
						new Identifier(PvZCubed.MOD_ID, "textures/entity/wallnut/endurian.png"));
				map.put(Crack.LOW,
						new Identifier(PvZCubed.MOD_ID, "textures/entity/wallnut/endurian.png"));
				map.put(Crack.MEDIUM,
						new Identifier(PvZCubed.MOD_ID, "textures/entity/wallnut/endurian_dmg1.png"));
				map.put(Crack.HIGH,
						new Identifier(PvZCubed.MOD_ID, "textures/entity/wallnut/endurian_dmg2.png"));
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
        if (this.getCrack().equals(Crack.NONE)){
            event.getController().setAnimation(RawAnimation.begin().thenLoop("wallnut.idle"));
        }
        else {
			event.getController().setAnimation(RawAnimation.begin().thenLoop("wallnut.damage"));
        }
        return PlayState.CONTINUE;
    }


	/** /~*~//~*AI*~//~*~/ **/

	protected void initGoals() {
		this.goalSelector.add(4, new EndurianEntity.FireBeamGoal(this));

		this.goalSelector.add(4, new LookAtEntityGoal(this, HostileEntity.class, 10.0F));
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
		BlockPos blockPos = this.getBlockPos();
		if (tickDelay <= 1) {
			BlockPos blockPos2 = this.getBlockPos();
			BlockState blockState = this.getLandingBlockState();
			if ((!blockPos2.equals(blockPos) || !blockState.hasSolidTopSurface(getWorld(), this.getBlockPos(), this)) && !this.hasVehicle()) {
				if (!this.getWorld().isClient && this.getWorld().getGameRules().getBooleanValue(GameRules.DO_MOB_LOOT) && !this.naturalSpawn && this.age <= 10 && !this.dead){
					this.dropItem(ModItems.ENDURIAN_SEED_PACKET);
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


	/** /~*~//~*INTERACTION*~//~*~/ **/
	protected void splashDamage() {
		Vec3d vec3d = this.getPos();
		List<LivingEntity> list = this.getWorld().getNonSpectatingEntities(LivingEntity.class, this.getBoundingBox().expand(2));
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
			} while (this.squaredDistanceTo(livingEntity) > 16);

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
				if (!getWorld().isClient &&
					!(zombiePropEntity2 != null && !(zombiePropEntity2 instanceof ZombieShieldEntity)) &&
					!(zombiePropEntity3 != null && !(zombiePropEntity3 instanceof ZombieShieldEntity)) &&
					!(livingEntity instanceof SnorkelEntity snorkelEntity && snorkelEntity.isInvisibleSnorkel())) {
					String zombieMaterial = PvZCubed.ZOMBIE_MATERIAL.get(livingEntity.getType()).orElse("flesh");
					SoundEvent sound;
					sound = switch (zombieMaterial) {
						case "metallic", "electronic" -> PvZSounds.PEAHITEVENT;
						case "plastic" -> PvZSounds.PEAHITEVENT;
						case "stone", "crystal" -> PvZSounds.PEAHITEVENT;
						default -> PvZSounds.PEAHITEVENT;
					};
					livingEntity.playSound(sound, 0.2F, (float) (0.5F + Math.random()));
					float damage = 3F;
					if (damage > livingEntity.getHealth() &&
						!(livingEntity instanceof ZombieShieldEntity) &&
						livingEntity.getVehicle() instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity.getHypno())) {
						float damage2 = damage - ((LivingEntity) livingEntity).getHealth();
						livingEntity.damage(getDamageSources().mobAttack(this), 0);
						livingEntity.damage(PvZDamageTypes.of(getWorld(), PvZDamageTypes.GENERIC_ANTI_IFRAME), damage);
						generalPvZombieEntity.damage(PvZDamageTypes.of(getWorld(), PvZDamageTypes.GENERIC_ANTI_IFRAME), damage);
					} else {
						livingEntity.damage(getDamageSources().mobAttack(this), 0);
						livingEntity.damage(PvZDamageTypes.of(getWorld(), PvZDamageTypes.GENERIC_ANTI_IFRAME), damage);
					}
				}
			}
		}
	}
	@Nullable
	@Override
	public ItemStack getPickBlockStack() {
		return ModItems.ENDURIAN_SEED_PACKET.getDefaultStack();
	}

	public ActionResult interactMob(PlayerEntity player, Hand hand) {
		ItemStack itemStack = player.getStackInHand(hand);
		if (itemStack.isOf(ModItems.GARDENINGGLOVE)) {
			dropItem(ModItems.ENDURIAN_SEED_PACKET);
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

	public static DefaultAttributeContainer.Builder createEndurianAttributes() {
        return MobEntity.createAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 120D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0D)
				.add(EntityAttributes.GENERIC_FOLLOW_RANGE, 3.0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0);
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
	static class FireBeamGoal extends Goal {
		private final EndurianEntity endurianEntity;
		private int beamTicks;
		private int animationTicks;

		public FireBeamGoal(EndurianEntity endurianEntity) {
			this.endurianEntity = endurianEntity;
			this.setControls(EnumSet.of(Control.MOVE, Control.LOOK));
		}

		public boolean canStart() {
			LivingEntity livingEntity = this.endurianEntity.getTarget();
			return livingEntity != null && livingEntity.isAlive();
		}

		public boolean shouldContinue() {
			return super.shouldContinue();
		}

		public void start() {
			this.beamTicks = -6;
			this.animationTicks = -11;
			this.endurianEntity.getNavigation().stop();
			this.endurianEntity.getLookControl().lookAt(this.endurianEntity.getTarget(), 90.0F, 90.0F);
			this.endurianEntity.velocityDirty = true;
		}

		public void stop() {
			this.endurianEntity.getWorld().sendEntityStatus(this.endurianEntity, (byte) 110);
			this.endurianEntity.setTarget((LivingEntity)null);
		}

		public void tick() {
			LivingEntity livingEntity = this.endurianEntity.getTarget();
			this.endurianEntity.getNavigation().stop();
			this.endurianEntity.getLookControl().lookAt(livingEntity, 90.0F, 90.0F);
			if ((!this.endurianEntity.canSee(livingEntity)) &&
				this.animationTicks >= 0) {
				this.endurianEntity.setTarget((LivingEntity) null);
			} else {
				this.endurianEntity.getWorld().sendEntityStatus(this.endurianEntity, (byte) 111);
				++this.beamTicks;
				++this.animationTicks;
				if (this.beamTicks >= 0 && this.animationTicks <= -5) {
					if (!this.endurianEntity.isInsideWaterOrBubbleColumn()) {
						this.beamTicks = -6;
						this.endurianEntity.getWorld().sendEntityStatus(this.endurianEntity, (byte) 111);
						this.endurianEntity.playSound(PvZSounds.PEASHOOTEVENT, 0.2F, 1);
						this.endurianEntity.splashDamage();
						this.endurianEntity.getWorld().sendEntityStatus(this.endurianEntity, (byte) 106);
					}
				}
				else if (this.animationTicks >= 0)
				{
					this.endurianEntity.getWorld().sendEntityStatus(this.endurianEntity, (byte) 110);
					this.beamTicks = -6;
					this.animationTicks = -11;
				}
				super.tick();
			}
		}
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
