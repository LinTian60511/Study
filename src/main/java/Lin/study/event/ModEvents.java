package Lin.study.event;

import Lin.study.Study;
import Lin.study.entity.SimpleEntity;
import Lin.study.entity.SimpleMoveableEntity;
import Lin.study.init.ModEntities;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber (modid = Study.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEvents {
    @SubscribeEvent
    public static void onEntityAttributeCreation(EntityAttributeCreationEvent event) {
        // 将实体类和属性绑定在一起
        // 参数讲解:
        /*
           第一个填入指定的实体对象
           第二个填入指定的实体属性
           实体对象 和 实体属性 是相互独立的,因此需要进行绑定将 实体属性 赋予给 实体对象
        */
        // SimpleEntity
        event.put(ModEntities.SIMPLE_ENTITY.get(), SimpleEntity.createAttributes().build());
        // SimpleRideableEntity
        event.put(ModEntities.SIMPLE_RIDEABLE_ENTITY.get(), SimpleEntity.createAttributes().build());
        // SimpleMoveableEntity
        event.put(ModEntities.SIMPLE_MOVEABLE_ENTITY.get(), SimpleMoveableEntity.createAttributes().build());
        // SimpleShootableEntity
        event.put(ModEntities.SIMPLE_SHOOTABLE_ENTITY.get(), SimpleEntity.createAttributes().build());
    }
}
