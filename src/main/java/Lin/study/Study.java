package Lin.study;

import Lin.study.init.*;
import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// Mod入口
@SuppressWarnings ("removal")
@Mod (Study.MODID)
public class Study {
    // Mod_ID
    public static final String MODID = "study";
    // 日志入口
    private static final Logger LOGGER = LogUtils.getLogger();

    // 构造函数.类似python的init
    public Study() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // region ModEventBus
        // 方块注册
        ModBlocks.register(modEventBus);
        // 方块实体注册
        ModBlockEntities.register(modEventBus);
        //物品注册
        ModItems.register(modEventBus);
        // 标签页注册
        ModCreativeModeTabs.register(modEventBus);
        // 菜单界面
        ModMenuTypes.register(modEventBus);
        // 实体注册
        ModEntities.register(modEventBus);
        // end region

        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {

    }
}
