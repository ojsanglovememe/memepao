package com.example.javatest;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Synchronized2Demo implements TestDemo {

    private  int x = 0;

    private void count() {
        x ++;
    }

    @Override
    public void runTest() {
        new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 100_100_100; i++) {
                    count();
                }
                System.out.println("线程1 ===> x = "+x);
            }
        }.start();

        new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 100_100_100; i++) {
                    count();
                }
                System.out.println("线程2 ===> x = "+x);
            }
        }.start();
    }
}
