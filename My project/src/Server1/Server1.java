package Server1;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server1 {

    public static void main(String[] args) throws RemoteException, AlreadyBoundException{
        FileInterface1 fileObject = new FileImp1();
        FileInterface1 skeleton=(FileInterface1) UnicastRemoteObject.exportObject(fileObject, 0);
        Registry registry = LocateRegistry.getRegistry("127.0.0.1",2022);
        registry.bind("myFile1", skeleton );
        System.out.println("Server1 is ready ....");
    }
}
