package drzhark.mocreatures.client.renderer.fx.data;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import drzhark.mocreatures.client.renderer.fx.MoCParticles;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;

import java.util.Locale;

public class StarParticleData implements IParticleData {
    public static final IDeserializer<StarParticleData> DESERIALIZER = new IDeserializer<StarParticleData>() {
        @Override
        public StarParticleData deserialize(ParticleType<StarParticleData> type, StringReader reader) throws CommandSyntaxException {
            reader.expect(' ');
            float r = reader.readFloat();
            reader.expect(' ');
            float g = reader.readFloat();
            reader.expect(' ');
            float b = reader.readFloat();
            return new StarParticleData(r, g, b);
        }

        @Override
        public StarParticleData read(ParticleType<StarParticleData> type, PacketBuffer buffer) {
            return new StarParticleData(buffer.readFloat(), buffer.readFloat(), buffer.readFloat());
        }
    };

    public final float red, green, blue;

    public StarParticleData(float red, float green, float blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    @Override
    public void write(PacketBuffer buffer) {
        buffer.writeFloat(red);
        buffer.writeFloat(green);
        buffer.writeFloat(blue);
    }

    @Override
    public String getParameters() {
        return String.format(Locale.ROOT, "%f %f %f", red, green, blue);
    }

    @Override
    public ParticleType<?> getType() {
        return MoCParticles.STAR_FX.get();
    }
}
