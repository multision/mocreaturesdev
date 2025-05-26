///*
// * GNU GENERAL PUBLIC LICENSE Version 3
// */
//package drzhark.mocreatures.compat.datafixes;
//
//import com.mojang.datafixers.DataFix;
//import com.mojang.datafixers.TypeRewriteRule;
//import drzhark.mocreatures.MoCConstants;
//import net.minecraft.entity.EntityType;
//import net.minecraft.nbt.CompoundNBT;
//import net.minecraft.util.ResourceLocation;
//import net.minecraftforge.common.MinecraftForge;
//import net.minecraftforge.event.RegistryEvent.MissingMappings;
//import net.minecraftforge.eventbus.api.SubscribeEvent;
//
//public class EntityIDFixer extends DataFix {
//    public EntityIDFixer() {
//        MinecraftForge.EVENT_BUS.register(this);
//    }
//
//    @Override
//    public TypeRewriteRule makeRule() {
//        String entityId = compound.getString("id");
//        if (entityId.equals(MoCConstants.MOD_PREFIX + "scorpion")) {
//            int entityType = compound.getInt("TypeInt");
//            switch (entityType) {
//                case 2:
//                    compound.putString("id", MoCConstants.MOD_PREFIX + "cavescorpion");
//                    break;
//                case 3:
//                    compound.putString("id", MoCConstants.MOD_PREFIX + "firescorpion");
//                    break;
//                case 4:
//                    compound.putString("id", MoCConstants.MOD_PREFIX + "frostscorpion");
//                    break;
//                default:
//                    compound.putString("id", MoCConstants.MOD_PREFIX + "dirtscorpion");
//            }
//            compound.putInt("TypeInt", 1);
//        }
//        if (entityId.equals(MoCConstants.MOD_PREFIX + "manticore")) {
//            int entityType = compound.getInt("TypeInt");
//            switch (entityType) {
//                case 2:
//                    compound.putString("id", MoCConstants.MOD_PREFIX + "darkmanticore");
//                    break;
//                case 3:
//                    compound.putString("id", MoCConstants.MOD_PREFIX + "frostmanticore");
//                    break;
//                case 4:
//                    compound.putString("id", MoCConstants.MOD_PREFIX + "toxicmanticore");
//                    break;
//                default:
//                    compound.putString("id", MoCConstants.MOD_PREFIX + "firemanticore");
//            }
//            compound.putInt("TypeInt", 1);
//        }
//        return compound;
//    }
//
//    @SubscribeEvent
//    public void missingEntityMapping(MissingMappings<EntityType<?>> event) {
//        ResourceLocation scorpion = new ResourceLocation(MoCConstants.MOD_ID, "scorpion");
//        ResourceLocation manticore = new ResourceLocation(MoCConstants.MOD_ID, "manticore");
//        for (MissingMappings.Mapping<EntityType<?>> entry : event.getAllMappings()) {
//            if (entry.key.equals(scorpion) || entry.key.equals(manticore)) {
//                entry.ignore();
//            }
//        }
//    }
//}

/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.compat.datafixes;

import drzhark.mocreatures.MoCConstants;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent.MissingMappings;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.EntityType;

public class EntityIDFixer {

    public EntityIDFixer() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    /**
     * Called manually when loading legacy entities to remap old IDs.
     */
    public CompoundNBT fixTagCompound(CompoundNBT compound) {
        String entityId = compound.getString("id");
        if (entityId.equals(MoCConstants.MOD_PREFIX + "scorpion")) {
            int entityType = compound.getInt("TypeInt");
            switch (entityType) {
                case 2:
                    compound.putString("id", MoCConstants.MOD_PREFIX + "cavescorpion");
                    break;
                case 3:
                    compound.putString("id", MoCConstants.MOD_PREFIX + "firescorpion");
                    break;
                case 4:
                    compound.putString("id", MoCConstants.MOD_PREFIX + "frostscorpion");
                    break;
                default:
                    compound.putString("id", MoCConstants.MOD_PREFIX + "dirtscorpion");
            }
            compound.putInt("TypeInt", 1);
        }

        if (entityId.equals(MoCConstants.MOD_PREFIX + "manticore")) {
            int entityType = compound.getInt("TypeInt");
            switch (entityType) {
                case 2:
                    compound.putString("id", MoCConstants.MOD_PREFIX + "darkmanticore");
                    break;
                case 3:
                    compound.putString("id", MoCConstants.MOD_PREFIX + "frostmanticore");
                    break;
                case 4:
                    compound.putString("id", MoCConstants.MOD_PREFIX + "toxicmanticore");
                    break;
                default:
                    compound.putString("id", MoCConstants.MOD_PREFIX + "firemanticore");
            }
            compound.putInt("TypeInt", 1);
        }

        return compound;
    }

    @SubscribeEvent
    public void onMissingMappings(MissingMappings<EntityType<?>> event) {
        ResourceLocation scorpion = new ResourceLocation(MoCConstants.MOD_ID, "scorpion");
        ResourceLocation manticore = new ResourceLocation(MoCConstants.MOD_ID, "manticore");

        for (MissingMappings.Mapping<EntityType<?>> entry : event.getAllMappings()) {
            if (entry.key.equals(scorpion) || entry.key.equals(manticore)) {
                entry.ignore(); // Ignore missing legacy IDs
            }
        }
    }

}
