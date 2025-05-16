/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.item;

import drzhark.mocreatures.entity.item.MoCEntityEgg;
import drzhark.mocreatures.init.MoCEntities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/*
 * ID LIST:
 * [0] Blue Fishy
 * [1] Regal Blue Fishy
 * [2] Orange White Stripe Fishy
 * [3] Light Blue Fishy
 * [4] Green Yellow Fishy
 * [5] Green Fishy
 * [6] Purple Fishy
 * [7] Yellow Fishy
 * [8] Orange Blue Stripe Fishy
 * [9] Black White Fishy
 * [10] Red Fishy
 * [11] Shark
 * [21] Dark Snake
 * [22] Spotted Snake
 * [23] Orange Snake
 * [24] Green Snake
 * [25] Coral Snake
 * [26] Cobra
 * [27] Rattlesnake
 * [28] Python
 * [30] Ostrich
 * [31] Stolen Ostrich
 * [33] Komodo Dragon
 * [41] Earth Scorpion
 * [42] Dark Scorpion
 * [43] Fire Scorpion
 * [44] Frost Scorpion
 * [45] Undead Scorpion
 * [50] Jungle Wyvern (Green)
 * [51] Swamp Wyvern (Lime)
 * [52] Savanna Wyvern (Russet)
 * [53] Sand Wyvern (Yellow)
 * [54] Mother Wyvern (Red)
 * [55] Undead Wyvern
 * [56] Light Wyvern
 * [57] Dark Wyvern
 * [58] Arctic Wyvern (Light Blue)
 * [59] Cave Wyvern (Gray)
 * [60] Mountain Wyvern (Brown)
 * [61] Sea Wyvern (Turquoise)
 * [62] Fire Manticore
 * [63] Dark Manticore
 * [64] Frost Manticore
 * [65] Toxic Manticore
 * [66] Manticore (Plain)
 * [70] Salmon
 * [71] Cod
 * [72] Bass
 * [80] Anchovy
 * [81] Angelfish
 * [82] Anglerfish
 * [83] Clownfish
 * [84] Goldfish
 * [85] Hippo Tang
 * [86] Mandarinfish
 * [90] Piranha
 */

public class MoCItemEgg extends MoCItem {

    public MoCItemEgg(Item.Properties properties, String name) {
        super(properties.maxStackSize(16), name);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        final ItemStack stack = player.getHeldItem(hand);
        if (!player.abilities.isCreativeMode) stack.shrink(1);
        if (!world.isRemote && player.isOnGround()) {
            int eggType = stack.getOrCreateTag().getInt("EggType");
            if (eggType == 30) {
                eggType = 31; //for ostrich eggs. placed eggs become stolen eggs.
            }
            MoCEntityEgg entityEgg = MoCEntities.EGG.create(world);
            assert entityEgg != null;
            entityEgg.setEggType(eggType);
            entityEgg.setPosition(player.getPosX(), player.getPosY(), player.getPosZ());
            player.world.addEntity(entityEgg);

            entityEgg.setMotion(entityEgg.getMotion().add((world.rand.nextFloat() - world.rand.nextFloat()) * 0.3F, world.rand.nextFloat() * 0.05F, (world.rand.nextFloat() - world.rand.nextFloat()) * 0.3F));

            System.out.println("[DEBUG] Placing egg with type: " + eggType);
        }
        return new ActionResult<>(ActionResultType.SUCCESS, stack);
    }


    @Override
    @OnlyIn(Dist.CLIENT)
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
        if (!this.isInGroup(group)) return;

        // Fish eggs
        for (int i = 0; i <= 10; i++) { // Fishy
            ItemStack stack = new ItemStack(this);
            stack.getOrCreateTag().putInt("EggType", i);
            items.add(stack);
        }

        // Shark
        ItemStack shark = new ItemStack(this);
        shark.getOrCreateTag().putInt("EggType", 11);
        items.add(shark);

        // Snakes
        for (int i = 21; i <= 28; i++) { // Snakes
            ItemStack snakeEgg = new ItemStack(this);
            snakeEgg.getOrCreateTag().putInt("EggType", i);
            items.add(snakeEgg);
        }

        // Ostrich & Stolen Ostrich
        for (int i : new int[] {30, 31}) {
            ItemStack ostrichEgg = new ItemStack(this);
            ostrichEgg.getOrCreateTag().putInt("EggType", i);
            items.add(ostrichEgg);
        }

        // Komodo Dragon
        ItemStack komodo = new ItemStack(this);
        komodo.getOrCreateTag().putInt("EggType", 33);
        items.add(komodo);

        // Scorpions
        for (int i = 41; i <= 45; i++) {
            ItemStack scorpion = new ItemStack(this);
            scorpion.getOrCreateTag().putInt("EggType", i);
            items.add(scorpion);
        }

        // Wyverns
        for (int i = 50; i <= 61; i++) {
            ItemStack wyvern = new ItemStack(this);
            wyvern.getOrCreateTag().putInt("EggType", i);
            items.add(wyvern);
        }

        // Manticores
        for (int i = 62; i <= 66; i++) {
            ItemStack manticore = new ItemStack(this);
            manticore.getOrCreateTag().putInt("EggType", i);
            items.add(manticore);
        }

        // Medium Fish
        for (int i = 70; i <= 72; i++) {
            ItemStack fish = new ItemStack(this);
            fish.getOrCreateTag().putInt("EggType", i);
            items.add(fish);
        }

        // Small Fish
        for (int i = 80; i <= 86; i++) {
            ItemStack fish = new ItemStack(this);
            fish.getOrCreateTag().putInt("EggType", i);
            items.add(fish);
        }

        // Piranha
        ItemStack piranha = new ItemStack(this);
        piranha.getOrCreateTag().putInt("EggType", 90);
        items.add(piranha);
    }

    @Override
    public String getTranslationKey(ItemStack itemstack) {
        int eggType = itemstack.getOrCreateTag().getInt("EggType");
        return getTranslationKey() + "." + eggType;  // item.mocreatures.mocegg.0, item.mocreatures.mocegg.1, etc.
    }

}
