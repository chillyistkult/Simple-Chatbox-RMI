package client;

/**
 * Created by benjaminschuch on 09.06.15.
 */

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.SimpleDateFormat;
import java.util.Date;

import server.IServer;

public class Client extends UnicastRemoteObject implements IClient {

    private static final long serialVersionUID = 1L;
    private String name;
    private IServer server;
    private boolean isLogged = true;

    public Client(String name, String ip) throws RemoteException, MalformedURLException, NotBoundException{
        this.name = name;
        try{
            this.server = (IServer) Naming.lookup("rmi://"+ ip + "/server");
            server.enter(this);
        }
        catch (RemoteException e) {
            setLoginState(false);
            System.out.println("IP-Address not found!");
        }
    }

    @Override
    public void setLoginState(boolean isLogged) throws RemoteException {
        this.isLogged = isLogged;
    }

    @Override
    public boolean getLoginState() throws RemoteException{
        return this.isLogged;
    }

    @Override
    public void exit() throws RemoteException {
        this.server.removeClient(this);

    }

    @Override
    public void showMessage(String msg) throws RemoteException {
        System.out.println(msg);
    }

    @Override
    public void broadcast(String msg) throws RemoteException {
        this.server.broadcast(new SimpleDateFormat("HH:mm:ss").format(new Date()) + " " + this.getName() + ": " + msg.replace("\n", ""));
    }

    public void listClients() throws RemoteException{
        this.server.listClients(this);
    }
    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

}
