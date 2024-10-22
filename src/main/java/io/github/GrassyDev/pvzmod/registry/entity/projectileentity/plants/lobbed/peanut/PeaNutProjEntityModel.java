package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.lobbed.peanut;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class PeaNutProjEntityModel extends GeoModel<PeaNutProjEntity> {

    @Override
    public Identifier getModelResource(PeaNutProjEntity object)
    {
        return new Identifier("pvzmod", "geo/peanut.geo.json");
    }

    @Override
    public Identifier getTextureResource(PeaNutProjEntity object){
			return new Identifier("pvzmod", "textures/entity/wallnut/peanut_dmg2.png");
	}

    @Override
    public Identifier getAnimationResource(PeaNutProjEntity object)
    {
        return new Identifier ("pvzmod", "animations/peanut.json");
    }
}
