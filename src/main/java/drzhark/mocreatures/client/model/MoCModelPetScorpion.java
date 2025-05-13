/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.model;

import drzhark.mocreatures.entity.hunter.MoCEntityPetScorpion;


public class MoCModelPetScorpion<T extends MoCEntityPetScorpion> extends MoCModelAbstractScorpion<T> {

    @Override
    public void setLivingAnimations(T entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
        poisoning = entityIn.swingingTail();
        isTalking = entityIn.mouthCounter != 0;
        babies = entityIn.getHasBabies();
        attacking = entityIn.armCounter;
        sitting = entityIn.getIsSitting();
    }
}
