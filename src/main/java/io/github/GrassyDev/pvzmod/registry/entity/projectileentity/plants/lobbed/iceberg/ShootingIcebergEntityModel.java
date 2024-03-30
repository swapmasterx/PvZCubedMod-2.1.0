package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.lobbed.iceberg;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class ShootingIcebergEntityModel extends GeoModel<ShootingIcebergEntity> {

    @Override
    public Identifier getModelResource(ShootingIcebergEntity object)
    {
        return new Identifier("pvzmod", "geo/iceberg.geo.json");
    }

    @Override
    public Identifier getTextureResource(ShootingIcebergEntity object){
			return new Identifier("pvzmod", "textures/entity/cabbagepult/icebergpult.png");
	}

    @Override
    public Identifier getAnimationResource(ShootingIcebergEntity object)
    {
        return new Identifier ("pvzmod", "animations/peashot.json");
    }
}
