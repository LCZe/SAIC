---
mode: agent
tools:
  - read_file
  - grep_search
  - replace_string_in_file
description: >
  Generate JUnit-style unit tests for a Java SavingsAccount subclass.
  Use when: adding a new account type, testing an untested class,
  writing test cases, coverage gaps, generate tests, write tests.
---

# Generate Unit Tests for a SavingsAccount Subclass

## Precise Prompt (Four-Part Structure)

### Part 1 — Role / Context
You are a senior Java engineer writing test cases for a bank savings-account
system. The base class is `SavingsAccount` (abstract). Concrete subclasses are
`StandardSavingsAccount`, `PremiumSavingsAccount`, and
`CorporateSavingsAccount`. The project uses plain Java (no JUnit dependency);
tests are implemented as static methods that call a hand-rolled `assertEquals`
helper and track `passed` / `failed` counters, exactly as in
`SavingsAccountTest.java`.

### Part 2 — Task
Add a new private static test method to `SavingsAccountTest.java` for the
target class specified by the user (default: the class currently open in the
editor). The method must:
1. Instantiate the target subclass with representative inputs.
2. Assert `getAccountName()`, `getCustomerType()`, `getAnnualInterestRate()`,
   `calculateInterest()`, and `calculateNewBalance()` all return expected
   values (derive expected values from the `INTEREST_RATE` constant and the
   `originalBalance` you chose).
3. Test at least **two boundary / error conditions** (e.g., null name,
   negative balance) using a `try/catch` that asserts an
   `IllegalArgumentException` is thrown.
4. Print `"✓ <methodName> 通过"` on success and call `passed++` or `failed++`
   appropriately.

### Part 3 — Format
- Output **only valid Java source** — no markdown fences, no explanatory prose
  outside comments.
- Append the new method(s) inside the existing `SavingsAccountTest` class,
  before the closing `}`.
- Also add a call to the new method inside `main()` in alphabetical order
  relative to existing calls.
- Preserve the existing indentation style (4 spaces).

### Part 4 — Constraints
- Do **not** add any import statements (all needed types are already imported).
- Do **not** modify any existing test methods.
- Do **not** introduce a JUnit or third-party dependency.
- Keep each test method under 40 lines.
- All hardcoded `BigDecimal` literals must use the `bd(String)` helper already
  present in the file.

---

## Vague Prompt (for comparison)

> "Write some tests for the savings account classes."

### Why the vague prompt fails
| Problem | Impact |
|---------|--------|
| No target class specified | AI may test all three classes or pick one at random |
| No framework constraint | AI likely adds JUnit 5 annotations that won't compile |
| No format guidance | AI wraps output in markdown, requiring manual extraction |
| No boundary cases required | Happy-path only; error paths remain uncovered |
| No style constraint | Inconsistent indentation / helper usage breaks the file |

### What the precise prompt guarantees
- Compiles on the first paste — no new imports, no framework changes.
- At least two error-path assertions per class, matching project conventions.
- `main()` is updated automatically so the new tests are actually executed.
- Output is drop-in Java, not prose with embedded code snippets.

---

## Usage

Invoke this prompt from GitHub Copilot Chat:

```
/generate-tests StandardSavingsAccount
```

Or leave the argument blank to test whichever class is currently open:

```
/generate-tests
```

Copilot will read `SavingsAccountTest.java` and the target class file, derive
expected values, and emit the new test method(s) ready to paste (or
auto-applied if running in agent mode).
