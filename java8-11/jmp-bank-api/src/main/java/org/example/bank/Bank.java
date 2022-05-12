package org.example.bank;

import org.example.dto.BankCard;
import org.example.dto.BankCardType;
import org.example.dto.User;

public interface Bank {
    BankCard createBankCard(User user, BankCardType cardType);
}
