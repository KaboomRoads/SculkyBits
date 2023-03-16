package com.kaboomroads.sculkybits.gamerule;

import com.kaboomroads.sculkybits.Sculkybits;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.logging.LogUtils;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.GameRules;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.function.BiConsumer;

public class ModGameRules {
    public static GameRules.Key<GameRules.BooleanValue> RULE_SCULK_SPREADS = register("sculkSpreads", GameRules.Category.UPDATES, GameRules.BooleanValue.create(false));
    public static GameRules.Key<FloatValue> RULE_SCULK_SPREAD_CHANCE = register("sculkSpreadChance", GameRules.Category.UPDATES, FloatValue.create(50f));
    public static GameRules.Key<GameRules.IntegerValue> RULE_SCULK_SAPROPHYTE_MOB_COUNT = register("mobSculkSaprophyteCount", GameRules.Category.SPAWNING, GameRules.IntegerValue.create(1));
    public static GameRules.Key<GameRules.IntegerValue> RULE_SCULK_SAPROPHYTE_PLAYER_COUNT = register("playerSculkSaprophyteCount", GameRules.Category.SPAWNING, GameRules.IntegerValue.create(3));
    public static GameRules.Key<FloatValue> RULE_SCULK_LURER_RANGE = register("sculkLurerRange", GameRules.Category.MISC, FloatValue.create(5));
    public static GameRules.Key<FloatValue> RULE_SCULK_NEST_RANGE = register("sculkNestRange", GameRules.Category.SPAWNING, FloatValue.create(10));
    public static GameRules.Key<GameRules.IntegerValue> RULE_SCULK_NEST_LIMIT = register("sculkNestLimit", GameRules.Category.SPAWNING, GameRules.IntegerValue.create(3));

    public static <T extends GameRules.Value<T>> GameRules.Key<T> register(String name, GameRules.Category category, GameRules.Type<T> type) {
        return GameRules.register(name, category, type);
    }

    public static void register() {
        System.out.println("Registering " + Sculkybits.MOD_ID + " game rules.");
    }

    public static class FloatValue extends GameRules.Value<FloatValue> {
        private float value;

        private static GameRules.Type<FloatValue> create(float p_46295_, BiConsumer<MinecraftServer, FloatValue> p_46296_) {
            return new GameRules.Type<>(FloatArgumentType::floatArg, (p_46293_) -> new FloatValue(p_46293_, p_46295_), p_46296_, GameRules.GameRuleTypeVisitor::visit);
        }

        public static GameRules.Type<FloatValue> create(float p_46313_) {
            return create(p_46313_, (p_46309_, p_46310_) -> {
            });
        }

        public FloatValue(GameRules.Type<FloatValue> p_46286_, float p_46287_) {
            super(p_46286_);
            this.value = p_46287_;
        }

        protected void updateFromArgument(@NotNull CommandContext<CommandSourceStack> p_46304_, @NotNull String p_46305_) {
            this.value = FloatArgumentType.getFloat(p_46304_, p_46305_);
        }

        public float get() {
            return this.value;
        }

        public void set(float p_151490_, @Nullable MinecraftServer p_151491_) {
            this.value = p_151490_;
            this.onChanged(p_151491_);
        }

        @NotNull
        public String serialize() {
            return Float.toString(this.value);
        }

        protected void deserialize(@NotNull String p_46307_) {
            this.value = safeParse(p_46307_);
        }

        public boolean tryDeserialize(String p_46315_) {
            try {
                this.value = Float.parseFloat(p_46315_);
                return true;
            } catch (NumberFormatException numberformatexception) {
                return false;
            }
        }

        private static float safeParse(String p_46318_) {
            if (!p_46318_.isEmpty()) {
                try {
                    return Float.parseFloat(p_46318_);
                } catch (NumberFormatException numberformatexception) {
                    LogUtils.getLogger().warn("Failed to parse float {}", p_46318_);
                }
            }
            return 0;
        }

        public int getCommandResult() {
            return (int) this.value;
        }

        @NotNull
        protected FloatValue getSelf() {
            return this;
        }

        @NotNull
        protected FloatValue copy() {
            return new FloatValue(this.type, this.value);
        }

        public void setFrom(FloatValue p_46298_, @Nullable MinecraftServer p_46299_) {
            this.value = p_46298_.value;
            this.onChanged(p_46299_);
        }
    }
}
