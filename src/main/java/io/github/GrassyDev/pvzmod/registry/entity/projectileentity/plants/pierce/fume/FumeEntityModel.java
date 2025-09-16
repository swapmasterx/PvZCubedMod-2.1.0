package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.pierce.fume;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class FumeEntityModel extends GeoModel<FumeEntity> {

    @Override
    public Identifier getModelResource(FumeEntity object)
    {
        return Identifier.of("pvzmod", "geo/blank.geo.json");
    }

    @Override
    public Identifier getTextureResource(FumeEntity object)
    {
        return Identifier.of("pvzmod", "textures/entity/projectiles/fume.png");
    }

    @Override
    public Identifier getAnimationResource(FumeEntity object)
    {
        return Identifier.of ("pvzmod", "animations/peashot.json");
    }
}
