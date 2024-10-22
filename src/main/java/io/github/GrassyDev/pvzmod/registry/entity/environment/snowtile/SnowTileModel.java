package io.github.GrassyDev.pvzmod.registry.entity.environment.snowtile;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class SnowTileModel extends GeoModel<SnowTile> {

    @Override
    public Identifier getModelResource(SnowTile object)
    {
        return new Identifier("pvzmod", "geo/snowtile.geo.json");
    }

    @Override
    public Identifier getTextureResource(SnowTile object)
    {
        return new Identifier("pvzmod", "textures/entity/environment/tiles.png");
    }

    @Override
    public Identifier getAnimationResource(SnowTile object)
    {
        return new Identifier ("pvzmod", "animations/tile.json");
    }
}
