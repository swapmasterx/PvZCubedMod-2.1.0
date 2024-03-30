package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.zombies.soundwave;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class SoundwaveRenderer extends GeoEntityRenderer {

	public SoundwaveRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new SoundwaveModel());
		this.shadowRadius = 0.1F; //change 0.7 to the desired shadow size.
	}
}
