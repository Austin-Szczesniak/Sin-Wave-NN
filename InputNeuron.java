import java.util.Random;

public class InputNeuron extends Neuron {

    public InputNeuron(Random r) {
        super(null, r);
    }

    public void setOutput(double output) {
        this.output = output;
    }

    @Override
    public void activateNeuron() { }

    @Override
    public void sumNeuron() { }

    @Override
    public void setUpdateWeight(int index, double val) {
    }

    @Override
    public void applyWeightUpdates() {
    }
}
