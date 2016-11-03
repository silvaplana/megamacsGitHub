package megamacs.messaging.transfer;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.Exception;import java.lang.String;import java.lang.System;import java.net.Socket;
import javax.swing.JFileChooser;

public class ClientTransfer {

    private Socket socket;
    private DataInputStream dis;
    private InputStream is;
    private File file;
    private JFileChooser jfc = new JFileChooser();

    public ClientTransfer() throws Exception {
        socket = new Socket("localhost", 1234);
        is = socket.getInputStream();
        dis = new DataInputStream(is);
        System.out.println("Client:Waiting for File");

        //File file = new File(dis.readUTF());
        System.out.println("Client:after readUTF=" + file);

        jfc.setSelectedFile(file);
        System.out.println("Client:after set selected file");
        //jfc.showSaveDialog(null);
        System.out.println("Client:after show save dialog");

        file = new File("C:\\Users\\sebastien\\Desktop\\DUM\\bull-macs-0.9.24-SNAPSHOT-bin.zip");

        //file = jfc.getSelectedFile();
        System.out.println("Client:after getSelected File=" + file);
        FileOutputStream fos = new FileOutputStream(file);
        int count = 0;
        byte[] b = new byte[1000];
        System.out.println("Incoming File");
        while((count = dis.read(b)) != -1){
            fos.write(b, 0, count);
        }

        fos.close();
        socket.close();
        System.out.println("File Transfer Completed Successfully!");
    }

    public static void main(String[] args) throws Exception{
        new ClientTransfer();
    }

}