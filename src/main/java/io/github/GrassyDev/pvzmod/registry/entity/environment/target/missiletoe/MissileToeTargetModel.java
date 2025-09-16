package io.github.GrassyDev.pvzmod.registry.entity.environment.target.missiletoe;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class MissileToeTargetModel extends GeoModel<MissileToeTarget> {

    @Override
    public Identifier getModelResource(MissileToeTarget object)
    {
        return Identifier.of("pvzmod", "geo/missiletoetarget.geo.json");
    }

    @Override
    public Identifier getTextureResource(MissileToeTarget object)
    {
        return Identifier.of("pvzmod", "textures/entity/environment/tiles.png");
    }

    @Override
    public Identifier getAnimationResource(MissileToeTarget object)
    {
        return Identifier.of ("pvzmod", "animations/tile.json");
    }
}
