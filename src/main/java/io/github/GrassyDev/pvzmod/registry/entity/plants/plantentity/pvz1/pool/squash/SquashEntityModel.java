package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.squash;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class SquashEntityModel extends GeoModel<SquashEntity> {

    @Override
    public Identifier getModelResource(SquashEntity object)
    {
        return Identifier.of("pvzmod", "geo/squash.geo.json");
    }

	public Identifier getTextureResource(SquashEntity object)
	{
		return Identifier.of("pvzmod", "textures/entity/squash/squash.png");
	}

    @Override
    public Identifier getAnimationResource(SquashEntity object)
    {
        return Identifier.of ("pvzmod", "animations/squash.json");
    }
}
