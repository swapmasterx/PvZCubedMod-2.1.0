package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.tile.cheese;

import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.entity.environment.TileEntity;
import io.github.GrassyDev.pvzmod.registry.entity.environment.cheesetile.CheeseTile;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.PvZProjectileEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.EndGatewayBlockEntity;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
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



import java.util.List;
import java.util.UUID;

public class CheeseProjEntity extends PvZProjectileEntity implements IAnimatable {

	private String controllerName = "projectilecontroller";
	private AnimationFactory factory = GeckoLibUtil.createFactory(this);

	private LivingEntity target;

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

    public CheeseProjEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
		this.setNoGravity(false);
    }

    public CheeseProjEntity(World world, LivingEntity owner) {
        super(EntityType.SNOWBALL, owner, world);
    }

    @Environment(EnvType.CLIENT)
    public CheeseProjEntity(World world, double x, double y, double z, float yaw, float pitch, int interpolation, boolean interpolate, int id, UUID uuid) {
        super(PvZEntity.PEPPERPROJ, world);
        updatePosition(x, y, z);
        updateTrackedPositionAndAngles(x, y, z, yaw, pitch, interpolation, interpolate);
		setId(id);
        setUuid(uuid);
    }

	public LivingEntity getTarget (LivingEntity target){
		return this.target = target;
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
			this.getWorld().sendEntityStatus(this, (byte)3);
            this.remove(RemovalReason.DISCARDED);
        }

        if (!this.getWorld().isClient && this.age >= 120) {
			this.getWorld().sendEntityStatus(this, (byte)3);
            this.remove(RemovalReason.DISCARDED);
        }
    }

    @Override
    protected Item getDefaultItem() {
        return null;
    }

	public void createCheeseTile(BlockPos blockPos){
		if (this.getWorld() instanceof ServerWorld serverWorld) {
			List<TileEntity> tileCheck = world.getNonSpectatingEntities(TileEntity.class, PvZEntity.PEASHOOTER.getDimensions().getBoxAt(blockPos.getX(), blockPos.getY(), blockPos.getZ()).expand(-0.5f, 0, -0.5f));
			if (tileCheck.isEmpty()) {
				CheeseTile tile = (CheeseTile) PvZEntity.CHEESETILE.create(world);
				tile.refreshPositionAndAngles(blockPos.getX(), blockPos.getY(), blockPos.getZ(), 0, 0);
				tile.initialize(serverWorld, world.getLocalDifficulty(blockPos), SpawnReason.SPAWN_EGG, (EntityData) null, (NbtCompound) null);
				tile.setPersistent();
				tile.setHeadYaw(0);
				serverWorld.spawnEntityAndPassengers(tile);
			}
		}
	}



	@Environment(EnvType.CLIENT)
    public void handleStatus(byte status) {
		if (status != 2 && status != 60){
			super.handleStatus(status);
		}

    }
    protected void onBlockHit(BlockHitResult blockHitResult) {
		super.onBlockHit(blockHitResult);
		createCheeseTile(blockHitResult.getBlockPos());
		this.discard();
    }

    public boolean collides() {
        return false;
    }
}
