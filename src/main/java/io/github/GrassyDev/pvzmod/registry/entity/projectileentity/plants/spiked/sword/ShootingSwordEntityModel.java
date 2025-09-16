package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.spiked.sword;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class ShootingSwordEntityModel extends GeoModel<ShootingSwordEntity> {

    @Override
    public Identifier getModelResource(ShootingSwordEntity object)
    {
        return Identifier.of("pvzmod", "geo/swordproj.geo.json");
    }

    @Override
    public Identifier getTextureResource(ShootingSwordEntity object)
    {
        return Identifier.of("pvzmod", "textures/entity/projectiles/swordproj.png");
    }

    @Override
    public Identifier getAnimationResource(ShootingSwordEntity object)
    {
        return Identifier.of ("pvzmod", "animations/peashot.json");
    }
}
