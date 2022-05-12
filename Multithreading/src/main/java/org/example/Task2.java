package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Task2 {
    static final List<Double> list = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {

        Random random = new Random();

        while (true) {
            Thread thread1 = new Thread(() -> {
                list.add((double) random.nextInt(10));
            });

            Thread thread2 = new Thread(() -> {
                synchronized (list) {
                    double temp = 0;
                    for (double e : list) {
                        temp += e;
                    }
                    System.out.println(temp);
                }
            });

            Thread thread3 = new Thread(() -> {
                synchronized (list) {
                    double temp = 0;
                    for (double e : list) {
                        temp += Math.pow(e, 2);
                    }
                    System.out.println(Math.sqrt(temp));
                }
            });
            thread1.start();
            thread2.start();
            thread3.start();
            Thread.sleep(500);
        }
    }
}
