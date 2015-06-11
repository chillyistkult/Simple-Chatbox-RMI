/**
 * Created by benjaminschuch on 10.06.15.
 */

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

import client.Client;

public class Main {
    private static Scanner console = new Scanner(System.in);
    ;
    private static Client client;
    private static String message;
    private static boolean connected = true;
    private static String name;

    public static void login() {
        String ip;

        try {
            do {
                System.out.println("Choose your name: ");
                name = console.nextLine().trim();
                System.out.println("IP-Address to connect: ");
                ip = console.nextLine().trim();

                client = new Client(name, ip);


            } while (!client.getLoginState());

        } catch (RemoteException e) {
            System.out.println("Error: " + e.toString());
        } catch (MalformedURLException e) {
            System.out.println("Error: " + e.toString());
        } catch (NotBoundException e) {
            System.out.println("Error: " + e.toString());
        }
    }

    public static void users() {
        System.out.println("Users in chatroom:");
        try {
            client.listClients();
        } catch (RemoteException e) {
            System.out.println("Error: " + e.toString());
        }
    }

    public static void menu() {
        // Kann nach belieben noch erweitert werden
        System.out.println("Messages:");
    }

    public static void messages() {
        message = console.nextLine();

        while (message.equals("")) {
            System.out.println();
            message = console.nextLine();
        }

    }

    public static void main(String[] args) throws RemoteException, MalformedURLException, NotBoundException {
        login();
        users();
        menu();
        while (connected) {
            messages();
            if (message.equals("/list")) {
                users();
                System.out.println();
                menu();
            } else if (message.equals("/exit")) {
                client.exit();
                connected = false;
            }
            try {
                client.broadcast(message);
            } catch (RemoteException e) {
                System.out.println("Error: " + e.toString());
            }

        }

    }

}