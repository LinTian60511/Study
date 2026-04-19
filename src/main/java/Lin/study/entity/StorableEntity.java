package Lin.study.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

public class StorableEntity extends SimpleShootableEntity {

    // 物品槽位
    private final ItemStackHandler inventory = new ItemStackHandler(9);

    public StorableEntity(EntityType<? extends SimpleShootableEntity> entityType, Level level) {
        super(entityType, level);
    }

    // 把数据存储到 NBT
    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.put("Inventory", inventory.serializeNBT());
    }

    // 从 NBT 提取数据
    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        inventory.deserializeNBT(tag.getCompound("Inventory"));
    }

    // 当玩家打开界面是创建 Menu
    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {

    }

    public IItemHandler getInventory() {
        return inventory;
    }

}
