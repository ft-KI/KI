import NeuronalNetwork.*;
import NeuronalNetwork.activationFunktions.Sigmoid;

public class Main {
    public static float epoch=0;
    public static float correct=0;
    public static NeuronalNetwork network=new NeuronalNetwork();

    public static float[] getCorrection(int digit){
        float data[] = {0,0,0,0,0,0,0,0,0,0};
        data[digit]=1;
        return data;
    }

    public static int highestOutputNeuron(){
        float max=0;
        int maxdigit=0;
        for(int i=0;i<10;i++){
            if(network.getOutputNeurons().get(i).getOutputValue() > max){
                max=network.getOutputNeurons().get(i).getOutputValue();
                maxdigit=i;
            }
        }


        return maxdigit;
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Hallo");
        network.createInputNeurons(TrainData.imageWidth* TrainData.imageHeight);
        network.addHiddenLayer(100);
        network.addHiddenLayer(100);
        network.addHiddenLayer(100);

        network.createOutputtNeurons(10);
        network.connectFullMeshed(0.5f);
        network.setAllActivationfunktions(new Sigmoid());



        int digit=0;
        int number=0;
        int maxnumber=40000;
        float epsilon=0.6f;
        while(true){
            network.reset();
            TrainData.loadDigit(digit,number);
            network.setInputValues(TrainData.getImageAsFloat());

            if(digit==highestOutputNeuron())  {
                System.out.println("\u001B[32m"+"digit: "+digit+" output: "+highestOutputNeuron()+" number: "+number+" gutigkeit: "+correct/epoch+"  "+network.getOutputNeurons().get(1).getOutputValue());

            }else{
                System.out.println("\u001B[31m"+"digit: "+digit+" output: "+highestOutputNeuron()+" number: "+number+" gutigkeit: "+correct/epoch+"  "+network.getOutputNeurons().get(1).getOutputValue());

            }


            // epsilon*=0.999f;
            Thread.sleep(0);

            if(highestOutputNeuron()==digit){
                correct++;
            }
            epoch++;

            digit++;
            if(digit>9){
                digit=0;
                number++;
            }
            if(number>maxnumber){
                break;
            }

            network.backpropagation(getCorrection(digit),epsilon);

        }

    }
}
