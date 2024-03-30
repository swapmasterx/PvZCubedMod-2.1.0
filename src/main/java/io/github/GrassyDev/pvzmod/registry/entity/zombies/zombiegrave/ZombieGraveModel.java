package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombiegrave;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class ZombieGraveModel extends GeoModel<ZombieGraveEntity> {

    @Override
    public Identifier getModelResource(ZombieGraveEntity object)
    {
        return new Identifier("pvzmod", "geo/zombiegravestone.geo.json");
    }

    @Override
    public Identifier getTextureResource(ZombieGraveEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/gravestone/zombiegravestone.png");
    }

    @Override
    public Identifier getAnimationResource(ZombieGraveEntity object)
    {
        return new Identifier ("pvzmod", "animations/gravestone.json");
    }
}
