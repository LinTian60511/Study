package Lin.study.init;

import Lin.study.Study;
import Lin.study.block.machine.machine;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

// 方块类
public class ModBlocks {

    // 创建一个方块注册器
    // 第一个参数指定注册类型
    // 第二个填入MODID
    // <Block>是告诉编译器该注册器只能处理 Block 类型
    // BLOCKS 是实例化名
    // ForgeRegistries 是工具类,用于提供注册表
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, Study.MODID);

    // 注册方块
    // RegisterObject 是注册对象
    // BlockBehaviour 是用于定义方块的行为
    // Properties 是上面的一个内部类,类似标签页的 builder
    // 粗原料块
    public static final RegistryObject<Block> RAW_MATERIAL_BLOCK =
            registerBlock("raw_material_block",
                    () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    // 机器
    public static final RegistryObject<Block> MACHINE =
            registerBlock("machine",
                    machine::new);

    //通用方块注册方法
    // <T extends Block> 提前声明该函数内的泛型
    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    // 注册 BlockItem
    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ModItems.ITEMS.register(
                name,
                () -> new BlockItem(block.get(), new Item.Properties())
        );
    }

    // 创建一个静态函数
    // 参数(类型 变量名）
    // 内部通过 上面的 BLOCKS 中的方法实现
    // register 来自 DefferRegister 类型
    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
