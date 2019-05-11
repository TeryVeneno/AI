package machine_learning.networks;

import machine_learning.neurons.NC;

public class SNetwork extends Network {

  public SNetwork (int l, int n, double lr, double[] desire, int total_out, int in_num) {
    layers = new Layer[l];
    learn_rate = lr;
    desired = desire;
    this.total_out = total_out;
    outputs = new double[total_out];
    error = 0;
    layers[l-1] = new FSigLayer(l-1, total_out, lr, n, n);
    layers[0] = new SigLayer(0, n, lr, in_num, n);
    for (int s = 1; s < l-1; s++) {
      layers[s] = new SigLayer(s, n, lr, n, n);
    }
    for (int s = 0; s < desire.length; s++) {
      outputs[s] = 0;
    }
  }

  public void train () {
    double[] error = new double[total_out];
    double t_error = 0;
    for (int s = 0; s < desired.length; s++) {
      error[s] = desired[s]-outputs[s];
      t_error += Math.pow(error[s], 2);
    }
    t_error/=2;
    layers[layers.length-1].train(error);
    for (int l = layers.length-2; l > 0; l--) {
      layers[l].train(t_error, layers[l+1]);
    }
  }

  public double[] output (double[] input) {
    double[] next = new double[layers[0].get_length()];
    next = layers[0].output(input);
    for (int l = 1; l < layers.length; l++) {
      next = layers[l].output(next);
    }
    outputs = next;
    return next;
  }

  public void practice (double[] input, int cycles) {
    for (int s = 0; s < cycles; s++) {
      output(input);
      train();
    }
  }

  public void practice (double[] input) {
    output(input);
    train();
  }

  public void change_desire (double desire[]) {
    desired = desire;
  }
}
