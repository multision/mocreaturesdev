/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.model;

import drzhark.mocreatures.entity.hunter.MoCEntityBigCat;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCModelBigCat<T extends MoCEntityBigCat> extends MoCModelAbstractBigCat<T> {

    public void setLivingAnimations(T entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
        this.bigcat =  entityIn;
        this.isFlyer = bigcat.isFlyer();
        this.isSaddled = bigcat.getIsRideable();
        this.flapwings = (bigcat.wingFlapCounter != 0);
        this.onAir = (bigcat.isOnAir());
        this.floating = (this.isFlyer && this.onAir);
        //this.poisoning = bigcat.swingingTail();
        this.openMouthCounter = bigcat.mouthCounter;
        this.isRidden = (bigcat.isBeingRidden());
        this.hasMane = bigcat.hasMane();
        this.isTamed = bigcat.getHasAmulet();
        this.isSitting = bigcat.getIsSitting();
        this.movingTail = bigcat.tailCounter != 0;
        this.hasSaberTeeth = bigcat.hasSaberTeeth();
        this.hasChest = bigcat.getIsChested();
        this.hasStinger = bigcat.getHasStinger();
        this.isGhost = bigcat.getIsGhost();
        this.isMovingVertically = bigcat.getMotion().getY() != 0 && !bigcat.isOnGround();
    }

    public float updateGhostTransparency() {
        return bigcat.tFloat();
    }
}