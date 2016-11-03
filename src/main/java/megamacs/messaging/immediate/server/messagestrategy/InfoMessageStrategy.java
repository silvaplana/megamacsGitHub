package megamacs.messaging.immediate.server.messagestrategy;


import megamacs.Injector;
import megamacs.messaging.immediate.messages.Message;

/**
 * Created by CARTIER-M on 03/11/2014.
 */
public class InfoMessageStrategy implements MessageStrategy<Message> {

    Injector i;

    public InfoMessageStrategy(Injector i) {
        this.i = i;
    }


    public void applyStategy(Message obj) {
       System.out.println("Received message InfoMessage " + obj.toString());
    }
}
