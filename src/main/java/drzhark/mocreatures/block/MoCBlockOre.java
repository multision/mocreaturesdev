/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.block;

import drzhark.mocreatures.init.MoCBlocks;
import drzhark.mocreatures.init.MoCItems;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.Random;

public class MoCBlockOre extends Block {

    public MoCBlockOre(AbstractBlock.Properties properties) {
        super(properties.sound(SoundType.STONE));
    }

    @Override
    public int getExpDrop(BlockState state, net.minecraft.world.IWorldReader reader, BlockPos pos, int fortune, int silktouch) {
        return silktouch == 0 ? this.getExperience(RANDOM) : 0;
    }
    public int getExperience(Random rand) {
        if (this == MoCBlocks.ancientOre) {
            return MathHelper.nextInt(rand, 2, 5);
        } else if (this == MoCBlocks.wyvernDiamondOre) {
            return MathHelper.nextInt(rand, 4, 8);
        } else if (this == MoCBlocks.wyvernEmeraldOre) {
            return MathHelper.nextInt(rand, 4, 8);
        } else if (this == MoCBlocks.wyvernLapisOre) {
            return MathHelper.nextInt(rand, 3, 6);
        } else {
            return 0;
        }
}
}
