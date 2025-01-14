package io.github.GrassyDev.pvzmod.registry.entity.plants.miscentity.gardenchallenge;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class GardenChallengeEntityRenderer extends GeoEntityRenderer<GardenChallengeEntity> {

    public GardenChallengeEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new GardenChallengeEntityModel());
        this.shadowRadius = 0F; //change 0.7 to the desired shadow size.
    }

	protected int getBlockLight(GardenChallengeEntity tile, BlockPos blockPos) {
		return 15;
	}
}
