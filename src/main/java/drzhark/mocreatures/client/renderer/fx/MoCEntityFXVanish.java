/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.fx;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCEntityFXVanish extends SpriteTexturedParticle {

    private final double portalPosX;
    private final double portalPosY;
    private final double portalPosZ;
    private final boolean implode;

    public MoCEntityFXVanish(ClientWorld par1World, double par2, double par4, double par6, double par8, double par10, double par12, float red, float green,
                             float blue, boolean flag) {
        super(par1World, par2, par4, par6, 0.0D, 0.0D, 0.0D);

        this.particleRed = red;
        this.particleGreen = green;
        this.particleBlue = blue;
        this.motionX = par8;
        this.motionY = par10 * 5D;
        this.motionZ = par12;
        this.portalPosX = this.posX = par2;
        this.portalPosY = this.posY = par4;// + 0.7D;
        this.portalPosZ = this.posZ = par6;
        this.implode = flag;
        this.maxAge = (int) (Math.random() * 10.0D) + 70;
    }

    /**
     * sets which texture to use (2 = items.png)
     */
    public IParticleRenderType getRenderType() {
        return IParticleRenderType.TERRAIN_SHEET;
    }

    /**
     * Called to update the entity's position/logic.
     */
    @Override
    public void tick() {
        super.tick();
        //this.setSprite(sprite);
    }

    @OnlyIn(Dist.CLIENT)
    public static class Factory implements IParticleFactory<BasicParticleType> {
        private final IAnimatedSprite spriteSet;

        public Factory(IAnimatedSprite spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Override
        public Particle makeParticle(BasicParticleType type, ClientWorld world, double x, double y, double z,
                                     double xSpeed, double ySpeed, double zSpeed) {
            MoCEntityFXVanish particle = new MoCEntityFXVanish(world, x, y, z, xSpeed, ySpeed, zSpeed, 1F, 1F, 1F, false);
            particle.selectSpriteRandomly(spriteSet);
            return particle;
        }
    }

}
