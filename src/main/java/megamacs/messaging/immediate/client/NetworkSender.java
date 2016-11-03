package megamacs.messaging.immediate.client;
/*
* MACS: Modular & Adaptive Communication Server.
* Copyright (c) 2014 Bull SAS.
* All rights reserved.
*/

import megamacs.messaging.immediate.messages.Message;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;


public class NetworkSender {




    private String multicastAddress = "228.2.2.2";

    private int multicastSocketPort = 6000;

    private int multicastTTL = 5;

    public String agentName = "Agent";

    private List<String> listInterfaces = new ArrayList<String>();
    private boolean start = false;
    private static final int LENGTH = 1024;
    private MulticastSocket socketMulticast;
    private InetAddress multicastAdd;


    public void onStart() {
        try {
            System.out.println("Start network sender");
            multicastAdd = InetAddress.getByName(multicastAddress);
            socketMulticast = new MulticastSocket(multicastSocketPort);
            socketMulticast.joinGroup(multicastAdd);
            socketMulticast.setTimeToLive(multicastTTL);
            start = true;
        } catch (UnknownHostException e) {
            System.out.println("UnknownHostException "+ e);
        } catch (IOException e) {
            System.out.println("IOException" + e);
        }
    }

    //@Async
    public void sendAsync(Message obj) throws IOException {
        obj.agentName = agentName;
        System.out.println("Try to send NetworkOject "+ obj.toString());
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutput output = new ObjectOutputStream(byteArrayOutputStream);
        output.writeObject(obj);
        output.close();
        byte[] bytes = byteArrayOutputStream.toByteArray();
        DatagramPacket packet = new DatagramPacket(bytes, bytes.length, multicastAdd, multicastSocketPort);
        socketMulticast.send(packet);
    }

    private boolean isAuthorizeInterface(String interfaceName) {
        if (listInterfaces.isEmpty()) {
            //All network interfaces are authorized
            return true;
        } else {
            return listInterfaces.contains(interfaceName);
        }
    }
}


