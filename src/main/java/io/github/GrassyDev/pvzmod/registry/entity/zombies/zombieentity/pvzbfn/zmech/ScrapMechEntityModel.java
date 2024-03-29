package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvzbfn.zmech;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class ScrapMechEntityModel extends AnimatedGeoModel<ScrapMechEntity> {

    @Override
    public Identifier getModelResource(ScrapMechEntity object)
    {
		return new Identifier("pvzmod", "geo/scrapmech.geo.json");
    }

    @Override
    public Identifier getTextureResource(ScrapMechEntity object)
    {
		Identifier identifier = new Identifier("pvzmod", "textures/entity/zmech/scrapmech.png");
        return identifier;
    }

    @Override
    public Identifier getAnimationResource(ScrapMechEntity object)
    {
        return new Identifier ("pvzmod", "animations/scrapmech.json");
    }
}
