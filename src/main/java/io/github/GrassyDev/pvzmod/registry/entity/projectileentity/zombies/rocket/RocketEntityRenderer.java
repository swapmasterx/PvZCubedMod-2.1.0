package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.zombies.rocket;

import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.entity.Entity;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

public class RocketEntityRenderer extends GeoProjectilesRenderer {

	public RocketEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new RocketEntityModel());
		this.shadowRadius = 0.3F; //change 0.7 to the desired shadow size.
	}

	@Override
	public boolean shouldRender(Entity entity, Frustum frustum, double x, double y, double z) {
		if (entity.age >= 7) {
			return super.shouldRender(entity, frustum, x, y, z);
		}
		else {
			return false;
		}
	}
}
