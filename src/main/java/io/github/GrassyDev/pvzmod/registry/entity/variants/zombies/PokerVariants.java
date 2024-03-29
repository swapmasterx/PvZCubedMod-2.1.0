package io.github.GrassyDev.pvzmod.registry.entity.variants.zombies;

import java.util.Arrays;
import java.util.Comparator;

public enum PokerVariants {
	HEART(0),
	DIAMOND(1),
	CLUB(2),
	SPADE(3);

	private static final PokerVariants[] BY_ID = Arrays.stream(values()).sorted(Comparator.
			comparingInt(PokerVariants::getId)).toArray(PokerVariants[]::new);
	private final int id;

	PokerVariants(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public static PokerVariants byId(int id) {
		return BY_ID[id % BY_ID.length];
	}
}
