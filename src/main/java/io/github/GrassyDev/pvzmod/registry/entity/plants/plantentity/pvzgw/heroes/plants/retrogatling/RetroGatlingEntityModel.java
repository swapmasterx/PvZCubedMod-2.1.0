package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzgw.heroes.plants.retrogatling;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class RetroGatlingEntityModel extends GeoModel<RetroGatlingEntity> {

    @Override
    public Identifier getModelResource(RetroGatlingEntity object)
    {
        return new Identifier("pvzmod", "geo/retrogatling.geo.json");
    }

    @Override
    public Identifier getTextureResource(RetroGatlingEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/peashooter/retrogatling.png");
    }

    @Override
    public Identifier getAnimationResource(RetroGatlingEntity object)
    {
        return new Identifier ("pvzmod", "animations/peashooter.json");
    }
}
