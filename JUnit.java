import Annotations.*;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.net.URLClassLoader;
import java.time.Duration;
import java.util.*;

public class JUnit {
    public static void main(String[] args) throws Exception {
        loadClasses();
        run(args);
    }

    static List<Method> beforeEach = new ArrayList<>();
    static List<Method> afterEach = new ArrayList<>();
    static List<Method> beforeClass = new ArrayList<>();
    static List<Method> afterClass = new ArrayList<>();
    static List<Method> test = new ArrayList<>();
    static Map<Method, Integer> repeatTest = new HashMap<>();
    static Map<Method, Duration> timeout = new HashMap<>();
    static Map<Method, List<Method>> dependsOn = new HashMap<>();
    static List<String> invoked = new ArrayList<>();
    static ClassLoader classLoader;

    private static void loadClasses() throws Exception {
        List<URL> urls = new ArrayList<>();
        File classes = new File("C:\\Users\\TD\\IdeaProjects\\JUnitImpl\\Tests\\target" + "\\classes");
        URL classesURL = classes.toURI().toURL();
        urls.add(classesURL);
        classLoader = new URLClassLoader(urls.toArray(new URL[urls.size()]));
    }

    public static void run(String[] classes) {
        try {
            for (String cl : classes)
                testClass(Class.forName(cl));
        } catch (Exception e) {
            throw new AssertionFailedError(e);
        }
    }

    public static void testClass(Class<?> cl) throws Exception {
        if (Objects.isNull(cl)) {
            throw new IllegalArgumentException("The object to check is null");
        }

        getMethods(cl);
        Object instancedClass = cl.getDeclaredConstructor().newInstance();

        invokeMethods(instancedClass);
    }

    private static void invokeMethods(Object instancedClass) throws Exception {
        for (Method method : beforeClass) {
            method.invoke(instancedClass);
        }
        invoke(instancedClass, test);


        for (Map.Entry<Method, Integer> method : repeatTest.entrySet()) {
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

    private static void invoke(Object instancedClass, List<Method> methods) throws IllegalAccessException, InvocationTargetException {
        for (Method method : methods) {
            if (invoked.contains(method)) {
                continue;
            }
            if (!dependsOn.containsKey(method) && !invoked.contains(method)) {
                inv(instancedClass, method);
            } else if (dependsOn.containsKey(method))
                invoke(instancedClass, dependsOn.get(method));

            inv(instancedClass, method);
        }
    }

    private static void inv(Object instancedClass, Method method) throws IllegalAccessException, InvocationTargetException {
        for (Method m : beforeEach) {
            m.invoke(instancedClass);
        }
        invokeTest(instancedClass, method);

        for (Method m : afterEach) {
            m.invoke(instancedClass);
        }
    }

    private static void invokeTest(Object instancedClass, Method method) throws IllegalAccessException, InvocationTargetException {
        boolean haveTimeout = timeout.containsKey(method);
        long timeoutInMillis = 0;
        long start = 0;

        if (haveTimeout) {
            timeoutInMillis = timeout.get(method).toMillis();
            start = System.currentTimeMillis();
        }
        if (!invoked.contains(method.getName()))
            invoked.add(method.getName());

        method.invoke(instancedClass);

        if (haveTimeout) {
            long timeElapsed = System.currentTimeMillis() - start;
            if (timeElapsed > timeoutInMillis) {
                throw new AssertionFailedError(""); //todo check msg
            }
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

            if (method.isAnnotationPresent(Timeout.class)) {
                method.setAccessible(true);
                Timeout rt = method.getAnnotation(Timeout.class);
                timeout.put(method, Duration.ofMillis(rt.time()));
            }

            if (method.isAnnotationPresent(DependsOnMethods.class)) {
                method.setAccessible(true);
                DependsOnMethods depends = method.getAnnotation(DependsOnMethods.class);
                List<Method> methods = new ArrayList<>();
                for (String name : depends.methods()) {
                    try {
                        methods.add(cl.getDeclaredMethod(name));
                    } catch (NoSuchMethodException e) {
                        throw new RuntimeException(e);
                    }
                }
                dependsOn.put(method, methods);
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
