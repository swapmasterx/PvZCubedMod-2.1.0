package io.github.GrassyDev.pvzmod.registry.entity.environment.shadowtile;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ShadowTileModel extends AnimatedGeoModel<ShadowTile> {

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
