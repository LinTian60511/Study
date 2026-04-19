package Lin.study.container.menu;

import Lin.study.entity.StorableEntity;
import Lin.study.init.ModMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

public class StorableEntityMenu extends AbstractContainerMenu {

    // 菜单绑定的实体
    public final StorableEntity storableEntity;
    // 物品槽位
    private final ItemStackHandler inventory = new ItemStackHandler(9);
    // 当前菜单所在的世界
    private final Level level;

    // 通用构造器
    public StorableEntityMenu(int id, Inventory inv, Entity entity) {
        // 指定菜单对应的 Entity
        super(ModMenuTypes.STORABLE_ENTITY_MENU.get(), id);

        this.storableEntity = (StorableEntity) entity;
        this.level = inv.player.level();

        // 添加实体的物品栏槽位
        addStorageSlots(storableEntity.getInventory(), 7, 53);

        // 添加玩家被与快捷栏
        addPlayerInventory(inv, 7, 83);
        addPlayerHotbar(inv, 7, 141);

    }

    // 客户端构造器
    public StorableEntityMenu(int id, Inventory inv, FriendlyByteBuf buf) {
        this(id, inv,
                (StorableEntity) inv.player.level().getEntity(buf.readInt()));
    }

    // 实体背包槽位
    private void addStorageSlots(IItemHandler handler, int startX, int startY) {
        for (int row = 0; row < 1; ++row) {
            for (int col = 0; col < 9; ++col) {
                int slotIndex = row * 3 + col;
                int x = startX + col * 18;
                int y = startY + row * 18;
                this.addSlot(new SlotItemHandler(handler, slotIndex, x, y));
            }
        }
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

    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        return null;
    }

    // 检查玩家是否还能使用该界面
    @Override
    public boolean stillValid(Player pPlayer) {
        if (this.storableEntity.isRemoved()) return false;

        double distance = pPlayer.distanceToSqr(this.storableEntity);
        return distance <= 64;
    }
}
