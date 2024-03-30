package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.sky.missiletoeproj;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class MissileToeProjEntityRenderer extends GeoEntityRenderer {

	public MissileToeProjEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new MissileToeProjEntityModel());
		this.shadowRadius = 0.1F; //change 0.7 to the desired shadow size.
	}
	protected int getBlockLight(MissileToeProjEntity plantEntity, BlockPos blockPos) {
		return 15;
	}
}
