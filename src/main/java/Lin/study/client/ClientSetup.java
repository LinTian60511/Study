package Lin.study.client;

import Lin.study.Study;
import Lin.study.client.renderer.SimpleEntityRenderer;
import Lin.study.client.renderer.SimpleMoveableEntityRenderer;
import Lin.study.client.renderer.SimpleRideableEntityRenderer;
import Lin.study.client.renderer.SimpleShootableEntityRenderer;
import Lin.study.container.screen.MachineScreen;
import Lin.study.init.ModEntities;
import Lin.study.init.ModMenuTypes;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

// value = Dist.Client 的意思是只在客户端加载 ClientSetup 这个类
@Mod.EventBusSubscriber (modid = Study.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetup {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        registerScreens();
        registerEntityRenderers();
    }

    // 用于绑定 Menu 和 Screen
    private static void registerScreens() {
        MenuScreens.register(
                ModMenuTypes.MACHINE_MENU.get(),
                MachineScreen::new
        );
    }

    // 注册实体渲染器
    private static void registerEntityRenderers() {
        // SimpleEntity
        EntityRenderers.register(ModEntities.SIMPLE_ENTITY.get(), SimpleEntityRenderer::new);
        // SimpleRideableEntity
        EntityRenderers.register(ModEntities.SIMPLE_RIDEABLE_ENTITY.get(), SimpleRideableEntityRenderer::new);
        // SimpleMoveableEntity
        EntityRenderers.register(ModEntities.SIMPLE_MOVEABLE_ENTITY.get(), SimpleMoveableEntityRenderer::new);
        // SimpleShootableEntity
        EntityRenderers.register(ModEntities.SIMPLE_SHOOTABLE_ENTITY.get(), SimpleShootableEntityRenderer::new);
    }
}
