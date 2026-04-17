package Lin.study.client;

import Lin.study.Study;
import Lin.study.container.screen.MachineScreen;
import Lin.study.init.ModMenuTypes;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber (modid = Study.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetup {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        registerScreens();
    }

    private static void registerScreens() {
        MenuScreens.register(
                ModMenuTypes.MACHINE_MENU.get(),
                MachineScreen::new
        );
    }
}
