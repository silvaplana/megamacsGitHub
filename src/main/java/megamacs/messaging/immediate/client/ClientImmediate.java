package megamacs.messaging.immediate.client;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.File;
import java.io.InputStream;
import java.net.Socket;

public class ClientImmediate {

    private Socket socket;
    private DataInputStream dis;
    private InputStream is;
    private File file;
    private JFileChooser jfc = new JFileChooser();

    public ClientImmediate() throws Exception {

        NetworkSender networkSender = new NetworkSender();
        networkSender.onStart();

       // NetworkSlot ns = new NetworkSlot();
        //ns.slotIndex = 34;

        //networkSender.sendAsync(ns);


        Thread.sleep(100000);

        System.out.println("FIN");
    }

    public static void main(String[] args) throws Exception{
        new ClientImmediate();
    }

}