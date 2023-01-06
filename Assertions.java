import java.time.Duration;
import java.util.function.Supplier;

public class Assertions {
    //Equals
    public static void assertEquals(int expected, int actual) {
        if (expected != actual) {
            String res = print(expected, actual, null);
            throw new AssertionFailedError(res);
        }
    }

    public static void assertEquals(int expected, int actual, String msg) {
        if (expected != actual) {
            String res = print(expected, actual, msg);
            throw new AssertionFailedError(res);
        }
    }

    public static void assertEquals(int expected, int actual, Supplier<String> messageSupplier) {
        if (expected != actual) {
            String res = print(expected, actual, messageSupplier.get());
            throw new AssertionFailedError(res);
        }
    }

    public static void assertEquals(double expected, double actual) {
        if (expected != actual) {
            String res = print(expected, actual, null);
            throw new AssertionFailedError(res);
        }
    }

    public static void assertEquals(double expected, double actual, String msg) {
        if (expected != actual) {
            String res = print(expected, actual, msg);
            throw new AssertionFailedError(res);
        }
    }

    public static void assertEquals(double expected, double actual, Supplier<String> messageSupplier) {
        if (expected != actual) {
            String res = print(expected, actual, messageSupplier.get());
            throw new AssertionFailedError(res);
        }
    }

    public static void assertEquals(Object expected, Object actual) {
        if (expected != actual) {
            String res = print(expected, actual, null);
            throw new AssertionFailedError(res);
        }
    }

    public static void assertEquals(Object expected, Object actual, String msg) {
        if (expected != actual) {
            String res = print(expected, actual, msg);
            throw new AssertionFailedError(res);
        }
    }

    public static void assertEquals(Object expected, Object actual, Supplier<String> messageSupplier) {
        if (expected != actual) {
            String res = print(expected, actual, messageSupplier.get());
            throw new AssertionFailedError(res);
        }
    }


    //True
    public static void assertTrue(boolean condition) {
        if (!condition) {
            String res = print(condition, !condition, null);
            throw new AssertionFailedError(res);
        }
    }

    public static void assertTrue(boolean condition, String msg) {
        if (!condition) {
            String res = print(condition, !condition, msg);
            throw new AssertionFailedError(res);
        }
    }

    public static void assertTrue(boolean condition, Supplier<String> messageSupplier) {
        if (!condition) {
            String res = print(condition, !condition, messageSupplier.get());
            throw new AssertionFailedError(res);
        }
    }


    //False
    public static void assertFalse(boolean condition) {
        if (condition) {
            String res = print(!condition, condition, null);
            throw new AssertionFailedError(res);
        }
    }

    public static void assertFalse(boolean condition, String msg) {
        if (condition) {
            String res = print(!condition, condition, msg);
            throw new AssertionFailedError(res);
        }
    }

    public static void assertFalse(boolean condition, Supplier<String> messageSupplier) {
        if (condition) {
            String res = print(!condition, condition, messageSupplier.get());
            throw new AssertionFailedError(res);
        }
    }


    //NotNull
    public static void assertNotNull(Object actual) {
        if (actual == null) {
            String res = print(actual, null, null);
            throw new AssertionFailedError(res);
        }
    }

    public static void assertNotNull(Object actual, String msg) {
        if (actual == null) {
            String res = print(actual, null, msg);
            throw new AssertionFailedError(res);
        }
    }

    public static void assertNotNull(Object actual, Supplier<String> messageSupplier) {
        if (actual == null) {
            String res = print(actual, null, messageSupplier.get());
            throw new AssertionFailedError(res);
        }
    }


    //Null
    public static void assertNull(Object actual) {
        if (actual != null) {
            String res = print(null, actual, null);
            throw new AssertionFailedError(res);
        }
    }

    public static void assertNull(Object actual, String msg) {
        if (actual != null) {
            String res = print(null, actual, msg);
            throw new AssertionFailedError(res);
        }
    }

    public static void assertNull(Object actual, Supplier<String> messageSupplier) {
        if (actual != null) {
            String res = print(null, actual, messageSupplier.get());
            throw new AssertionFailedError(res);
        }
    }

    private static String print(Object expected, Object actual, String message) {
        StringBuilder sb = new StringBuilder();
        if (message != null)
            sb.append(message).append(" ==>");

        sb.append("Expected :").append(expected);
        sb.append("Actual   :").append(actual);
        return sb.toString();
    }


    //Throws
    public static <T extends Throwable> T assertThrows(Class<T> expectedType, Executable executable) throws AssertionFailedError {
        return assertThrows(expectedType, executable, (String)null);
    }

    public static <T extends Throwable> T assertThrows(Class<T> expectedType, Executable executable, String message) {
        try {
            executable.execute();
        } catch (Throwable e) {
            if (expectedType.isInstance(e)) {
                return (T) e;
            }
            String m = "Unexpected exception type thrown ==> expected: " + expectedType + " but was: " + e;
            throw new AssertionFailedError(m);
        }
        String m = "Expected: " + expectedType + " actual: no exception";
        throw new AssertionFailedError(m);
    }

    public static <T extends Throwable> T assertThrows(Class<T> expectedType, Executable executable, Supplier<String> messageSupplier) {
        return assertThrows(expectedType, executable, messageSupplier.get()); //todo check msgs
    }


    //Timeout
    public static void assertTimeout(Duration timeout, Executable executable) {
       assertTimeout(timeout, executable, (String) null);
    }

    public static void assertTimeout(Duration timeout, Executable executable, String message) {
        long timeoutInMillis = timeout.toMillis();
        long start = System.currentTimeMillis();
        try {
            executable.execute();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }

        long timeElapsed = System.currentTimeMillis() - start;
        if (timeElapsed > timeoutInMillis) {
            throw new AssertionFailedError("execution exceeded timeout of " + timeoutInMillis + " ms by " + (timeElapsed - timeoutInMillis) + " ms");
            //todo check msg
        }
    }

    public static void assertTimeout(Duration timeout, Executable executable, Supplier<String> messageSupplier) {
        assertTimeout(timeout, executable, messageSupplier.get());
    }

    public static <T> T assertTimeout(Duration timeout, ThrowingSupplier<T> supplier) {
        return assertTimeout(timeout, supplier, (String)null);
    }

    public static <T> T assertTimeout(Duration timeout, ThrowingSupplier<T> supplier, String message) {
        long timeoutInMillis = timeout.toMillis();
        long start = System.currentTimeMillis();
        T result = null;

        try {
            result = supplier.get();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }

        long timeElapsed = System.currentTimeMillis() - start;
        if (timeElapsed > timeoutInMillis) {
            throw new AssertionFailedError("execution exceeded timeout of " + timeoutInMillis + " ms by " + (timeElapsed - timeoutInMillis) + " ms");
            //todo check msg
        }

        return result;
    }

    public static <T> T assertTimeout(Duration timeout, ThrowingSupplier<T> supplier, Supplier<String> messageSupplier) {
        return assertTimeout(timeout, supplier, messageSupplier.get());
    }


    //timeoutPreemptively
    static void assertTimeoutPreemptively(Duration timeout, Executable executable) {
        assertTimeoutPreemptively(timeout, executable, (String) null);
    }

    static void assertTimeoutPreemptively(Duration timeout, Executable executable, String message) {
        Thread thread = new Thread(() -> {
            try {
                executable.execute();
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        });
        thread.start();
        try {
            thread.join(timeout.toMillis());
        } catch (InterruptedException e) {
            throw new AssertionFailedError(e);
        }

        if (thread.isAlive()) {
            thread.interrupt();
            throw new AssertionFailedError("Expected timeout: " + timeout);
        }
    }

    static void assertTimeoutPreemptively(Duration timeout, Executable executable, Supplier<String> messageSupplier) {
        assertTimeoutPreemptively(timeout, executable, messageSupplier.get());
    }

    static void assertAll(String msg, Executable... executables) {
        int count = 0;
        StringBuilder sb = new StringBuilder();
        for (Executable ex : executables) {
            try {
                ex.execute();
            } catch (Throwable e) {
                count++;
                sb.append(e.getMessage());
                System.out.println(e.getMessage());
            }
        }

        if (count > 0) {
            String m = msg != null ? msg + ": " : null;
            StringBuilder res = new StringBuilder();
            sb.append(m);
            res.append(count).append(" failed\n");
            res.append(sb);
            throw new AssertionFailedError(res.toString());
        }
    }
    static void assertAll(Executable... executables) {
        assertAll(null, executables);
    }
}


