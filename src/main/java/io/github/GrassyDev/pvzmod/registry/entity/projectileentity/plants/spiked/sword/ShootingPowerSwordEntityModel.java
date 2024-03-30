package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.spiked.sword;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class ShootingPowerSwordEntityModel extends GeoModel<ShootingPowerSwordEntity> {

    @Override
    public Identifier getModelResource(ShootingPowerSwordEntity object)
    {
        return new Identifier("pvzmod", "geo/swordproj.geo.json");
    }

    @Override
    public Identifier getTextureResource(ShootingPowerSwordEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/projectiles/powerswordproj.png");
    }

    @Override
    public Identifier getAnimationResource(ShootingPowerSwordEntity object)
    {
        return new Identifier ("pvzmod", "animations/peashot.json");
    }
}
