package Lin.study.entity;

import Lin.study.network.Networking;
import Lin.study.network.packet.ShootPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.level.Level;

public class SimpleShootableEntity extends SimpleMoveableEntity {

    // 射击冷却
    private int cooldown = 0;

    public SimpleShootableEntity(EntityType<? extends SimpleMoveableEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public void tick() {
        super.tick();

        // 冷却递减
        if (cooldown > 0) {
            cooldown--;
        }

        // 客户端: 检测鼠标左键
        if (this.level().isClientSide && this.getFirstPassenger() == Minecraft.getInstance().player) {
            // 检测左键是否被按下
            if (Minecraft.getInstance().mouseHandler.isLeftPressed() && cooldown == 0) {
                // 发送网络包给服务端
                Networking.INSTANCE.sendToServer(new ShootPacket());
                cooldown = 20;
            }
        }
    }

    public void shoot(Player shooter) {
        if (!this.level().isClientSide && cooldown == 0) {
            // 创建箭
            Arrow arrow = new Arrow(this.level(), shooter);

            double offset = this.getBbWidth() * 0.8;
            double x = this.getX() + this.getLookAngle().x * offset;
            double y = this.getY() + this.getEyeHeight() * 0.6;
            double z = this.getZ() + this.getLookAngle().z * offset;
            arrow.setPos(x, y, z);

            arrow.shoot(this.getLookAngle().x, this.getLookAngle().y, this.getLookAngle().z,
                    1.5f, 1.0f);
            arrow.setBaseDamage(2.0);

            this.level().addFreshEntity(arrow);
            this.level().playSound(null, this.getX(), this.getY(), this.getZ(),
                    SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS,
                    1.0f, 1.0f);

            cooldown = 20;  // 服务端冷却
        }
    }

    // 右键攻击
    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (!this.level().isClientSide && hand == InteractionHand.MAIN_HAND) {
            if (this.getFirstPassenger() != player) {
                player.startRiding(this);
                return InteractionResult.SUCCESS;
            }
        }
        return super.mobInteract(player, hand);
    }
}
