package lesson171212;


import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FivePhilosophers_HomeWork {

    public static void main(String[] args) {

        // sit around the table
        Thread t1 = new Thread(()->{
            Philosopher ph = new Philosopher(0, 1);
            ph.eat();
        });
        Thread t2 = new Thread(()->{
            Philosopher ph = new Philosopher(1, 2);
            ph.eat();
        });
        Thread t3 = new Thread(()->{
            Philosopher ph = new Philosopher(2, 3);
            ph.eat();
        });
        Thread t4 = new Thread(()->{
            Philosopher ph = new Philosopher(3, 4);
            ph.eat();
        });
        Thread t5 = new Thread(()->{
            Philosopher ph = new Philosopher(4, 0);
            ph.eat();
        });

        // start eating
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
    }
}

class Philosopher {

    private static Lock[] sticks = new Lock[5];
    static {
        for (int i = 0; i < sticks.length; i++) {
            sticks[i] = new ReentrantLock();
        }
    }

    Philosopher(int left, int right) {
        this.left = left;
        this.right = right;
    };

    private int left, right;

    void eat() {
        sticks[left < right ? left : right].lock();
        System.out.println("Grabbed " + (left < right ? left : right));
        sticks[left > right ? left : right].lock();
        System.out.println("Grabbed " + (left > right ? left : right));

        try {
            Thread.sleep(1000);
            System.out.println(" I am eating with " + left + " and " + right);

        } catch(Exception ex) {
        } finally {
            sticks[left > right ? left : right].unlock();
            System.out.println("Released " + (left > right ? left : right));
            sticks[left < right ? left : right].unlock();
            System.out.println("Released " + (left < right ? left : right));
        }
    }
}
