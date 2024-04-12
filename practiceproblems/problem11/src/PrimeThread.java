public class PrimeThread extends Thread {
    private int start;
    private int end;
    private int result;

    /**
     * Constructor for PrimeThread initializes the start and end int
     * 
     * @param start int - the beggining of the numbers (inclusive)
     * @param end int - the end of the numbers (exclusive)
     */
    PrimeThread(int start, int end) {
        this.start = start;
        this.end = end;
        result = 0;
    }

    /**
     * Calculates if the the numbers from starting (inclusive) to the end (exclusive) are prime. If the number is prime
     * it adds 1 to the result.
     */
    @Override
    public void run() {
        for (int i = start; i < end; ++i) {
            if (isPrime(i)) {
                ++result;
            }
        }
    }

    /**
     * Gets the result or amount of how many numbers were prime from the start (inclusive) and end (exclusive).
     * 
     * @return int - result
     */
    public int getResult() {
        return result;
    }

    /**
     * Calculates whether a given int is a prime number or not
     * 
     * @param n int - the number that is being tested if it's prime
     * @return true if n is prime, false otherwise
     */
    public static boolean isPrime(int n) {
        if (n <= 1) return false;

        if (n <= 3) return true;
        
        if (n % 2 == 0 || n % 3 == 0) return false;

        for (int i=5; i*i <= n; i+=6)
            if (n % i == 0 || n % (i+2) == 0)
                return false;

        return true;
    }
}
