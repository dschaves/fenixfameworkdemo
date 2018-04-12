package pt.ulisboa.tecnico.softeng.bank.domain;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Test;

import pt.ist.fenixframework.Atomic;
import pt.ist.fenixframework.Atomic.TxMode;
import pt.ist.fenixframework.FenixFramework;

public class BankPersistenceTest {
	@Test
	public void success() {
		atomicProcess();
		atomicAssert();
	}

	@Test
	public void account() {
		atomicProcess();
		atomicAccount();
		atomicAssertAccount();
	}

	@Test
	public void accountWithdrawalAndDeposit() {
		atomicProcess();
		atomicAccount();
		atomicDeposit();
		atomicAssertBalanceAfterDeposit();
		atomicWithdraw();
		atomicAssertBalanceAfterWithdraw();
	}

	@Test
	public void savingsAccountWithdrawalAndDeposit() {
		atomicProcess();
		atomicSavingsAccount();
		atomicDepositSavings();
		atomicAssertBalanceAfterDepositSavings();
		atomicWithdrawSavings();
		atomicAssertBalanceAfterWithdrawSavings();
	}
	
	@Test
	public void bankTotalBalance() {
		atomicProcess();
		atomicAccount();
		atomicSecondAccount();
		atomicAssertBalance();
	}

	@Atomic(mode = TxMode.WRITE)
	public void atomicSavingsAccount() {
		new SavingsAccount("sacc", "SC01", "BK01");
	}

	@Atomic(mode = TxMode.WRITE)
	public void atomicDepositSavings() {
		Account account = Account.getAccountByCode("SC01");
		account.deposit(100);
	}

	@Atomic(mode = TxMode.WRITE)
	public void atomicWithdrawSavings() {
		Account account = Account.getAccountByCode("SC01");
		account.withdraw(25);
	}

	@Atomic(mode = TxMode.READ)
	public void atomicAssertBalanceAfterDepositSavings() {
		Bank bank = Bank.getBankByCode("BK01");
		Account account = bank.getAccountSet().iterator().next();

		assertEquals(200, account.getBalance());
	}

	@Atomic(mode = TxMode.READ)
	public void atomicAssertBalanceAfterWithdrawSavings() {
		Bank bank = Bank.getBankByCode("BK01");
		Account account = bank.getAccountSet().iterator().next();

		assertEquals(200, account.getBalance());
	}

	@Atomic(mode = TxMode.WRITE)
	public void atomicProcess() {
		new Bank("Money", "BK01");
	}

	@Atomic(mode = TxMode.WRITE)
	public void atomicDeposit() {
		Account account = Account.getAccountByCode("AC01");
		account.deposit(25);
	}

	@Atomic(mode = TxMode.WRITE)
	public void atomicWithdraw() {
		Account account = Account.getAccountByCode("AC01");
		account.withdraw(25);
	}

	@Atomic(mode = TxMode.READ)
	public void atomicAssertBalanceAfterDeposit() {
		Bank bank = Bank.getBankByCode("BK01");
		Account account = bank.getAccountSet().iterator().next();

		assertEquals(125, account.getBalance());
	}

	@Atomic(mode = TxMode.READ)
	public void atomicAssertBalanceAfterWithdraw() {
		Bank bank = Bank.getBankByCode("BK01");
		Account account = bank.getAccountSet().iterator().next();

		assertEquals(100, account.getBalance());
	}

	@Atomic(mode = TxMode.READ)
	public void atomicAssertBalance() {
		Bank bank = Bank.getBankByCode("BK01");
		Account account = bank.getAccountSet().iterator().next();

		assertEquals(200, bank.totalBalance());
	}

	@Atomic(mode = TxMode.WRITE)
	public void atomicAccount() {
		new WithdrawalAccount("acc", "AC01", "BK01");
	}

	@Atomic(mode = TxMode.WRITE)
	public void atomicSecondAccount() {
		new WithdrawalAccount("acd", "AC02", "BK01");
	}

	@Atomic(mode = TxMode.READ)
	public void atomicAssert() {
		Bank bank = Bank.getBankByCode("BK01");

		assertEquals("Money", bank.getName());
	}

	@Atomic(mode = TxMode.READ)
	public void atomicAssertAccount() {
		Bank bank = Bank.getBankByCode("BK01");
		Account account = bank.getAccountSet().iterator().next();

		assertEquals("Money", bank.getName());
		assertEquals("acc", account.getName());
	}

	@After
	@Atomic(mode = TxMode.WRITE)
	public void tearDown() {
		for (Bank bank : FenixFramework.getDomainRoot().getBankSet()) {
			bank.delete();
		}
	}

}
