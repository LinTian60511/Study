package Lin.study.container.menu;

import Lin.study.blockentity.MachineBlockEntity;
import Lin.study.init.ModBlocks;
import Lin.study.init.ModMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public class MachineMenu extends AbstractContainerMenu {

    // 菜单绑定的方块
    public final MachineBlockEntity blockEntity;

    // 当前菜单所在的世界
    private final Level level;

    // 用于同步数据的容器
    private final ContainerData data;

    // 客户端构造器
    public MachineMenu(int id, Inventory inv, FriendlyByteBuf buf) {
        this(id, inv,
                inv.player.level().getBlockEntity(buf.readBlockPos()),
                new SimpleContainerData(1));
    }

    // 服务端构造器
    public MachineMenu(int id, Inventory inv, BlockEntity entity, ContainerData data) {
        // 指定菜单对应的 MenuType
        super(ModMenuTypes.MACHINE_MENU.get(), id);

        // 保存方块实体引用
        this.blockEntity = (MachineBlockEntity) entity;

        // 保存世界引用
        this.level = inv.player.level();

        // 保存数据同步容器
        this.data = data;

        // 注册数据同步操
        addDataSlots(data);
    }

    // shift快速点击逻辑
    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        return null;
    }

    // 检查玩家是否还能使用该界面
    @Override
    public boolean stillValid(Player pPlayer) {
        return stillValid(
                ContainerLevelAccess.create(level, blockEntity.getBlockPos()),
                pPlayer,
                ModBlocks.MACHINE.get()
        );
    }

    // 获取方块实体
    public MachineBlockEntity getBlockEntity() {
        return this.blockEntity;
    }
}
