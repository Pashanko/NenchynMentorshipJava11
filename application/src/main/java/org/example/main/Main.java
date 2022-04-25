package org.example.main;

import org.example.DataBase;
import org.example.bank.Bank;
import org.example.bank.impl.BankImpl;
import org.example.dto.BankCard;
import org.example.dto.BankCardType;
import org.example.dto.Subscription;
import org.example.dto.User;
import org.example.service.CardNotFoundException;
import org.example.service.Service;
import org.example.service.impl.ServiceImpl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        DataBase.users.addAll(List.of(
                new User("Paul", "Nenchyn", LocalDate.of(2002,8,22)),
                new User("Vlad", "Pats", LocalDate.of(2002,5,24)),
                new User("Oleh", "Petrol", LocalDate.of(2004,11,25)),
                new User("Max", "Haliant", LocalDate.of(2005,1,1)),
                new User("Rostyslav", "March", LocalDate.of(2001,12,15)),
                new User("Danylo", "Tadei", LocalDate.of(2002,4,13))
        ));
        DataBase.subscriptions.addAll(List.of(new Subscription("1234 5678 9101 1123",
                LocalDate.of(2020, 8, 23))));
        DataBase.bankCards.addAll(List.of(
                new BankCard("1234 5678 9101 1123", DataBase.users.get(0)),
                new BankCard("3453 3425 6754 8564", DataBase.users.get(1)),
                new BankCard("7545 4575 5462 1243", DataBase.users.get(2)),
                new BankCard("1325 4362 5678 5123", DataBase.users.get(3)),
                new BankCard("2346 9566 5374 2344", DataBase.users.get(4)),
                new BankCard("9999 1244 6665 3254", DataBase.users.get(5))
        ));
        while (true) {
            Main service = new Main();
            Scanner mainScanner = new Scanner(System.in);

            System.out.println("Welcome!\n1.Create user with card\n" +
                    "2.Get all users\n3.Subscribe (card number needed)\n4.Get subscription (card number needed)\n" +
                    "5.Get average users age\n6.Is user payable? (card number needed)");

            String number = mainScanner.nextLine();

            switch (number) {
                case "1":
                    service.createUser();
                    break;
                case "2":
                    service.getAllUsers();
                    break;
                case "3":
                    service.subscribe();
                    break;
                case "4":
                    service.getSubscriptionByCardNumber();
                    break;
                case "5":
                    service.getAverageUsersAge();
                    break;
                case "6":
                    service.isUserPayable();
                    break;
                default:
                    System.out.println("Try again");
                    break;
            }
        }
    }

    private final Bank bank = new BankImpl();
    private final Service service = new ServiceImpl();
    private final Scanner scanner = new Scanner(System.in);

    public void createUser() {
        User user = new User();

        System.out.println("Enter name:");
        user.setName(scanner.nextLine());
        System.out.println("Enter surname:");
        user.setSurname(scanner.nextLine());

        String birthday;
        System.out.println("Enter birthday in **.**.**** format:");
        birthday = scanner.nextLine();

        if (birthday.matches("^\\d\\d\\.\\d\\d\\.\\d\\d\\d\\d$")) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            user.setBirthday(LocalDate.parse(birthday, formatter));
            DataBase.users.add(user);

            System.out.println("Witch type of card do you prefere?:\n1. Credit\n2. Debit");

            BankCard card;
            int cardTypeInt = scanner.nextInt();

            switch (cardTypeInt) {
                case 1:
                    card = bank.createBankCard(user, BankCardType.CREDIT);
                    break;
                case 2:
                    card = bank.createBankCard(user, BankCardType.DEBIT);
                    break;
                default:
                    DataBase.users.remove(user);
                    System.out.println("Bad card type, try again");
                    return;
            }

            DataBase.bankCards.add(card);
            System.out.println("New card created: " + card.toString());
            return;
        }

        System.out.println("Bad birthday format, try again");
    }

    public void subscribe() {
        String cardNumber = getCardNumber();

        if (cardNumber.equals("0")) {
            System.out.println("Bad card number");
            return;
        }

        BankCard card = DataBase.bankCards.stream()
                .filter(bankCard -> bankCard.getNumber().equals(cardNumber))
                .findFirst()
                .orElse(null);

        if (Objects.isNull(card)) {
            System.out.println("Card not found");
            return;
        }

        service.subscribe(card);
        System.out.println("Subscription - done");
    }

    public void getSubscriptionByCardNumber() {
        String cardNumber = getCardNumber();

        if (cardNumber.equals("0")) {
            System.out.println("Bad card number, try again");
            return;
        }

        Subscription subscription =
                service.getSubscriptionByBankCardNumber(cardNumber)
                        .orElseThrow(CardNotFoundException::new);

        System.out.println(subscription.toString());
    }

    public void getAllUsers() {
        System.out.println(service.getAllUsers().toString());
    }

    public void getAverageUsersAge() {
        System.out.println(service.getAverageUsersAge());
    }

    public void isUserPayable() {
        String cardNumber = getCardNumber();

        BankCard card = DataBase.bankCards.stream()
                .filter(bankCard -> bankCard.getNumber().equals(cardNumber))
                .findFirst()
                .orElse(null);

        if (Objects.isNull(card)) {
            System.out.println("User not found");
            return;
        }

        if (service.isPayableUser(card.getUser())) {
            System.out.println("User is payable");
        } else {
            System.out.println("User is under 18");
        }
    }

    public String getCardNumber() {
        String cardNumber;
        System.out.println("Enter card number:");
        cardNumber = scanner.nextLine();

        return cardNumber;
    }

}