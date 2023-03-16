package com.kaboomroads.sculkybits.networking.packet;

import com.kaboomroads.sculkybits.block.entity.custom.SculkFeelerBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ClientboundSculkTriggerLengthPacket {
    public BlockPos blockEntityPos;
    public float length;

    public ClientboundSculkTriggerLengthPacket(BlockPos blockEntityPos, float length) {
        this.blockEntityPos = blockEntityPos;
        this.length = length;
    }

    public ClientboundSculkTriggerLengthPacket(FriendlyByteBuf buf) {
        this(buf.readBlockPos(), buf.readFloat());
    }

    public static ClientboundSculkTriggerLengthPacket read(FriendlyByteBuf buf) {
        BlockPos blockEntityPos = buf.readBlockPos();
        float length = buf.readFloat();
        return new ClientboundSculkTriggerLengthPacket(blockEntityPos, length);
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeBlockPos(this.blockEntityPos);
        buf.writeFloat(this.length);
    }

    public boolean handle(Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            if (Minecraft.getInstance().level.getBlockEntity(blockEntityPos) instanceof SculkFeelerBlockEntity blockEntity)
                blockEntity.length = length;
        });
        return true;
    }
}
