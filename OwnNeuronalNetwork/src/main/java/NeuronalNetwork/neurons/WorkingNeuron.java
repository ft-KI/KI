package NeuronalNetwork.neurons;

import NeuronalNetwork.Connection;
import NeuronalNetwork.activationFunktions.ActivationFunktion;
import NeuronalNetwork.activationFunktions.Identity;
import NeuronalNetwork.activationFunktions.Sigmoid;

import java.util.ArrayList;

public class WorkingNeuron implements Neuron{
    private ArrayList<Connection>inputConnections=new ArrayList<>();
    private ActivationFunktion activationFunktion=new Sigmoid();
    private float smallDelta=0;
    private float value=0;
    private boolean isvalueset=false;
    @Override
    public float getOutputValue() {
        if(!isvalueset) {
            float sum = 0;
            for (Connection c : inputConnections) {
                sum += c.getOutputValue();
            }
            value= activationFunktion.activation(sum);
            isvalueset=true;
        }
        return value;
    }
    public void addInputConnection(Connection ic){
        inputConnections.add(ic);
    }
    public void calcualteOutputDelta(float should){
        smallDelta=should-getOutputValue();
    }
    public void backpropagateSmallDelta(){
        for(Connection c:inputConnections){
            Neuron n= c.getStartNeuron();
            if(c.getStartNeuron() instanceof WorkingNeuron){
                WorkingNeuron wn = (WorkingNeuron)n;
               wn.smallDelta += this.smallDelta * c.getWeight();
            }

        }
    }
    public void reset(){
        smallDelta=0;
        isvalueset=false;
    }
    public void deltaLearning(float epsilon) {
        float bigDeltaFaktorbestandteil=activationFunktion.derivative(getOutputValue()) * epsilon * smallDelta;
        for(int i = 0; i<inputConnections.size(); i++) {
            float bigDelta = bigDeltaFaktorbestandteil * inputConnections.get(i).getStartNeuron().getOutputValue();
            inputConnections.get(i).addWeight(bigDelta);
        }
    }
    public ArrayList<Connection> getInputConnections() {
        return inputConnections;
    }

    public void setActivationFunktion(ActivationFunktion activationFunktion) {
        this.activationFunktion = activationFunktion;
    }
}
