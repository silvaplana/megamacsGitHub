/*
* MACS: Modular & Adaptive Communication Server.
* Copyright (c) 2014 Bull SAS.
* All rights reserved.
*/
package megamacs.messaging.immediate.server;

import megamacs.messaging.immediate.messages.Message;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.net.*;
import java.util.Enumeration;

/**
 * @author Bull SAS
 */
public class NetworkReceiver {



    private String multicastAddress = "228.2.2.2";

    //private String multicastAddress = "192.168.1.20";

    private int multicastSocketPort = 6000;

    private boolean start;
    private static final int LENGTH = 4096;
    private MulticastSocket socketMulticast;
    private InetSocketAddress multicastAdd;



    private NetworkObjectDispatcher dispatcher;


    public NetworkReceiver(NetworkObjectDispatcher dispatcher){
        this.dispatcher = dispatcher;
    }


    public void onStart() {
        try {
            System.out.println("Start network broadcaster");
            multicastAdd = new InetSocketAddress(multicastAddress, multicastSocketPort);
            socketMulticast = new MulticastSocket(multicastSocketPort);

            Enumeration<NetworkInterface> ifs = NetworkInterface.getNetworkInterfaces();
            while (ifs.hasMoreElements()) {
                NetworkInterface xface = ifs.nextElement();
                if (xface.getInetAddresses().hasMoreElements()) {
                    try {
                        System.out.println("Try to join interface " +xface.getName());
                        socketMulticast.joinGroup(multicastAdd, xface);
                    } catch (SocketException e) {
                        // Do nothing
                        System.out.println("Unable to join group for interface " + xface.getName() + " ,exception=" + e);
                    }
                }
            }

            start = true;
            Thread thread = new Thread(new JobReceivedPacket(socketMulticast));
            thread.start();
        } catch (UnknownHostException e) {
            //logger.error("UnknownHostException", e);
        } catch (IOException e) {
            //logger.error("IOException", e);
        }
    }

    private class JobReceivedPacket implements Runnable {

        private final DatagramPacket packet;
        private final DatagramSocket socket;

        public JobReceivedPacket(final DatagramSocket datagramSocket) {
            socket = datagramSocket;
            byte[] tab = new byte[LENGTH];
            packet = new DatagramPacket(tab, LENGTH);
        }

        @Override
        public void run() {
            while (start) {
                try {
                 System.out.println("Wait for data on network");
                socket.receive(packet);
                    System.out.println("Received from network a Packet try to decode");
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(packet.getData());
                ObjectInput input = new ObjectInputStream(byteArrayInputStream);
                    Object obj1 = input.readObject();
                    Message obj = (Message) obj1;
                    System.out.println("====================>Received from network an object: " + obj.getClass().getSimpleName());
                    dispatcher.dispatch(obj);
                } catch (IOException | NoStrategyException | ClassNotFoundException e) {
                    System.out.println( "Exception while decoding incoming packet" + e);
                }
            }
        }
    }

}
