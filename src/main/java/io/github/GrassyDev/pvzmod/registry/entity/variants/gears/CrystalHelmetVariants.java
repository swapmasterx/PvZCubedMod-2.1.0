package io.github.GrassyDev.pvzmod.registry.entity.variants.gears;

import java.util.Arrays;
import java.util.Comparator;

public enum CrystalHelmetVariants {
	HOLO(0),
	CRYSTALSHOE(1);

	private static final CrystalHelmetVariants[] BY_ID = Arrays.stream(values()).sorted(Comparator.
			comparingInt(CrystalHelmetVariants::getId)).toArray(CrystalHelmetVariants[]::new);
	private final int id;

	CrystalHelmetVariants(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public static CrystalHelmetVariants byId(int id) {
		return BY_ID[id % BY_ID.length];
	}
}
