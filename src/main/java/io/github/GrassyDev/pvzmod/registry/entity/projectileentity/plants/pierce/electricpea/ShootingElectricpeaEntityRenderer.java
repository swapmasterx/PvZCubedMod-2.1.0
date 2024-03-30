package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.pierce.electricpea;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class ShootingElectricpeaEntityRenderer extends GeoEntityRenderer {

	public ShootingElectricpeaEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new ShootingElectricPeaEntityModel());
		this.shadowRadius = 0.1F; //change 0.7 to the desired shadow size.
	}
	protected int getBlockLight(ShootingElectricPeaEntity plantEntity, BlockPos blockPos) {
		return 15;
	}
}
