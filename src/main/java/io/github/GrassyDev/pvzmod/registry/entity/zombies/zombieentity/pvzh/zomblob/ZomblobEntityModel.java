package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvzh.zomblob;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ZomblobEntityModel extends AnimatedGeoModel<ZomblobEntity> {

    @Override
    public Identifier getModelResource(ZomblobEntity object)
    {
        return new Identifier("pvzmod", "geo/zomblob.geo.json");
    }

    @Override
    public Identifier getTextureResource(ZomblobEntity object) {
		return new Identifier("pvzmod", "textures/entity/browncoat/zomblob.png");
    }

    @Override
    public Identifier getAnimationResource(ZomblobEntity object)
    {
        return new Identifier ("pvzmod", "animations/newbrowncoat.json");
    }
}
