package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2o.browncoat.sargeant;


import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.config.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.sound.PvZSounds;
import io.github.GrassyDev.pvzmod.registry.entity.plants.miscentity.garden.GardenEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.miscentity.gardenchallenge.GardenChallengeEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.PlantEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.zombies.flamingbook.FlamingBookEntity;
import io.github.GrassyDev.pvzmod.registry.entity.variants.zombies.BrowncoatVariants;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz1.browncoat.modernday.BrowncoatEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombiePropEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static io.github.GrassyDev.pvzmod.PvZCubed.PLANT_LOCATION;
import static io.github.GrassyDev.pvzmod.PvZCubed.PVZCONFIG;

public class SargeantEntity extends BrowncoatEntity {
	public SargeantEntity(EntityType<? extends BrowncoatEntity> entityType, World world) {
		super(entityType, world);
		doesntBite = true;
	}

	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(DATA_ID_TYPE_COUNT, true);
	}

	public void writeCustomDataToNbt(NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);
		nbt.putBoolean("Fire", this.getFireStage());
	}

	public void readCustomDataFromNbt(NbtCompound nbt) {
		super.readCustomDataFromNbt(nbt);
		this.dataTracker.set(DATA_ID_TYPE_COUNT, nbt.getBoolean("Fire"));
	}

	@Environment(EnvType.CLIENT)
	public void handleStatus(byte status) {
		if (status != 2 && status != 60){
			super.handleStatus(status);
		}
		if (status == 104) {
			this.inLaunchAnimation = true;
		}
		else if (status == 103) {
			this.inLaunchAnimation = false;
		}
	}

	/** /~*~//~*VARIANTS*~//~*~/ **/

	private static final TrackedData<Boolean> DATA_ID_TYPE_COUNT =
			DataTracker.registerData(SargeantEntity.class, TrackedDataHandlerRegistry.BOOLEAN);


	public enum FireStage {
		FIRE(true),
		EXTINGUISHED(false);

		FireStage(boolean id) {
			this.id = id;
		}

		private final boolean id;

		public boolean getId() {
			return this.id;
		}
	}

	public Boolean getFireStage() {
		return this.dataTracker.get(DATA_ID_TYPE_COUNT);
	}

	public void setFireStage(SargeantEntity.FireStage fireStage) {
		this.dataTracker.set(DATA_ID_TYPE_COUNT, fireStage.getId());
	}


	/** /~*~//~*INTERACTION*~//~*~/ **/

	@Nullable
	@Override
	public ItemStack getPickBlockStack() {
		ItemStack itemStack;
		if (this.getVariant().equals(BrowncoatVariants.CONEHEAD) || this.getVariant().equals(BrowncoatVariants.CONEHEADHYPNO)){
			itemStack = ModItems.SARGEANTCONEEGG.getDefaultStack();
		}
		else if (this.getVariant().equals(BrowncoatVariants.BUCKETHEAD) || this.getVariant().equals(BrowncoatVariants.BUCKETHEADHYPNO)){
			itemStack = ModItems.SARGEANTBUCKETEGG.getDefaultStack();
		}
		else if (this.getVariant().equals(BrowncoatVariants.SCREENDOOR) || this.getVariant().equals(BrowncoatVariants.SCREENDOORHYPNO)){
			itemStack = ModItems.SARGEANTSHIELDEGG.getDefaultStack();
		}
		else if (this.getVariant().equals(BrowncoatVariants.BOOKBURN) || this.getVariant().equals(BrowncoatVariants.BOOKBURNHYPNO)){
			itemStack = ModItems.BOOKBURNEREGG.getDefaultStack();
		}
		else{
			itemStack = ModItems.SARGEANTEGG.getDefaultStack();
		}
		return itemStack;
	}

	/** /~*~//~*ATTRIBUTES*~//~*~/ **/

	public static DefaultAttributeContainer.Builder createSergeantAttributes() {
		return HostileEntity.createAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 75.0D)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.12D)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 8.0D)
				.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
				.add(EntityAttributes.GENERIC_MAX_HEALTH, PVZCONFIG.nestedZombieHealth.sargeantH());
	}

	public static DefaultAttributeContainer.Builder createBookBurnerAttributes() {
		return HostileEntity.createAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 75.0D)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.12D)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.0D)
				.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
				.add(EntityAttributes.GENERIC_MAX_HEALTH, PVZCONFIG.nestedZombieHealth.sargeantH());
	}



	public boolean tryAttack(Entity target) {
		if (!this.hasStatusEffect(PvZCubed.FROZEN) && !this.hasStatusEffect(PvZCubed.STUN) && !this.hasStatusEffect(PvZCubed.DISABLE) && !this.inLaunchAnimation && this.getTarget() != null) {
			return super.tryAttack(this.getTarget());
		}
		else {
			return false;
		}
	}

	//Launch Basket
	public void tryLaunch(Entity target) {
		FlamingBookEntity flamingBookEntity = new FlamingBookEntity(PvZEntity.FLAMINGBOOK, this.getWorld());
		List<LivingEntity> list = getWorld().getNonSpectatingEntities(LivingEntity.class, PvZEntity.PEASHOOTER.getDimensions().getBoxAt(this.getPos()).expand(this.getAttributeValue(EntityAttributes.GENERIC_FOLLOW_RANGE) + 1));
		double targetDist = 0;
		flamingBookEntity.damageMultiplier = this.damageMultiplier;
		for (LivingEntity livingEntity : list){
			if (livingEntity instanceof PlantEntity plantEntity && !(plantEntity instanceof GardenChallengeEntity) && !(plantEntity instanceof GardenEntity) && !plantEntity.getImmune() && !(PLANT_LOCATION.get(plantEntity.getType()).orElse("normal").equals("flying"))){
				if (targetDist == 0){
					targetDist = this.squaredDistanceTo(plantEntity);
					target = plantEntity;
				}
				else if (this.squaredDistanceTo(plantEntity) <= targetDist){
					targetDist = this.squaredDistanceTo(plantEntity);
					target = plantEntity;
				}
			}
		}
		if (launchAnimation == 29 * animationMultiplier && !this.hasStatusEffect(PvZCubed.FROZEN) && !this.hasStatusEffect(PvZCubed.STUN) && !this.hasStatusEffect(PvZCubed.DISABLE)) {
			if (this.getTarget() instanceof ZombiePropEntity zombiePropEntity && zombiePropEntity.hasVehicle()){
				target = zombiePropEntity.getVehicle();
			}
			if (this.getTarget() != null) {
				assert target != null;
				Vec3d targetPos = target.getPos();
				double d = this.squaredDistanceTo(targetPos);
				float df = (float) d;
				float h = MathHelper.sqrt(MathHelper.sqrt(df)) * 0.5F;
				Vec3d projPos = new Vec3d(this.getX(), this.getY() + 2.3D, this.getZ());
				Vec3d vel = this.solve_ballistic_arc_lateral(projPos, 1F, targetPos, 5);
				flamingBookEntity.setVelocity(vel.getX(), -3.9200000762939453 + 28 / (h * 2.2), vel.getZ(), 1F, 0F);
				flamingBookEntity.updatePosition(projPos.getX(), projPos.getY(), projPos.getZ());
				flamingBookEntity.setOwner(this);
				flamingBookEntity.getTarget((LivingEntity) target);
			}
			else {
				flamingBookEntity.setVelocity(random.range(-1, 1), 0, random.range(-1, 1), 1F, 0F);
			}
			if (this.getHypno()) {
				flamingBookEntity.isHypno = true;
			}

			flamingBookEntity.setOwner(this);
			if (!this.getFireStage()) {
				flamingBookEntity.doExtinguish = true;
			}
			this.playSound(PvZSounds.PEASHOOTEVENT, 1F, 1);
			this.getWorld().spawnEntity(flamingBookEntity);
		}
	}

	protected void mobTick() {
		super.mobTick();
		if (!this.hasPassengers()){
			this.setFireStage(FireStage.EXTINGUISHED);
		}
		if (this.getVariant().equals(BrowncoatVariants.BOOKBURN) || this.getVariant().equals(BrowncoatVariants.BOOKBURNHYPNO)) {
			double random = Math.random();
			for (int x = 0; x <= 10; ++x){
				if ((this.CollidesWithPlant((float)x, 0f) != null)
						&& !this.hasStatusEffect(PvZCubed.BOUNCED)) {
					if (random <= 0.0075 && this.hasPassengers() && getTarget() != null && !this.inLaunchAnimation) {
						this.launchAnimation = 80 * animationMultiplier;
						this.inLaunchAnimation = true;
						this.getWorld().sendEntityStatus(this, (byte) 104);
					}
				}
			}
			if (this.launchAnimation > 0) {
				this.getNavigation().stop();
				--launchAnimation;
				tryLaunch(getTarget());
				this.inLaunchAnimation = true;
				this.getWorld().sendEntityStatus(this, (byte) 104);
			} else {
				this.inLaunchAnimation = false;
				this.getWorld().sendEntityStatus(this, (byte) 103);
			}
		}
	}

	public Vec3d fire_velocity;

	public Vec3d solve_ballistic_arc_lateral(Vec3d proj_pos, float lateral_speed, Vec3d target_pos, float max_height) {

		Vec3d diff = target_pos.subtract(proj_pos);
		Vec3d diffXZ = new Vec3d(diff.x, 0f, diff.z);
		float lateralDist = (float) diffXZ.length();

		if (lateralDist == 0)
			return fire_velocity = Vec3d.ZERO;

		float time = lateralDist / lateral_speed;

		fire_velocity = diffXZ.normalize().multiply(lateral_speed);

		// System of equations. Hit max_height at t=.5*time. Hit target at t=time.
		//
		// peak = y0 + vertical_speed*halfTime + .5*gravity*halfTime^2
		// end = y0 + vertical_speed*time + .5*gravity*time^s
		// Wolfram Alpha: solve b = a + .5*v*t + .5*g*(.5*t)^2, c = a + vt + .5*g*t^2 for g, v
		float a = (float) proj_pos.y;       // initial
		float b = max_height;       // peak
		float c = (float) target_pos.y;     // final

		return fire_velocity.subtract(0, -(3*a - 4*b + c) / time, 0);
	}
}
