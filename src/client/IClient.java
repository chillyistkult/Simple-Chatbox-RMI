package client;

/**
 * Created by benjaminschuch on 11.06.15.
 */

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IClient extends Remote{
    public void exit() throws RemoteException;
    public void setLoginState(boolean validacaoLogin) throws RemoteException;
    public boolean getLoginState() throws RemoteException;
    public String getName() throws RemoteException;
    public void setName(String nome) throws RemoteException;
    public void showMessage(String msg) throws RemoteException;
    public void broadcast(String msg) throws RemoteException;

}