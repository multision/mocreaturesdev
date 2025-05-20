package drzhark.mocreatures.command;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

public enum MoCHorseType {
    WHITE(1),
    CREAMY(2),
    BROWN(3),
    DARK_BROWN(4),
    BLACK(5),
    BRIGHT_CREAMY(6),
    SPECKLED(7),
    PALE_BROWN(8),
    GREY(9),
    PINTO(11),
    BRIGHT_PINTO(12),
    PALE_SPECKLES(13),
    SPOTTED(16),
    COW(17),
    GHOST(21),
    GHOST_B(22),
    UNDEAD(23),
    UNICORN_UNDEAD(24),
    PEGASUS_UNDEAD(25),
    SKELETON(26),
    UNICORN_SKELETON(27),
    PEGASUS_SKELETON(28),
    BAT(32),
    UNICORN(36),
    NIGHTMARE(38),
    PEGASUS(39),
    DARK_PEGASUS(40),
    FAIRY_YELLOW(48),
    FAIRY_PURPLE(49),
    FAIRY_WHITE(50),
    FAIRY_BLUE(51),
    FAIRY_PINK(52),
    FAIRY_LIGHT_GREEN(53),
    FAIRY_BLACK(54),
    FAIRY_RED(55),
    FAIRY_DARK_BLUE(56),
    FAIRY_CYAN(57),
    FAIRY_GREEN(58),
    FAIRY_ORANGE(59),
    ZEBRA(60),
    ZORSE(61),
    DONKEY(65),
    MULE(66),
    ZONKY(67);

    private final int id;

    MoCHorseType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static Optional<MoCHorseType> fromName(String name) {
        for (MoCHorseType type : values()) {
            if (type.name().equalsIgnoreCase(name)) {
                return Optional.of(type);
            }
        }
        return Optional.empty();
    }

    public static Collection<String> getNames() {
        return Arrays.stream(values()).map(Enum::name).collect(Collectors.toList());
    }
}
