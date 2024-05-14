import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.Semaphore;

public class Philosopher implements Runnable {
    private int id;
    private Semaphore forks;
    private Lock lock;

    public Philosopher(int id, Semaphore forks, Lock lock) {
        this.id = id;
        this.forks = forks;
        this.lock = lock;
    }

    @Override
    public void run() {
        while (true) {
            think();
            lock.lock();
            try {
                if (forks.tryAcquire(2)) {
                    System.out.println("Philosopher " + id + " successfully picked up forks");
                    eat();
                    System.out.println("Philosopher " + id + " has thought and eaten");
                    forks.release(2);
                } else {
                    System.out.println("Philosopher " + id + " failed to pick up forks");
                }
            } finally {
                lock.unlock();
            }
            try {
                Thread.sleep(1000000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void think() {
        System.out.println("Philosopher " + id + " is thinking");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void eat() {
        System.out.println("Philosopher " + id + " is eating");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}