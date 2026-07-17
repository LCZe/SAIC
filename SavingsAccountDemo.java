import java.math.BigDecimal;
import java.util.Locale;
import java.util.Scanner;

public class SavingsAccountDemo {

    private static final int ACCOUNT_COUNT = 1;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        SavingsAccount[] accounts = new SavingsAccount[ACCOUNT_COUNT];

        for (int i = 0; i < accounts.length; i++) {
            System.out.println("\n请输入第 " + (i + 1) + " 个储蓄账户资料");

            String accountName = readAccountName(scanner);

            CustomerType customerType = readCustomerType(scanner);

            BigDecimal originalBalance = readBigDecimal(
                    scanner,
                    "原始余额：",
                    BigDecimal.ZERO,
                    SavingsAccount.MAX_BALANCE,
                    "原始余额"
            );

            accounts[i] = createAccount(customerType, accountName, originalBalance);
        }

        System.out.println("\n========== 储蓄账户计算结果 ==========");

        for (SavingsAccount account : accounts) {
            account.displayAccountInfo();
        }

        scanner.close();
    }

    private static SavingsAccount createAccount(
            CustomerType customerType,
            String accountName,
            BigDecimal originalBalance) {

        switch (customerType) {
            case STANDARD:
                return new StandardSavingsAccount(accountName, originalBalance);
            case PREMIUM:
                return new PremiumSavingsAccount(accountName, originalBalance);
            case CORPORATE:
                return new CorporateSavingsAccount(accountName, originalBalance);
            default:
                throw new IllegalArgumentException("不支持的客户类型：" + customerType);
        }
    }

    /**
     * 读取非空账户名称
     */
    private static String readAccountName(Scanner scanner) {
        while (true) {
            System.out.print("账户名称：");
            String input = scanner.nextLine();

            if (input != null && !input.trim().isEmpty()) {
                return input.trim();
            }

            System.out.println("账户名称不能为空，请重新输入。");
        }
    }

    /**
     * 读取客户类型
     */
    private static CustomerType readCustomerType(Scanner scanner) {
        while (true) {
            System.out.println("客户类型（STANDARD / PREMIUM / CORPORATE）：");
            String input = scanner.nextLine();

            if (input == null || input.trim().isEmpty()) {
                System.out.println("客户类型不能为空，请重新输入。");
                continue;
            }

            try {
                return CustomerType.valueOf(input.trim().toUpperCase(Locale.ROOT));
            } catch (IllegalArgumentException e) {
                System.out.println("客户类型输入无效，请输入 STANDARD、PREMIUM 或 CORPORATE。");
            }
        }
    }

    /**
     * 读取一个在 [min, max] 范围内的 BigDecimal 值
     */
    private static BigDecimal readBigDecimal(
            Scanner scanner,
            String prompt,
            BigDecimal min,
            BigDecimal max,
            String fieldName) {

        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();

            try {
                BigDecimal value = new BigDecimal(input.trim());

                if (value.compareTo(min) < 0) {
                    System.out.printf("%s 不能小于 %s，请重新输入。%n", fieldName, min.toPlainString());
                    continue;
                }

                if (value.compareTo(max) > 0) {
                    System.out.printf("%s 不能大于 %s，请重新输入。%n", fieldName, max.toPlainString());
                    continue;
                }

                return value;
            } catch (NumberFormatException e) {
                System.out.println("输入格式错误，请输入有效金额。");
            }
        }
    }
}

