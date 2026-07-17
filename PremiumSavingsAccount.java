import java.math.BigDecimal;

public class PremiumSavingsAccount extends SavingsAccount {

    public static final BigDecimal INTEREST_RATE = new BigDecimal("2.50");

    public PremiumSavingsAccount(String accountName, BigDecimal originalBalance) {
        super(accountName, originalBalance, CustomerType.PREMIUM, INTEREST_RATE);
    }
}

