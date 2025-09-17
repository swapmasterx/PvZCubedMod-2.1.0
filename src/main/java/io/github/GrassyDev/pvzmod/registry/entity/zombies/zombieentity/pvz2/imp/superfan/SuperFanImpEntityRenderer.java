package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2.imp.superfan;

import com.google.common.collect.Maps;

import com.mojang.blaze3d.vertex.VertexConsumer;
import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.entity.variants.zombies.ImpVariants;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.oc.pumpkincar.PumpkinCarEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.oc.pumpkincar.PumpkinCarEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2.imp.announcer.AnnouncerImpEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.animatable.GeoAnimatable;

import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.util.ClientUtil;
import software.bernie.geckolib.util.Color;
import software.bernie.geckolib.util.GeckoLibUtil;
import io.github.GrassyDev.pvzmod.registry.entity.damage.PvZDamageTypes;

import software.bernie.geckolib.renderer.GeoEntityRenderer;

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
						Identifier.of(PvZCubed.MOD_ID, "textures/entity/imp/superfanimp.png"));
				map.put(ImpVariants.NEWYEAR,
						Identifier.of(PvZCubed.MOD_ID, "textures/entity/imp/newyearimp.png"));
			});

	public Identifier getTextureResource(SuperFanImpEntity object) {
		return LOCATION_BY_VARIANT.get(object.getVariant());
	}

	protected int getBlockLight(SuperFanImpEntity zombie, BlockPos blockPos) {
		return zombie.getFireStage()? 15 : super.getBlockLight(zombie, blockPos);
	}
	@Override
	public Color getRenderColor(SuperFanImpEntity animatable, float partialTick, int packedLight) {
		Color color = SuperFanImpEntityRenderer.super.getRenderColor(animatable, partialTick, packedLight);

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
}
