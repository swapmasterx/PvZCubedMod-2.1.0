package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieprops.metallichelmet;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class MetalHelmetEntityModel extends GeoModel<MetalHelmetEntity> {

	@Override
	public Identifier getModelResource(MetalHelmetEntity object)
	{
		return new Identifier("pvzmod", "geo/blank.geo.json");
	}

	@Override
	public Identifier getTextureResource(MetalHelmetEntity object)
	{
		return new Identifier("pvzmod", "textures/entity/projectiles/peashot.png");
	}

	@Override
	public Identifier getAnimationResource(MetalHelmetEntity object)
	{
		return new Identifier ("pvzmod", "animations/peashot.json");
	}
}
