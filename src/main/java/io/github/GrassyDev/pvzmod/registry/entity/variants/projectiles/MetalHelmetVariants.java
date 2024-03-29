package io.github.GrassyDev.pvzmod.registry.entity.variants.projectiles;

import java.util.Arrays;
import java.util.Comparator;

public enum MetalHelmetVariants {
	BUCKET(0),
	MUMMYBUCKET(1),
	PEASANTBUCKET(2),
	SCREENDOOR(3),
	FOOTBALL(4),
	BERSERKER(5),
	DEFENSIVEEND(6),
	TRASHCAN(7),
	BLASTRONAUT(8),
	KNIGHT(9),
	MEDALLION(10),
	SERGEANTHELMET(11),
	SOLDIERHELMET(12),
	BASSPROP(13),
	SARGEAMTSHIELD(14),
	SCRAPIMP(15),
	FUTUREBUCKET(16),
	SUMMERBUCKET(17),
	POKERLIDRED(18),
	POKERLIDBLACK(19);

	private static final MetalHelmetVariants[] BY_ID = Arrays.stream(values()).sorted(Comparator.
			comparingInt(MetalHelmetVariants::getId)).toArray(MetalHelmetVariants[]::new);
	private final int id;

	MetalHelmetVariants(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public static MetalHelmetVariants byId(int id) {
		return BY_ID[id % BY_ID.length];
	}
}
