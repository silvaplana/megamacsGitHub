package megamacs.messaging.immediate.server;

import javax.swing.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerImmediate {


    public ServerImmediate() throws IOException {


        NetworkObjectDispatcher networkObjectDispatcher = new NetworkObjectDispatcher(null);
        networkObjectDispatcher.onStart();
        NetworkReceiver networkReceiver = new NetworkReceiver(networkObjectDispatcher);
        networkReceiver.onStart();

        try {
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("FIN");




    }

    public static void main(String[] args) throws Exception {
        new ServerImmediate();
    }

}