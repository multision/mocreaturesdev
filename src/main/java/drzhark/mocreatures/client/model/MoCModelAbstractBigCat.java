/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class MoCModelAbstractBigCat<T extends CreatureEntity> extends EntityModel<T> {

    private final float radianF = 57.29578F;
    private final float lLegMov = 0F;
    private final float rLegMov = 0F;
    protected boolean hasMane;
    protected boolean isRidden;
    protected boolean isSaddled;
    protected boolean flapwings;
    protected boolean onAir;
    protected boolean isFlyer;
    protected boolean floating;
    protected boolean poisoning;
    protected boolean isTamed;
    protected boolean movingTail;
    protected int openMouthCounter;
    protected boolean hasSaberTeeth;
    protected boolean hasChest;
    protected boolean hasStinger;
    protected boolean isGhost;
    protected boolean isMovingVertically;
    ModelRenderer RightHindFoot;
    ModelRenderer Stinger;
    ModelRenderer RightHindUpperLeg;
    ModelRenderer RightAnkle;
    ModelRenderer RightHindLowerLeg;
    ModelRenderer Ass;
    ModelRenderer TailTusk;
    ModelRenderer LeftChinBeard;
    ModelRenderer NeckBase;
    ModelRenderer RightEar;
    ModelRenderer LeftEar;
    ModelRenderer ForeheadHair;
    ModelRenderer LeftHarness;
    ModelRenderer RightHarness;
    ModelRenderer LeftUpperLip;
    ModelRenderer RightChinBeard;
    ModelRenderer LeftHindUpperLeg;
    ModelRenderer LeftHindLowerLeg;
    ModelRenderer LeftHindFoot;
    ModelRenderer LeftAnkle;
    ModelRenderer InsideMouth;
    ModelRenderer RightUpperLip;
    ModelRenderer LowerJawTeeth;
    ModelRenderer Nose;
    ModelRenderer LeftFang;
    ModelRenderer UpperTeeth;
    ModelRenderer RightFang;
    ModelRenderer LowerJaw;
    ModelRenderer SaddleFront;
    ModelRenderer LeftUpperLeg;
    ModelRenderer LeftLowerLeg;
    ModelRenderer LeftFrontFoot;
    ModelRenderer LeftClaw2;
    ModelRenderer LeftClaw1;
    ModelRenderer LeftClaw3;
    ModelRenderer RightClaw1;
    ModelRenderer RightClaw2;
    ModelRenderer RightClaw3;
    ModelRenderer RightFrontFoot;
    ModelRenderer RightLowerLeg;
    ModelRenderer RightUpperLeg;
    ModelRenderer Head;
    ModelRenderer ChinHair;
    ModelRenderer NeckHair;
    ModelRenderer Mane;
    ModelRenderer InnerWing;
    ModelRenderer MidWing;
    ModelRenderer OuterWing;
    ModelRenderer InnerWingR;
    ModelRenderer MidWingR;
    ModelRenderer OuterWingR;
    ModelRenderer Abdomen;
    ModelRenderer STailRoot;
    ModelRenderer STail2;
    ModelRenderer STail3;
    ModelRenderer STail4;
    ModelRenderer STail5;
    ModelRenderer StingerLump;
    ModelRenderer TailRoot;
    ModelRenderer Tail2;
    ModelRenderer Tail3;
    ModelRenderer Tail4;
    ModelRenderer TailTip;
    ModelRenderer Chest;
    ModelRenderer SaddleBack;
    ModelRenderer LeftFootRing;
    ModelRenderer Saddle;
    ModelRenderer LeftFootHarness;
    ModelRenderer RightFootHarness;
    ModelRenderer RightFootRing;
    ModelRenderer HeadBack;
    ModelRenderer HarnessStick;
    ModelRenderer NeckHarness;
    ModelRenderer Collar;
    ModelRenderer StorageChest;
    private float prevTailSwingYaw;
    private float prevMouthAngle;
    protected boolean isChested;
    protected boolean diving;
    protected boolean isSitting;
    protected T bigcat;

    public MoCModelAbstractBigCat() {
        this.textureWidth = 128;
        this.textureHeight = 128;

        this.Chest = new ModelRenderer(this, 0, 18);
        this.Chest.addBox(-3.5F, 0F, -8F, 7, 8, 9);
        this.Chest.setRotationPoint(0F, 8F, 0F);

        this.NeckBase = new ModelRenderer(this, 0, 7);
        this.NeckBase.addBox(-2.5F, 0F, -2.5F, 5, 6, 5);
        this.NeckBase.setRotationPoint(0F, -0.5F, -8F);
        setRotation(this.NeckBase, -14F / this.radianF, 0F, 0F);
        this.Chest.addChild(this.NeckBase);

        this.Collar = new ModelRenderer(this, 18, 0);
        this.Collar.addBox(-2.5F, 0F, 0F, 5, 4, 1, 0.0F);
        this.Collar.setRotationPoint(0.0F, 6F, -2F);
        setRotation(this.Collar, 20F / this.radianF, 0F, 0F);
        this.NeckBase.addChild(this.Collar);

        this.HeadBack = new ModelRenderer(this, 0, 0);
        this.HeadBack.addBox(-2.51F, -2.5F, -1F, 5, 5, 2);
        this.HeadBack.setRotationPoint(0F, 2.7F, -2.9F);
        setRotation(this.HeadBack, 14F / this.radianF, 0F, 0F);
        this.NeckBase.addChild(this.HeadBack);

        this.NeckHarness = new ModelRenderer(this, 85, 32);
        this.NeckHarness.addBox(-3F, -3F, -2F, 6, 6, 2);
        this.NeckHarness.setRotationPoint(0F, 0F, 0.95F);
        this.HeadBack.addChild(this.NeckHarness);

        this.HarnessStick = new ModelRenderer(this, 85, 42);
        this.HarnessStick.addBox(-3.5F, -0.5F, -0.5F, 7, 1, 1);
        this.HarnessStick.setRotationPoint(0F, -1.8F, 0.5F);
        setRotation(this.HarnessStick, 45F / this.radianF, 0F, 0F);
        this.HeadBack.addChild(this.HarnessStick);

        this.LeftHarness = new ModelRenderer(this, 85, 32);
        this.LeftHarness.addBox(3.2F, -0.6F, 1.5F, 0, 1, 9);
        this.LeftHarness.setRotationPoint(0F, 8.6F, -13F);
        setRotation(this.LeftHarness, 25F / this.radianF, 0F, 0F);

        this.RightHarness = new ModelRenderer(this, 85, 31);
        this.RightHarness.addBox(-3.2F, -0.6F, 1.5F, 0, 1, 9);
        this.RightHarness.setRotationPoint(0F, 8.6F, -13F);
        setRotation(this.RightHarness, 25F / this.radianF, 0F, 0F);

        this.Head = new ModelRenderer(this, 32, 0);
        this.Head.addBox(-3.5F, -3F, -2F, 7, 6, 4);
        this.Head.setRotationPoint(0F, 0.2F, -2.2F);
        this.HeadBack.addChild(this.Head);

        this.Nose = new ModelRenderer(this, 46, 19);
        this.Nose.addBox(-1.5F, -1F, -2F, 3, 2, 4);
        this.Nose.setRotationPoint(0F, 0F, -3F);
        setRotation(this.Nose, 27F / this.radianF, 0F, 0F);
        this.Head.addChild(this.Nose);

        this.RightUpperLip = new ModelRenderer(this, 34, 19);
        this.RightUpperLip.addBox(-1F, -1F, -2F, 2, 2, 4);
        this.RightUpperLip.setRotationPoint(-1.25F, 1F, -2.8F);
        setRotation(this.RightUpperLip, 10F / this.radianF, 2F / this.radianF, -15F / this.radianF);
        this.Head.addChild(this.RightUpperLip);

        this.LeftUpperLip = new ModelRenderer(this, 34, 25);
        this.LeftUpperLip.addBox(-1F, -1F, -2F, 2, 2, 4);
        this.LeftUpperLip.setRotationPoint(1.25F, 1F, -2.8F);
        setRotation(this.LeftUpperLip, 10F / this.radianF, -2F / this.radianF, 15F / this.radianF);
        this.Head.addChild(this.LeftUpperLip);

        this.UpperTeeth = new ModelRenderer(this, 20, 7);
        this.UpperTeeth.addBox(-1.5F, -1F, -1.5F, 3, 2, 3);
        this.UpperTeeth.setRotationPoint(0F, 2F, -2.5F);
        setRotation(this.UpperTeeth, 15F / this.radianF, 0F, 0F);
        this.Head.addChild(this.UpperTeeth);

        this.LeftFang = new ModelRenderer(this, 44, 10);
        this.LeftFang.addBox(-0.5F, -1.5F, -0.5F, 1, 3, 1);
        this.LeftFang.setRotationPoint(1.2F, 2.8F, -3.4F);
        setRotation(this.LeftFang, 15F / this.radianF, 0F, 0F);
        this.Head.addChild(this.LeftFang);

        this.RightFang = new ModelRenderer(this, 48, 10);
        this.RightFang.addBox(-0.5F, -1.5F, -0.5F, 1, 3, 1);
        this.RightFang.setRotationPoint(-1.2F, 2.8F, -3.4F);
        setRotation(this.RightFang, 15F / this.radianF, 0F, 0F);
        this.Head.addChild(this.RightFang);

        this.InsideMouth = new ModelRenderer(this, 50, 0);
        this.InsideMouth.addBox(-1.5F, -1F, -1F, 3, 2, 2);
        this.InsideMouth.setRotationPoint(0F, 2F, -1F);
        this.Head.addChild(this.InsideMouth);

        this.LowerJaw = new ModelRenderer(this, 46, 25);
        this.LowerJaw.addBox(-1.5F, -1F, -4F, 3, 2, 4);
        this.LowerJaw.setRotationPoint(0F, 2.1F, 0F);
        this.Head.addChild(this.LowerJaw);

        this.LowerJawTeeth = new ModelRenderer(this, 20, 12);
        this.LowerJawTeeth.addBox(-1F, 0F, -1F, 2, 1, 2);
        this.LowerJawTeeth.setRotationPoint(0F, -1.8F, -2.7F);
        this.LowerJawTeeth.mirror = true;
        this.LowerJaw.addChild(this.LowerJawTeeth);

        this.ChinHair = new ModelRenderer(this, 76, 7);
        this.ChinHair.addBox(-2.5F, 0F, -2F, 5, 6, 4);
        this.ChinHair.setRotationPoint(0F, 0F, 1F);
        this.LowerJaw.addChild(this.ChinHair);

        this.LeftChinBeard = new ModelRenderer(this, 48, 10);
        this.LeftChinBeard.addBox(-1F, -2.5F, -2F, 2, 5, 4);
        this.LeftChinBeard.setRotationPoint(3.6F, 0F, 0.25F);
        setRotation(this.LeftChinBeard, 0F, 30F / this.radianF, 0F);
        this.Head.addChild(this.LeftChinBeard);

        this.RightChinBeard = new ModelRenderer(this, 36, 10);
        this.RightChinBeard.addBox(-1F, -2.5F, -2F, 2, 5, 4);
        this.RightChinBeard.setRotationPoint(-3.6F, 0F, 0.25F);
        setRotation(this.RightChinBeard, 0F, -30F / this.radianF, 0F);
        this.Head.addChild(this.RightChinBeard);

        this.ForeheadHair = new ModelRenderer(this, 88, 0);
        this.ForeheadHair.addBox(-1.5F, -1.5F, -1.5F, 3, 3, 3);
        this.ForeheadHair.setRotationPoint(0F, -3.2F, 0F);
        setRotation(this.ForeheadHair, 10F / this.radianF, 0F, 0F);
        this.Head.addChild(this.ForeheadHair);

        this.Mane = new ModelRenderer(this, 94, 0);
        this.Mane.addBox(-5.5F, -5.5F, -3F, 11, 11, 6);
        this.Mane.setRotationPoint(0F, 0.7F, 3.7F);
        setRotation(this.Mane, -5F / this.radianF, 0F, 0F);
        this.Head.addChild(this.Mane);

        this.RightEar = new ModelRenderer(this, 54, 7);
        this.RightEar.addBox(-1F, -1F, -0.5F, 2, 2, 1);
        this.RightEar.setRotationPoint(-2.7F, -3.5F, 1F);
        setRotation(this.RightEar, 0F, 0F, -15F / this.radianF);
        this.Head.addChild(this.RightEar);

        this.LeftEar = new ModelRenderer(this, 54, 4);
        this.LeftEar.addBox(-1F, -1F, -0.5F, 2, 2, 1);
        this.LeftEar.setRotationPoint(2.7F, -3.5F, 1F);
        setRotation(this.LeftEar, 0F, 0F, 15F / this.radianF);
        this.Head.addChild(this.LeftEar);

        this.NeckHair = new ModelRenderer(this, 108, 17);
        this.NeckHair.addBox(-2F, -1F, -3F, 4, 2, 6);
        this.NeckHair.setRotationPoint(0F, -0.5F, 3F);
        setRotation(this.NeckHair, -10.6F / this.radianF, 0F, 0F);
        this.NeckBase.addChild(this.NeckHair);

        this.InnerWing = new ModelRenderer(this, 26, 115);
        this.InnerWing.addBox(0F, 0F, 0F, 7, 2, 11);
        this.InnerWing.setRotationPoint(4F, 9F, -7F);//(5F, 3F, -6F);
        setRotation(this.InnerWing, 0F, -20F / this.radianF, 0F);

        this.MidWing = new ModelRenderer(this, 36, 89);
        this.MidWing.addBox(1F, 0.1F, 1F, 12, 2, 11);
        this.MidWing.setRotationPoint(4F, 9F, -7F);//(5F, 3F, -6F);
        setRotation(this.MidWing, 0F, 5F / this.radianF, 0F);

        this.OuterWing = new ModelRenderer(this, 62, 115);
        this.OuterWing.addBox(0F, 0F, 0F, 22, 2, 11);
        this.OuterWing.setRotationPoint(16F, 9F, -7F);//(17F, 3F, -6F);
        setRotation(this.OuterWing, 0F, -18F / this.radianF, 0F);

        this.InnerWingR = new ModelRenderer(this, 26, 102);
        this.InnerWingR.addBox(-7F, 0F, 0F, 7, 2, 11);
        this.InnerWingR.setRotationPoint(-4F, 9F, -7F);//(-5F, 3F, -6F);
        setRotation(this.InnerWingR, 0F, 20F / this.radianF, 0F);

        this.MidWingR = new ModelRenderer(this, 82, 89);
        this.MidWingR.addBox(-13F, 0.1F, 1F, 12, 2, 11);
        this.MidWingR.setRotationPoint(-4F, 9F, -7F);//(-5F, 3F, -6F);
        setRotation(this.MidWingR, 0F, -5F / this.radianF, 0F);

        this.OuterWingR = new ModelRenderer(this, 62, 102);
        this.OuterWingR.addBox(-22F, 0F, 0F, 22, 2, 11);
        this.OuterWingR.setRotationPoint(-16F, 9F, -7F);//(-17F, 3F, -6F);
        setRotation(this.OuterWingR, 0F, 18F / this.radianF, 0F);

        this.Abdomen = new ModelRenderer(this, 0, 35);
        this.Abdomen.addBox(-3F, 0F, 0F, 6, 7, 7);
        this.Abdomen.setRotationPoint(0F, 0F, 0F);
        setRotation(this.Abdomen, -0.0523599F, 0F, 0F);
        this.Chest.addChild(this.Abdomen);

        this.Ass = new ModelRenderer(this, 0, 49);
        this.Ass.addBox(-2.5F, 0F, 0F, 5, 5, 3);
        this.Ass.setRotationPoint(0F, 0F, 7F);
        setRotation(this.Ass, -20F / this.radianF, 0F, 0F);
        this.Abdomen.addChild(this.Ass);

        this.TailRoot = new ModelRenderer(this, 96, 83);
        this.TailRoot.addBox(-1F, 0F, -1F, 2, 4, 2);
        this.TailRoot.setRotationPoint(0F, 1F, 7F);
        setRotation(this.TailRoot, 87F / this.radianF, 0F, 0F);
        this.Abdomen.addChild(this.TailRoot);

        this.Tail2 = new ModelRenderer(this, 96, 75);
        this.Tail2.addBox(-1F, 0F, -1F, 2, 6, 2);
        this.Tail2.setRotationPoint(-0.01F, 3.5F, 0F);
        setRotation(this.Tail2, -30F / this.radianF, 0F, 0F);
        this.TailRoot.addChild(this.Tail2);

        this.Tail3 = new ModelRenderer(this, 96, 67);
        this.Tail3.addBox(-1F, 0F, -1F, 2, 6, 2);
        this.Tail3.setRotationPoint(0.01F, 5.5F, 0F);
        setRotation(this.Tail3, -17F / this.radianF, 0F, 0F);
        this.Tail2.addChild(this.Tail3);

        this.Tail4 = new ModelRenderer(this, 96, 61);
        this.Tail4.addBox(-1F, 0F, -1F, 2, 4, 2);
        this.Tail4.setRotationPoint(-0.01F, 5.5F, 0F);
        setRotation(this.Tail4, 21F / this.radianF, 0F, 0F);
        this.Tail3.addChild(this.Tail4);

        this.TailTip = new ModelRenderer(this, 96, 55);
        this.TailTip.addBox(-1F, 0F, -1F, 2, 4, 2);
        this.TailTip.setRotationPoint(0.01F, 3.5F, 0F);
        setRotation(this.TailTip, 21F / this.radianF, 0F, 0F);
        this.Tail4.addChild(this.TailTip);

        this.TailTusk = new ModelRenderer(this, 96, 49);
        this.TailTusk.addBox(-1.5F, 0F, -1.5F, 3, 3, 3);
        this.TailTusk.setRotationPoint(0F, 3.5F, 0F);
        setRotation(this.TailTusk, 21F / this.radianF, 0F, 0F);
        this.Tail4.addChild(this.TailTusk);

        this.Saddle = new ModelRenderer(this, 79, 18);
        this.Saddle.addBox(-4F, -1F, -3F, 8, 2, 6);
        this.Saddle.setRotationPoint(0F, 0.5F, -1F);
        this.Chest.addChild(this.Saddle);

        this.SaddleFront = new ModelRenderer(this, 101, 26);
        this.SaddleFront.addBox(-2.5F, -1F, -1.5F, 5, 2, 3);
        this.SaddleFront.setRotationPoint(0F, -1.0F, -1.5F);
        setRotation(this.SaddleFront, -10.6F / this.radianF, 0F, 0F);
        this.Saddle.addChild(this.SaddleFront);

        this.SaddleBack = new ModelRenderer(this, 77, 26);
        this.SaddleBack.addBox(-4F, -2F, -2F, 8, 2, 4);
        this.SaddleBack.setRotationPoint(0F, 0.7F, 4F);
        setRotation(this.SaddleBack, 12.78F / this.radianF, 0F, 0F);
        this.Saddle.addChild(this.SaddleBack);

        this.LeftFootHarness = new ModelRenderer(this, 81, 18);
        this.LeftFootHarness.addBox(-0.5F, 0F, -0.5F, 1, 5, 1);
        this.LeftFootHarness.setRotationPoint(4F, 0F, 0.5F);
        this.Saddle.addChild(this.LeftFootHarness);

        this.LeftFootRing = new ModelRenderer(this, 107, 31);
        this.LeftFootRing.addBox(0F, 0F, 0F, 1, 2, 2);
        this.LeftFootRing.setRotationPoint(-0.5F, 5F, -1F);
        this.LeftFootHarness.addChild(this.LeftFootRing);

        this.RightFootHarness = new ModelRenderer(this, 101, 18);
        this.RightFootHarness.addBox(-0.5F, 0F, -0.5F, 1, 5, 1);
        this.RightFootHarness.setRotationPoint(-4F, 0F, 0.5F);
        this.Saddle.addChild(this.RightFootHarness);

        this.RightFootRing = new ModelRenderer(this, 101, 31);
        this.RightFootRing.addBox(0F, 0F, 0F, 1, 2, 2);
        this.RightFootRing.setRotationPoint(-0.5F, 5F, -1F);
        this.RightFootHarness.addChild(this.RightFootRing);

        this.StorageChest = new ModelRenderer(this, 32, 59);
        this.StorageChest.addBox(-5F, -2F, -2.5F, 10, 4, 5);
        this.StorageChest.setRotationPoint(0F, -2F, 5.5F);
        setRotation(this.StorageChest, -90F / this.radianF, 0F, 0F);
        this.Abdomen.addChild(this.StorageChest);

        this.STailRoot = new ModelRenderer(this, 104, 79);
        this.STailRoot.addBox(-3F, 4F, 5F, 6, 4, 6);
        this.STailRoot.setRotationPoint(0F, 8F, 0F);
        this.STailRoot.setTextureSize(128, 128);
        this.STailRoot.mirror = true;
        setRotation(this.STailRoot, 0.5796765F, 0F, 0F);
        this.STail2 = new ModelRenderer(this, 106, 69);
        this.STail2.addBox(-2.5F, 7.5F, 7.3F, 5, 4, 6);
        this.STail2.setRotationPoint(0F, 8F, 0F);
        this.STail2.setTextureSize(128, 128);
        this.STail2.mirror = true;
        setRotation(this.STail2, 0.9514626F, 0F, 0F);
        this.STail3 = new ModelRenderer(this, 108, 60);
        this.STail3.addBox(-2F, 13.5F, 3.3F, 4, 3, 6);
        this.STail3.setRotationPoint(0F, 8F, 0F);
        this.STail3.setTextureSize(128, 128);
        this.STail3.mirror = true;
        setRotation(this.STail3, 1.660128F, 0F, 0F);
        this.STail4 = new ModelRenderer(this, 108, 51);
        this.STail4.addBox(-2F, 15.2F, -5.3F, 4, 3, 6);
        this.STail4.setRotationPoint(0F, 8F, 0F);
        this.STail4.setTextureSize(128, 128);
        this.STail4.mirror = true;
        setRotation(this.STail4, 2.478058F, 0F, 0F);
        this.STail5 = new ModelRenderer(this, 108, 42);
        this.STail5.addBox(-2F, 12.9F, -9F, 4, 3, 6);
        this.STail5.setRotationPoint(0F, 8F, 0F);
        this.STail5.setTextureSize(128, 128);
        this.STail5.mirror = true;
        setRotation(this.STail5, 3.035737F, 0F, 0F);
        this.StingerLump = new ModelRenderer(this, 112, 34);
        this.StingerLump.addBox(-1.5F, 7.9F, 6F, 3, 3, 5);
        this.StingerLump.setRotationPoint(0F, 8F, 0F);
        this.StingerLump.setTextureSize(128, 128);
        this.StingerLump.mirror = true;
        setRotation(this.StingerLump, 2.031914F, 0F, 0F);
        this.Stinger = new ModelRenderer(this, 118, 29);
        this.Stinger.addBox(-0.5F, 1.9F, 8F, 1, 1, 4);
        this.Stinger.setRotationPoint(0F, 8F, 0F);
        this.Stinger.setTextureSize(128, 128);
        this.Stinger.mirror = true;
        setRotation(this.Stinger, 1.213985F, 0F, 0F);

        this.LeftUpperLeg = new ModelRenderer(this, 0, 96);
        this.LeftUpperLeg.addBox(-1.5F, 0F, -2F, 3, 7, 4);
        this.LeftUpperLeg.setRotationPoint(3.99F, 3F, -7F);
        setRotation(this.LeftUpperLeg, 15F / this.radianF, 0F, 0F);
        this.Chest.addChild(this.LeftUpperLeg);

        this.LeftLowerLeg = new ModelRenderer(this, 0, 107);
        this.LeftLowerLeg.addBox(-1.5F, 0F, -1.5F, 3, 6, 3);
        this.LeftLowerLeg.setRotationPoint(-0.01F, 6.5F, 0.2F);
        setRotation(this.LeftLowerLeg, -21.5F / this.radianF, 0F, 0F);
        this.LeftUpperLeg.addChild(this.LeftLowerLeg);

        this.LeftFrontFoot = new ModelRenderer(this, 0, 116);
        this.LeftFrontFoot.addBox(-2F, 0F, -2F, 4, 2, 4);
        this.LeftFrontFoot.setRotationPoint(0F, 5F, -1.0F);
        setRotation(this.LeftFrontFoot, 6.5F / this.radianF, 0F, 0F);
        this.LeftLowerLeg.addChild(this.LeftFrontFoot);

        this.LeftClaw1 = new ModelRenderer(this, 16, 125);
        this.LeftClaw1.addBox(-0.5F, 0F, -0.5F, 1, 1, 2);
        this.LeftClaw1.setRotationPoint(-1.3F, 1.2F, -3.0F);
        setRotation(this.LeftClaw1, 45F / this.radianF, 0F, -1F / this.radianF);
        this.LeftFrontFoot.addChild(this.LeftClaw1);

        this.LeftClaw2 = new ModelRenderer(this, 16, 125);
        this.LeftClaw2.addBox(-0.5F, 0F, -0.5F, 1, 1, 2);
        this.LeftClaw2.setRotationPoint(0F, 1.1F, -3F);
        setRotation(this.LeftClaw2, 45F / this.radianF, 0F, 0F);
        this.LeftFrontFoot.addChild(this.LeftClaw2);

        this.LeftClaw3 = new ModelRenderer(this, 16, 125);
        this.LeftClaw3.addBox(-0.5F, 0F, -0.5F, 1, 1, 2);
        this.LeftClaw3.setRotationPoint(1.3F, 1.2F, -3F);
        setRotation(this.LeftClaw3, 45F / this.radianF, 0F, 1F / this.radianF);
        this.LeftFrontFoot.addChild(this.LeftClaw3);

        this.RightUpperLeg = new ModelRenderer(this, 14, 96);
        this.RightUpperLeg.addBox(-1.5F, 0F, -2F, 3, 7, 4);
        this.RightUpperLeg.setRotationPoint(-3.99F, 3F, -7F);
        setRotation(this.RightUpperLeg, 15F / this.radianF, 0F, 0F);
        this.Chest.addChild(this.RightUpperLeg);

        this.RightLowerLeg = new ModelRenderer(this, 12, 107);
        this.RightLowerLeg.addBox(-1.5F, 0F, -1.5F, 3, 6, 3);
        this.RightLowerLeg.setRotationPoint(0.01F, 6.5F, 0.2F);
        setRotation(this.RightLowerLeg, -21.5F / this.radianF, 0F, 0F);
        this.RightUpperLeg.addChild(this.RightLowerLeg);

        this.RightFrontFoot = new ModelRenderer(this, 0, 122);
        this.RightFrontFoot.addBox(-2F, 0F, -2F, 4, 2, 4);
        this.RightFrontFoot.setRotationPoint(0F, 5F, -1.0F);
        setRotation(this.RightFrontFoot, 6.5F / this.radianF, 0F, 0F);
        this.RightLowerLeg.addChild(this.RightFrontFoot);

        this.RightClaw1 = new ModelRenderer(this, 16, 125);
        this.RightClaw1.addBox(-0.5F, 0F, -0.5F, 1, 1, 2);
        this.RightClaw1.setRotationPoint(-1.3F, 1.2F, -3.0F);
        setRotation(this.RightClaw1, 45F / this.radianF, 0F, -1F / this.radianF);
        this.RightFrontFoot.addChild(this.RightClaw1);

        this.RightClaw2 = new ModelRenderer(this, 16, 125);
        this.RightClaw2.addBox(-0.5F, 0F, -0.5F, 1, 1, 2);
        this.RightClaw2.setRotationPoint(0F, 1.1F, -3F);
        setRotation(this.RightClaw2, 45F / this.radianF, 0F, 0F);
        this.RightFrontFoot.addChild(this.RightClaw2);

        this.RightClaw3 = new ModelRenderer(this, 16, 125);
        this.RightClaw3.addBox(-0.5F, 0F, -0.5F, 1, 1, 2);
        this.RightClaw3.setRotationPoint(1.3F, 1.2F, -3F);
        setRotation(this.RightClaw3, 45F / this.radianF, 0F, 1F / this.radianF);
        this.RightFrontFoot.addChild(this.RightClaw3);

        this.LeftHindUpperLeg = new ModelRenderer(this, 0, 67);
        this.LeftHindUpperLeg.addBox(-2F, -1.0F, -1.5F, 3, 8, 5);
        this.LeftHindUpperLeg.setRotationPoint(3F, 3F, 6.8F);
        setRotation(this.LeftHindUpperLeg, -25F / this.radianF, 0F, 0F);
        this.Abdomen.addChild(this.LeftHindUpperLeg);

        this.LeftAnkle = new ModelRenderer(this, 0, 80);
        this.LeftAnkle.addBox(-1F, 0F, -1.5F, 2, 3, 3);
        this.LeftAnkle.setRotationPoint(-0.5F, 4F, 5F);
        this.LeftHindUpperLeg.addChild(this.LeftAnkle);

        this.LeftHindLowerLeg = new ModelRenderer(this, 0, 86);
        this.LeftHindLowerLeg.addBox(-1F, 0F, -1F, 2, 3, 2);
        this.LeftHindLowerLeg.setRotationPoint(0F, 3F, 0.5F);
        this.LeftAnkle.addChild(this.LeftHindLowerLeg);

        this.LeftHindFoot = new ModelRenderer(this, 0, 91);
        this.LeftHindFoot.addBox(-1.5F, 0F, -1.5F, 3, 2, 3);
        this.LeftHindFoot.setRotationPoint(0F, 2.6F, -0.8F);
        setRotation(this.LeftHindFoot, 27F / this.radianF, 0F, 0F);
        this.LeftHindLowerLeg.addChild(this.LeftHindFoot);

        this.RightHindUpperLeg = new ModelRenderer(this, 16, 67);
        this.RightHindUpperLeg.addBox(-2F, -1F, -1.5F, 3, 8, 5);
        this.RightHindUpperLeg.setRotationPoint(-2F, 3F, 6.8F);
        setRotation(this.RightHindUpperLeg, -25F / this.radianF, 0F, 0F);
        this.Abdomen.addChild(this.RightHindUpperLeg);

        this.RightAnkle = new ModelRenderer(this, 10, 80);
        this.RightAnkle.addBox(-1F, 0F, -1.5F, 2, 3, 3);
        this.RightAnkle.setRotationPoint(-0.5F, 4F, 5F);
        this.RightHindUpperLeg.addChild(this.RightAnkle);

        this.RightHindLowerLeg = new ModelRenderer(this, 8, 86);
        this.RightHindLowerLeg.addBox(-1F, 0F, -1F, 2, 3, 2);
        this.RightHindLowerLeg.setRotationPoint(0F, 3F, 0.5F);
        this.RightAnkle.addChild(this.RightHindLowerLeg);

        this.RightHindFoot = new ModelRenderer(this, 12, 91);
        this.RightHindFoot.addBox(-1.5F, 0F, -1.5F, 3, 2, 3);
        this.RightHindFoot.setRotationPoint(0F, 2.6F, -0.8F);
        setRotation(this.RightHindFoot, 27F / this.radianF, 0F, 0F);
        this.RightHindLowerLeg.addChild(this.RightHindFoot);

    }

    public float updateGhostTransparency() {
        return 1.0F;
    }

    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        renderSaddle(this.isSaddled);
        renderMane(this.hasMane);
        renderCollar(this.isTamed);
        renderTeeth(this.hasSaberTeeth);
        renderChest(this.hasChest);

        matrixStackIn.push();
        //matrixStackIn.translate(0F, yOffset, 0F);

        if (this.isGhost) {
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.color4f(0.8F, 0.8F, 0.8F, updateGhostTransparency());
            //matrixStackIn.scale(1.3F, 1.0F, 1.3F);
        }

        this.Chest.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);

        if (this.isFlyer) {
            this.InnerWing.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            this.MidWing.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            this.OuterWing.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            this.InnerWingR.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            this.MidWingR.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            this.OuterWingR.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        }

        if (this.hasStinger) {
            this.STailRoot.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            this.STail2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            this.STail3.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            this.STail4.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            this.STail5.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            this.StingerLump.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            this.Stinger.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        }

        if (this.isSaddled && this.isRidden) {
            this.LeftHarness.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            this.RightHarness.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        }

        if (this.isGhost) {
            RenderSystem.disableBlend();
        }
        matrixStackIn.pop();

    }

    private void renderTeeth(boolean flag) {
        this.LeftFang.showModel = flag;
        this.RightFang.showModel = flag;
    }

    private void renderCollar(boolean flag) {
        this.Collar.showModel = flag;
    }

    private void renderSaddle(boolean flag) {
        this.NeckHarness.showModel = flag;
        this.HarnessStick.showModel = flag;
        this.Saddle.showModel = flag;
    }

    private void renderMane(boolean flag) {
        this.Mane.showModel = flag;
        this.LeftChinBeard.showModel = flag;
        this.RightChinBeard.showModel = flag;
        this.ForeheadHair.showModel = flag;
        this.NeckHair.showModel = flag;
        this.ChinHair.showModel = flag;
    }

    private void renderChest(boolean flag) {
        this.StorageChest.showModel = flag;
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

        //limbSwing = time
        //limbSwingAmount = movement speed!
        //ageInTicks = ??timer!
        //netHeadYaw = Head Y movement or rotation yaw
        //headPitch = Head X movement or rotation Pitch
        //f5 = ?

        // Interpolation factor for smoother animations
        float interpolationFactor = 0.1F;

        float RLegXRot = MathHelper.cos((limbSwing * 0.8F) + 3.141593F) * 0.8F * limbSwingAmount;
        float LLegXRot = MathHelper.cos(limbSwing * 0.8F) * 0.8F * limbSwingAmount;
        float gallopRLegXRot = MathHelper.cos((limbSwing * 0.6F) + 3.141593F) * 0.8F * limbSwingAmount;
        float gallopLLegXRot = MathHelper.cos(limbSwing * 0.6F) * 0.8F * limbSwingAmount;

        float stingYOffset;
        float stingZOffset;

        // Set the target tail angle based on the tail swinging state or any other conditions
        float targetTailAngle;
        if (this.movingTail) {
            // Set the desired tail swing angle when the tail is moving
            targetTailAngle = MathHelper.cos(ageInTicks * 0.3F);
        } else {
            // Set the target angle to 0 when the tail is not moving
            targetTailAngle = 0F;
        }
        // Interpolate tail swinging rotation towards the target angle
        float interpolatedTailSwingYaw = this.prevTailSwingYaw + (targetTailAngle - this.prevTailSwingYaw) * interpolationFactor;
        // Apply the interpolated tail swinging rotation to the tail
        this.Tail2.rotateAngleY = interpolatedTailSwingYaw;
        // Save the current value for the next frame's interpolation
        this.prevTailSwingYaw = interpolatedTailSwingYaw;

        if (this.isSitting) {
            stingYOffset = 17F;
            stingZOffset = -3F;
            this.Chest.rotationPointY = 14F;
            this.Abdomen.rotateAngleX = -10F / this.radianF;
            this.Chest.rotateAngleX = -45F / this.radianF;
            this.RightUpperLeg.rotateAngleX = (35F / this.radianF);
            this.RightLowerLeg.rotateAngleX = 5F / this.radianF;
            this.LeftUpperLeg.rotateAngleX = (35F / this.radianF);
            this.LeftLowerLeg.rotateAngleX = 5F / this.radianF;
            this.NeckBase.rotateAngleX = 20F / this.radianF;
            this.RightHindUpperLeg.rotationPointY = 1F;
            this.RightHindUpperLeg.rotateAngleX = -50F / this.radianF;
            this.LeftHindUpperLeg.rotationPointY = 1F;
            this.LeftHindUpperLeg.rotateAngleX = -50F / this.radianF;
            this.RightHindFoot.rotateAngleX = 90F / this.radianF;
            this.LeftHindFoot.rotateAngleX = 90F / this.radianF;
            this.TailRoot.rotateAngleX = 100F / this.radianF;
            this.Tail2.rotateAngleX = 35F / this.radianF;
            this.Tail3.rotateAngleX = 10F / this.radianF;
            this.NeckHair.rotationPointY = 2F;
            this.Collar.rotateAngleX = 0F / this.radianF;
            this.Collar.rotationPointY = 7F;
            this.Collar.rotationPointZ = -4F;
        } else {
            stingYOffset = 8F;
            stingZOffset = 0F;
            this.Chest.rotationPointY = 8F;
            this.Abdomen.rotateAngleX = 0F;
            this.Chest.rotateAngleX = 0F;
            this.NeckBase.rotateAngleX = -14F / this.radianF;
            this.TailRoot.rotateAngleX = 87F / this.radianF;
            this.Tail2.rotateAngleX = -30F / this.radianF;
            this.Tail3.rotateAngleX = -17F / this.radianF;
            this.RightLowerLeg.rotateAngleX = -21.5F / this.radianF;
            this.LeftLowerLeg.rotateAngleX = -21.5F / this.radianF;
            this.RightHindUpperLeg.rotationPointY = 3F;
            this.LeftHindUpperLeg.rotationPointY = 3F;
            this.RightHindFoot.rotateAngleX = 27F / this.radianF;
            this.LeftHindFoot.rotateAngleX = 27F / this.radianF;
            this.Collar.rotationPointZ = -2F;
            this.NeckHair.rotationPointY = -0.5F;
            if (this.hasMane) {
                this.Collar.rotationPointY = 9F;
            } else {
                this.Collar.rotationPointY = 6F;
            }
            this.Collar.rotateAngleX = (20F / this.radianF) + MathHelper.cos(limbSwing * 0.8F) * 0.5F * limbSwingAmount;

            boolean galloping = (limbSwingAmount >= 0.97F);
            if (this.onAir || this.isGhost) {
                if (this.isGhost || (this.isFlyer && limbSwingAmount > 0)) {
                    float speedMov = (limbSwingAmount * 0.5F);
                    this.RightUpperLeg.rotateAngleX = (45F / this.radianF) + speedMov;
                    this.LeftUpperLeg.rotateAngleX = (45F / this.radianF) + speedMov;
                    this.RightHindUpperLeg.rotateAngleX = (10F / this.radianF) + speedMov;
                    this.LeftHindUpperLeg.rotateAngleX = (10F / this.radianF) + speedMov;
                } else if (this.isMovingVertically) {
                    this.RightUpperLeg.rotateAngleX = (-35F / this.radianF);
                    this.LeftUpperLeg.rotateAngleX = (-35F / this.radianF);
                    this.RightHindUpperLeg.rotateAngleX = (35F / this.radianF);
                    this.LeftHindUpperLeg.rotateAngleX = (35F / this.radianF);
                }

            } else {
                if (galloping) {
                    this.RightUpperLeg.rotateAngleX = (15F / this.radianF) + gallopRLegXRot;
                    this.LeftUpperLeg.rotateAngleX = (15F / this.radianF) + gallopRLegXRot;
                    this.RightHindUpperLeg.rotateAngleX = (-25F / this.radianF) + gallopLLegXRot;
                    this.LeftHindUpperLeg.rotateAngleX = (-25F / this.radianF) + gallopLLegXRot;
                    this.Abdomen.rotateAngleY = 0F;
                } else {
                    this.RightUpperLeg.rotateAngleX = (15F / this.radianF) + RLegXRot;
                    this.LeftHindUpperLeg.rotateAngleX = (-25F / this.radianF) + RLegXRot;
                    this.LeftUpperLeg.rotateAngleX = (15F / this.radianF) + LLegXRot;
                    this.RightHindUpperLeg.rotateAngleX = (-25F / this.radianF) + LLegXRot;
                    if (!this.hasStinger) {
                        this.Abdomen.rotateAngleY = MathHelper.cos(limbSwing * 0.3F) * 0.25F * limbSwingAmount;
                    }
                }
            }

            if (this.isRidden) {
                this.LeftFootHarness.rotateAngleX = -60 / this.radianF;
                this.RightFootHarness.rotateAngleX = -60 / this.radianF;
            } else {
                this.LeftFootHarness.rotateAngleX = RLegXRot / 3F;
                this.RightFootHarness.rotateAngleX = RLegXRot / 3F;

                this.LeftFootHarness.rotateAngleZ = RLegXRot / 5F;
                this.RightFootHarness.rotateAngleZ = -RLegXRot / 5F;
            }

            float TailXRot = MathHelper.cos(limbSwing * 0.4F) * 0.15F * limbSwingAmount;
            this.TailRoot.rotateAngleX = (87F / this.radianF) + TailXRot;
            this.Tail2.rotateAngleX = (-30F / this.radianF) + TailXRot;
            this.Tail3.rotateAngleX = (-17F / this.radianF) + TailXRot;
            this.Tail4.rotateAngleX = (21F / this.radianF) + TailXRot;
            this.TailTip.rotateAngleX = (21F / this.radianF) + TailXRot;
            this.TailTusk.rotateAngleX = (21F / this.radianF) + TailXRot;
        }

        float HeadXRot = (headPitch / 57.29578F);

        this.HeadBack.rotateAngleX = 14F / this.radianF + HeadXRot;
        this.HeadBack.rotateAngleY = (netHeadYaw / 57.29578F);

        // Calculate the target mouth movement angle based on the openMouthCounter
        float targetMouthAngle;
        if (this.openMouthCounter != 0) {
            if (this.openMouthCounter < 10) {
                targetMouthAngle = 22 + (this.openMouthCounter * 3);
            } else if (this.openMouthCounter > 20) {
                targetMouthAngle = 22 + (90 - (this.openMouthCounter * 3));
            } else {
                targetMouthAngle = 55F;
            }
        } else {
            // Set the target angle to 0 when the openMouthCounter is 0 (mouth closed)
            targetMouthAngle = 0F;
        }
        // Interpolate mouth movement towards the target angle
        float interpolatedMouthAngle = this.prevMouthAngle + (targetMouthAngle - this.prevMouthAngle) * interpolationFactor;
        // Apply the interpolated mouth movement angle to the LowerJaw
        this.LowerJaw.rotateAngleX = interpolatedMouthAngle / this.radianF;
        // Save the current value for the next frame's interpolation
        this.prevMouthAngle = interpolatedMouthAngle;

        if (this.isSaddled) {
            this.LeftHarness.rotateAngleX = 25F / this.radianF + this.HeadBack.rotateAngleX;
            this.LeftHarness.rotateAngleY = this.HeadBack.rotateAngleY;
            this.RightHarness.rotateAngleX = 25F / this.radianF + this.HeadBack.rotateAngleX;
            this.RightHarness.rotateAngleY = this.HeadBack.rotateAngleY;
        }

        if (this.isFlyer) {

            /*
             * flapping wings or cruising. IF flapping wings, move up and down.
             * if cruising, movement depends on speed
             */
            float WingRot;
            if (this.flapwings) {
                WingRot = MathHelper.cos((ageInTicks * 0.3F) + 3.141593F) * 1.2F;
            } else {
                WingRot = MathHelper.cos((limbSwing * 0.5F)) * 0.1F;
            }

            if (this.floating) {
                this.OuterWing.rotateAngleY = -0.3228859F + (WingRot / 2F);
                this.OuterWingR.rotateAngleY = 0.3228859F - (WingRot / 2F);

            } else {
                WingRot = 60 / this.radianF;//0.7854F;
                this.OuterWing.rotateAngleY = -90 / this.radianF;//-1.396F;
                this.OuterWingR.rotateAngleY = 90 / this.radianF;//1.396F;
            }

            this.InnerWingR.rotationPointY = this.InnerWing.rotationPointY;
            this.InnerWingR.rotationPointZ = this.InnerWing.rotationPointZ;
            this.OuterWing.rotationPointX = this.InnerWing.rotationPointX + (MathHelper.cos(WingRot) * 12F);
            this.OuterWingR.rotationPointX = this.InnerWingR.rotationPointX - (MathHelper.cos(WingRot) * 12F);

            this.MidWing.rotationPointY = this.InnerWing.rotationPointY;
            this.MidWingR.rotationPointY = this.InnerWing.rotationPointY;
            this.OuterWing.rotationPointY = this.InnerWing.rotationPointY + (MathHelper.sin(WingRot) * 12F);
            this.OuterWingR.rotationPointY = this.InnerWingR.rotationPointY + (MathHelper.sin(WingRot) * 12F);

            this.MidWing.rotationPointZ = this.InnerWing.rotationPointZ;
            this.MidWingR.rotationPointZ = this.InnerWing.rotationPointZ;
            this.OuterWing.rotationPointZ = this.InnerWing.rotationPointZ;
            this.OuterWingR.rotationPointZ = this.InnerWing.rotationPointZ;

            this.MidWing.rotateAngleZ = WingRot;
            this.InnerWing.rotateAngleZ = WingRot;
            this.OuterWing.rotateAngleZ = WingRot;

            this.InnerWingR.rotateAngleZ = -WingRot;
            this.MidWingR.rotateAngleZ = -WingRot;
            this.OuterWingR.rotateAngleZ = -WingRot;

            if (this.hasStinger) {

                if (!this.poisoning) {
                    this.STailRoot.rotateAngleX = 33F / this.radianF;
                    this.STailRoot.rotationPointY = stingYOffset;
                    this.STailRoot.rotationPointZ = stingZOffset;

                    this.STail2.rotateAngleX = 54.5F / this.radianF;
                    this.STail2.rotationPointY = stingYOffset;
                    this.STail2.rotationPointZ = stingZOffset;

                    this.STail3.rotateAngleX = 95.1F / this.radianF;
                    this.STail3.rotationPointY = stingYOffset;
                    this.STail3.rotationPointZ = stingZOffset;

                    this.STail4.rotateAngleX = 141.8F / this.radianF;
                    this.STail4.rotationPointY = stingYOffset;
                    this.STail4.rotationPointZ = stingZOffset;

                    this.STail5.rotateAngleX = 173.9F / this.radianF;
                    this.STail5.rotationPointY = stingYOffset;
                    this.STail5.rotationPointZ = stingZOffset;

                    this.StingerLump.rotateAngleX = 116.4F / this.radianF;
                    this.StingerLump.rotationPointY = stingYOffset;
                    this.StingerLump.rotationPointZ = stingZOffset;

                    this.Stinger.rotateAngleX = 69.5F / this.radianF;
                    this.Stinger.rotationPointY = stingYOffset;
                    this.Stinger.rotationPointZ = stingZOffset;

                } else if (!this.isSitting) {
                    this.STailRoot.rotateAngleX = 95.2F / this.radianF;
                    this.STailRoot.rotationPointY = 14.5F;
                    this.STailRoot.rotationPointZ = 2F;

                    this.STail2.rotateAngleX = 128.5F / this.radianF;
                    this.STail2.rotationPointY = 15F;
                    this.STail2.rotationPointZ = 4F;

                    this.STail3.rotateAngleX = 169F / this.radianF;
                    this.STail3.rotationPointY = 14F;
                    this.STail3.rotationPointZ = 3.8F;

                    this.STail4.rotateAngleX = 177F / this.radianF;
                    this.STail4.rotationPointY = 13.5F;
                    this.STail4.rotationPointZ = -8.5F;

                    this.STail5.rotateAngleX = 180F / this.radianF;
                    this.STail5.rotationPointY = 11.5F;
                    this.STail5.rotationPointZ = -17F;

                    this.StingerLump.rotateAngleX = 35.4F / this.radianF;
                    this.StingerLump.rotationPointY = -4F;
                    this.StingerLump.rotationPointZ = -28F;

                    this.Stinger.rotateAngleX = 25.5F / this.radianF;
                    this.Stinger.rotationPointY = 4F;
                    this.Stinger.rotationPointZ = -29F;
                }
            }
        }
    }
}

//TODO
//size is too small? (specially manticores)
//legs back when flying
//saddle movement, position when ridden
//galloping when ridden
//FIRECAT!!
//LIZARDCAT
//BONE CAT
