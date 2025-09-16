package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.zombies.soundwave;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class SoundwaveModel extends GeoModel<SoundwaveEntity> {

    @Override
    public Identifier getModelResource(SoundwaveEntity object)
    {
        return Identifier.of("pvzmod", "geo/bark.geo.json");
    }

    @Override
    public Identifier getTextureResource(SoundwaveEntity object)
    {
        return Identifier.of("pvzmod", "textures/entity/projectiles/bark.png");
    }

    @Override
    public Identifier getAnimationResource(SoundwaveEntity object)
    {
        return Identifier.of ("pvzmod", "animations/peashot.json");
    }
}
