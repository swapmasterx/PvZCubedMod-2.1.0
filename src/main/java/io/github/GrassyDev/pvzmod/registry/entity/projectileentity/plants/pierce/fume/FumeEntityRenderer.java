package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.pierce.fume;

import com.mojang.blaze3d.vertex.VertexConsumer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.pierce.piercingpea.PiercePeaEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.util.RenderUtils;

public class FumeEntityRenderer extends GeoEntityRenderer<FumeEntity> {
	@Override
	public void preRender(MatrixStack poseStack, FumeEntity animatable, BakedGeoModel model, VertexConsumerProvider bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue,
						  float alpha) {
		RenderUtils.faceRotation(poseStack, animatable, partialTick);

	}
	public FumeEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new FumeEntityModel());
		this.shadowRadius = 0.1F; //change 0.7 to the desired shadow size.
	}

}
