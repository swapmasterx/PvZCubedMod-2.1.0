package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz1.imp.modernday;

import com.google.common.collect.Maps;

import com.mojang.blaze3d.vertex.VertexConsumer;
import io.github.GrassyDev.pvzmod.registry.entity.variants.zombies.ImpVariants;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.oc.bully.basic.BullyEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.util.GeckoLibUtil;
import io.github.GrassyDev.pvzmod.registry.entity.damage.PvZDamageTypes;

import software.bernie.geckolib.renderer.GeoEntityRenderer;

import java.util.Map;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class ImpEntityRenderer extends GeoEntityRenderer<ImpEntity> {

    public ImpEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new ImpEntityModel());
        this.shadowRadius = 0.4F; //change 0.7 to the desired shadow size.
    }

	public static final Map<ImpVariants, Identifier> LOCATION_MODEL_BY_VARIANT =
			Util.make(Maps.newEnumMap(ImpVariants.class), (map) -> {
				map.put(ImpVariants.DEFAULT,
						new Identifier("pvzmod", "geo/imp.geo.json"));
				map.put(ImpVariants.DEFAULTHYPNO,
						new Identifier("pvzmod", "geo/imp.geo.json"));
				map.put(ImpVariants.MUMMY,
						new Identifier("pvzmod", "geo/impmummy.geo.json"));
				map.put(ImpVariants.MUMMYHYPNO,
						new Identifier("pvzmod", "geo/impmummy.geo.json"));
				map.put(ImpVariants.IMPDRAGON,
						new Identifier("pvzmod", "geo/impdragon.geo.json"));
				map.put(ImpVariants.IMPDRAGONHYPNO,
						new Identifier("pvzmod", "geo/impdragon.geo.json"));
				map.put(ImpVariants.THROWER,
						new Identifier("pvzmod", "geo/imp.geo.json"));
				map.put(ImpVariants.THROWERHYPNO,
						new Identifier("pvzmod", "geo/imp.geo.json"));
				map.put(ImpVariants.SCRAP,
						new Identifier("pvzmod", "geo/scrapimp.geo.json"));
				map.put(ImpVariants.SCRAPHYPNO,
						new Identifier("pvzmod", "geo/scrapimp.geo.json"));
				map.put(ImpVariants.BASSIMP,
						new Identifier("pvzmod", "geo/rainbowbassimp.geo.json"));
				map.put(ImpVariants.BASSIMPHYPNO,
						new Identifier("pvzmod", "geo/rainbowbassimp.geo.json"));
				map.put(ImpVariants.CINDERELLA,
						new Identifier("pvzmod", "geo/cinderellaimp.geo.json"));
				map.put(ImpVariants.CINDERELLAHYPNO,
						new Identifier("pvzmod", "geo/cinderellaimp.geo.json"));
			});

	public Identifier getModelResource(ImpEntity object) {
		return LOCATION_MODEL_BY_VARIANT.get(object.getVariant());
	}

	@Override
	public void preRender(MatrixStack poseStack, ImpEntity animatable, BakedGeoModel model, VertexConsumerProvider bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue,
						  float alpha) {
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
			super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, 255, packedOverlay, s, t, u, alpha);
		}
		else
		if (animatable.getHypno()) {
			super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, 255, packedOverlay, 1, 255, 1, alpha);
		}
		else if (animatable.fireSplashTicks > 0){
			super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, 1, 255, 255, alpha);
		}
		else if(animatable.isIced || animatable.isFrozen){
			super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, 255, 75, 1, alpha);
		}
		else if (animatable.isPoisoned){
			super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, 100, 255, 1, alpha);
		}
		else {
			super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
		}
	}
}

