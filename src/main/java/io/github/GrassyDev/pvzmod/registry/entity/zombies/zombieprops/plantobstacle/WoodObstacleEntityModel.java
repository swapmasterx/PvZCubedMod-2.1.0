package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieprops.plantobstacle;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class WoodObstacleEntityModel extends AnimatedGeoModel<WoodObstacleEntity> {

    @Override
    public Identifier getModelResource(WoodObstacleEntity object)
    {
		Identifier identifier;
		identifier = new Identifier("pvzmod", "geo/hawkercart.geo.json");
		return identifier;
    }

    @Override
    public Identifier getTextureResource(WoodObstacleEntity object)
	{
		Identifier identifier;
		identifier = new Identifier("pvzmod", "textures/entity/hawker/hawker.png");
		return identifier;
    }

    @Override
    public Identifier getAnimationResource(WoodObstacleEntity object)
    {
        return new Identifier ("pvzmod", "animations/hawker.json");
    }
}
