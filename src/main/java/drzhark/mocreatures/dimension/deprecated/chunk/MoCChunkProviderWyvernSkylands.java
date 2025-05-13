///*
// * GNU GENERAL PUBLIC LICENSE Version 3
// */
//package drzhark.mocreatures.dimension.chunk;
//
//import net.minecraft.entity.EntityClassification;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.world.World;
//import net.minecraft.world.biome.Biome;
//import net.minecraft.world.chunk.Chunk;
//import net.minecraft.world.chunk.ChunkPrimer;
//import net.minecraft.world.gen.IChunkGenerator;
//
//import java.util.List;
//import java.util.Random;
//
//// Courtesy of lhns
//public class MoCChunkProviderWyvernSkylands implements IChunkGenerator {
//    private final World world;
//    private final Random random;
//    private final MoCTerrainGeneratorWyvernSkylands terrainGenerator;
//
//    public MoCChunkProviderWyvernSkylands(World world) {
//        this.world = world;
//        this.random = new Random(world.getSeed() * 0x4f9939f508L);
//        this.terrainGenerator = new MoCTerrainGeneratorWyvernSkylands(world, random);
//    }
//
//    @Override
//    public Chunk generateChunk(int chunkX, int chunkZ) {
//        ChunkPrimer chunkPrimer = new ChunkPrimer();
//        MoCOffsetChunkPrimer offsetPrimer = new MoCOffsetChunkPrimer(chunkPrimer, 64);
//
//        terrainGenerator.generate(chunkX, chunkZ, offsetPrimer);
//
//        Chunk chunk = new Chunk(world, chunkPrimer, chunkX, chunkZ);
//
//        chunk.generateSkylightMap();
//
//        return chunk;
//    }
//
//    @Override
//    public void populate(int chunkX, int chunkZ) {
//        int x = chunkX * 16;
//        int z = chunkZ * 16;
//
//        BlockPos blockPos = new BlockPos(x, 0, z);
//        Biome biome = world.getBiome(blockPos.add(16, 0, 16));
//
//        terrainGenerator.populate(chunkX, chunkZ);
//    }
//
//    @Override
//    public List<Biome.MobSpawnInfo.Spawners> getPossibleCreatures(EntityClassification creatureType, BlockPos pos) {
//        return world.getBiome(pos).getSpawnableList(creatureType);
//    }
//
//    @Override
//    public void recreateStructures(Chunk chunkIn, int x, int z) {
//        // No implementation needed
//    }
//
//    @Override
//    public boolean generateStructures(Chunk chunkIn, int x, int z) {
//        return false;
//    }
//
//    @Override
//    public boolean isInsideStructure(World worldIn, String structureName, BlockPos blockPos) {
//        return false;
//    }
//
//    @Override
//    public BlockPos getNearestStructurePos(World worldIn, String structureName, BlockPos blockPos, boolean findUnexplored) {
//        return null;
//    }
//}
