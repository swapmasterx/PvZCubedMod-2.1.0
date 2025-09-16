package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieprops.plantobstacle;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class WoodObstacleEntityModel extends GeoModel<WoodObstacleEntity> {

    @Override
    public Identifier getModelResource(WoodObstacleEntity object)
    {
		Identifier identifier;
		identifier = Identifier.of("pvzmod", "geo/hawkercart.geo.json");
		return identifier;
    }

    @Override
    public Identifier getTextureResource(WoodObstacleEntity object)
	{
		Identifier identifier;
		identifier = Identifier.of("pvzmod", "textures/entity/hawker/hawker.png");
		return identifier;
    }

    @Override
    public Identifier getAnimationResource(WoodObstacleEntity object)
    {
        return Identifier.of ("pvzmod", "animations/hawker.json");
    }
}
