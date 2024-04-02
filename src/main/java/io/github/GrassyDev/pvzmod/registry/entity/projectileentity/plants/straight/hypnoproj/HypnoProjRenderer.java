package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.straight.hypnoproj;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class HypnoProjRenderer extends GeoEntityRenderer <HypnoProjEntity>{

	public HypnoProjRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new HypnoProjModel());
		this.shadowRadius = 0.1F; //change 0.7 to the desired shadow size.
	}
}
