package Client;

import Server1.FileInterface1;
import Server2.FileInterface2;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

public class Client {
    private static List<byte[]> serializedFiles=new ArrayList<>();

    public static void DisplayImage(String path) throws IOException
    {
        BufferedImage img= ImageIO.read(new File(path));
        ImageIcon icon=new ImageIcon(img);
        JFrame frame=new JFrame();
        frame.setLayout(new FlowLayout());
        frame.setSize(500,500);
        JLabel lbl=new JLabel();
        lbl.setIcon(icon);
        frame.add(lbl);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    public static void output() throws IOException {
        int i=1;
        for(byte [] serializedFile : serializedFiles){
            ByteArrayInputStream inStreambj = new ByteArrayInputStream(serializedFile);

            // read image from byte array
            BufferedImage newImage = ImageIO.read(inStreambj);

            // write output image
            ImageIO.write(newImage, "jpg", new File("outputImage"+i+".jpg"));
            i++;
        }

    }



    public static void main(String[] args) {

        try {

            Registry registry1 = LocateRegistry.getRegistry("127.0.0.1",2022);
            FileInterface1 stub1 = (FileInterface1) registry1.lookup("myFile1");

            Registry registry2 = LocateRegistry.getRegistry("127.0.0.1",2023);
            FileInterface2 stub2 = (FileInterface2) registry2.lookup("myFile2");

            Socket socket = new Socket("localhost",3030);
            InputStream inputStream=socket.getInputStream();
            OutputStream outputStream=socket.getOutputStream();

            File file = new File("Client/image.png");
            if(file.exists()){
                    //serialisation of the file
                    FileInputStream fileInputStream=new FileInputStream(file);
                    byte [] contentSerialized=new byte[(int)file.length()];
                    fileInputStream.read(contentSerialized,0,(int)file.length());

                    stub1.uploadFile(contentSerialized);
                    stub2.uploadFile(contentSerialized);

                    DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
                    dataOutputStream.writeInt(contentSerialized.length);
                    dataOutputStream.write(contentSerialized);


                    stub1.GrayScale();
                    stub2.RedFilter();

                    DataInputStream dataInputStream=new DataInputStream(inputStream);
                    int fileLength = dataInputStream.readInt();

                    byte [] serializedFile1=stub1.downloadFile();
                    byte [] serializedFile2=stub2.downloadFile();
                    byte [] serializedFile3=new byte[fileLength];
                    dataInputStream.readFully(serializedFile3,0,fileLength);

                    serializedFiles.add(serializedFile1);
                    serializedFiles.add(serializedFile2);

                    serializedFiles.add(serializedFile3);

                    output();

                    for(int i=1;i<=serializedFiles.size();i++)
                        DisplayImage("outputImage"+i+".jpg");

            }else{
                System.out.println("file doesn't exist!");
            }
        } catch (Exception e) {
            System.err.println("Client.Client exception: " + e.toString());
            e.printStackTrace();
        }

    }
}
