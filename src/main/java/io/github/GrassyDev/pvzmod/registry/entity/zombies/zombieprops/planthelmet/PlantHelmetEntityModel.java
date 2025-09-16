package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieprops.planthelmet;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class PlantHelmetEntityModel extends GeoModel<PlantHelmetEntity> {

	@Override
	public Identifier getModelResource(PlantHelmetEntity object)
	{
		return Identifier.of("pvzmod", "geo/blank.geo.json");
	}

	@Override
	public Identifier getTextureResource(PlantHelmetEntity object)
	{
		return Identifier.of("pvzmod", "textures/entity/projectiles/peashot.png");
	}

	@Override
	public Identifier getAnimationResource(PlantHelmetEntity object)
	{
		return Identifier.of ("pvzmod", "animations/peashot.json");
	}
}
