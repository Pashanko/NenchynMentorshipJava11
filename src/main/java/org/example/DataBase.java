package org.example;

import org.example.dto.BankCard;
import org.example.dto.Subscription;
import org.example.dto.User;

import java.util.ArrayList;
import java.util.List;

public class DataBase {
    public static List<User> users = new ArrayList<>();

    public static List<BankCard> bankCards =new ArrayList<>();

    public static List<Subscription> subscriptions = new ArrayList<>();
}
