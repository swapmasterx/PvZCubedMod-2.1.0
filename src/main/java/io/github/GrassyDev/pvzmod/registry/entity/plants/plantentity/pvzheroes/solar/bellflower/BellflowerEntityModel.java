package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.solar.bellflower;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class BellflowerEntityModel extends GeoModel<BellflowerEntity> {

    @Override
    public Identifier getModelResource(BellflowerEntity object)
    {
        return new Identifier("pvzmod", "geo/bellflower.geo.json");
    }

    @Override
    public Identifier getTextureResource(BellflowerEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/bellflower/bellflower.png");
    }

    @Override
    public Identifier getAnimationResource(BellflowerEntity object)
    {
        return new Identifier ("pvzmod", "animations/bellflower.json");
    }
}
