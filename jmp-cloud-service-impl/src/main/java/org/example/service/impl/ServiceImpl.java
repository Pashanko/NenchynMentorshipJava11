package org.example.service.impl;

import org.example.DataBase;
import org.example.dto.BankCard;
import org.example.service.Service;
import org.example.dto.Subscription;
import org.example.dto.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ServiceImpl implements Service {

    @Override
    public void subscribe(BankCard card) {
        DataBase.subscriptions.add(new Subscription(card.getNumber(), LocalDate.now()));
    }

    @Override
    public Optional<Subscription> getSubscriptionByBankCardNumber(String cardNumber) {
        return DataBase.subscriptions.stream().filter(subscription -> subscription.getBankcard().equals(cardNumber)).findAny();
    }

    @Override
    public List<User> getAllUsers() {
        return DataBase.users;
    }

    @Override
    public double getAverageUsersAge() {
        return DataBase.users.stream().collect(Collectors.averagingInt(x ->
                x.getBirthday().until(LocalDate.now()).getYears()));
    }

    @Override
    public boolean isPayableUser(User user) {
        return user.getBirthday().until(LocalDate.now()).getYears() > 18;
    }

    @Override
    public List<Subscription> getAllSubscriptionsByCondition(Predicate<Subscription> predicate) {
        return DataBase.subscriptions.stream().filter(predicate).collect(Collectors.toList());
    }
}
