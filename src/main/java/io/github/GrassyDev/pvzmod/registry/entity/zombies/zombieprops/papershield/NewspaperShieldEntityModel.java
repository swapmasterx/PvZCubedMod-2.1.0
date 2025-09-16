package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieprops.papershield;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class NewspaperShieldEntityModel extends GeoModel<NewspaperShieldEntity> {

    @Override
    public Identifier getModelResource(NewspaperShieldEntity object)
    {
        return Identifier.of("pvzmod", "geo/blank.geo.json");
    }

    @Override
    public Identifier getTextureResource(NewspaperShieldEntity object)
    {
		return Identifier.of("pvzmod", "textures/entity/projectiles/peashot.png");
    }

    @Override
    public Identifier getAnimationResource(NewspaperShieldEntity object)
    {
		return Identifier.of ("pvzmod", "animations/peashot.json");
    }
}
