package Lin.study.init;

import Lin.study.Study;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Study.MODID);

    //注册模组的创造模式标签页
    // "study" 是该标签页的注册名,会作为内部ID使用
    public static final RegistryObject<CreativeModeTab> STUDY =
            CREATIVE_MODE_TABS.register("study",
                    () -> CreativeModeTab.builder()
                            //该方法是设置标签页图标
                            .icon(() -> new ItemStack(Items.STONE))
                            // 设置标签的显示名称
                            // 这样写可以直接方便本地化
                            .title(Component.translatable("tab.study"))
                            // 定义在该标签页中显示的内容
                            // output.accept() 用于在标签页中添加物品
                            //
                            .displayItems(((itemDisplayParameters, output) -> {
                                output.accept(ModItems.RAW_MATERIAL.get());
                                output.accept(ModBlocks.RAW_MATERIAL_BLOCK.get());
                                output.accept(ModBlocks.MACHINE.get());
                            }))
                            //构建示例
                            .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}