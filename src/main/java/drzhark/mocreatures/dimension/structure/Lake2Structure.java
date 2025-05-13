package drzhark.mocreatures.dimension.structure;

import drzhark.mocreatures.init.MoCBlocks;
import net.minecraft.block.BlockState;
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
import net.minecraft.world.gen.feature.template.StructureProcessor;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;

@Mod.EventBusSubscriber
public class Lake2Structure {
    private static Feature<NoFeatureConfig> feature = null;
    private static ConfiguredFeature<?, ?> configuredFeature = null;

    @SubscribeEvent
    public static void addFeatureToBiomes(BiomeLoadingEvent event) {
        boolean biomeCriteria = false;
        if (new ResourceLocation("mocreatures:wyvernlairlandsforest").equals((Object)event.getName())) {
            biomeCriteria = true;
        }
        if (new ResourceLocation("mocreatures:wyvernlairlands").equals((Object)event.getName())) {
            biomeCriteria = true;
        }
        if (!biomeCriteria) {
            return;
        }
        event.getGeneration().getFeatures(GenerationStage.Decoration.SURFACE_STRUCTURES).add(() -> configuredFeature);
    }

    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    private static class FeatureRegisterHandler {
        private FeatureRegisterHandler() {
        }

        @SubscribeEvent
        public static void registerFeature(RegistryEvent.Register<Feature<?>> event) {
            feature = new Feature<NoFeatureConfig>(NoFeatureConfig.CODEC){

                public boolean generate(ISeedReader world, ChunkGenerator generator, Random random, BlockPos pos, NoFeatureConfig config) {
                    int ci = pos.getX() >> 4 << 4;
                    int ck = pos.getZ() >> 4 << 4;
                    RegistryKey dimensionType = world.getWorld().getDimensionKey();
                    boolean dimensionCriteria = false;
                    if (dimensionType == RegistryKey.getOrCreateKey((RegistryKey) Registry.WORLD_KEY, (ResourceLocation)new ResourceLocation("mocreatures:wyvernlairworld"))) {
                        dimensionCriteria = true;
                    }
                    if (!dimensionCriteria) {
                        return false;
                    }
                    if (random.nextInt(1000000) + 1 <= 40000) {
                        int count = random.nextInt(1) + 1;
                        for (int a = 0; a < count; ++a) {
                            int i = ci + random.nextInt(16);
                            int k = ck + random.nextInt(16);
                            int j = world.getHeight(Heightmap.Type.OCEAN_FLOOR_WG, i, k);
                            BlockState blockAt = world.getBlockState(new BlockPos(i, --j, k));
                            boolean blockCriteria = false;
                            if (blockAt.getBlock() == MoCBlocks.wyvgrass) {
                                blockCriteria = true;
                            }
                            if (!blockCriteria) continue;
                            Rotation rotation = Rotation.values()[random.nextInt(3)];
                            Mirror mirror = Mirror.values()[random.nextInt(2)];
                            BlockPos spawnTo = new BlockPos(i + 0, j + 1, k + 0);
                            int x = spawnTo.getX();
                            int y = spawnTo.getY();
                            int z = spawnTo.getZ();
                            Template template = world.getWorld().getStructureTemplateManager().getTemplate(new ResourceLocation("mocreatures", "lake2"));
                            if (template == null) {
                                return false;
                            }
                            template.func_237144_a_((IServerWorld)world, spawnTo, new PlacementSettings().setRotation(rotation).setRandom(random).setMirror(mirror).addProcessor((StructureProcessor) BlockIgnoreStructureProcessor.AIR_AND_STRUCTURE_BLOCK).setChunk(null).setIgnoreEntities(false), random);
                        }
                    }
                    return true;
                }
            };
            configuredFeature = feature.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG));
            event.getRegistry().register(feature.setRegistryName("lake_2"));
            Registry.register((Registry) WorldGenRegistries.CONFIGURED_FEATURE, (ResourceLocation)new ResourceLocation("mocreatures:lake_2"), (Object)configuredFeature);
        }
    }
}