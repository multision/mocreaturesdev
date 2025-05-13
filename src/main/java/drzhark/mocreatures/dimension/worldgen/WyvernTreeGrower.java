package drzhark.mocreatures.dimension.worldgen;

import net.minecraft.block.trees.Tree;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;

import java.util.Random;

public class WyvernTreeGrower extends Tree {
    @Override
    protected ConfiguredFeature<BaseTreeFeatureConfig, ?> getTreeFeature(Random rand, boolean hive) {
        return MoCFeatures.WYVERN_TREE_FEATURE;
    }
}
