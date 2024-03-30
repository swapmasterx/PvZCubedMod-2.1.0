package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.lobbed.cabbage;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class ShootingCabbageEntityModel extends GeoModel<ShootingCabbageEntity> {

    @Override
    public Identifier getModelResource(ShootingCabbageEntity object)
    {
        return new Identifier("pvzmod", "geo/cabbage.geo.json");
    }

    @Override
    public Identifier getTextureResource(ShootingCabbageEntity object){
			return new Identifier("pvzmod", "textures/entity/projectiles/cabbage.png");
	}

    @Override
    public Identifier getAnimationResource(ShootingCabbageEntity object)
    {
        return new Identifier ("pvzmod", "animations/peashot.json");
    }
}
