package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2.browncoat.future;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.VertexConsumer;
import io.github.GrassyDev.pvzmod.registry.entity.variants.zombies.BrowncoatVariants;
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
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import java.util.Map;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class FutureZombieEntityRenderer extends GeoEntityRenderer<FutureZombieEntity> {

    public FutureZombieEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new FutureZombieEntityModel());
        this.shadowRadius = 0.7F; //change 0.7 to the desired shadow size.
    }

	public static final Map<BrowncoatVariants, Identifier> LOCATION_MODEL_BY_VARIANT =
			Util.make(Maps.newEnumMap(BrowncoatVariants.class), (map) -> {
				map.put(BrowncoatVariants.BROWNCOAT,
				        new Identifier("pvzmod", "geo/futurezombie.geo.json"));
				map.put(BrowncoatVariants.BROWNCOATHYPNO,
						new Identifier("pvzmod", "geo/futurezombie.geo.json"));
				map.put(BrowncoatVariants.CONEHEAD,
						new Identifier("pvzmod", "geo/futurecone.geo.json"));
				map.put(BrowncoatVariants.CONEHEADHYPNO,
						new Identifier("pvzmod", "geo/futurecone.geo.json"));
				map.put(BrowncoatVariants.BUCKETHEAD,
						new Identifier("pvzmod", "geo/futurebucket.geo.json"));
				map.put(BrowncoatVariants.BUCKETHEADHYPNO,
						new Identifier("pvzmod", "geo/futurebucket.geo.json"));
				map.put(BrowncoatVariants.HOLOHEAD,
						new Identifier("pvzmod", "geo/holohead.geo.json"));
				map.put(BrowncoatVariants.HOLOHEADHYPNO,
						new Identifier("pvzmod", "geo/holohead.geo.json"));
			});

	public Identifier getModelResource(FutureZombieEntity object) {
		return LOCATION_MODEL_BY_VARIANT.get(object.getVariant());
	}

	@Override
	public void preRender(MatrixStack poseStack, FutureZombieEntity animatable, BakedGeoModel model, VertexConsumerProvider bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue,
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
