package Lin.study.blockentity;

import Lin.study.container.menu.MachineMenu;
import Lin.study.init.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class MachineBlockEntity extends BlockEntity implements MenuProvider {
    private int progress = 0;
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
    }

    // load 和上面的相反
    // 当方块被重新加载的时候,数据会被读取并且被方块实体加载
    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        progress = pTag.getInt("Progress");
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
}
