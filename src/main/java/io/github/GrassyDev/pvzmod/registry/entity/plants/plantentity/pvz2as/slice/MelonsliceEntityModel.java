package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2as.slice;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class MelonsliceEntityModel extends GeoModel<MelonsliceEntity> {

    @Override
    public Identifier getModelResource(MelonsliceEntity object)
    {
        return new Identifier("pvzmod", "geo/melonslice.geo.json");
    }

    @Override
    public Identifier getTextureResource(MelonsliceEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/cabbagepult/melonslice.png");
    }

    @Override
    public Identifier getAnimationResource(MelonsliceEntity object)
    {
        return new Identifier ("pvzmod", "animations/cabbagepult.json");
    }
}
