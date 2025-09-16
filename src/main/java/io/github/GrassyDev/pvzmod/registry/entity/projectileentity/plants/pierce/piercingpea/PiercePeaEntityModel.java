package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.pierce.piercingpea;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class PiercePeaEntityModel extends GeoModel<PiercePeaEntity> {

    @Override
    public Identifier getModelResource(PiercePeaEntity object)
    {
        return Identifier.of("pvzmod", "geo/spit.geo.json");
    }

    @Override
    public Identifier getTextureResource(PiercePeaEntity object)
    {
        return Identifier.of("pvzmod", "textures/entity/projectiles/peashot.png");
    }

    @Override
    public Identifier getAnimationResource(PiercePeaEntity object)
    {
        return Identifier.of ("pvzmod", "animations/peashot.json");
    }
}
