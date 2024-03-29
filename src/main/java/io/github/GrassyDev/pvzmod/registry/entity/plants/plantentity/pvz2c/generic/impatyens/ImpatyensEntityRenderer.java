package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2c.generic.impatyens;

import com.google.common.collect.Maps;
import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.entity.variants.plants.DyeVariants;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import java.util.Map;

public class ImpatyensEntityRenderer extends GeoEntityRenderer<ImpatyensEntity> {

	public static final Map<DyeVariants, Identifier> LOCATION_BY_VARIANT =
			Util.make(Maps.newEnumMap(DyeVariants.class), (map) -> {
				map.put(DyeVariants.CONTAIN,
						new Identifier(PvZCubed.MOD_ID, "textures/entity/impatyens/impatyens.png"));
				map.put(DyeVariants.APPEASE,
						new Identifier(PvZCubed.MOD_ID, "textures/entity/impatyens/impatyens_appease.png"));
				map.put(DyeVariants.SPEAR,
						new Identifier(PvZCubed.MOD_ID, "textures/entity/impatyens/impatyens_spear.png"));
				map.put(DyeVariants.CONCEAL,
						new Identifier(PvZCubed.MOD_ID, "textures/entity/impatyens/impatyens_conceal.png"));
				map.put(DyeVariants.ENFORCE,
						new Identifier(PvZCubed.MOD_ID, "textures/entity/impatyens/impatyens_enforce.png"));
				map.put(DyeVariants.ENCHANT,
						new Identifier(PvZCubed.MOD_ID, "textures/entity/impatyens/impatyens_enchant.png"));
				map.put(DyeVariants.AILMENT,
						new Identifier(PvZCubed.MOD_ID, "textures/entity/impatyens/impatyens_ailment.png"));
				map.put(DyeVariants.BOMBARD,
						new Identifier(PvZCubed.MOD_ID, "textures/entity/impatyens/impatyens_bombard.png"));
				map.put(DyeVariants.REINFORCE,
						new Identifier(PvZCubed.MOD_ID, "textures/entity/impatyens/impatyens_reinforce.png"));
				map.put(DyeVariants.ENLIGHTEN,
						new Identifier(PvZCubed.MOD_ID, "textures/entity/impatyens/impatyens_enlighten.png"));
				map.put(DyeVariants.WINTER,
						new Identifier(PvZCubed.MOD_ID, "textures/entity/impatyens/impatyens_winter.png"));
				map.put(DyeVariants.PEPPER,
						new Identifier(PvZCubed.MOD_ID, "textures/entity/impatyens/impatyens_pepper.png"));
				map.put(DyeVariants.FILAMENT,
						new Identifier(PvZCubed.MOD_ID, "textures/entity/impatyens/impatyens_filament.png"));
				map.put(DyeVariants.ARMA,
						new Identifier(PvZCubed.MOD_ID, "textures/entity/impatyens/impatyens_arma.png"));
			});

	public Identifier getTextureResource(ImpatyensEntity object) {
		return LOCATION_BY_VARIANT.get(object.getVariant());
	}

	public ImpatyensEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new ImpatyensEntityModel());
		this.shadowRadius = 0.3F; //change 0.7 to the desired shadow size.
	}

}
