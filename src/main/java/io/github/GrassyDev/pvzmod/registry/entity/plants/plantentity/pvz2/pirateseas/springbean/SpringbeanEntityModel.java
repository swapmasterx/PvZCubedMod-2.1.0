package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.pirateseas.springbean;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class SpringbeanEntityModel extends GeoModel<SpringbeanEntity> {

    @Override
    public Identifier getModelResource(SpringbeanEntity object)
    {
        return Identifier.of("pvzmod", "geo/springbean.geo.json");
    }

    @Override
    public Identifier getTextureResource(SpringbeanEntity object)
    {
		return Identifier.of("pvzmod", "textures/entity/springbean/springbean.png");
    }

    @Override
    public Identifier getAnimationResource(SpringbeanEntity object)
    {
        return Identifier.of ("pvzmod", "animations/springbean.json");
    }
}
