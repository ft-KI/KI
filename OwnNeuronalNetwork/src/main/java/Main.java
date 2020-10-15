import NeuronalNetwork.NeuronalNetwork;
import NeuronalNetwork.activationFunktions.Sigmoid;

public class Main {

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
        network.createOutputtNeurons(10);
        network.connectFullMeshed();
        network.setAllActivationfunktions(new Sigmoid());



        int digit=0;
        int number=0;
        int maxnumber=100;
        float epsilon=0.91f;
        while(true){
            network.reset();
            TrainData.loadDigit(digit,number);
            network.setInputValues(TrainData.getImageAsFloat());

            network.backpropagation(getCorrection(digit),epsilon);
            System.out.println("digit: "+digit+" number: "+number+" output: "+highestOutputNeuron());
            epsilon*=0.9999f;
            Thread.sleep(0);

            digit++;
            if(digit>9){
                digit=0;
                number++;
            }
            if(number>maxnumber){
                break;
            }

        }

    }
}
