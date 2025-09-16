package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.zombies.flamingbook;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class FlamingBookEntityModel extends GeoModel<FlamingBookEntity> {

    @Override
    public Identifier getModelResource(FlamingBookEntity object)
    {
        return Identifier.of("pvzmod", "geo/flamingbook.geo.json");
    }

    @Override
    public Identifier getTextureResource(FlamingBookEntity object){
		if (object.getFireStage()) {
			return Identifier.of("pvzmod", "textures/entity/browncoat/sargeant/sargeant.png");
		}
		else {
			return Identifier.of("pvzmod", "textures/entity/browncoat/sargeant/sargeant_extinguished.png");
		}
	}

    @Override
    public Identifier getAnimationResource(FlamingBookEntity object)
    {
        return Identifier.of ("pvzmod", "animations/peashot.json");
    }
}
