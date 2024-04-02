package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.ground.wallnut;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class WallnutBowlingEntityRenderer extends GeoEntityRenderer<WallnutBowlingEntity> {

	public WallnutBowlingEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new WallnutBowlingEntityModel());
		this.shadowRadius = 0F; //change 0.7 to the desired shadow size.
	}

}
