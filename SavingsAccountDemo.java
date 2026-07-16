import java.util.Scanner;

public class SavingsAccountDemo {

    private static final int ACCOUNT_COUNT = 5;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        SavingsAccount[] accounts = new SavingsAccount[ACCOUNT_COUNT];

        for (int i = 0; i < accounts.length; i++) {
            System.out.println("\n请输入第 " + (i + 1) + " 个储蓄账户资料");

            String accountName = readAccountName(scanner);

            double originalBalance = readDouble(
                    scanner,
                    "原始余额：",
                    0,
                    SavingsAccount.MAX_BALANCE,
                    "原始余额"
            );

            double annualInterestRate = readDouble(
                    scanner,
                    "年利率（输入n,代表年利率n%）：",
                    0,
                    SavingsAccount.MAX_INTEREST_RATE,
                    "年利率"
            );

            accounts[i] = new SavingsAccount(
                    accountName,
                    originalBalance,
                    annualInterestRate
            );
        }

        System.out.println("\n========== 储蓄账户计算结果 ==========");

        for (SavingsAccount account : accounts) {
            account.displayAccountInfo();
        }

        scanner.close();
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
     * 读取一个在 [min, max] 范围内的 double 值
     */
    private static double readDouble(
            Scanner scanner,
            String prompt,
            double min,
            double max,
            String fieldName) {

        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();

            try {
                double value = Double.parseDouble(input);

                if (value < min) {
                    System.out.printf("%s 不能小于 %.2f，请重新输入。%n", fieldName, min);
                    continue;
                }

                if (value > max) {
                    System.out.printf("%s 不能大于 %.2f，请重新输入。%n", fieldName, max);
                    continue;
                }

                return value;
            } catch (NumberFormatException e) {
                System.out.println("输入格式错误，请输入数字。");
            }
        }
    }
}
