package megamacs.messaging.immediate.server.messagestrategy;


import megamacs.Injector;
import megamacs.messaging.immediate.messages.Message;

/**
 * Created by CARTIER-M on 03/11/2014.
 */
public class StateNotificationMessageStrategy implements MessageStrategy<Message> {

    Injector i;

    public StateNotificationMessageStrategy(Injector i) {
        this.i = i;
    }


    public void applyStategy(Message obj) {
       System.out.println("Received message StateNotificationMessage " + obj.toString());
    }
}
