
class CalcTest {
    Calc c = new Calc();
    @Test
    void multiply() {
        Assertions.assertTrue(c.multiply(5, 4) == 20);
    }
}