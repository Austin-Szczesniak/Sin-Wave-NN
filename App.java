import processing.core.PApplet;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class App extends PApplet {
    NeuralNetwork test;
    int runNums = -1;
    int epoch = 0;
    ArrayList<ArrayList<Double>> inputBatch = new ArrayList<>(), outputBatch = new ArrayList<>();

    @Override
    public void settings() {
        size(640, 480);
    }

    @Override
    public void setup() {
        background(60);

//        strokeWeight(2);
//        stroke(255, 0, 0);
//        noFill();
//
        int[] layerSizes = {1, 5, 5, 5, 1};
        test = new NeuralNetwork(layerSizes);
//        ArrayList<ArrayList<Double>> inputBatch = new ArrayList<>(), outputBatch = new ArrayList<>();

        for(int i = 0; i < 25; i++) {
            float length = map(i, 0, 25, 0, 1);
            inputBatch.add(new ArrayList<>());
            outputBatch.add(new ArrayList<>());
            inputBatch.get(i).add((double) length);
            outputBatch.get(i).add(customSin(length));
        }
    }

    @Override
    public void draw() {
        boolean flip = true;
        runNums++;
        System.out.println(runNums);
        if(flip) {
            flip = false;
            strokeWeight(2);
            stroke(255, 0, 0);
            noFill();

                background(60);

                test.train(inputBatch, outputBatch, false);
                epoch++;
                text("epoch: " + epoch, 15, 15);

                noFill();
                stroke(255,0,0);
                beginShape();
                for(float i = 0; i <= 1; i += 0.01) {
                    float xAxis = map(i, 0, 1, 0, width);
                    float y = sin(i * 2 * PI);
                    float yAxis = map(y, -1, 1, 0, height);

                    curveVertex(xAxis, yAxis);
                }
                endShape();

                for(double i = 0; i < 1; i += 0.1) {
                    test.setInput(0, i);
                    test.evaluate();
                    float xAxis1 = map((float)i, 0, 1, 0, width);
                    float y1 = (float) test.getOutput(0);
                    float yAxis1 = map(y1, -1, 1, 0, height);
                    strokeWeight(2);
                    stroke(255,255,255);
                    point(xAxis1, yAxis1);
                }
        }

    }

    private double customSin(float x) {
        return sin(x * 2 * PI);
    }
}
