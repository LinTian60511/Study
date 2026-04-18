package Lin.study.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;

public class SimpleEntity extends Mob {
    public SimpleEntity(EntityType<? extends Mob> entityType, Level level) {
        super(entityType, level);
    }

    // 设置实体的基础属性
    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20)
                .add(Attributes.MOVEMENT_SPEED, 0);
    }

    // 实体每 tick 执行的逻辑
    @Override
    public void tick() {
        super.tick();
        // 以下填入自写逻辑
    }
}
