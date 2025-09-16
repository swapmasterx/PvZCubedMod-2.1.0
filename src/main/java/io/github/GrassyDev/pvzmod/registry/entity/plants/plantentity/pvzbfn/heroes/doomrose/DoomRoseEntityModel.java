package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzbfn.heroes.doomrose;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class DoomRoseEntityModel extends GeoModel<DoomRoseEntity> {

    @Override
    public Identifier getModelResource(DoomRoseEntity object)
    {
		if (object.getShadowPowered()){
			return Identifier.of("pvzmod", "geo/doomrose_shadow.geo.json");

		}
		else if (object.getMoonPowered()){
			return Identifier.of("pvzmod", "geo/doomrose_moon.geo.json");
		}
		else {
			return Identifier.of("pvzmod", "geo/doomrose.geo.json");
		}
    }

	public Identifier getTextureResource(DoomRoseEntity object) {
		if (object.getShadowPowered()){
			return Identifier.of("pvzmod", "textures/entity/rose/doomrose_shadow.png");
		}
		else if (object.getMoonPowered()){
			return Identifier.of("pvzmod", "textures/entity/rose/doomrose_moon.png");
		}
		else {
			return Identifier.of("pvzmod", "textures/entity/rose/doomrose.png");
		}
	}

    @Override
    public Identifier getAnimationResource(DoomRoseEntity object)
    {
        return Identifier.of ("pvzmod", "animations/doomrose.json");
    }
}
