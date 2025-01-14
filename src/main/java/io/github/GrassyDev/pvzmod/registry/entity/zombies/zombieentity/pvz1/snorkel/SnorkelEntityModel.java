package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz1.snorkel;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class SnorkelEntityModel extends GeoModel<SnorkelEntity> {

    @Override
    public Identifier getModelResource(SnorkelEntity object)
    {
        return new Identifier("pvzmod", "geo/snorkel.geo.json");
    }

    @Override
    public Identifier getTextureResource(SnorkelEntity object) {
		Identifier identifier;
		identifier = new Identifier("pvzmod", "textures/entity/snorkel/snorkel.png");
		if (object.armless) {
			identifier = new Identifier("pvzmod", "textures/entity/snorkel/snorkel_dmg1.png");
		}
		return identifier;
    }

    @Override
    public Identifier getAnimationResource(SnorkelEntity object)
    {
        return new Identifier ("pvzmod", "animations/snorkel.json");
    }
}
