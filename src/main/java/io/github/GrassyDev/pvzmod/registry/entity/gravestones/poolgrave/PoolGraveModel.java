package io.github.GrassyDev.pvzmod.registry.entity.gravestones.poolgrave;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class PoolGraveModel extends GeoModel<PoolGraveEntity> {

    @Override
    public Identifier getModelResource(PoolGraveEntity object)
    {
        return new Identifier("pvzmod", "geo/poolgravestone.geo.json");
    }

    @Override
    public Identifier getTextureResource(PoolGraveEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/gravestone/poolgravestone.png");
    }

    @Override
    public Identifier getAnimationResource(PoolGraveEntity object)
    {
        return new Identifier ("pvzmod", "animations/gravestone.json");
    }
}
