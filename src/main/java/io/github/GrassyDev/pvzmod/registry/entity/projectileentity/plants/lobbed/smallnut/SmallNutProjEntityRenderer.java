package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.lobbed.smallnut;


import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class SmallNutProjEntityRenderer extends GeoEntityRenderer<SmallNutProjEntity> {

	public SmallNutProjEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new SmallNutProjEntityModel());
		this.shadowRadius = 0.3F; //change 0.7 to the desired shadow size.
	}
}
