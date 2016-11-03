package megamacs.filetransfer;

import megamacs.Injector;

import javax.swing.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerTransferService {

    Injector i;

    private boolean start;

    private ServerSocket sSocket;
    private Socket socket;
    private DataOutputStream dos;
    private OutputStream os;
    // source file to transfer
    private File file;

    public ServerTransferService(Injector i) throws IOException {

        this.i = i;

        sSocket = new ServerSocket(1234);
        System.out.println("Waiting for incoming connection request...");

        start = true;
        Thread thread = new Thread(new JobReceivedPacketTransfer(sSocket));
        thread.start();
    }

    // set source file to transfer
    public void setSourceFile(File file){
        System.out.println("Set source file=" + file.toString());
        this.file = file;
    }

    private class JobReceivedPacketTransfer implements Runnable {
        private final ServerSocket serverSocket;

        public JobReceivedPacketTransfer(final ServerSocket serverSocket) {
            this.serverSocket = serverSocket;
        }

        @Override
        public void run() {
            while (start) {

                System.out.println("Server: after start");
                try {
                    socket = serverSocket.accept();
                    System.out.println("Server: after accept");

                System.out.println("Server: after show open dialog");
                System.out.println("Server: after getSelected file: file" + file);
                FileInputStream fis = new FileInputStream(file);
                os = socket.getOutputStream();
                dos = new DataOutputStream(os);
               //dos.writeUTF(file.getName());
                    System.out.println("Server: after write UTF" + file.getName());
                int count = 0;
                byte[] b = new byte[1000];
                System.out.println("Uploading File...");
                while ((count = fis.read(b)) != -1) {
                    dos.write(b, 0, count);
                }

                fis.close();
                socket.close();
                System.out.println("File Transfer Completed Successfully!");

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}