package Lin.study.network;

import Lin.study.Study;
import Lin.study.network.packet.ShootPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class Networking {

    public static SimpleChannel INSTANCE;
    private static int packetId = 0;

    public static void register() {
        // 创建网络通道
        INSTANCE = NetworkRegistry.newSimpleChannel(
                new ResourceLocation(Study.MODID, "main"),
                () -> "1.0",
                (version) -> true,
                (version) -> true
        );

        // 注册 ShootPacket
        INSTANCE.messageBuilder(ShootPacket.class, packetId++)
                .encoder(ShootPacket::encode)
                .decoder(ShootPacket::decode)
                .consumerNetworkThread(ShootPacket::handle)
                .add();
    }
}
