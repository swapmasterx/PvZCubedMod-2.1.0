package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieprops.crystalhelmet;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class CrystalHelmetEntityModel extends GeoModel<CrystalHelmetEntity> {

	@Override
	public Identifier getModelResource(CrystalHelmetEntity object)
	{
		return new Identifier("pvzmod", "geo/blank.geo.json");
	}

	@Override
	public Identifier getTextureResource(CrystalHelmetEntity object)
	{
		return new Identifier("pvzmod", "textures/entity/projectiles/peashot.png");
	}

	@Override
	public Identifier getAnimationResource(CrystalHelmetEntity object)
	{
		return new Identifier ("pvzmod", "animations/peashot.json");
	}
}
