package io.github.GrassyDev.pvzmod.registry.entity.environment.shadowtile;

import io.github.GrassyDev.pvzmod.registry.entity.environment.TileEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;



public class ShadowFullTile extends TileEntity {
	public ShadowFullTile(EntityType<? extends TileEntity> entityType, World world) {
		super(entityType, world);

		setNoGravity(true);
	}

	private AnimationFactory factory = GeckoLibUtil.createFactory(this);
	private String controllerName = "firetrailcontroller";


	/** /~*~//~*GECKOLIB ANIMATION*~//~*~/ **/

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}

	private <P extends IAnimatable> PlayState predicate(AnimationEvent<P> event) {
		event.getController().setAnimation(new AnimationBuilder().loop("tile.anim"));
		return PlayState.CONTINUE;
	}

	@Override
	public void registerControllers(AnimationData data) {
		AnimationController controller = new AnimationController(this, controllerName, 0, this::predicate);
		data.addAnimationController(controller);
	}

	@Override
	public void tick() {
		super.tick();
	}
}
