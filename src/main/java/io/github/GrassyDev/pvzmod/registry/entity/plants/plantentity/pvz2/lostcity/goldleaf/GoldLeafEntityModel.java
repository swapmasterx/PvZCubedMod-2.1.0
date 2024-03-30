package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.lostcity.goldleaf;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class GoldLeafEntityModel extends GeoModel<GoldLeafEntity> {

    @Override
    public Identifier getModelResource(GoldLeafEntity object)
    {
        return new Identifier("pvzmod", "geo/goldleaf.geo.json");
    }

    @Override
    public Identifier getTextureResource(GoldLeafEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/environment/goldleaf.png");
    }

    @Override
    public Identifier getAnimationResource(GoldLeafEntity object)
    {
        return new Identifier ("pvzmod", "animations/goldleaf.json");
    }
}
