package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.zombies.bone;


import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

public class BoneProjEntityRenderer extends GeoProjectilesRenderer {

	public BoneProjEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new BonProjEntityModel());
		this.shadowRadius = 0.4F; //change 0.7 to the desired shadow size.
	}
}
