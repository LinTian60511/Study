package Lin.study.init;

import Lin.study.Study;
import Lin.study.container.menu.MachineMenu;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

// 类似 BlockEntityType
public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, Study.MODID);

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }    // region 注册区

    private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> registerMenuType(
            String name, IContainerFactory<T> factory) {
        // 将构造器引用包装成 Forge 能识别的工厂
        return MENUS.register(name, () -> IForgeMenuType.create(factory));
    }

    // 注册 MachineMenu
    public static final RegistryObject<MenuType<MachineMenu>>
            MACHINE_MENU =
            registerMenuType("machine_menu",
                    MachineMenu::new);
    // 注册 StorableEntityMenu
    public static final RegistryObject<MenuType<MachineMenu>>
            STORABLE_ENTITY_MENU =
            registerMenuType("storable_entity_menu",
                    MachineMenu::new);
}
