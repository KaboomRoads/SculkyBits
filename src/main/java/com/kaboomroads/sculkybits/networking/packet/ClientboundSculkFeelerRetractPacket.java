package com.kaboomroads.sculkybits.networking.packet;

import com.kaboomroads.sculkybits.block.entity.custom.SculkFeelerBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ClientboundSculkFeelerRetractPacket {
    public BlockPos blockEntityPos;

    public ClientboundSculkFeelerRetractPacket(BlockPos blockEntityPos) {
        this.blockEntityPos = blockEntityPos;
    }

    public ClientboundSculkFeelerRetractPacket(FriendlyByteBuf buf) {
        this(buf.readBlockPos());
    }

    public static ClientboundSculkFeelerRetractPacket read(FriendlyByteBuf buf) {
        BlockPos blockEntityPos = buf.readBlockPos();
        return new ClientboundSculkFeelerRetractPacket(blockEntityPos);
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeBlockPos(this.blockEntityPos);
    }

    public boolean handle(Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            if (Minecraft.getInstance().level.getBlockEntity(blockEntityPos) instanceof SculkFeelerBlockEntity blockEntity)
                blockEntity.extending = false;
        });
        return true;
    }
}
