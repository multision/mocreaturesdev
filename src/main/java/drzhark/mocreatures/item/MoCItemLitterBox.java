/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.item;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.entity.item.MoCEntityLitterBox;
import drzhark.mocreatures.init.MoCEntities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class MoCItemLitterBox extends MoCItem {

    public MoCItemLitterBox(Item.Properties properties, String name) {
        super(properties.maxStackSize(16), name);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        final ItemStack stack = player.getHeldItem(hand);
        if (!world.isRemote) {
            if (!player.abilities.isCreativeMode) stack.shrink(1);
            MoCEntityLitterBox litterBox = MoCEntities.LITTERBOX.create(world);
            litterBox.setPosition(player.getPosX(), player.getPosY(), player.getPosZ());
            world.addEntity(litterBox);
            MoCTools.playCustomSound(litterBox, SoundEvents.BLOCK_WOOD_PLACE);
        }
        return new ActionResult<>(ActionResultType.SUCCESS, stack);
    }
}
