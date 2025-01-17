package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieprops.planthelmet;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class PlantHelmetEntityModel extends GeoModel<PlantHelmetEntity> {

	@Override
	public Identifier getModelResource(PlantHelmetEntity object)
	{
		return new Identifier("pvzmod", "geo/blank.geo.json");
	}

	@Override
	public Identifier getTextureResource(PlantHelmetEntity object)
	{
		return new Identifier("pvzmod", "textures/entity/projectiles/peashot.png");
	}

	@Override
	public Identifier getAnimationResource(PlantHelmetEntity object)
	{
		return new Identifier ("pvzmod", "animations/peashot.json");
	}
}
