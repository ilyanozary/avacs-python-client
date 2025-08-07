package org.microemu.util;

/* loaded from: avacs.jar:org/microemu/util/JadMidletEntry.class */
public class JadMidletEntry {
    String name;
    String icon;
    String className;

    JadMidletEntry(String name, String icon, String className) {
        this.name = name;
        this.icon = icon;
        this.className = className;
    }

    public String getClassName() {
        return this.className;
    }

    public String getName() {
        return this.name;
    }

    public String toString() {
        return String.valueOf(this.name) + "+" + this.icon + "+" + this.className;
    }
}
