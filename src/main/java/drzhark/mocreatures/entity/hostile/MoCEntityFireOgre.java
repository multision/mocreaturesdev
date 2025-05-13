/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.hostile;

import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.init.MoCLootTables;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class MoCEntityFireOgre extends MoCEntityOgre {

    public MoCEntityFireOgre(EntityType<? extends MoCEntityFireOgre> type, World world) {
        super(type, world);
        //this.isImmuneToFire = true;
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MoCEntityOgre.registerAttributes().createMutableAttribute(Attributes.MAX_HEALTH, 65.0D).createMutableAttribute(Attributes.ARMOR, 9.0D).createMutableAttribute(Attributes.ATTACK_DAMAGE, 7.5D);
    }

    @Override
    public ResourceLocation getTexture() {
        return MoCreatures.proxy.getModelTexture("ogre_fire.png");
    }

    @Override
    public boolean isFireStarter() {
        return true;
    }

    @Override
    public float getDestroyForce() {
        return MoCreatures.proxy.ogreFireStrength;
    }

    @Override
    public void livingTick() {
        super.livingTick();

        if (this.isWet()) {
            this.attackEntityFrom(DamageSource.DROWN, 1.0F);
        }
    }

    @Nullable
    protected ResourceLocation getLootTable() {        return MoCLootTables.FIRE_OGRE;
    }
}
