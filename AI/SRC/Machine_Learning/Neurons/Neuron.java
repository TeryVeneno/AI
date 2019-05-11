package machine_learning.neurons;

public abstract class Neuron {
  protected double[] weights;
  protected double[] inputs;
  protected double output;
  protected double bias;
  protected int index;

  public double output (double[] input) {
    inputs = new double[input.length+1];
    for (int i = 0; i < input.length; i++) {
      inputs[i] = input[i];
    }
    inputs[inputs.length-1] = 1;
    for (int s = 0; s < inputs.length; s++) {
      output += weights[s]*inputs[s];
    }
    output = act_function(output+bias);
    return output;
  }
  public abstract double act_function (double val);
  public abstract void train (double error, double learn_rate);

  public double[] get_inputs () {
    return inputs;
  }

  public double[] get_weights () {
    return weights;
  }

  public double get_output () {
    return output;
  }

  /*public double get_bias () {
    return bias;
  }*/

  public double get_weight (int index) {
    return weights[index];
  }

  public double get_input (int index) {
    return inputs[index];
  }

  public int get_index () {
    return index;
  }
}
