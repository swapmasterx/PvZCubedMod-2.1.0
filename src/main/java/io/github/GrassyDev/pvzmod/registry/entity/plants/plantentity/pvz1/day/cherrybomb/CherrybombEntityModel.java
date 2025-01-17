package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.day.cherrybomb;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class CherrybombEntityModel extends GeoModel<CherrybombEntity> {

    @Override
    public Identifier getModelResource(CherrybombEntity object)
    {
        return new Identifier("pvzmod", "geo/cherrybomb.geo.json");
    }

    @Override
    public Identifier getTextureResource(CherrybombEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/cherrybomb/cherrybomb.png");
    }

    @Override
    public Identifier getAnimationResource(CherrybombEntity object)
    {
        return new Identifier ("pvzmod", "animations/cherrybomb.json");
    }
}
