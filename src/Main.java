import java.util.concurrent.Semaphore;


public class Main {
    public static void main(String[] args) {
        Semaphore forks = new Semaphore(5);

        Philosopher[] philosophers = new Philosopher[5];
        for (int i = 0; i < 5; i++) {
            philosophers[i] = new Philosopher(i, forks);
            new Thread(philosophers[i]).start();
        }
    }
}