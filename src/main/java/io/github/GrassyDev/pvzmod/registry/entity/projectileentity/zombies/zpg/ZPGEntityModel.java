package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.zombies.zpg;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ZPGEntityModel extends AnimatedGeoModel<ZPGEntity> {

    @Override
    public Identifier getModelResource(ZPGEntity object)
    {
        return new Identifier("pvzmod", "geo/zpg.geo.json");
    }

    @Override
    public Identifier getTextureResource(ZPGEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/projectiles/zpg.png");
    }

    @Override
    public Identifier getAnimationResource(ZPGEntity object)
    {
        return new Identifier ("pvzmod", "animations/peashot.json");
    }
}
