package Server1;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.rmi.RemoteException;

public class FileImp1 implements FileInterface1 {

    private byte [] serializedFile;


    @Override
    public void uploadFile(byte [] serializedFile) throws RemoteException {
        this.serializedFile=serializedFile;
    }



    @Override
    public byte [] downloadFile() throws RemoteException {
        return serializedFile;
    }

    public void GrayScale() throws IOException,RemoteException {
        InputStream is = new ByteArrayInputStream(serializedFile);
        BufferedImage img = ImageIO.read(is);

        //get image width and height
        int width = img.getWidth();
        int height = img.getHeight();

        //convert to grayscale
        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                int p = img.getRGB(x,y);

                int a = (p>>24)&0xff;
                int r = (p>>16)&0xff;
                int g = (p>>8)&0xff;
                int b = p&0xff;

                //calculate average
                int avg = (r+g+b)/3;

                //replace RGB value with avg
                p = (a<<24) | (avg<<16) | (avg<<8) | avg;

                img.setRGB(x, y, p);
            }
        }

        //write image

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
