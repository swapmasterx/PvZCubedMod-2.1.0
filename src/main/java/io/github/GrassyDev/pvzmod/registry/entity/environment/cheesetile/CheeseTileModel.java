package io.github.GrassyDev.pvzmod.registry.entity.environment.cheesetile;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class CheeseTileModel extends GeoModel<CheeseTile> {

    @Override
    public Identifier getModelResource(CheeseTile object)
    {
        return Identifier.of("pvzmod", "geo/cheesetile.geo.json");
    }

    @Override
    public Identifier getTextureResource(CheeseTile object)
    {
        return Identifier.of("pvzmod", "textures/entity/environment/tiles.png");
    }

    @Override
    public Identifier getAnimationResource(CheeseTile object)
    {
        return Identifier.of ("pvzmod", "animations/tile.json");
    }
}
