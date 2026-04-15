package Lin.study.block.machine;

import Lin.study.blockentity.MachineEntity;
import Lin.study.init.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.Nullable;

// HorizontalDirectionalBlock
// 理解为提前设置好的方块模版,允许翻转和镜像等操作
public class machine extends HorizontalDirectionalBlock implements EntityBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    public machine() {
        // super 是调用父类的构造方法
        // .of() 直接返回一个全新的实例化对象
        super(Properties.of());

        // FACING 属性定义
        // 来源于父类 HorizontalDirectionalBlock ,赋值:BlockStateProperties.HORIZONTAL_FACING;
        // HORIZONTAL_FACING 是一个方块状态属性的变量,赋值: DirectionProperty.create("facing", Direction.Plane.HORIZONTAL);
        // create 是一个方向属性的方法
        // Direction.Plane.HORIZONTAL 是一个方向数组,存储了4个方向
        // 也就是说 FACING 属性对象, 此时允许的取值范围只有数组的四个方向
        this.registerDefaultState(
                // any() 返回第一个方向,也就是默认的 FACING=NORTH
                this.getStateDefinition().any()
                        .setValue(FACING, Direction.NORTH)
        );
    }

    // 玩家放置方块的时候,调用该函数
    // 使机器的正面朝向玩家
    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext placeContext) {
        return this.defaultBlockState()
                // placeContext.getHorizontalDirection().getOpposite():
                // 没有玩家的时候默认是 NORTH ，有玩家的则是返回玩家面朝的方向
                // getOpposite() 返回的取反方向
                .setValue(FACING, placeContext.getHorizontalDirection().getOpposite());
    }

    // 向方块状态注册系统注册属性 FACING
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new MachineEntity(blockPos, blockState);
    }

    // getTicker 是用于告诉游戏在每一刻需要执行的逻辑
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState,
                                                                  BlockEntityType<T> pBlockEntityType) {
        // 这是一个三元运算符,用法:
        // 条件? 结果1 : 结果2
        // 完整写法是 if(条件){} else{}
        return pBlockEntityType == ModBlockEntities.MACHINE_BE.get()
                // 以下四个参数来源于 BlockEntityTicker 的 tick
                // 这个 tick 明确要求了需要4个参数,哪怕不用也要接受
                // 然后用匿名函数执行我们方块实体的 tick 方法
                ? (lvl, p, st, be) -> ((MachineEntity) be).tick()
                : null;
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos,
                                 Player player, InteractionHand hand, BlockHitResult hit) {
        if (!level.isClientSide()) {
            BlockEntity entity = level.getBlockEntity(pos);
            if (entity instanceof MachineEntity machine) {
                player.sendSystemMessage(Component.literal(machine.getDebugMessage()));
            }
        }
        return InteractionResult.sidedSuccess(level.isClientSide());
    }
}
