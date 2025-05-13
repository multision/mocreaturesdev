/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.fx;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

@OnlyIn(Dist.CLIENT)
public class MoCEntityFXUndead extends SpriteTexturedParticle {
    private final TextureAtlasSprite spriteAir;
    private final TextureAtlasSprite spriteGround;

    public MoCEntityFXUndead(ClientWorld world, double x, double y, double z, IAnimatedSprite spriteSet) {
        super(world, x, y, z, 0, 0, 0);

        this.particleGravity = 0.06F;
        this.maxAge = (int)(32.0D / (Math.random() * 0.8D + 0.2D));
        this.particleScale *= 0.8F;

        this.spriteGround = spriteSet.get(new Random()); // fx_undead1
        this.spriteAir = spriteSet.get(new Random());    // fx_undead2

        this.setSprite(spriteAir);
    }

    @Override
    public void tick() {
        super.tick();
        this.setSprite(this.onGround ? spriteGround : spriteAir);
    }

    @Override
    public IParticleRenderType getRenderType() {
        return IParticleRenderType.PARTICLE_SHEET_LIT;
    }

    public static class Factory implements IParticleFactory<BasicParticleType> {
        private final IAnimatedSprite spriteSet;

        public Factory(IAnimatedSprite spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Override
        public Particle makeParticle(BasicParticleType type, ClientWorld world, double x, double y, double z,
                                     double xSpeed, double ySpeed, double zSpeed) {
            return new MoCEntityFXUndead(world, x, y, z, spriteSet);
        }
    }
}
