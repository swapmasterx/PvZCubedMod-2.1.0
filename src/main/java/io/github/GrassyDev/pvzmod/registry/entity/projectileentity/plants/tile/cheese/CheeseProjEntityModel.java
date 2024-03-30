package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.tile.cheese;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class CheeseProjEntityModel extends GeoModel<CheeseProjEntity> {

    @Override
    public Identifier getModelResource(CheeseProjEntity object)
    {
        return new Identifier("pvzmod", "geo/bigpea.geo.json");
    }

    @Override
    public Identifier getTextureResource(CheeseProjEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/projectiles/cheese.png");
    }

    @Override
    public Identifier getAnimationResource(CheeseProjEntity object)
    {
        return new Identifier ("pvzmod", "animations/peashot.json");
    }
}
