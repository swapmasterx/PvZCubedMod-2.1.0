package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.lobbed.kernal;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class ShootingKernalEntityModel extends GeoModel<ShootingKernalEntity> {

    @Override
    public Identifier getModelResource(ShootingKernalEntity object)
    {
        return new Identifier("pvzmod", "geo/kernal.geo.json");
    }

    @Override
    public Identifier getTextureResource(ShootingKernalEntity object){
			return new Identifier("pvzmod", "textures/entity/cabbagepult/kernalpult.png");
	}

    @Override
    public Identifier getAnimationResource(ShootingKernalEntity object)
    {
        return new Identifier ("pvzmod", "animations/peashot.json");
    }
}
