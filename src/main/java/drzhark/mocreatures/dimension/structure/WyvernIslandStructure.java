package drzhark.mocreatures.dimension.structure;

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
public class WyvernIslandStructure {
    private static final String[] STRUCTURE_NAMES = {"wyvernisland", "wyvernisland2", "wyvernisland3", "wyvernisland4"};

    @SubscribeEvent
    public static void addFeatureToBiomes(BiomeLoadingEvent event) {
        for (String name : STRUCTURE_NAMES) {
            ConfiguredFeature<?, ?> feature = WorldGenRegistries.CONFIGURED_FEATURE.getOrDefault(new ResourceLocation("mocreatures:" + name));
            if (feature != null) {
                event.getGeneration().getFeatures(GenerationStage.Decoration.RAW_GENERATION).add(() -> feature);
            }
        }
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    private static class FeatureRegisterHandler {

        @SubscribeEvent
        public static void registerFeature(RegistryEvent.Register<Feature<?>> event) {
            for (String name : STRUCTURE_NAMES) {
                Feature<NoFeatureConfig> feature = new Feature<NoFeatureConfig>(NoFeatureConfig.CODEC) {
                    @Override
                    public boolean generate(ISeedReader world, ChunkGenerator generator, Random random, BlockPos pos, NoFeatureConfig config) {
                        RegistryKey<?> dimensionType = world.getWorld().getDimensionKey();
                        if (!dimensionType.equals(RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation("mocreatures:wyvernlairworld")))) {
                            return false;
                        }

                        if (random.nextInt(1000000) + 1 <= 30000) {
                            int ci = pos.getX() >> 4 << 4;
                            int ck = pos.getZ() >> 4 << 4;
                            int count = random.nextInt(1) + 1;

                            for (int a = 0; a < count; ++a) {
                                int i = ci + random.nextInt(16);
                                int k = ck + random.nextInt(16);
                                int j = world.getHeight(Heightmap.Type.OCEAN_FLOOR_WG, i, k);
                                j += random.nextInt(64) + 16;
                                Rotation rotation = Rotation.values()[random.nextInt(3)];
                                Mirror mirror = Mirror.values()[random.nextInt(2)];
                                BlockPos spawnTo = new BlockPos(i, j, k);

                                Template template = world.getWorld().getStructureTemplateManager().getTemplate(new ResourceLocation("mocreatures", name));
                                if (template == null) return false;

                                template.func_237144_a_((IServerWorld) world, spawnTo,
                                        new PlacementSettings().setRotation(rotation).setRandom(random).setMirror(mirror)
                                                .addProcessor(BlockIgnoreStructureProcessor.AIR_AND_STRUCTURE_BLOCK)
                                                .setChunk(null).setIgnoreEntities(false), random);
                            }
                        }
                        return true;
                    }
                };

                ConfiguredFeature<?, ?> configuredFeature = feature.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG)
                        .withPlacement(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG));

                event.getRegistry().register(feature.setRegistryName(name));
                Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, new ResourceLocation("mocreatures", name), configuredFeature);
            }
        }
    }
}
