package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.ancientegypt.frisbloom;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class FrisbloomEntityModel extends GeoModel<FrisbloomEntity> {

    @Override
    public Identifier getModelResource(FrisbloomEntity object)
    {
        return Identifier.of("pvzmod", "geo/frisbloom.geo.json");
    }

    @Override
    public Identifier getTextureResource(FrisbloomEntity object)
    {
        return Identifier.of("pvzmod", "textures/entity/bloomerang/bloomerang.png");
    }

    @Override
    public Identifier getAnimationResource(FrisbloomEntity object)
    {
        return Identifier.of ("pvzmod", "animations/bloomerang.json");
    }
}
