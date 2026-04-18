package Lin.study.client.renderer;

import Lin.study.entity.SimpleRideableEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.PigModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class SimpleRideableEntityRenderer extends MobRenderer<SimpleRideableEntity, EntityModel<SimpleRideableEntity>> {

    // 指定实体的纹理贴图
    private static final ResourceLocation TEXTURE =
            ResourceLocation.parse("minecraft:textures/entity/pig/pig.png");

    // 和父类构造
    public SimpleRideableEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new PigModel<>(context.bakeLayer(ModelLayers.PIG)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(SimpleRideableEntity entity) {
        return TEXTURE;
    }
}
