/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.item;

import drzhark.mocreatures.MoCConstants;
import net.minecraft.item.Item;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class MoCItemRecord extends MusicDiscItem {

    public static ResourceLocation RECORD_SHUFFLE_RESOURCE = new ResourceLocation(MoCConstants.MOD_ID, "shuffling");

    public MoCItemRecord(int comparatorValueIn, String name, java.util.function.Supplier<SoundEvent> soundSupplier, Item.Properties builder) {
        super(comparatorValueIn, soundSupplier, builder);
        this.setRegistryName(MoCConstants.MOD_ID, name);
    }

    @OnlyIn(Dist.CLIENT)
    /*
     * Return the title for this record.
     */ public String getRecordTitle() {
        return "MoC - " + this.getDescription();
    }
}
