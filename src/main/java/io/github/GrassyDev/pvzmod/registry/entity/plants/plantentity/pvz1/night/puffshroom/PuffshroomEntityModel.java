package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.night.puffshroom;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class PuffshroomEntityModel extends GeoModel<PuffshroomEntity> {

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
