package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvzgw.hovergoat;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class HoverGoatEntityModel extends GeoModel<HoverGoatEntity> {

    @Override
    public Identifier getModelResource(HoverGoatEntity object)
    {
		return Identifier.of("pvzmod", "geo/hovergoat3000.geo.json");
    }

    @Override
    public Identifier getTextureResource(HoverGoatEntity object)
	{
		Identifier identifier;
		identifier = Identifier.of("pvzmod", "textures/entity/goat/hovergoat.png");
		return identifier;
    }

    @Override
    public Identifier getAnimationResource(HoverGoatEntity object)
    {
        return Identifier.of ("pvzmod", "animations/hovergoat.json");
    }
}
