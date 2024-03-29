package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.zombies.flamingbook;


import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

public class FlamingBookEntityRenderer extends GeoProjectilesRenderer {

	public FlamingBookEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new FlamingBookEntityModel());
		this.shadowRadius = 0F; //change 0.7 to the desired shadow size.
	}
	protected int getBlockLight(FlamingBookEntity plantEntity, BlockPos blockPos) {
		return 15;
	}
}
