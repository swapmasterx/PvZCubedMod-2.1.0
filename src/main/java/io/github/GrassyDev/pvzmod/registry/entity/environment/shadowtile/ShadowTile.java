package io.github.GrassyDev.pvzmod.registry.entity.environment.shadowtile;

import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.entity.environment.TileEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.gemium.gloomvine.GloomVineEntity;
import net.minecraft.entity.EntityType;
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



import java.util.List;

public class ShadowTile extends TileEntity {
	public ShadowTile(EntityType<? extends TileEntity> entityType, World world) {
		super(entityType, world);

		setNoGravity(true);
	}

	private AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);
	private String controllerName = "firetrailcontroller";


	/** /~*~//~*GECKOLIB ANIMATION*~//~*~/ **/

	@Override
	public AnimatableInstanceCache getFactory() {
		return this.factory;
	}

	private <P extends GeoAnimatable> PlayState predicate(AnimationState<P> event) {
		event.getController().setAnimation(new RawAnimation().loop("tile.anim"));
		return PlayState.CONTINUE;
	}

	@Override
	public void registerControllers(AnimatableManager data) {
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
