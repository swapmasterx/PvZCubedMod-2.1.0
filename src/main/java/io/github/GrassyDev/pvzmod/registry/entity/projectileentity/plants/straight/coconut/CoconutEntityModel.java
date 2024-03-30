package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.straight.coconut;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class CoconutEntityModel extends GeoModel<CoconutEntity> {

    @Override
    public Identifier getModelResource(CoconutEntity object)
    {
        return new Identifier("pvzmod", "geo/bigpea.geo.json");
    }

    @Override
    public Identifier getTextureResource(CoconutEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/projectiles/coconut.png");
    }

    @Override
    public Identifier getAnimationResource(CoconutEntity object)
    {
        return new Identifier ("pvzmod", "animations/peashot.json");
    }
}
