package io.github.GrassyDev.pvzmod.registry.entity.environment.maritile;

import io.github.GrassyDev.pvzmod.registry.entity.variants.plants.PeapodCountVariants;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class MariTileModel extends AnimatedGeoModel<MariTile> {

    @Override
    public Identifier getModelResource(MariTile object)
    {
		if (object.getCount().equals(PeapodCountVariants.ONE)){
			return new Identifier("pvzmod", "geo/maritile.geo.json");
		}
		else if (object.getCount().equals(PeapodCountVariants.TWO)){
			return new Identifier("pvzmod", "geo/maritile2.geo.json");
		}
		else {
			return new Identifier("pvzmod", "geo/maritile3.geo.json");
		}
    }

    @Override
    public Identifier getTextureResource(MariTile object)
    {
		if (object.getShadowPowered()){
			return new Identifier("pvzmod", "textures/entity/environment/maritile_shadow.png");
		}
		else {
			return new Identifier("pvzmod", "textures/entity/environment/maritile.png");
		}
    }

    @Override
    public Identifier getAnimationResource(MariTile object)
    {
        return new Identifier ("pvzmod", "animations/tile.json");
    }
}
