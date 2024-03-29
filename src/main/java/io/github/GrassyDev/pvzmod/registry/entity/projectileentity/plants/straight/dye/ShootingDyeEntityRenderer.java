package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.straight.dye;

import com.google.common.collect.Maps;
import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.entity.variants.plants.DyeVariants;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

import java.util.Map;

public class ShootingDyeEntityRenderer extends GeoProjectilesRenderer {

	public static final Map<DyeVariants, Identifier> LOCATION_BY_VARIANT =
			Util.make(Maps.newEnumMap(DyeVariants.class), (map) -> {
				map.put(DyeVariants.CONTAIN,
						new Identifier(PvZCubed.MOD_ID, "textures/entity/projectiles/dye.png"));
				map.put(DyeVariants.APPEASE,
						new Identifier(PvZCubed.MOD_ID, "textures/entity/projectiles/dye_appease.png"));
				map.put(DyeVariants.SPEAR,
						new Identifier(PvZCubed.MOD_ID, "textures/entity/projectiles/dye_spear.png"));
				map.put(DyeVariants.CONCEAL,
						new Identifier(PvZCubed.MOD_ID, "textures/entity/projectiles/dye_conceal.png"));
				map.put(DyeVariants.ENFORCE,
						new Identifier(PvZCubed.MOD_ID, "textures/entity/projectiles/dye_enforce.png"));
				map.put(DyeVariants.ENCHANT,
						new Identifier(PvZCubed.MOD_ID, "textures/entity/projectiles/dye_enchant.png"));
				map.put(DyeVariants.AILMENT,
						new Identifier(PvZCubed.MOD_ID, "textures/entity/projectiles/dye_ailment.png"));
				map.put(DyeVariants.BOMBARD,
						new Identifier(PvZCubed.MOD_ID, "textures/entity/projectiles/dye_bombard.png"));
				map.put(DyeVariants.REINFORCE,
						new Identifier(PvZCubed.MOD_ID, "textures/entity/projectiles/dye_reinforce.png"));
				map.put(DyeVariants.ENLIGHTEN,
						new Identifier(PvZCubed.MOD_ID, "textures/entity/projectiles/dye_enlighten.png"));
				map.put(DyeVariants.WINTER,
						new Identifier(PvZCubed.MOD_ID, "textures/entity/projectiles/dye_winter.png"));
				map.put(DyeVariants.PEPPER,
						new Identifier(PvZCubed.MOD_ID, "textures/entity/projectiles/dye_pepper.png"));
				map.put(DyeVariants.FILAMENT,
						new Identifier(PvZCubed.MOD_ID, "textures/entity/projectiles/dye_filament.png"));
				map.put(DyeVariants.ARMA,
						new Identifier(PvZCubed.MOD_ID, "textures/entity/projectiles/dye_arma.png"));
			});

	public Identifier getTextureResource(ShootingDyeEntity object) {
		return LOCATION_BY_VARIANT.get(object.getVariant());
	}

	public ShootingDyeEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new ShootingDyeEntityModel());
		this.shadowRadius = 0.3F; //change 0.7 to the desired shadow size.
	}

}
