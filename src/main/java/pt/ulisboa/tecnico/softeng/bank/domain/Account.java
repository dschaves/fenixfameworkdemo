package pt.ulisboa.tecnico.softeng.bank.domain;

import pt.ist.fenixframework.FenixFramework;

public abstract class Account extends Account_Base {

	public Account() {}

	public Account(String name, String code, String bankCode) {
		setName(name);
		setCode(code);
		setBalance(100);

		Bank.getBankByCode(bankCode).addAccount(this);
	}

	public void delete() {
		deleteDomainObject();
	}

	public void deposit(int value) {
		if (value < 0)
			return;
		setBalance(getBalance() + value);
	}

	public void withdraw(int value) {
		if (value > getBalance())
			return;
		setBalance(getBalance() - value);
	}

	public static Account getAccountByCode(String code) {
		for (Bank bank : FenixFramework.getDomainRoot().getBankSet()) {
			for (Account account : bank.getAccountSet()) {
				if (account.getCode().equals(code)) {
					return account;
				}
			}
		}
		return null;
	}

}
