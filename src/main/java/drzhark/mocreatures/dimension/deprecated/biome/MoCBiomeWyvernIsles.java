

///*
// * GNU GENERAL PUBLIC LICENSE Version 3
// */
//package drzhark.mocreatures.dimension.biome;
//
//import drzhark.mocreatures.dimension.worldgen.MoCWorldGenBigTree;
//import drzhark.mocreatures.dimension.worldgen.MoCWorldGenWyvernGrass;
//import drzhark.mocreatures.dimension.worldgen.MoCWorldGenWyvernNest;
//import drzhark.mocreatures.entity.ambient.MoCEntityDragonfly;
//import drzhark.mocreatures.entity.hunter.MoCEntitySnake;
//import drzhark.mocreatures.entity.neutral.MoCEntityWyvern;
//import drzhark.mocreatures.entity.passive.MoCEntityBunny;
//import drzhark.mocreatures.init.MoCBlocks;
//import net.minecraft.block.material.Material;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.world.World;
//import net.minecraft.world.biome.Biome;
//import net.minecraft.world.biome.MobSpawnInfo;
//import net.minecraftforge.api.distmarker.Dist;
//import net.minecraftforge.api.distmarker.OnlyIn;
//
//import java.util.Random;
//
//public class MoCBiomeWyvernIsles extends Biome {
//
//    private final MoCWorldGenBigTree wyvernGenBigTree;
//    private final WorldGenShrub worldGenShrub;
//
//    @SuppressWarnings("deprecation")
//    public MoCBiomeWyvernIsles(Biome.BiomeProperties biomeProperties) {
//        super(biomeProperties);
//        this.spawnableCreatureList.clear();
//        this.spawnableMonsterList.clear();
//        this.spawnableWaterCreatureList.clear();
//        this.spawnableCaveCreatureList.clear();
//        this.modSpawnableLists.clear();
//        this.spawnableCreatureList.add(new MobSpawnInfo.Spawners(MoCEntityBunny.class, 6, 2, 4));
//        this.spawnableCreatureList.add(new MobSpawnInfo.Spawners(MoCEntityDragonfly.class, 6, 2, 4));
//        this.spawnableCreatureList.add(new MobSpawnInfo.Spawners(MoCEntitySnake.class, 6, 1, 3));
//        this.spawnableCreatureList.add(new MobSpawnInfo.Spawners(MoCEntityWyvern.class, 12, 2, 3));
//        this.topBlock = MoCBlocks.wyvgrass.getDefaultState();
//        this.fillerBlock = MoCBlocks.wyvdirt.getDefaultState();
//        this.wyvernGenBigTree = new MoCWorldGenBigTree(false, MoCBlocks.wyvwoodLog.getDefaultState(), MoCBlocks.wyvwoodLeaves.getStateFromMeta(0), 2, 30, 10);
//        this.worldGenShrub = new WorldGenShrub(MoCBlocks.wyvwoodLog.getDefaultState(), MoCBlocks.wyvwoodLeaves.getDefaultState());
//        this.decorator = new MoCBiomeWyvernIslesDecorator();
//    }
//
//    @Override
//    public WorldGenAbstractTree getRandomTreeFeature(Random par1Random) {
//        if (par1Random.nextInt(10) == 0) {
//            return this.wyvernGenBigTree;
//        } else {
//            return this.worldGenShrub;
//        }
//    }
//
//    @Override
//    public WorldGenerator getRandomWorldGenForGrass(Random par1Random) {
//        return new MoCWorldGenWyvernGrass(MoCBlocks.tallWyvgrass.getDefaultState());
//    }
//
//    @Override
//    public void decorate(World world, Random random, BlockPos pos) {
//        super.decorate(world, random, pos);
//
//        if (random.nextInt(100) == 0) {
//            int i = random.nextInt(16) + 8;
//            int j = random.nextInt(16) + 8;
//            BlockPos blockpos = world.getHeight(pos.add(i, 0, j));
//            if (world.getBlockState(blockpos.down()).getMaterial() != Material.AIR) {
//                (new MoCWorldGenWyvernNest()).generate(world, random, blockpos);
//            }
//        }
//    }
//
//    @Override
//    @OnlyIn(Dist.CLIENT)
//    public int getModdedBiomeFoliageColor(final int original) {
//        return 0x4F4569;
//    }
//
//    @Override
//    @OnlyIn(Dist.CLIENT)
//    public int getModdedBiomeGrassColor(final int original) {
//        return 0x4F4569;
//    }
//
//    @Override
//    @OnlyIn(Dist.CLIENT)
//    public int getSkyColorByTemp(final float currentTemperature) {
//        return 0x8C95FF;
//    }
//}
