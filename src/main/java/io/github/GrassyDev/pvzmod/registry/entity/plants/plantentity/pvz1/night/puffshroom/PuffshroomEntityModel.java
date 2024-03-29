package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.night.puffshroom;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class PuffshroomEntityModel extends AnimatedGeoModel<PuffshroomEntity> {

    @Override
    public Identifier getModelResource(PuffshroomEntity object)
    {
        return new Identifier("pvzmod", "geo/puffshroom.geo.json");
    }

    @Override
    public Identifier getTextureResource(PuffshroomEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/puffshroom/puffshroom.png");
    }

    @Override
    public Identifier getAnimationResource(PuffshroomEntity object)
    {
        return new Identifier ("pvzmod", "animations/puffshroom.json");
    }
}
