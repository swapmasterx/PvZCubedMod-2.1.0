package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.lostcity.endurian;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class EndurianEntityModel extends GeoModel<EndurianEntity> {

    @Override
    public Identifier getModelResource(EndurianEntity object)
    {
        return new Identifier("pvzmod", "geo/endurian.geo.json");
    }

    @Override
    public Identifier getTextureResource(EndurianEntity object)
    {
		return EndurianEntity.LOCATION_BY_VARIANT.get(object.getCrack());
    }

    @Override
    public Identifier getAnimationResource(EndurianEntity object)
    {
        return new Identifier ("pvzmod", "animations/wallnut.json");
    }
}
