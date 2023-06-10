package com.kaboomroads.sculkybits.event;

import com.kaboomroads.sculkybits.Sculkybits;
import com.kaboomroads.sculkybits.block.custom.override.ModSculkBlock;
import com.kaboomroads.sculkybits.damagesource.ModDamageSources;
import com.kaboomroads.sculkybits.effect.ModEffects;
import com.kaboomroads.sculkybits.entity.ModEntityTypes;
import com.kaboomroads.sculkybits.entity.custom.SculkSaprophyte;
import com.kaboomroads.sculkybits.gamerule.ModGameRules;
import com.kaboomroads.sculkybits.util.MathUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.kaboomroads.sculkybits.util.EchoPower.ECHO_POWER;

@Mod.EventBusSubscriber(modid = Sculkybits.MOD_ID)
public class ModEvents {
    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity.level().isClientSide) return;
        if (event.getSource() != ((ModDamageSources) entity.level().damageSources()).sculk() && entity.hasEffect(ModEffects.SCULK.get())) {
            float multiplier = entity.getEffect(ModEffects.SCULK.get()).getAmplifier() * 0.75f + 1;
            event.setAmount(event.getAmount() * multiplier);
        }
    }

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        LivingEntity entity = event.getEntity();
        Level level = entity.level();
        if (level.isClientSide) return;
        if (event.getSource() == ((ModDamageSources) level.damageSources()).sculkAttack() || event.getSource() == ((ModDamageSources) level.damageSources()).sculk()) {
            if (entity.getType() != ModEntityTypes.SCULK_SAPROPHYTE.get()) {
                int amount = ModGameRules.RULE_SCULK_SAPROPHYTE_MOB_COUNT != null ? level.getGameRules().getInt(ModGameRules.RULE_SCULK_SAPROPHYTE_MOB_COUNT) : 1;
                if (entity.getType() == EntityType.PLAYER)
                    amount = ModGameRules.RULE_SCULK_SAPROPHYTE_PLAYER_COUNT != null ? level.getGameRules().getInt(ModGameRules.RULE_SCULK_SAPROPHYTE_PLAYER_COUNT) : 3;
                for (int i = amount; i > 0; i--) {
                    SculkSaprophyte sculkSaprophyte = new SculkSaprophyte(ModEntityTypes.SCULK_SAPROPHYTE.get(), level);
                    sculkSaprophyte.setPos(entity.getPosition(1));
                    sculkSaprophyte.setDeltaMovement(new Vec3(MathUtils.randomNum(-0.1, 0.1), 0, MathUtils.randomNum(-0.1, 0.1)));
                    level.addFreshEntity(sculkSaprophyte);
                }
            }
        }
    }


    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        BlockState blockState = event.getState();
        Block block = blockState.getBlock();
        BlockPos pos = event.getPos();
        LevelAccessor level = event.getLevel();
        ItemStack item = player.getOffhandItem();
        if (level.isClientSide()) return;
        if (level instanceof ServerLevel serverLevel && serverLevel.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS))
            if (item.is(Items.ECHO_SHARD) && ECHO_POWER.containsKey(block) && item.getCount() - ECHO_POWER.get(block) >= 0 && blockState.hasProperty(ModSculkBlock.IS_NATURAL) && blockState.getValue(ModSculkBlock.IS_NATURAL))
                item.shrink(ECHO_POWER.get(block));
            else if (blockState.hasProperty(ModSculkBlock.IS_NATURAL) && blockState.getValue(ModSculkBlock.IS_NATURAL)) {
                event.setCanceled(true);
                level.destroyBlock(pos, false, player);
            }
    }
}
