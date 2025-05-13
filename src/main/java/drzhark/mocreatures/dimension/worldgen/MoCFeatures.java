// drzhark.mocreatures.worldgen.MoCFeatures.java
package drzhark.mocreatures.dimension.worldgen;

import drzhark.mocreatures.init.MoCBlocks;
import net.minecraft.advancements.criterion.BlockPredicate;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraft.world.gen.placement.CountPlacement;
import net.minecraft.world.gen.placement.NoPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.placement.NoiseDependant;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.trunkplacer.StraightTrunkPlacer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "mocreatures", bus = Mod.EventBusSubscriber.Bus.MOD)
public class MoCFeatures {

    public static ConfiguredFeature<?, ?> TALL_WYVGRASS_CONFIGURED;
    public static ConfiguredFeature<BaseTreeFeatureConfig, ?> WYVERN_TREE_FEATURE;

    @SubscribeEvent
    public static void registerFeature(RegistryEvent.Register<Feature<?>> event) {
        Feature<BlockClusterFeatureConfig> feature = new TallWyvGrassFeature();
        feature.setRegistryName("mocreatures", "tall_wyvgrass");
        event.getRegistry().register(feature);

        TALL_WYVGRASS_CONFIGURED = feature
                .withConfiguration(new BlockClusterFeatureConfig.Builder(
                        new SimpleBlockStateProvider(MoCBlocks.tallWyvgrass.getDefaultState()),
                        new SimpleBlockPlacer()
                ).tries(32).build())
                .withPlacement(Placement.HEIGHTMAP.configure(NoPlacementConfig.INSTANCE))
                .withPlacement(Placement.COUNT_NOISE.configure(new NoiseDependant(-0.8, 0, 4)));

        Registry.register(
                WorldGenRegistries.CONFIGURED_FEATURE,
                new ResourceLocation("mocreatures", "tall_wyvgrass"),
                TALL_WYVGRASS_CONFIGURED
        );

        System.out.println("[MoC] Registered tall_wyvgrass feature and configured feature.");

        // Use vanilla TreeFeature directly — no custom class needed
        Feature<BaseTreeFeatureConfig> wyvernTree = new TreeFeature(BaseTreeFeatureConfig.CODEC);
        wyvernTree.setRegistryName("mocreatures", "wyvern_tree");
        event.getRegistry().register(wyvernTree);

        // Build your config
        BaseTreeFeatureConfig config = new BaseTreeFeatureConfig.Builder(
                new SimpleBlockStateProvider(MoCBlocks.wyvwoodLog.getDefaultState()),
                new SimpleBlockStateProvider(MoCBlocks.wyvwoodLeaves.getDefaultState()),
                Features.SPRUCE.config.foliagePlacer,
                Features.SPRUCE.config.trunkPlacer,
                Features.SPRUCE.config.minimumSize
                )     // controls how foliage stacks
                .setIgnoreVines()
                .build();


        WYVERN_TREE_FEATURE = wyvernTree.withConfiguration(config);

        Registry.register(WorldGenRegistries.CONFIGURED_FEATURE,
                new ResourceLocation("mocreatures", "wyvern_tree"),
                WYVERN_TREE_FEATURE);

        System.out.println("[MoC] Registered WYVERN_TREE_FEATURE.");
    }

}
