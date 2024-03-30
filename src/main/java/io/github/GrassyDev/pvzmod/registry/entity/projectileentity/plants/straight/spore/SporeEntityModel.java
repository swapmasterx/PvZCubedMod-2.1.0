package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.straight.spore;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class SporeEntityModel extends GeoModel<SporeEntity> {

    @Override
    public Identifier getModelResource(SporeEntity object)
    {
        return new Identifier("pvzmod", "geo/peashot.geo.json");
    }

    @Override
    public Identifier getTextureResource(SporeEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/projectiles/fume.png");
    }

    @Override
    public Identifier getAnimationResource(SporeEntity object)
    {
        return new Identifier ("pvzmod", "animations/peashot.json");
    }
}
