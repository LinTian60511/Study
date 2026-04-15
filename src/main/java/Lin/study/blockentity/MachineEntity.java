package Lin.study.blockentity;

import Lin.study.init.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class MachineEntity extends BlockEntity {
    private int progress = 0;

    public MachineEntity(BlockPos pPos, BlockState pBlockState) {
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
        return "Progess:" + progress;
    }
}
