/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
// TODO
// open mouth animation (done)
// eating animation
// change mane to the right center (done)
// change mule ears to the right center (done)
// bridles to be different when not ridden (done)
// legsaddles to extend when ridden (done)
// butterfly wings animation
// pegasus wing animation (done)
// pegasus resting wings (done)
// butterfly resting wings
// special fx for fairy horses (magic items), zombie horses(flies), ghost horses
// (?), unicorns (?), pegasus (?)
// articulated legs (done)
// frisky horse animation (done)
// stay animation (neck down) (done)
// when flying, the legs should stop moving and flip backwards or be flexed at
// the knee (done)

// flying pegasus:
// about 15-20 degrees horizontal
// about 45 when abduction
// about -15 when adduction
// if flying and not flapping, keep wings horizontal with small tremor and
// perhaps variation of elbow extension
// when user jumps, flap wings fully
// flap wings rarely as well

package drzhark.mocreatures.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import drzhark.mocreatures.entity.passive.MoCEntityHorse;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCModelHorse<T extends MoCEntityHorse> extends MoCModelAbstractHorse<T> {

    private boolean chested;
    private boolean flyer;
    private boolean isGhost;
    private boolean isUnicorned;
    private float transparency;
    private int vanishingInt;
    private int wingflapInt;
    private boolean openMouth;

    public void setLivingAnimations(T entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
        type = entityIn.getTypeMoC();
        vanishingInt = entityIn.getVanishC();
        wingflapInt = entityIn.wingFlapCounter;
        saddled = entityIn.getIsRideable();
        openMouth = (entityIn.mouthCounter != 0);
        rider = (entityIn.isBeingRidden());
        chested = entityIn.getIsChested();
        flyer = entityIn.isFlyer();
        isGhost = entityIn.getIsGhost();
        isUnicorned = entityIn.isUnicorned();
        if (vanishingInt != 0)// && vanishingInt > 30)
        {
            transparency = 1.0F - (((float) (vanishingInt)) / 100);
        } else {
            transparency = entityIn.tFloat();
        }
        flapwings = (entityIn.wingFlapCounter != 0);
        shuffling = (entityIn.shuffleCounter != 0);
        wings = (entityIn.isFlyer() && !entityIn.getIsGhost() && type < 45);
        eating = entityIn.getIsSitting();
        standing = (entityIn.standCounter != 0 && entityIn.getRidingEntity() == null);
        moveTail = (entityIn.tailCounter != 0);
        floating = (entityIn.getIsGhost() || (entityIn.isFlyer() && entityIn.isOnAir()));
        //                || (entityhorse.getRidingEntity() == null && !entityhorse.onGround)
        //                || (entityhorse.isBeingRidden() && !entityhorse.getRidingEntity().onGround));
    }

    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        //MoCEntityHorse entityhorse = (MoCEntityHorse) entity;
        //super.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, f5);

        if (!isGhost && vanishingInt == 0) {
            if (saddled) {
                this.HeadSaddle.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                this.Saddle.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                this.SaddleB.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                this.SaddleC.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                this.SaddleL.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                this.SaddleL2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                this.SaddleR.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                this.SaddleR2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                this.SaddleMouthL.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                this.SaddleMouthR.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                if (rider) {
                    this.SaddleMouthLine.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                    this.SaddleMouthLineR.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                }

            }

            if (type == 65 || type == 66 || type == 67) //mule, donkey or zonkey
            {
                this.MuleEarL.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                this.MuleEarR.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            } else {
                this.Ear1.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                this.Ear2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);

            }
            this.Neck.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            this.Head.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            if (openMouth) {
                this.UMouth2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                this.LMouth2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            } else {
                this.UMouth.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                this.LMouth.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            }
            this.Mane.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            this.Body.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            this.TailA.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            this.TailB.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            this.TailC.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);

            this.Leg1A.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            this.Leg1B.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            this.Leg1C.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);

            this.Leg2A.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            this.Leg2B.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            this.Leg2C.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);

            this.Leg3A.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            this.Leg3B.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            this.Leg3C.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);

            this.Leg4A.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            this.Leg4B.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            this.Leg4C.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);

            if (isUnicorned) {
                this.Unicorn.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            }

            if (chested) {
                this.Bag1.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                this.Bag2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            }

            if (flyer && type < 45) { //pegasus
                this.MidWing.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                this.InnerWing.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                this.OuterWing.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                this.InnerWingR.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                this.MidWingR.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                this.OuterWingR.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            } else if (type > 44 && type < 60) { //fairys
                matrixStackIn.push();
                RenderSystem.enableBlend();
                float transparency = 0.7F;
                RenderSystem.defaultBlendFunc();
                RenderSystem.color4f(1.2F, 1.2F, 1.2F, transparency);
                matrixStackIn.scale(1.3F, 1.0F, 1.3F);
                this.ButterflyL.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                this.ButterflyR.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                RenderSystem.disableBlend();
                matrixStackIn.pop();
            }/*
             * else { ButterflyL.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha); ButterflyR.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha); }
             */

        } else
        //rendering a ghost or vanishing
        {
            matrixStackIn.push();
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.color4f(0.8F, 0.8F, 0.8F, transparency);
            matrixStackIn.scale(1.3F, 1.0F, 1.3F);

            this.Ear1.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            this.Ear2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);

            this.Neck.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            this.Head.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            if (openMouth) {
                this.UMouth2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                this.LMouth2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            } else {
                this.UMouth.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                this.LMouth.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            }

            this.Mane.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            this.Body.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            this.TailA.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            this.TailB.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            this.TailC.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);

            this.Leg1A.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            this.Leg1B.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            this.Leg1C.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);

            this.Leg2A.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            this.Leg2B.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            this.Leg2C.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);

            this.Leg3A.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            this.Leg3B.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            this.Leg3C.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);

            this.Leg4A.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            this.Leg4B.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            this.Leg4C.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);

            if (type == 39 || type == 40 || type == 28) {
                this.MidWing.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                this.InnerWing.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                this.OuterWing.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                this.InnerWingR.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                this.MidWingR.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                this.OuterWingR.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            }
            if (type >= 50 && type < 60) {
                this.ButterflyL.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                this.ButterflyR.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            }

            if (saddled) {
                this.HeadSaddle.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                this.Saddle.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                this.SaddleB.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                this.SaddleC.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                this.SaddleL.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                this.SaddleL2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                this.SaddleR.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                this.SaddleR2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                this.SaddleMouthL.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                this.SaddleMouthR.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                if (rider) {
                    this.SaddleMouthLine.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                    this.SaddleMouthLineR.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                }

            }

            RenderSystem.disableBlend();
            matrixStackIn.pop();

            if (type == 21 || type == 22)//|| (type >=50 && type <60))
            {
                float wingTransparency = 0F;
                if (wingflapInt != 0) {
                    wingTransparency = 1F - (((float) wingflapInt) / 25);
                }
                if (wingTransparency > transparency) {
                    wingTransparency = transparency;
                }
                matrixStackIn.push();
                RenderSystem.enableBlend();
                RenderSystem.defaultBlendFunc();
                RenderSystem.color4f(0.8F, 0.8F, 0.8F, wingTransparency);
                matrixStackIn.scale(1.3F, 1.0F, 1.3F);
                this.ButterflyL.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                this.ButterflyR.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                RenderSystem.disableBlend();
                matrixStackIn.pop();
            }
        }

    }
}
