package megamacs.messaging.immediate.server.messagestrategy;


import megamacs.Injector;
import megamacs.messaging.immediate.messages.Message;
import megamacs.messaging.immediate.messages.ProcessOrderMessage;
import megamacs.messaging.immediate.messages.TransferRequestMessage;

/**
 * Created by CARTIER-M on 03/11/2014.
 */
public class ProcessOrderMessageStrategy implements MessageStrategy<Message> {

    Injector i;

    public ProcessOrderMessageStrategy(Injector i) {
        this.i = i;
    }


    public void applyStategy(Message obj) {
       System.out.println("Received message ProcessOrderMessage " + obj.toString());
        i.processOrderService.onProcessOrderMessage((ProcessOrderMessage)obj);

    }
}
