public class SavingsAccount {

    public static final double MAX_BALANCE = 1_000_000_000.0;
    public static final double MAX_INTEREST_RATE = 100.0;

    private String accountName;
    private double originalBalance;
    private double annualInterestRate;

    public SavingsAccount(
            String accountName,
            double originalBalance,
            double annualInterestRate) {

        setAccountName(accountName);
        setOriginalBalance(originalBalance);
        setAnnualInterestRate(annualInterestRate);
    }

    // ---------- getters ----------

    public String getAccountName() {
        return accountName;
    }

    public double getOriginalBalance() {
        return originalBalance;
    }

    public double getAnnualInterestRate() {
        return annualInterestRate;
    }

    // ---------- setters with validation ----------

    public void setAccountName(String accountName) {
        if (accountName == null || accountName.trim().isEmpty()) {
            throw new IllegalArgumentException("账户名称不能为空。");
        }
        this.accountName = accountName.trim();
    }

    public void setOriginalBalance(double originalBalance) {
        if (originalBalance < 0) {
            throw new IllegalArgumentException("原始余额不能为负数，当前值：" + originalBalance);
        }
        if (originalBalance > MAX_BALANCE) {
            throw new IllegalArgumentException(
                    "原始余额超过最大限额 " + MAX_BALANCE + "，当前值：" + originalBalance);
        }
        this.originalBalance = originalBalance;
    }

    public void setAnnualInterestRate(double annualInterestRate) {
        if (annualInterestRate < 0) {
            throw new IllegalArgumentException("年利率不能为负数，当前值：" + annualInterestRate);
        }
        if (annualInterestRate > MAX_INTEREST_RATE) {
            throw new IllegalArgumentException(
                    "年利率超过最大允许值 " + MAX_INTEREST_RATE + "%，当前值：" + annualInterestRate);
        }
        this.annualInterestRate = annualInterestRate;
    }

    // ---------- business methods ----------

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
