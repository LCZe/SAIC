import java.math.BigDecimal;

public class StandardSavingsAccount extends SavingsAccount {

    public static final BigDecimal INTEREST_RATE = new BigDecimal("1.50");

    public StandardSavingsAccount(String accountName, BigDecimal originalBalance) {
        super(accountName, originalBalance, CustomerType.STANDARD, INTEREST_RATE);
    }
}

