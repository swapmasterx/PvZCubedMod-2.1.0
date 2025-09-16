package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieprops.fleshobstacle;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class FleshObstacleEntityModel extends GeoModel<FleshObstacleEntity> {

    @Override
    public Identifier getModelResource(FleshObstacleEntity object)
    {
		Identifier identifier;
		identifier = Identifier.of("pvzmod", "geo/octoobstacle.geo.json");
		return identifier;
    }

    @Override
    public Identifier getTextureResource(FleshObstacleEntity object)
	{
		Identifier identifier;
		identifier = Identifier.of("pvzmod", "textures/entity/bully/octo.png");
		return identifier;
    }

    @Override
    public Identifier getAnimationResource(FleshObstacleEntity object)
    {
        return Identifier.of ("pvzmod", "animations/gravestone.json");
    }
}
