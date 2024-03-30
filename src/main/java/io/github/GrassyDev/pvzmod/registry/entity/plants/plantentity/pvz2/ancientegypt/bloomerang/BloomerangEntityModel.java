package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.ancientegypt.bloomerang;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class BloomerangEntityModel extends GeoModel<BloomerangEntity> {

    @Override
    public Identifier getModelResource(BloomerangEntity object)
    {
        return new Identifier("pvzmod", "geo/bloomerang.geo.json");
    }

    @Override
    public Identifier getTextureResource(BloomerangEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/bloomerang/bloomerang.png");
    }

    @Override
    public Identifier getAnimationResource(BloomerangEntity object)
    {
        return new Identifier ("pvzmod", "animations/bloomerang.json");
    }
}
