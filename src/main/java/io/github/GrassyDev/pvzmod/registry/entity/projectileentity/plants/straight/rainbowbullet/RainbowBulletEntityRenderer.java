package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.straight.rainbowbullet;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class RainbowBulletEntityRenderer extends GeoEntityRenderer<RainbowBulletEntity> {

	public RainbowBulletEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new RainbowBulletEntityModel());
		this.shadowRadius = 0.3F; //change 0.7 to the desired shadow size.
	}
	protected int getBlockLight(RainbowBulletEntity plantEntity, BlockPos blockPos) {
		return 15;
	}
}
