package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz1.zomboni;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class ZomboniEntityModel extends GeoModel<ZomboniEntity> {

	@Override
	public Identifier getModelResource(ZomboniEntity object)
	{
		return Identifier.of("pvzmod", "geo/blank.geo.json");
	}

	@Override
	public Identifier getTextureResource(ZomboniEntity object)
	{
		return Identifier.of("pvzmod", "textures/entity/zomboni/zomboni.png");
	}

	@Override
	public Identifier getAnimationResource(ZomboniEntity object)
	{
		return Identifier.of ("pvzmod", "animations/zomboni.json");
	}
}
