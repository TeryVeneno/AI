package machine_learning.networks;

import machine_learning.neurons.Neuron;

public abstract class Layer {

  Neuron[] neurons;
  protected double learn_rate;
  protected int index;
  protected int length;
  protected double[] output;
  protected double[] inputs;

  public double[] output (double[] input) {
    double[] ret = new double[neurons.length];
    for (int n = 0; n < neurons.length; n++) {
      ret[n] = neurons[n].output(input);
    }
    inputs = input;
    output = ret;
    return output;
  }

  public abstract void train (double error, Layer next_layer);
  public abstract void train (double[] error);

  public Neuron[] get_neurons () {
    return neurons;
  }

  public Neuron get_neuron (int index) {
    return neurons[index];
  }

  public double get_lr () {
    return learn_rate;
  }

  public int get_index () {
    return index;
  }

  public double[] get_in () {
    return inputs;
  }

  public double[] get_output () {
    return output;
  }

  public int get_length () {
    return length;
  }
}
