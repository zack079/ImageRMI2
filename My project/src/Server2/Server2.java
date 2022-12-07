package Server2;


import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server2 {

    public static void main(String[] args) throws RemoteException, AlreadyBoundException{
        FileInterface2 fileObject = new FileImp2();
        FileInterface2 skeleton=(FileInterface2) UnicastRemoteObject.exportObject(fileObject, 0);
        Registry registry = LocateRegistry.getRegistry("127.0.0.1",2023);
        registry.bind("myFile2", skeleton );
        System.out.println("Server2 is ready ....");
    }
}
