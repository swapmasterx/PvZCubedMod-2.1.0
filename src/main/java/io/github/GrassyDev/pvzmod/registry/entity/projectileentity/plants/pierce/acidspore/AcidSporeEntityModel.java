package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.pierce.acidspore;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class AcidSporeEntityModel extends GeoModel<AcidSporeEntity> {

    @Override
    public Identifier getModelResource(AcidSporeEntity object)
    {
        return new Identifier("pvzmod", "geo/peashot.geo.json");
    }

    @Override
    public Identifier getTextureResource(AcidSporeEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/projectiles/acidspore.png");
    }

    @Override
    public Identifier getAnimationResource(AcidSporeEntity object)
    {
        return new Identifier ("pvzmod", "animations/peashot.json");
    }
}
