package Server2;

import Server1.FileInterface1;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.rmi.RemoteException;

public class FileImp2 implements FileInterface2 {

    private byte [] serializedFile;


    @Override
    public void uploadFile(byte [] serializedFile) throws RemoteException {
        this.serializedFile=serializedFile;
    }



    @Override
    public byte [] downloadFile() throws RemoteException {
        return serializedFile;
    }

    public void RedFilter() throws IOException,RemoteException {
        InputStream is = new ByteArrayInputStream(serializedFile);
        BufferedImage img = ImageIO.read(is);


        for(int i=0 ; i < img.getHeight() ; i++){
            for(int j=0 ; j < img.getWidth() ; j++){

                Color c = new Color(img.getRGB(j,i));

                int red = 255 - c.getRed();
                //int green = 255 - c.getGreen();
                //int blue = 255 - c.getBlue();

                Color newColor = new Color(red,0,0);
                img.setRGB(j,i,newColor.getRGB());
            }
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(img, "png", baos);
        serializedFile = baos.toByteArray();


    }



/*
    public void exit() throws RemoteException
    {
        try{
            // Unregister ourself
            Naming.unbind(mServerName);

            // Unexport; this will also remove us from the RMI runtime
            UnicastRemoteObject.unexportObject(this, true);

            System.out.println("CalculatorServer exiting.");
        }
        catch(Exception e){}
    }*/
}
