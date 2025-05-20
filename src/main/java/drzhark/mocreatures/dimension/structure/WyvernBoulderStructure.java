package drzhark.mocreatures.dimension.structure;

import drzhark.mocreatures.init.MoCBlocks;
import net.minecraft.util.Mirror;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.template.BlockIgnoreStructureProcessor;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;

@Mod.EventBusSubscriber
public class WyvernBoulderStructure {

    private static Feature<NoFeatureConfig> boulderFeature = null;
    private static ConfiguredFeature<?, ?> boulder1Configured = null;
    private static ConfiguredFeature<?, ?> boulder2Configured = null;

    @SubscribeEvent
    public static void addFeatureToBiomes(BiomeLoadingEvent event) {
        if (!event.getName().getNamespace().equals("mocreatures")) return;
        String path = event.getName().getPath();
        if (path.startsWith("wyvernlairlands")) {
            event.getGeneration().getFeatures(GenerationStage.Decoration.SURFACE_STRUCTURES).add(() -> boulder1Configured);
            event.getGeneration().getFeatures(GenerationStage.Decoration.SURFACE_STRUCTURES).add(() -> boulder2Configured);
        }
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    private static class FeatureRegisterHandler {
        @SubscribeEvent
        public static void registerFeature(RegistryEvent.Register<Feature<?>> event) {
            boulderFeature = new Feature<NoFeatureConfig>(NoFeatureConfig.CODEC) {
                @Override
                public boolean generate(ISeedReader world, ChunkGenerator generator, Random random, BlockPos pos, NoFeatureConfig config) {
                    int chunkX = pos.getX() >> 4 << 4;
                    int chunkZ = pos.getZ() >> 4 << 4;
                    RegistryKey dimensionType = world.getWorld().getDimensionKey();
                    if (!dimensionType.getLocation().toString().equals("mocreatures:wyvernlairworld")) return false;

                    if (random.nextInt(1000000) >= 40000) return false;

                    int i = chunkX + random.nextInt(16);
                    int k = chunkZ + random.nextInt(16);
                    int j = world.getHeight(Heightmap.Type.OCEAN_FLOOR_WG, i, k) - 1;
                    BlockPos base = new BlockPos(i, j, k);

                    // 4x4 flat wyvgrass check
                    for (int dx = -2; dx < 2; dx++) {
                        for (int dz = -2; dz < 2; dz++) {
                            BlockPos check = base.add(dx, 0, dz);
                            if (world.getBlockState(check).getBlock() != MoCBlocks.wyvgrass ||
                                    world.isAirBlock(check.down())) {
                                return false;
                            }
                        }
                    }

                    Rotation rotation = Rotation.values()[random.nextInt(Rotation.values().length)];
                    Mirror mirror = Mirror.values()[random.nextInt(Mirror.values().length)];
                    BlockPos spawnTo = base.up();
                    String structureName = random.nextBoolean() ? "wyvernboulder1" : "wyvernboulder2";
                    Template template = world.getWorld().getStructureTemplateManager().getTemplate(new ResourceLocation("mocreatures", structureName));
                    if (template == null) return false;

                    template.func_237144_a_((IServerWorld) world, spawnTo,
                            new PlacementSettings()
                                    .setRotation(rotation)
                                    .setMirror(mirror)
                                    .addProcessor(BlockIgnoreStructureProcessor.AIR_AND_STRUCTURE_BLOCK)
                                    .setIgnoreEntities(false),
                            random);
                    return true;
                }
            };

            event.getRegistry().register(boulderFeature.setRegistryName("wyvern_boulder_feature"));

            boulder1Configured = boulderFeature.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG)
                    .withPlacement(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG));
            boulder2Configured = boulderFeature.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG)
                    .withPlacement(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG));

            Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, new ResourceLocation("mocreatures", "boulder_1"), boulder1Configured);
            Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, new ResourceLocation("mocreatures", "boulder_2"), boulder2Configured);
        }
    }
}