///*
// * GNU GENERAL PUBLIC LICENSE Version 3
// */
//package drzhark.mocreatures.dimension.chunk;
//
//import drzhark.mocreatures.MoCTools;
//import drzhark.mocreatures.init.MoCBlocks;
//import net.minecraft.block.Block;
//import net.minecraft.block.BlockFalling;
//import net.minecraft.block.Blocks;
//import net.minecraft.entity.EntityClassification;
//import net.minecraft.entity.MobEntity;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.world.World;
//import net.minecraft.world.biome.Biome;
//import net.minecraft.world.chunk.Chunk;
//import net.minecraft.world.chunk.ChunkPrimer;
//import net.minecraft.world.gen.IChunkGenerator;
//import net.minecraft.world.gen.MapGenCaves;
//import net.minecraft.world.gen.NoiseGeneratorOctaves;
//import net.minecraft.world.gen.feature.WorldGenLakes;
//import net.minecraftforge.event.ForgeEventFactory;
//
//import java.util.List;
//import java.util.Random;
//
//public class MoCChunkGeneratorWyvernSkylandsOld implements IChunkGenerator {
//    private final World world;
//    private final Random rand;
//    private final NoiseGeneratorOctaves terrainNoise1;
//    private final NoiseGeneratorOctaves terrainNoise2;
//    private final NoiseGeneratorOctaves terrainNoise3;
//    private final NoiseGeneratorOctaves biomeBlocksNoise;
//    private final MapGenCaves mapGenCaves = new MapGenCaves();
//
//    public MoCChunkGeneratorWyvernSkylandsOld(World world, long seed) {
//        this.world = world;
//        this.rand = new Random(seed);
//        this.terrainNoise1 = new NoiseGeneratorOctaves(rand, 16);
//        this.terrainNoise2 = new NoiseGeneratorOctaves(rand, 16);
//        this.terrainNoise3 = new NoiseGeneratorOctaves(rand, 8);
//        this.biomeBlocksNoise = new NoiseGeneratorOctaves(rand, 4);
//    }
//
//    @Override
//    public Chunk generateChunk(int chunkX, int chunkZ) {
//        ChunkPrimer primer = new ChunkPrimer();
//        Biome[] biomes = world.getBiomeProvider().getBiomes(new Biome[0], chunkX * 16, chunkZ * 16, 16, 16);
//        setBlocksInChunk(chunkX, chunkZ, primer);
//        replaceBiomeBlocks(chunkX, chunkZ, primer, biomes);
//        mapGenCaves.generate(world, chunkX, chunkZ, primer);
//        Chunk chunk = new Chunk(world, primer, chunkX, chunkZ);
//        chunk.generateSkylightMap();
//        return chunk;
//    }
//
//    private double[] getNoiseArray(int xOffset, int yOffset, int zOffset, int xSize, int ySize, int zSize) {
//        double[] noiseArray = new double[xSize * ySize * zSize];
//        double hScale = 684.41200000000003D * 2D;
//        double vScale = 684.41200000000003D;
//        double[] terrainNoiseArray1 = terrainNoise1.generateNoiseOctaves(null, xOffset, yOffset, zOffset, xSize, ySize, zSize, hScale, vScale, hScale);
//        double[] terrainNoiseArray2 = terrainNoise2.generateNoiseOctaves(null, xOffset, yOffset, zOffset, xSize, ySize, zSize, hScale, vScale, hScale);
//        double[] terrainNoiseArray3 = terrainNoise3.generateNoiseOctaves(null, xOffset, yOffset, zOffset, xSize, ySize, zSize, hScale / 80D, vScale / 160D, hScale / 80D);
//        int index = 0;
//        for (int x = 0; x < xSize; x++) {
//            for (int z = 0; z < zSize; z++) {
//                for (int y = 0; y < ySize; y++) {
//                    double terrainNoiseValue1 = terrainNoiseArray1[index] / 512D;
//                    double terrainNoiseValue2 = terrainNoiseArray2[index] / 512D;
//                    double terrainNoiseValue3 = (terrainNoiseArray3[index] / 10D + 1.0D) / 2D;
//                    double noise;
//                    if (terrainNoiseValue3 < 0.0D) noise = terrainNoiseValue1;
//                    else if (terrainNoiseValue3 > 1.0D) noise = terrainNoiseValue2;
//                    else noise = terrainNoiseValue1 + (terrainNoiseValue2 - terrainNoiseValue1) * terrainNoiseValue3;
//                    noise -= 8D;
//                    if (y > ySize - 32) {
//                        double multiplicator = (y - (ySize - 32)) / (32 - 1.0);
//                        noise = noise * (1.0 - multiplicator) + -30 * multiplicator;
//                    }
//                    if (y < 8) {
//                        double multiplicator = (8 - y) / (8 - 1.0);
//                        noise = noise * (1.0 - multiplicator) + -30 * multiplicator;
//                    }
//                    noiseArray[index] = noise;
//                    index++;
//                }
//            }
//        }
//        return noiseArray;
//    }
//
//    private void setBlocksInChunk(int chunkX, int chunkZ, ChunkPrimer primer) {
//        double[] noiseArray = getNoiseArray(chunkX * 2, 0, chunkZ * 2, 3, 33, 3);
//        for (int i1 = 0; i1 < 2; i1++) {
//            for (int j1 = 0; j1 < 2; j1++) {
//                for (int k1 = 0; k1 < 32; k1++) {
//                    double noiseOffset1 = noiseArray[(i1 * 3 + j1) * 33 + k1];
//                    double noiseOffset2 = noiseArray[(i1 * 3 + (j1 + 1)) * 33 + k1];
//                    double noiseOffset3 = noiseArray[((i1 + 1) * 3 + j1) * 33 + k1];
//                    double noiseOffset4 = noiseArray[((i1 + 1) * 3 + (j1 + 1)) * 33 + k1];
//                    double noiseIncreaseScale = 0.25D;
//                    double noiseIncrease1 = (noiseArray[(i1 * 3 + j1) * 33 + (k1 + 1)] - noiseOffset1) * noiseIncreaseScale;
//                    double noiseIncrease2 = (noiseArray[(i1 * 3 + (j1 + 1)) * 33 + (k1 + 1)] - noiseOffset2) * noiseIncreaseScale;
//                    double noiseIncrease3 = (noiseArray[((i1 + 1) * 3 + j1) * 33 + (k1 + 1)] - noiseOffset3) * noiseIncreaseScale;
//                    double noiseIncrease4 = (noiseArray[((i1 + 1) * 3 + (j1 + 1)) * 33 + (k1 + 1)] - noiseOffset4) * noiseIncreaseScale;
//                    for (int l1 = 0; l1 < 4; l1++) {
//                        double noiseIncrease2Scale = 0.125D;
//                        double noiseValue1 = noiseOffset1;
//                        double noiseValue2 = noiseOffset2;
//                        double noiseIncrease21 = (noiseOffset3 - noiseOffset1) * noiseIncrease2Scale;
//                        double noiseIncrease22 = (noiseOffset4 - noiseOffset2) * noiseIncrease2Scale;
//                        for (int i2 = 0; i2 < 8; i2++) {
//                            int x = j1 * 8;
//                            int y = k1 * 4 + l1;
//                            int z = i2 + i1 * 8;
//                            double terrainDensityIncreaseScale = 0.125D;
//                            double terrainDensity = noiseValue1;
//                            double terrainDensityIncrease = (noiseValue2 - noiseValue1) * terrainDensityIncreaseScale;
//                            for (int j2 = 0; j2 < 8; j2++) {
//                                primer.setBlockState(z, y, x, terrainDensity > 0.0D ? MoCBlocks.wyvstone.getDefaultState() : Blocks.AIR.getDefaultState());
//                                x++;
//                                terrainDensity += terrainDensityIncrease;
//                            }
//                            noiseValue1 += noiseIncrease21;
//                            noiseValue2 += noiseIncrease22;
//                        }
//                        noiseOffset1 += noiseIncrease1;
//                        noiseOffset2 += noiseIncrease2;
//                        noiseOffset3 += noiseIncrease3;
//                        noiseOffset4 += noiseIncrease4;
//                    }
//                }
//            }
//        }
//    }
//
//    private void replaceBiomeBlocks(int xChunk, int zChunk, ChunkPrimer primer, Biome[] biomes) {
//        double scale = 0.03125D;
//        double[] biomeBlocksNoiseArray = biomeBlocksNoise.generateNoiseOctaves(new double[256], xChunk * 16, zChunk * 16, 0, 16, 16, 1, scale * 2D, scale * 2D, scale * 2D);
//        for (int x = 0; x < 16; x++) {
//            for (int z = 0; z < 16; z++) {
//                Biome biome = biomes[x + z * 16];
//                int biomeBlocksNoiseValue = (int) (biomeBlocksNoiseArray[x + z * 16] / 3D + 3D + rand.nextDouble() * 0.25D);
//                int biomeBlocksLeft = -1;
//                Block topBlock = biome.topBlock.getBlock();
//                Block fillerBlock = biome.fillerBlock.getBlock();
//                for (int y = 127; y >= 0; y--) {
//                    Block block = primer.getBlockState(x, y, z).getBlock();
//                    if (block == Blocks.AIR) biomeBlocksLeft = -1;
//                    else if (block == MoCBlocks.wyvstone) {
//                        if (biomeBlocksLeft == -1) {
//                            if (biomeBlocksNoiseValue <= 0) {
//                                topBlock = Blocks.AIR;
//                                fillerBlock = Blocks.STONE;
//                            }
//                            biomeBlocksLeft = biomeBlocksNoiseValue;
//                            primer.setBlockState(x, y, z, y >= 0 ? topBlock.getDefaultState() : fillerBlock.getDefaultState());
//                        } else if (biomeBlocksLeft > 0) {
//                            biomeBlocksLeft--;
//                            primer.setBlockState(x, y, z, fillerBlock.getDefaultState());
//                            if (biomeBlocksLeft == 0 && fillerBlock == Blocks.SAND) {
//                                biomeBlocksLeft = rand.nextInt(4);
//                                fillerBlock = Blocks.SANDSTONE;
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }
//
//    @Override
//    public void populate(int x, int z) {
//        BlockFalling.fallInstantly = true;
//        ForgeEventFactory.onChunkPopulate(true, this, world, rand, x, z, false);
//        int var4 = x * 16;
//        int var5 = z * 16;
//        BlockPos chunkWorldPos = new BlockPos(var4, 0, var5);
//        Biome biome = world.getBiome(chunkWorldPos.add(16, 0, 16));
//        boolean var11 = false;
//        int var12;
//        int var13;
//        int var14;
//        if (!var11 && rand.nextInt(2) == 0) {
//            var12 = var4 + rand.nextInt(16) + 8;
//            var13 = rand.nextInt(128);
//            var14 = var5 + rand.nextInt(16) + 8;
//            (new WorldGenLakes(Blocks.WATER)).generate(world, rand, new BlockPos(var12, var13, var14));
//        }
//        if (!var11 && rand.nextInt(8) == 0) {
//            var12 = var4 + rand.nextInt(16) + 8;
//            var13 = rand.nextInt(rand.nextInt(120) + 8);
//            var14 = var5 + rand.nextInt(16) + 8;
//            if (var13 < 63 || rand.nextInt(10) == 0) {
//                (new WorldGenLakes(Blocks.LAVA)).generate(world, rand, new BlockPos(var12, var13, var14));
//            }
//        }
//        biome.decorate(world, rand, new BlockPos(var4, 0, var5));
//        MoCTools.performCustomWorldGenSpawning(world, biome, var4 + 8, var5 + 8, 16, 16, rand, world.getBiome(chunkWorldPos).getSpawnableList(EntityClassification.CREATURE), MobEntity.SpawnPlacementType.ON_GROUND);
//        ForgeEventFactory.onChunkPopulate(false, this, world, rand, x, z, false);
//        BlockFalling.fallInstantly = false;
//    }
//
//    @Override
//    public boolean generateStructures(Chunk chunkIn, int x, int z) {
//        return false;
//    }
//
//    @Override
//    public List<Biome.MobSpawnInfo.Spawners> getPossibleCreatures(EntityClassification creatureType, BlockPos pos) {
//        return world.getBiome(pos).getSpawnableList(creatureType);
//    }
//
//    @Override
//    public BlockPos getNearestStructurePos(World worldIn, String structureName, BlockPos position, boolean findUnexplored) {
//        return null;
//    }
//
//    @Override
//    public boolean isInsideStructure(World worldIn, String structureName, BlockPos pos) {
//        return false;
//    }
//
//    @Override
//    public void recreateStructures(Chunk chunkIn, int x, int z) {
//    }
//}
