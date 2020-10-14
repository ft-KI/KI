package NeuronalNetwork;

import NeuronalNetwork.neurons.Neuron;

public class Connection {
    private Neuron startNeuron;
    private float weight;
    public Connection(Neuron StartNeuron, float weight){
        this.startNeuron=StartNeuron;
        this.weight=weight;
    }
    public float getOutputValue(){
        return this.startNeuron.getOutputValue()*weight;
    }

    public float getWeight() {
        return weight;
    }

    public Neuron getStartNeuron() {
        return startNeuron;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }
    public void addWeight(float deltaweight){
        this.weight+=deltaweight;
    }

    public void setStartNeuron(Neuron startNeuron) {
        this.startNeuron = startNeuron;
    }
}
