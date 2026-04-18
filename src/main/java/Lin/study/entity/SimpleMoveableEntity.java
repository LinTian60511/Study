package Lin.study.entity;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class SimpleMoveableEntity extends SimpleRideableEntity {

    // 移动速度
    private static final double MOVE_SPEED = 0.3D;

    // 用于平滑转向
    private float clientYawDelta;

    // 记录上一个乘客
    private Player lastPlayer;

    public SimpleMoveableEntity(EntityType<? extends SimpleRideableEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public void tick() {
        super.tick();

        // 获取第一个乘客（骑乘者）
        if (this.getFirstPassenger() instanceof Player player) {
            lastPlayer = player;
            // 处理玩家输入，控制移动
            handleInput(player);
        }
    }

    private void handleInput(Player player) {
        // 获取玩家的移动输入（WASD）
        float xxa = player.xxa;   // 左右：A/D 或 鼠标横向移动
        float zza = player.zza;   // 前后：W/S

        // 没有输入则停止移动
        if (xxa == 0 && zza == 0) {
            // 可以添加摩擦力，让实体慢慢停下
            return;
        }

        // 朝向
        float yaw = player.getYRot();

        // 计算移动向量
        Vec3 movement = calculateMovement(xxa, zza, yaw);

        // 应用移动
        this.setDeltaMovement(movement);

        // 让实体面朝移动方向（可选，为了视觉效果）
        if (zza != 0) {
            float targetYaw = yaw;
            if (zza < 0) {
                // 后退时，实体面朝反方向？或者保持玩家朝向
                // 这里简单处理：后退时实体也面朝玩家看的方向
                targetYaw = yaw;
            }
            this.setYRot(targetYaw);
            this.yBodyRot = targetYaw;
            this.yHeadRot = targetYaw;
        }
    }

    // 计算移动向量
    private Vec3 calculateMovement(float xxa, float zza, float yaw) {
        double rad = Math.toRadians(yaw);

        // 前方向量（玩家看的方向）
        double forwardX = -Math.sin(rad);
        double forwardZ = Math.cos(rad);

        // 右方向量（垂直于前方向量）
        double rightX = Math.cos(rad);
        double rightZ = Math.sin(rad);

        // 根据输入组合移动向量
        double moveX = forwardX * zza + rightX * xxa;
        double moveZ = forwardZ * zza + rightZ * xxa;

        // 归一化并应用速度
        double length = Math.sqrt(moveX * moveX + moveZ * moveZ);
        if (length > 0.01) {
            moveX = moveX / length * MOVE_SPEED;
            moveZ = moveZ / length * MOVE_SPEED;
        } else {
            moveX = 0;
            moveZ = 0;
        }

        // 保持原有的 Y 轴速度（重力）
        return new Vec3(moveX, this.getDeltaMovement().y, moveZ);
    }

    // 玩家右键时候调用
    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (!this.level().isClientSide) {
            // 让玩家坐上实体
            player.startRiding(this);
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public void travel(Vec3 travelVector) {
        // 如果有骑乘的玩家，使用自定义移动
        if (this.getFirstPassenger() instanceof Player) {
            // 不调用 super.travel，而是使用我们自己的移动逻辑
            // 但为了重力等效果，可以只处理水平移动
            double motionY = this.getDeltaMovement().y;
            super.travel(new Vec3(0, motionY, 0));
        } else {
            super.travel(travelVector);
        }
    }
}
