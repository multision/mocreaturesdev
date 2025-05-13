package drzhark.mocreatures.dimension.biome;

import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.init.MoCBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.blockplacer.BlockPlacer;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.BlockStateProvider;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.BlockClusterFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "mocreatures", bus = Mod.EventBusSubscriber.Bus.MOD)
public class MoCBiomes {

    public static Biome WYVERN_LAIR_FOREST;
    public static Biome WYVERN_LAIR_LANDS;
    public static Biome WYVERN_LAIR_MOUNTAINS;
    public static Biome WYVERN_LAIR_DESERT;

    @SubscribeEvent
    public static void registerBiomes(RegistryEvent.Register<Biome> event) {
        WYVERN_LAIR_FOREST = registerBiome(event, "wyvernlairlandsforest", -13434625);
        WYVERN_LAIR_LANDS = registerBiome(event, "wyvernlairlands", -13434625);
        WYVERN_LAIR_MOUNTAINS = registerBiome(event, "wyvernlair_mountains", -13434625, 1.5f, 2.0f);
        WYVERN_LAIR_DESERT = registerBiome(event, "wyvernlair_desertlands", -13434625, 0.1f, 0.2f, MoCBlocks.silverSand.getDefaultState(), MoCBlocks.silverSandstone.getDefaultState(), Blocks.WATER.getDefaultState());
    }

    private static Biome registerBiome(RegistryEvent.Register<Biome> event, String name, int fogColor) {
        return registerBiome(event, name, fogColor, 0.1f, 0.2f);
    }

    private static Biome registerBiome(RegistryEvent.Register<Biome> event, String name, int fogColor, float depth, float scale) {
        return registerBiome(event, name, fogColor, depth, scale, MoCBlocks.wyvgrass.getDefaultState(), MoCBlocks.wyvdirt.getDefaultState(), MoCBlocks.wyvstone.getDefaultState());
    }

    private static Biome registerBiome(RegistryEvent.Register<Biome> event, String name, int fogColor, float depth, float scale,
                                       net.minecraft.block.BlockState top, net.minecraft.block.BlockState middle, net.minecraft.block.BlockState bottom) {
        BiomeAmbience effects = new BiomeAmbience.Builder()
                .setFogColor(fogColor)
                .setWaterColor(-13421569)
                .setWaterFogColor(fogColor)
                .withSkyColor(fogColor)
                .withFoliageColor(-13395457)
                .withGrassColor(fogColor)
                .build();

        BiomeGenerationSettings.Builder generationSettings = new BiomeGenerationSettings.Builder()
                .withSurfaceBuilder(SurfaceBuilder.DEFAULT.func_242929_a(new SurfaceBuilderConfig(top, middle, bottom)))
                .withFeature(GenerationStage.Decoration.VEGETAL_DECORATION,
                        Feature.RANDOM_PATCH.withConfiguration(new BlockClusterFeatureConfig.Builder(
                                (BlockStateProvider) new SimpleBlockStateProvider(Blocks.BROWN_MUSHROOM.getDefaultState()), SimpleBlockPlacer.PLACER)
                                .tries(2).preventProjection().build()))
                .withFeature(GenerationStage.Decoration.VEGETAL_DECORATION,
                        Feature.RANDOM_PATCH.withConfiguration(new BlockClusterFeatureConfig.Builder(
                                (BlockStateProvider) new SimpleBlockStateProvider(Blocks.RED_MUSHROOM.getDefaultState()), SimpleBlockPlacer.PLACER)
                                .tries(2).preventProjection().build()));

        DefaultBiomeFeatures.withCavesAndCanyons(generationSettings);
        DefaultBiomeFeatures.withOverworldOres(generationSettings);
        DefaultBiomeFeatures.withFrozenTopLayer(generationSettings);

        MobSpawnInfo.Builder mobSpawnInfo = new MobSpawnInfo.Builder().isValidSpawnBiomeForPlayer();

        Biome biome = new Biome.Builder()
                .precipitation(Biome.RainType.RAIN)
                .category(Biome.Category.NONE)
                .depth(depth)
                .scale(scale)
                .temperature(1.0f)
                .downfall(0.2f)
                .setEffects(effects)
                .withMobSpawnSettings(mobSpawnInfo.build())
                .withGenerationSettings(generationSettings.build())
                .build();

        biome.setRegistryName(new ResourceLocation("mocreatures", name));
        event.getRegistry().register(biome);
        return biome;
    }
}
