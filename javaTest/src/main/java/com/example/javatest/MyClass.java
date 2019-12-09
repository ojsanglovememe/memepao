package com.example.javatest;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;

public class MyClass {
    public static void main(String[] args) {
        System.out.println("当前线程为："+Thread.currentThread().getName());
//        thread();
//        threadFactory();
//        executer();
//        callable();
//        synchronized1Test();
//        synchronized2Test();
//        interruptTest();
        waitTest();
    }

    static void thread(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("runnable 当前线程为："+Thread.currentThread().getName());
                System.out.println("先执行runnable。。。");
            }
        }){
            @Override
            public void run() {
//                super.run();
                System.out.println("run 当前线程为："+Thread.currentThread().getName());
            }
        };
        thread.start();
    }




    static void threadFactory(){
        ThreadFactory factory = new ThreadFactory() {
            int count = 0;
            @Override
            public Thread newThread(Runnable runnable) {
                count++;
                return new Thread(runnable,"Thread- "+count);
            }
        };

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("run 当前线程为："+Thread.currentThread().getName());
            }
        };
        Thread thread = factory.newThread(runnable);
        thread.start();
    }


//    static void executer(){
//        Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("run 当前线程为："+Thread.currentThread().getName());
//            }
//        };
//        ExecutorService executorService = Executors.newCachedThreadPool();
//        e.execute(runnable);
//        e.execute(runnable);
//        e.execute(runnable);


//        ExecutorService executorService = Executors.newFixedThreadPool(20);
//        ExecutorService executorService = Executors.newScheduledThreadPool(20);
//        for (int i=0;i<100;i++){
//            executorService.execute(runnable);
//        }
//        executorService.shutdown();
//    }


    static void callable(){
        Callable<String> callable = () -> {
            try {
                System.out.println(" 当前线程为："+Thread.currentThread().getName());
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "傻狍么么";
        };

        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<String> future = executorService.submit(callable);
        try {
            String result = future.get();
            System.out.println(" 当前线程为："+Thread.currentThread().getName()+" ,result = "+result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }


    /**
     * 第一次
     * x = 10834,y = 10930
     * x = 12231,y = 12277
     * x = 2799,y = 2967
     * x = 14983,y = 15001
     * x = 16076,y = 16100
     * x = 18131,y = 18154
     * x = 8421,y = 8468
     * x = 18385,y = 18399
     *
     * 第二次
     * x = 5002,y = 5219
     * x = 6255,y = 6300
     * x = 3037,y = 3071
     * x = 7969,y = 7991
     *
     * 第三次
     * x = 11616,y = 11721
     * x = 13321,y = 13417
     * x = 16770,y = 17017
     */
    static synchronized void synchronized1Test(){
            new Synchronized1Demo().runTest();
    }


    /**
     * 第一次
     * 线程1 ===> x = 359437
     * 线程2 ===> x = 1246774
     *
     * 第二次
     *线程1 ===> x = 178557
     * 线程2 ===> x = 1025604
     *
     * 第三次
     *线程1 ===> x = 193572
     * 线程2 ===> x = 1086015
     */
    static synchronized void synchronized2Test(){
        new Synchronized2Demo().runTest();
    }

    static void interruptTest(){
        new ThreadInteractionDemo().runTest();
    }

    static void waitTest(){
        new WaitDemo().runTest();
    }
}
