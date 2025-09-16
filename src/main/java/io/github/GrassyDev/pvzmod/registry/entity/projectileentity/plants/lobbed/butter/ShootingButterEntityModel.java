package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.lobbed.butter;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class ShootingButterEntityModel extends GeoModel<ShootingButterEntity> {

    @Override
    public Identifier getModelResource(ShootingButterEntity object)
    {
        return Identifier.of("pvzmod", "geo/butter.geo.json");
    }

    @Override
    public Identifier getTextureResource(ShootingButterEntity object){
			return Identifier.of("pvzmod", "textures/entity/cabbagepult/kernalpult.png");
	}

    @Override
    public Identifier getAnimationResource(ShootingButterEntity object)
    {
        return Identifier.of ("pvzmod", "animations/peashot.json");
    }
}
