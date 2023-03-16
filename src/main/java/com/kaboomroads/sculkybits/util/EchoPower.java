package com.kaboomroads.sculkybits.util;

import com.kaboomroads.sculkybits.block.ModBlocks;
import net.minecraft.world.level.block.Block;

import java.util.HashMap;
import java.util.Map;

public class EchoPower {
    public static final Map<Block, Integer> ECHO_POWER = new HashMap<>();

    static {
        ECHO_POWER.put(ModBlocks.SCULK_JAW.get(), 2);
        ECHO_POWER.put(ModBlocks.SCULK_TRAP.get(), 2);
        ECHO_POWER.put(ModBlocks.SCULK_LURKER.get(), 2);
        ECHO_POWER.put(ModBlocks.SCULK_STABBER.get(), 4);
        ECHO_POWER.put(ModBlocks.SCULK_ABSORBER.get(), 3);
        ECHO_POWER.put(ModBlocks.SCULK_LURER.get(), 5);
        ECHO_POWER.put(ModBlocks.SCULK_FEELER.get(), 3);
        ECHO_POWER.put(ModBlocks.SCULK_NEST.get(), 4);
    }
}
