package Tests.losPollosAnnotations;

import Auxiliary.GlobalConfig;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.lang.reflect.Method;

public class UseTestingDatabaseExtension implements BeforeTestExecutionCallback, AfterTestExecutionCallback {

    private boolean wasActivated = false;

    @Override
    public void beforeTestExecution(ExtensionContext context) {
        Method testMethod = context.getRequiredTestMethod();
        if (testMethod.isAnnotationPresent(UseTestingDatabase.class)) {
            GlobalConfig.setTestMode(true);
            wasActivated = true;
        }
    }

    @Override
    public void afterTestExecution(ExtensionContext context) {
        if (wasActivated) {
            GlobalConfig.setTestMode(false);
        }
    }
}
