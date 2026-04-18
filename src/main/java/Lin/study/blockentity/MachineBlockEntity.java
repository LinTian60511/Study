package Lin.study.blockentity;

import Lin.study.container.menu.MachineMenu;
import Lin.study.init.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class MachineBlockEntity extends BlockEntity implements MenuProvider {

    // 输入槽索引
    private static final int INPUT_SLOT = 0;
    // 输出槽索引
    private static final int OUTPUT_SLOT = 1;
    // 物品处理
    // size:2 意味着拥有2个物品槽位
    private final ItemStackHandler itemHandler = new ItemStackHandler(2) {

        // 因为 BlockEntity 的数据变了,因此需要进行保存
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }

        // 用于控制能不能放入物品,但是允许玩家拿出来
        // 什么槽位能被输入就返回什么槽位
        // 如果不被填写的槽位就是默认只能被取出,也就是输出
        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return slot == INPUT_SLOT;
        }
    };
    // 调试数据
    private int progress = 0;
    // ContainerData 是一个整数数据接口,只接受整数
    // 如果有其他类型则需要转换成整数
    // 服务端通过 ContainerData 来操作 BlockEntity 内的数据
    protected final ContainerData data = new ContainerData() {
        @Override
        public int get(int pIndex) {
            return switch (pIndex) {
                case 0 -> progress;
                default -> 0;
            };
        }

        @Override
        public void set(int pIndex, int pValue) {
            if (pIndex == 0) progress = pValue;
        }

        @Override
        // 返回可以允许访问的范围(0,return-1)
        public int getCount() {
            return 1;
        }
    };

    public MachineBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.MACHINE_BE.get(), pPos, pBlockState);
    }

    // saveAdditional 是把数据写入存档的接口
    // 在保存世界,区块卸载时候调用
    // 数据需要写入 NBT 里
    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.putInt("Progress", progress);
        // 将内部物品栏序列化后写入 NBT
        pTag.put("inventory", itemHandler.serializeNBT());
    }

    // load 和上面的相反
    // 当方块被重新加载的时候,数据会被读取并且被方块实体加载
    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        progress = pTag.getInt("Progress");
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
    }

    public void tick() {
        progress++;
        setChanged();// 此时方块的数据被修改,告诉游戏保存数据
    }

    public String getDebugMessage() {
        return "Progress:" + progress;
    }

    // 返回界面标题，决定 GUI 显示的标题名称
    @Override
    public Component getDisplayName() {
        return Component.translatable("be.title.machine");
    }

    // 当玩家打开界面时创建 Menu
    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new MachineMenu(id, inventory, this, data);
    }

    //
    public IItemHandler getItemHandler() {
        return itemHandler;
    }

    // 掉落逻辑
    public void drops() {
        // 创建一个临时容器槽位的大小
        // getSlots 方法返回的是
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());

        // 将 itemHandler 中的每个槽位内容复制到临时容器
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        // 将容器的物品掉落到世界
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }
}
