package de.vms.javaagent.sensor.tainttracker.config;

import java.util.Collections;
import java.util.List;

public class MethodConfig {
    private List<MethodArgumentsConfig> arguments;
    private String bySuperType;
    private String byReturnType;
    private String byClass;
    private String methodName;
    private String byClassAnnotation;
    private String byMethodAnnotation;
    private Taint taint;


    public List<MethodArgumentsConfig> getArguments() {
        if (arguments == null) {
            return Collections.singletonList(new MethodArgumentsConfig(null));
        }
        return arguments;
    }

    public String getBySuperType() {
        if (bySuperType == null) {
            return "";
        }
        return bySuperType;
    }

    public String getByReturnType() {
        if (byReturnType == null) {
            return "";
        }
        return byReturnType;
    }

    public String getByClass() {
        if (byClass == null) {
            return "";
        }
        return byClass;
    }

    public String getMethodName() {
        if (methodName == null) {
            return "";
        }
        return methodName;
    }

    public Taint getTaint() {
        return taint;
    }

    public String getByClassAnnotation() {
        if (byClassAnnotation == null) {
            return "";
        }
        return byClassAnnotation;
    }

    public String getByMethodAnnotation() {
        if (byMethodAnnotation == null) {
            return "";
        }
        return byMethodAnnotation;
    }

    public MethodConfig(List<MethodArgumentsConfig> arguments, String bySuperType, String byReturnType, String byClass,
            String methodName, Taint taint, String byClassAnnotation, String byMethodAnnotation) {
        this.arguments = arguments;
        this.bySuperType = bySuperType;
        this.byReturnType = byReturnType;
        this.byClass = byClass;
        this.methodName = methodName;
        this.taint = taint;
        this.byClassAnnotation = byClassAnnotation;
        this.byMethodAnnotation = byMethodAnnotation;
    }

    public MethodConfig() {
        this.arguments = null;
        this.bySuperType = null;
        this.byClass = null;
        this.methodName = null;
        this.taint = null;
        this.byClassAnnotation = null;
        this.byMethodAnnotation = null;
    }
}
