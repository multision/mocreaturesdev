package drzhark.mocreatures.util;

import net.minecraft.item.Item;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags.IOptionalNamedTag;

public class MoCTags {
    public static class Items {
        public static final IOptionalNamedTag<Item> COOKED_FISHES = ItemTags.createOptional(new ResourceLocation("forge", "cooked_fishes"));
        public static final IOptionalNamedTag<Item> RAW_FISHES = ItemTags.createOptional(new ResourceLocation("forge", "raw_fishes"));
    }
}
