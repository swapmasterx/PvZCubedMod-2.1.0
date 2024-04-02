package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.spiked.icespike;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class ShootingIceSpikeEntityRenderer extends GeoEntityRenderer<ShootingIcespikeEntity>{

	public ShootingIceSpikeEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new ShootingIcespikeEntityModel());
		this.shadowRadius = 0.3F; //change 0.7 to the desired shadow size.
	}
	protected int getBlockLight(ShootingIcespikeEntity plantEntity, BlockPos blockPos) {
		return Math.min(super.getBlockLight(plantEntity, blockPos) + 3, 15);
	}
}
