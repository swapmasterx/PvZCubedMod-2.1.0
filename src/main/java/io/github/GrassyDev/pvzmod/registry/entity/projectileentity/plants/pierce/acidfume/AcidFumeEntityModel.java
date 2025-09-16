package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.pierce.acidfume;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class AcidFumeEntityModel extends GeoModel<AcidFumeEntity> {

    @Override
    public Identifier getModelResource(AcidFumeEntity object)
    {
        return Identifier.of("pvzmod", "geo/blank.geo.json");
    }

    @Override
    public Identifier getTextureResource(AcidFumeEntity object)
    {
        return Identifier.of("pvzmod", "textures/entity/projectiles/fume.png");
    }

    @Override
    public Identifier getAnimationResource(AcidFumeEntity object)
    {
        return Identifier.of ("pvzmod", "animations/peashot.json");
    }
}
