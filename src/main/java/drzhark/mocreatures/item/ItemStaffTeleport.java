/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.item;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.init.MoCSoundEvents;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ItemStaffTeleport extends MoCItem {

    public ItemStaffTeleport(Item.Properties properties, String name) {
        super(properties.maxStackSize(1).maxDamage(128), name);
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
     * Called whenever this item is equipped and the right mouse button is
     * pressed. Args: itemStack, world, entityPlayer
     */
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity player, Hand hand) {
        final ItemStack stack = player.getHeldItem(hand);
        if (player.getRidingEntity() != null || player.isBeingRidden()) {
            return ActionResult.resultPass(stack);
        }

        double coordY = player.getPosY() + player.getEyeHeight();
        double coordZ = player.getPosZ();
        double coordX = player.getPosX();
        for (int x = 4; x < 128; x++) {
            double newPosY = coordY - Math.cos((player.rotationPitch - 90F) / 57.29578F) * x;
            double newPosX = coordX + Math.cos((MoCTools.realAngle(player.rotationYaw - 90F) / 57.29578F)) * (Math.sin((player.rotationPitch - 90F) / 57.29578F) * x);
            double newPosZ = coordZ + Math.sin((MoCTools.realAngle(player.rotationYaw - 90F) / 57.29578F)) * (Math.sin((player.rotationPitch - 90F) / 57.29578F) * x);
            BlockPos pos = new BlockPos(MathHelper.floor(newPosX), MathHelper.floor(newPosY), MathHelper.floor(newPosZ));
            BlockState blockstate = player.world.getBlockState(pos);
            if (blockstate.getBlock() != Blocks.AIR) {
                newPosY = coordY - Math.cos((player.rotationPitch - 90F) / 57.29578F) * (x - 1);
                newPosX = coordX + Math.cos((MoCTools.realAngle(player.rotationYaw - 90F) / 57.29578F)) * (Math.sin((player.rotationPitch - 90F) / 57.29578F) * (x - 1));
                newPosZ = coordZ + Math.sin((MoCTools.realAngle(player.rotationYaw - 90F) / 57.29578F)) * (Math.sin((player.rotationPitch - 90F) / 57.29578F) * (x - 1));

                if (!worldIn.isRemote) {
                    ServerPlayerEntity playerMP = (ServerPlayerEntity) player;
                    playerMP.connection.setPlayerLocation(newPosX, newPosY, newPosZ, player.rotationYaw, player.rotationPitch);
                    MoCTools.playCustomSound(player, MoCSoundEvents.ENTITY_GENERIC_MAGIC_APPEAR.get());
                }
                MoCreatures.proxy.teleportFX(player);
                // player.setItemInUse(stack, 200);
                stack.damageItem(1, player, (user) -> {
                    user.sendBreakAnimation(hand);
                });

                return ActionResult.resultSuccess(stack);
            }
        }

        //player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
        return ActionResult.resultSuccess(stack);
    }

    public int getMaxItemUseDuration(ItemStack stack) {
        return 200;
    }
}
