import java.util.Random;

public class Neuron {

    private double[] weights;
    private double[] weightsUpdate;
    double output;
    double sum;
    HiddenLayer prevLayer;
    Random r;

    public Neuron(HiddenLayer prevLayer, Random r) {
        this.prevLayer = prevLayer;
        this.r = r;

        if(prevLayer != null) {
            weights = new double[prevLayer.getNumNeuron() + 1];
            weightsUpdate = new double[prevLayer.getNumNeuron() + 1];
            initWeights();
        }
    }

   // store output so not calculated every time
   public double getOutput() {
        return output;
   }

   public void incrementWeight(int index) {
        weights[index] += NeuralNetwork.E;
   }

   public void decrementWeight(int index) {
        weights[index] -= NeuralNetwork.E;
   }

   public void sumNeuron() {
        sum = 0;
        for(int i = 0; i < weights.length - 1; i++)
            sum += prevLayer.getNeuronOutput(i) * weights[i];

        // Bias neuron addition
        sum += weights[weights.length - 1];

        output = sum;
   }

   public int getNumWeights() {
        return weights.length;
   }

   public void activateNeuron() {
       output = Math.tanh(sum);
   }

   public void setUpdateWeight(int index, double val) {
        weightsUpdate[index] = val;
   }

   public void applyWeightUpdates() {
        for(int i = 0; i < weightsUpdate.length; i++) {
            weights[i] += weightsUpdate[i];
        }
   }

   // Restrict initial values to (-1, 1)
   private void initWeights() {
        for(int i = 0; i < weights.length; i++) {
           double random = -1 + r.nextDouble() * 2;
           weights[i] = random;
        }
   }

}