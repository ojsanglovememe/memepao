package com.example.javatest;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Synchronized3Demo implements TestDemo {

    private int x = 0;
    private int y = 0;
    private String name;
    private Object monitor1 = new Object();
    private Object monitor2 = new Object();
    private Lock lock = new ReentrantLock();

    private ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private ReentrantReadWriteLock.ReadLock readLock = readWriteLock.readLock();
    private ReentrantReadWriteLock.WriteLock writeLock = readWriteLock.writeLock();

    private void read() {
        readLock.lock();
        try {
            System.out.println("x = " + x + ",y = " + y);
        } finally {
            readLock.unlock();
        }
    }

    private void write() {
        writeLock.lock();
        try {
            x = 10;
            y = 10;
        } finally {
            writeLock.unlock();
        }

    }


    private void rest() {
        lock.lock();
        try {
            x = 0;
            y = 0;
        } catch (Exception e) {

        } finally {
            lock.unlock();
        }
    }

    private void count(int newValue) {
        synchronized (monitor1) {
            x = newValue;
            y = newValue;
        }
    }

    private void setName(String newName) {
        synchronized (monitor2) {
            name = newName;
        }
    }

    @Override
    public void runTest() {
//        new Thread() {
//            @Override
//            public void run() {
//                for (int i = 0; i < 1_000_000; i++) {
//                    count(i);
//                }
//                System.out.println("线程1 ===> x = "+x);
//            }
//        }.start();
//
//        new Thread() {
//            @Override
//            public void run() {
//                for (int i = 0; i < 1_000_000; i++) {
//                    count(i);
//                }
//                System.out.println("线程2 ===> x = "+x);
//            }
//        }.start();
    }
}
