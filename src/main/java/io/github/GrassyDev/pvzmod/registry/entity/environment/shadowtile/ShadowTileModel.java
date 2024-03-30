package io.github.GrassyDev.pvzmod.registry.entity.environment.shadowtile;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class ShadowTileModel extends GeoModel<ShadowTile> {

    @Override
    public Identifier getModelResource(ShadowTile object)
    {
        return new Identifier("pvzmod", "geo/shadowtile.geo.json");
    }

    @Override
    public Identifier getTextureResource(ShadowTile object)
    {
        return new Identifier("pvzmod", "textures/entity/environment/tiles.png");
    }

    @Override
    public Identifier getAnimationResource(ShadowTile object)
    {
        return new Identifier ("pvzmod", "animations/tile.json");
    }
}
