package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2.browncoat.mummy;


import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.items.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.sound.PvZSounds;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.zombies.bone.BoneProjEntity;
import io.github.GrassyDev.pvzmod.registry.entity.variants.zombies.BrowncoatVariants;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz1.browncoat.modernday.BrowncoatEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.animation.AnimationState;

import java.util.ArrayList;
import java.util.List;

import static io.github.GrassyDev.pvzmod.PvZCubed.PVZCONFIG;

public class MummyEntity extends BrowncoatEntity {
	public MummyEntity(EntityType<? extends BrowncoatEntity> entityType, World world) {
		super(entityType, world);
	}

	@Override
	public void tick() {
		super.tick();
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

	protected  <P extends GeoAnimatable> PlayState predicate(AnimationState<P> event) {
		if (this.isInsideWaterOrBubbleColumn()) {
			event.getController().setAnimation(RawAnimation.begin().thenLoop("headwear.ducky"));
			if (this.getVariant().equals(BrowncoatVariants.TOMB) || this.getVariant().equals(BrowncoatVariants.TOMBHYPNO)){
				if (this.isIced) {
					event.getController().setAnimationSpeed(0.375);
				} else {
					event.getController().setAnimationSpeed(0.75);
				}
			}
			else {
				if (this.isIced) {
					event.getController().setAnimationSpeed(0.5);
				} else {
					event.getController().setAnimationSpeed(1);
				}
			}
		} else {
			if (inLaunchAnimation) {
				event.getController().setAnimation(RawAnimation.begin().thenLoop("tombraiser.lick"));
				if (this.isIced) {
					event.getController().setAnimationSpeed(0.5);
				} else {
					event.getController().setAnimationSpeed(1);
				}
			}
			else if (!(event.getLimbSwingAmount() > -0.01F && event.getLimbSwingAmount() < 0.01F)) {
				event.getController().setAnimation(RawAnimation.begin().thenLoop("headwear.walking"));
				if (this.getVariant().equals(BrowncoatVariants.TOMB) || this.getVariant().equals(BrowncoatVariants.TOMBHYPNO)){
					if (this.isIced) {
						event.getController().setAnimationSpeed(0.375);
					} else {
						event.getController().setAnimationSpeed(0.75);
					}
				}
				else {
					if (this.isIced) {
						event.getController().setAnimationSpeed(0.5);
					} else {
						event.getController().setAnimationSpeed(1);
					}
				}
			} else {
				event.getController().setAnimation(RawAnimation.begin().thenLoop("headwear.idle"));
				if (this.isIced) {
					event.getController().setAnimationSpeed(0.5);
				} else {
					event.getController().setAnimationSpeed(1);
				}
			}
		}
		return PlayState.CONTINUE;
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
		else if (this.getVariant().equals(BrowncoatVariants.TOMB) || this.getVariant().equals(BrowncoatVariants.TOMBHYPNO)){
			itemStack = ModItems.PYRAMIDHEADEGG.getDefaultStack();
		}
		else{
			itemStack = ModItems.MUMMYEGG.getDefaultStack();
		}
		return itemStack;
	}

	/** /~*~//~*ATTRIBUTES*~//~*~/ **/

	public static DefaultAttributeContainer.Builder createMummyAttributes() {
		return HostileEntity.createAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 100.0D)

				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.12D)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.0D)
				.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
				.add(EntityAttributes.GENERIC_MAX_HEALTH, PVZCONFIG.nestedZombieHealth.mummyH());
	}

	public static DefaultAttributeContainer.Builder createTombRaiserAttributes() {
		return HostileEntity.createAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 100.0D)

				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.10D)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.0D)
				.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
				.add(EntityAttributes.GENERIC_MAX_HEALTH, PVZCONFIG.nestedZombieHealth.tombraiserH());
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
	public void tryLaunch(Vec3d location) {
		BoneProjEntity boneProj = new BoneProjEntity(PvZEntity.BONEPROJ, this.getWorld());
		List<LivingEntity> list = getWorld().getNonSpectatingEntities(LivingEntity.class, PvZEntity.PEASHOOTER.getDimensions().getBoxAt(this.getPos()).expand(this.getAttributeValue(EntityAttributes.GENERIC_FOLLOW_RANGE) + 1));
		if (launchAnimation == 10 * animationMultiplier && !this.hasStatusEffect(PvZCubed.FROZEN) && !this.hasStatusEffect(PvZCubed.STUN) && !this.hasStatusEffect(PvZCubed.DISABLE)) {
			double d = this.squaredDistanceTo(location);
			float df = (float) d;
			float h = MathHelper.sqrt(MathHelper.sqrt(df)) * 0.5F;
			Vec3d projPos = new Vec3d(this.getX(), this.getY() + 2.3D, this.getZ());
			Vec3d vel = this.solve_ballistic_arc_lateral(projPos, 1F, location, 5);
			boneProj.setVelocity(vel.getX(), -3.9200000762939453 + 28 / (h * 2.2), vel.getZ(), 1F, 0F);
			boneProj.updatePosition(projPos.getX(), projPos.getY(), projPos.getZ());
			boneProj.setOwner(this);
			boneProj.setOwner(this);
			this.playSound(PvZSounds.PEASHOOTEVENT, 1F, 1);
			this.getWorld().spawnEntity(boneProj);
			boneProj.vec3dTarget  = location;
		}
	}

	public List<Vec3d> tombLocation = new ArrayList<>();

	protected void mobTick() {
		super.mobTick();
		if (this.getVariant().equals(BrowncoatVariants.TOMB) && !this.getHypno() && !this.isInsideWaterOrBubbleColumn() && !this.hasStatusEffect(PvZCubed.FROZEN) && !this.hasStatusEffect(PvZCubed.STUN) && !this.hasStatusEffect(PvZCubed.DISABLE)) {
			double random = Math.random();
			for (int x = 0; x <= 15; ++x){
				if ((this.CollidesWithPlant((float)x, 0f) != null)
						&& !this.hasStatusEffect(PvZCubed.BOUNCED)) {
					if (random <= 0.0075 && getTarget() != null && !this.inLaunchAnimation) {
						this.playSound(PvZSounds.TOMBRAISERLICKEVENT);
						this.launchAnimation = 40 * animationMultiplier;
						this.inLaunchAnimation = true;
						this.getWorld().sendEntityStatus(this, (byte) 104);
					}
				}
			}
			if (this.launchAnimation > 0) {
				this.getNavigation().stop();
				--launchAnimation;
				for (int x = -6; x < 6; ++x) {
					int l = MathHelper.floor(this.getPos().x + x);
					int m = MathHelper.floor(this.getPos().y);
					int n = MathHelper.floor(this.getPos().z);
					BlockPos blockPos = new BlockPos(l, m, n);
					for (int z = -6; z < 6; ++z) {
						int l2 = MathHelper.floor(blockPos.getX());
						int m2 = MathHelper.floor(blockPos.getY());
						int n2 = MathHelper.floor(blockPos.getZ() + z);
						BlockPos blockPos2 = new BlockPos(l2, m2, n2);
						Vec3d blockLocation = Vec3d.ofCenter(blockPos2);
						if (!tombLocation.contains(blockLocation)){
							tombLocation.add(blockLocation);
						}
					}
				}
				RandomGenerator randomGenerator = this.getRandom();
				tryLaunch(tombLocation.get(randomGenerator.range(0, tombLocation.size() - 1)));
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
