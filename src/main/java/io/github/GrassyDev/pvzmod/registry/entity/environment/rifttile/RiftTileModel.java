package io.github.GrassyDev.pvzmod.registry.entity.environment.rifttile;

import io.github.GrassyDev.pvzmod.registry.entity.variants.graves.RiftVariants;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class RiftTileModel extends GeoModel<RiftTile> {

    @Override
    public Identifier getModelResource(RiftTile object)
    {
		if (object.getVariant().equals(RiftVariants.GARGOLITH)){
			return Identifier.of("pvzmod", "geo/rifttile2.geo.json");
		}
		else {
			return Identifier.of("pvzmod", "geo/rifttile.geo.json");
		}
    }

    @Override
    public Identifier getTextureResource(RiftTile object)
    {
        return Identifier.of("pvzmod", "textures/entity/environment/tiles.png");
    }

    @Override
    public Identifier getAnimationResource(RiftTile object)
    {
        return Identifier.of ("pvzmod", "animations/tile.json");
    }
}
