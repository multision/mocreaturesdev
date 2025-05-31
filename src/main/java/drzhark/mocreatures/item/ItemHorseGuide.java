package drzhark.mocreatures.item;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.ReadBookScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.ITextComponent.Serializer;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ItemHorseGuide extends Item {

    public ItemHorseGuide(Item.Properties properties) {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if (world.isRemote) {
            openClientBookScreen(stack);
        }
        return ActionResult.resultSuccess(stack);
    }

    @OnlyIn(Dist.CLIENT)
    private void openClientBookScreen(ItemStack stack) {
        Minecraft.getInstance().displayGuiScreen(new ReadBookScreen(new ReadBookScreen.WrittenBookInfo(stack)));
    }

    @Override
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
        if (this.isInGroup(group)) {
            ItemStack book = new ItemStack(this);
            CompoundNBT tag = new CompoundNBT();

            tag.putString("title", "Mo' Creatures Horse Guide");
            tag.putString("author", "forgoted");
            tag.putBoolean("resolved", true);

            CompoundNBT display = new CompoundNBT();
            display.putString("Name", "{\"text\":\"Mo' Creatures Horse Guide\",\"italic\":false}");

            ListNBT lore = new ListNBT();
            lore.add(StringNBT.valueOf("{\"text\":\"by forgoted\",\"italic\":false,\"color\":\"gray\"}"));
            lore.add(StringNBT.valueOf("{\"text\":\"Original\",\"italic\":false,\"color\":\"gray\"}"));
            display.put("Lore", lore);

            tag.put("display", display);

            ListNBT pages = new ListNBT();

            pages.add(StringNBT.valueOf(Serializer.toJson(new StringTextComponent(
                    "Welcome to the Mo' Creatures Horse Guide!\n\n" +
                            "Learn detailed methods to breed and raise all horses, special breeds, and mythical creatures."))));

            pages.add(StringNBT.valueOf(Serializer.toJson(new StringTextComponent(
                    "Tier 1 Horses (Breed Only)\n\n" +
                            "- Vanilla: Natural spawn\n" +
                            "- White/Light Grey: White + Pegasus/Fairy/Unicorn/Nightmare\n" +
                            "- Buckskin: Palomino Snowflake + Bay/Brown"))));

            pages.add(StringNBT.valueOf(Serializer.toJson(new StringTextComponent(
                    "Tier 1 (continued)\n\n" +
                            "- Blood Bay: Dark Brown/Bay + Buckskin\n" +
                            "- Mahogany/Dark Bay: Grulla Overo + Black Horse\n" +
                            "- Black: Nightmare/Fairy/Unicorn/Pegasus + Black Horse"))));

            pages.add(StringNBT.valueOf(Serializer.toJson(new StringTextComponent(
                    "Tier 2 Horses\n\n" +
                            "- Palomino Snowflake (wild)\n" +
                            "- Grulla Overo/Grullo (wild)\n" +
                            "- Bay (wild)\n" +
                            "- Dappled Grey: Grey Spotted + Pegasus/Unicorn/Nightmare/Fairy"))));

            pages.add(StringNBT.valueOf(Serializer.toJson(new StringTextComponent(
                    "Tier 3 Horses\n\n" +
                            "- Bay Tovero: Grulla Overo + Brown Spotted/Blood Bay\n" +
                            "- Palomino Tovero: Grulla Overo + White/Light Grey\n" +
                            "- Grulla/Grullo Tovero: Dappled Grey + White/Light Grey"))));

            pages.add(StringNBT.valueOf(Serializer.toJson(new StringTextComponent(
                    "Tier 4 Horses (Zebra Taming)\n\n" +
                            "- Black Leopard: Grulla Tovero + Black\n" +
                            "- Black Tovero: Bay Tovero + Black\n\n" +
                            "These horses tame zebras."))));

            pages.add(StringNBT.valueOf(Serializer.toJson(new StringTextComponent(
                    "Unique Horses\n\n" +
                            "- Zebra (Chest Carrier)\n" +
                            "- Zorse (Sterile)\n" +
                            "- Zonkey (Sterile, Chest Carrier)\n" +
                            "- Mule (Sterile, Chest Carrier)"))));

            pages.add(StringNBT.valueOf(Serializer.toJson(new StringTextComponent(
                    "Mythical Horses\n\n" +
                            "- Nightmare (No Armor)\n" +
                            "- Unicorn (Armor)\n" +
                            "- Pegasus (Armor)\n" +
                            "- Bat Horse (No Armor)"))));

            pages.add(StringNBT.valueOf(Serializer.toJson(new StringTextComponent(
                    "Advanced Mythicals\n\n" +
                            "- Dark Pegasus (Chest Carrier)\n" +
                            "- Fairy Horse (Chest Carrier, Armor)"))));

            pages.add(StringNBT.valueOf(Serializer.toJson(new StringTextComponent(
                    "Undead & Skeleton\n\n" +
                            "- Created: Essence of Undead\n" +
                            "- Decays to Skeleton\n" +
                            "- Heal/Restore: Essence of Light/Undead\n" +
                            "- No Armor"))));

            pages.add(StringNBT.valueOf(Serializer.toJson(new StringTextComponent(
                    "Breeding & Care Tips\n\n" +
                            "- Enclose horses\n" +
                            "- Feed Bread/Sugar to grow foals\n" +
                            "- Keep chunks loaded\n" +
                            "- Only breed tamed horses"))));

            tag.put("pages", pages);
            book.setTag(tag);
            items.add(book);
        }
    }

    @Override
    public boolean hasEffect(ItemStack stack) {
        return true;
    }
}
