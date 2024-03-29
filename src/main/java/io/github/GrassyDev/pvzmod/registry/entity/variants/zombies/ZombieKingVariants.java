package io.github.GrassyDev.pvzmod.registry.entity.variants.zombies;

import java.util.Arrays;
import java.util.Comparator;

public enum ZombieKingVariants {
	DEFAULT(0),
	RED(1),
	BLACK(2);

	private static final ZombieKingVariants[] BY_ID = Arrays.stream(values()).sorted(Comparator.
			comparingInt(ZombieKingVariants::getId)).toArray(ZombieKingVariants[]::new);
	private final int id;

	ZombieKingVariants(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public static ZombieKingVariants byId(int id) {
		return BY_ID[id % BY_ID.length];
	}
}
