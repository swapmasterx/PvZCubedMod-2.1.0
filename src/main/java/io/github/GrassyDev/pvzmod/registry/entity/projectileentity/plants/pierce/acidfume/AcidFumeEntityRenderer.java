package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.pierce.acidfume;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class AcidFumeEntityRenderer extends GeoEntityRenderer<AcidFumeEntity> {

	public AcidFumeEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new AcidFumeEntityModel());
		this.shadowRadius = 0.3F; //change 0.7 to the desired shadow size.
	}

}
