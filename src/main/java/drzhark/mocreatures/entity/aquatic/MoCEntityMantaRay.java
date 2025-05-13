/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.aquatic;

import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.init.MoCLootTables;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.Pose;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class MoCEntityMantaRay extends MoCEntityRay {

    public MoCEntityMantaRay(EntityType<? extends MoCEntityMantaRay> type, World world) {
        super(type, world);
        //setSize(1.4F, 0.4F);
        // TODO: Make hitboxes adjust depending on size
        //setAge(80 + (this.rand.nextInt(100)));
        setAge(180);
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MoCEntityRay.registerAttributes().createMutableAttribute(Attributes.MAX_HEALTH, 20.0D);
    }

    @Override
    public int getMaxAge() {
        return 180;
    }

    @Override
    public ResourceLocation getTexture() {
        return MoCreatures.proxy.getModelTexture("ray_manta.png");
    }

    @Nullable
    protected ResourceLocation getLootTable() {        return MoCLootTables.MANTA_RAY;
    }

    @Override
    public boolean isMantaRay() {
        return true;
    }

    protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return this.getHeight() * 0.5875F;
    }
}
