package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2c.browncoat.fairytale;


import io.github.GrassyDev.pvzmod.registry.ModItems;
import io.github.GrassyDev.pvzmod.registry.entity.variants.zombies.BrowncoatVariants;
import io.github.GrassyDev.pvzmod.registry.entity.variants.zombies.PokerVariants;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz1.browncoat.modernday.BrowncoatEntity;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.util.GeckoLibUtil;
import io.github.GrassyDev.pvzmod.registry.entity.damage.PvZDamageTypes;

import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.util.GeckoLibUtil;
import io.github.GrassyDev.pvzmod.registry.entity.damage.PvZDamageTypes;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.core.animation.AnimationState;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

import static io.github.GrassyDev.pvzmod.PvZCubed.MOD_ID;
import static io.github.GrassyDev.pvzmod.PvZCubed.PVZCONFIG;

public class PokerEntity extends BrowncoatEntity {
	public PokerEntity(EntityType<? extends BrowncoatEntity> entityType, World world) {
		super(entityType, world);
	}

	private int pawnTicks = 100;
	private int knightTicks = 150;
	private int flyDelay = 0;

	public boolean resetAttribute = false;

	@Override
	public void tick() {
		if (resetAttribute){
			if (this.getAttributes().hasModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, POKER_SPEED_UUID)) {
				EntityAttributeInstance maxSpeedAttribute = this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
				maxSpeedAttribute.removeModifier(POKER_SPEED_UUID);
			}
			if (this.getAttributes().hasModifier(EntityAttributes.GENERIC_MAX_HEALTH, POKER_HEALTH_UUID)) {
				EntityAttributeInstance maxHealthAttribute = this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
				maxHealthAttribute.removeModifier(POKER_HEALTH_UUID);
			}
			if (this.getAttributes().hasModifier(EntityAttributes.GENERIC_ATTACK_DAMAGE, POKER_ATTACK_UUID)) {
				EntityAttributeInstance maxAttackAttribute = this.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE);
				maxAttackAttribute.removeModifier(POKER_ATTACK_UUID);
			}
			resetAttribute = false;
		}
		if (this.getPoker().equals(PokerVariants.SPADE)){
			if (!this.getAttributes().hasModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, POKER_SPEED_UUID)) {
				EntityAttributeInstance maxSpeedAttribute = this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
				maxSpeedAttribute.addPersistentModifier(createSpeedModifier(0.04));
			}
			if (!this.getAttributes().hasModifier(EntityAttributes.GENERIC_MAX_HEALTH, POKER_HEALTH_UUID)) {
				EntityAttributeInstance maxHealthAttribute = this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
				maxHealthAttribute.addPersistentModifier(createHealthModifier( PVZCONFIG.nestedZombieHealth.pokerspadeH() - PVZCONFIG.nestedZombieHealth.pokerheartH()));
				this.setHealth(this.getMaxHealth());
			}
			if (!this.getAttributes().hasModifier(EntityAttributes.GENERIC_ATTACK_DAMAGE, POKER_ATTACK_UUID)) {
				EntityAttributeInstance maxAttackAttribute = this.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE);
				maxAttackAttribute.addPersistentModifier(createAttackModifier( 6));
			}
		}
		if (this.getPoker().equals(PokerVariants.DIAMOND)){
			if (!this.getAttributes().hasModifier(EntityAttributes.GENERIC_MAX_HEALTH, POKER_HEALTH_UUID)) {
				EntityAttributeInstance maxHealthAttribute = this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
				maxHealthAttribute.addPersistentModifier(createHealthModifier( PVZCONFIG.nestedZombieHealth.pokerdiamondH() - PVZCONFIG.nestedZombieHealth.pokerheartH()));
				this.setHealth(this.getMaxHealth());
			}
			if (!this.getAttributes().hasModifier(EntityAttributes.GENERIC_ATTACK_DAMAGE, POKER_ATTACK_UUID)) {
				EntityAttributeInstance maxAttackAttribute = this.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE);
				maxAttackAttribute.addPersistentModifier(createAttackModifier( 6));
			}
		}
		if (this.getPoker().equals(PokerVariants.CLUB)){
			if (!this.getAttributes().hasModifier(EntityAttributes.GENERIC_MAX_HEALTH, POKER_HEALTH_UUID)) {
				EntityAttributeInstance maxHealthAttribute = this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
				maxHealthAttribute.addPersistentModifier(createHealthModifier( PVZCONFIG.nestedZombieHealth.pokerclubH() - PVZCONFIG.nestedZombieHealth.pokerheartH()));
				this.setHealth(this.getMaxHealth());
			}
			if (!this.getAttributes().hasModifier(EntityAttributes.GENERIC_ATTACK_DAMAGE, POKER_ATTACK_UUID)) {
				EntityAttributeInstance maxAttackAttribute = this.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE);
				maxAttackAttribute.addPersistentModifier(createAttackModifier( 12));
			}
		}
		super.tick();
		if (this.getTarget() != null){
			--pawnTicks;
			--knightTicks;
		}
		EntityAttributeInstance maxSpeedAttribute = this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
		if (!this.getWorld().isClient()) {
			if (this.getVariant().equals(BrowncoatVariants.POKERPAWN) || this.getVariant().equals(BrowncoatVariants.POKERPAWNHYPNO)) {
				if (pawnTicks <= 0) {
					if (this.getAttributes().hasModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, TEMP_SPEED_UUID)) {
						assert maxSpeedAttribute != null;
						maxSpeedAttribute.removeModifier(TEMP_SPEED_UUID);
					}
				} else {
					if (!this.getAttributes().hasModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, TEMP_SPEED_UUID)) {
						assert maxSpeedAttribute != null;
						maxSpeedAttribute.addPersistentModifier(createTempSpeedModifier(0.04));
					}
				}
			}
		}
		if (!this.getWorld().isClient()){
			if (this.getVariant().equals(BrowncoatVariants.POKERKNIGHT) || this.getVariant().equals(BrowncoatVariants.POKERKNIGHTHYPNO)) {
				--flyDelay;
				if (knightTicks <= 0){
					Vec3d vec3d2 = new Vec3d((double) 0.6, 0.8, 0).rotateY(-this.getHeadYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
					this.addVelocity(vec3d2.x, vec3d2.y, vec3d2.z);
					knightTicks = 100;
					flyDelay = 10;
					this.setFlying(Flying.TRUE);
				}
				if ((this.isOnGround() || isInsideWaterOrBubbleColumn()) && flyDelay <= 0){
					this.setFlying(Flying.FALSE);
				}
			}
		}
		if (!this.getWorld().isClient()) {
			if (this.getVariant().equals(BrowncoatVariants.POKERTOWER) || this.getVariant().equals(BrowncoatVariants.POKERTOWERHYPNO)) {
				boolean stop = false;
				for (float x = 0; x <= 6f; ++x) {
					if (this.CollidesWithPlant(x, 0f) != null) {
						stop = true;
					}
				}
				if (stop) {
					if (this.getAttributes().hasModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, TEMP_SPEED_UUID)) {
						assert maxSpeedAttribute != null;
						maxSpeedAttribute.removeModifier(TEMP_SPEED_UUID);
					}
					if (!this.getAttributes().hasModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, TEMP_SPEED_UUID2)) {
						assert maxSpeedAttribute != null;
						maxSpeedAttribute.addPersistentModifier(createTempSpeed2Modifier(-0.02));
					}
				} else {
					if (this.getAttributes().hasModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, TEMP_SPEED_UUID2)) {
						assert maxSpeedAttribute != null;
						maxSpeedAttribute.removeModifier(TEMP_SPEED_UUID2);
					}
					if (!this.getAttributes().hasModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, TEMP_SPEED_UUID)) {
						assert maxSpeedAttribute != null;
						maxSpeedAttribute.addPersistentModifier(createTempSpeedModifier(0.04));
					}
				}
			}
		}
	}

	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(POKER_TYPE, 0);
	}

	public void writeCustomDataToNbt(NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);
		nbt.putInt("poker", this.getPokerType());
	}

	public void readCustomDataFromNbt(NbtCompound nbt) {
		super.readCustomDataFromNbt(nbt);
		this.dataTracker.set(POKER_TYPE, nbt.getInt("poker"));
	}

	@Override
	protected <P extends GeoAnimatable> PlayState predicate(AnimationState<P> event) {
		super.predicate(event);
		if (this.getPoker().equals(PokerVariants.SPADE)){
			if (this.isFrozen || this.isStunned) {
				event.getController().setAnimationSpeed(0);
			}
			else if (this.isIced) {
				event.getController().setAnimationSpeed(0.75);
			}
			else {
				event.getController().setAnimationSpeed(1.5);
			}
		}
		return PlayState.CONTINUE;
	}


	/** /~*~//~*VARIANTS*~//~*~/ **/

	private static final TrackedData<Integer> POKER_TYPE =
			DataTracker.registerData(PokerEntity.class, TrackedDataHandlerRegistry.INTEGER);

	public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty,
								 SpawnReason spawnReason, @Nullable EntityData entityData,
								 @Nullable NbtCompound entityNbt) {
		this.setVariant(PokerVariants.byId(world.getRandom().nextInt(PokerVariants.values().length)));
		return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
	}

	private int getPokerType() {
		return this.dataTracker.get(POKER_TYPE);
	}

	public PokerVariants getPoker() {
		return PokerVariants.byId(this.getPokerType() & 255);
	}

	public void setVariant(PokerVariants variant) {
		this.dataTracker.set(POKER_TYPE, variant.getId() & 255);
	}

	/** /~*~//~*ATTRIBUTES*~//~*~/ **/

	public static final UUID POKER_SPEED_UUID = UUID.nameUUIDFromBytes(MOD_ID.getBytes(StandardCharsets.UTF_16LE));

	public static EntityAttributeModifier createSpeedModifier(double amount) {
		return new EntityAttributeModifier(
				POKER_SPEED_UUID,
				MOD_ID,
				amount,
				EntityAttributeModifier.Operation.ADDITION
		);
	}

	public static final UUID POKER_HEALTH_UUID = UUID.nameUUIDFromBytes(MOD_ID.getBytes(StandardCharsets.UTF_16LE));

	public static EntityAttributeModifier createHealthModifier(double amount) {
		return new EntityAttributeModifier(
				POKER_HEALTH_UUID,
				MOD_ID,
				amount,
				EntityAttributeModifier.Operation.ADDITION
		);
	}

	public static final UUID POKER_ATTACK_UUID = UUID.nameUUIDFromBytes(MOD_ID.getBytes(StandardCharsets.UTF_16LE));

	public static EntityAttributeModifier createAttackModifier(double amount) {
		return new EntityAttributeModifier(
				POKER_ATTACK_UUID,
				MOD_ID,
				amount,
				EntityAttributeModifier.Operation.ADDITION
		);
	}

	public static final UUID TEMP_SPEED_UUID = UUID.nameUUIDFromBytes(MOD_ID.getBytes(StandardCharsets.UTF_16BE));

	public static EntityAttributeModifier createTempSpeedModifier(double amount) {
		return new EntityAttributeModifier(
				TEMP_SPEED_UUID,
				MOD_ID,
				amount,
				EntityAttributeModifier.Operation.ADDITION
		);
	}

	public static final UUID TEMP_SPEED_UUID2 = UUID.randomUUID();

	public static EntityAttributeModifier createTempSpeed2Modifier(double amount) {
		return new EntityAttributeModifier(
				TEMP_SPEED_UUID2,
				MOD_ID,
				amount,
				EntityAttributeModifier.Operation.ADDITION
		);
	}

	/** /~*~//~*INTERACTION*~//~*~/ **/

	@Nullable
	@Override
	public ItemStack getPickBlockStack() {
		ItemStack itemStack;
		if (this.getVariant().equals(BrowncoatVariants.CONEHEAD) || this.getVariant().equals(BrowncoatVariants.CONEHEADHYPNO)){
			itemStack = ModItems.MUMMYCONEEGG.getDefaultStack();
		}
		else if (this.getVariant().equals(BrowncoatVariants.BUCKETHEAD) || this.getVariant().equals(BrowncoatVariants.BUCKETHEADHYPNO)){
			itemStack = ModItems.MUMMYBUCKETEGG.getDefaultStack();
		}
		else if (this.getVariant().equals(BrowncoatVariants.PYRAMIDHEAD) || this.getVariant().equals(BrowncoatVariants.PYRAMIDHEADHYPNO)){
			itemStack = ModItems.PYRAMIDHEADEGG.getDefaultStack();
		}
		else{
			itemStack = ModItems.MUMMYEGG.getDefaultStack();
		}
		return itemStack;
	}

	/** /~*~//~*ATTRIBUTES*~//~*~/ **/

	public static DefaultAttributeContainer.Builder createPokerHeartAttributes() {
		return HostileEntity.createAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 100.0D)

				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.12D)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 6.0D)
				.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
				.add(EntityAttributes.GENERIC_MAX_HEALTH, PVZCONFIG.nestedZombieHealth.pokerheartH());
	}

	public static DefaultAttributeContainer.Builder createPokerSpadeAttributes() {
		return HostileEntity.createAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 100.0D)

				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.16D)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 12.0D)
				.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
				.add(EntityAttributes.GENERIC_MAX_HEALTH, PVZCONFIG.nestedZombieHealth.pokerspadeH());
	}

	public static DefaultAttributeContainer.Builder createPokerClubAttributes() {
		return HostileEntity.createAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 100.0D)

				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.12D)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 18.0D)
				.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
				.add(EntityAttributes.GENERIC_MAX_HEALTH, PVZCONFIG.nestedZombieHealth.pokerclubH());
	}

	public static DefaultAttributeContainer.Builder createPokerDiamondAttributes() {
		return HostileEntity.createAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 100.0D)

				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.12D)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 12.0D)
				.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
				.add(EntityAttributes.GENERIC_MAX_HEALTH, PVZCONFIG.nestedZombieHealth.pokerdiamondH());
	}
}
