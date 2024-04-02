package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.ground.peanut;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class PeanutBowlingEntityRenderer extends GeoEntityRenderer<PeanutBowlingEntity> {

	public PeanutBowlingEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new PeanutBowlingEntityModel());
		this.shadowRadius = 0F; //change 0.7 to the desired shadow size.
	}

}
