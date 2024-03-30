package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2.imp.announcer;

import io.github.GrassyDev.pvzmod.registry.entity.variants.zombies.ZombieKingVariants;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class AnnouncerImpEntityModel extends GeoModel<AnnouncerImpEntity> {

    @Override
    public Identifier getModelResource(AnnouncerImpEntity object)
    {
		if (object.getColor().equals(ZombieKingVariants.RED)) {
			return new Identifier("pvzmod", "geo/redannouncerimp.geo.json");
		}
		else if (object.getColor().equals(ZombieKingVariants.BLACK)) {
			return new Identifier("pvzmod", "geo/blackannouncerimp.geo.json");
		}
		else {
			return new Identifier("pvzmod", "geo/announcerimp.geo.json");
		}
    }

    @Override
    public Identifier getTextureResource(AnnouncerImpEntity object)
    {
		if (object.getColor().equals(ZombieKingVariants.RED)) {
			return new Identifier("pvzmod", "textures/entity/imp/announcerimp_red.png");
		}
		else if (object.getColor().equals(ZombieKingVariants.BLACK)) {
			return new Identifier("pvzmod", "textures/entity/imp/announcerimp_black.png");
		}
		else {
			return new Identifier("pvzmod", "textures/entity/imp/announcerimp.png");
		}
    }

    @Override
    public Identifier getAnimationResource(AnnouncerImpEntity object)
    {
        return new Identifier ("pvzmod", "animations/imp.json");
    }
}
