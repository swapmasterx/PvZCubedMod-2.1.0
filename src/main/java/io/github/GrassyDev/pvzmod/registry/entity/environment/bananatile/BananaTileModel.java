package io.github.GrassyDev.pvzmod.registry.entity.environment.bananatile;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class BananaTileModel extends GeoModel<BananaTile> {

    @Override
    public Identifier getModelResource(BananaTile object)
    {
        return Identifier.of("pvzmod", "geo/bananapeel.geo.json");
    }

    @Override
    public Identifier getTextureResource(BananaTile object)
    {
        return Identifier.of("pvzmod", "textures/entity/bananasaurus/bananapeel.png");
    }

    @Override
    public Identifier getAnimationResource(BananaTile object)
    {
        return Identifier.of ("pvzmod", "animations/tile.json");
    }
}
