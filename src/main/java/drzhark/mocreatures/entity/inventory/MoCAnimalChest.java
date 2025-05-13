/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.inventory;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.LockableTileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.LockCode;

public class MoCAnimalChest extends Inventory implements INamedContainerProvider {

    private LockCode lockCode = LockCode.EMPTY_CODE;
    private ITextComponent name;
    private Size size;

    public MoCAnimalChest(String name, Size size) {
        super(size.numSlots);
        this.name = new StringTextComponent(name);
        this.size = size;
    }

    @Override
    public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity playerIn) {
        return LockableTileEntity.canUnlock(playerIn, this.lockCode, this.getDisplayName()) ? new ChestContainer(this.size.getType(), id, playerInventory, this, this.size.getRows()) : null;
    }

    public void write(CompoundNBT nbttagcompound) {
        this.lockCode.write(nbttagcompound);
    }
    public void read(CompoundNBT nbttagcompound) {
        this.lockCode = LockCode.read(nbttagcompound);
    }

    @Override
    public ITextComponent getDisplayName() {
        return name;
    }

    public static enum Size {

        tiny(9, 1, ContainerType.GENERIC_9X1),
        small(18, 2, ContainerType.GENERIC_9X2),
        medium(27, 3, ContainerType.GENERIC_9X3),
        large(36, 4, ContainerType.GENERIC_9X4),
        huge(45, 5, ContainerType.GENERIC_9X5),
        gigantic(54, 6, ContainerType.GENERIC_9X6);
        private final int numSlots;
        private final int rows;
        private final ContainerType type;

        private Size(int numSlots, int rows, ContainerType type){
            this.numSlots = numSlots;
            this.rows = rows;
            this.type = type;
        }

        public int getNumSlots() {
            return numSlots;
        }

        public int getRows() {
            return rows;
        }

        public ContainerType getType() {
            return type;
        }
    }
}
