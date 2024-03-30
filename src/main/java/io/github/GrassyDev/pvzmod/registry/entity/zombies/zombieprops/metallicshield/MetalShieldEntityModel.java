package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieprops.metallicshield;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class MetalShieldEntityModel extends GeoModel<MetalShieldEntity> {

    @Override
    public Identifier getModelResource(MetalShieldEntity object)
    {
        return new Identifier("pvzmod", "geo/blank.geo.json");
    }

    @Override
    public Identifier getTextureResource(MetalShieldEntity object)
    {
		return new Identifier("pvzmod", "textures/entity/projectiles/peashot.png");
    }

    @Override
    public Identifier getAnimationResource(MetalShieldEntity object)
    {
		return new Identifier ("pvzmod", "animations/peashot.json");
    }
}
