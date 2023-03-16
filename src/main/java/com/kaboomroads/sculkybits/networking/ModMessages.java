package com.kaboomroads.sculkybits.networking;

import com.kaboomroads.sculkybits.Sculkybits;
import com.kaboomroads.sculkybits.networking.packet.ClientboundSculkTriggerLengthPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class ModMessages {
    private static SimpleChannel INSTANCE;

    private static int packetId = 0;

    private static int id() {
        return packetId++;
    }

    public static void register() {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(Sculkybits.MOD_ID, "messages"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();
        INSTANCE = net;

        net.messageBuilder(ClientboundSculkTriggerLengthPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ClientboundSculkTriggerLengthPacket::read)
                .encoder(ClientboundSculkTriggerLengthPacket::write)
                .consumerMainThread(ClientboundSculkTriggerLengthPacket::handle)
                .add();
    }

    public static <M> void sendToServer(M message) {
        INSTANCE.sendToServer(message);
    }

    public static <M> void sendToClient(M message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }
}
