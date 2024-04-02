package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.spiked.icespike;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class ShootingPowerIceSpikeEntityRenderer extends GeoEntityRenderer<ShootingPowerIcespikeEntity> {

	public ShootingPowerIceSpikeEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new ShootingPowerIcespikeEntityModel());
		this.shadowRadius = 0.3F; //change 0.7 to the desired shadow size.
	}
	protected int getBlockLight(ShootingPowerIcespikeEntity plantEntity, BlockPos blockPos) {
		return 15;
	}
}
