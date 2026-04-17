package Lin.study.container.screen;

import Lin.study.Study;
import Lin.study.container.menu.MachineMenu;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class MachineScreen extends AbstractContainerScreen<MachineMenu> {

    // GUI 贴图位置
    private static final ResourceLocation GUI =
            new ResourceLocation(Study.MODID, "textures/container/machine.png");

    // Screen 构造器
    public MachineScreen(MachineMenu menu,
                         Inventory playerInventory,
                         Component title) {
        super(menu, playerInventory, title);

        // GUI 的宽度与高度
        // 需要和背景图分辨率保持一致
        this.imageWidth = 176;
        this.imageHeight = 174;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        // 设置渲染使用的 Shader
        RenderSystem.setShader(GameRenderer::getPositionTexShader);

        // 设置颜色
        RenderSystem.setShaderColor(1F, 1F, 1F, 1F);

        // 绑定要回执的纹理
        RenderSystem.setShaderTexture(0, GUI);

        // 计算 GUI 左上角的位置,使其界面居中
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(GUI, x, y, 0, 0, imageWidth, imageHeight);
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mousY, float partialTick) {
        // 绘制界面背景
        renderBackground(graphics);

        // 调用父类渲染 GUI 元素
        super.render(graphics, mouseX, mousY, partialTick);
    }
}
