package de.vms.policy.model;

public enum Taint {
    PARAMETER("PARAMETER"),
    RETURN_VALUE("RETURN_VALUE");

    private String code;

    Taint(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
