package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombiemachines.metallicvehicle.speakervehicle;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class SpeakerVehicleEntityModel extends GeoModel<SpeakerVehicleEntity> {

    @Override
    public Identifier getModelResource(SpeakerVehicleEntity object)
    {
		return new Identifier("pvzmod", "geo/speaker.geo.json");
    }

    @Override
    public Identifier getTextureResource(SpeakerVehicleEntity object)
	{
		return new Identifier("pvzmod", "textures/entity/bass/speaker.png");
    }

    @Override
    public Identifier getAnimationResource(SpeakerVehicleEntity object)
    {
        return new Identifier ("pvzmod", "animations/gravestone.json");
    }
}
