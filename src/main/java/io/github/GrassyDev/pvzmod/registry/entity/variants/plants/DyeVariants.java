package io.github.GrassyDev.pvzmod.registry.entity.variants.plants;

import java.util.Arrays;
import java.util.Comparator;

public enum DyeVariants {
	CONTAIN(0),
	APPEASE(1),
	SPEAR(2),
	CONCEAL(3),
	ENFORCE(4),
	ENCHANT(5),
	AILMENT(6),
	BOMBARD(7),
	REINFORCE(8),
	ENLIGHTEN(9),
	WINTER(10),
	PEPPER(11),
	FILAMENT(12),
	ARMA(13);

	private static final DyeVariants[] BY_ID = Arrays.stream(values()).sorted(Comparator.
			comparingInt(DyeVariants::getId)).toArray(DyeVariants[]::new);
	private final int id;

	DyeVariants(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public static DyeVariants byId(int id) {
		return BY_ID[id % BY_ID.length];
	}
}
