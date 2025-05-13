/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.IMoCEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.ForgeRenderTypes;

@OnlyIn(Dist.CLIENT)
public class MoCRenderMoC<T extends MobEntity, M extends EntityModel<T>> extends MobRenderer<T, M> {

    private static class Internal extends RenderType{
        private Internal(String name, VertexFormat fmt, int glMode, int size, boolean doCrumbling, boolean depthSorting, Runnable onEnable, Runnable onDisable)
        {
            super(name, fmt, glMode, size, doCrumbling, depthSorting, onEnable, onDisable);
            throw new IllegalStateException("This class must not be instantiated");
        }

        public static RenderType getHealth() {
            RenderType.State rendertype$state = RenderType.State.getBuilder()
                    .alpha(RenderState.DEFAULT_ALPHA)
                    .transparency(RenderState.TRANSLUCENT_TRANSPARENCY)
                    .build(false);
            return makeType("health", DefaultVertexFormats.POSITION_COLOR, 7, 256, false, true, rendertype$state);
        }
    }

    private float prevPitch;
    private float prevRoll;
    private float prevYaw;

    public MoCRenderMoC(EntityRendererManager renderManagerIn, M modelbase, float f) {
        super(renderManagerIn, modelbase, f);
    }

    @Override
    public void render(T entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        renderMoC(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    public void renderMoC(T entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
        IMoCEntity entityMoC = (IMoCEntity) entityIn;
        boolean flag = MoCreatures.proxy.getDisplayPetName() && !(entityMoC.getPetName().isEmpty());
        boolean flag1 = MoCreatures.proxy.getDisplayPetHealth();
        if (entityMoC.getIsTamed()) {
            float f2 = 1.6F;
            float f3 = 0.01666667F * f2;
            float f5 = (float) this.renderManager.squareDistanceTo((Entity) entityMoC);
            if (f5 < 256F) {
                String s = "";
                s = s + entityMoC.getPetName();
                float f7 = 0.1F;
                FontRenderer fontrenderer = getFontRendererFromRenderManager();
                matrixStackIn.push();
                matrixStackIn.translate(0.0F, f7, 0.0F);
                RenderSystem.normal3f(0.0F, 1.0F, 0.0F);
                matrixStackIn.rotate(Vector3f.YP.rotationDegrees(-this.renderManager.info.getYaw()));
                matrixStackIn.scale(-f3, -f3, f3);
                int yOff = entityMoC.nameYOffset();
                if (flag1) {

                    if (!flag) {
                        yOff += 8;
                    }
                    Matrix4f matrix = matrixStackIn.getLast().getMatrix();
                    IVertexBuilder ivertexbuilder = bufferIn.getBuffer(Internal.getHealth());
                    // might break SSP
                    float f8 = ((MobEntity) entityMoC).getHealth();
                    float f9 = ((MobEntity) entityMoC).getMaxHealth();
                    float f10 = f8 / f9;
                    float f11 = 40F * f10;
                    ivertexbuilder.pos(matrix, -20F + f11, -10 + yOff, 0.0F).color(0.7F, 0.0F, 0.0F, 1.0F).endVertex();
                    ivertexbuilder.pos(matrix, -20F + f11, -6 + yOff, 0.0F).color(0.7F, 0.0F, 0.0F, 1.0F).endVertex();
                    ivertexbuilder.pos(matrix, 20F, -6 + yOff, 0.0F).color(0.7F, 0.0F, 0.0F, 1.0F).endVertex();
                    ivertexbuilder.pos(matrix, 20F, -10 + yOff, 0.0F).color(0.7F, 0.0F, 0.0F, 1.0F).endVertex();
                    ivertexbuilder.pos(matrix, -20F, -10 + yOff, 0.0F).color(0.0F, 0.7F, 0.0F, 1.0F).endVertex();
                    ivertexbuilder.pos(matrix, -20F, -6 + yOff, 0.0F).color(0.0F, 0.7F, 0.0F, 1.0F).endVertex();
                    ivertexbuilder.pos(matrix, f11 - 20F, -6 + yOff, 0.0F).color(0.0F, 0.7F, 0.0F, 1.0F).endVertex();
                    ivertexbuilder.pos(matrix, f11 - 20F, -10 + yOff, 0.0F).color(0.0F, 0.7F, 0.0F, 1.0F).endVertex();

                }
                if (flag) {
                    Matrix4f matrix4f = matrixStackIn.getLast().getMatrix();
                    float f1 = Minecraft.getInstance().gameSettings.getTextBackgroundOpacity(0.25F);
                    int j = (int)(f1 * 255.0F) << 24;
                    float i = (float)(-fontrenderer.getStringPropertyWidth(new StringTextComponent(s)) / 2);
                    fontrenderer.func_243247_a(new StringTextComponent(s), i, yOff, 553648127, false, matrix4f, bufferIn, true, j, packedLightIn);
                    fontrenderer.func_243247_a(new StringTextComponent(s), i, yOff, -1, false, matrix4f, bufferIn, false, 0, packedLightIn);
                }
                matrixStackIn.pop();
            }
        }
    }

    protected void stretch(IMoCEntity mocreature, MatrixStack matrixStackIn) {
        float f = mocreature.getSizeFactor();
        if (f != 0) {
            matrixStackIn.scale(f, f, f);
        }
    }

    @Override
    protected void preRenderCallback(T entityliving, MatrixStack matrixStackIn, float f) {
        IMoCEntity mocreature = (IMoCEntity) entityliving;
        super.preRenderCallback(entityliving, matrixStackIn, f);
        // Interpolation factor for smoother animations
        float interpolationFactor = 0.05F;
        // Interpolate pitch, roll, and yaw
        float interpolatedPitch = prevPitch + (mocreature.pitchRotationOffset() - prevPitch) * interpolationFactor;
        float interpolatedRoll = prevRoll + (mocreature.rollRotationOffset() - prevRoll) * interpolationFactor;
        float interpolatedYaw = prevYaw + (mocreature.yawRotationOffset() - prevYaw) * interpolationFactor;
        // Apply the interpolated transformations
        if (interpolatedPitch != 0) {
            matrixStackIn.rotate(Vector3f.XN.rotationDegrees(interpolatedPitch));
        }
        if (interpolatedRoll != 0) {
            matrixStackIn.rotate(Vector3f.ZN.rotationDegrees(interpolatedRoll));
        }
        if (interpolatedYaw != 0) {
            matrixStackIn.rotate(Vector3f.YN.rotationDegrees(interpolatedYaw));
        }
        // Save the current values for the next frame's interpolation
        prevPitch = interpolatedPitch;
        prevRoll = interpolatedRoll;
        prevYaw = interpolatedYaw;
        adjustPitch(mocreature, matrixStackIn);
        adjustRoll(mocreature, matrixStackIn);
        adjustYaw(mocreature, matrixStackIn);
        stretch(mocreature, matrixStackIn);
    }

    /**
     * Tilts the creature to the front / back
     */
    protected void adjustPitch(IMoCEntity mocreature, MatrixStack matrixStackIn) {
        float f = mocreature.pitchRotationOffset();

        if (f != 0) {
            matrixStackIn.rotate(Vector3f.XN.rotationDegrees(f));
        }
    }

    /**
     * Rolls creature
     */
    protected void adjustRoll(IMoCEntity mocreature, MatrixStack matrixStackIn) {
        float f = mocreature.rollRotationOffset();

        if (f != 0) {
            matrixStackIn.rotate(Vector3f.ZN.rotationDegrees(f));
        }
    }

    protected void adjustYaw(IMoCEntity mocreature, MatrixStack matrixStackIn) {
        float f = mocreature.yawRotationOffset();
        if (f != 0) {
            matrixStackIn.rotate(Vector3f.YN.rotationDegrees(f));
        }
    }

    /**
     * translates the model
     */
    protected void adjustOffsets(float xOffset, float yOffset, float zOffset, MatrixStack matrixStackIn) {
        matrixStackIn.translate(xOffset, yOffset, zOffset);
    }

    @Override
    public ResourceLocation getEntityTexture(MobEntity entity) {
        return ((IMoCEntity) entity).getTexture();
    }
}
