package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.upgrades.spikerock;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.config.ModItems;
import io.github.GrassyDev.pvzmod.registry.entity.damage.PvZDamageTypes;
import io.github.GrassyDev.pvzmod.sound.PvZSounds;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.PlantEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.lilypad.LilyPadEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1c.endless.oxygen.bubble.BubblePadEntity;
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

import java.util.*;
import java.util.stream.Stream;

import static io.github.GrassyDev.pvzmod.PvZCubed.PVZCONFIG;

public class SpikerockEntity extends PlantEntity implements GeoEntity {

    private AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);
	private String controllerName = "spikeweed";

    public SpikerockEntity(EntityType<? extends SpikerockEntity> entityType, World world) {
        super(entityType, world);
		this.setFireImmune(FireImmune.TRUE);

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
		FULL(1.0F),
		DAMAGED(0.67F),
		DYING(0.37F);

		private static final List<SpikerockEntity.Crack> VALUES = (List) Stream.of(values()).sorted(Comparator.comparingDouble((crack) -> {
			return (double)crack.maxHealthFraction;
		})).collect(ImmutableList.toImmutableList());
		private final float maxHealthFraction;

		Crack(float maxHealthFraction) {
			this.maxHealthFraction = maxHealthFraction;
		}

		public static SpikerockEntity.Crack from(float healthFraction) {
			Iterator var1 = VALUES.iterator();

			SpikerockEntity.Crack crack;
			do {
				if (!var1.hasNext()) {
					return FULL;
				}

				crack = (SpikerockEntity.Crack)var1.next();
			} while(!(healthFraction < crack.maxHealthFraction));

			return crack;
		}
	}

	public SpikerockEntity.Crack getCrack() {
		return SpikerockEntity.Crack.from(this.getHealth() / this.getMaxHealth());
	}

	public static final Map<SpikerockEntity.Crack, Identifier> LOCATION_BY_VARIANT =
			Util.make(Maps.newEnumMap(SpikerockEntity.Crack.class), (map) -> {
				map.put(Crack.FULL,
						new Identifier(PvZCubed.MOD_ID, "textures/entity/spikeweed/spikerock.png"));
				map.put(Crack.DAMAGED,
						new Identifier(PvZCubed.MOD_ID, "textures/entity/spikeweed/spikerock_dmg1.png"));
				map.put(Crack.DYING,
						new Identifier(PvZCubed.MOD_ID, "textures/entity/spikeweed/spikerock_dmg2.png"));
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
		if (this.attacking){
			event.getController().setAnimation(RawAnimation.begin().thenLoop("spikeweed.attack"));
		}
		else {
			event.getController().setAnimation(RawAnimation.begin().thenLoop("spikeweed.idle"));
		}
        return PlayState.CONTINUE;
    }

	/** /~*~//~*AI*~//~*~/ **/

	protected void initGoals() {
		this.goalSelector.add(1, new LookAtEntityGoal(this, GeneralPvZombieEntity.class, 2.5F));
	}

	protected boolean attacking = false;
	protected List<LivingEntity> zombieList = new ArrayList<>();
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
			} while (this.squaredDistanceTo(livingEntity) > 3.0625);

			if ((livingEntity instanceof Monster &&
					!(livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity
							&& (generalPvZombieEntity.getHypno())) &&
					!(livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity1 &&
							generalPvZombieEntity1.isFlying()) && !(livingEntity instanceof GeneralPvZombieEntity zombie && zombie.isHovering())) && !livingEntity.isInsideWaterOrBubbleColumn() && !(livingEntity instanceof ZombieShieldEntity)) {
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
				if (zombiePropEntity2 == null ||
				zombiePropEntity2 instanceof ZombieShieldEntity) {
					String zombieMaterial = PvZCubed.ZOMBIE_MATERIAL.get(livingEntity.getType()).orElse("flesh");
					SoundEvent sound;
					sound = switch (zombieMaterial) {
						case "metallic", "electronic" -> PvZSounds.PEAHITEVENT;
						case "plastic" -> PvZSounds.PEAHITEVENT;
						case "stone", "crystal" -> PvZSounds.PEAHITEVENT;
						default -> PvZSounds.PEAHITEVENT;
					};
					livingEntity.playSound(sound, 0.1F, (float) (0.5F + Math.random()));
					float damage = 6;
					if (this.getCrack().equals(Crack.DAMAGED)){
						damage = 5;
					}
					else if (this.getCrack().equals(Crack.DYING)){
						damage = 4;
					}
					if ("metallic".equals(zombieMaterial) || "stone".equals(zombieMaterial) || "electronic".equals(zombieMaterial) || "crystal".equals(zombieMaterial) || "gold".equals(zombieMaterial)) {
						damage = damage * 2;
					}
					if ("paper".equals(zombieMaterial) || "rubber".equals(zombieMaterial) || "cloth".equals(zombieMaterial)) {
						damage = damage / 2;
					}
					if (damage > livingEntity.getHealth() &&
							!(livingEntity instanceof ZombieShieldEntity) &&
							livingEntity.getVehicle() instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity.getHypno())) {
						float damage2 = damage - livingEntity.getHealth();
						livingEntity.damage(PvZDamageTypes.of(getWorld(), PvZDamageTypes.GENERIC_ANTI_IFRAME), damage);
						generalPvZombieEntity.damage(PvZDamageTypes.of(getWorld(), PvZDamageTypes.GENERIC_ANTI_IFRAME), damage2);
					} else {
						livingEntity.damage(PvZDamageTypes.of(getWorld(), PvZDamageTypes.GENERIC_ANTI_IFRAME), damage);
					}
					this.attacking = true;
					this.zombieList.add(livingEntity);
				}
			}
			else {
				this.zombieList.remove(livingEntity);
			}
		}
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
	private int tickDamage = 20;

	public void tick() {
		super.tick();
		if (this.getVehicle() instanceof LilyPadEntity ||
				this.getVehicle() instanceof BubblePadEntity){
			this.discard();
		}
		BlockPos blockPos = this.getBlockPos();
		if (tickDelay <= 1) {
			BlockPos blockPos2 = this.getBlockPos();
			BlockState blockState = this.getLandingBlockState();
			if ((!blockPos2.equals(blockPos) || !blockState.hasSolidTopSurface(getWorld(), this.getBlockPos(), this)) && !this.hasVehicle()) {
				if (!this.getWorld().isClient && this.getWorld().getGameRules().getBooleanValue(GameRules.DO_MOB_LOOT) && !this.naturalSpawn && this.age <= 10 && !this.dead){
					this.dropItem(ModItems.SPIKEROCK_SEED_PACKET);
				}
				this.discard();
			}
		}
		if (zombieList.isEmpty()){
			this.attacking = false;
		}
		if (--tickDamage <= 0){
			this.zombieList.clear();
			if (!this.hasStatusEffect(PvZCubed.DISABLE)) {
				this.damageEntity();
			}
			tickDamage = 20;
		}
	}

	public void tickMovement() {
		super.tickMovement();
		if (!this.getWorld().isClient && this.isAlive() && this.isInsideWaterOrBubbleColumn() && this.deathTime == 0) {
			this.discard();
		}
	}


	/** /~*~//~*INTERACTION*~//~*~/ **/

	@Nullable
	@Override
	public ItemStack getPickBlockStack() {
		return ModItems.SPIKEROCK_SEED_PACKET.getDefaultStack();
	}


	public ActionResult interactMob(PlayerEntity player, Hand hand) {
		ItemStack itemStack = player.getStackInHand(hand);
		if (itemStack.isOf(ModItems.GARDENINGGLOVE)) {
			dropItem(ModItems.SPIKEROCK_SEED_PACKET);
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

	public static DefaultAttributeContainer.Builder createSpikerockAttributes() {
        return MobEntity.createAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 270D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0D)
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
	protected SoundEvent getHurtSound(DamageSource source) {return PvZSounds.SILENCEVENET;}

	@Nullable
	protected SoundEvent getDeathSound() {return PvZSounds.PLANTPLANTEDEVENT;}

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
