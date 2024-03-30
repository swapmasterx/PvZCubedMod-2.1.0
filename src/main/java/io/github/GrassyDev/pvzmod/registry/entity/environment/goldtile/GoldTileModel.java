package io.github.GrassyDev.pvzmod.registry.entity.environment.goldtile;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class GoldTileModel extends GeoModel<GoldTile> {

    @Override
    public Identifier getModelResource(GoldTile object)
    {
        return new Identifier("pvzmod", "geo/goldtile.geo.json");
    }

    @Override
    public Identifier getTextureResource(GoldTile object)
    {
        return new Identifier("pvzmod", "textures/entity/environment/tiles.png");
    }

    @Override
    public Identifier getAnimationResource(GoldTile object)
    {
        return new Identifier ("pvzmod", "animations/tile.json");
    }
}
