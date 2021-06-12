package de.vms.javaagent.sensor.application.advice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.bytebuddy.asm.Advice;

import de.vms.javaagent.app.Application;

public class SpringAdvice {

    public static Logger LOG = LoggerFactory.getLogger(SpringAdvice.class);

    /*
        Method signature is: launch(String[] args, String launchClass, ClassLoader classLoader)
     */
    @Advice.OnMethodEnter
    public static void intercept(@Advice.Argument(1) String launchClass) {
        LOG.debug("Detected Spring Framework");

        Application springApplication = Application.getInstance();


        if (launchClass != null) {
            springApplication.setMainClassName(launchClass);
            springApplication.setMainPackageName(launchClass.substring(0, launchClass.lastIndexOf('.')));
        }
    }
}
