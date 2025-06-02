/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.fx.impl;

import drzhark.mocreatures.client.renderer.fx.data.VacuumParticleData;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCEntityFXVacuum extends SpriteTexturedParticle {
    private final float initialScale;
    private final double startX, startY, startZ;

    public MoCEntityFXVacuum(ClientWorld world, double x, double y, double z,
                             double xSpeed, double ySpeed, double zSpeed,
                             float red, float green, float blue,
                             IAnimatedSprite spriteSet) {
        super(world, x, y, z, xSpeed, ySpeed, zSpeed);

        this.particleRed = red;
        this.particleGreen = green;
        this.particleBlue = blue;

        this.startX = x;
        this.startY = y;
        this.startZ = z;

        this.initialScale = this.particleScale = this.rand.nextFloat() * 0.2F + 0.5F;
        this.maxAge = (int)(Math.random() * 10.0D) + 30;

        this.setSprite(spriteSet.get(0, maxAge)); // static texture
    }

    @Override
    public void tick() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;

        float t = (float)this.age / (float)this.maxAge;
        float scaleFactor = -t + t * t * 2.0F;
        scaleFactor = 1.0F - scaleFactor;
        this.posX = this.startX + this.motionX * scaleFactor;
        this.posY = this.startY + this.motionY * scaleFactor + (1.0F - t);
        this.posZ = this.startZ + this.motionZ * scaleFactor;

        if (this.age++ >= this.maxAge) {
            this.setExpired();
        }
    }

    @Override
    public int getBrightnessForRender(float partialTick) {
        int base = super.getBrightnessForRender(partialTick);
        float t = (float)this.age / (float)this.maxAge;
        t = t * t * t;
        int low = base & 255;
        int high = base >> 16 & 255;
        high += (int)(t * 15.0F * 16.0F);
        if (high > 240) high = 240;
        return low | (high << 16);
    }

    @Override
    public float getScale(float partialTicks) {
        float lifeRatio = (this.age + partialTicks) / this.maxAge;
        lifeRatio = 1.0F - lifeRatio;
        lifeRatio *= lifeRatio;
        lifeRatio = 1.0F - lifeRatio;
        return this.initialScale * lifeRatio;
    }

    @Override
    public IParticleRenderType getRenderType() {
        return IParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    public static class Factory implements IParticleFactory<VacuumParticleData> {
        private final IAnimatedSprite sprite;

        public Factory(IAnimatedSprite sprite) {
            this.sprite = sprite;
        }

        @Override
        public Particle makeParticle(VacuumParticleData data, ClientWorld world, double x, double y, double z,
                                     double dx, double dy, double dz) {
            return new MoCEntityFXVacuum(world, x, y, z, dx, dy, dz, data.red, data.green, data.blue, sprite);
        }
    }
}

