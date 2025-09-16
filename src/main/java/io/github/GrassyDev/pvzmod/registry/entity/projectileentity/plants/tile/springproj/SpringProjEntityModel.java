package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.tile.springproj;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class SpringProjEntityModel extends GeoModel<SpringProjEntity> {

    @Override
    public Identifier getModelResource(SpringProjEntity object)
    {
        return Identifier.of("pvzmod", "geo/springtile.geo.json");
    }

    @Override
    public Identifier getTextureResource(SpringProjEntity object)
    {
        return Identifier.of("pvzmod", "textures/entity/springprincess/springprincess.png");
    }

    @Override
    public Identifier getAnimationResource(SpringProjEntity object)
    {
        return Identifier.of ("pvzmod", "animations/peashot.json");
    }
}
