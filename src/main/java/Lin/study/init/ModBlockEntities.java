package Lin.study.init;

import Lin.study.Study;
import Lin.study.blockentity.MachineBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    // 方块实体类
    // 用于存储方块的数据记录状态等

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Study.MODID);

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITY.register(eventBus);
    }

    public static final RegistryObject<BlockEntityType<MachineBlockEntity>>
            MACHINE_BE =
            BLOCK_ENTITY.register("machine_be", () ->
                    BlockEntityType.Builder.of(
                            MachineBlockEntity::new,// 方法引用
                            ModBlocks.MACHINE.get()// 可以接受多个参数
                    ).build(null)
            );
}
