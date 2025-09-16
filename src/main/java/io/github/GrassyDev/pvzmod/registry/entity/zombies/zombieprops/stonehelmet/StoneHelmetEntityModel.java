package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieprops.stonehelmet;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class StoneHelmetEntityModel extends GeoModel<StoneHelmetEntity> {

	@Override
	public Identifier getModelResource(StoneHelmetEntity object)
	{
		return Identifier.of("pvzmod", "geo/blank.geo.json");
	}

	@Override
	public Identifier getTextureResource(StoneHelmetEntity object)
	{
		return Identifier.of("pvzmod", "textures/entity/projectiles/peashot.png");
	}

	@Override
	public Identifier getAnimationResource(StoneHelmetEntity object)
	{
		return Identifier.of ("pvzmod", "animations/peashot.json");
	}
}
