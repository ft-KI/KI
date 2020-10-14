import NeuronalNetwork.Connection;
import NeuronalNetwork.NeuronalNetwork;
import NeuronalNetwork.activationFunktions.Sigmoid;
import NeuronalNetwork.neurons.InputNeuron;
import NeuronalNetwork.neurons.WorkingNeuron;

import java.util.ArrayList;

public class Main {

    public static NeuronalNetwork network=new NeuronalNetwork();
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Hallo");
        network.createInputNeurons(3);
        network.addHiddenLayer(30);
        network.createOutputtNeurons(1);
        network.setInputValues(8901,7348,9872);
        network.connectFullMeshed();
        network.setAllActivationfunktions(new Sigmoid());
        float epsilon=0.9f;
        while(true){
            network.reset();
            network.backpropagation(new float[]{0.789f},epsilon);
            System.out.println("output "+network.getOutputNeurons().get(0).getOutputValue());
            epsilon*=0.99f;
            Thread.sleep(1000);
        }

    }
}
