import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TrainData {
    public static BufferedImage image;
    public static int imageWidth=50;
    public static int imageHeight=50;



    public static int getRed(int x, int y){

        int rgb = image.getRGB(x, y);
        int red = (rgb >> 16) & 0xFF;
        int green = (rgb >> 8) & 0xFF;
        int blue = rgb & 0xFF;
        Color c = new Color(red,green,blue,1);

        return red;
    }

    public static int getGreen(int x, int y){

        int rgb = image.getRGB(x, y);
        int red = (rgb >> 16) & 0xFF;
        int green = (rgb >> 8) & 0xFF;
        int blue = rgb & 0xFF;
        Color c = new Color(red,green,blue,1);

        return green;
    }

    public static int getBlue(int x, int y){

        int rgb = image.getRGB(x, y);
        int red = (rgb >> 16) & 0xFF;
        int green = (rgb >> 8) & 0xFF;
        int blue = rgb & 0xFF;
        Color c = new Color(red,green,blue,1);

        return blue;
    }

    public static float luminance(int x,int y){
        float luminanz=(int) (0.229*getRed(x,y)+0.587*getGreen(x,y)+0.114*getBlue(x,y));
        return luminanz/255f;
    }

    public static String getnumberstring(int number){
        String output="";
        if(number < 10){
            output="000000"+number;
        }
        if(number < 100 && number >=10){
            output="00000"+number;
        }
        if(number < 1000 && number >=100){
            output="0000"+number;
        }
        if(number < 10000 && number >=1000){
            output="000"+number;
        }
        if(number < 100000 && number >=10000){
            output="00"+number;
        }
        if(number < 1000000 && number >=100000){
            output="0"+number;
        }
        if(number < 10000000 && number >=1000000){
            output=""+number;
        }

        return output;
    }
    public static void loadDigit(int digit, int number){
        readImage("E:\\Downloads\\numbers-master\\numbers-master\\UNCATEGORIZED\\"+digit+"\\number-"+getnumberstring(number)+".png");
    }

    public static void readImage(String s)
    {
        BufferedImage ergebnis = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB); // bzw. TYPE_INT_RGB falls du kein Alphakanal brauchst
        try{
            ergebnis.getGraphics().drawImage(ImageIO.read(new File(s)), 0,0, imageWidth, imageHeight, null);
            //image= ImageIO.read(new File(s));
        }catch(IOException e){
            e.printStackTrace();
        }

        image=ergebnis;
    }
    public static float[] getImageAsFloat(){
        float data[] = new float[imageWidth*imageHeight];
        for(int x=0;x<image.getWidth();x++){
            for(int y=0;y<image.getHeight();y++){
                data[x*y]=luminance(x,y);
            }
        }
        return data;
    }

}
