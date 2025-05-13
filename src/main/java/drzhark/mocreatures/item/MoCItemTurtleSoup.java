/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;

public class MoCItemTurtleSoup extends MoCItemFood {

    protected MoCItemTurtleSoup(MoCItemFood.Builder builder) {
        super(builder);
    }

    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving) {
        ItemStack itemstack = super.onItemUseFinish(stack, worldIn, entityLiving);
        return entityLiving instanceof PlayerEntity && ((PlayerEntity)entityLiving).abilities.isCreativeMode ? itemstack : new ItemStack(Items.BOWL);
    }
    public static class Builder extends MoCItemFood.Builder {
        public Builder(Item.Properties properties, String name, int amount) {
            this(properties, name, amount, 0.6F, false);
        }

        public Builder(Item.Properties properties, String name, int amount, float saturation, boolean isWolfFood) {
            this(properties, name, amount, saturation, isWolfFood, 32);
        }

        public Builder(Item.Properties properties, String name, int amount, float saturation, boolean isWolfFood, int eatingSpeed) {
            super(properties, name, amount, saturation, isWolfFood, eatingSpeed);
        }

        public MoCItemTurtleSoup build() {
            return new MoCItemTurtleSoup(this);
        }
    }
}
