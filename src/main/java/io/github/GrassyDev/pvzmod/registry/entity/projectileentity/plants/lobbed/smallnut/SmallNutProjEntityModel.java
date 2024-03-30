package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.lobbed.smallnut;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class SmallNutProjEntityModel extends GeoModel<SmallNutProjEntity> {

    @Override
    public Identifier getModelResource(SmallNutProjEntity object)
    {
        return new Identifier("pvzmod", "geo/smallnut.geo.json");
    }

    @Override
    public Identifier getTextureResource(SmallNutProjEntity object){
			return new Identifier("pvzmod", "textures/entity/small/smallnut.png");
	}

    @Override
    public Identifier getAnimationResource(SmallNutProjEntity object)
    {
        return new Identifier ("pvzmod", "animations/small.json");
    }
}
