/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.item;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.entity.item.MoCEntityKittyBed;
import drzhark.mocreatures.init.MoCEntities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class MoCItemKittyBed extends MoCItem {

    private int sheetType;

    public MoCItemKittyBed(Item.Properties properties, String name) {
        super(properties, name);
    }

    public MoCItemKittyBed(Item.Properties properties, String name, int type) {
        this(properties, name);
        this.sheetType = type;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        final ItemStack stack = player.getHeldItem(hand);
        if (!world.isRemote) {
            if (!player.abilities.isCreativeMode) stack.shrink(1);
            MoCEntityKittyBed kittyBed = MoCEntities.KITTY_BED.create(world);
            kittyBed.setSheetColor(this.sheetType);
            kittyBed.setPosition(player.getPosX(), player.getPosY(), player.getPosZ());
            world.addEntity(kittyBed);
            MoCTools.playCustomSound(kittyBed, SoundEvents.BLOCK_WOOD_PLACE);
        }
        return new ActionResult<>(ActionResultType.SUCCESS, stack);
    }
}
