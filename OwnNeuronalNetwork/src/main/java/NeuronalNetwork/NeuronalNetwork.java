package NeuronalNetwork;

import NeuronalNetwork.activationFunktions.ActivationFunktion;
import NeuronalNetwork.neurons.InputNeuron;
import NeuronalNetwork.neurons.WorkingNeuron;

import java.util.ArrayList;

public class NeuronalNetwork {
    private ArrayList<InputNeuron> inputNeurons=new ArrayList<>();
    private ArrayList<ArrayList<WorkingNeuron>>hiddenNeurons=new ArrayList<>();
    private ArrayList<WorkingNeuron>outputNeurons=new ArrayList<>();

    public NeuronalNetwork(){

    }

    public void backpropagation(float[] shoulds, float epsilon) {
        if(shoulds.length != outputNeurons.size()) {
            throw new IllegalArgumentException();
        }
        reset();

        for(int i=0;i<outputNeurons.size();i++){
            outputNeurons.get(i).calcualteOutputDelta(shoulds[i]);
        }

        if(hiddenNeurons.size()>0){
            for(int i=0;i<outputNeurons.size();i++){
                outputNeurons.get(i).backpropagateSmallDelta();
            }        }

        for(int i=0;i<shoulds.length;i++){
            outputNeurons.get(i).deltaLearning(epsilon);
        }
        for(int i=0;i<hiddenNeurons.size();i++) {
            for (int a = 0; a < hiddenNeurons.get(i).size(); a++) {
                hiddenNeurons.get(i).get(a).deltaLearning(epsilon);
            }
        }

    }
    public void reset(){
        for(ArrayList<WorkingNeuron> a:hiddenNeurons){
            for(WorkingNeuron wn:a) {
                wn.reset();
            }
        }
        for(WorkingNeuron on:outputNeurons){
            on.reset();
        }
    }

    public void setAllActivationfunktions(ActivationFunktion af){
        for(ArrayList<WorkingNeuron> a:hiddenNeurons){
            for(WorkingNeuron wn:a) {
                wn.setActivationFunktion(af);
            }
        }
        for(WorkingNeuron n:outputNeurons){
            n.setActivationFunktion(af);
        }
    }
    public void clearConnections(){
        for(ArrayList<WorkingNeuron> a:hiddenNeurons){
            for(WorkingNeuron wn:a) {
                wn.getInputConnections().clear();
            }
        }
        for(WorkingNeuron n:outputNeurons){
            n.getInputConnections().clear();
        }
    }
    public void clearNetwork(){
        inputNeurons.clear();
        hiddenNeurons.clear();
        outputNeurons.clear();
    }
    public void createInputNeurons(int n){
        for(int i=0;i<n;i++){
            inputNeurons.add(new InputNeuron());
        }
    }
    public void addHiddenLayer(int n){
        ArrayList<WorkingNeuron>hidden=new ArrayList<>();
        hiddenNeurons.add(hidden);
        for(int i=0;i<n;i++){
            hidden.add(new WorkingNeuron());
        }
    }
    public void createOutputtNeurons(int n){
        for(int i=0;i<n;i++){
            outputNeurons.add(new WorkingNeuron());
        }
    }
    public void setInputValues(float... v){
        if(v.length!=inputNeurons.size()){
            throw new IndexOutOfBoundsException();
        }
        for(int i=0;i<inputNeurons.size();i++){
            inputNeurons.get(i).setValue(v[i]);
        }
    }
    public void createFullMesh(float... weights) {
        if(hiddenNeurons.size()==0) {
            if(weights.length != inputNeurons.size() * outputNeurons.size()) {
                throw new RuntimeException();
            }

            int index = 0;



            for(WorkingNeuron wn : outputNeurons) {
                for(InputNeuron in : inputNeurons) {
                    wn.addInputConnection(new Connection(in, weights[index++]));
                }
            }
        }else{
            if(weights.length != inputNeurons.size() * hiddenNeurons.size() + hiddenNeurons.size() * outputNeurons.size()) {
                throw new RuntimeException();
            }

            int index = 0;

            for(WorkingNeuron hidden : hiddenNeurons) {
                for(InputNeuron in : inputNeurons) {
                    hidden.addInputConnection(new Connection(in, weights[index++]));
                }
            }

            for(WorkingNeuron out : outputNeurons) {
                for(WorkingNeuron hidden : hiddenNeurons) {
                    out.addInputConnection(new Connection(hidden, weights[index++]));
                }
            }
        }

    }

    public void connectFullMeshed() {
        if(hiddenNeurons.size()==0) {
            for (WorkingNeuron wn : outputNeurons) {
                for (InputNeuron in : inputNeurons) {
                    wn.addInputConnection(new Connection(in, 0.5f));
                }
            }
        }else{
            for (WorkingNeuron wn : outputNeurons) {
                for (WorkingNeuron hidden : hiddenNeurons) {
                    wn.addInputConnection(new Connection(hidden, 0.5f));
                }
            }
            for (WorkingNeuron hidden : hiddenNeurons) {
                for (InputNeuron in : inputNeurons) {
                    hidden.addInputConnection(new Connection(in, 0.5f));
                }
            }

        }
    }

    public ArrayList<InputNeuron> getInputNeurons() {
        return inputNeurons;
    }

    public ArrayList<WorkingNeuron> getOutputNeurons() {
        return outputNeurons;
    }
}
