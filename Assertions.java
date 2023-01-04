public class Assertions {
   public static void assertEquals(int expected, int given) {
       if (expected == given) {
           System.out.println("Equal");
           return;
       }
       System.out.println("Not equal");
   }

    public static void assertTrue(boolean condition) {
        if (condition) {
            System.out.println("True");
            return;
        }
        System.out.println("False");
    }

    public static void assertFalse(boolean condition) {
        if (!condition) {
            System.out.println("True");
            return;
        }
        System.out.println("False");
    }

    public static void assertNotNull(Object actual) {
        if (actual != null) {
            System.out.println("Not null");
            return;
        }
        System.out.println("Fail Test: Null");
    }

    public static void assertNull(Object actual) {
        if (actual == null) {
            System.out.println("Null");
            return;
        }
        System.out.println("Fail Test: Not null");
    }
}
