import java.math.BigDecimal;
import java.math.RoundingMode;

public abstract class SavingsAccount {

    public static final BigDecimal MAX_BALANCE = new BigDecimal("1000000000.00");
    public static final BigDecimal MAX_INTEREST_RATE = new BigDecimal("100.00");

    private static final BigDecimal PERCENT_DIVISOR = new BigDecimal("100");
    private static final int MONEY_SCALE = 2;
    private static final int RATE_SCALE = 2;

    private String accountName;
    private CustomerType customerType;
    private BigDecimal originalBalance;
    private BigDecimal annualInterestRate;

    protected SavingsAccount(
            String accountName,
            BigDecimal originalBalance,
            CustomerType customerType,
            BigDecimal annualInterestRate) {

        setAccountName(accountName);
        setCustomerType(customerType);
        setOriginalBalance(originalBalance);
        setAnnualInterestRate(annualInterestRate);
    }

    // ---------- getters ----------

    public String getAccountName() {
        return accountName;
    }

    public CustomerType getCustomerType() {
        return customerType;
    }

    public BigDecimal getOriginalBalance() {
        return originalBalance;
    }

    public BigDecimal getAnnualInterestRate() {
        return annualInterestRate;
    }

    // ---------- setters with validation ----------

    public void setAccountName(String accountName) {
        if (accountName == null || accountName.trim().isEmpty()) {
            throw new IllegalArgumentException("账户名称不能为空。");
        }
        this.accountName = accountName.trim();
    }

    public void setCustomerType(CustomerType customerType) {
        if (customerType == null) {
            throw new IllegalArgumentException("客户类型不能为空。");
        }
        this.customerType = customerType;
    }

    public void setOriginalBalance(BigDecimal originalBalance) {
        if (originalBalance == null) {
            throw new IllegalArgumentException("原始余额不能为空。");
        }

        BigDecimal normalizedBalance = originalBalance.setScale(MONEY_SCALE, RoundingMode.HALF_UP);

        if (normalizedBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("原始余额不能为负数，当前值：" + normalizedBalance.toPlainString());
        }
        if (normalizedBalance.compareTo(MAX_BALANCE) > 0) {
            throw new IllegalArgumentException(
                    "原始余额超过最大限额 " + MAX_BALANCE.toPlainString() + "，当前值："
                            + normalizedBalance.toPlainString());
        }
        this.originalBalance = normalizedBalance;
    }

    protected void setAnnualInterestRate(BigDecimal annualInterestRate) {
        if (annualInterestRate == null) {
            throw new IllegalArgumentException("年利率不能为空。");
        }

        BigDecimal normalizedRate = annualInterestRate.setScale(RATE_SCALE, RoundingMode.HALF_UP);

        if (normalizedRate.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("年利率不能为负数，当前值：" + normalizedRate.toPlainString());
        }
        if (normalizedRate.compareTo(MAX_INTEREST_RATE) > 0) {
            throw new IllegalArgumentException(
                    "年利率超过最大允许值 " + MAX_INTEREST_RATE.toPlainString() + "%，当前值："
                            + normalizedRate.toPlainString());
        }
        this.annualInterestRate = normalizedRate;
    }

    // ---------- business methods ----------

    public BigDecimal calculateInterest() {
        return originalBalance
                .multiply(annualInterestRate)
                .divide(PERCENT_DIVISOR, MONEY_SCALE, RoundingMode.HALF_UP);
    }

    public BigDecimal calculateNewBalance() {
        return originalBalance.add(calculateInterest()).setScale(MONEY_SCALE, RoundingMode.HALF_UP);
    }

    public void displayAccountInfo() {
        System.out.println("----------------------------------------");
        System.out.println("Account Name     : " + accountName);
        System.out.println("Customer Type    : " + customerType);
        System.out.printf("Annual Rate      : %s%%%n", annualInterestRate.toPlainString());
        System.out.printf("Original Balance : %s%n", originalBalance.toPlainString());
        System.out.printf("Interest Earned  : %s%n", calculateInterest().toPlainString());
        System.out.printf("New Balance      : %s%n", calculateNewBalance().toPlainString());
    }
}
