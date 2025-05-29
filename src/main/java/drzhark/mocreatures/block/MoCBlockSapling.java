/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.block;

import drzhark.mocreatures.dimension.worldgen.WyvernTreeGrower;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.trees.Tree;

public class MoCBlockSapling extends SaplingBlock{

    public MoCBlockSapling(AbstractBlock.Properties properties) {
        //super(treeIn, properties.sound(SoundType.PLANT).doesNotBlockMovement().tickRandomly());
        super(new WyvernTreeGrower(), AbstractBlock.Properties.create(Material.PLANTS)
                .tickRandomly()
                .doesNotBlockMovement()
                .zeroHardnessAndResistance()
                .sound(SoundType.PLANT));
    }
}
