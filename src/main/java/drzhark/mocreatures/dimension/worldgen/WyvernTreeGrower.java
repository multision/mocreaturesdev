package drzhark.mocreatures.dimension.worldgen;

import drzhark.mocreatures.dimension.worldgen.features.MoCFeatures;
import net.minecraft.block.trees.Tree;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;

import java.util.Random;

public class WyvernTreeGrower extends Tree {
    /*@Override
    protected ConfiguredFeature<BaseTreeFeatureConfig, ?> getTreeFeature(Random rand, boolean hive) {
        return MoCFeatures.WYVERN_TREE_SPRUCE;
    }*/
    @Override
    protected ConfiguredFeature<BaseTreeFeatureConfig, ?> getTreeFeature(Random rand, boolean hive) {
        if (rand.nextInt(3) == 0) {
            return MoCFeatures.WYVERN_TREE_MEGA_OAK; // 1 in 3 chance for mega oak style
        }
        return MoCFeatures.WYVERN_TREE_SPRUCE; // default spruce-style tree
    }
}
