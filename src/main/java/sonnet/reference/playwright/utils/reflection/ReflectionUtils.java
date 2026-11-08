package sonnet.reference.playwright.utils.reflection;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.IntStream;

public class ReflectionUtils {

    private ReflectionUtils() {}

    /**
     * Returns first method from clazz's methods which has a type signature that is *assignable* from params.
     * This differs from <code>class.getMethod(name, params)</code> in that params do not have to *exactly* match
     * @param clazz Class to search for method in
     * @param methodName Name of method to search for
     * @param params Parameters of method to search for
     * @return Method assignable from provided parameter types
     * @throws NoSuchMethodException If no such method exists
     */
    public static Method getAssignableMethod(Class<?> clazz, String methodName, Class<?>... params) throws NoSuchMethodException {

        return Arrays.stream(clazz.getMethods())
                .filter(method -> method.getName().equals(methodName))
                .filter(method -> method.getParameterCount() == params.length)
                .filter(method -> IntStream.range(0, params.length)
                        .allMatch(i -> method.getParameterTypes()[i].isAssignableFrom(params[i])))
                .findFirst().orElseThrow(() ->
                        new NoSuchMethodException("No such method: " + methodName + " assignable from parameter types"));
    }

    public static Method getAssignableMethod(Class<?> c, String methodName, Object[] params) throws NoSuchMethodException {

        return getAssignableMethod(c, methodName, getTypes(params));
    }

    public static Class<?>[] getTypes(Object... params) {

        return Arrays.stream(params).map(Object::getClass).toArray(size -> new Class<?>[size]);
    }

}
