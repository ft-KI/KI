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
            }
            for(int a=1;a<hiddenNeurons.size();a++){
                for(int i=0;i<hiddenNeurons.get(a).size();i++){
                    hiddenNeurons.get(a).get(i).backpropagateSmallDelta();
                }
            }
        }

        for(int i=0;i<shoulds.length;i++){
            outputNeurons.get(i).deltaLearning(epsilon);
        }
        for(int i = hiddenNeurons.size() - 1; i >= 0; i--) {
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

            for(WorkingNeuron hidden : hiddenNeurons.get(0)) {
                for(InputNeuron in : inputNeurons) {
                    hidden.addInputConnection(new Connection(in, weights[index++]));
                }
            }

            for(int i=0;i<hiddenNeurons.size()-1;i++){
                for(int j=0;j<hiddenNeurons.get(i).size();j++){
                    for(int k=0;k<hiddenNeurons.get(i+1).size();k++){
                        hiddenNeurons.get(i+1).get(k).addInputConnection(new Connection(hiddenNeurons.get(i).get(j),weights[index++]));
                    }
                }
            }

            for(WorkingNeuron out : outputNeurons) {
                for(WorkingNeuron hidden : hiddenNeurons.get(hiddenNeurons.size()-1)) {
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
            for(WorkingNeuron hidden : hiddenNeurons.get(0)) {
                for(InputNeuron in : inputNeurons) {
                    if(random) {
                        hidden.addInputConnection(new Connection(in, (float) Math.random()));
                    }else{
                        hidden.addInputConnection(new Connection(in, initWeights));

                    }
                }
            }
/*
            for(int i=0;i<hiddenNeurons.size()-1;i++){
                for(int j=0;j<hiddenNeurons.get(i).size();j++){
                    for(int k=0;k<hiddenNeurons.get(i+1).size();k++){
                        if(random) {
                            hiddenNeurons.get(i + 1).get(k).addInputConnection(new Connection(hiddenNeurons.get(i).get(j), (float) Math.random()));
                        }else{
                            hiddenNeurons.get(i + 1).get(k).addInputConnection(new Connection(hiddenNeurons.get(i).get(j), initWeights));

                        }
                    }
                }
            }
            */

            for(int layer=1;layer<hiddenNeurons.size();layer++){
                for(int right=0;right<hiddenNeurons.get(layer).size();right++){
                    for(int left=0;left<hiddenNeurons.get(layer-1).size();left++){
                        if(random) {
                            hiddenNeurons.get(layer).get(right).addInputConnection(new Connection(hiddenNeurons.get(layer-1).get(left),(float) Math.random()));

                        }else{
                            hiddenNeurons.get(layer).get(right).addInputConnection(new Connection(hiddenNeurons.get(layer-1).get(left),initWeights));

                        }
                    }
                }
            }

            for(WorkingNeuron out : outputNeurons) {
                for(WorkingNeuron hidden : hiddenNeurons.get(hiddenNeurons.size()-1)) {
                    if(random) {
                        out.addInputConnection(new Connection(hidden, (float) Math.random()));
                    }else{
                        out.addInputConnection(new Connection(hidden, initWeights));

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
