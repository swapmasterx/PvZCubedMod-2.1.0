package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzbfn.heroes.nightcap;

import io.github.GrassyDev.pvzmod.PvZCubed;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

	public class NightcapEntityModel extends GeoModel<NightcapEntity> {

    @Override
    public Identifier getModelResource(NightcapEntity object)
    {
        return new Identifier("pvzmod", "geo/nightcap.geo.json");
    }

    @Override
    public Identifier getTextureResource(NightcapEntity object)
    {
		if (object.getShadowPowered()){
			return new  Identifier(PvZCubed.MOD_ID, "textures/entity/nightcap/nightcap_shadow.png");
		}
		else {
			return new Identifier(PvZCubed.MOD_ID, "textures/entity/nightcap/nightcap.png");
		}
    }

    @Override
    public Identifier getAnimationResource(NightcapEntity object)
    {
        return new Identifier ("pvzmod", "animations/nightcap.json");
    }
}
