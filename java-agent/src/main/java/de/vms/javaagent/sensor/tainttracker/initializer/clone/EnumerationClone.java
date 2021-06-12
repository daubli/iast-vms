package de.vms.javaagent.sensor.tainttracker.initializer.clone;

import java.util.Enumeration;

import de.vms.javaagent.sensor.tainttracker.util.TaintHelper;

public final class EnumerationClone implements Enumeration<Object> {
    private final Enumeration enumeration;

    public EnumerationClone(Enumeration var1) {
        if (var1 == null) {
            throw new NullPointerException("enumeration cannot be null");
        } else {
            this.enumeration = var1;
        }
    }

    public boolean hasMoreElements() {
        return this.enumeration.hasMoreElements();
    }

    public Object nextElement() {
        Object value = this.enumeration.nextElement();
        TaintHelper.setTaint(value, true);
        return value;
    }

    public int hashCode() {
        return this.enumeration.hashCode();
    }

    public boolean equals(Object var1) {
        return this.enumeration.equals(var1);
    }

    public String toString() {
        return this.enumeration.toString();
    }
}
