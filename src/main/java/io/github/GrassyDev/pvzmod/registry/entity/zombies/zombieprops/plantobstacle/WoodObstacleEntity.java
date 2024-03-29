package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieprops.plantobstacle;

import io.github.GrassyDev.pvzmod.registry.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.PvZSounds;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.PlantEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.day.chomper.ChomperEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.night.gravebuster.GravebusterEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.spikeweed.SpikeweedEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.upgrades.spikerock.SpikerockEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1c.social.superchomper.SuperChomperEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.gemium.olivepit.OlivePitEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzgw.heroes.plants.chester.ChesterEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2o.hawker.piggy.PiggyEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.GeneralPvZombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombieObstacleEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import com.jamieswhiteshirt.reachentityattributes.ReachEntityAttributes;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;


import java.util.List;

import static io.github.GrassyDev.pvzmod.PvZCubed.PVZCONFIG;

public class WoodObstacleEntity extends ZombieObstacleEntity implements IAnimatable {
    private AnimationFactory factory = GeckoLibUtil.createFactory(this);
    private String controllerName = "shieldcontroller";

    public WoodObstacleEntity(EntityType<? extends WoodObstacleEntity> entityType, World world) {
        super(entityType, world);

        this.experiencePoints = 3;
	}

	public WoodObstacleEntity(World world) {
		this(PvZEntity.HAWKERCART, world);
	}

	static {

	}

	@Environment(EnvType.CLIENT)
	public void handleStatus(byte status) {
		if (status != 2 && status != 60){
			super.handleStatus(status);
		}
	}

	/** /~*~//~*TICKING*~//~*~/ **/

	private int hawkerTicks = 0;
	private int hawkerCount = 0;

	public void tick() {
		super.tick();

		if (this.getWorld() instanceof ServerWorld serverWorld) {
			if (this.getType().equals(PvZEntity.HAWKERCART)) {
				if (++hawkerTicks >= 140) {
					EntityType<?> type = PvZEntity.PIGGY;
					if (this.getVehicle() instanceof GeneralPvZombieEntity generalPvZombieEntity && generalPvZombieEntity.getHypno()){
						type = PvZEntity.PIGGYHYPNO;
					}
					List<PlantEntity> list = world.getNonSpectatingEntities(PlantEntity.class, PvZEntity.PEASHOOTER.getDimensions().getBoxAt(this.getPos()).expand(15));
					if (!list.isEmpty()) {
						PiggyEntity piggyEntity = (PiggyEntity) type.create(this.getWorld());
						piggyEntity.initialize(serverWorld, this.getWorld().getLocalDifficulty(this.getBlockPos()), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
						piggyEntity.refreshPositionAndAngles(this.getBlockPos(), this.getYaw(), 0.0F);
						piggyEntity.setOwner(this);
						piggyEntity.setHeadYaw(this.getHeadYaw());
						piggyEntity.setYaw(this.getYaw());
						piggyEntity.setBodyYaw(this.bodyYaw);
						serverWorld.spawnEntityAndPassengers(piggyEntity);
						hawkerTicks = 0;
						++hawkerCount;
					}
				}
			}
		}
		if (hawkerCount >= 5){
			this.discard();
		}
		if (this.hasVehicle() && this.getVehicle() instanceof GeneralPvZombieEntity generalPvZombieEntity && (generalPvZombieEntity.getHealth() <= 0 || generalPvZombieEntity.isDead())){
			this.dismountVehicle();
		}
		if (this.CollidesWithPlant(0f, 0f) != null){
			if (this.CollidesWithPlant(0f, 0f) instanceof SpikerockEntity) {
				this.CollidesWithPlant(0f, 0f).damage(DamageSource.thrownProjectile(this, this), 90);
				this.kill();
			}
			else if (this.CollidesWithPlant(0f, 0f) instanceof SpikeweedEntity) {
				this.CollidesWithPlant(0f, 0f).kill();
				this.kill();
			}
			else if (this.CollidesWithPlant(0f, 0f) != null && !(this.CollidesWithPlant(0f, 0f) instanceof GravebusterEntity)) {
				this.CollidesWithPlant(0f, 0f).kill();
			}
		}
	}

	/** /~*~//~*GECKOLIB ANIMATION*~//~*~/ **/

	@Override
	public void registerControllers(AnimationData data) {
		AnimationController controller = new AnimationController(this, controllerName, 0, this::predicate);

		data.addAnimationController(controller);
	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}

	private <P extends IAnimatable> PlayState predicate(AnimationEvent<P> event) {
		if (beingEaten || this.hasVehicle()){
			event.getController().setAnimation(new AnimationBuilder().loop("hawker.eating"));
		}
		else {
			event.getController().setAnimation(new AnimationBuilder().loop("hawker.obstacle"));
		}
        return PlayState.CONTINUE;
    }


	/** /~*~//~*ATTRIBUTES*~//~*~/ **/

	public static DefaultAttributeContainer.Builder createHawkerCartAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 100.0D)
				.add(ReachEntityAttributes.ATTACK_RANGE, 1.5D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, PVZCONFIG.nestedZombieHealth.hawkerObstH());
    }

	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.ITEM_SHIELD_BREAK;
	}

	protected SoundEvent getAmbientSound() {
		if (this.getType().equals(PvZEntity.HAWKERCART)) {
			return SoundEvents.ENTITY_PIGLIN_AMBIENT;
		}
		else {
			return PvZSounds.SILENCEVENET;
		}
	}

	public EntityGroup getGroup() {
		return EntityGroup.UNDEAD;
	}

	protected SoundEvent getStepSound() {
		return PvZSounds.SILENCEVENET;
	}

	@Nullable
	@Override
	public ItemStack getPickBlockStack() {
		ItemStack itemStack;
		if (this.getType().equals(PvZEntity.HAWKERCART)){
			itemStack = ModItems.HAWKEREGG.getDefaultStack();
		}
		else{
			itemStack = ModItems.HAWKEREGG.getDefaultStack();
		}
		return itemStack;
	}


	@Override
	public void onDeath(DamageSource source) {
		EntityType<?> type = PvZEntity.PIGGY;
		if (this.getVehicle() instanceof GeneralPvZombieEntity generalPvZombieEntity && generalPvZombieEntity.getHypno()){
			type = PvZEntity.PIGGYHYPNO;
		}
		if (this.getWorld() instanceof ServerWorld serverWorld  && !(source.getSource() instanceof SuperChomperEntity) &&
				!(source.getSource() instanceof ChomperEntity) &&
				!(source.getSource() instanceof ChesterEntity) &&
				!(source.getSource() instanceof OlivePitEntity)) {
			for (int x = -1; x <= 1; x += 2) {
				Vec3d vec3d = new Vec3d((double) 0, 0, x).rotateY(-this.getHeadYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
				BlockPos blockPos = this.getBlockPos().add(vec3d.getX(), 0, vec3d.getZ());
				if (!world.getBlockState(blockPos).isOf(Blocks.AIR) && !world.getBlockState(blockPos).isOf(Blocks.CAVE_AIR)) {
					vec3d = new Vec3d((double) 0, 0, 0).rotateY(-this.getHeadYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
				}
				PiggyEntity piggy = (PiggyEntity) type.create(world);
				piggy.refreshPositionAndAngles(this.getX() + vec3d.x, this.getY(), this.getZ() + vec3d.z, 0, 0);
				piggy.initialize(serverWorld, world.getLocalDifficulty(blockPos), SpawnReason.SPAWN_EGG, (EntityData) null, (NbtCompound) null);
				piggy.setYaw(this.getYaw());
				piggy.setOwner(this);
				serverWorld.spawnEntityAndPassengers(piggy);
			}
		}
		super.onDeath(source);
	}
}
