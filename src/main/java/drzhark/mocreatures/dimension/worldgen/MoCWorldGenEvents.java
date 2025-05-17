// drzhark.mocreatures.worldgen.MoCWorldGenEvents.java
package drzhark.mocreatures.dimension.worldgen;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.GenerationStage;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "mocreatures", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class MoCWorldGenEvents {

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onBiomeLoad(BiomeLoadingEvent event) {
        if (event.getName() == null) return;


        // Only target Wyvern Lair biomes
        if (event.getName().getNamespace().equals("mocreatures") &&
                event.getName().getPath().startsWith("wyvernlair")) {

            System.out.println("[MoC] Injecting tall_wyvgrass into biome: " + event.getName());


            if (MoCFeatures.TALL_WYVGRASS_CONFIGURED != null) {
                event.getGeneration()
                        .getFeatures(GenerationStage.Decoration.VEGETAL_DECORATION)
                        .add(() -> MoCFeatures.TALL_WYVGRASS_CONFIGURED);
            } else {
                System.out.println("[MoC] Warning: ConfiguredFeature is still null during biome load!");
            }

            event.getGeneration().getFeatures(GenerationStage.Decoration.UNDERGROUND_ORES).add(() -> MoCFeatures.WYV_IRON_ORE);
            event.getGeneration().getFeatures(GenerationStage.Decoration.UNDERGROUND_ORES).add(() -> MoCFeatures.WYV_GOLD_ORE);
            event.getGeneration().getFeatures(GenerationStage.Decoration.UNDERGROUND_ORES).add(() -> MoCFeatures.WYV_LAPIS_ORE);
            event.getGeneration().getFeatures(GenerationStage.Decoration.UNDERGROUND_ORES).add(() -> MoCFeatures.WYV_EMERALD_ORE);
            event.getGeneration().getFeatures(GenerationStage.Decoration.UNDERGROUND_ORES).add(() -> MoCFeatures.WYV_DIAMOND_ORE);
            event.getGeneration().getFeatures(GenerationStage.Decoration.UNDERGROUND_ORES).add(() -> MoCFeatures.WYV_ANCIENT_ORE);

            event.getGeneration().getFeatures(GenerationStage.Decoration.UNDERGROUND_DECORATION)
                    .add(() -> MoCFeatures.WYV_FIRESTONE_CLUSTER);
        }
    }
}
