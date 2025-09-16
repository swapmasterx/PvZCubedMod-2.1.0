package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvzbfn.zmech;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class ScrapMechEntityModel extends GeoModel<ScrapMechEntity> {

    @Override
    public Identifier getModelResource(ScrapMechEntity object)
    {
		return Identifier.of("pvzmod", "geo/scrapmech.geo.json");
    }

    @Override
    public Identifier getTextureResource(ScrapMechEntity object)
    {
		Identifier identifier = Identifier.of("pvzmod", "textures/entity/zmech/scrapmech.png");
        return identifier;
    }

    @Override
    public Identifier getAnimationResource(ScrapMechEntity object)
    {
        return Identifier.of ("pvzmod", "animations/scrapmech.json");
    }
}
