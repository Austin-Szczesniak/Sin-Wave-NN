import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class NeuralNetwork {
    private ArrayList<HiddenLayer> layers;
    private ArrayList<InputNeuron> inputs;
    public static final double E = 1E-5;
    private static final double LEARNING_RATE = .01;

    public NeuralNetwork(int[] layerSizes, long seed) {
        Random r = new Random(seed);
        layers = new ArrayList<>();
        inputs = new ArrayList<>();

        for(int i = 0; i < layerSizes[0]; i++)
            inputs.add(new InputNeuron(r));

        HiddenLayer inputLayer = new HiddenLayer(inputs);
        layers.add(inputLayer);

        for (int i = 1; i < layerSizes.length; i++) {
            HiddenLayer layer = new HiddenLayer(layerSizes[i], layers.get(layers.size() - 1), r);
            layers.add(layer);
        }
    }

    public NeuralNetwork(int[] layerSizes) {
        this(layerSizes, (long) (Math.random() * Long.MAX_VALUE));
    }

    // Forward prop...
    public void setInput(int neuronIndex, double input) {
        inputs.get(neuronIndex).setOutput(input);
    }

    public double getOutput(int neuronIndex) {
        return layers.get(layers.size() - 1).getNeuronOutput(neuronIndex);
    }

    public void evaluate() {
        for(int i = 1; i < layers.size() - 1; i++)
            layers.get(i).sumAndActivateNeurons();

        layers.get(layers.size() - 1).sumNeurons();
    }

    // Back Prop
    public void train(ArrayList<ArrayList<Double>> inputsBatch, ArrayList<ArrayList<Double>> expectedOutputsBatch, boolean printDebug) {
        for(int l = 1; l < layers.size(); l++) {
            for(int n = 0; n < layers.get(l).getNumNeuron(); n++) {
                for(int w = 0; w < layers.get(l).getNeuron(n).getNumWeights(); w++) {
                    calcWeightUpdate(inputsBatch, expectedOutputsBatch, l, n, w);
                }
            }
        }

        for(int l = 1; l < layers.size(); l++) {
            layers.get(l).applyWeightUpdates();
        }

        if(printDebug)
            System.out.println("Error Value: " + getErrorBatch(inputsBatch, expectedOutputsBatch));
    }

    private void calcWeightUpdate(ArrayList<ArrayList<Double>> inputsBatch, ArrayList<ArrayList<Double>> expectedOutputsBatch,
                                  int layer, int neuron, int weightNum)
    {
        double prevError = getErrorBatch(inputsBatch, expectedOutputsBatch);
        layers.get(layer).getNeuron(neuron).incrementWeight(weightNum);
        double newError = getErrorBatch(inputsBatch, expectedOutputsBatch);
        layers.get(layer).getNeuron(neuron).decrementWeight(weightNum);

        double weightUpdateVal = (-(newError - prevError) / E) * LEARNING_RATE;
        layers.get(layer).getNeuron(neuron).setUpdateWeight(weightNum, weightUpdateVal);
    }

    private double getErrorBatch(ArrayList<ArrayList<Double>> inputsBatch, ArrayList<ArrayList<Double>> expectedOutputsBatch) {
        double errorBatch = 0;
        for(int i = 0; i < inputsBatch.size(); i++) {
            errorBatch += getError(inputsBatch.get(i), expectedOutputsBatch.get(i));
        }

        return errorBatch / inputsBatch.size();
    }

    private double getError(ArrayList<Double> inputs, ArrayList<Double> expectedOutputs) {
        double sum = 0;

        for(int i = 0; i < inputs.size(); i++) {
            setInput(i, inputs.get(i));
        }

        evaluate();

        for(int i = 0; i < expectedOutputs.size(); i++) {
            sum += Math.pow(getOutput(i) - expectedOutputs.get(i), 2);
        }

        return sum;
    }
}
