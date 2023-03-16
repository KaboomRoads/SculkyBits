package com.kaboomroads.sculkybits.gameevent;

import net.minecraft.world.level.gameevent.GameEvent;

public class ModGameEvent {
    public static GameEvent SCULK_ATTACK;

    public static void register() {
        SCULK_ATTACK = register("sculk_attack");
    }

    public static GameEvent register(String name) {
        return register(name, 16);
    }

    public static GameEvent register(String name, int radius) {
        return GameEvent.register(name, radius);
    }
}
