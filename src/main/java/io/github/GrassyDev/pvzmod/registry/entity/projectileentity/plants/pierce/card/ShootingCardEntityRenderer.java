package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.pierce.card;

import com.mojang.blaze3d.vertex.VertexConsumer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.pierce.piercingpea.PiercePeaEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.util.RenderUtils;

public class ShootingCardEntityRenderer extends GeoEntityRenderer <ShootingCardEntity>{
	@Override
	public void preRender(MatrixStack poseStack, ShootingCardEntity animatable, BakedGeoModel model, VertexConsumerProvider bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue,
						  float alpha) {
		RenderUtils.faceRotation(poseStack, animatable, partialTick);

	}
	public ShootingCardEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new ShootingCardEntityModel());
		this.shadowRadius = 0.4F; //change 0.7 to the desired shadow size.
	}

}
