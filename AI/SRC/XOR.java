package machine_learning;

import machine_learning.networks.SNetwork;
import java.util.Arrays;

public class XOR {

  public static void main(String[] args) {
    double[][] input = new double[4][2];
    double[][] desire = new double[4][1];
    input[0][0] = 0;
    input[0][1] = 0;
    input[1][0] = 1;
    input[1][1] = 0;
    input[2][0] = 0;
    input[2][1] = 1;
    input[3][0] = 1;
    input[3][1] = 1;

    desire[0][0] = 0;
    desire[1][0] = 1;
    desire[2][0] = 1;
    desire[3][0] = 0;

    SNetwork network = new SNetwork(10, 10, 0.04, desire[0], 1, 8);
    //network.practice(input, 2000);
    for (int s = 0; s < 2000; s++) {
      for (int i = 0; i < 4; i++) {
        network.change_desire(desire[i]);
        network.practice(input[i]);
      }
    }
    System.out.println(Arrays.toString(network.output(input[0])));
    System.out.println(Arrays.toString(network.output(input[1])));
    System.out.println(Arrays.toString(network.output(input[2])));
    System.out.println(Arrays.toString(network.output(input[3])));
  }
}
