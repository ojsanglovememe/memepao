package com.example.javatest;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class Synchronized1Demo implements TestDemo {

    private   int x = 0;
    private  int y = 0;
    private synchronized void count(int newValue) {
        x = newValue;
        y = newValue;
        if (x != y) {
            System.out.println("x = " + x + ",y = " + y);
        }
    }

    @Override
    public void runTest() {
        new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 1_000_000_000; i++) {
                    count(i);
                }
                System.out.println("线程1 完毕");
            }
        }.start();

        new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 1_000_000_000; i++) {
                    count(i);
                }
                System.out.println("线程2 完毕");
            }
        }.start();
    }
}
