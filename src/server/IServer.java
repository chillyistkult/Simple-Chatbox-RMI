package server;

/**
 * Created by benjaminschuch on 11.06.15.
 */

import java.rmi.Remote;
import java.rmi.RemoteException;

import client.IClient;

public interface IServer extends Remote{
    public void listClients(IClient client) throws RemoteException;
    public void enter(IClient client) throws RemoteException;
    public void removeClient(IClient client) throws RemoteException;
    public void broadcast(String message) throws RemoteException;
}
