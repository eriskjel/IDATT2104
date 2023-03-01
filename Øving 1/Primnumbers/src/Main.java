import javax.sound.midi.Soundbank;
import java.sql.Time;
import java.util.Collection;
import java.util.Collections;
import java.util.Scanner;

public class Main {

    static Scanner scanner = new Scanner(System.in);
    static Thread[] threads;

    public static void main(String[] args) throws InterruptedException {

        //int[] lowerAndUpperBound = getLowerAndUpperBound();
        //int numThreads = getNumThreads();
        int numThreads = 12;
        threads = new Thread[numThreads];

        int[] lowerAndUpperBound = {1, 100_000_000};


        long startTime = System.currentTimeMillis();

        for (int i = 0; i < numThreads; i++) {
            MyRunnable myRunnable = new MyRunnable(i, lowerAndUpperBound[0], lowerAndUpperBound[1], numThreads);
            Thread thread = new Thread(myRunnable);
            threads[i] = thread;
        }
        startThreads();
        joinThreads();
        long endTime = System.currentTimeMillis();

        //System.out.println("Done");
        //Collections.sort(MyRunnable.primeNumbers);
        System.out.println("Prime numbers found: " + MyRunnable.primeNumbers);
        System.out.println("Time: " + (endTime - startTime) + " ms");
        System.out.println("Number of primes: " + MyRunnable.primeNumbers.size());
    }

    public static void startThreads() {
        for (Thread thread : threads) {
            thread.start();
        }
    }

    public static void joinThreads() throws InterruptedException {
        for (Thread thread : threads) {
            thread.join();
        }
    }

    private static int getNumThreads() {
        System.out.println("Enter number of threads: ");
        return scanner.nextInt();
    }


    private static int[] getLowerAndUpperBound() {
        System.out.println("Enter lower bound: ");
        int lowerBound = scanner.nextInt();
        System.out.println("Enter upper bound: ");
        int upperBound = scanner.nextInt();
        return new int[]{lowerBound, upperBound};
    }
}
