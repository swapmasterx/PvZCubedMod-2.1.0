package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.pirateseas.coconutcannon;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class CoconutCannonEntityModel extends GeoModel<CoconutCannonEntity> {

    @Override
    public Identifier getModelResource(CoconutCannonEntity object)
    {
        return new Identifier("pvzmod", "geo/coconutcannon.geo.json");
    }

    @Override
    public Identifier getTextureResource(CoconutCannonEntity object)
    {
		Identifier identifier = new Identifier("pvzmod", "textures/entity/coconut/coconutcannon.png");
		if (object.blink){
			identifier = new Identifier("pvzmod", "textures/entity/coconut/coconutcannon_blink.png");
		}
		return identifier;
    }

    @Override
    public Identifier getAnimationResource(CoconutCannonEntity object)
    {
        return new Identifier ("pvzmod", "animations/coconutcannon.json");
    }
}
