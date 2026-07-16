import java.util.Scanner;

class SavingsAccount {
    private String accountName;
    private double originalBalance;
    private double annualInterestRate;

    public SavingsAccount(
            String accountName,
            double originalBalance,
            double annualInterestRate) {

        this.accountName = accountName;
        this.originalBalance = originalBalance;
        this.annualInterestRate = annualInterestRate;
    }

    public double calculateInterest() {
        return originalBalance * annualInterestRate / 100;
    }

    public double calculateNewBalance() {
        return originalBalance + calculateInterest();
    }

    public void displayAccountInfo() {
        System.out.println("----------------------------------------");
        System.out.println("Account Name     : " + accountName);
        System.out.printf("Annual Rate      : %.2f%%%n", annualInterestRate);
        System.out.printf("Original Balance : %.2f%n", originalBalance);
        System.out.printf("Interest Earned  : %.2f%n", calculateInterest());
        System.out.printf("New Balance      : %.2f%n", calculateNewBalance());
    }
}

public class SavingsAccountDemo {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        SavingsAccount[] accounts = new SavingsAccount[5];

        for (int i = 0; i < accounts.length; i++) {
            System.out.println("\n请输入第 " + (i + 1) + " 个储蓄账户资料");

            System.out.print("账户名称：");
            String accountName = scanner.nextLine();

            double originalBalance = readPositiveDouble(
                    scanner,
                    "原始余额："
            );

            double annualInterestRate = readPositiveDouble(
                    scanner,
                    "年利率（例如 3.5 表示 3.5%）："
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

    private static double readPositiveDouble(
            Scanner scanner,
            String message) {

        while (true) {
            System.out.print(message);
            String input = scanner.nextLine();

            try {
                double value = Double.parseDouble(input);

                if (value >= 0) {
                    return value;
                }

                System.out.println("请输入大于或等于 0 的数字。");
            } catch (NumberFormatException e) {
                System.out.println("输入格式错误，请输入数字。");
            }
        }
    }
}
