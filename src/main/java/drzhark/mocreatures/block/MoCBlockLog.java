/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.block.SoundType;

public class MoCBlockLog extends RotatedPillarBlock {

    public MoCBlockLog(AbstractBlock.Properties properties) {
        super(properties.sound(SoundType.WOOD));
    }
}
