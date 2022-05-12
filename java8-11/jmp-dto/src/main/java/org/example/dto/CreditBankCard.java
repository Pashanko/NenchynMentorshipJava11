package org.example.dto;


public class CreditBankCard extends BankCard {
    @Override
    public String toString() {
        return "CreditBankCard{" +
                "number='" + number + '\'' +
                ", user=" + user +
                '}';
    }
}
