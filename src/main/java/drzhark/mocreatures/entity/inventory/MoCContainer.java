package drzhark.mocreatures.entity.inventory;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.tileentity.LockableTileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.LockCode;

import javax.annotation.Nullable;

public class MoCContainer implements INamedContainerProvider {
    private LockCode lockCode = LockCode.EMPTY_CODE;
    private ITextComponent name;
    private MoCAnimalChest.Size size;
    private IInventory inventory;

    public MoCContainer(String name, MoCAnimalChest.Size size, IInventory inventory) {
        this.name = new StringTextComponent(name);
        this.size = size;
        this.inventory = inventory;
    }
    @Override
    public ITextComponent getDisplayName() {
        return name;
    }

    @Nullable
    @Override
    public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity playerIn) {
        return LockableTileEntity.canUnlock(playerIn, this.lockCode, this.getDisplayName()) ? new ChestContainer(this.size.getType(), id, playerInventory, this.inventory, this.size.getRows()) : null;
    }
}
