package io.github.GrassyDev.pvzmod.registry.entity.environment.maritile;

import io.github.GrassyDev.pvzmod.registry.entity.variants.plants.PeapodCountVariants;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class MariTileModel extends GeoModel<MariTile> {

    @Override
    public Identifier getModelResource(MariTile object)
    {
		if (object.getCount().equals(PeapodCountVariants.ONE)){
			return Identifier.of("pvzmod", "geo/maritile.geo.json");
		}
		else if (object.getCount().equals(PeapodCountVariants.TWO)){
			return Identifier.of("pvzmod", "geo/maritile2.geo.json");
		}
		else {
			return Identifier.of("pvzmod", "geo/maritile3.geo.json");
		}
    }

    @Override
    public Identifier getTextureResource(MariTile object)
    {
		if (object.getShadowPowered()){
			return Identifier.of("pvzmod", "textures/entity/environment/maritile_shadow.png");
		}
		else {
			return Identifier.of("pvzmod", "textures/entity/environment/maritile.png");
		}
    }

    @Override
    public Identifier getAnimationResource(MariTile object)
    {
        return Identifier.of ("pvzmod", "animations/tile.json");
    }
}
