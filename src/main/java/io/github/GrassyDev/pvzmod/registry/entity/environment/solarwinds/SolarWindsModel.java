package io.github.GrassyDev.pvzmod.registry.entity.environment.solarwinds;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class SolarWindsModel extends GeoModel<SolarWinds> {

    @Override
    public Identifier getModelResource(SolarWinds object)
    {
        return Identifier.of("pvzmod", "geo/blank.geo.json");
    }

    @Override
    public Identifier getTextureResource(SolarWinds object)
    {
		return Identifier.of("pvzmod", "textures/entity/projectiles/peashot.png");
    }

    @Override
    public Identifier getAnimationResource(SolarWinds object)
    {
        return Identifier.of ("pvzmod", "animations/tile.json");
    }
}
