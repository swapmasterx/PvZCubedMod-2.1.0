package io.github.GrassyDev.pvzmod.registry.entity.environment.springtile;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.entity.environment.TileEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.GeneralPvZombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombiePropEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.Monster;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
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



import java.util.Iterator;
import java.util.List;

public class SpringTile extends TileEntity {

	public SpringTile(EntityType<? extends SpringTile> entityType, World world) {
		super(entityType, world);

	}

	private AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);
	private String controllerName = "tile";

	private int springTicks = 15;
	private boolean tickDown = false;

	@Environment(EnvType.CLIENT)
	public void handleStatus(byte status) {
		if (status != 2 && status != 60){
			super.handleStatus(status);
		}
		if (status == 110){
			tickDown = true;
		}
	}


	/** /~*~//~*GECKOLIB ANIMATION*~//~*~/ **/

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.factory;
	}
	@Override
	public void registerControllers(AnimatableManager.ControllerRegistrar controllers){
		controllers.add(new AnimationController<>(this, controllerName, 0, this::predicate));
	}

	private <P extends GeoAnimatable> PlayState predicate(AnimationState<P> event) {
		if (tickDown){
			event.getController().setAnimation(RawAnimation.begin().thenLoop("spring.tile"));
		} else {
			event.getController().setAnimation(RawAnimation.begin().thenLoop("springprincess.idle"));
		}
		return PlayState.CONTINUE;
	}

	@Override
	protected void applyDamage(DamageSource source, float amount) {
		super.applyDamage(source, 555);
	}

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

			if (!tickDown && livingEntity instanceof Monster && !(livingEntity instanceof ZombiePropEntity) && !(livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && generalPvZombieEntity.getHypno())) {
				if (livingEntity.getY() < (this.getY() + 2) && livingEntity.getY() > (this.getY() - 2) &&
						!(livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && generalPvZombieEntity.isFlying()) && !(livingEntity instanceof GeneralPvZombieEntity zombie && zombie.isHovering())) {
					Vec3d vec3d = new Vec3d((double) -0.5, +0.25, 0).rotateY(-livingEntity.getHeadYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
					livingEntity.addStatusEffect((new StatusEffectInstance(PvZCubed.BOUNCED, 20, 1)));
					livingEntity.setVelocity(Vec3d.ZERO);
					livingEntity.addVelocity(vec3d.getX(), vec3d.getY(), vec3d.getZ());
					this.tickDown = true;
					this.getWorld().sendEntityStatus(this, (byte) 110);
					break;
				}
			}
		}
	}

	@Override
	public void tick() {
		List<LivingEntity> list = this.getWorld().getNonSpectatingEntities(LivingEntity.class, this.getBoundingBox().expand(1));
		for (LivingEntity livingEntity : list){
			if (livingEntity instanceof SpringTile && this.squaredDistanceTo(livingEntity) <= 0.5f && livingEntity != this){
				this.discard();
			}
		}
		super.tick();

		if (tickDown) {
			if (--springTicks <= 0) {
				this.discard();
			}
		}
		if (this.age >= 100){
			this.discard();
		}
		BlockPos blockPos = this.getBlockPos();
		this.damageEntity();
	}
<<<<<<< Updated upstream
=======

	@Override
	public double getTick(Object object) {
		return 0;
	}
>>>>>>> Stashed changes
}
