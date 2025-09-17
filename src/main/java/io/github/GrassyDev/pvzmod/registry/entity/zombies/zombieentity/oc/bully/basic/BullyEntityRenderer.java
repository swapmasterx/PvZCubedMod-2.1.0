package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.oc.bully.basic;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.VertexConsumer;
import io.github.GrassyDev.pvzmod.registry.entity.variants.zombies.BullyVariants;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2.browncoat.future.FutureZombieEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.network.packet.s2c.play.data.LightData;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.animatable.GeoAnimatable;

import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.util.ClientUtil;
import software.bernie.geckolib.util.Color;
import software.bernie.geckolib.util.GeckoLibUtil;
import io.github.GrassyDev.pvzmod.registry.entity.damage.PvZDamageTypes;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import java.util.Map;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class BullyEntityRenderer extends GeoEntityRenderer<BullyEntity> {

	@Override
	public Color getRenderColor(BullyEntity animatable, float partialTick, int packedLight) {
		Color color = BullyEntityRenderer.super.getRenderColor(animatable, partialTick, packedLight);

		if (animatable.isInvisible() && !animatable.isInvisibleTo(ClientUtil.getClientPlayer()))
			color = Color.ofARGB(MathHelper.ceil(color.getAlpha() * 38 / 255f), color.getRed(), color.getGreen(), color.getBlue());

		else if (animatable.getHypno())
			color = Color.ofRGB(1, 255, 1);
		else if (animatable.fireSplashTicks > 0)
			color = Color.ofRGB(1, 255, 225);
		else if (animatable.isIced || animatable.isFrozen)
			color = Color.ofRGB(225, 75, 1);
		else if (animatable.isPoisoned)
			color = Color.ofRGB(100, 255, 1);
		else
			color = Color.ofRGB(color.getRed(), color.getGreen(), color.getBlue());

		return color;

	}
//	@Override
//	public void preRender(MatrixStack poseStack, BullyEntity animatable, BakedGeoModel model, VertexConsumerProvider bufferSource, VertexConsumer buffer,
//						  boolean isReRender, float partialTick, int packedLight, int packedOverlay, int color) {
//		if (animatable.getRainbow()) {
//			float s;
//			float t;
//			float u;
//			int n = animatable.age / 25 + animatable.getId();
//			int o = DyeColor.values().length;
//			int p = n % o;
//			int q = (n + 1) % o;
//			float r = ((float) (animatable.age % 25) + color) / 25.0F;
//			float[] fs = SheepEntity.getColor(DyeColor.byId(p));
//			float[] gs = SheepEntity.getColor(DyeColor.byId(q));
//			s = fs[0] * (1.0F - r) + gs[0] * r;
//			t = fs[1] * (1.0F - r) + gs[1] * r;
//			u = fs[2] * (1.0F - r) + gs[2] * r;
//			super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, 255, packedOverlay, s, t, u, alpha);
//		} else if (animatable.getHypno()) {
//			super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, 255, packedOverlay, 1, 255, 1, alpha);
//		} else if (animatable.fireSplashTicks > 0) {
//			super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, 1, 255, 255, alpha);
//		} else if (animatable.isIced || animatable.isFrozen) {
//			super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, 255, 75, 1, alpha);
//		} else if (animatable.isPoisoned) {
//			super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, 100, 255, 1, alpha);
//		} else {
//			super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
//		}
//	}
    public BullyEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new BullyEntityModel());
        this.shadowRadius = 0.7F; //change 0.7 to the desired shadow size.
    }

	public static final Map<BullyVariants, Identifier> LOCATION_MODEL_BY_VARIANT =
			Util.make(Maps.newEnumMap(BullyVariants.class), (map) -> {
				map.put(BullyVariants.BULLY,
				        Identifier.of("pvzmod", "geo/bully.geo.json"));
				map.put(BullyVariants.BULLYHYPNO,
						Identifier.of("pvzmod", "geo/bully.geo.json"));
			});

	public Identifier getModelResource(BullyEntity object) {
		return LOCATION_MODEL_BY_VARIANT.get(object.getVariant());
	}
}

