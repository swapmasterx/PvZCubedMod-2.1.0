package io.github.GrassyDev.pvzmod.registry.entity.variants.projectiles;

import java.util.Arrays;
import java.util.Comparator;

public enum ShadowSporeVariants {
	DEFAULT(0),
	SHADOW(1);

	private static final ShadowSporeVariants[] BY_ID = Arrays.stream(values()).sorted(Comparator.
			comparingInt(ShadowSporeVariants::getId)).toArray(ShadowSporeVariants[]::new);
	private final int id;

	ShadowSporeVariants(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public static ShadowSporeVariants byId(int id) {
		return BY_ID[id % BY_ID.length];
	}
}
