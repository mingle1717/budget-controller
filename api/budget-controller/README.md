# All Methods For This Project:

## Security Methods:
- Login method in SecurityController
- 

## Budget Methods:
- createBudget in BudgetController
- createBudget in BudgetService
- createBudget in BudgetRepository

### Tests for createBudget:
#### BudgetService:
- shouldCreateValidBudget()
- shouldNotCreateNullBudget()
- shouldNotCreateBudgetWithCategoryPercentagesAddingUpPastOneHundred()
- shouldNotCreateBudgetWithNullBudgetName()
- shouldNotCreateBudgetWithNullBalance()
- shouldNotCreateBudgetWithNegativeBalance()
- shouldNotCreateBudgetWithBalanceOfZero()

#### BudgetRepository:
- shouldCreateBudget()


- editBudgetCategories in CategoryService
- editBudgetCategories in CategoryRepository

### Tests for editBudgetCategories:
#### CategoryService:
- shouldEditValidCategories
- shouldNotEditCategoriesToSumPercentagesOverOneHundred
- shouldNotEditAnyCategoryToHaveANegativePercentage
- 
#### CategoryRepository:
- 

- editBudget in BudgetController
- editBudgetCategories in CategoryService (re-used method)
- editBudgetCategories in CategoryRepository (re-used method)
- editBudgetBalanceOrName in BudgetService
- editBudgetBalanceOrName in BudgetRepository

### Tests for editBudget:
#### BudgetService:
-
#### BudgetRepository:
-

- addTransactionToCategory in TransactionController
- addTransactionToCategory in TransactionService
- addTransactionToCategory in TransactionRepository

### Tests for addTransactionToCategory:
#### TransactionService:
-
#### TransactionRepository:
- 

- addAutoTriggerTransactionToCategory in TransactionController
- addAutoTriggerTransactionToCategory in TransactionService
- addAutoTriggerTransactionToCategory in TransactionRepositoru

### Tests for addAutoTriggerTransactionToCategory:
#### TransactionService:
-
#### TransactionRepository:
- 

- displayBudgetInformation in BudgetController
- displayBudgetInformation in BudgetService
- displayBudgetInformation in BudgetRepository

### Tests for displayBudgetInformation:
#### BudgetService:
- 
#### BudgetRepository:
-

- getBudgetCategoryInfo in BudgetController
- getBudgetCategoryInfo in CategoryService
- getBudgetCategoryInfo in CategoryRepository

### Tests for getBudgetCategoryInfo:
#### CategoryService:
- 
#### CategoryRepository:
- 

- showActualSpending in TransactionController
- showActualSpending in TransactionService
- getAllTransactions in TransactionRepository

### Tests for showActualSpending:
#### TransactionService:
-
#### TransactionRepository:
- 

- viewTransactions in TransactionController
- viewTransactions in TransactionService (will make use of getAllTransactions from TransactionRepository)

### Tests for viewTransactions:
#### TransactionService:
-
#### TransactionRepository:
- 

- editTransaction in TransactionController
- editTransaction in TransactionService
- editTransaction in TransactionRepository

### Tests for editTransaction:
#### TransactionService:
-
#### TransactionRepository:
-

- deleteTransaction in TransactionController
- deleteTransaction in TransactionService
- deleteTransaction in TransactionRepository

### Tests for deleteTransaction:
#### TransactionService:
-
#### TransactionRepository:
-


- getTotalSpending in TransactionController
- getTotalSpending in TransactionService (will also make use of getAllTransactions method)

### Tests for getTotalSpending:
#### TransactionService:
-
#### TransactionRepository:
- 

- deleteBudget in BudgetController
- deleteBudget in BudgetService
- deleteBudgetCategories in CategoryRepository
- deleteBudgetPrimaryInfo in BudgetRepository