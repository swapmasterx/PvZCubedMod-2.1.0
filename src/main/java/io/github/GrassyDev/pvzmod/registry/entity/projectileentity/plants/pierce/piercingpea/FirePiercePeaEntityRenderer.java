package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.pierce.piercingpea;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class FirePiercePeaEntityRenderer extends GeoEntityRenderer<FirePiercePeaEntity> {

	public FirePiercePeaEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new FirePiercePeaEntityModel());
		this.shadowRadius = 0.2F; //change 0.7 to the desired shadow size.
	}

}
