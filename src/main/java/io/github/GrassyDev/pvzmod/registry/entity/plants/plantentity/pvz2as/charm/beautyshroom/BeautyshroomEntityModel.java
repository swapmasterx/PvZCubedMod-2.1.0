package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2as.charm.beautyshroom;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class BeautyshroomEntityModel extends GeoModel<BeautyshroomEntity> {

    @Override
    public Identifier getModelResource(BeautyshroomEntity object)
    {
        return new Identifier("pvzmod", "geo/beautyshroom.geo.json");
    }

    @Override
    public Identifier getTextureResource(BeautyshroomEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/hypnoshroom/beautyshroom.png");
    }

    @Override
    public Identifier getAnimationResource(BeautyshroomEntity object)
    {
        return new Identifier ("pvzmod", "animations/beautyshroom.json");
    }
}
