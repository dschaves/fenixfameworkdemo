package pt.ulisboa.tecnico.softeng.bank.domain;

import pt.ist.fenixframework.FenixFramework;

public class SavingsAccount extends Account {

	public SavingsAccount(String name, String code, String bankCode) {
		super(name, code, bankCode);
	}

	public void deposit(int value) {
		if (value < 0 || (value % 100 != 0))
			return;
		setBalance(getBalance() + value);
	}

	public void withdraw(int value) {
		if (value != getBalance())
			return;
		setBalance(getBalance() - value);
	}

}
