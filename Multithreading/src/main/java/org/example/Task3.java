package org.example;

import java.util.ArrayList;
import java.util.List;

public class Task3 {
    static final MessageBus messageBus = new MessageBus();

    public static void main(String[] args) throws InterruptedException {

        while (true) {
            Thread thread1 = new Thread(() -> {
                try {
                    messageBus.producer("Thread: " + Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            Thread thread2 = new Thread(() -> {
                try {
                    messageBus.consumer();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

            thread1.start();
            thread2.start();

            Thread.sleep(1000);
        }
    }
}

class MessageBus {
    private final List<String> buffer = new ArrayList<>();
    private static final int LIMIT = 5;

    public synchronized void producer(String message) throws InterruptedException {
        while (buffer.size() == LIMIT) {
            wait();
        }
        buffer.add(message);
        System.out.println("Message added: " + message);
        notify();
    }

    public synchronized void consumer() throws InterruptedException {
        while (buffer.size() == 0) {
            wait();
        }
        String message = buffer.get(0);
        buffer.remove(0);
        System.out.println("Message removed: " + message);
        notify();
    }
}

