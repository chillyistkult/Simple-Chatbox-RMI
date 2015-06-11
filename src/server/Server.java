package server;

/**
 * Created by benjaminschuch on 10.06.15.
 */

import client.IClient;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Server extends UnicastRemoteObject implements IServer {
	
	private static final long serialVersionUID = 1L;
	private ArrayList<IClient> clients;
	
	protected Server() throws RemoteException {
		this.clients = new ArrayList<IClient>();
	}

	@Override
	public void listClients(IClient client) throws RemoteException {
		String clientList = "";
		for (IClient c : clients) {
            clientList += " " + c.getName();
		}
		client.showMessage(clientList);
	}

	@Override
	public void enter(IClient client) throws RemoteException {
		
		if (clients.isEmpty()){
			client.setLoginState(true);
			clients.add(client);
		}
		
		else{
			for (IClient u : clients) {
				if(u.getName().equals(client.getName())){
					client.setLoginState(false);
					client.showMessage("Name already taken!");
					break;
				}
				else{
					client.setLoginState(true);
					clients.add(client);
                    this.broadcast(client.getName() + " entered the chat!");
					break;
				}
			}
		}
	}

	public ArrayList<IClient> getclients() {
		return clients;
	}
	
	@Override
	public void removeClient(IClient client) throws RemoteException {
		clients.remove(client);
        client.showMessage("Successfully logged out!");
        this.broadcast(client.getName() + " is gone!");
	}
	
	@Override
	public void broadcast(String message) throws RemoteException {
		for (IClient c : clients) {
			c.showMessage(message);
		}
	}


	public static void main(String[] args) {
		
		try {
			Server s = new Server();
			// Initialisieren des Registrierungsdienstes
			LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
			Naming.bind("server", s);
			System.out.println("Server runs...");

        } catch (RemoteException e) {
            System.out.println("Error: " + e.toString());
        } catch (MalformedURLException e) {
            System.out.println("Error: " + e.toString());
        } catch (AlreadyBoundException e) {
            System.out.println("Error: " + e.toString());
        }
		
	}
}