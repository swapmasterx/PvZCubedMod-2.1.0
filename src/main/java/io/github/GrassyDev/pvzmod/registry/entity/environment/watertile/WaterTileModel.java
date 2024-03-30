package io.github.GrassyDev.pvzmod.registry.entity.environment.watertile;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class WaterTileModel extends GeoModel<WaterTile> {

    @Override
    public Identifier getModelResource(WaterTile object)
    {
        return new Identifier("pvzmod", "geo/watertile.geo.json");
    }

    @Override
    public Identifier getTextureResource(WaterTile object)
    {
        return new Identifier("pvzmod", "textures/entity/environment/tiles.png");
    }

    @Override
    public Identifier getAnimationResource(WaterTile object)
    {
        return new Identifier ("pvzmod", "animations/tile.json");
    }
}
