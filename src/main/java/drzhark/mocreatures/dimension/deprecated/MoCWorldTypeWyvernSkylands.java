///*
// * GNU GENERAL PUBLIC LICENSE Version 3
// */
//package drzhark.mocreatures.dimension;
//
//import drzhark.mocreatures.dimension.biome.MoCBiomeProviderWyvernSkylands;
//import drzhark.mocreatures.dimension.chunk.MoCChunkProviderWyvernSkylands;
//import net.minecraft.world.World;
//import net.minecraft.world.WorldType;
//import net.minecraft.world.biome.BiomeProvider;
//import net.minecraft.world.gen.IChunkGenerator;
//
//@SuppressWarnings("unused")
//public class MoCWorldTypeWyvernSkylands extends WorldType {
//
//    public MoCWorldTypeWyvernSkylands() {
//        super("wyvern_skylands");
//    }
//
//    @Override
//    public BiomeProvider getBiomeProvider(World world) {
//        return new MoCBiomeProviderWyvernSkylands(world);
//    }
//
//    @Override
//    public IChunkGenerator getChunkGenerator(World world, String generatorOptions) {
//        return new MoCChunkProviderWyvernSkylands(world);
//    }
//}
