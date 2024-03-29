package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.tile.banana;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BananaProjEntityModel extends AnimatedGeoModel<BananaProjEntity> {

    @Override
    public Identifier getModelResource(BananaProjEntity object)
    {
        return new Identifier("pvzmod", "geo/bananapeel.geo.json");
    }

    @Override
    public Identifier getTextureResource(BananaProjEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/bananasaurus/bananapeel.png");
    }

    @Override
    public Identifier getAnimationResource(BananaProjEntity object)
    {
        return new Identifier ("pvzmod", "animations/peashot.json");
    }
}
