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
        callable();
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
//                return new Thread(runnable);
            }
        };

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("run 当前线程为："+Thread.currentThread().getName());
            }
        };

        factory.newThread(runnable).start();
        factory.newThread(runnable).start();
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
}
