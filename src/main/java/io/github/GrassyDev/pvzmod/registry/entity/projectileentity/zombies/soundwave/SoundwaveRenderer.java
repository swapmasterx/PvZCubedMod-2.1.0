package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.zombies.soundwave;

import com.mojang.blaze3d.vertex.VertexConsumer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.zombies.flamingbook.FlamingBookEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.util.RenderUtils;

public class SoundwaveRenderer extends GeoEntityRenderer<SoundwaveEntity> {
	@Override
	public void preRender(MatrixStack poseStack, SoundwaveEntity animatable, BakedGeoModel model, VertexConsumerProvider bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue,
						  float alpha) {
		RenderUtils.faceRotation(poseStack, animatable, partialTick);

	}
	public SoundwaveRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new SoundwaveModel());
		this.shadowRadius = 0.1F; //change 0.7 to the desired shadow size.
	}
}
