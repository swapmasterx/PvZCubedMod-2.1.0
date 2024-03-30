package io.github.GrassyDev.pvzmod.registry.entity.environment.rosebuds;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class RoseBudEntityModel extends GeoModel<RoseBudTile> {

    @Override
    public Identifier getModelResource(RoseBudTile object)
    {
        return new Identifier("pvzmod", "geo/doomrose.geo.json");
    }

	public Identifier getTextureResource(RoseBudTile object) {
		if (object.getShadowPowered()){
			return new Identifier("pvzmod", "textures/entity/rose/doomrose_shadow.png");
		}
		else if (object.getMoonPowered()){
			return new Identifier("pvzmod", "textures/entity/rose/doomrose_moon.png");
		}
		else {
			return new Identifier("pvzmod", "textures/entity/rose/doomrose.png");
		}
	}

    @Override
    public Identifier getAnimationResource(RoseBudTile object)
    {
        return new Identifier ("pvzmod", "animations/doomrose.json");
    }
}
