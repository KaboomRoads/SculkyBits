package com.kaboomroads.sculkybits.block.custom.override;

import com.kaboomroads.sculkybits.gamerule.ModGameRules;
import com.kaboomroads.sculkybits.util.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class SpreadingSculkBlock extends ModSculkBlock {
    public SpreadingSculkBlock(Properties properties) {
        super(properties);
    }

    @Override
    public int costOverride(Level level) {
        return 0;
    }

    @Override
    public boolean overrideCost(Level level) {
        return ModGameRules.RULE_SCULK_SPREADS != null && level.getGameRules().getBoolean(ModGameRules.RULE_SCULK_SPREADS);
    }

    @Override
    public void randomTick(@NotNull BlockState blockState, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource source) {
        if (ModGameRules.RULE_SCULK_SPREADS != null && level.getGameRules().getBoolean(ModGameRules.RULE_SCULK_SPREADS)) {
            float f = ModGameRules.RULE_SCULK_SPREAD_CHANCE != null ? level.getGameRules().getRule(ModGameRules.RULE_SCULK_SPREAD_CHANCE).get() : 0.5f;
            if (level.random.nextFloat() < f / 100f)
                Utils.spreadSculk(pos, level, source, 10, 10);
        }
    }

    @Override
    public boolean isRandomlyTicking(@NotNull BlockState blockState) {
        return true;
    }
}
