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

public class MoCEntityCod extends MoCEntityMediumFish {

    public MoCEntityCod(EntityType<? extends MoCEntityCod> type, World world) {
        super(type, world);
        this.setTypeMoC(2);
    }

    @Override
    public ResourceLocation getTexture() {
        return MoCreatures.proxy.getModelTexture("mediumfish_cod.png");
    }

    @Nullable
    protected ResourceLocation getLootTable() {        return MoCLootTables.COD;
    }
}
