package drzhark.mocreatures.client.renderer.fx.impl;

import drzhark.mocreatures.client.renderer.fx.data.VanishParticleData;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCEntityFXVanish extends SpriteTexturedParticle {

    private final double portalPosX;
    private final double portalPosY;
    private final double portalPosZ;
    private final boolean implode;

    public MoCEntityFXVanish(ClientWorld world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed,
                             float red, float green, float blue, boolean implode, IAnimatedSprite spriteSet) {
        super(world, x, y, z, 0, 0, 0);

        this.motionX = xSpeed;
        this.motionY = ySpeed * 5D;
        this.motionZ = zSpeed;

        this.portalPosX = this.posX = x;
        this.portalPosY = this.posY = y;
        this.portalPosZ = this.posZ = z;

        this.implode = implode;
        this.maxAge = (int)(Math.random() * 10.0D) + 70;

        this.particleRed = red;
        this.particleGreen = green;
        this.particleBlue = blue;
        this.particleAlpha = 1.0F;

        this.setSprite(spriteSet.get(0, maxAge));
    }

    @Override
    public void tick() {
        super.tick();

        int speeder = implode ? (this.maxAge / 2) : 0;
        float sizeExp = implode ? 5.0F : 2.0F;

        float var1 = (float) (this.age + speeder) / (float) this.maxAge;
        float var2 = var1;
        var1 = -var1 + var1 * var1 * sizeExp;
        var1 = 1.0F - var1;

        this.posX = this.portalPosX + this.motionX * var1;
        this.posY = this.portalPosY + this.motionY * var1 + (1.0F - var2);
        this.posZ = this.portalPosZ + this.motionZ * var1;

        if (this.age++ >= this.maxAge) {
            this.setExpired();
        }
    }

    @Override
    public IParticleRenderType getRenderType() {
        return IParticleRenderType.PARTICLE_SHEET_LIT;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Factory implements IParticleFactory<VanishParticleData> {
        private final IAnimatedSprite spriteSet;

        public Factory(IAnimatedSprite sprite) {
            this.spriteSet = sprite;
        }

        @Override
        public Particle makeParticle(VanishParticleData data, ClientWorld world,
                                     double x, double y, double z,
                                     double xSpeed, double ySpeed, double zSpeed) {
            return new MoCEntityFXVanish(world, x, y, z,
                    xSpeed, ySpeed, zSpeed,
                    data.red, data.green, data.blue, data.implode,
                    spriteSet);
        }
    }

}
