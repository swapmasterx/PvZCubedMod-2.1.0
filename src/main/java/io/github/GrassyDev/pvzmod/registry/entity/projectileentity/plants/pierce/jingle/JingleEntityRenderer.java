package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.pierce.jingle;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class JingleEntityRenderer extends GeoEntityRenderer <JingleEntity>{

	public JingleEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new JingleEntityModel());
		this.shadowRadius = 0.1F; //change 0.7 to the desired shadow size.
	}
}
