package Server1;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface FileInterface1 extends Remote {
    public void uploadFile(byte [] content) throws  RemoteException;

    public void GrayScale() throws IOException,RemoteException;
    public byte [] downloadFile() throws RemoteException;
    //public void exit() throws RemoteException;

}
