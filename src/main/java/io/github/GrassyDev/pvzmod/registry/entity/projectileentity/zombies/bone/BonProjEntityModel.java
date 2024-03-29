package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.zombies.bone;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BonProjEntityModel extends AnimatedGeoModel<BoneProjEntity> {

    @Override
    public Identifier getModelResource(BoneProjEntity object)
    {
        return new Identifier("pvzmod", "geo/boneproj.geo.json");
    }

    @Override
    public Identifier getTextureResource(BoneProjEntity object){
			return new Identifier("pvzmod", "textures/entity/browncoat/mummy/mummy.png");
	}

    @Override
    public Identifier getAnimationResource(BoneProjEntity object)
    {
        return new Identifier ("pvzmod", "animations/peashot.json");
    }
}
