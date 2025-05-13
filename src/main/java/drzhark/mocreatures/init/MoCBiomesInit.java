package drzhark.mocreatures.init;

// Call this after your entity and biome registrations are complete.
// Likely inside FMLCommonSetupEvent (deferred) or directly via DeferredWorkQueue.runLater

import drzhark.mocreatures.dimension.biome.MoCBiomes;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "mocreatures", bus = Mod.EventBusSubscriber.Bus.MOD)
public class MoCBiomesInit {

    public static void setupSpawnRules() {
        DeferredWorkQueue.runLater(() -> {
            BiomeDictionary.Type WYVERN_LAIR = BiomeDictionary.Type.getType("WYVERN_LAIR");

            BiomeDictionary.addTypes(RegistryKey.getOrCreateKey(Registry.BIOME_KEY,
                    new ResourceLocation("mocreatures", "wyvernlairlandsforest")), WYVERN_LAIR);
            BiomeDictionary.addTypes(RegistryKey.getOrCreateKey(Registry.BIOME_KEY,
                    new ResourceLocation("mocreatures", "wyvernlairlands")), WYVERN_LAIR);
            BiomeDictionary.addTypes(RegistryKey.getOrCreateKey(Registry.BIOME_KEY,
                    new ResourceLocation("mocreatures", "wyvernlair_mountains")), WYVERN_LAIR);
            BiomeDictionary.addTypes(RegistryKey.getOrCreateKey(Registry.BIOME_KEY,
                    new ResourceLocation("mocreatures", "wyvernlair_desertlands")), WYVERN_LAIR);
        });
    }
}
