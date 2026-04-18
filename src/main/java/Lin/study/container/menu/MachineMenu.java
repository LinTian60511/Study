package Lin.study.container.menu;

import Lin.study.blockentity.MachineBlockEntity;
import Lin.study.init.ModBlocks;
import Lin.study.init.ModMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class MachineMenu extends AbstractContainerMenu {

    // 输入槽
    private static final int INPUT_SLOT = 0;
    private static final int OUTPUT_SLOT = 1;
    // 菜单绑定的方块
    public final MachineBlockEntity blockEntity;
    // 当前菜单所在的世界
    private final Level level;
    // 数据接口
    private final ContainerData data;

    // 客户端构造器
    // 用于接受来自服务端发送的数据
    // id 是游戏自动分配的
    // 其实就是在客户端创建一个临时的Menu
    public MachineMenu(int id, Inventory inv, FriendlyByteBuf buf) {
        this(id, inv,
                // 从 buf 中读取 GUI 数据
                inv.player.level().getBlockEntity(buf.readBlockPos()),
                // 因为 BlockMenu 的 data 属性是服务端专属的
                // 因此我们需要临时创建一个 SimpleContainerData 用于接受数据
                new SimpleContainerData(1));// pSize是这个容器的大小
    }

    // 通用构造器
    // 用于直接访问内存中的 BlockEntity 里的数据
    public MachineMenu(int id, Inventory inv, BlockEntity entity, ContainerData data) {
        // 指定菜单对应的 MenuType
        super(ModMenuTypes.MACHINE_MENU.get(), id);

        // 保存方块实体引用
        // 因为我们传进来的是一个通用的 BlockEntity
        // 因此我们需要转换成对应的 MachineBlockEntity 类型
        // 因为我们已经指定了该属性的类型
        this.blockEntity = (MachineBlockEntity) entity;

        // 保存世界引用
        this.level = inv.player.level();

        // 保存数据同步容器
        this.data = data;

        // 添加玩家背包与快捷栏
        addPlayerInventory(inv, 8, 92);
        addPlayerHotbar(inv, 8, 150);
        addMachineSlots(blockEntity.getItemHandler());

        // 注册数据同步槽
        // 来自于 AbstractContainerMenu
        // 遍历 data 中的每一个数据,并为每一个数据创建一个 DataSlot 用于同步
        // 并且将 DataSlot 注册进Menu的同步系统
        // 用于同步客户端和服务端的数据
        addDataSlots(data);
    }

    // 添加槽位
    // handler 是机器内部库存
    // 坐标是用于定位到 GUI 对应的位置
    private void addMachineSlots(IItemHandler handler) {
        this.addSlot(new SlotItemHandler(handler, INPUT_SLOT, 77, 38));
        this.addSlot(new SlotItemHandler(handler, OUTPUT_SLOT, 142, 38));
    }

    // 玩家背包槽位
    private void addPlayerInventory(Inventory inv, int leftCol, int topRow) {
        for (int row = 0; row < 3; ++row) { // 对应背包的3行
            for (int col = 0; col < 9; ++col) {// 对应背包的9列
                this.addSlot(new Slot(
                        inv,// 玩家背包
                        col + row * 9 + 9,// 背包槽位索引值
                        leftCol + col * 18,// 屏幕的X坐标
                        topRow + row * 18// 屏幕的Y坐标
                ));
            }
        }
    }

    // 玩家快捷栏槽位
    private void addPlayerHotbar(Inventory inv, int leftCol, int topRow) {
        for (int col = 0; col < 9; ++col) {
            this.addSlot(new Slot(
                    inv,
                    col,
                    leftCol + col * 18,
                    topRow
            ));
        }
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
