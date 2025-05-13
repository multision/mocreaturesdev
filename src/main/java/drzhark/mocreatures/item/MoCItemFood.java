/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.item;

import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;

public class MoCItemFood extends MoCItem {
    public int itemUseDuration;

    protected MoCItemFood(MoCItemFood.Builder builder) {
        super(builder.properties.food(builder.foodBuilder.build()), builder.name);
        itemUseDuration = builder.itemUseDuration;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        if (itemUseDuration == 0) {
            return 32;
        }

        return itemUseDuration;
    }


    public static class Builder {
        private Food.Builder foodBuilder;
        private int itemUseDuration;
        private Item.Properties properties;
        private String name;

        public Builder(Item.Properties properties, String name, int amount) {
            this(properties, name, amount, 0.6F, false);
        }

        public Builder(Item.Properties properties, String name, int amount, float saturation, boolean isWolfFood) {
            this(properties, name, amount, saturation, isWolfFood, 32);
        }

        public Builder(Item.Properties properties, String name, int amount, float saturation, boolean isWolfFood, int eatingSpeed) {
            this.properties = properties;
            this.name = name;
            foodBuilder = (new Food.Builder()).hunger(amount).saturation(saturation);
            if(isWolfFood)
                foodBuilder.meat();
            itemUseDuration = eatingSpeed; // 32 by default
        }

        public Builder setAlwaysEdible() {
            foodBuilder.setAlwaysEdible();
            return this;
        }

        public Builder setPotionEffect(EffectInstance effectIn, float probability) {
            foodBuilder.effect(() -> effectIn, probability);
            return this;
        }

        public MoCItemFood build() {
            return new MoCItemFood(this);
        }
    }
}
