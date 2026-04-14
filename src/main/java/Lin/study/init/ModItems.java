package Lin.study.init;

import Lin.study.Study;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Consumer;
import java.util.function.Function;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Study.MODID);

    // 注册物品
    // 粗材料
    public static final RegistryObject<Item> RAW_MATERIAL =
            registerItem("raw_material");

    // 普通物品注册
    private static RegistryObject<Item> registerItem(String name) {
        return ITEMS.register(name, () -> new Item(new Item.Properties()));
    }

    // 自定义物品注册
    // Function<输入A 输出B>
    private static RegistryObject<Item> registerItem(String name, Function<Item.Properties, Item> factory) {
        return ITEMS.register(name, () -> factory.apply(new Item.Properties()));
    }

    // 仅自定义属性物品注册
    // Consumer 是一个函数接口 <> 用于表明消费对象
    private static RegistryObject<Item> registrySimplePropItem(String name, Consumer<Item.Properties> propertiesModifier) {
        return ITEMS.register(name, () -> {
            // 创建新的物品属性
            Item.Properties pros = new Item.Properties();
            // 使用Consumer 来进行属性修改改,全局生效
            propertiesModifier.accept(pros);
            return new Item(pros);
        });
    }

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
