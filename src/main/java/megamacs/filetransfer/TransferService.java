package megamacs.filetransfer;

import megamacs.Enums;
import megamacs.Injector;
import megamacs.conf.Host;
import megamacs.fileoperations.PathsService;
import megamacs.messaging.immediate.messages.TransferRequestMessage;
import megamacs.messaging.immediate.messages.TransferResponseMessage;
import megamacs.utils.Log;

import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by sebastien on 23/07/2016.
 */
public class TransferService {
    Injector i;

    Map<UUID, TransferOperation> transfers = new HashMap<>();

    TransferOperation currentTransfer = null;


    public TransferService(Injector injector) {
        i = injector;
    }


    /* ask a transfer
    @result if result != "" , transfer is not possible, the result string contains the reason
    * */
    public String initiateTransfer(String transferRecipientTrigram, Enums.TransferableFileElement element, Enums.FileDrive sourceDrive, Enums.FileDrive destDrive) {

        String validateRequest = validateRequestFromMMI(element, sourceDrive, destDrive);
        if (validateRequest != "") {
            return validateRequest;
        }
        TransferOperation transferSending = new TransferOperation(transferRecipientTrigram, element, sourceDrive, destDrive);
        transfers.put(transferSending.id, transferSending);
        currentTransfer = transferSending;

        return "";
    }

    public void onTransferRequestMessage(TransferRequestMessage message) {

        String validateMessage = validateRequestFromTransferRequestMessage(message);
        if (validateMessage != "") {
            Log.error("Cannot answer positively to transfer request message:" + validateMessage);
            return;
        }

        if (transfers.get(message.id) == null) {
            TransferOperation transferReception = new TransferOperation(message.from, message.id, message.fileElement, message.destDrive, message.sourceFileName);
            transfers.put(transferReception.id, transferReception);
            currentTransfer = transferReception;
        } else {
            TransferOperation transferReception = transfers.get(message.id);
            currentTransfer = transferReception;
        }
        Thread thread = new Thread(new StartJobClient());
        thread.start();
    }

    private class StartJobClient implements Runnable {

        @Override
        public void run() {
            currentTransfer.startClientJob();
        }
    }


        public void onTransferResponseMessage(TransferResponseMessage message) {
        if (currentTransfer != null && currentTransfer.id.equals(message.id) && currentTransfer.isSending) {

            if (message.fileTransferState == Enums.TransferResponseState.ACCEPTED) {
                i.serverTransferService.setSourceFile(currentTransfer.sourceFile);
            }
            if (message.fileTransferState == Enums.TransferResponseState.REFUSED) {
                currentTransfer = null;
                transfers.remove(message.id);
                Log.error("End of sending transfer request because transfer response message is REFUSED");
            }

        } else {
            Log.error("Transfer response message " + message.toString() + " could not be handled");
        }

    }

    /* validate the request coming from MMI
    @result if result != "" , transfer is not possible, the result string contains the reason
    * */
    String validateRequestFromMMI(Enums.TransferableFileElement element, Enums.FileDrive sourceDrive, Enums.FileDrive destDrive) {
        if (currentTransfer != null) {
            return "Unable to start transfer, a request is pending";
        }
        if (buildSourceFile(element, sourceDrive) == null){
            return "Unable to build source file element = " + element + " source drive" + sourceDrive;
        }

        return "";
    }

    /* validate the request coming from TransferRequestMessage
    @result if result != "" , transfer is not possible, the result string contains the reason
    * */
    String validateRequestFromTransferRequestMessage(TransferRequestMessage message) {

        if (!i.confService.getUserTrigram().equals(message.from) && currentTransfer != null) {
            return "Unable to handle transfer request message, a request is pending";
        }


        // check serverIpAdress

        Host serverHost = i.confService.getHostForTrigram(message.from);
        if (serverHost == null) {
            return "Unable to handle transfer request message, cannot get server host";
        }
        return "";
    }


    public File buildSourceFile(Enums.TransferableFileElement element, Enums.FileDrive sourceDrive) {
        File res = null;
        PathsService.PathResponse sourcePathResponse = i.pathsService.getPath(Enums.FileElement.valueOf(element.name()),sourceDrive, "");
        if (sourcePathResponse.reason==""){
            res = sourcePathResponse.path.toFile();
        }
        else{
            System.out.println("build source file impossible " + sourcePathResponse.reason);
        }
        return res;
    }

    public File buildDestFile(String sourceFileName, Enums.TransferableFileElement element, Enums.FileDrive destDrive) {
        File res = null;
        PathsService.PathResponse destPathResponse = i.pathsService.getPath(Enums.FileElement.valueOf(element.name()),destDrive, sourceFileName /* force this filename*/, "");
        res = destPathResponse.path.toFile();
        return res;
    }


    private class TransferOperation {

        boolean isSending = false;
        boolean isReceiving = false;
        String transferSenderTrigram = "";
        String transferRecipientTrigram = "";
        public UUID id = null;
        // only != null if sending
        File sourceFile = null;
        // only != null if receiving
        File destFile = null;

        /*
        Constructor called from client side when wanting to send a file
         */
        public TransferOperation(String transferRecipientTrigram, Enums.TransferableFileElement element, Enums.FileDrive sourceDrive, Enums.FileDrive destDrive) {
            this.isSending = true;
            this.transferSenderTrigram = i.confService.getUserTrigram();
            this.transferRecipientTrigram = transferRecipientTrigram;
            this.id = UUID.randomUUID();
            this.sourceFile = buildSourceFile(element, sourceDrive);

            // if transfer on same host
            if (transferSenderTrigram.equals(transferRecipientTrigram)) {
                isReceiving = true;
                this.destFile = buildDestFile(sourceFile.getName(), element, destDrive);
            }
            sendTransferRequest(sourceFile.getName(), element, destDrive);
        }

        /*
       Constructor called from server side when receiving a transfer request message
        */
        public TransferOperation(String transferSenderTrigram, UUID id, Enums.TransferableFileElement element, Enums.FileDrive destDrive, String sourceFileName) {
            isReceiving = true;
            this.transferSenderTrigram = transferSenderTrigram;
            this.transferRecipientTrigram = i.confService.getUserTrigram();
            this.id = id;
            this.destFile = buildDestFile(sourceFileName, element, destDrive);
        }

        public void startClientJob() {
            sendTransferResponse(Enums.TransferResponseState.ACCEPTED, "");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                // the client will try to connect to server, that will trigger the process
                String serverIpAdress = i.confService.getHostForTrigram(transferSenderTrigram).getIpAddress();
                new ClientTransfer(serverIpAdress, destFile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        private void sendTransferRequest(String sourceFileName, Enums.TransferableFileElement element, Enums.FileDrive destDrive) {
            Set<String> tos = new HashSet<>();
            tos.add(transferRecipientTrigram);
            //TransferRequestMessage msg = new TransferRequestMessage(transferSenderTrigram, tos, id, element, destDrive, sourceFileName);
            TransferRequestMessage msg = new TransferRequestMessage();
            msg.from = transferSenderTrigram;
            msg.tos = tos;
            msg.id = id;
            msg.fileElement = element;
            msg.destDrive = destDrive;
            msg.sourceFileName = sourceFileName;


            try {
                i.networkSender.sendAsync(msg);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

        private void sendTransferResponse(Enums.TransferResponseState transferResponseState, String reason) {
            Set<String> tos = new HashSet<>();
            tos.add(transferSenderTrigram);
            TransferResponseMessage msg = new TransferResponseMessage();
            msg.from = transferRecipientTrigram;
            msg.tos = tos;
            msg.id = id;
            msg.fileTransferState = transferResponseState;
            msg.reason = reason;
            try {
                i.networkSender.sendAsync(msg);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}

