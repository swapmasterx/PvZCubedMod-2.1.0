package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvzgw.hovergoat;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class HoverGoatEntityModel extends AnimatedGeoModel<HoverGoatEntity> {

    @Override
    public Identifier getModelResource(HoverGoatEntity object)
    {
		return new Identifier("pvzmod", "geo/hovergoat3000.geo.json");
    }

    @Override
    public Identifier getTextureResource(HoverGoatEntity object)
	{
		Identifier identifier;
		identifier = new Identifier("pvzmod", "textures/entity/goat/hovergoat.png");
		return identifier;
    }

    @Override
    public Identifier getAnimationResource(HoverGoatEntity object)
    {
        return new Identifier ("pvzmod", "animations/hovergoat.json");
    }
}
