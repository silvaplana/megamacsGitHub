package megamacs.filetransfer;

import megamacs.compression.UnzipUtils;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.Socket;

public class ClientTransfer {

    private Socket socket;
    private DataInputStream dis;
    private InputStream is;
    // destination file
    private File file;

    public ClientTransfer(String serverIpAddress, File destFile) throws Exception {
        this.file = destFile;
        socket = new Socket(serverIpAddress, 1234);

        System.out.println("Initiatiate a client transfer for server ipAdress" + serverIpAddress + " destFile=" + destFile);

        is = socket.getInputStream();
        dis = new DataInputStream(is);
        System.out.println("Client:Waiting for File");

        //File file = new File(dis.readUTF());
        System.out.println("Client:after readUTF=" + file);
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
        System.out.println("File Transfer Completed Successfully !!!!!!!!!!!   destFile !" + destFile );
    }
}