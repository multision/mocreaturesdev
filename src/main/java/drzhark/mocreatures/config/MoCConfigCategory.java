/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.config;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableSet;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.*;

import static drzhark.mocreatures.config.MoCConfiguration.NEW_LINE;
import static drzhark.mocreatures.config.MoCConfiguration.allowedProperties;

public class MoCConfigCategory implements Map<String, MoCProperty> {

    public final MoCConfigCategory parent;
    private final ArrayList<MoCConfigCategory> children = new ArrayList<>();
    private final Map<String, MoCProperty> properties = new TreeMap<>();
    private String name;
    private String comment;
    private boolean changed = false;

    public MoCConfigCategory(String name) {
        this(name, null, true);
    }

    public MoCConfigCategory(String name, MoCConfigCategory parent) {
        this(name, parent, true);
    }

    public MoCConfigCategory(String name, boolean newline) {
        this(name, null, newline);
    }

    public MoCConfigCategory(String name, MoCConfigCategory parent, boolean newline) {
        this.name = name;
        this.parent = parent;
        if (parent != null) {
            parent.children.add(this);
        }
    }

    public static String getQualifiedName(String name, MoCConfigCategory parent) {
        return (parent == null ? name : parent.getQualifiedName() + MoCConfiguration.CATEGORY_SPLITTER + name);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof MoCConfigCategory) {
            MoCConfigCategory cat = (MoCConfigCategory) obj;
            return this.name.equals(cat.name) && this.children.equals(cat.children);
        }

        return false;
    }

    public String getQualifiedName() {
        return getQualifiedName(this.name, this.parent);
    }

    public MoCConfigCategory getFirstParent() {
        return (this.parent == null ? this : this.parent.getFirstParent());
    }

    public boolean isChild() {
        return this.parent != null;
    }

    public Map<String, MoCProperty> getValues() {
        return this.properties;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean containsKey(String key) {
        return this.properties.containsKey(key);
    }

    public MoCProperty get(String key) {
        return this.properties.get(key);
    }

    public void set(String key, MoCProperty value) {
        this.properties.put(key, value);
    }

    private void write(BufferedWriter out, String... data) throws IOException {
        write(out, true, data);
    }

    private void write(BufferedWriter out, boolean new_line, String... data) throws IOException {
        for (String datum : data) {
            if (datum != null) {
                out.write(datum);
            }
        }
        if (new_line) {
            out.write(NEW_LINE);
        }
    }

    public void write(BufferedWriter out, int indent) throws IOException {
        String pad0 = getIndent(indent);
        String pad1 = getIndent(indent + 1);

        write(out, pad0, "####################");
        write(out, pad0, "# ", this.name);

        if (this.comment != null) {
            write(out, pad0, "#===================");
            Splitter splitter = Splitter.onPattern("\r?\n");

            for (String line : splitter.split(this.comment)) {
                write(out, pad0, "# ", line);
            }
        }

        write(out, pad0, "####################", NEW_LINE);

        if (!allowedProperties.matchesAllOf(this.name)) {
            this.name = '"' + this.name + '"';
        }

        write(out, pad0, this.name, " {");

        MoCProperty[] props = this.properties.values().toArray(new MoCProperty[0]);

        for (int x = 0; x < props.length; x++) {
            MoCProperty prop = props[x];

            if (prop.comment != null) {
                if (x != 0) {
                    out.newLine();
                }

                Splitter splitter = Splitter.onPattern("\r?\n");
                for (String commentLine : splitter.split(prop.comment)) {
                    write(out, pad1, "# ", commentLine);
                }
            }

            String propName = prop.getName();

            if (!allowedProperties.matchesAllOf(propName)) {
                propName = '"' + propName + '"';
            }

            if (prop.isList()) {
                char type = prop.getTypeMoC().getID();
                write(out, false, pad1 + type, ":", propName, " <");
                for (int i = 0; i < prop.valueList.size(); i++) {
                    String line = prop.valueList.get(i);
                    if (prop.valueList.size() == i + 1) // if last line, don't write delimiter
                    {
                        write(out, false, line);
                    } else {
                        write(out, false, line + ":");
                    }
                }
                write(out, false, ">", NEW_LINE);
            } else if (prop.getTypeMoC() == null) {
                write(out, false, propName, "=", prop.getString());
            } else {
                char type = prop.getTypeMoC().getID();
                write(out, pad1, String.valueOf(type), ":", propName, "=", prop.getString());
            }
        }

        for (MoCConfigCategory child : this.children) {
            child.write(out, indent + 1);
        }

        write(out, pad0, "}", NEW_LINE);
    }

    private String getIndent(int indent) {
        StringBuilder buf = new StringBuilder();
        for (int x = 0; x < indent; x++) {
            buf.append("    ");
        }
        return buf.toString();
    }

    public boolean hasChanged() {
        if (this.changed) {
            return true;
        }
        for (MoCProperty prop : this.properties.values()) {
            if (prop.hasChanged()) {
                return true;
            }
        }
        return false;
    }

    void resetChangedState() {
        this.changed = false;
        for (MoCProperty prop : this.properties.values()) {
            prop.resetChangedState();
        }
    }

    //Map bouncer functions for compatibility with older mods, to be removed once all mods stop using it.
    @Override
    public int size() {
        return this.properties.size();
    }

    @Override
    public boolean isEmpty() {
        return this.properties.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return this.properties.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return this.properties.containsValue(value);
    }

    @Override
    public MoCProperty get(Object key) {
        return this.properties.get(key);
    }

    @Override
    public MoCProperty put(String key, MoCProperty value) {
        this.changed = true;
        return this.properties.put(key, value);
    }

    @Override
    public MoCProperty remove(Object key) {
        this.changed = true;
        return this.properties.remove(key);
    }

    @Override
    public void putAll(Map<? extends String, ? extends MoCProperty> m) {
        this.changed = true;
        this.properties.putAll(m);
    }

    @Override
    public void clear() {
        this.changed = true;
        this.properties.clear();
    }

    @Override
    public Set<String> keySet() {
        return this.properties.keySet();
    }

    @Override
    public Collection<MoCProperty> values() {
        return this.properties.values();
    }

    @Override
    //Immutable copy, changes will NOT be reflected in this category
    public Set<java.util.Map.Entry<String, MoCProperty>> entrySet() {
        return ImmutableSet.copyOf(this.properties.entrySet());
    }

    public Set<MoCConfigCategory> getChildren() {
        return ImmutableSet.copyOf(this.children);
    }

    public void removeChild(MoCConfigCategory child) {
        if (this.children.contains(child)) {
            this.children.remove(child);
            this.changed = true;
        }
    }

}
