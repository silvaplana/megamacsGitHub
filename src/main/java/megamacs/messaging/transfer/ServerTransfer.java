package megamacs.messaging.transfer;

import javax.swing.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerTransfer {

    private boolean start;

    private ServerSocket sSocket;
    private Socket socket;
    private DataOutputStream dos;
    private OutputStream os;
    private File file;
    private JFileChooser jfc = new JFileChooser();

    public ServerTransfer() throws IOException {

        sSocket = new ServerSocket(1234);
        System.out.println("Waiting for incoming connection request...");

        start = true;
        Thread thread = new Thread(new JobReceivedPacketTransfer(sSocket));
        thread.start();


        ////////////////////////////////////////////////////////

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

                //jfc.showOpenDialog(null);
                System.out.println("Server: after show open dialog");
                //file = jfc.getSelectedFile();
                file = new File("C:\\Users\\sebastien\\Desktop\\SEB\\testSeb\\bull-macs-0.9.24-SNAPSHOT-bin.zip");
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









    public static void main(String[] args) throws Exception {
        new ServerTransfer();
    }

}