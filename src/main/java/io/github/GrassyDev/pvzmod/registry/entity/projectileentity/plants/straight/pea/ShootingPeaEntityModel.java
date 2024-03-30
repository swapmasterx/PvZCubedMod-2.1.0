package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.straight.pea;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class ShootingPeaEntityModel extends GeoModel<ShootingPeaEntity> {

    @Override
    public Identifier getModelResource(ShootingPeaEntity object)
    {
        return new Identifier("pvzmod", "geo/peashot.geo.json");
    }

    @Override
    public Identifier getTextureResource(ShootingPeaEntity object){
		return ShootingPeaEntityRenderer.LOCATION_BY_VARIANT.get(object.getVariant());
	}

    @Override
    public Identifier getAnimationResource(ShootingPeaEntity object)
    {
        return new Identifier ("pvzmod", "animations/peashot.json");
    }
}
