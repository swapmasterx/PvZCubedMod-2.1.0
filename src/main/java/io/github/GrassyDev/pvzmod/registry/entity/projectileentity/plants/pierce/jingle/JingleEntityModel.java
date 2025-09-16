package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.pierce.jingle;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class JingleEntityModel extends GeoModel<JingleEntity> {

    @Override
    public Identifier getModelResource(JingleEntity object)
    {
        return Identifier.of("pvzmod", "geo/blank.geo.json");
    }

    @Override
    public Identifier getTextureResource(JingleEntity object)
    {
        return Identifier.of("pvzmod", "textures/entity/projectiles/peashot.png");
    }

    @Override
    public Identifier getAnimationResource(JingleEntity object)
    {
        return Identifier.of ("pvzmod", "animations/peashot.json");
    }
}
