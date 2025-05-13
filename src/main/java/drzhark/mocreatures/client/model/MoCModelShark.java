/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import drzhark.mocreatures.entity.aquatic.MoCEntityShark;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

public class MoCModelShark<T extends MoCEntityShark> extends EntityModel<T> {
    private final ModelRenderer body;
    private final ModelRenderer torso_1;
    private final ModelRenderer neck;
    private final ModelRenderer head;
    private final ModelRenderer upper_head;
    private final ModelRenderer jaw_base_r1;
    private final ModelRenderer teeth_row_left_r1;
    private final ModelRenderer teeth_row_front_r1;
    private final ModelRenderer upper_skull_r1;
    private final ModelRenderer nose_bot_r1;
    private final ModelRenderer lower_jaw;
    private final ModelRenderer teeth_row_front_r2;
    private final ModelRenderer dorsal_fin;
    private final ModelRenderer dorsal_fin_d_r1;
    private final ModelRenderer dorsal_fin_c_r1;
    private final ModelRenderer dorsal_fin_b_r1;
    private final ModelRenderer dorsal_fin_a_r1;
    private final ModelRenderer pectoral_fins;
    private final ModelRenderer left_fin;
    private final ModelRenderer left_fin_d_r1;
    private final ModelRenderer left_fin_a_r1;
    private final ModelRenderer left_fin_a_r2;
    private final ModelRenderer right_fin;
    private final ModelRenderer right_fin_a_r1;
    private final ModelRenderer right_fin_a_r2;
    private final ModelRenderer right_fin_d_r1;
    private final ModelRenderer torso_2;
    private final ModelRenderer pelvic_fin_right_r1;
    private final ModelRenderer pelvic_fin_left_r1;
    private final ModelRenderer tail;
    private final ModelRenderer caudal_fin;
    private final ModelRenderer caudal_fin_bot_c_r1;
    private final ModelRenderer caudal_fin_bot_b_r1;
    private final ModelRenderer caudal_fin_bot_a_r1;
    private final ModelRenderer caudal_fin_top_d_r1;
    private final ModelRenderer caudal_fin_top_c_r1;
    private final ModelRenderer caudal_fin_top_b_r1;
    private final ModelRenderer caudal_fin_top_a_r1;

    public MoCModelShark() {
        textureWidth = 256;
        textureHeight = 256;

        body = new ModelRenderer(this);
        body.setRotationPoint(-0.5F, 16.0F, -3.0F);


        torso_1 = new ModelRenderer(this);
        torso_1.setRotationPoint(0.0F, -4.0F, 16.0F);
        body.addChild(torso_1);
        torso_1.setTextureOffset(0, 0);
        torso_1.addBox(-10.5F, -9.0F, -30.0F, 22, 20, 33, 0.0F, false);

        neck = new ModelRenderer(this);
        neck.setRotationPoint(-0.5F, 2.0F, -30.0F);
        torso_1.addChild(neck);
        neck.setTextureOffset(56, 73);
        neck.addBox(-9.0F, -10.0F, -14.0F, 20, 18, 15, 0.0F, false);

        head = new ModelRenderer(this);
        head.setRotationPoint(-1.5F, 0.0F, -14.0F);
        neck.addChild(head);


        upper_head = new ModelRenderer(this);
        upper_head.setRotationPoint(1.15F, -5.5F, 30.5F);
        head.addChild(upper_head);
        upper_head.setTextureOffset(89, 24);
        upper_head.addBox(7.451F, 4.5311F, -43.1503F, 0, 4, 4, 0.0F, true);
        upper_head.addBox(-4.749F, 4.5311F, -43.1503F, 0, 4, 4, 0.0F, true);

        jaw_base_r1 = new ModelRenderer(this);
        jaw_base_r1.setRotationPoint(1.351F, 5.5014F, -37.2182F);
        upper_head.addChild(jaw_base_r1);
        setRotation(jaw_base_r1, 1.5708F, 0.0F, 0.0F);
        jaw_base_r1.setTextureOffset(160, 4);
        jaw_base_r1.addBox(-7.001F, -1.9315F, -4.8207F, 14, 9, 8, 0.0F, false);
        jaw_base_r1.setTextureOffset(0, 0);
        jaw_base_r1.addBox(-5.9794F, -10.9315F, 2.1793F, 12, 20, 3, 0.0F, false);

        teeth_row_left_r1 = new ModelRenderer(this);
        teeth_row_left_r1.setRotationPoint(2.351F, 7.5014F, -41.2182F);
        upper_head.addChild(teeth_row_left_r1);
        setRotation(teeth_row_left_r1, 0.0F, 0.0F, 0.0F);
        teeth_row_left_r1.setTextureOffset(8, 92);
        teeth_row_left_r1.addBox(5.0F, -3.5704F, -5.5322F, 0, 3, 8, 0.0F, false);
        teeth_row_left_r1.addBox(-7.0F, -3.5704F, -5.5322F, 0, 3, 8, 0.0F, false);

        teeth_row_front_r1 = new ModelRenderer(this);
        teeth_row_front_r1.setRotationPoint(1.351F, 8.5014F, -41.2182F);
        upper_head.addChild(teeth_row_front_r1);
        setRotation(teeth_row_front_r1, 0.0F, 1.5708F, 0.0F);
        teeth_row_front_r1.setTextureOffset(1, 80);
        teeth_row_front_r1.addBox(5.4612F, -4.5704F, -6.072F, 0, 3, 12, 0.0F, false);

        upper_skull_r1 = new ModelRenderer(this);
        upper_skull_r1.setRotationPoint(1.351F, 5.5014F, -36.7182F);
        upper_head.addChild(upper_skull_r1);
        setRotation(upper_skull_r1, 1.7017F, 0.0F, 0.0F);
        upper_skull_r1.setTextureOffset(78, 0);
        upper_skull_r1.addBox(-7.9794F, -11.3154F, 1.1579F, 16, 19, 7, 0.0F, false);
        upper_skull_r1.setTextureOffset(68, 107);
        upper_skull_r1.addBox(-7.9794F, -16.3154F, 5.1579F, 16, 5, 3, 0.0F, false);

        nose_bot_r1 = new ModelRenderer(this);
        nose_bot_r1.setRotationPoint(1.3716F, 2.5064F, -53.5873F);
        upper_head.addChild(nose_bot_r1);
        setRotation(nose_bot_r1, 1.1083F, 0.0F, 0.0F);
        nose_bot_r1.setTextureOffset(129, 1);
        nose_bot_r1.addBox(-8.0F, 0.0207F, -0.0117F, 16, 7, 3, -0.02F, false);

        lower_jaw = new ModelRenderer(this);
        lower_jaw.setRotationPoint(2.4828F, 1.6811F, -8.3576F);
        head.addChild(lower_jaw);
        setRotation(lower_jaw, 0.1309F, 0.0F, 0.0F);
        lower_jaw.setTextureOffset(55, 57);
        lower_jaw.addBox(-5.9828F, -0.25F, -6.8927F, 12, 3, 10, 0.05F, false);
        lower_jaw.setTextureOffset(10, 89);
        lower_jaw.addBox(5.0182F, -3.25F, -5.8927F, 0, 3, 7, 0.0F, false);
        lower_jaw.addBox(-4.9818F, -3.25F, -5.8927F, 0, 3, 7, 0.0F, false);

        teeth_row_front_r2 = new ModelRenderer(this);
        teeth_row_front_r2.setRotationPoint(-0.4828F, 2.7552F, 10.1978F);
        lower_jaw.addChild(teeth_row_front_r2);
        setRotation(teeth_row_front_r2, 0.0F, 1.5708F, 0.0F);
        teeth_row_front_r2.setTextureOffset(4, 78);
        teeth_row_front_r2.addBox(16.0196F, -6.0052F, -4.571F, 0, 3, 10, 0.0F, false);

        dorsal_fin = new ModelRenderer(this);
        dorsal_fin.setRotationPoint(0.5F, -20.1292F, -6.2277F);
        torso_1.addChild(dorsal_fin);


        dorsal_fin_d_r1 = new ModelRenderer(this);
        dorsal_fin_d_r1.setRotationPoint(-9.0F, 18.324F, -2.6875F);
        dorsal_fin.addChild(dorsal_fin_d_r1);
        setRotation(dorsal_fin_d_r1, 1.5708F, 0.0F, 0.0F);
        dorsal_fin_d_r1.setTextureOffset(111, 39);
        dorsal_fin_d_r1.addBox(8.0F, -5.1949F, 7.2152F, 2, 7, 9, 0.01F, false);

        dorsal_fin_c_r1 = new ModelRenderer(this);
        dorsal_fin_c_r1.setRotationPoint(0.0F, 10.7262F, 3.0681F);
        dorsal_fin.addChild(dorsal_fin_c_r1);
        setRotation(dorsal_fin_c_r1, 1.8326F, 0.0F, 0.0F);
        dorsal_fin_c_r1.setTextureOffset(170, 28);
        dorsal_fin_c_r1.addBox(-1.0F, -4.0F, -1.0F, 2, 4, 12, 0.0F, false);

        dorsal_fin_b_r1 = new ModelRenderer(this);
        dorsal_fin_b_r1.setRotationPoint(0.0F, 7.6647F, -2.5701F);
        dorsal_fin.addChild(dorsal_fin_b_r1);
        setRotation(dorsal_fin_b_r1, 0.6981F, 0.0F, 0.0F);
        dorsal_fin_b_r1.setTextureOffset(161, 22);
        dorsal_fin_b_r1.addBox(-1.0F, -8.0F, -1.0F, 2, 4, 8, -0.01F, false);

        dorsal_fin_a_r1 = new ModelRenderer(this);
        dorsal_fin_a_r1.setRotationPoint(0.0F, 12.0248F, -8.2271F);
        dorsal_fin.addChild(dorsal_fin_a_r1);
        setRotation(dorsal_fin_a_r1, 1.1781F, 0.0F, 0.0F);
        dorsal_fin_a_r1.setTextureOffset(143, 25);
        dorsal_fin_a_r1.addBox(-1.0F, -4.0F, -1.0F, 2, 4, 10, 0.0F, false);

        pectoral_fins = new ModelRenderer(this);
        pectoral_fins.setRotationPoint(0.0F, 0.0F, 0.0F);
        torso_1.addChild(pectoral_fins);


        left_fin = new ModelRenderer(this);
        left_fin.setRotationPoint(11.5F, 7.0F, -21.0F);
        pectoral_fins.addChild(left_fin);
        setRotation(left_fin, 0.0F, 0.0F, -0.7854F);


        left_fin_d_r1 = new ModelRenderer(this);
        left_fin_d_r1.setRotationPoint(0.3933F, -2.7076F, -1.1278F);
        left_fin.addChild(left_fin_d_r1);
        setRotation(left_fin_d_r1, 0.3316F, 0.0F, 0.0F);
        left_fin_d_r1.setTextureOffset(225, 0);
        left_fin_d_r1.addBox(-1.0F, 0.3827F, -3.0761F, 2, 19, 5, -0.01F, false);

        left_fin_a_r1 = new ModelRenderer(this);
        left_fin_a_r1.setRotationPoint(0.3933F, 12.7347F, -9.897F);
        left_fin.addChild(left_fin_a_r1);
        setRotation(left_fin_a_r1, 0.9076F, 0.0F, 0.0F);
        left_fin_a_r1.setTextureOffset(216, 23);
        left_fin_a_r1.addBox(-1.0F, 3.9561F, 4.8879F, 2, 11, 4, 0.0F, false);

        left_fin_a_r2 = new ModelRenderer(this);
        left_fin_a_r2.setRotationPoint(0.3933F, -1.6235F, -5.2607F);
        left_fin.addChild(left_fin_a_r2);
        setRotation(left_fin_a_r2, 0.3316F, 0.0F, 0.0F);
        left_fin_a_r2.setTextureOffset(200, 17);
        left_fin_a_r2.addBox(-1.0F, -1.2777F, -2.8042F, 2, 14, 5, 0.01F, false);

        right_fin = new ModelRenderer(this);
        right_fin.setRotationPoint(-10.5F, 6.0F, -21.0F);
        pectoral_fins.addChild(right_fin);
        setRotation(right_fin, 0.0F, 0.0F, 0.7854F);


        right_fin_a_r1 = new ModelRenderer(this);
        right_fin_a_r1.setRotationPoint(0.9246F, -1.4211F, -5.2607F);
        right_fin.addChild(right_fin_a_r1);
        setRotation(right_fin_a_r1, 0.3316F, 0.0F, 0.0F);
        right_fin_a_r1.setTextureOffset(200, 17);
        right_fin_a_r1.addBox(-1.0F, -1.2777F, -2.8042F, 2, 14, 5, 0.01F, false);

        right_fin_a_r2 = new ModelRenderer(this);
        right_fin_a_r2.setRotationPoint(0.9246F, 12.9372F, -9.897F);
        right_fin.addChild(right_fin_a_r2);
        setRotation(right_fin_a_r2, 0.9076F, 0.0F, 0.0F);
        right_fin_a_r2.setTextureOffset(216, 23);
        right_fin_a_r2.addBox(-1.0F, 3.9561F, 4.8879F, 2, 11, 4, 0.0F, false);

        right_fin_d_r1 = new ModelRenderer(this);
        right_fin_d_r1.setRotationPoint(0.9246F, -2.5052F, -1.1278F);
        right_fin.addChild(right_fin_d_r1);
        setRotation(right_fin_d_r1, 0.3316F, 0.0F, 0.0F);
        right_fin_d_r1.setTextureOffset(225, 0);
        right_fin_d_r1.addBox(-1.0F, 0.3827F, -3.0761F, 2, 19, 5, -0.01F, true);

        torso_2 = new ModelRenderer(this);
        torso_2.setRotationPoint(-0.5F, 0.0F, 2.0F);
        torso_1.addChild(torso_2);
        torso_2.setTextureOffset(0, 54);
        torso_2.addBox(-8.0F, -8.0F, 0.0F, 18, 16, 17, 0.0F, false);

        pelvic_fin_right_r1 = new ModelRenderer(this);
        pelvic_fin_right_r1.setRotationPoint(-5.0F, 7.0F, 10.5F);
        torso_2.addChild(pelvic_fin_right_r1);
        setRotation(pelvic_fin_right_r1, 0.0F, 0.0F, 0.3927F);
        pelvic_fin_right_r1.setTextureOffset(0, 16);
        pelvic_fin_right_r1.addBox(1.5F, 0.0F, -4.5F, 0, 6, 9, 0.0F, false);

        pelvic_fin_left_r1 = new ModelRenderer(this);
        pelvic_fin_left_r1.setRotationPoint(6.0F, 7.0F, 10.5F);
        torso_2.addChild(pelvic_fin_left_r1);
        setRotation(pelvic_fin_left_r1, 0.0F, 0.0F, -0.3927F);
        pelvic_fin_left_r1.setTextureOffset(0, 16);
        pelvic_fin_left_r1.addBox(-1.5F, 0.0F, -4.5F, 0, 6, 9, 0.0F, false);

        tail = new ModelRenderer(this);
        tail.setRotationPoint(0.5F, -1.0F, 17.0F);
        torso_2.addChild(tail);
        tail.setTextureOffset(0, 88);
        tail.addBox(-3.5F, -6.2608F, -4.1248F, 8, 10, 25, 0.0F, false);
        tail.setTextureOffset(78, 22);
        tail.addBox(0.5F, -10.8811F, 0.3854F, 0, 5, 5, 0.0F, false);
        tail.setTextureOffset(10, 98);
        tail.addBox(0.5F, 3.35F, 4.3854F, 0, 7, 7, 0.0F, false);

        caudal_fin = new ModelRenderer(this);
        caudal_fin.setRotationPoint(0.25F, 0.229F, 69.8752F);
        tail.addChild(caudal_fin);
        caudal_fin.setTextureOffset(148, 69);
        caudal_fin.addBox(-2.75F, -4.1371F, -50.1669F, 6, 6, 9, 0.0F, false);

        caudal_fin_bot_c_r1 = new ModelRenderer(this);
        caudal_fin_bot_c_r1.setRotationPoint(0.25F, 5.2995F, -40.2294F);
        caudal_fin.addChild(caudal_fin_bot_c_r1);
        setRotation(caudal_fin_bot_c_r1, -1.1781F, 0.0F, 0.0F);
        caudal_fin_bot_c_r1.setTextureOffset(189, 49);
        caudal_fin_bot_c_r1.addBox(-1.0F, -3.8859F, -6.906F, 2, 4, 15, 0.0F, false);

        caudal_fin_bot_b_r1 = new ModelRenderer(this);
        caudal_fin_bot_b_r1.setRotationPoint(0.25F, 5.2995F, -40.2294F);
        caudal_fin.addChild(caudal_fin_bot_b_r1);
        setRotation(caudal_fin_bot_b_r1, -0.7418F, 0.0F, 0.0F);
        caudal_fin_bot_b_r1.setTextureOffset(146, 45);
        caudal_fin_bot_b_r1.addBox(-0.99F, -0.0384F, -9.3692F, 2, 4, 19, 0.02F, false);

        caudal_fin_bot_a_r1 = new ModelRenderer(this);
        caudal_fin_bot_a_r1.setRotationPoint(0.25F, 5.2995F, -40.2294F);
        caudal_fin.addChild(caudal_fin_bot_a_r1);
        setRotation(caudal_fin_bot_a_r1, -1.1781F, 0.0F, 0.0F);
        caudal_fin_bot_a_r1.setTextureOffset(129, 13);
        caudal_fin_bot_a_r1.addBox(-1.01F, 0.1241F, -8.396F, 2, 4, 8, -0.01F, false);

        caudal_fin_top_d_r1 = new ModelRenderer(this);
        caudal_fin_top_d_r1.setRotationPoint(1.24F, -7.2547F, -40.024F);
        caudal_fin.addChild(caudal_fin_top_d_r1);
        setRotation(caudal_fin_top_d_r1, 0.9599F, 0.0F, 0.0F);
        caudal_fin_top_d_r1.setTextureOffset(113, 27);
        caudal_fin_top_d_r1.addBox(-2.0F, -3.5F, 3.0F, 2, 3, 6, 0.01F, false);

        caudal_fin_top_c_r1 = new ModelRenderer(this);
        caudal_fin_top_c_r1.setRotationPoint(1.25F, -19.9763F, -26.8568F);
        caudal_fin.addChild(caudal_fin_top_c_r1);
        setRotation(caudal_fin_top_c_r1, 0.9599F, 0.0F, 0.0F);
        caudal_fin_bot_c_r1.setTextureOffset(171, 45);
        caudal_fin_top_c_r1.addBox(-2.0F, -3.9791F, -14.9833F, 2, 4, 14, 0.0F, false);

        caudal_fin_top_b_r1 = new ModelRenderer(this);
        caudal_fin_top_b_r1.setRotationPoint(1.26F, -16.7717F, -35.6639F);
        caudal_fin.addChild(caudal_fin_top_b_r1);
        setRotation(caudal_fin_top_b_r1, 0.5236F, 0.0F, 0.0F);
        caudal_fin_top_b_r1.setTextureOffset(124, 41);
        caudal_fin_top_b_r1.addBox(-2.02F, -2.0F, -9.0F, 2, 4, 18, 0.03F, false);

        caudal_fin_top_a_r1 = new ModelRenderer(this);
        caudal_fin_top_a_r1.setRotationPoint(0.25F, -4.3334F, -46.33F);
        caudal_fin.addChild(caudal_fin_top_a_r1);
        setRotation(caudal_fin_top_a_r1, 0.9599F, 0.0F, 0.0F);
        caudal_fin_bot_a_r1.setTextureOffset(112, 65);
        caudal_fin_top_a_r1.addBox(-1.0F, -4.0F, -3.0F, 2, 11, 12, 0.01F, false);
    }

    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        body.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }

    public void setRotation(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }

    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.tail.rotateAngleY = (MathHelper.cos(limbSwing * 0.6662F) * limbSwingAmount) / 4;
    }
}
