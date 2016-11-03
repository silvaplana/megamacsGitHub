package megamacs.messaging.immediate.server.messagestrategy;


import megamacs.Injector;
import megamacs.messaging.immediate.messages.Message;
import megamacs.messaging.immediate.messages.TransferRequestMessage;

public class TransferRequestMessageStrategy implements MessageStrategy<Message> {

    Injector i;

    public TransferRequestMessageStrategy(Injector i) {
        this.i = i;
    }


    public void applyStategy(Message obj) {
       System.out.println("Received message TransferRequestMessage " + obj.toString());
        i.transferService.onTransferRequestMessage((TransferRequestMessage)obj);
    }
}
