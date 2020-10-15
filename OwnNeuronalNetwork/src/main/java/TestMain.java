import NeuronalNetwork.NeuronalNetwork;
import NeuronalNetwork.activationFunktions.Sigmoid;

public class TestMain {
    public static NeuronalNetwork network=new NeuronalNetwork();
    public static void main(String[] args) {
        network.createInputNeurons(50*50);
        network.addHiddenLayer(100);
        network.createOutputtNeurons(10);
        network.connectFullMeshed(0.5f);
        network.setAllActivationfunktions(new Sigmoid());
        //network.setInputValues(1,2,3);
        while(true){
            network.reset();
            System.out.println(network.getOutputNeurons().get(1).getOutputValue());
            network.backpropagation(new float[] {0.45f,0.45f,0,0,0,0,0,0,0,0},0.9f);
        }

    }
}
