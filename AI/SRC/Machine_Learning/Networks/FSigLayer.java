package machine_learning.networks;

import machine_learning.neurons.*;

public class FSigLayer extends Layer {

  public FSigLayer (int index, int neuron_num, double learn_rate, int in_num, int length) {
    this.learn_rate = learn_rate;
    this.index = index;
    this.length = length;
    neurons = new Neuron[neuron_num];
    for (int s = 0; s < neuron_num; s++) {
      neurons[s] = new FSigmoid(in_num, s);
    }
  }

  public void train (double error, Layer next_layer) {}

  public void train (double[] error) {
    for (int i = 0; i < neurons.length; i++) {
      neurons[i].train(error[i], learn_rate);
    }
  }
}
