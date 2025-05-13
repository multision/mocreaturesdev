///*
// * GNU GENERAL PUBLIC LICENSE Version 3
// */
//package drzhark.mocreatures.dimension.biome;
//
//import drzhark.mocreatures.init.MoCBiomes;
//import net.minecraft.world.World;
//import net.minecraft.world.WorldType;
//import net.minecraft.world.biome.BiomeProvider;
//import net.minecraft.world.gen.layer.GenLayer;
//import net.minecraft.world.gen.layer.GenLayerVoronoiZoom;
//import net.minecraft.world.gen.layer.GenLayerZoom;
//
//// Courtesy of Dragonisser
//public class MoCBiomeProviderWyvernSkylands extends BiomeProvider {
//
//    public MoCBiomeProviderWyvernSkylands(World world) {
//        super(world.getWorldInfo());
//        allowedBiomes.clear();
//        allowedBiomes.add(MoCBiomes.wyvernIsles);
//        allowedBiomes.add(MoCBiomes.wyvernDesertIsles);
//
//        getBiomesToSpawnIn().clear();
//        getBiomesToSpawnIn().add(MoCBiomes.wyvernIsles);
//    }
//
//    @Override
//    public GenLayer[] getModdedBiomeGenerators(WorldType worldType, long seed, GenLayer[] original) {
//        GenLayer biomes = new MoCGenLayerBiomesWyvernSkylands(1);
//
//        biomes = new MoCGenLayerBiomesWyvernSkylands(1000, biomes);
//        biomes = new GenLayerZoom(1000, biomes);
//        biomes = new GenLayerZoom(1001, biomes);
//        biomes = new GenLayerZoom(1002, biomes);
//        biomes = new GenLayerZoom(1003, biomes);
//        biomes = new GenLayerZoom(1004, biomes);
//
//        GenLayer biomeIndexLayer = new GenLayerVoronoiZoom(10L, biomes);
//        biomeIndexLayer.initWorldGenSeed(seed);
//
//        return new GenLayer[]{biomes, biomeIndexLayer};
//    }
//}