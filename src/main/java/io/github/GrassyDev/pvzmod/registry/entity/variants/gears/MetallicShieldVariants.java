package io.github.GrassyDev.pvzmod.registry.entity.variants.gears;

import java.util.Arrays;
import java.util.Comparator;

public enum MetallicShieldVariants {
	SCREENDOOR(0),
	SERGEANT(1);

	private static final MetallicShieldVariants[] BY_ID = Arrays.stream(values()).sorted(Comparator.
			comparingInt(MetallicShieldVariants::getId)).toArray(MetallicShieldVariants[]::new);
	private final int id;

	MetallicShieldVariants(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public static MetallicShieldVariants byId(int id) {
		return BY_ID[id % BY_ID.length];
	}
}
