import java.math.BigInteger;
import java.util.Vector;

public class MyRunnable implements Runnable {

    private final int threadNumber;
    private final int numThreads;
    private final int lowerBound;
    private final int upperBound;
    final static Vector<Integer> primeNumbers = new Vector<>();


    public MyRunnable(int n, int lowerBound, int upperBound, int numThreads) {
        this.threadNumber = n;
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.numThreads = numThreads;
    }

    private boolean isPrime2(int i) {
        if (i != 2) {
            if (i < 2) {
                return false;
            } else {
                for (int j = 2; j <= i / 2; j++) {
                    if (i % j == 0) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean isPrime(int n){
        if (n == 1) {
            return false;
        }
        if (n <= 3) {
            return true;
        }
        if ((n & 1) == 0) {
            return false;
        }
        int root = (int) Math.sqrt(n);
        for (int i = 3; i <= root; i += 2) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }


    @Override
    public void run() {
        for (int i = threadNumber + lowerBound; i <= upperBound; i+= numThreads) {
            //System.out.println("Thread " + threadNumber + " checking " + i);



            /*
            BigInteger bigInteger = new BigInteger(String.valueOf(i));
            if (bigInteger.isProbablePrime(1)) {
                primeNumbers.add(i);
            }

             */



            if (isPrime(i)) {
                //System.out.println("Thread " + threadNumber + " found prime " + i);
                primeNumbers.add(i);
            }
        }
    }
}