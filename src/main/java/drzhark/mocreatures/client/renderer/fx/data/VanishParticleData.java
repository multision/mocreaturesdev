package drzhark.mocreatures.client.renderer.fx.data;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import drzhark.mocreatures.client.renderer.fx.MoCParticles;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;

import java.util.Locale;

public class VanishParticleData implements IParticleData {
    public static final IParticleData.IDeserializer<VanishParticleData> DESERIALIZER = new IParticleData.IDeserializer<VanishParticleData>() {
        @Override
        public VanishParticleData deserialize(ParticleType<VanishParticleData> type, StringReader reader) throws CommandSyntaxException {
            reader.expect(' ');
            float r = reader.readFloat();
            reader.expect(' ');
            float g = reader.readFloat();
            reader.expect(' ');
            float b = reader.readFloat();
            reader.expect(' ');
            boolean implode = reader.readBoolean();
            return new VanishParticleData(r, g, b, implode);
        }

        @Override
        public VanishParticleData read(ParticleType<VanishParticleData> type, PacketBuffer buffer) {
            return new VanishParticleData(buffer.readFloat(), buffer.readFloat(), buffer.readFloat(), buffer.readBoolean());
        }
    };

    public final float red, green, blue;
    public final boolean implode;

    public VanishParticleData(float red, float green, float blue, boolean implode) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.implode = implode;
    }

    @Override
    public void write(PacketBuffer buffer) {
        buffer.writeFloat(red);
        buffer.writeFloat(green);
        buffer.writeFloat(blue);
        buffer.writeBoolean(implode);
    }

    @Override
    public String getParameters() {
        return String.format(Locale.ROOT, "%f %f %f %b", red, green, blue, implode);
    }

    @Override
    public ParticleType<?> getType() {
        return MoCParticles.VANISH_FX.get();
    }
}
