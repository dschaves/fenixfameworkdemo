package pt.ulisboa.tecnico.softeng.bank.domain;

import pt.ist.fenixframework.FenixFramework;

public class WithdrawalAccount extends Account {
	public WithdrawalAccount(String name, String code, String bankCode) {
		super(name, code, bankCode);
	}
}
