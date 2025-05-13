/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.item;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ItemOgreHammer extends MoCItem {

    public ItemOgreHammer(Item.Properties properties, String name) {
        super(properties.maxStackSize(1).maxDamage(2048), name);
    }

    /**
     * returns the action that specifies what animation to play when the items
     * are being used
     */
    @Override
    public UseAction getUseAction(ItemStack par1ItemStack) {
        return UseAction.BLOCK;
    }

    /**
     * How long it takes to use or consume an item
     */
    @Override
    public int getUseDuration(ItemStack par1ItemStack) {
        return 72000;
    }

    /**
     * Called whenever this item is equipped and the right mouse button is
     * pressed. Args: itemStack, world, entityPlayer
     */
    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        final ItemStack stack = player.getHeldItem(hand);
        double coordY = player.getPosY() + player.getEyeHeight();
        double coordZ = player.getPosZ();
        double coordX = player.getPosX();

        for (int x = 3; x < 128; x++) {
            double newPosY = coordY - Math.cos((player.rotationPitch - 90F) / 57.29578F) * x;
            double newPosX = coordX + Math.cos((MoCTools.realAngle(player.rotationYaw - 90F) / 57.29578F)) * (Math.sin((player.rotationPitch - 90F) / 57.29578F) * x);
            double newPosZ = coordZ + Math.sin((MoCTools.realAngle(player.rotationYaw - 90F) / 57.29578F)) * (Math.sin((player.rotationPitch - 90F) / 57.29578F) * x);
            BlockPos pos = new BlockPos(MathHelper.floor(newPosX), MathHelper.floor(newPosY), MathHelper.floor(newPosZ));
            BlockState blockstate = player.world.getBlockState(pos);

            if (blockstate.getBlock() != Blocks.AIR) {
                newPosY = coordY - Math.cos((player.rotationPitch - 90F) / 57.29578F) * (x - 1);
                newPosX = coordX + Math.cos((MoCTools.realAngle(player.rotationYaw - 90F) / 57.29578F)) * (Math.sin((player.rotationPitch - 90F) / 57.29578F) * (x - 1));
                newPosZ = coordZ + Math.sin((MoCTools.realAngle(player.rotationYaw - 90F) / 57.29578F)) * (Math.sin((player.rotationPitch - 90F) / 57.29578F) * (x - 1));
                pos = new BlockPos(MathHelper.floor(newPosX), MathHelper.floor(newPosY), MathHelper.floor(newPosZ));
                if (player.world.getBlockState(pos).getBlock() != Blocks.AIR) {
                    return new ActionResult<>(ActionResultType.PASS, stack);
                }

                int blockInfo = obtainBlockAndMetadataFromBelt(player, true);
                if (blockInfo != 0) {
                    if (!world.isRemote) {
                        BlockState block = Block.getStateById(blockInfo);
                        player.world.setBlockState(pos, block, 3);
                        player.world.playSound(player, (float) newPosX + 0.5F, (float) newPosY + 0.5F, (float) newPosZ + 0.5F, block.getSoundType().getPlaceSound(), SoundCategory.BLOCKS, (block.getSoundType().getVolume() + 1.0F) / 2.0F, block.getSoundType().getPitch() * 0.8F);
                    }
                    MoCreatures.proxy.hammerFX(player);
                    //entityplayer.setItemInUse(itemstack, 200);
                }
                return new ActionResult<>(ActionResultType.SUCCESS, stack);
            }
        }
        return new ActionResult<>(ActionResultType.PASS, stack);
    }

    /**
     * Finds a block from the belt inventory of player, passes the block ID and
     * Metadata and reduces the stack by 1 if not on Creative mode
     */
    private int obtainBlockAndMetadataFromBelt(PlayerEntity player, boolean remove) {
        for (int y = 0; y < 9; y++) {
            ItemStack slotStack = player.inventory.getStackInSlot(y);
            if (slotStack.isEmpty()) {
                continue;
            }
            Item itemTemp = slotStack.getItem();
            if (itemTemp instanceof BlockItem) {
                if (remove && !player.abilities.isCreativeMode) {
                    slotStack.shrink(1);
                    if (slotStack.isEmpty()) {
                        player.inventory.setInventorySlotContents(y, ItemStack.EMPTY);
                    } else {
                        player.inventory.setInventorySlotContents(y, slotStack);
                    }
                }
                return Item.getIdFromItem(itemTemp);
            }
        }
        return 0;
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        return ActionResultType.FAIL;
    }
}
