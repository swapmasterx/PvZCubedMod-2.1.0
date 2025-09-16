package io.github.GrassyDev.pvzmod.registry.entity.environment.shadowtile;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class ShadowFullTileModel extends GeoModel<ShadowFullTile> {

    @Override
    public Identifier getModelResource(ShadowFullTile object)
    {
        return Identifier.of("pvzmod", "geo/shadowfulltile.geo.json");
    }

    @Override
    public Identifier getTextureResource(ShadowFullTile object)
    {
        return Identifier.of("pvzmod", "textures/entity/environment/tiles.png");
    }

    @Override
    public Identifier getAnimationResource(ShadowFullTile object)
    {
        return Identifier.of ("pvzmod", "animations/tile.json");
    }
}
