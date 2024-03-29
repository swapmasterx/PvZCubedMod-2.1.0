package io.github.GrassyDev.pvzmod.registry.entity.variants.zombies;

import java.util.Arrays;
import java.util.Comparator;

public enum ImpVariants {
	DEFAULT(0),
	DEFAULTHYPNO(1),
	SUPERFAN(2),
	SUPERFANHYPNO(3),
	NEWYEAR(4),
	NEWYEARHYPNO(5),
	IMPDRAGON(6),
	IMPDRAGONHYPNO(7),
	THROWER(8),
	THROWERHYPNO(9),
	SCRAP(10),
	SCRAPHYPNO(11),
	BASSIMP(12),
	BASSIMPHYPNO(13),
	CINDERELLA(14),
	CINDERELLAHYPNO(15),
	MUMMY(16),
	MUMMYHYPNO(17);

	private static final ImpVariants[] BY_ID = Arrays.stream(values()).sorted(Comparator.
			comparingInt(ImpVariants::getId)).toArray(ImpVariants[]::new);
	private final int id;

	ImpVariants(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public static ImpVariants byId(int id) {
		return BY_ID[id % BY_ID.length];
	}
}
