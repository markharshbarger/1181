public class PrimeThread extends Thread {
    private int start;
    private int end;
    private int result;

    PrimeThread(int start, int end) {
        this.start = start;
        this.end = end;
        result = 0;
    }

    @Override
    public void run() {
        for (int i = start; i < end; ++i) {
            if (isPrime(i)) {
                ++result;
            }
        }
    }

    public int getResult() {
        return result;
    }

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
