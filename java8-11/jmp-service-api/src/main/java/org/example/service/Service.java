package org.example.service;

import org.example.dto.BankCard;
import org.example.dto.Subscription;
import org.example.dto.User;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public interface Service {
    void subscribe(BankCard card);

    Optional<Subscription> getSubscriptionByBankCardNumber(String cardNumber);

    List<User> getAllUsers();

    double getAverageUsersAge();

    boolean isPayableUser(User user);

    List<Subscription> getAllSubscriptionsByCondition(Predicate<Subscription> predicate);
}
