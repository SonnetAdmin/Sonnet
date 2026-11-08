package sonnet.reference.selenium.utils.reflection;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.function.Supplier;

public class MethodCallWithSupplierArgs extends MethodCall {

    private final String methodName;
    private final Supplier<?>[] suppliers;

    public MethodCallWithSupplierArgs(String methodName, Supplier<?>[] suppliers) {

        super(methodName, suppliers);
        this.methodName = methodName;
        this.suppliers = suppliers;
    }

    @Override
    public void invoke(Object target)
            throws InvocationTargetException, IllegalAccessException, ArgumentResolutionException, NoSuchMethodException {

        Object[] resolvedArgs;

        try {
            resolvedArgs = Arrays.stream(suppliers)
                    .map(Supplier::get)
                    .toArray();

        } catch (Exception e) {
            throw new ArgumentResolutionException(e.getMessage());
        }

        ReflectionUtils
                .getAssignableMethod(target.getClass(), methodName, resolvedArgs)
                .invoke(target, resolvedArgs);
    }

}
