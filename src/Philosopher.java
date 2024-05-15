import java.util.concurrent.Semaphore;

public class Philosopher implements Runnable {
    private static final int THINK_TIME = 1000;
    private static final int EAT_TIME = 1000;
    private static final int WAIT_TIME = 10000;

    private int id;
    private Semaphore availableForks;

    public Philosopher(int id, Semaphore availableForks) {
        this.id = id;
        this.availableForks = availableForks;
    }

    @Override
    public void run() {
        think();
        while (true) {
            if (tryPickupForks()) {
                eat();
                putDownForks();
                System.out.println("Philosopher " + id + " has thought and eaten");
                break;
            } else {
                System.out.println("Philosopher " + id + " failed to pick up forks");
                think();
            }
        }
        think();
    }

    private void think() {
        System.out.println("Philosopher " + id + " is thinking");
        sleep(THINK_TIME);
    }

    private boolean tryPickupForks() {
        return availableForks.tryAcquire(2);
    }

    private void eat() {
        System.out.println("Philosopher " + id + " is eating");
        sleep(EAT_TIME);
    }

    private void putDownForks() {
        availableForks.release(2);
    }

    private void waitBeforeNextAttempt() {
        sleep(WAIT_TIME);
    }

    private void sleep(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}