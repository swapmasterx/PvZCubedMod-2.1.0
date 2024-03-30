package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.pierce.piercingpea;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class PiercePeaEntityRenderer extends GeoEntityRenderer {

	public PiercePeaEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new PiercePeaEntityModel());
		this.shadowRadius = 0.2F; //change 0.7 to the desired shadow size.
	}

	protected int getBlockLight(FirePiercePeaEntity plantEntity, BlockPos blockPos) {
		return 15;
	}
}
