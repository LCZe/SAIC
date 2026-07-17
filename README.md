# SAIC

储蓄账户利息计算系统（Savings Account Interest Calculator）。

## 当前实现

- `CustomerType.java`：定义 `STANDARD`、`PREMIUM`、`CORPORATE` 三种客户类型。
- `SavingsAccount.java`：抽象父类，统一处理账户名、客户类型、`BigDecimal` 余额与利息计算。
- `StandardSavingsAccount.java`、`PremiumSavingsAccount.java`、`CorporateSavingsAccount.java`：三个子类分别提供不同的固定年利率。
- `SavingsAccountDemo.java`：控制台输入示例，按客户类型创建不同账户。
- `SavingsAccountTest.java`：基础自检程序，验证边界条件与利息计算结果。
