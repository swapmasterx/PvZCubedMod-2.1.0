package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.zombies.bone;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class BonProjEntityModel extends GeoModel<BoneProjEntity> {

    @Override
    public Identifier getModelResource(BoneProjEntity object)
    {
        return Identifier.of("pvzmod", "geo/boneproj.geo.json");
    }

    @Override
    public Identifier getTextureResource(BoneProjEntity object){
			return Identifier.of("pvzmod", "textures/entity/browncoat/mummy/mummy.png");
	}

    @Override
    public Identifier getAnimationResource(BoneProjEntity object)
    {
        return Identifier.of ("pvzmod", "animations/peashot.json");
    }
}
