package lesson171205;

import lesson171201.Utils;

import java.util.ArrayList;
import java.util.List;

public class MyBlockingQueue {

    private Object mutex = new Object();

    List<Runnable> tasks = new ArrayList<>();

    void put(Runnable runnable) {
        synchronized (mutex) {
            if (!tasks.isEmpty()) {
                try {
                    System.out.println("PUT: '!tasks.isEmpty()', going to wait");
                    mutex.wait();
                    System.out.println("PUT: await finished!");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            tasks.add(runnable);
            mutex.notify();
        }
    }

    Runnable get() {
        Runnable ret = null;
        synchronized (mutex) {
            if (tasks.isEmpty()) {
                try {
                    System.out.println("GET: 'tasks.isEmpty()', going to wait");
                    mutex.wait();
                    System.out.println("GET: await finished!");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            ret = tasks.remove(0);
            System.out.println("GET: task get successfull: " + ret);
            mutex.notify();
        }
        return ret;
    }

    boolean isEmpty() {
        return tasks.isEmpty();
    }



    // User of MyBlockingQueue
    public static void main(String[] args) {

        MyBlockingQueue myBlockingQueue = new MyBlockingQueue();

        myBlockingQueue.put(
                () -> {
                    Utils.pause(1000);
                });

        new Thread( ()->{
            while (true) {
                myBlockingQueue.get().run();
                if (myBlockingQueue.isEmpty()) {
                    return;
                }
            }
        } ).start();
        System.out.println("One task has been put and worker started");

        for (int i = 0; i < 4; i++) {
            int j = i;
            myBlockingQueue.put(
                    () -> {
                        Utils.pause(800);
                        System.out.println("Task №" + j + " has been put");
                    });
            System.out.println("Task №" + i + " has been taken");
        }
    }
}
