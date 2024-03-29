package io.github.GrassyDev.pvzmod.registry.entity.variants.gears;

import java.util.Arrays;
import java.util.Comparator;

public enum PlasticHelmetVariants {
	CONE(0),
	PAWN(1),
	KNIGHT(2),
	TOWER(3),
	BISHOP(4),
	KING(5);

	private static final PlasticHelmetVariants[] BY_ID = Arrays.stream(values()).sorted(Comparator.
			comparingInt(PlasticHelmetVariants::getId)).toArray(PlasticHelmetVariants[]::new);
	private final int id;

	PlasticHelmetVariants(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public static PlasticHelmetVariants byId(int id) {
		return BY_ID[id % BY_ID.length];
	}
}
