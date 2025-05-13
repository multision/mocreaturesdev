/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.model;

import drzhark.mocreatures.entity.hostile.MoCEntityScorpion;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCModelScorpion<T extends MoCEntityScorpion> extends MoCModelAbstractScorpion<T> {

    @Override
    public void setLivingAnimations(T entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
        poisoning = entityIn.swingingTail();
        isAdult = entityIn.getIsAdult();
        isTalking = entityIn.mouthCounter != 0;
        babies = entityIn.getHasBabies();
        attacking = entityIn.armCounter;
    }
}
