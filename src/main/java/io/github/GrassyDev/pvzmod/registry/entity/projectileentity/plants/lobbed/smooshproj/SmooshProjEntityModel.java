package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.lobbed.smooshproj;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SmooshProjEntityModel extends AnimatedGeoModel<SmooshProjEntity> {

    @Override
    public Identifier getModelResource(SmooshProjEntity object)
    {
        return new Identifier("pvzmod", "geo/smooshproj.geo.json");
    }

    @Override
    public Identifier getTextureResource(SmooshProjEntity object){
			return new Identifier("pvzmod", "textures/entity/smooshroom/smooshroom.png");
	}

    @Override
    public Identifier getAnimationResource(SmooshProjEntity object)
    {
        return new Identifier ("pvzmod", "animations/peashot.json");
    }
}
