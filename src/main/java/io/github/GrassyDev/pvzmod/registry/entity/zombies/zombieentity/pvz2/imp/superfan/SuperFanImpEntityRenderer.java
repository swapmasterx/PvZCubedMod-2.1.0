package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2.imp.superfan;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.VertexConsumer;
import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.entity.variants.zombies.ImpVariants;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import java.util.Map;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class SuperFanImpEntityRenderer extends GeoEntityRenderer<SuperFanImpEntity> {

    public SuperFanImpEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new SuperFanImpEntityModel());
        this.shadowRadius = 0.4F; //change 0.7 to the desired shadow size.
    }

	public static final Map<ImpVariants, Identifier> LOCATION_BY_VARIANT =
			Util.make(Maps.newEnumMap(ImpVariants.class), (map) -> {
				map.put(ImpVariants.DEFAULT,
						new Identifier(PvZCubed.MOD_ID, "textures/entity/imp/superfanimp.png"));
				map.put(ImpVariants.NEWYEAR,
						new Identifier(PvZCubed.MOD_ID, "textures/entity/imp/newyearimp.png"));
			});

	public Identifier getTextureResource(SuperFanImpEntity object) {
		return LOCATION_BY_VARIANT.get(object.getVariant());
	}

	protected int getBlockLight(SuperFanImpEntity zombie, BlockPos blockPos) {
		return zombie.getFireStage()? 15 : super.getBlockLight(zombie, blockPos);
	}

	@Override
	public void render(GeoModel model, SuperFanImpEntity animatable, float partialTick, RenderLayer type, MatrixStack poseStack, @Nullable VertexConsumerProvider bufferSource, @Nullable VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		if (animatable.getRainbow()) {
			float s;
			float t;
			float u;
			int n = animatable.age / 25 + animatable.getId();
			int o = DyeColor.values().length;
			int p = n % o;
			int q = (n + 1) % o;
			float r = ((float) (animatable.age % 25) + alpha) / 25.0F;
			float[] fs = SheepEntity.getRgbColor(DyeColor.byId(p));
			float[] gs = SheepEntity.getRgbColor(DyeColor.byId(q));
			s = fs[0] * (1.0F - r) + gs[0] * r;
			t = fs[1] * (1.0F - r) + gs[1] * r;
			u = fs[2] * (1.0F - r) + gs[2] * r;
			super.render(model, animatable, partialTick, type, poseStack, bufferSource, buffer, 255, packedOverlay, s, t, u, alpha);
		}
		else
		if (animatable.getHypno()) {
			super.render(model, animatable, partialTick, type, poseStack, bufferSource, buffer, 255, packedOverlay, 1, 255, 1, alpha);
		}
		else if (animatable.fireSplashTicks > 0){
			super.render(model, animatable, partialTick, type, poseStack, bufferSource, buffer, packedLight, packedOverlay, 1, 255, 255, alpha);
		}
		else if(animatable.isIced || animatable.isFrozen){
			super.render(model, animatable, partialTick, type, poseStack, bufferSource, buffer, packedLight, packedOverlay, 255, 75, 1, alpha);
		}
		else if (animatable.isPoisoned){
			super.render(model, animatable, partialTick, type, poseStack, bufferSource, buffer, packedLight, packedOverlay, 100, 255, 1, alpha);
		}
		else {
			super.render(model, animatable, partialTick, type, poseStack, bufferSource, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		}
	}
}
