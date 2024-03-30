package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.og.gambleshroom;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class GamblehatEntityModel extends GeoModel<GamblehatEntity> {

    @Override
    public Identifier getModelResource(GamblehatEntity object)
    {
        return new Identifier("pvzmod", "geo/magichat.geo.json");
    }

    @Override
    public Identifier getTextureResource(GamblehatEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/magicshroom/gambleshroom.png");
    }

    @Override
    public Identifier getAnimationResource(GamblehatEntity object)
    {
        return new Identifier ("pvzmod", "animations/magicshroom.json");
    }
}
