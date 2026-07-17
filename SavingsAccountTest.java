import java.math.BigDecimal;

public class SavingsAccountTest {

    private static int passed = 0;
    private static int failed = 0;

    public static void main(String[] args) {
        System.out.println("========== SavingsAccount 单元测试 ==========\n");

        testStandardAccount();
        testPremiumAccount();
        testCorporateAccount();
        testZeroBalance();
        testRoundedInterestCalculation();
        testCustomerTypes();
        testEmptyAccountName();
        testNullAccountName();
        testWhitespaceAccountName();
        testNegativeBalance();
        testExcessiveBalance();
        testBoundaryMaxBalance();

        System.out.println("===========================================");
        System.out.printf("测试结果: 通过 %d, 失败 %d, 总计 %d%n",
                passed, failed, passed + failed);

        if (failed > 0) {
            System.exit(1);
        }
    }

    private static void testStandardAccount() {
        SavingsAccount account = new StandardSavingsAccount("张三", bd("10000.00"));

        assertEquals("张三", account.getAccountName(), "账户名");
        assertEquals(CustomerType.STANDARD, account.getCustomerType(), "客户类型");
        assertEquals(bd("10000.00"), account.getOriginalBalance(), "原始余额");
        assertEquals(StandardSavingsAccount.INTEREST_RATE, account.getAnnualInterestRate(), "标准客户年利率");
        assertEquals(bd("150.00"), account.calculateInterest(), "标准客户利息计算");
        assertEquals(bd("10150.00"), account.calculateNewBalance(), "标准客户新余额计算");

        System.out.println("✓ testStandardAccount 通过");
        passed++;
    }

    private static void testPremiumAccount() {
        SavingsAccount account = new PremiumSavingsAccount("李四", bd("20000.00"));

        assertEquals(CustomerType.PREMIUM, account.getCustomerType(), "客户类型");
        assertEquals(PremiumSavingsAccount.INTEREST_RATE, account.getAnnualInterestRate(), "高级客户年利率");
        assertEquals(bd("500.00"), account.calculateInterest(), "高级客户利息计算");
        assertEquals(bd("20500.00"), account.calculateNewBalance(), "高级客户新余额计算");

        System.out.println("✓ testPremiumAccount 通过");
        passed++;
    }

    private static void testCorporateAccount() {
        SavingsAccount account = new CorporateSavingsAccount("企业账户", bd("50000.00"));

        assertEquals(CustomerType.CORPORATE, account.getCustomerType(), "客户类型");
        assertEquals(CorporateSavingsAccount.INTEREST_RATE, account.getAnnualInterestRate(), "企业客户年利率");
        assertEquals(bd("1750.00"), account.calculateInterest(), "企业客户利息计算");
        assertEquals(bd("51750.00"), account.calculateNewBalance(), "企业客户新余额计算");

        System.out.println("✓ testCorporateAccount 通过");
        passed++;
    }

    private static void testZeroBalance() {
        SavingsAccount account = new StandardSavingsAccount("零余额账户", bd("0"));

        assertEquals(bd("0.00"), account.getOriginalBalance(), "余额为0");
        assertEquals(bd("0.00"), account.calculateInterest(), "余额为0时利息应为0");
        assertEquals(bd("0.00"), account.calculateNewBalance(), "余额为0时新余额应为0");

        System.out.println("✓ testZeroBalance 通过");
        passed++;
    }

    private static void testRoundedInterestCalculation() {
        SavingsAccount account = new PremiumSavingsAccount("小数余额账户", bd("1234.56"));

        assertEquals(bd("30.86"), account.calculateInterest(), "BigDecimal 利息应按两位小数四舍五入");
        assertEquals(bd("1265.42"), account.calculateNewBalance(), "BigDecimal 新余额应按两位小数计算");

        System.out.println("✓ testRoundedInterestCalculation 通过");
        passed++;
    }

    private static void testCustomerTypes() {
        SavingsAccount[] accounts = {
                new StandardSavingsAccount("标准", bd("1000")),
                new PremiumSavingsAccount("高级", bd("1000")),
                new CorporateSavingsAccount("企业", bd("1000"))
        };

        assertEquals(CustomerType.STANDARD, accounts[0].getCustomerType(), "标准客户类型");
        assertEquals(CustomerType.PREMIUM, accounts[1].getCustomerType(), "高级客户类型");
        assertEquals(CustomerType.CORPORATE, accounts[2].getCustomerType(), "企业客户类型");

        if (accounts[0].getAnnualInterestRate().compareTo(accounts[1].getAnnualInterestRate()) >= 0
                || accounts[1].getAnnualInterestRate().compareTo(accounts[2].getAnnualInterestRate()) >= 0) {
            fail("三种子类的利率应依次递增");
        }

        System.out.println("✓ testCustomerTypes 通过");
        passed++;
    }

    private static void testEmptyAccountName() {
        try {
            new StandardSavingsAccount("", bd("1000.00"));
            fail("空账户名应抛出异常");
        } catch (IllegalArgumentException e) {
            assertExceptionContains(e, "账户名称不能为空");
            System.out.println("✓ testEmptyAccountName 通过 — 捕获: " + e.getMessage());
            passed++;
        }
    }

    private static void testNullAccountName() {
        try {
            new StandardSavingsAccount(null, bd("1000.00"));
            fail("null 账户名应抛出异常");
        } catch (IllegalArgumentException e) {
            assertExceptionContains(e, "账户名称不能为空");
            System.out.println("✓ testNullAccountName 通过 — 捕获: " + e.getMessage());
            passed++;
        }
    }

    private static void testWhitespaceAccountName() {
        try {
            new StandardSavingsAccount("   ", bd("1000.00"));
            fail("纯空白账户名应抛出异常");
        } catch (IllegalArgumentException e) {
            assertExceptionContains(e, "账户名称不能为空");
            System.out.println("✓ testWhitespaceAccountName 通过 — 捕获: " + e.getMessage());
            passed++;
        }
    }

    private static void testNegativeBalance() {
        try {
            new StandardSavingsAccount("测试", bd("-100.00"));
            fail("负余额应抛出异常");
        } catch (IllegalArgumentException e) {
            assertExceptionContains(e, "原始余额不能为负数");
            System.out.println("✓ testNegativeBalance 通过 — 捕获: " + e.getMessage());
            passed++;
        }
    }

    private static void testExcessiveBalance() {
        try {
            new StandardSavingsAccount("测试", SavingsAccount.MAX_BALANCE.add(BigDecimal.ONE));
            fail("超大余额应抛出异常");
        } catch (IllegalArgumentException e) {
            assertExceptionContains(e, "原始余额超过最大限额");
            System.out.println("✓ testExcessiveBalance 通过 — 捕获: " + e.getMessage());
            passed++;
        }
    }

    private static void testBoundaryMaxBalance() {
        SavingsAccount account = new CorporateSavingsAccount("最大余额边界", SavingsAccount.MAX_BALANCE);

        assertEquals(SavingsAccount.MAX_BALANCE, account.getOriginalBalance(), "允许的最大余额");
        assertEquals(bd("35000000.00"), account.calculateInterest(), "最大余额的利息");

        System.out.println("✓ testBoundaryMaxBalance 通过");
        passed++;
    }

    private static BigDecimal bd(String value) {
        return new BigDecimal(value).setScale(2);
    }

    private static void assertEquals(BigDecimal expected, BigDecimal actual, String description) {
        if (expected.compareTo(actual) == 0) {
            return;
        }
        System.out.printf("  ✗ %s: 期望 %s, 实际 %s%n",
                description, expected.toPlainString(), actual.toPlainString());
        failed++;
    }

    private static void assertEquals(String expected, String actual, String description) {
        if (expected.equals(actual)) {
            return;
        }
        System.out.printf("  ✗ %s: 期望 \"%s\", 实际 \"%s\"%n",
                description, expected, actual);
        failed++;
    }

    private static void assertEquals(CustomerType expected, CustomerType actual, String description) {
        if (expected == actual) {
            return;
        }
        System.out.printf("  ✗ %s: 期望 %s, 实际 %s%n", description, expected, actual);
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
