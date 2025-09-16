package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.solar.bellflower;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class BellflowerEntityModel extends GeoModel<BellflowerEntity> {

    @Override
    public Identifier getModelResource(BellflowerEntity object)
    {
        return Identifier.of("pvzmod", "geo/bellflower.geo.json");
    }

    @Override
    public Identifier getTextureResource(BellflowerEntity object)
    {
        return Identifier.of("pvzmod", "textures/entity/bellflower/bellflower.png");
    }

    @Override
    public Identifier getAnimationResource(BellflowerEntity object)
    {
        return Identifier.of ("pvzmod", "animations/bellflower.json");
    }
}
