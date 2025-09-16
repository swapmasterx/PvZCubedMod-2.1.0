package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2c.fairytale.springprincess;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class SpringPrincessEntityModel extends GeoModel<SpringPrincessEntity> {

    @Override
    public Identifier getModelResource(SpringPrincessEntity object)
    {
        return Identifier.of("pvzmod", "geo/springprincess.geo.json");
    }

	public Identifier getTextureResource(SpringPrincessEntity object) {
		return Identifier.of("pvzmod", "textures/entity/springprincess/springprincess.png");
	}

    @Override
    public Identifier getAnimationResource(SpringPrincessEntity object)
    {
        return Identifier.of ("pvzmod", "animations/springprincess.json");
    }
}
