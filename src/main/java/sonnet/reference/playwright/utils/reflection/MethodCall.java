package sonnet.reference.playwright.utils.reflection;

import java.lang.reflect.InvocationTargetException;

public class MethodCall {

    private final String methodName;
    private final Object[] args;

    public MethodCall(String methodName) {

        this.methodName = methodName;
        this.args = new Object[0];
    }

    public MethodCall(String methodName, Object[] args) {

        this.methodName = methodName;
        this.args = args;
    }

    public void invoke(Object target)
            throws InvocationTargetException, IllegalAccessException, ArgumentResolutionException, NoSuchMethodException {

        ReflectionUtils
                .getAssignableMethod(target.getClass(), methodName, args)
                .invoke(target, args);
    }

}
