import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

public class JUnit {
    public static void main(String[] args) throws Exception {
        run(args);
    }
    static List<Method> beforeEach =new ArrayList<>();
    static List<Method> afterEach = new ArrayList<>();
    static List<Method> beforeClass = new ArrayList<>();
    static List<Method> afterClass =new ArrayList<>();
    static List<Method> test = new ArrayList<>();
    static Map<Method, Integer> repeatTest = new HashMap<>();

    public static void run(String[] classes) {
        try {
            for (String cl : classes)
                testClass(Class.forName(cl));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void testClass(Class<?> cl) throws Exception {
        if (Objects.isNull(cl)) {
            throw new IllegalArgumentException("The object to serialize is null");
        }

        getMethods(cl);
        Object instancedClass = cl.getDeclaredConstructor().newInstance();

        invokeMethods(instancedClass);
    }

    private static void invokeMethods(Object instancedClass) throws IllegalAccessException, InvocationTargetException {
        for (Method method : beforeClass) {
            method.invoke(instancedClass);
        }

        for (Method method : test) {
            for (Method m : beforeEach) {
                m.invoke(instancedClass);
            }

            method.invoke(instancedClass);

            for (Method m : afterEach) {
                m.invoke(instancedClass);
            }
        }

        for (Map.Entry<Method, Integer> method: repeatTest.entrySet()) {
            for (int i = 0; i < method.getValue(); i++) {
                for (Method m : beforeEach) {
                    m.invoke(instancedClass);
                }

                method.getKey().invoke(instancedClass);

                for (Method m : afterEach) {
                    m.invoke(instancedClass);
                }
            }
        }

        for (Method method : afterClass) {
            method.invoke(instancedClass);
        }
    }

    private static void getMethods(Class<?> cl) {
        for (Method method : cl.getDeclaredMethods()) {
            if (method.isAnnotationPresent(BeforeClass.class) && Modifier.isStatic(method.getModifiers())) {
                method.setAccessible(true);
                beforeClass.add(method);
            } else if (method.isAnnotationPresent(BeforeClass.class) && !Modifier.isStatic(method.getModifiers())) {
                throw new IllegalArgumentException("@BeforeClass method must be static");
            }

            if (method.isAnnotationPresent(BeforeEach.class) && !Modifier.isStatic(method.getModifiers())) {
                method.setAccessible(true);
                beforeEach.add(method);
            } else if (method.isAnnotationPresent(BeforeEach.class) && Modifier.isStatic(method.getModifiers())) {
                throw new IllegalArgumentException("@BeforeEach method must not be static");
            }

            if (method.isAnnotationPresent(Test.class)) {
                method.setAccessible(true);
                test.add(method);
            }
            if (method.isAnnotationPresent(RepeatedTest.class)) {
                method.setAccessible(true);
                RepeatedTest rt = method.getAnnotation(RepeatedTest.class);
                repeatTest.put(method, rt.times());
            }

            if (method.isAnnotationPresent(AfterClass.class) && Modifier.isStatic(method.getModifiers())) {
                method.setAccessible(true);
                afterClass.add(method);
            } else if (method.isAnnotationPresent(AfterClass.class) && !Modifier.isStatic(method.getModifiers())) {
                throw new IllegalArgumentException("@AfterClass method must be static");
            }

            if (method.isAnnotationPresent(AfterEach.class) && !Modifier.isStatic(method.getModifiers())) {
                method.setAccessible(true);
                afterEach.add(method);
            } else if (method.isAnnotationPresent(AfterEach.class) && Modifier.isStatic(method.getModifiers())) {
                throw new IllegalArgumentException("@AfterEach method must not be static");
            }
        }
    }
}
