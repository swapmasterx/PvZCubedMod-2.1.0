package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2as.boomerang.frisbloom;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class FrisbloomEntityModel extends AnimatedGeoModel<FrisbloomEntity> {

    @Override
    public Identifier getModelResource(FrisbloomEntity object)
    {
        return new Identifier("pvzmod", "geo/frisbloom.geo.json");
    }

    @Override
    public Identifier getTextureResource(FrisbloomEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/bloomerang/bloomerang.png");
    }

    @Override
    public Identifier getAnimationResource(FrisbloomEntity object)
    {
        return new Identifier ("pvzmod", "animations/bloomerang.json");
    }
}
