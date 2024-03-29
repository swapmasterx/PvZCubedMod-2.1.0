package io.github.GrassyDev.pvzmod.registry.entity.variants.zombies;

import java.util.Arrays;
import java.util.Comparator;

public enum ZomblobVariants {
	MEDIUM(0),
	MEDIUMHYPNO(0),
	BIG(1),
	BIGHYPNO(2),
	SMALL(1),
	SMALLHYPNO(2);

	private static final ZomblobVariants[] BY_ID = Arrays.stream(values()).sorted(Comparator.
			comparingInt(ZomblobVariants::getId)).toArray(ZomblobVariants[]::new);
	private final int id;

	ZomblobVariants(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public static ZomblobVariants byId(int id) {
		return BY_ID[id % BY_ID.length];
	}
}
