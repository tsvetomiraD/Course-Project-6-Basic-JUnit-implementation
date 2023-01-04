import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class JUnit {
    static Method beforeEach = null;
    static Method afterEach = null;
    static Method beforeClass = null;
    static Method afterClass = null;

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

        checkBeforeAndAfter(cl);
        List<Object> fieldsValues = new ArrayList<>();
        for (Field field : cl.getDeclaredFields()) {
            fieldsValues.add(field.get(cl.newInstance()));
        }

        for (Method method : cl.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Test.class)) {
                if (beforeEach != null) {
                    beforeEach.setAccessible(true);
                    beforeEach.invoke(cl.newInstance());
                }
                method.setAccessible(true);
                method.invoke(cl.newInstance());

                if (afterEach != null) {
                    afterEach.setAccessible(true);
                    afterEach.invoke(cl.newInstance());
                }
            }
        }
    }

    private static void checkBeforeAndAfter(Class<?> cl) {
        beforeEach = (Method) Arrays.stream(cl.getDeclaredMethods()).filter(m -> m.isAnnotationPresent(BeforeEach.class)).toArray()[0];

        //afterEach = (Method) Arrays.stream(cl.getDeclaredMethods()).filter(m -> m.isAnnotationPresent(AfterEach.class)).toArray()[0];

        //beforeClass = (Method) Arrays.stream(cl.getDeclaredMethods()).filter(m -> m.isAnnotationPresent(BeforeClass.class)).toArray()[0];

        //afterClass = (Method) Arrays.stream(cl.getDeclaredMethods()).filter(m -> m.isAnnotationPresent(AfterClass.class)).toArray()[0];
    }
}
