package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.pierce.piercingpea;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class PiercePeaEntityModel extends GeoModel<PiercePeaEntity> {

    @Override
    public Identifier getModelResource(PiercePeaEntity object)
    {
        return new Identifier("pvzmod", "geo/spit.geo.json");
    }

    @Override
    public Identifier getTextureResource(PiercePeaEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/projectiles/peashot.png");
    }

    @Override
    public Identifier getAnimationResource(PiercePeaEntity object)
    {
        return new Identifier ("pvzmod", "animations/peashot.json");
    }
}
