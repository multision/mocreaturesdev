/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.fx.impl;

import drzhark.mocreatures.client.renderer.fx.data.StarParticleData;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCEntityFXStar extends SpriteTexturedParticle {

    public MoCEntityFXStar(ClientWorld world, double posX, double posY, double posZ,
                           float red, float green, float blue, IAnimatedSprite spriteSet) {
        super(world, posX, posY, posZ, 0.0D, 0.0D, 0.0D);

        this.motionX *= 0.8D;
        this.motionY = this.rand.nextFloat() * 0.4F + 0.05F;
        this.motionZ *= 0.8D;

        this.particleRed = red;
        this.particleGreen = green;
        this.particleBlue = blue;
        this.particleAlpha = 1.0F;

        this.setSize(0.01F, 0.01F);
        this.particleGravity = 0.06F;
        this.maxAge = (int)(64.0D / (Math.random() * 0.8D + 0.2D));
        this.particleScale *= 0.6F;

        this.selectSpriteWithAge(spriteSet);
    }

    @Override
    public void tick() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;

        this.particleScale *= 0.995F;
        this.motionY -= 0.03D;
        this.move(this.motionX, this.motionY, this.motionZ);
        this.motionX *= 0.9D;
        this.motionY *= 0.2D;
        this.motionZ *= 0.9D;

        if (this.onGround) {
            this.motionX *= 0.7D;
            this.motionZ *= 0.7D;
        }

        if (this.maxAge-- <= 0) {
            this.setExpired();
        }
    }

    @Override
    public IParticleRenderType getRenderType() {
        return IParticleRenderType.PARTICLE_SHEET_LIT;
    }

    public static class Factory implements IParticleFactory<StarParticleData> {
        private final IAnimatedSprite spriteSet;

        public Factory(IAnimatedSprite spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Override
        public Particle makeParticle(StarParticleData data, ClientWorld world, double x, double y, double z,
                                     double xSpeed, double ySpeed, double zSpeed) {
            return new MoCEntityFXStar(world, x, y, z, data.red, data.green, data.blue, spriteSet);
        }
    }

}
