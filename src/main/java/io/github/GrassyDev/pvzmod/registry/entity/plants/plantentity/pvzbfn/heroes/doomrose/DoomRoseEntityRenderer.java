package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzbfn.heroes.doomrose;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class DoomRoseEntityRenderer extends GeoEntityRenderer<DoomRoseEntity> {

    public DoomRoseEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new DoomRoseEntityModel());
        this.shadowRadius = 0.8F; //change 0.7 to the desired shadow size.
    }

	protected int getBlockLight(DoomRoseEntity plantEntity, BlockPos blockPos) {
		if (plantEntity.isFiring && plantEntity.charge){
			return Math.min(super.getBlockLight(plantEntity, blockPos) + 9, 15);
		}
		else {
			return Math.min(super.getBlockLight(plantEntity, blockPos) + 3, 15);
		}
	}
}
