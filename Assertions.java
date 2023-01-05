
import org.junit.jupiter.api.function.ThrowingSupplier;

import java.lang.reflect.Executable;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.function.Supplier;

public class Assertions {

    //Equals
    public static void assertEquals(int expected, int actual) {
        if (expected != actual) {
            System.out.println("Expected :" + expected);
            System.out.println("Actual   :" + actual);
        }
    }

    public static void assertEquals(int expected, int actual, String msg) {
        if (expected != actual) {
            System.out.println(msg + " ==>");
            System.out.println("Expected :" + expected);
            System.out.println("Actual   :" + actual);
        }
    }

    public static void assertEquals(int expected, int actual, Supplier<String> messageSupplier) {
        if (expected != actual) {
            System.out.println(messageSupplier.get() + " ==>");
            System.out.println("Expected :" + expected);
            System.out.println("Actual   :" + actual);
        }
    }

    public static void assertEquals(double expected, double actual) {
        if (expected != actual) {
            System.out.println("Expected :" + expected);
            System.out.println("Actual   :" + actual);
        }
    }

    public static void assertEquals(double expected, double actual, String msg) {
        if (expected != actual) {
            System.out.println(msg + " ==>");
            System.out.println("Expected :" + expected);
            System.out.println("Actual   :" + actual);
        }
    }

    public static void assertEquals(double expected, double actual, Supplier<String> messageSupplier) {
        if (expected != actual) {
            System.out.println(messageSupplier.get() + " ==>");
            System.out.println("Expected :" + expected);
            System.out.println("Actual   :" + actual);
        }
    }

    public static void assertEquals(Object expected, Object actual) {
        if (expected != actual) {
            System.out.println("Expected :" + expected);
            System.out.println("Actual   :" + actual);
        }
    }

    public static void assertEquals(Object expected, Object actual, String msg) {
        if (expected != actual) {
            System.out.println(msg + " ==>");
            System.out.println("Expected :" + expected);
            System.out.println("Actual   :" + actual);
        }
    }

    public static void assertEquals(Object expected, Object actual, Supplier<String> messageSupplier) {
        if (expected != actual) {
            System.out.println(messageSupplier.get() + " ==>");
            System.out.println("Expected :" + expected);
            System.out.println("Actual   :" + actual);
        }
    }


    //True
    public static void assertTrue(boolean condition) {
        if (!condition) {
            System.out.println("Expected :true");
            System.out.println("Actual   :false");
        }
    }

    public static void assertTrue(boolean condition, String msg) {
        if (!condition) {
            System.out.println(msg + " ==>");
            System.out.println("Expected :true");
            System.out.println("Actual   :false");
        }
    }

    public static void assertTrue(boolean condition, Supplier<String> messageSupplier) {
        if (!condition) {
            System.out.println(messageSupplier.get() + " ==>");
            System.out.println("Expected :true");
            System.out.println("Actual   :false");
        }
    }

    //False
    public static void assertFalse(boolean condition) {
        if (condition) {
            System.out.println("Expected :false");
            System.out.println("Actual   :true");
        }
    }

    public static void assertFalse(boolean condition, String msg) {
        if (condition) {
            System.out.println(msg + " ==>");
            System.out.println("Expected :false");
            System.out.println("Actual   :true");
        }
    }

    public static void assertFalse(boolean condition, Supplier<String> messageSupplier) {
        if (condition) {
            System.out.println(messageSupplier.get() + " ==>");
            System.out.println("Expected :false");
            System.out.println("Actual   :true");
        }
    }

    //NotNull
    public static void assertNotNull(Object actual) {
        if (actual == null) {
            System.out.println("Expected :" + actual);
            System.out.println("Actual   :null");
        }
    }

    public static void assertNotNull(Object actual, String msg) {
        if (actual == null) {
            System.out.println(msg + " ==>");
            System.out.println("Expected :" + actual);
            System.out.println("Actual   :null");
        }
    }

    public static void assertNotNull(Object actual, Supplier<String> messageSupplier) {
        if (actual == null) {
            System.out.println(messageSupplier.get() + " ==>");
            System.out.println("Expected :" + actual);
            System.out.println("Actual   :null");
        }
    }

    //Null
    public static void assertNull(Object actual) {
        if (actual != null) {

            System.out.println("Expected :null");
            System.out.println("Actual   :" + actual);
        }
    }

    public static void assertNull(Object actual, String msg) {
        if (actual != null) {
            System.out.println(msg + " ==>");
            System.out.println("Expected :null");
            System.out.println("Actual   :" + actual);
        }
    }

    public static void assertNull(Object actual, Supplier<String> messageSupplier) {
        if (actual != null) {
            System.out.println(messageSupplier.get() + " ==>");
            System.out.println("Expected :null");
            System.out.println("Actual   :" + actual);
        }
    }


    //Throws
    public static <T extends Throwable> T assertThrows(Class<T> expectedType, Executable executable) {
        if (executable instanceof Method) {
            Method ex = (Method) executable;
        }
        //ex.invoke()
        return null;
    }

    public static <T extends Throwable> T assertThrows(Class<T> expectedType, Executable executable, String message) {
        return null;
    }

    public static <T extends Throwable> T assertThrows(Class<T> expectedType, Executable executable, Supplier<String> messageSupplier) {
        return null;
    }


    //Timeout
    public static void assertTimeout(Duration timeout, Executable executable) {
        long timeoutInMillis = timeout.toMillis();
        long start = System.currentTimeMillis();
        //todo function

        long timeElapsed = System.currentTimeMillis() - start;
        if (timeElapsed > timeoutInMillis) {
            System.out.println("execution exceeded timeout of " + timeoutInMillis + " ms by " + (timeElapsed - timeoutInMillis) + " ms");
        }
    }

    public static void assertTimeout(Duration timeout, Executable executable, String message) {
        long timeoutInMillis = timeout.toMillis();
        long start = System.currentTimeMillis();

        //todo function

        long timeElapsed = System.currentTimeMillis() - start;
        if (timeElapsed > timeoutInMillis) {
            System.out.println(message + " ==>");
            System.out.println("execution exceeded timeout of " + timeoutInMillis + " ms by " + (timeElapsed - timeoutInMillis) + " ms");
        }
    }

    public static void assertTimeout(Duration timeout, Executable executable, Supplier<String> messageSupplier) {
        long timeoutInMillis = timeout.toMillis();
        long start = System.currentTimeMillis();

        //todo function

        long timeElapsed = System.currentTimeMillis() - start;
        if (timeElapsed > timeoutInMillis) {
            System.out.println(messageSupplier.get() + " ==>");
            System.out.println("execution exceeded timeout of " + timeoutInMillis + " ms by " + (timeElapsed - timeoutInMillis) + " ms");
        }
    }

    public static <T> T assertTimeout(Duration timeout, ThrowingSupplier<T> supplier) {
        long timeoutInMillis = timeout.toMillis();
        long start = System.currentTimeMillis();
        T result = null;
        //todo function

        long timeElapsed = System.currentTimeMillis() - start;
        if (timeElapsed > timeoutInMillis) {
            System.out.println("execution exceeded timeout of " + timeoutInMillis + " ms by " + (timeElapsed - timeoutInMillis) + " ms");
        }

        return result;
    }

    public static <T> T assertTimeout(Duration timeout, ThrowingSupplier<T> supplier, String message) {
        long timeoutInMillis = timeout.toMillis();
        long start = System.currentTimeMillis();
        T result = null;
        //todo function

        long timeElapsed = System.currentTimeMillis() - start;
        if (timeElapsed > timeoutInMillis) {
            System.out.println(message + " ==>");
            System.out.println("execution exceeded timeout of " + timeoutInMillis + " ms by " + (timeElapsed - timeoutInMillis) + " ms");
        }

        return result;
    }

    public static <T> T assertTimeout(Duration timeout, ThrowingSupplier<T> supplier, Supplier<String> messageSupplier) {
        long timeoutInMillis = timeout.toMillis();
        long start = System.currentTimeMillis();
        T result = null;
        //todo function

        long timeElapsed = System.currentTimeMillis() - start;
        if (timeElapsed > timeoutInMillis) {
            System.out.println(messageSupplier.get() + " ==>");
            System.out.println("execution exceeded timeout of " + timeoutInMillis + " ms by " + (timeElapsed - timeoutInMillis) + " ms");
        }

        return result;
    }
}
