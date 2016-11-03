package megamacs.processOrder;

import megamacs.Enums;
import megamacs.Injector;
import megamacs.conf.Host;
import megamacs.filetransfer.ClientTransfer;
import megamacs.messaging.immediate.messages.ProcessOrderMessage;
import megamacs.messaging.immediate.messages.TransferRequestMessage;
import megamacs.messaging.immediate.messages.TransferResponseMessage;
import megamacs.utils.Log;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by sebastien on 23/07/2016.
 */
public class ProcessOrderService {
    Injector i;

    public ProcessOrderService(Injector injector) {
        i = injector;
    }


    public String sendPrepareRequest(String fromTrigram, String targetTrigram, Enums.Process process ) {

        Set<String> tos = new HashSet<>();
        tos.add(targetTrigram);

        ProcessOrderMessage msg = new ProcessOrderMessage();
        msg.from = fromTrigram;
        msg.tos = tos;
        msg.order = Enums.ProcessOrder.PREPARE;
        msg.process = process;
        msg.targets = tos;
        msg.fileElement = Enums.FileElement.NO_ELEMENT;
        msg.fileSource ="";
        msg.fileDestination="";




        try {
            i.networkSender.sendAsync(msg);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }

    public String sendRunRequest(String fromTrigram, String targetTrigram, Enums.Process process ) {
        Set<String> tos = new HashSet<>();
        tos.add(targetTrigram);

        ProcessOrderMessage msg = new ProcessOrderMessage();
        msg.from = fromTrigram;
        msg.tos = new HashSet<>();
        tos.add(targetTrigram);
        msg.order = Enums.ProcessOrder.RUN;
        msg.process = process;
        msg.targets = new HashSet<>();
        msg.targets.add(targetTrigram);

        try {
            i.networkSender.sendAsync(msg);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }

    public String sendKillRequest(String fromTrigram, String targetTrigram, Enums.Process process ) {
        Set<String> tos = new HashSet<>();
        tos.add(targetTrigram);

        ProcessOrderMessage msg = new ProcessOrderMessage();
        msg.from = fromTrigram;
        msg.tos = new HashSet<>();
        tos.add(targetTrigram);
        msg.order = Enums.ProcessOrder.KILL;
        msg.process = process;
        msg.targets = new HashSet<>();
        msg.targets.add(targetTrigram);

        try {
            i.networkSender.sendAsync(msg);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }



    public void onProcessOrderMessage(ProcessOrderMessage message) {
        if (message.tos.contains(i.confService.getUserTrigram())) {

            System.out.println ("I received a ProcessOrderMessage " + message.toString());

            if (message.order.equals(Enums.ProcessOrder.PREPARE)) {
                if (message.process.equals(Enums.Process.MACS)) {
                    String reason = i.prepareMacsDataService.prepareMacs();
                    if (reason != "") {
                        System.out.println("Failed to Prepare Macs. reason=" + reason);
                    }
                }
                if (message.process.equals(Enums.Process.SICS)) {
                    String reason = i.prepareSicsDataService.prepareSics();
                    if (reason != "") {
                        System.out.println("Failed to Prepare Sics. reason=" + reason);
                    }
                }
            }
            if (message.order.equals(Enums.ProcessOrder.RUN)) {
                if (message.process.equals(Enums.Process.MACS) || message.process.equals(Enums.Process.SICS)) {
                    String reason = i.runService.run(message.process);
                    if (reason != "") {
                        System.out.println("Failed to Run  Macs or Sics. reason=" + reason);
                    }
                }
            }
            if (message.order.equals(Enums.ProcessOrder.KILL)) {
                if (message.process.equals(Enums.Process.MACS) || message.process.equals(Enums.Process.SICS)) {
                    String reason = i.runService.kill(message.process);
                    if (reason != "") {
                        System.out.println("Failed to Kill Macs or Sics. reason=" + reason);
                    }
                }
            }
        }
    }

    public void processRun(ProcessOrderMessage message){

    }

    public void processKill(ProcessOrderMessage message){

    }

    public String validateUnzipRequestFromMMI (ProcessOrderMessage message){
        return "";
    }

    public String validateRunRequestFromMMI (ProcessOrderMessage message){
        return "";
    }

    public String validateKillRequestFromMMI (ProcessOrderMessage message){
        return "";
    }
}
