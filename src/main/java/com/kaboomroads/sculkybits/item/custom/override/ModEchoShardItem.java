package com.kaboomroads.sculkybits.item.custom.override;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static com.kaboomroads.sculkybits.util.EchoPower.ECHO_POWER;

public class ModEchoShardItem extends Item {
    public ModEchoShardItem(Properties properties) {
        super(properties);
    }

    @NotNull
    @Override
    public InteractionResult useOn(@NotNull UseOnContext context) {
        Level level = context.getLevel();
        if (level.isClientSide)
            return InteractionResult.PASS;
        Block block = level.getBlockState(context.getClickedPos()).getBlock();
        Player player = context.getPlayer();
        if (player != null && ECHO_POWER.containsKey(block)) {
            context.getPlayer().displayClientMessage(Component.literal(String.valueOf(ECHO_POWER.get(block))), true);
        }
        return super.useOn(context);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> components, @NotNull TooltipFlag tooltipFlag) {
        if (Screen.hasShiftDown()) {
            components.add(Component.literal("Hold this in your offhand to pick").withStyle(ChatFormatting.YELLOW));
            components.add(Component.literal("up the most powerful Sculk blocks.").withStyle(ChatFormatting.YELLOW));
            components.add(Component.literal("Right click on a block to find out").withStyle(ChatFormatting.YELLOW));
            components.add(Component.literal("how many you need!").withStyle(ChatFormatting.YELLOW));
            if (Screen.hasAltDown()) {
                components.add(Component.literal("The number of Echo Shards needed").withStyle(ChatFormatting.AQUA));
                components.add(Component.literal("depends on the power of the block!").withStyle(ChatFormatting.AQUA));
            } else
                components.add(Component.literal("Hold ALT for even more info").withStyle(ChatFormatting.AQUA));
        } else
            components.add(Component.literal("Hold SHIFT for more info").withStyle(ChatFormatting.YELLOW));

        super.appendHoverText(itemStack, level, components, tooltipFlag);
    }
}
