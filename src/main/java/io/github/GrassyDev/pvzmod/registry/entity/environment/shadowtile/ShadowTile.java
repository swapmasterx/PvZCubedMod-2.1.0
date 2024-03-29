package io.github.GrassyDev.pvzmod.registry.entity.environment.shadowtile;

import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.entity.environment.TileEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.gemium.gloomvine.GloomVineEntity;
import net.minecraft.entity.EntityType;
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



import java.util.List;

public class ShadowTile extends TileEntity {
	public ShadowTile(EntityType<? extends TileEntity> entityType, World world) {
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
		if (this.age > 1 && !this.getWorld().isClient()){
			Vec3d vec3d = Vec3d.ofCenter(this.getBlockPos()).add(0, -0.5, 0);
			List<GloomVineEntity> gloom = world.getNonSpectatingEntities(GloomVineEntity.class, PvZEntity.PEASHOOTER.getDimensions().getBoxAt(vec3d.getX(), vec3d.getY(), vec3d.getZ()));
			if (gloom.isEmpty()){
				this.discard();
			}
		}
	}
}
