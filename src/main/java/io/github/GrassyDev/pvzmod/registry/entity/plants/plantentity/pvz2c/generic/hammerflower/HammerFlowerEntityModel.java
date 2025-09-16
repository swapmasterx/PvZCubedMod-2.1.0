package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2c.generic.hammerflower;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class HammerFlowerEntityModel extends GeoModel<HammerFlowerEntity> {

    @Override
    public Identifier getModelResource(HammerFlowerEntity object)
    {
        return Identifier.of("pvzmod", "geo/hammerflower.geo.json");
    }

	public Identifier getTextureResource(HammerFlowerEntity object) {
		return Identifier.of("pvzmod", "textures/entity/meteorhammer/hammerflower.png");
	}

    @Override
    public Identifier getAnimationResource(HammerFlowerEntity object)
    {
        return Identifier.of ("pvzmod", "animations/hammerflower.json");
    }
}
