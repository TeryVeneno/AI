package machine_learning.neurons;

import java.util.Random;
import java.util.Arrays;

public class NC {

  private static Random rand;
  private static long seed;

  static {
    seed = System.currentTimeMillis();
    rand = new Random(seed);
  }

  public static void set_seed (long s) {
    seed = s;
    rand = new Random(seed);
  }

  public static long get_seed () {
    return seed;
  }

  public static double rand_double () {
    return rand.nextDouble();
  }

  public static int rand_int (int n) {
    if (n <= 0) {
      return 0;
    }
    return rand.nextInt(n);
  }

  public static long rand_long (long n) {
    if (n <= 0L) {
      return 0L;
    }
    long r = rand.nextLong();
    long m = n - 1;
    if ((n & m) == 0L) {
        return r & m;
    }
    long u = r >>> 1;
    while (u + m - (r = u % n) < 0L) {
        u = rand.nextLong() >>> 1;
    }
    return r;
  }

  public static int rand_int (int a, int b) {
    if ((b <= a) || ((long) b - a >= Integer.MAX_VALUE)) {
      return 0;
    }
    return a + rand_int(b - a);
  }

  public static double rand_double (double a, double b) {
    if (!(a<b)) {
      return 0.0;
    }
    return a+rand_double()*(b-a);
  }

  public static double cross_entropy (int batch_size, double[] desired, double[] outputs) {
    double sum = 0;
    for (int s = 0; s < desired.length; s++) {
      sum += (desired[s]*Math.log(outputs[s])+(1-desired[s])*(1-outputs[s]));
    }
    return -sum/batch_size;
  }
}
