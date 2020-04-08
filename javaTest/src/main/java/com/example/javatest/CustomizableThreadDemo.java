package com.example.javatest;

/**
 * 模拟安卓的handlerThread
 */
public class CustomizableThreadDemo implements TestDemo {
    private CustomizableThread thread = new CustomizableThread();

    @Override
    public void runTest() {
        thread.start();
        thread.looper.setTask(new Runnable() {
            @Override
            public void run() {
                System.out.println("wj");
            }
        });
        thread.looper.setQuit();
    }

    class CustomizableThread extends Thread {
        Looper looper = new Looper();
    }

    class Looper{
        private Runnable task ;
        private boolean quit;
        synchronized void setTask(Runnable task){
            this.task = task;
        }

        synchronized void setQuit(){
            quit = true;
        }

        public void loop() {
            while (!quit){
                synchronized (this){
                    if (task!=null){
                        task.run();
                        task = null;
                    }
                }
            }
        }
    }

}
