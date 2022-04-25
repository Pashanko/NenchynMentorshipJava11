package org.example.bank.impl;

import org.example.bank.Bank;
import org.example.dto.*;

public class BankImpl implements Bank {
    @Override
    public BankCard createBankCard(User user, BankCardType cardType) {
        switch (cardType) {
            case DEBIT:
                return new DebitBankCard();
            case CREDIT:
                return new CreditBankCard();
            default:
                return null;
        }
    }
}
