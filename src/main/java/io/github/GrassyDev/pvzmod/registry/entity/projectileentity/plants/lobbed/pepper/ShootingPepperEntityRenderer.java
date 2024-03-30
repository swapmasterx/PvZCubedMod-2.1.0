package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.lobbed.pepper;


import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class ShootingPepperEntityRenderer extends GeoEntityRenderer {

	public ShootingPepperEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new ShootingPepperEntityModel());
		this.shadowRadius = 0.3F; //change 0.7 to the desired shadow size.
	}
	protected int getBlockLight(ShootingPepperEntity plantEntity, BlockPos blockPos) {
		return 15;
	}
}
