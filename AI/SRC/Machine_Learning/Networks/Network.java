package machine_learning.networks;

import machine_learning.neurons.NC;

abstract class Network {

  protected Layer[] layers;
  protected double learn_rate;
  protected double cost;
  protected double error;
  protected double[] outputs;
  protected double[] input;
  protected double[] desired;
  protected int total_out;

  public Layer[] get_layers () {
    return layers;
  }

  public double get_lr () {
    return learn_rate;
  }

  public double get_cost () {
    return NC.cross_entropy(total_out, desired, outputs);
  }

  public double get_total_out () {
    return total_out;
  }

  public double[] get_outputs () {
    return outputs;
  }

  public double[] get_inputs () {
    return input;
  }

  public double[] get_desired () {
    return desired;
  }

  public double get_error () {
    return error;
  }

  public Layer get_layer (int index) {
    return layers[index];
  }

  public abstract void train ();

  public abstract double[] output (double[] input);

}
