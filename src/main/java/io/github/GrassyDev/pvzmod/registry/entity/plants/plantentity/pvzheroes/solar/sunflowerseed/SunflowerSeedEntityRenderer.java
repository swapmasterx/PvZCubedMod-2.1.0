package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.solar.sunflowerseed;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class SunflowerSeedEntityRenderer extends GeoEntityRenderer<SunflowerSeedEntity> {

    public SunflowerSeedEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new SunflowerSeedEntityModel());
        this.shadowRadius = 0.3F; //change 0.7 to the desired shadow size.
    }

	protected int getBlockLight(SunflowerSeedEntity plantEntity, BlockPos blockPos) {
		return (plantEntity.isFiring) ? Math.min(super.getBlockLight(plantEntity, blockPos) + 12, 15) : Math.min(super.getBlockLight(plantEntity, blockPos) + 3, 15);
	}
}
