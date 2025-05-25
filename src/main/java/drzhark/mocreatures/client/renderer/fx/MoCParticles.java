package drzhark.mocreatures.client.renderer.fx;

import com.mojang.serialization.Codec;
import drzhark.mocreatures.client.renderer.fx.data.StarParticleData;
import drzhark.mocreatures.client.renderer.fx.data.VacuumParticleData;
import drzhark.mocreatures.client.renderer.fx.data.VanishParticleData;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = "mocreatures", bus = Mod.EventBusSubscriber.Bus.MOD)
public class MoCParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLES =
            DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, "mocreatures");

    public static final RegistryObject<BasicParticleType> UNDEAD_FX =
            PARTICLES.register("undead_fx", () -> new BasicParticleType(false));

    public static final RegistryObject<ParticleType<VanishParticleData>> VANISH_FX =
            PARTICLES.register("vanish_fx", () -> new ParticleType<VanishParticleData>(false, VanishParticleData.DESERIALIZER) {
                @Override
                public Codec<VanishParticleData> func_230522_e_() {
                    return Codec.unit(new VanishParticleData(1.0F, 1.0F, 1.0F, false));
                }
            });

    public static final RegistryObject<ParticleType<StarParticleData>> STAR_FX =
            PARTICLES.register("star_fx", () -> new ParticleType<StarParticleData>(false, StarParticleData.DESERIALIZER) {
                @Override
                public Codec<StarParticleData> func_230522_e_() {
                    return Codec.unit(new StarParticleData(1.0F, 1.0F, 1.0F)); // default white
                }
            });

    public static final RegistryObject<ParticleType<VacuumParticleData>> VACUUM_FX =
            PARTICLES.register("vacuum_fx", () -> new ParticleType<VacuumParticleData>(false, VacuumParticleData.DESERIALIZER) {
                @Override
                public Codec<VacuumParticleData> func_230522_e_() {
                    return Codec.unit(new VacuumParticleData(1.0F, 1.0F, 1.0F));
                }
            });


}