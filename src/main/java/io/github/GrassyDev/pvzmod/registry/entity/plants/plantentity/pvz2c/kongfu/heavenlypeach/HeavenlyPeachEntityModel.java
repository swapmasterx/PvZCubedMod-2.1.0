package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2c.kongfu.heavenlypeach;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class HeavenlyPeachEntityModel extends AnimatedGeoModel<HeavenlyPeachEntity> {

    @Override
    public Identifier getModelResource(HeavenlyPeachEntity object)
    {
        return new Identifier("pvzmod", "geo/heavenlypeach.geo.json");
    }

    @Override
    public Identifier getTextureResource(HeavenlyPeachEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/heavenlypeach/heavenlypeach.png");
    }

    @Override
    public Identifier getAnimationResource(HeavenlyPeachEntity object)
    {
        return new Identifier ("pvzmod", "animations/heavenlypeach.json");
    }
}
