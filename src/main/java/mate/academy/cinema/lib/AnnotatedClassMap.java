package mate.academy.cinema.lib;

import java.util.HashMap;
import java.util.Map;

public class AnnotatedClassMap {
    private static final Map<Class<?>, Object> classMap = new HashMap<>();

    static {
    }

    public static Object getImplementation(Class<?> interfaceClass) {
        return classMap.get(interfaceClass);
    }
}
