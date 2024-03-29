package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2as.magnet;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class MagnetoShroomEntityModel extends AnimatedGeoModel<MagnetoShroomEntity> {

    @Override
    public Identifier getModelResource(MagnetoShroomEntity object)
    {
        return new Identifier("pvzmod", "geo/magnetoshroom.geo.json");
    }

    @Override
    public Identifier getTextureResource(MagnetoShroomEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/magnetshroom/magnetoshroom.png");
    }

    @Override
    public Identifier getAnimationResource(MagnetoShroomEntity object)
    {
        return new Identifier ("pvzmod", "animations/magnetshroom.json");
    }
}
