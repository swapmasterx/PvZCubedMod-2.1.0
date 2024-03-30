package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.kabloom.buttonshroom;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class ButtonshroomEntityModel extends GeoModel<ButtonshroomEntity> {

    @Override
    public Identifier getModelResource(ButtonshroomEntity object)
    {
        return new Identifier("pvzmod", "geo/buttonshroom.geo.json");
    }

    @Override
    public Identifier getTextureResource(ButtonshroomEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/small/buttonshroom.png");
    }

    @Override
    public Identifier getAnimationResource(ButtonshroomEntity object)
    {
        return new Identifier ("pvzmod", "animations/small.json");
    }
}
