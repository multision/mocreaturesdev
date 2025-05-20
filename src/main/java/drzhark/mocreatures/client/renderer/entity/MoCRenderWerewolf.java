/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.client.model.MoCModelWerehuman;
import drzhark.mocreatures.client.model.MoCModelWerewolf;
import drzhark.mocreatures.entity.hostile.MoCEntityWerewolf;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCRenderWerewolf<M extends EntityModel<MoCEntityWerewolf>> extends MobRenderer<MoCEntityWerewolf, M> {

    private final MoCModelWerewolf tempWerewolf;

    public MoCRenderWerewolf(EntityRendererManager renderManagerIn, MoCModelWerehuman modelwerehuman, M modelbase, float f) {
        super(renderManagerIn, modelbase, f);
        this.tempWerewolf = (MoCModelWerewolf) modelbase;
        this.addLayer(new LayerMoCWereHuman(this));
    }

    @Override
    public void render(MoCEntityWerewolf entity, float entityYaw, float partialTicks,
                       MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        this.tempWerewolf.hunched = entity.getIsHunched();
        super.render(entity, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    @Override
    public ResourceLocation getEntityTexture(MoCEntityWerewolf entity) {
        return entity.getTexture();
    }

    private class LayerMoCWereHuman extends LayerRenderer<MoCEntityWerewolf, M> {

        private final MoCRenderWerewolf<M> renderer;
        private final MoCModelWerehuman<MoCEntityWerewolf> humanModel = new MoCModelWerehuman<>();

        public LayerMoCWereHuman(MoCRenderWerewolf<M> renderer) {
            super(renderer);
            this.renderer = renderer;
        }

        @Override
        public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn,
                           MoCEntityWerewolf entity, float limbSwing, float limbSwingAmount, float partialTicks,
                           float ageInTicks, float netHeadYaw, float headPitch) {
            if (!entity.getIsHumanForm()) return;

            // Pick correct texture
            ResourceLocation texture;
            switch (entity.getTypeMoC()) {
                case 1:
                    texture = MoCreatures.proxy.getModelTexture("werehuman_dude.png");
                    break;
                case 2:
                    texture = MoCreatures.proxy.getModelTexture("werehuman_classic.png");
                    break;
                case 4:
                    texture = MoCreatures.proxy.getModelTexture("werehuman_woman.png");
                    break;
                default:
                    texture = MoCreatures.proxy.getModelTexture("werehuman_oldie.png");
            }

            // Sync animation and pose
            humanModel.setRotationAngles(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
            humanModel.setLivingAnimations(entity, limbSwing, limbSwingAmount, partialTicks);
            this.getEntityModel().copyModelAttributesTo(humanModel); // ✅ CORRECT direction

            matrixStackIn.push();

            // ✅ Optional scale fix
            //matrixStackIn.translate(0.0D, -0.05D, 0.0D); // Try -0.1D or -0.0625D if needed

            matrixStackIn.scale(1.0F, 1.0F, 1.0F); // Adjust if needed

            IVertexBuilder ivertexbuilder = bufferIn.getBuffer(RenderType.getEntityCutoutNoCull(texture));
            humanModel.render(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY,
                    1.0F, 1.0F, 1.0F, 1.0F);

            matrixStackIn.pop();
        }
    }
}
