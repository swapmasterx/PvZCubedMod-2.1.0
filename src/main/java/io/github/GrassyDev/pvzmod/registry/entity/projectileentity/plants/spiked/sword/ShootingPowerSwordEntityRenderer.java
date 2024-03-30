package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.spiked.sword;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class ShootingPowerSwordEntityRenderer extends GeoEntityRenderer {

	public ShootingPowerSwordEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new ShootingPowerSwordEntityModel());
		this.shadowRadius = 0.2F; //change 0.7 to the desired shadow size.
	}
	protected int getBlockLight(ShootingPowerSwordEntity plantEntity, BlockPos blockPos) {
		return 15;
	}
}
