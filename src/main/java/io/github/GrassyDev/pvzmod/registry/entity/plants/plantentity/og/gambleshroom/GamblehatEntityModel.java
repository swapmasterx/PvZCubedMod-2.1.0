package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.og.gambleshroom;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class GamblehatEntityModel extends GeoModel<GamblehatEntity> {

    @Override
    public Identifier getModelResource(GamblehatEntity object)
    {
        return Identifier.of("pvzmod", "geo/magichat.geo.json");
    }

    @Override
    public Identifier getTextureResource(GamblehatEntity object)
    {
        return Identifier.of("pvzmod", "textures/entity/magicshroom/gambleshroom.png");
    }

    @Override
    public Identifier getAnimationResource(GamblehatEntity object)
    {
        return Identifier.of ("pvzmod", "animations/magicshroom.json");
    }
}
