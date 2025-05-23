/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
/*
 * This software is provided under the terms of the Minecraft Forge Public
 * License v1.0.
 */

package drzhark.mocreatures.config;

import java.util.ArrayList;
import java.util.List;

public class MoCProperty {

    private final boolean wasRead;
    private final boolean isList;
    private final Type type;
    public String value;
    public String comment;
    public List<String> valueList;
    private String name;
    private boolean changed = false;

    public MoCProperty() {
        this.wasRead = false;
        this.type = null;
        this.isList = false;
    }

    public MoCProperty(String name, String value, Type type) {
        this(name, value, type, false);
    }

    public MoCProperty(String name, String value, Type type, String comment) {
        this(name, value, type, false);
        this.comment = comment;
    }

    MoCProperty(String name, String value, Type type, boolean read) {
        setName(name);
        this.value = value;
        this.type = type;
        this.wasRead = read;
        this.isList = false;
    }

    public MoCProperty(String name, List<String> values, Type type) {
        this(name, values, type, false);
    }

    public MoCProperty(String name, List<String> values, Type type, String comment) {
        this(name, values, type, false);
        this.comment = comment;
    }

    MoCProperty(String name, List<String> values, Type type, boolean read) {
        setName(name);
        this.type = type;
        this.valueList = values;
        this.wasRead = read;
        this.isList = true;
    }

    /**
     * Returns the value in this property as it's raw string.
     *
     * @return current value
     */
    public String getString() {
        return this.value;
    }

    /**
     * Returns the value in this property as an integer, if the value is not a
     * valid integer, it will return -1.
     *
     * @return The value
     */
    public int getInt() {
        return getInt(-1);
    }

    /**
     * Returns the value in this property as an integer, if the value is not a
     * valid integer, it will return the provided default.
     *
     * @param _default The default to provide if the current value is not a
     *                 valid integer
     * @return The value
     */
    public int getInt(int _default) {
        try {
            return Integer.parseInt(this.value);
        } catch (NumberFormatException e) {
            return _default;
        }
    }

    /**
     * Checks if the current value stored in this MoCProperty can be converted
     * to an integer.
     *
     * @return True if the type of the MoCProperty is an Integer
     */
    public boolean isIntValue() {
        try {
            Integer.parseInt(this.value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Returns the value in this property as a boolean, if the value is not a
     * valid boolean, it will return the provided default.
     *
     * @param _default The default to provide
     * @return The value as a boolean, or the default
     */
    public boolean getBoolean(boolean _default) {
        if (isBooleanValue()) {
            return Boolean.parseBoolean(this.value);
        } else {
            return _default;
        }
    }

    /**
     * Checks if the current value held by this property is a valid boolean
     * value.
     *
     * @return True if it is a boolean value
     */
    public boolean isBooleanValue() {
        return ("true".equalsIgnoreCase(this.value) || "false".equalsIgnoreCase(this.value));
    }

    /**
     * Checks if the current value held by this property is a valid double
     * value.
     *
     * @return True if the value can be converted to a double
     */
    public boolean isDoubleValue() {
        try {
            Double.parseDouble(this.value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Returns the value in this property as a double, if the value is not a
     * valid double, it will return the provided default.
     *
     * @param _default The default to provide if the current value is not a
     *                 valid double
     * @return The value
     */
    public double getDouble(double _default) {
        try {
            return Double.parseDouble(this.value);
        } catch (NumberFormatException e) {
            return _default;
        }
    }

    public List<String> getStringList() {
        return this.valueList;
    }

    /**
     * Returns the integer value of all values that can be parsed in the list.
     *
     * @return Array of length 0 if none of the values could be parsed.
     */
    public int[] getIntList() {
        ArrayList<Integer> nums = new ArrayList<>();

        for (String value : this.valueList) {
            try {
                nums.add(Integer.parseInt(value));
            } catch (NumberFormatException ignored) {
            }
        }

        int[] primitives = new int[nums.size()];

        for (int i = 0; i < nums.size(); i++) {
            primitives[i] = nums.get(i);
        }

        return primitives;
    }

    /**
     * Checks if all the current values stored in this property can be
     * converted to an integer.
     *
     * @return True if the type of the Property is an Integer List
     */
    public boolean isIntList() {
        for (String value : this.valueList) {
            try {
                Integer.parseInt(value);
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns the boolean value of all values that can be parsed in the list.
     *
     * @return Array of length 0 if none of the values could be parsed.
     */
    public boolean[] getBooleanList() {
        ArrayList<Boolean> tmp = new ArrayList<>();
        for (String value : this.valueList) {
            try {
                tmp.add(Boolean.parseBoolean(value));
            } catch (NumberFormatException ignored) {
            }
        }

        boolean[] primitives = new boolean[tmp.size()];

        for (int i = 0; i < tmp.size(); i++) {
            primitives[i] = tmp.get(i);
        }

        return primitives;
    }

    /**
     * Checks if all of current values stored in this property can be converted
     * to a boolean.
     *
     * @return True if it is a boolean value
     */
    public boolean isBooleanList() {
        for (String value : this.valueList) {
            if (!"true".equalsIgnoreCase(value) && !"false".equalsIgnoreCase(value)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Returns the double value of all values that can be parsed in the list.
     *
     * @return Array of length 0 if none of the values could be parsed.
     */
    public double[] getDoubleList() {
        ArrayList<Double> tmp = new ArrayList<>();
        for (String value : this.valueList) {
            try {
                tmp.add(Double.parseDouble(value));
            } catch (NumberFormatException ignored) {
            }
        }

        double[] primitives = new double[tmp.size()];

        for (int i = 0; i < tmp.size(); i++) {
            primitives[i] = tmp.get(i);
        }

        return primitives;
    }

    /**
     * Checks if all the current values stored in this property can be
     * converted to a double.
     *
     * @return True if the type of the Property is a double List
     */
    public boolean isDoubleList() {
        for (String value : this.valueList) {
            try {
                Double.parseDouble(value);
            } catch (NumberFormatException e) {
                return false;
            }
        }

        return true;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValueList(List<String> list) {
        this.valueList = list;
    }

    /**
     * Determines if this config value was just created, or if it was read from
     * the config file. This is useful for mods who auto-assign there blocks to
     * determine if the ID returned is a configured one, or an automatically
     * generated one.
     *
     * @return True if this property was loaded from the config file with a
     * value
     */
    public boolean wasRead() {
        return this.wasRead;
    }

    public Type getTypeMoC() {
        return this.type;
    }

    public boolean isList() {
        return this.isList;
    }

    public boolean hasChanged() {
        return this.changed;
    }

    void resetChangedState() {
        this.changed = false;
    }

    public void set(String value) {
        this.value = value;
        this.changed = true;
    }

    public void set(List<String> values) {
        this.valueList = values;
        this.changed = true;
    }

    public void set(int value) {
        set(Integer.toString(value));
    }

    public void set(boolean value) {
        set(Boolean.toString(value));
    }

    public void set(double value) {
        set(Double.toString(value));
    }

    public enum Type {
        STRING, INTEGER, BOOLEAN, DOUBLE;

        private static final Type[] values = {STRING, INTEGER, BOOLEAN, DOUBLE};

        public static Type tryParse(char id) {
            for (Type item : values) {
                if (item.getID() == id) {
                    return item;
                }
            }
            return STRING;
        }

        public char getID() {
            return name().charAt(0);
        }
    }
}
