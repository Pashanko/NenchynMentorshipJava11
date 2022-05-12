package org.example;

import java.util.*;

/*
java 11: 200 - 310 millis
java 10: 190 - 250 millis
java 8: 220 - 250 millis
java 6: 220 - 270 millis
 */
public class Task1 {
    public static void main(String[] args) {
        ThreadSafeMap<Integer, Integer> map = new ThreadSafeMap();
        final int LIMIT = 100;
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < LIMIT; i++) {
                var start = System.currentTimeMillis();
                map.put(i, i);
                var end = System.currentTimeMillis();
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("#1#");
                System.out.println(Thread.currentThread().getName() + " Perf: "+ (end-start) + " " + map);
                System.out.println("#1#");
            }
        });
        Thread thread2 = new Thread(() -> {
            do {
                var start = System.currentTimeMillis();
                int sum = 0;
                for (int i = 0; i < map.size(); i++) {
                    sum += map.get(i);
                }
                var end = System.currentTimeMillis();
                System.out.println("#2#");
                System.out.println(Thread.currentThread().getName() + " Perf: "+ (end-start) + " " + sum);
                System.out.println("#2#");
            } while (map.size() != LIMIT);
        });
//        Thread mapThread = new Thread(() -> {
//            do {
//                try {
//                    Thread.sleep(10);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                System.out.println("###");
//                System.out.println(Thread.currentThread().getName() + " " + map);
//                System.out.println("###");
//            } while (map.size() != limit);
//        });
        var start = System.currentTimeMillis();
//        mapThread.start();
        thread1.start();
        thread2.start();
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        var end = System.currentTimeMillis();
        System.out.println("Perf: "+ (end-start));
    }
}

class ThreadSafeMap<K, V> {
    private final Map<K, V> map;

    public ThreadSafeMap() {
        map = new HashMap<K, V>();
    }

    synchronized void put(K key, V value) {
        map.put(key, value);
    }

    synchronized V get(K key) {
        return map.get(key);
    }

    synchronized V remove(K key) {
        return map.remove(key);
    }

    synchronized int size() {
        return map.size();
    }

    @Override
    synchronized public String toString() {
        return "ThreadSafeMap{" +
                "map=" + map +
                '}';
    }
}