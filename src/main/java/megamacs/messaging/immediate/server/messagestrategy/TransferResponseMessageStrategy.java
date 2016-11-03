package megamacs.messaging.immediate.server.messagestrategy;


import megamacs.Injector;
import megamacs.messaging.immediate.messages.Message;
import megamacs.messaging.immediate.messages.TransferResponseMessage;

public class TransferResponseMessageStrategy implements MessageStrategy<Message> {

    Injector i;

    public TransferResponseMessageStrategy(Injector i) {
        this.i = i;
    }


    public void applyStategy(Message obj) {
       System.out.println("Received message TransferResponseMessage " + obj.toString());
        i.transferService.onTransferResponseMessage((TransferResponseMessage) obj);
    }
}
