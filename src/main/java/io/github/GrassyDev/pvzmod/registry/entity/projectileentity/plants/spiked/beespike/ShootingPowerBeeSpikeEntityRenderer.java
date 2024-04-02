package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.spiked.beespike;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class ShootingPowerBeeSpikeEntityRenderer extends GeoEntityRenderer <ShootingPowerBeeSpikeEntity>{

	public ShootingPowerBeeSpikeEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new ShootingPowerBeeSpikeEntityModel());
		this.shadowRadius = 0.2F; //change 0.7 to the desired shadow size.
	}
	protected int getBlockLight(ShootingPowerBeeSpikeEntity plantEntity, BlockPos blockPos) {
		return 15;
	}
}
