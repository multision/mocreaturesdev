/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.aquatic;

import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.init.MoCLootTables;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class MoCEntityAnchovy extends MoCEntitySmallFish {

    public MoCEntityAnchovy(EntityType<? extends MoCEntityAnchovy> type, World world) {
        super(type, world);
        this.setTypeMoC(1);
    }

    @Override
    public ResourceLocation getTexture() {
        return MoCreatures.proxy.getModelTexture("smallfish_anchovy.png");
    }

    @Nullable
    protected ResourceLocation getLootTable() {        return MoCLootTables.ANCHOVY;
    }
}
