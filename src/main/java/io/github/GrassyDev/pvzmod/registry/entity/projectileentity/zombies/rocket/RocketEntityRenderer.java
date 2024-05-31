package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.zombies.rocket;

import com.mojang.blaze3d.vertex.VertexConsumer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.zombies.flamingbook.FlamingBookEntity;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.util.RenderUtils;

public class RocketEntityRenderer extends GeoEntityRenderer<RocketEntity> {
	@Override
	public void preRender(MatrixStack poseStack, RocketEntity animatable, BakedGeoModel model, VertexConsumerProvider bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue,
						  float alpha) {
		RenderUtils.faceRotation(poseStack, animatable, partialTick);

	}
	public RocketEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new RocketEntityModel());
		this.shadowRadius = 0.3F; //change 0.7 to the desired shadow size.
	}

	@Override
	public boolean shouldRender(RocketEntity entity, Frustum frustum, double x, double y, double z) {
		if (entity.age >= 7) {
			return super.shouldRender(entity, frustum, x, y, z);
		}
		else {
			return false;
		}
	}
}
