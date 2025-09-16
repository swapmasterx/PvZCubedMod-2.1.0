package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.smarty.navybean;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class NavyBeanEntityModel extends GeoModel<NavyBeanEntity> {

    @Override
    public Identifier getModelResource(NavyBeanEntity object)
    {
        return Identifier.of("pvzmod", "geo/navybean.geo.json");
    }

	public Identifier getTextureResource(NavyBeanEntity object) {
		return Identifier.of("pvzmod", "textures/entity/bean/navybean.png");
	}

    @Override
    public Identifier getAnimationResource(NavyBeanEntity object)
    {
        return Identifier.of ("pvzmod", "animations/navybean.json");
    }
}
