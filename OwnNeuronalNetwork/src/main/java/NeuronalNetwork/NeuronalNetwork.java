package NeuronalNetwork;

import NeuronalNetwork.activationFunktions.ActivationFunktion;
import NeuronalNetwork.neurons.InputNeuron;
import NeuronalNetwork.neurons.WorkingNeuron;

import java.util.ArrayList;

public class NeuronalNetwork {
    private ArrayList<InputNeuron> inputNeurons=new ArrayList<>();
    private ArrayList<WorkingNeuron>hiddenNeurons=new ArrayList<>();
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
        for(int i=0;i<hiddenNeurons.size();i++){
            hiddenNeurons.get(i).deltaLearning(epsilon);
        }

    }
    public void reset(){
        for(WorkingNeuron wn:hiddenNeurons){
            wn.reset();
        }
        for(WorkingNeuron on:outputNeurons){
            on.reset();
        }
    }

    public void setAllActivationfunktions(ActivationFunktion af){
        for(WorkingNeuron n:hiddenNeurons){
            n.setActivationFunktion(af);
        }
        for(WorkingNeuron n:outputNeurons){
            n.setActivationFunktion(af);
        }
    }
    public void clearConnections(){
        for(WorkingNeuron n:hiddenNeurons){
            n.getInputConnections().clear();
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
    public void createHiddenNeurons(int n){
        for(int i=0;i<n;i++){
            hiddenNeurons.add(new WorkingNeuron());
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

    private void connectFullMeshed(boolean random,float initWeights) {
        if(hiddenNeurons.size()==0) {
            for (WorkingNeuron wn : outputNeurons) {
                for (InputNeuron in : inputNeurons) {
                    if(random) {
                        wn.addInputConnection(new Connection(in, (float) Math.random()));
                    }else{
                        wn.addInputConnection(new Connection(in, initWeights));

                    }
                }
            }
        }else{
            for (WorkingNeuron wn : outputNeurons) {
                for (WorkingNeuron hidden : hiddenNeurons) {
                    if(random) {
                        wn.addInputConnection(new Connection(hidden, (float) Math.random()));
                    }else{
                        wn.addInputConnection(new Connection(hidden, initWeights));

                    }
                }
            }
            for (WorkingNeuron hidden : hiddenNeurons) {
                for (InputNeuron in : inputNeurons) {
                    if(random) {
                        hidden.addInputConnection(new Connection(in, (float) Math.random()));
                    }else{
                        hidden.addInputConnection(new Connection(in, initWeights));

                    }
                }
            }

        }
    }

    public void connectFullMeshed() {
        connectFullMeshed(false,0.5f);
    }
    public void connectFullMeshed(float initWeights) {
        connectFullMeshed(false,initWeights);
    }

    public void connectRandomFullMeshed() {
        connectFullMeshed(true,-1);
    }


    public ArrayList<InputNeuron> getInputNeurons() {
        return inputNeurons;
    }

    public ArrayList<WorkingNeuron> getOutputNeurons() {
        return outputNeurons;
    }
}
