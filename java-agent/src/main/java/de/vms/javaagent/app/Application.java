package de.vms.javaagent.app;

public class Application {

    private static Application application;

    private String mainClassName;

    private String mainPackageName;

    private Application() {
    }

    public static synchronized Application getInstance () {
        if (application == null) {
            application = new Application ();
        }
        return application;
    }

    public String getMainClassName() {
        return mainClassName;
    }

    public void setMainClassName(String mainClassName) {
        this.mainClassName = mainClassName;
    }

    public String getMainPackageName() {
        return mainPackageName;
    }

    public void setMainPackageName(String mainPackageName) {
        this.mainPackageName = mainPackageName;
    }
}