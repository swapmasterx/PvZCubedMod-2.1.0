package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.lobbed.peanut;


import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class PeaNutProjEntityRenderer extends GeoEntityRenderer {

	public PeaNutProjEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new PeaNutProjEntityModel());
		this.shadowRadius = 0.3F; //change 0.7 to the desired shadow size.
	}
}
