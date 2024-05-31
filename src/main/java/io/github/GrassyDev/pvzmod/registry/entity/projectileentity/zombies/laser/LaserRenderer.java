package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.zombies.laser;

import com.mojang.blaze3d.vertex.VertexConsumer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.zombies.flamingbook.FlamingBookEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.util.RenderUtils;

public class LaserRenderer extends GeoEntityRenderer<LaserEntity> {
	@Override
	public void preRender(MatrixStack poseStack, LaserEntity animatable, BakedGeoModel model, VertexConsumerProvider bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue,
						  float alpha) {
		RenderUtils.faceRotation(poseStack, animatable, partialTick);

	}
	public LaserRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new LaserModel());
		this.shadowRadius = 0F; //change 0.7 to the desired shadow size.
	}
	protected int getBlockLight(LaserEntity plantEntity, BlockPos blockPos) {
		return 15;
	}
}
