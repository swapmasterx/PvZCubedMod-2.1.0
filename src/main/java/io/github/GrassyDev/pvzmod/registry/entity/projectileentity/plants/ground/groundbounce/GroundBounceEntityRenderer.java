package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.ground.groundbounce;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class GroundBounceEntityRenderer extends GeoEntityRenderer {

	public GroundBounceEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new GroundBounceEntityModel());
		this.shadowRadius = 0F; //change 0.7 to the desired shadow size.
	}

}
