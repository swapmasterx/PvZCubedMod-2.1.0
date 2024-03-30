package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.farfuture.empeach;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class EMPeachEntityModel extends GeoModel<EMPeachEntity> {

    @Override
    public Identifier getModelResource(EMPeachEntity object)
    {
        return new Identifier("pvzmod", "geo/empeach.geo.json");
    }

    @Override
    public Identifier getTextureResource(EMPeachEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/empeach/empeach.png");
    }

    @Override
    public Identifier getAnimationResource(EMPeachEntity object)
    {
        return new Identifier ("pvzmod", "animations/empeach.json");
    }
}
