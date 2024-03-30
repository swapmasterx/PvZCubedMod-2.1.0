package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.lobbed.smooshproj;


import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class SmooshProjEntityRenderer extends GeoEntityRenderer {

	public SmooshProjEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new SmooshProjEntityModel());
		this.shadowRadius = 0.3F; //change 0.7 to the desired shadow size.
	}
}
