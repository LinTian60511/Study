package Lin.study.network.packet;

import Lin.study.entity.SimpleShootableEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ShootPacket {

    public ShootPacket() {

    }

    public static ShootPacket decode(FriendlyByteBuf buf) {
        return new ShootPacket();
    }

    public void encode(FriendlyByteBuf buf) {

    }

    // 处理逻辑
    public void handle(Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> { // 确保在主线程运行
            // 获取发送包的玩家
            ServerPlayer player = context.getSender();
            if (player != null && player.getVehicle() instanceof SimpleShootableEntity vehicle) {
                // 让载具涉及
                vehicle.shoot(player);
            }
        });
        context.setPacketHandled(true);
    }
}
