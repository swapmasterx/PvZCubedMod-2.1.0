package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.pierce.piercingpea;


import com.mojang.blaze3d.vertex.VertexConsumer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.oc.bully.basic.BullyEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.util.RenderUtils;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class PiercePeaEntityRenderer extends GeoEntityRenderer<PiercePeaEntity> {
	@Override
	public void preRender(MatrixStack poseStack, PiercePeaEntity animatable, BakedGeoModel model, VertexConsumerProvider bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue,
						  float alpha) {
		RenderUtils.faceRotation(poseStack, animatable, partialTick);

	}
	public PiercePeaEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new PiercePeaEntityModel());
		this.shadowRadius = 0.2F; //change 0.7 to the desired shadow size.
	}

	protected int getBlockLight(FirePiercePeaEntity plantEntity, BlockPos blockPos) {
		return 15;
	}
}
