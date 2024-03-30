package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.guardian.smallnut;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class SmallNutEntityModel extends GeoModel<SmallNutEntity> {

    @Override
    public Identifier getModelResource(SmallNutEntity object)
    {
        return new Identifier("pvzmod", "geo/smallnut.geo.json");
    }

    @Override
    public Identifier getTextureResource(SmallNutEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/small/smallnut.png");
    }

    @Override
    public Identifier getAnimationResource(SmallNutEntity object)
    {
        return new Identifier ("pvzmod", "animations/small.json");
    }
}
