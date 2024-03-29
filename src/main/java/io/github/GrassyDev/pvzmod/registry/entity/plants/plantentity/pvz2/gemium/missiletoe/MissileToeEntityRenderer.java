package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.gemium.missiletoe;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class MissileToeEntityRenderer extends GeoEntityRenderer<MissileToeEntity> {

    public MissileToeEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new MissileToeEntityModel());
        this.shadowRadius = 0.5F; //change 0.7 to the desired shadow size.
    }

	protected int getBlockLight(MissileToeEntity plantEntity, BlockPos blockPos) {
		return (plantEntity.isFiring) ? Math.min(super.getBlockLight(plantEntity, blockPos) + 7, 15) : 0;
	}

}
