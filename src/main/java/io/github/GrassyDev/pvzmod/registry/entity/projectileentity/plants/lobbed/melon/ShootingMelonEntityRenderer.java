package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.lobbed.melon;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.util.RenderUtil;

public class ShootingMelonEntityRenderer extends GeoEntityRenderer<ShootingMelonEntity>{
	@Override
	public void preRender(MatrixStack poseStack, ShootingMelonEntity animatable, BakedGeoModel model, VertexConsumerProvider bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue,
						  float alpha) {
		RenderUtil.faceRotation(poseStack, animatable, partialTick);

	}
	public ShootingMelonEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new ShootingMelonEntityModel());
		this.shadowRadius = 0.3F; //change 0.7 to the desired shadow size.
	}
}

