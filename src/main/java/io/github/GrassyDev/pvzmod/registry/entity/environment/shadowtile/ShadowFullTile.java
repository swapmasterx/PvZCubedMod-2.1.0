package io.github.GrassyDev.pvzmod.registry.entity.environment.shadowtile;

import io.github.GrassyDev.pvzmod.registry.entity.environment.TileEntity;
import net.minecraft.entity.EntityType;
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



public class ShadowFullTile extends TileEntity {
	public ShadowFullTile(EntityType<? extends TileEntity> entityType, World world) {
		super(entityType, world);

		setNoGravity(true);
	}

	private AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);
	private String controllerName = "firetrailcontroller";


	/**
	 * /~*~//~*GECKOLIB ANIMATION*~//~*~/
	 **/

	@Override
	public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
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
		event.getController().setAnimation(RawAnimation.begin().thenLoop("tile.anim"));
		return PlayState.CONTINUE;
	}

	@Override
	public void tick() {
		super.tick();
	}
}
