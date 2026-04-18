package Lin.study.entity;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class SimpleRideableEntity extends SimpleEntity {

    public SimpleRideableEntity(EntityType<? extends SimpleEntity> entityType, Level level) {
        super(entityType, level);
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

    // 实体被玩家右键骑乘的那一瞬的逻辑
    // Passenger : 乘客
    @Override
    protected void addPassenger(Entity passenger) {
        super.addPassenger(passenger);
        // 下面写逻辑
    }

    // 玩家从实体上下来那一瞬间的逻辑
    @Override
    protected void removePassenger(Entity passenger) {
        super.removePassenger(passenger);
        // 下面写逻辑
    }
}
