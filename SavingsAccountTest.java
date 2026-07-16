public class SavingsAccountTest {

    private static int passed = 0;
    private static int failed = 0;

    public static void main(String[] args) {
        System.out.println("========== SavingsAccount 单元测试 ==========\n");

        testNormalCase();
        testZeroBalance();
        testZeroInterestRate();
        testInterestCalculation();
        testNewBalanceCalculation();
        testEmptyAccountName();
        testNullAccountName();
        testWhitespaceAccountName();
        testNegativeBalance();
        testExcessiveBalance();
        testNegativeInterestRate();
        testExcessiveInterestRate();
        testBoundaryMaxBalance();
        testBoundaryMaxRate();

        System.out.println("===========================================");
        System.out.printf("测试结果: 通过 %d, 失败 %d, 总计 %d%n",
                passed, failed, passed + failed);

        if (failed > 0) {
            System.exit(1);
        }
    }

    // ---------- 正常场景 ----------

    private static void testNormalCase() {
        SavingsAccount account = new SavingsAccount("张三", 10000.0, 3.5);

        assertEquals("张三", account.getAccountName(), "账户名");
        assertEquals(10000.0, account.getOriginalBalance(), "原始余额");
        assertEquals(3.5, account.getAnnualInterestRate(), "年利率");
        assertEquals(350.0, account.calculateInterest(), "利息计算");
        assertEquals(10350.0, account.calculateNewBalance(), "新余额计算");

        System.out.println("✓ testNormalCase 通过");
        passed++;
    }

    private static void testZeroBalance() {
        SavingsAccount account = new SavingsAccount("零余额账户", 0.0, 5.0);

        assertEquals(0.0, account.getOriginalBalance(), "余额为0");
        assertEquals(0.0, account.calculateInterest(), "余额为0时利息应为0");
        assertEquals(0.0, account.calculateNewBalance(), "余额为0时新余额应为0");

        System.out.println("✓ testZeroBalance 通过");
        passed++;
    }

    private static void testZeroInterestRate() {
        SavingsAccount account = new SavingsAccount("零利率账户", 5000.0, 0.0);

        assertEquals(0.0, account.getAnnualInterestRate(), "利率为0");
        assertEquals(0.0, account.calculateInterest(), "利率为0时利息应为0");
        assertEquals(5000.0, account.calculateNewBalance(), "利率为0时新余额应等于原始余额");

        System.out.println("✓ testZeroInterestRate 通过");
        passed++;
    }

    private static void testInterestCalculation() {
        SavingsAccount account = new SavingsAccount("测试", 20000.0, 2.5);

        assertEquals(500.0, account.calculateInterest(), "20000 * 2.5% = 500");
        assertEquals(20500.0, account.calculateNewBalance(), "20000 + 500 = 20500");

        System.out.println("✓ testInterestCalculation 通过");
        passed++;
    }

    private static void testNewBalanceCalculation() {
        SavingsAccount account = new SavingsAccount("测试", 1500.0, 10.0);

        assertEquals(150.0, account.calculateInterest(), "1500 * 10% = 150");
        assertEquals(1650.0, account.calculateNewBalance(), "1500 + 150 = 1650");

        System.out.println("✓ testNewBalanceCalculation 通过");
        passed++;
    }

    // ---------- 边界 / 异常场景 ----------

    private static void testEmptyAccountName() {
        try {
            new SavingsAccount("", 1000.0, 5.0);
            fail("空账户名应抛出异常");
        } catch (IllegalArgumentException e) {
            assertExceptionContains(e, "账户名称不能为空");
            System.out.println("✓ testEmptyAccountName 通过 — 捕获: " + e.getMessage());
            passed++;
        }
    }

    private static void testNullAccountName() {
        try {
            new SavingsAccount(null, 1000.0, 5.0);
            fail("null 账户名应抛出异常");
        } catch (IllegalArgumentException e) {
            assertExceptionContains(e, "账户名称不能为空");
            System.out.println("✓ testNullAccountName 通过 — 捕获: " + e.getMessage());
            passed++;
        }
    }

    private static void testWhitespaceAccountName() {
        try {
            new SavingsAccount("   ", 1000.0, 5.0);
            fail("纯空白账户名应抛出异常");
        } catch (IllegalArgumentException e) {
            assertExceptionContains(e, "账户名称不能为空");
            System.out.println("✓ testWhitespaceAccountName 通过 — 捕获: " + e.getMessage());
            passed++;
        }
    }

    private static void testNegativeBalance() {
        try {
            new SavingsAccount("测试", -100.0, 5.0);
            fail("负余额应抛出异常");
        } catch (IllegalArgumentException e) {
            assertExceptionContains(e, "原始余额不能为负数");
            System.out.println("✓ testNegativeBalance 通过 — 捕获: " + e.getMessage());
            passed++;
        }
    }

    private static void testExcessiveBalance() {
        try {
            new SavingsAccount("测试", SavingsAccount.MAX_BALANCE + 1, 5.0);
            fail("超大余额应抛出异常");
        } catch (IllegalArgumentException e) {
            assertExceptionContains(e, "原始余额超过最大限额");
            System.out.println("✓ testExcessiveBalance 通过 — 捕获: " + e.getMessage());
            passed++;
        }
    }

    private static void testNegativeInterestRate() {
        try {
            new SavingsAccount("测试", 1000.0, -1.0);
            fail("负利率应抛出异常");
        } catch (IllegalArgumentException e) {
            assertExceptionContains(e, "年利率不能为负数");
            System.out.println("✓ testNegativeInterestRate 通过 — 捕获: " + e.getMessage());
            passed++;
        }
    }

    private static void testExcessiveInterestRate() {
        try {
            new SavingsAccount("测试", 1000.0, SavingsAccount.MAX_INTEREST_RATE + 1);
            fail("超大利率应抛出异常");
        } catch (IllegalArgumentException e) {
            assertExceptionContains(e, "年利率超过最大允许值");
            System.out.println("✓ testExcessiveInterestRate 通过 — 捕获: " + e.getMessage());
            passed++;
        }
    }

    private static void testBoundaryMaxBalance() {
        SavingsAccount account = new SavingsAccount(
                "最大余额边界", SavingsAccount.MAX_BALANCE, 5.0);

        assertEquals(SavingsAccount.MAX_BALANCE, account.getOriginalBalance(),
                "允许的最大余额");
        assertEquals(
                SavingsAccount.MAX_BALANCE * 5.0 / 100,
                account.calculateInterest(),
                "最大余额的利息");

        System.out.println("✓ testBoundaryMaxBalance 通过");
        passed++;
    }

    private static void testBoundaryMaxRate() {
        SavingsAccount account = new SavingsAccount(
                "最大利率边界", 10000.0, SavingsAccount.MAX_INTEREST_RATE);

        assertEquals(SavingsAccount.MAX_INTEREST_RATE, account.getAnnualInterestRate(),
                "允许的最大利率");
        assertEquals(10000.0, account.calculateInterest(),
                "10000 * 100% = 10000");

        System.out.println("✓ testBoundaryMaxRate 通过");
        passed++;
    }

    // ---------- 断言工具方法 ----------

    private static void assertEquals(
            double expected, double actual, String description) {

        if (Math.abs(expected - actual) < 0.001) {
            return; // 通过
        }
        System.out.printf("  ✗ %s: 期望 %.4f, 实际 %.4f%n", description, expected, actual);
        failed++;
    }

    private static void assertEquals(
            String expected, String actual, String description) {

        if (expected.equals(actual)) {
            return;
        }
        System.out.printf("  ✗ %s: 期望 \"%s\", 实际 \"%s\"%n",
                description, expected, actual);
        failed++;
    }

    private static void assertExceptionContains(
            IllegalArgumentException e, String expectedFragment) {

        if (e.getMessage() != null && e.getMessage().contains(expectedFragment)) {
            return;
        }
        System.out.printf("  ✗ 异常信息不包含 \"%s\", 实际信息: \"%s\"%n",
                expectedFragment, e.getMessage());
        failed++;
    }

    private static void fail(String message) {
        System.out.println("  ✗ " + message);
        failed++;
    }
}
