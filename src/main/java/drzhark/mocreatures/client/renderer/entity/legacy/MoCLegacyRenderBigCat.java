/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity.legacy;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.client.model.legacy.MoCLegacyModelBigCat1;
import drzhark.mocreatures.client.model.legacy.MoCLegacyModelBigCat2;
import drzhark.mocreatures.entity.hunter.MoCEntityBigCat;
import drzhark.mocreatures.entity.hunter.MoCEntityLion;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCLegacyRenderBigCat extends MobRenderer<MoCEntityBigCat, MoCLegacyModelBigCat2<MoCEntityBigCat>> {

    public MoCLegacyModelBigCat2 bigcat1;

    public MoCLegacyRenderBigCat(EntityRendererManager renderManagerIn, MoCLegacyModelBigCat2 modelbigcat2, MoCLegacyModelBigCat1 modelbigcat1, float f) {
        super(renderManagerIn, modelbigcat2, f);
        this.addLayer(new LayerMoCBigCat(this));
        this.bigcat1 = modelbigcat2;
    }

    @Override
    public ResourceLocation getEntityTexture(MoCEntityBigCat entitybigcat) {
        return entitybigcat.getTexture();
    }

    @Override
    public void render(MoCEntityBigCat entitybigcat, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        super.render(entitybigcat, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
        boolean flag = MoCreatures.proxy.getDisplayPetName() && !(entitybigcat.getPetName()).isEmpty();
        boolean flag1 = MoCreatures.proxy.getDisplayPetHealth();

        if (entitybigcat.getIsTamed()) {
            float f2 = 1.6F;
            float f3 = 0.01666667F * f2;
            float f5 = entitybigcat.getDistance(this.renderManager.info.getRenderViewEntity());
            if (f5 < 16F) {
                String s = "";
                s = s + entitybigcat.getPetName();
                float f7 = 0.1F;
                FontRenderer fontrenderer = getFontRendererFromRenderManager();
                matrixStackIn.push();
                matrixStackIn.translate(0.0F, f7, 0.0F);
                RenderSystem.normal3f(0.0F, 1.0F, 0.0F);
                matrixStackIn.rotate(Vector3f.YP.rotationDegrees(-this.renderManager.info.getYaw()));
                matrixStackIn.scale(-f3, -f3, f3);

                Tessellator tessellator1 = Tessellator.getInstance();
                byte byte0 = -60;
                if (flag1) {

                    if (!flag) {
                        byte0 += 8;
                    }

                    tessellator1.getBuffer().begin(7, DefaultVertexFormats.POSITION_COLOR);
                    // may break SSP, need to test
                    float f8;
                    f8 = entitybigcat.getHealth();

                    /*
                     * if(MoCreatures.mc.isMultiplayerWorld()) { f8 =
                     * entityliving.getHealth(); } else { f8 =
                     * entityliving.getHealth(); }
                     */
                    float f9 = entitybigcat.getMaxHealth();
                    float f10 = f8 / f9;
                    float f11 = 40F * f10;
                    tessellator1.getBuffer().pos(-20F + f11, -10 + byte0, 0.0D).color(0.7F, 0.0F, 0.0F, 1.0F).endVertex();
                    tessellator1.getBuffer().pos(-20F + f11, -6 + byte0, 0.0D).color(0.7F, 0.0F, 0.0F, 1.0F).endVertex();
                    tessellator1.getBuffer().pos(20D, -6 + byte0, 0.0D).color(0.7F, 0.0F, 0.0F, 1.0F).endVertex();
                    tessellator1.getBuffer().pos(20D, -10 + byte0, 0.0D).color(0.7F, 0.0F, 0.0F, 1.0F).endVertex();
                    tessellator1.getBuffer().pos(-20D, -10 + byte0, 0.0D).color(0.0F, 0.7F, 0.0F, 1.0F).endVertex();
                    tessellator1.getBuffer().pos(-20D, -6 + byte0, 0.0D).color(0.0F, 0.7F, 0.0F, 1.0F).endVertex();
                    tessellator1.getBuffer().pos(f11 - 20F, -6 + byte0, 0.0D).color(0.0F, 0.7F, 0.0F, 1.0F).endVertex();
                    tessellator1.getBuffer().pos(f11 - 20F, -10 + byte0, 0.0D).color(0.0F, 0.7F, 0.0F, 1.0F).endVertex();
                    tessellator1.draw();

                }
                if (flag) {
                    RenderSystem.depthMask(false);
                    RenderSystem.disableDepthTest();
                    RenderSystem.enableBlend();
                    RenderSystem.defaultBlendFunc();

                    tessellator1.getBuffer().begin(7, DefaultVertexFormats.POSITION_COLOR);
                    int i = fontrenderer.getStringWidth(s) / 2;
                    tessellator1.getBuffer().pos(-i - 1, -1 + byte0, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
                    tessellator1.getBuffer().pos(-i - 1, 8 + byte0, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
                    tessellator1.getBuffer().pos(i + 1, 8 + byte0, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
                    tessellator1.getBuffer().pos(i + 1, -1 + byte0, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
                    tessellator1.draw();

                    fontrenderer.drawString(matrixStackIn, s, -fontrenderer.getStringWidth(s) / 2, byte0, 0x20ffffff);
                    RenderSystem.enableDepthTest();
                    RenderSystem.depthMask(true);
                    fontrenderer.drawString(matrixStackIn, s, -fontrenderer.getStringWidth(s) / 2, byte0, -1);
                    RenderSystem.disableBlend();
                    RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
                }

                matrixStackIn.pop();
            }
        }

    }

    @Override
    protected void preRenderCallback(MoCEntityBigCat entitybigcat, MatrixStack matrixStackIn, float f) {
        this.bigcat1.sitting = entitybigcat.getIsSitting();
        this.bigcat1.tamed = entitybigcat.getIsTamed();
        stretch(entitybigcat, matrixStackIn);
    }

    protected void stretch(MoCEntityBigCat entitybigcat, MatrixStack matrixStackIn) {
        float f = entitybigcat.getAge() * 0.01F;
        if (entitybigcat.getIsAdult()) {
            f = 1.0F;
        }
        matrixStackIn.scale(f, f, f);
    }

    // Render mane
    private class LayerMoCBigCat extends LayerRenderer<MoCEntityBigCat, MoCLegacyModelBigCat2<MoCEntityBigCat>> {

        private final MoCLegacyRenderBigCat mocRenderer;
        private final MoCLegacyModelBigCat1 mocModel = new MoCLegacyModelBigCat1();

        public LayerMoCBigCat(MoCLegacyRenderBigCat render) {
            super(render);
            this.mocRenderer = render;
        }

        public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, MoCEntityBigCat entitybigcat, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
            ResourceLocation resourcelocation;
            if (entitybigcat instanceof MoCEntityLion && entitybigcat.hasMane()) {
                if (entitybigcat.getTypeMoC() == 7) {
                    resourcelocation = MoCreatures.proxy.getModelTexture("big_cat_white_lion_legacy_layer.png");
                } else {
                    resourcelocation = MoCreatures.proxy.getModelTexture("big_cat_lion_legacy_layer_male.png");
                }
            } else {
                resourcelocation = MoCreatures.proxy.getModelTexture("big_cat_lion_legacy_layer_female.png");
            }
            this.getEntityModel().copyModelAttributesTo(this.mocModel);
            this.mocModel.setRotationAngles(entitybigcat, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
            IVertexBuilder ivertexbuilder = bufferIn.getBuffer(RenderType.getEntityCutoutNoCull(resourcelocation));
            this.mocModel.setLivingAnimations(entitybigcat, limbSwing, limbSwingAmount, partialTicks);
            this.mocModel.render(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        }
    }
}
