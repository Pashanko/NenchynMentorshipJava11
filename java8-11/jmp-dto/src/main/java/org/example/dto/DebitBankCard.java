package org.example.dto;


public class DebitBankCard extends BankCard {
    @Override
    public String toString() {
        return "DebitBankCard{" +
                "number='" + number + '\'' +
                ", user=" + user +
                '}';
    }
}
