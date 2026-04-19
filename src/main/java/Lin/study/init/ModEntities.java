package Lin.study.init;

import Lin.study.Study;
import Lin.study.entity.*;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {

    public static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Study.MODID);

    // 注册实体
    // 简单实体
    public static final RegistryObject<EntityType<SimpleEntity>> SIMPLE_ENTITY =
            ENTITIES.register("simple_entity",
                    // 前面填入实体的构造方法,后面填入实体的类型,比如monster(怪物),creature(生物)
                    // <T>of 用于设置基础属性
                    () -> EntityType.Builder.<SimpleEntity>of(SimpleEntity::new, MobCategory.CREATURE)
                            .sized(0.8f, 0.8f)
                            .build("simple_entity"));

    // 简单可骑乘实体
    public static final RegistryObject<EntityType<SimpleRideableEntity>> SIMPLE_RIDEABLE_ENTITY =
            ENTITIES.register("simple_rideable_entity",
                    () -> EntityType.Builder.<SimpleRideableEntity>of(SimpleRideableEntity::new, MobCategory.CREATURE)
                            .sized(0.8f, 0.8f)
                            .build("simple_rideable_entity"));

    // 简单可移动实体
    public static final RegistryObject<EntityType<SimpleMoveableEntity>> SIMPLE_MOVEABLE_ENTITY =
            ENTITIES.register("simple_moveable_entity",
                    () -> EntityType.Builder.<SimpleMoveableEntity>of(SimpleMoveableEntity::new, MobCategory.CREATURE)
                            .sized(0.8f, 0.8f)
                            .build("simple_moveable_entity"));

    // 简单可发射实体
    public static final RegistryObject<EntityType<SimpleShootableEntity>> SIMPLE_SHOOTABLE_ENTITY =
            ENTITIES.register("simple_shootable_entity",
                    () -> EntityType.Builder.<SimpleShootableEntity>of(SimpleShootableEntity::new, MobCategory.CREATURE)
                            .sized(0.8f, 0.8f)
                            .build("simple_shootable_entity"));

    // 可存储实体
    public static final RegistryObject<EntityType<StorableEntity>> STORABLE_ENTITY =
            ENTITIES.register("storable_entity",
                    () -> EntityType.Builder.of(StorableEntity::new, MobCategory.CREATURE)
                            .sized(0.8f, 0.8f)
                            .build("simple_shootable_entity"));

    public static void register(IEventBus eventBus) {
        ENTITIES.register(eventBus);
    }
}
