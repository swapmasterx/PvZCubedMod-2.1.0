package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.straight.dye;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class ShootingDyeEntityModel extends GeoModel<ShootingDyeEntity> {

    @Override
    public Identifier getModelResource(ShootingDyeEntity object)
    {
        return new Identifier("pvzmod", "geo/dyeproj.geo.json");
    }

    @Override
    public Identifier getTextureResource(ShootingDyeEntity object){
		return ShootingDyeEntityRenderer.LOCATION_BY_VARIANT.get(object.getVariant());
	}

    @Override
    public Identifier getAnimationResource(ShootingDyeEntity object)
    {
        return new Identifier ("pvzmod", "animations/peashot.json");
    }
}
