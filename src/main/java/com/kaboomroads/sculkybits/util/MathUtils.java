package com.kaboomroads.sculkybits.util;

import net.minecraft.core.Vec3i;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;

public class MathUtils {
    public static int randomInt(int minInclusive, int maxInclusive) {
        return (int) ((Math.random() * (maxInclusive + 1 - minInclusive)) + minInclusive);
    }

    public static double randomNum(double min, double max) {
        return Math.random() * (max - min) + min;
    }

    public static double loopClamp(double num, double min, double max) {
        if (num > max) num %= max;
        if (num < min) num = max - (Math.abs(num) % max);
        return num;
    }

    public static float loopClamp(float num, float min, float max) {
        if (num > max) num %= max;
        if (num < min) num = max - (Math.abs(num) % max);
        return num;
    }

    public static int loopClamp(int num, int min, int max) {
        if (num > max) num = num % max - 1;
        if (num < min) num = max - (Math.abs(num) % max);
        return num;
    }

    public static float interpolateLinear(float from, float to, float speed) {
        if (Math.abs(from - to) > speed) return from + speed * (to - from);
        return to;
    }

    public static double interpolateLinear(double from, double to, double speed) {
        if (Math.abs(from - to) > speed) return from + speed * (to - from);
        return to;
    }

    public static double randomNegative(double num, RandomSource random) {
        if (random.nextBoolean()) return -num;
        return num;
    }

    public static int randomNegative(int num, RandomSource random) {
        if (random.nextBoolean()) return -num;
        return num;
    }

    public static Vec3 generateRandomVec(double offset) {
        return new Vec3(randomNum(-offset, offset), randomNum(-offset, offset), randomNum(-offset, offset));
    }

    public static Vec3i generateRandomVec(int offset) {
        return new Vec3i(randomInt(-offset, offset), randomInt(-offset, offset), randomInt(-offset, offset));
    }

    public static Vec3i generateRandomNormal(RandomSource random) {
        Vec3i vec3 = generateRandomVec(1);
        if (vec3.getX() == 0 && vec3.getY() == 0 && vec3.getZ() == 0)
            switch (randomInt(0, 2)) {
                case 0 -> vec3 = new Vec3i(randomNegative(1, random), 0, 0);
                case 1 -> vec3 = new Vec3i(0, randomNegative(1, random), 0);
                case 2 -> vec3 = new Vec3i(0, 0, randomNegative(1, random));
            }
        return vec3;
    }

    public static Vec3i generateRandomNormal2d(RandomSource random) {
        Vec3i vec3 = generateRandomVec(1);
        if (vec3.getX() == 0 && vec3.getZ() == 0)
            vec3 = random.nextBoolean()
                    ? new Vec3i(randomNegative(1, random), 0, 0)
                    : new Vec3i(0, 0, randomNegative(1, random));
        return new Vec3i(vec3.getX(), 0, vec3.getZ());
    }
}
