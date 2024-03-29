package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.spiked.sword;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ShootingSwordEntityModel extends AnimatedGeoModel<ShootingSwordEntity> {

    @Override
    public Identifier getModelResource(ShootingSwordEntity object)
    {
        return new Identifier("pvzmod", "geo/swordproj.geo.json");
    }

    @Override
    public Identifier getTextureResource(ShootingSwordEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/projectiles/swordproj.png");
    }

    @Override
    public Identifier getAnimationResource(ShootingSwordEntity object)
    {
        return new Identifier ("pvzmod", "animations/peashot.json");
    }
}
