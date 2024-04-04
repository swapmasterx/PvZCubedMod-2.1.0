package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2c.heian.dripphylleia;

import io.github.GrassyDev.pvzmod.items.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.sound.PvZSounds;
import io.github.GrassyDev.pvzmod.registry.entity.environment.TileEntity;
import io.github.GrassyDev.pvzmod.registry.entity.environment.cratertile.CraterTile;
import io.github.GrassyDev.pvzmod.registry.entity.environment.watertile.WaterTile;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.PlantEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;

public class DripphylleiaEntity extends PlantEntity implements GeoAnimatable {

    private AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);
	private String controllerName = "nutcontroller";

	protected int turningTicks = 25;

    public DripphylleiaEntity(EntityType<? extends DripphylleiaEntity> entityType, World world) {
        super(entityType, world);

		this.setNoGravity(true);
		this.setImmune(Immune.TRUE);
    }

	static {
	}

	@Environment(EnvType.CLIENT)
	public void handleStatus(byte status) {
		if (status != 2 && status != 60){
			super.handleStatus(status);
		}
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

	@Override
	public double getTick(Object object) {
		return 0;
	}

	private <P extends GeoAnimatable> PlayState predicate(AnimationState<P> event) {
		int i = this.turningTicks;
		event.getController().setAnimation(RawAnimation.begin().thenLoop("drip.idle"));
        return PlayState.CONTINUE;
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

	public void createWaterTile(BlockPos blockPos){
		if (this.getWorld() instanceof ServerWorld serverWorld) {
			List<TileEntity> tileCheck = getWorld().getNonSpectatingEntities(TileEntity.class, PvZEntity.PEASHOOTER.getDimensions().getBoxAt(blockPos.getX(), blockPos.getY(), blockPos.getZ()).expand(-0.5f, -0.5f, -0.5f));
			tileCheck.removeIf(tile -> tile instanceof CraterTile);
			for (TileEntity tile : tileCheck){
				tile.discard();
			}
			WaterTile tile = (WaterTile) PvZEntity.WATERTILE.create(getWorld());
			tile.refreshPositionAndAngles(blockPos.getX(), blockPos.getY(), blockPos.getZ(), 0, 0);
			tile.initialize(serverWorld, getWorld().getLocalDifficulty(blockPos), SpawnReason.SPAWN_EGG, (EntityData) null, (NbtCompound) null);
			tile.setPersistent();
			tile.setHeadYaw(0);
			serverWorld.spawnEntityAndPassengers(tile);
		}
	}

	public void tick() {
		super.tick();
		BlockPos blockPos = this.getBlockPos();
		if (tickDelay <= 1) {
			BlockPos blockPos2 = this.getBlockPos();
			BlockState blockState = this.getLandingBlockState();
			if ((!blockPos2.equals(blockPos) || !blockState.hasSolidTopSurface(getWorld(), this.getBlockPos(), this)) && !this.hasVehicle()) {
				if (!this.getWorld().isClient && this.getWorld().getGameRules().getBoolean(GameRules.DO_MOB_LOOT) && !this.naturalSpawn && this.age <= 10 && !this.dead){
					this.dropItem(ModItems.DRIPPHYLLEIA_SEED_PACKET);
				}
				this.discard();
			}
		}
		if (--turningTicks <=0 ){
			BlockPos bubble = new BlockPos(this.getBlockPos().getX() - 1, this.getBlockPos().getY(), this.getBlockPos().getZ() + 1);
			this.createWaterTile(bubble);
			BlockPos bubble2 = new BlockPos(this.getBlockPos().getX() + 1, this.getBlockPos().getY(), this.getBlockPos().getZ());
			this.createWaterTile(bubble2);
			BlockPos bubble3 = new BlockPos(this.getBlockPos().getX() + 1, this.getBlockPos().getY(), this.getBlockPos().getZ() + 1);
			this.createWaterTile(bubble3);
			BlockPos bubble4 = new BlockPos(this.getBlockPos().getX(), this.getBlockPos().getY(), this.getBlockPos().getZ() + 1);
			this.createWaterTile(bubble4);
			BlockPos bubble5 = new BlockPos(this.getBlockPos().getX() - 1, this.getBlockPos().getY(), this.getBlockPos().getZ());
			this.createWaterTile(bubble5);
			BlockPos bubble6 = new BlockPos(this.getBlockPos().getX() - 1, this.getBlockPos().getY(), this.getBlockPos().getZ() - 1);
			this.createWaterTile(bubble6);
			BlockPos bubble7 = new BlockPos(this.getBlockPos().getX(), this.getBlockPos().getY(), this.getBlockPos().getZ() - 1);
			this.createWaterTile(bubble7);
			BlockPos bubble8 = new BlockPos(this.getBlockPos().getX() + 1, this.getBlockPos().getY(), this.getBlockPos().getZ() - 1);
			this.createWaterTile(bubble8);
			BlockPos bubble9 = new BlockPos(this.getBlockPos().getX(), this.getBlockPos().getY(), this.getBlockPos().getZ());
			this.createWaterTile(bubble9);
			this.playSound(SoundEvents.ENTITY_PLAYER_SPLASH_HIGH_SPEED, 1, 1);
			this.discard();
		}
	}


	public void tickMovement() {
        super.tickMovement();
		if (!this.getWorld().isClient && this.isAlive() && this.isInsideWaterOrBubbleColumn() && this.deathTime == 0) {
			this.clearStatusEffects();
			this.discard();
		}
    }


	/** /~*~//~*INTERACTION*~//~*~/ **/

	@Nullable
	@Override
	public ItemStack getPickBlockStack() {
		return ModItems.DRIPPHYLLEIA_SEED_PACKET.getDefaultStack();
	}


	/** /~*~//~*ATTRIBUTES*~//~*~/ **/

	public static DefaultAttributeContainer.Builder createDripphylleiaAttributes() {
        return MobEntity.createAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 6.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 3D);
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

	@Override
	protected void applyDamage(DamageSource source, float amount) {
		if (this.turningTicks < 0 || source.getAttacker() instanceof PlayerEntity || source.isTypeIn(DamageTypeTags.BYPASSES_COOLDOWN)) {
			super.applyDamage(source, amount);
		}
	}



	public boolean handleFallDamage(float fallDistance, float damageMultiplier) {
		if (fallDistance > 0F) {
			this.playSound(PvZSounds.PLANTPLANTEDEVENT, 0.4F, 1.0F);
			this.discard();
		}
		this.playBlockFallSound();
		return true;
	}
}
