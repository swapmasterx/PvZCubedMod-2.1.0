package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzbfn.heroes.nightcap;


import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class NightcapEntityRenderer extends GeoEntityRenderer<NightcapEntity> {

    public NightcapEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new NightcapEntityModel());
        this.shadowRadius = 0.7F; //change 0.7 to the desired shadow size.
    }

	protected int getBlockLight(NightcapEntity plantEntity, BlockPos blockPos) {
		if (plantEntity.getShadowPowered()){
			return 15;
		}
		else {
			return Math.min(super.getBlockLight(plantEntity, blockPos) + 6, 15);
		}
	}
}
