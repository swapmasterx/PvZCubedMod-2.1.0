package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.upgrades.wintermelon;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class WinterMelonEntityModel extends GeoModel<WinterMelonEntity> {

    @Override
    public Identifier getModelResource(WinterMelonEntity object)
    {
        return Identifier.of("pvzmod", "geo/wintermelon.geo.json");
    }

    @Override
    public Identifier getTextureResource(WinterMelonEntity object)
    {
        return Identifier.of("pvzmod", "textures/entity/cabbagepult/wintermelon.png");
    }

    @Override
    public Identifier getAnimationResource(WinterMelonEntity object)
    {
        return Identifier.of ("pvzmod", "animations/cabbagepult.json");
    }
}
