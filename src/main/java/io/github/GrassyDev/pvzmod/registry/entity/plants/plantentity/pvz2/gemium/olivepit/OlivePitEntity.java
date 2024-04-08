package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.gemium.olivepit;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.items.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.sound.PvZSounds;
import io.github.GrassyDev.pvzmod.registry.entity.environment.TileEntity;
import io.github.GrassyDev.pvzmod.registry.entity.environment.bananatile.BananaTile;
import io.github.GrassyDev.pvzmod.registry.entity.environment.oiltile.OilTile;
import io.github.GrassyDev.pvzmod.registry.entity.environment.target.missiletoe.MissileToeTarget;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.PlantEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.lilypad.LilyPadEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.upgrades.spikerock.SpikerockEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1c.endless.oxygen.bubble.BubblePadEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.GeneralPvZombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombiePropEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombieShieldEntity;
import io.github.GrassyDev.pvzmod.items.seedpackets.GatlingpeaSeeds;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.random.RandomGenerator;
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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static io.github.GrassyDev.pvzmod.PvZCubed.PVZCONFIG;

public class OlivePitEntity extends PlantEntity implements GeoEntity {

    private AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);
	private String controllerName = "olivepit";

    public OlivePitEntity(EntityType<? extends OlivePitEntity> entityType, World world) {
        super(entityType, world);

    }

	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(DATA_ID_TYPE_COUNT, false);
		this.dataTracker.startTracking(CHEWTIME, 0);
	}

	public void writeCustomDataToNbt(NbtCompound tag) {
		super.writeCustomDataToNbt(tag);
		tag.putBoolean("Prepared", this.getOlivePit());
		tag.putInt("Count", this.getTypeCount());
	}

	public void readCustomDataFromNbt(NbtCompound tag) {
		super.readCustomDataFromNbt(tag);
		this.dataTracker.set(DATA_ID_TYPE_COUNT, tag.getBoolean("Prepared"));
		this.dataTracker.set(CHEWTIME, tag.getInt("Count"));
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

	private static final TrackedData<Boolean> DATA_ID_TYPE_COUNT =
			DataTracker.registerData(OlivePitEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

	public enum OlivePit {
		FALSE(false),
		TRUE(true);

		OlivePit(boolean id) {
			this.id = id;
		}

		private final boolean id;

		public boolean getId() {
			return this.id;
		}
	}

	public Boolean getOlivePit() {
		return this.dataTracker.get(DATA_ID_TYPE_COUNT);
	}

	private void setOlivePit(OlivePitEntity.OlivePit olivePit) {
		this.dataTracker.set(DATA_ID_TYPE_COUNT, olivePit.getId());
	}

	//Counter

	private static final TrackedData<Integer> CHEWTIME =
			DataTracker.registerData(OlivePitEntity.class, TrackedDataHandlerRegistry.INTEGER);

	public int getTypeCount() {
		return this.dataTracker.get(CHEWTIME);
	}

	public void setCount(Integer count) {
		this.dataTracker.set(CHEWTIME, count);
	}

	public void reduceCount(){
		int count = getTypeCount();
		this.dataTracker.set(CHEWTIME, count - 1);
	}


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
		int i = this.getTypeCount();
		if (!this.getOlivePit()) {
			event.getController().setAnimation(RawAnimation.begin().thenPlay("olivepit.burrow"));
		}
		else if (this.attacking) {
			event.getController().setAnimation(RawAnimation.begin().thenPlay("olivepit.swallow"));
		}
		else if (i > 0) {
			event.getController().setAnimation(RawAnimation.begin().thenLoop("olivepit.idle2"));
		}
		else {
			event.getController().setAnimation(RawAnimation.begin().thenLoop("olivepit.idle"));
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
			} while (this.squaredDistanceTo(livingEntity) > 1);

			if (livingEntity.isAlive() && (livingEntity instanceof Monster &&
					!(livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity
							&& (generalPvZombieEntity.getHypno())) &&
					!(livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity1 &&
							generalPvZombieEntity1.isFlying()) && !(livingEntity instanceof GeneralPvZombieEntity zombie && zombie.isHovering())) && !livingEntity.isInsideWaterOrBubbleColumn() && !(livingEntity instanceof ZombieShieldEntity) &&
					!(PvZCubed.ZOMBIE_SIZE.get(livingEntity.getType()).orElse("flesh").equals("big") ||
							PvZCubed.ZOMBIE_SIZE.get(livingEntity.getType()).orElse("flesh").equals("gargantuar"))) {
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
						case "metallic", "electronic" -> PvZSounds.BUCKETHITEVENT;
						case "plastic" -> PvZSounds.CONEHITEVENT;
						case "stone", "crystal" -> PvZSounds.STONEHITEVENT;
						default -> PvZSounds.PEAHITEVENT;
					};
					livingEntity.playSound(SoundEvents.ENTITY_PLAYER_BURP, 0.4F, (float) (0.5F + Math.random()));
					float damage = Integer.MAX_VALUE;
					if (damage > livingEntity.getHealth() &&
							!(livingEntity instanceof ZombieShieldEntity) &&
							livingEntity.getVehicle() instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity.getHypno())) {
						if (livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity1){
							generalPvZombieEntity1.swallowed = true;
						}
						generalPvZombieEntity.swallowed = true;
						livingEntity.damage(getDamageSources().mobAttack(this), damage);
						generalPvZombieEntity.damage(getDamageSources().mobAttack(this), Integer.MAX_VALUE);
					} else {
						if (livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity1){
							generalPvZombieEntity1.swallowed = true;
						}
						livingEntity.damage(getDamageSources().mobAttack(this), damage);
					}
					if (!PvZCubed.ZOMBIE_SIZE.get(livingEntity.getType()).orElse("flesh").equals("small")){
						this.setCount(800);
						this.attackTicks = 20;
					}
					this.zombieList.add(livingEntity);
					BlockPos blockPos = new BlockPos(this.getBlockPos().getX() + this.getWorld().random.range(-1, 1), this.getBlockPos().getY(), this.getBlockPos().getZ() + this.getWorld().random.range(-1, 1));
					BlockPos blockPos2 = new BlockPos(this.getBlockPos().getX() + this.getWorld().random.range(-1, 1), this.getBlockPos().getY(), this.getBlockPos().getZ() + this.getWorld().random.range(-1, 1));
					BlockPos blockPos3 = new BlockPos(this.getBlockPos().getX() + this.getWorld().random.range(-1, 1), this.getBlockPos().getY(), this.getBlockPos().getZ() + this.getWorld().random.range(-1, 1));
					this.createOilTile(blockPos);
					this.createOilTile(blockPos2);
					this.createOilTile(blockPos3);
					break;
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

	private int attackTicks = 20;
	private int burrowTicks = 30;
	private int spitTicks = 0;
	private int addParticle = 0;

	private int prevCount;

	public void tick() {
		super.tick();
		if (this.getVehicle() instanceof LilyPadEntity ||
				this.getVehicle() instanceof BubblePadEntity){
			this.discard();
		}
		if (--spitTicks <= 0 && this.getOlivePit() && this.getTypeCount() <= 0){
			this.spitTicks = this.getWorld().random.range(20, 100);
			BlockPos blockPos = new BlockPos(this.getBlockPos().getX() + this.getWorld().random.range(-1, 1), this.getBlockPos().getY(), this.getBlockPos().getZ() + this.getWorld().random.range(-1, 1));
			this.createOilTile(blockPos);
		}
		if (--burrowTicks <= 0 && !this.getOlivePit()){
			this.setOlivePit(OlivePit.TRUE);
		}
		if (burrowTicks <= 13 && burrowTicks >= 5 && !this.getOlivePit()){
			++addParticle;
			BlockState blockState = this.getLandingBlockState();
			for(int i = 0; i <= addParticle; ++i) {
				RandomGenerator randomGenerator = this.getRandom();
				double d = this.getX() + (double)MathHelper.nextBetween(randomGenerator, -0.3F, 0.3F);
				double e = this.getY();
				double f = this.getZ() + (double)MathHelper.nextBetween(randomGenerator, -0.3F, 0.3F);
				this.getWorld().addParticle(new BlockStateParticleEffect(ParticleTypes.BLOCK, blockState), d, e, f, 0.0, 0.0, 0.0);
				if (addParticle % 2 == 0){
					this.playSound(blockState.getSoundGroup().getBreakSound(), 0.1f, 1f);
				}
			}
		}
		BlockPos blockPos = this.getBlockPos();
		if (tickDelay <= 1) {
			BlockPos blockPos2 = this.getBlockPos();
			BlockState blockState = this.getLandingBlockState();
			if ((!blockPos2.equals(blockPos) || !blockState.hasSolidTopSurface(getWorld(), this.getBlockPos(), this)) && !this.hasVehicle()) {
				if (!this.getWorld().isClient && this.getWorld().getGameRules().getBooleanValue(GameRules.DO_MOB_LOOT) && !this.naturalSpawn && this.age <= 10 && !this.dead){
					this.dropItem(ModItems.OLIVEPIT_SEED_PACKET);
				}
				this.discard();
			}
		}
		if (attacking){
			if (--attackTicks <= 0){
				attacking = false;
				attackTicks = 20;
			}
		}

		if (this.getOlivePit()) {
			if (this.getTypeCount() > 0) {
				this.reduceCount();
			} else {
				this.zombieList.clear();
				if (!this.hasStatusEffect(PvZCubed.DISABLE)) {
					this.damageEntity();
				}
			}
		}
		if (this.getTypeCount() > prevCount){
			this.attacking = true;
		}
		prevCount = this.getTypeCount();
	}

	public void tickMovement() {
		super.tickMovement();
		if (!this.getWorld().isClient && this.isAlive() && this.isInsideWaterOrBubbleColumn() && this.deathTime == 0) {
			this.discard();
		}
	}

	public void createOilTile(BlockPos blockPos){
		if (this.getWorld() instanceof ServerWorld serverWorld) {
			List<TileEntity> tileCheck = getWorld().getNonSpectatingEntities(TileEntity.class, PvZEntity.PEASHOOTER.getDimensions().getBoxAt(blockPos.getX(), blockPos.getY(), blockPos.getZ()).expand(-0.5f, -0.5f, -0.5f));
			tileCheck.removeIf(tile -> tile instanceof MissileToeTarget);
			tileCheck.removeIf(tile -> tile instanceof BananaTile);
			if (tileCheck.isEmpty()) {
				OilTile tile = (OilTile) PvZEntity.OILTILE.create(getWorld());
				tile.refreshPositionAndAngles(blockPos.getX(), blockPos.getY(), blockPos.getZ(), 0, 0);
				tile.initialize(serverWorld, getWorld().getLocalDifficulty(blockPos), SpawnReason.SPAWN_EGG, (EntityData) null, (NbtCompound) null);
				tile.setPersistent();
				tile.setHeadYaw(0);
				serverWorld.spawnEntityAndPassengers(tile);
			}
		}
	}


	/** /~*~//~*INTERACTION*~//~*~/ **/

	@Nullable
	@Override
	public ItemStack getPickBlockStack() {
		return ModItems.OLIVEPIT_SEED_PACKET.getDefaultStack();
	}


	public ActionResult interactMob(PlayerEntity player, Hand hand) {
		ItemStack itemStack = player.getStackInHand(hand);
		if (itemStack.isOf(ModItems.GARDENINGGLOVE)) {
			dropItem(ModItems.OLIVEPIT_SEED_PACKET);
			if (!player.getAbilities().creativeMode) {
				if (!PVZCONFIG.nestedSeeds.infiniteSeeds() && !getWorld().getGameRules().getBooleanValue(PvZCubed.INFINITE_SEEDS)) {
					itemStack.decrement(1);
				}
			}
			this.discard();
			return ActionResult.SUCCESS;
		}
		Item item = itemStack.getItem();
		if (itemStack.isOf(ModItems.SPIKEROCK_SEED_PACKET) && !player.getItemCooldownManager().isCoolingDown(item)) {
			this.playSound(PvZSounds.PLANTPLANTEDEVENT);
			if ((this.getWorld() instanceof ServerWorld)) {
				ServerWorld serverWorld = (ServerWorld) this.getWorld();
				SpikerockEntity upgradeEntity = (SpikerockEntity) PvZEntity.SPIKEROCK.create(getWorld());
				upgradeEntity.setTarget(this.getTarget());
				upgradeEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), this.getPitch());
				upgradeEntity.initialize(serverWorld, getWorld().getLocalDifficulty(upgradeEntity.getBlockPos()), SpawnReason.SPAWN_EGG, (EntityData) null, (NbtCompound) null);
				upgradeEntity.setAiDisabled(this.isAiDisabled());
				if (this.hasCustomName()) {
					upgradeEntity.setCustomName(this.getCustomName());
					upgradeEntity.setCustomNameVisible(this.isCustomNameVisible());
				}
				if (this.hasVehicle()){
					upgradeEntity.startRiding(this.getVehicle(), true);
				}

				upgradeEntity.setPersistent();
				serverWorld.spawnEntityAndPassengers(upgradeEntity);
				this.remove(RemovalReason.DISCARDED);
			}
			if (!player.getAbilities().creativeMode) {
				if (!PVZCONFIG.nestedSeeds.infiniteSeeds() && !getWorld().getGameRules().getBooleanValue(PvZCubed.INFINITE_SEEDS)) {
					itemStack.decrement(1);
				}
				;
				if (!PVZCONFIG.nestedSeeds.instantRecharge() && !getWorld().getGameRules().getBooleanValue(PvZCubed.INSTANT_RECHARGE)) {
					player.getItemCooldownManager().set(ModItems.SPIKEROCK_SEED_PACKET, GatlingpeaSeeds.cooldown);
				}
			}
			return ActionResult.SUCCESS;
		}
		return super.interactMob(player, hand);
	}


	/** /~*~//~*ATTRIBUTES*~//~*~/ **/

	public static DefaultAttributeContainer.Builder createOlivePitAttributes() {
        return MobEntity.createAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 8D)
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
