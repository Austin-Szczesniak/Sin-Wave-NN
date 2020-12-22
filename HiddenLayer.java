import java.util.ArrayList;
import java.util.Random;

public class HiddenLayer {

    private ArrayList<Neuron> neuronList;
    Random r;

    public HiddenLayer(int numNeurons, HiddenLayer prevLayer, Random r) {
        neuronList = new ArrayList<>();
        this.r = r;

        for(int i = 0; i < numNeurons; i++) {
            Neuron neuron = new Neuron(prevLayer, r);
            neuronList.add(neuron);
        }
    }

    public HiddenLayer(ArrayList<InputNeuron> inputNeurons) {
        neuronList = new ArrayList<>();
        neuronList.addAll(inputNeurons);
    }

    public void sumAndActivateNeurons() {
        neuronList.parallelStream().forEach(Neuron::sumNeuron);
        neuronList.parallelStream().forEach(Neuron::activateNeuron);
    }

    public void sumNeurons() {
        neuronList.parallelStream().forEach(Neuron::sumNeuron);
    }

    public void applyWeightUpdates() {
        for(Neuron neuron: neuronList) {
            neuron.applyWeightUpdates();
        }
    }

    public Neuron getNeuron(int index) {
        return neuronList.get(index);
    }

    public int getNumNeuron() {
        return neuronList.size();
    }

    public double getNeuronOutput(int index) {
        return neuronList.get(index).getOutput();
    }
}
