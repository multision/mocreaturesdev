// drzhark.mocreatures.worldgen.MoCFeatures.java
package drzhark.mocreatures.dimension.worldgen.features;

import drzhark.mocreatures.init.MoCBlocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.template.BlockMatchRuleTest;
import net.minecraft.world.gen.feature.template.RuleTest;
import net.minecraft.world.gen.placement.*;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "mocreatures", bus = Mod.EventBusSubscriber.Bus.MOD)
public class MoCFeatures {

    // Vegetation
    public static ConfiguredFeature<?, ?> TALL_WYVGRASS_CONFIGURED;
    public static ConfiguredFeature<BaseTreeFeatureConfig, ?> WYVERN_TREE_RANDOM;
    public static ConfiguredFeature<BaseTreeFeatureConfig, ?> WYVERN_TREE_SPRUCE;
    public static ConfiguredFeature<BaseTreeFeatureConfig, ?> WYVERN_TREE_MEGA_OAK;

    // Ores
    public static ConfiguredFeature<?, ?> WYV_IRON_ORE;
    public static ConfiguredFeature<?, ?> WYV_GOLD_ORE;
    public static ConfiguredFeature<?, ?> WYV_LAPIS_ORE;
    public static ConfiguredFeature<?, ?> WYV_EMERALD_ORE;
    public static ConfiguredFeature<?, ?> WYV_DIAMOND_ORE;
    public static ConfiguredFeature<?, ?> WYV_ANCIENT_ORE;

    // Other
    public static ConfiguredFeature<?, ?> WYV_FIRESTONE_CLUSTER;

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

        // Trees

        // Step 1: Create base configs for spruce and mega oak
        BaseTreeFeatureConfig spruceConfig = new BaseTreeFeatureConfig.Builder(
                new SimpleBlockStateProvider(MoCBlocks.wyvwoodLog.getDefaultState()),
                new SimpleBlockStateProvider(MoCBlocks.wyvwoodLeaves.getDefaultState()),
                Features.SPRUCE.config.foliagePlacer,
                Features.SPRUCE.config.trunkPlacer,
                Features.SPRUCE.config.minimumSize
        ).setIgnoreVines().build();

        BaseTreeFeatureConfig megaOakConfig = new BaseTreeFeatureConfig.Builder(
                new SimpleBlockStateProvider(MoCBlocks.wyvwoodLog.getDefaultState()),
                new SimpleBlockStateProvider(MoCBlocks.wyvwoodLeaves.getDefaultState()),
                Features.DARK_OAK.config.foliagePlacer,
                Features.DARK_OAK.config.trunkPlacer,
                Features.DARK_OAK.config.minimumSize
        ).setIgnoreVines().build();

// Step 2: Create temporary configured features with no placement
        ConfiguredFeature<BaseTreeFeatureConfig, ?> tempSpruce = Feature.TREE.withConfiguration(spruceConfig);
        WYVERN_TREE_SPRUCE = Feature.TREE.withConfiguration(spruceConfig);
        ConfiguredFeature<BaseTreeFeatureConfig, ?> tempMegaOak = Feature.TREE.withConfiguration(megaOakConfig);
        WYVERN_TREE_MEGA_OAK = Feature.TREE.withConfiguration(megaOakConfig);

// Step 3: Build SafeWyvernTreeFeature with variants
        Feature<BaseTreeFeatureConfig> safeRandomTree = new SafeWyvernTreeFeature(tempSpruce, tempMegaOak);
        safeRandomTree.setRegistryName("mocreatures", "wyvern_tree_random");
        event.getRegistry().register(safeRandomTree);

// Step 4: Register final configured feature using one dummy config (required by Forge)
        WYVERN_TREE_RANDOM = (ConfiguredFeature<BaseTreeFeatureConfig, ?>) (
                safeRandomTree.withConfiguration(spruceConfig))
                .withPlacement(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG));

        Registry.register(WorldGenRegistries.CONFIGURED_FEATURE,
                new ResourceLocation("mocreatures", "wyvern_tree_random"),
                WYVERN_TREE_RANDOM);

        System.out.println("[MoC] Registered Wyvern Trees with random variant support.");


        // Ores
        RuleTest wyvStoneReplaceable = new BlockMatchRuleTest(MoCBlocks.wyvstone);

        // IRON: common, mid-depth
        WYV_IRON_ORE = Feature.ORE
                .withConfiguration(new OreFeatureConfig(wyvStoneReplaceable, MoCBlocks.wyvernIronOre.getDefaultState(), 9))
                .withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(0, 0, 64)))
                .square().count(20);

        // GOLD: less common, lower depth
        WYV_GOLD_ORE = Feature.ORE
                .withConfiguration(new OreFeatureConfig(wyvStoneReplaceable, MoCBlocks.wyvernGoldOre.getDefaultState(), 9))
                .withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(0, 0, 32)))
                .square().count(10);

        // LAPIS: rare, low-mid depth, spawns in small blobs
        WYV_LAPIS_ORE = Feature.ORE
                .withConfiguration(new OreFeatureConfig(wyvStoneReplaceable, MoCBlocks.wyvernLapisOre.getDefaultState(), 7))
                .withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(0, 0, 40)))
                .square().count(4);

        // EMERALD: very rare, mid-high elevation, 1 per chunk
        WYV_EMERALD_ORE = Feature.ORE
                .withConfiguration(new OreFeatureConfig(wyvStoneReplaceable, MoCBlocks.wyvernEmeraldOre.getDefaultState(), 1))
                .withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(32, 0, 128)))
                .square().count(1);

        // DIAMOND: rare, low elevation
        WYV_DIAMOND_ORE = Feature.ORE
                .withConfiguration(new OreFeatureConfig(wyvStoneReplaceable, MoCBlocks.wyvernDiamondOre.getDefaultState(), 8))
                .withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(0, 0, 16)))
                .square().count(6);

        // ANCIENT: ultra rare, deep, maybe used for special recipes
        WYV_ANCIENT_ORE = Feature.ORE
                .withConfiguration(new OreFeatureConfig(wyvStoneReplaceable, MoCBlocks.ancientOre.getDefaultState(), 3))
                .withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(0, 0, 16)))
                .square().count(4); // slightly more generous than diamond to help testing

        Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, new ResourceLocation("mocreatures", "wyv_iron_ore"), WYV_IRON_ORE);
        Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, new ResourceLocation("mocreatures", "wyv_gold_ore"), WYV_GOLD_ORE);
        Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, new ResourceLocation("mocreatures", "wyv_lapis_ore"), WYV_LAPIS_ORE);
        Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, new ResourceLocation("mocreatures", "wyv_emerald_ore"), WYV_EMERALD_ORE);
        Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, new ResourceLocation("mocreatures", "wyv_diamond_ore"), WYV_DIAMOND_ORE);
        Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, new ResourceLocation("mocreatures", "wyv_ancient_ore"), WYV_ANCIENT_ORE);

        // Firestone
        Feature<NoFeatureConfig> firestoneCluster = new FirestoneClusterFeature();
        firestoneCluster.setRegistryName("mocreatures", "firestone_cluster");
        event.getRegistry().register(firestoneCluster);

        WYV_FIRESTONE_CLUSTER = firestoneCluster
                .withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG)
                .withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(0, 0, 128)))
                .square()
                .count(10); // 10 clusters per chunk, adjust as needed

        Registry.register(WorldGenRegistries.CONFIGURED_FEATURE,
                new ResourceLocation("mocreatures", "wyv_firestone_cluster"),
                WYV_FIRESTONE_CLUSTER);
    }

}
