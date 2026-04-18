package Lin.study.client.renderer;

import Lin.study.entity.SimpleEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.PigModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

// MobRender 需要进入两个泛型
// 第一个泛型 T : 必须是 Mob(生物) 类型或者子类
// 第二个泛型 M : 必须是 能渲染 T 类型的实体模型
public class SimpleEntityRenderer extends MobRenderer<SimpleEntity, EntityModel<SimpleEntity>> {

    // 指定实体的纹理贴图
    private static final ResourceLocation TEXTURE =
            ResourceLocation.parse("minecraft:textures/entity/pig/pig.png");

    // 构造参数是固定的
    public SimpleEntityRenderer(EntityRendererProvider.Context context) {
        // 传入一个固定的 context
        // 第二个参数要求填入一个模型 Model ,步骤:
        /* context 中有一个 bakeLayer 方法,可以返回 ModelPart 的参数
           使用 bakeLayer 提取模型布局 Pig 的 ModelPart 部分
           而刚好 PigModel 的构造参数只要求一个 ModelPart 类型
         */
        // 第三个参数是方块的阴影半径
        super(context, new PigModel<>(context.bakeLayer(ModelLayers.PIG)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(SimpleEntity entity) {
        return TEXTURE;
    }
}
