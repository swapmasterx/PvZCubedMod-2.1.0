package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1c.social.burstshroom;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class BurstshroomEntityRenderer extends GeoEntityRenderer<BurstshroomEntity> {

    public BurstshroomEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new BurstShroomEntityModel());
        this.shadowRadius = 0.6F; //change 0.7 to the desired shadow size.
    }

	protected int getBlockLight(BurstshroomEntity plantEntity, BlockPos blockPos) {
		return plantEntity.isWet() || plantEntity.getIsAltFire() || plantEntity.getIsAsleep()? super.getBlockLight(plantEntity, blockPos) + 6 : 15;
	}
}
