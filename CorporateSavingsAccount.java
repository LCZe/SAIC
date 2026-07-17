import java.math.BigDecimal;

public class CorporateSavingsAccount extends SavingsAccount {

    public static final BigDecimal INTEREST_RATE = new BigDecimal("3.50");

    public CorporateSavingsAccount(String accountName, BigDecimal originalBalance) {
        super(accountName, originalBalance, CustomerType.CORPORATE, INTEREST_RATE);
    }
}

