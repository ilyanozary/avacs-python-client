package org.microemu;

/* loaded from: avacs.jar:org/microemu/MIDletEntry.class */
public class MIDletEntry {
    private String name;
    private Class midletClass;

    public MIDletEntry(String name, Class midletClass) {
        this.name = name;
        this.midletClass = midletClass;
    }

    public String getName() {
        return this.name;
    }

    public Class getMIDletClass() {
        return this.midletClass;
    }
}
