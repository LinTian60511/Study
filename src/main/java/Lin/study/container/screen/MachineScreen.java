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
    // partialTick 是动画帧率
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        // 设置渲染使用的 Shader (着色器)
        RenderSystem.setShader(GameRenderer::getPositionTexShader);

        // 设置颜色
        RenderSystem.setShaderColor(1F, 1F, 1F, 1F);

        // 绑定纹理
        // pShaderTexture 指定纹理
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

    // 和构造方法不同的是,构造方法用于定义 Screen 的基础参数
    // 而 init 用于负责 GUI 打开时候的布局初始化
    @Override
    protected void init() {
        super.init();

        // inventoryLabelX/Y 控制玩家背包标题的 X/Y 位置
        // titleLabelX/Y 控制GUI标题的 X/Y 位置
        // imageWidth/Heigh 控制 GUI 的时机宽度/高度
        this.inventoryLabelX = 8;
        this.inventoryLabelY = 81;
    }
}
