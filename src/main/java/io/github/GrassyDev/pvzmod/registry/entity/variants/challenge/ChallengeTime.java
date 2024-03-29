package io.github.GrassyDev.pvzmod.registry.entity.variants.challenge;

import java.util.Arrays;
import java.util.Comparator;

public enum ChallengeTime {
	DAY(0),
	FULLMOON(1),
	HALFMOON(2),
	NEWMOON(3),
	BOMB(4),
	DROUGHT(5);

	private static final ChallengeTime[] BY_ID = Arrays.stream(values()).sorted(Comparator.
			comparingInt(ChallengeTime::getId)).toArray(ChallengeTime[]::new);
	private final int id;

	ChallengeTime(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public static ChallengeTime byId(int id) {
		return BY_ID[id % BY_ID.length];
	}
}
