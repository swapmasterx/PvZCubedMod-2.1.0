package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.pierce.piercingpea;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class FirePiercePeaEntityModel extends GeoModel<FirePiercePeaEntity> {

    @Override
    public Identifier getModelResource(FirePiercePeaEntity object)
    {
        return Identifier.of("pvzmod", "geo/spit.geo.json");
    }

    @Override
    public Identifier getTextureResource(FirePiercePeaEntity object)
    {
        return Identifier.of("pvzmod", "textures/entity/projectiles/firepea.png");
    }

    @Override
    public Identifier getAnimationResource(FirePiercePeaEntity object)
    {
        return Identifier.of ("pvzmod", "animations/peashot.json");
    }
}
