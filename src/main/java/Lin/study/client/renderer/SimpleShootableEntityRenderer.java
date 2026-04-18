package Lin.study.client.renderer;

import Lin.study.entity.SimpleShootableEntity;
import net.minecraft.client.model.PigModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class SimpleShootableEntityRenderer extends MobRenderer<SimpleShootableEntity, PigModel<SimpleShootableEntity>> {

    private static final ResourceLocation TEXTURE =
            new ResourceLocation("minecraft", "textures/entity/pig/pig.png");

    public SimpleShootableEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new PigModel<>(context.bakeLayer(ModelLayers.PIG)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(SimpleShootableEntity entity) {
        return TEXTURE;
    }
}
