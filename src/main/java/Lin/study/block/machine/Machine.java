package Lin.study.block.machine;

import Lin.study.blockentity.MachineBlockEntity;
import Lin.study.init.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
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
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nullable;

// HorizontalDirectionalBlock
// 理解为提前设置好的方块模版,允许翻转和镜像等操作
public class Machine extends HorizontalDirectionalBlock implements EntityBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    public Machine() {
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
        return new MachineBlockEntity(blockPos, blockState);
    }

    // getTicker 是用于告诉游戏在每一刻需要执行的逻辑
    // getTicker 是一个通用方法
    // 当方块被加载的时候,游戏会调用方块的 getTicker ,该函数的作用是提交该方块的每tick逻辑
    // 我们指定了函数要求返回 BlockEntityTicker ,这是一个接口,内部是一个抽象方法
    // 函数的四个参数来源于 BlockEntityTicker 的 tick 函数,此函数要求四个参数
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState,
                                                                  BlockEntityType<T> pBlockEntityType) {
        // 这是一个三元运算符,用法:
        // 条件? 结果1 : 结果2
        // 完整写法是 if(条件){} else{}
        return pBlockEntityType == ModBlockEntities.MACHINE_BE.get()
                // 因为 BlockEntityTicker 内部只有一个抽象方法,因此匿名函数直接默认为 tick
                // 因为 BlockEntityTicker 内部的 tick 并没有实现,因此我们在匿名函数为他加上实现
                // 因为我们不能确认传入的方块实体是否是正确的实体类型,因此需要进行转换
                ? (lvl, p, st, be) -> ((MachineBlockEntity) be).tick()
                : null;
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos,
                                 Player player, InteractionHand hand, BlockHitResult hit) {
        // 判断确保不是在客户端打开
        if (!level.isClientSide()) {
            // 通过方块坐标指定方块实体
            BlockEntity entity = level.getBlockEntity(pos);
            // 如果 entity 是 MachineBlockEntity ,那么则把它当作 machine 使用
            // machine 只是一个局部变量
            if (entity instanceof MachineBlockEntity machine) {
                // 告诉服务端执行打开 Screen
                NetworkHooks.openScreen((ServerPlayer) player, machine, pos);
            } else {
                // entity 不是 MachineBlockEntity 类型则崩溃并提示
                throw new IllegalStateException("Missing Container!");
            }
        }
        return InteractionResult.sidedSuccess(level.isClientSide());
    }

    // 重写被移除的方法
    @Override
    public void onRemove(BlockState pState, Level pLvel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        // 只有当旧方块与新方块不是一个方块的时候,说明该方块被替换或者破坏了
        // 因为有德方块状态变化的时候也有可能会触发,因此需要进行类型判断
        if (pState.getBlock() != pNewState.getBlock()) {
            // 去除当前位置的 BlockEntity
            BlockEntity blockEntity = pLvel.getBlockEntity(pPos);

            // 如果是该 Machine 的BlockEntity ,执行掉落逻辑
            if (blockEntity instanceof MachineBlockEntity Machine) {
                Machine.drops();
            }

            // 保留父类逻辑
            super.onRemove(pState, pLvel, pPos, pNewState, pIsMoving);
        }
    }
}
