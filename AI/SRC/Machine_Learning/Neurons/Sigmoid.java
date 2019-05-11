package machine_learning.neurons;

import java.util.Random;

public class Sigmoid extends Neuron {

  private int num_output;

  public Sigmoid (int num, int index) {
    weights = new double[num+1];
    for (int s = 0; s < weights.length; s++) {
      weights[s] = NC.rand_double();
    }
    bias = 0;
    output = 0;
    this.index = index;
  }

  public double act_function (double val) {
    return 1.0/(1.0+Math.exp(-val));
  }

  public void train (double error, double learn_rate) {
    for (int s = 0; s < weights.length; s++) {
      weights[s] = weights[s] + learn_rate*error*inputs[s]*output*(1.0-output);
    }
    bias += learn_rate*output*error;
  }
}
