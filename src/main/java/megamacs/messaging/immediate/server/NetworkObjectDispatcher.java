/*
* MACS: Modular & Adaptive Communication Server.
* Copyright (c) 2014 Bull SAS.
* All rights reserved.
*/
package megamacs.messaging.immediate.server;

import megamacs.Injector;
import megamacs.messaging.immediate.messages.*;
import megamacs.messaging.immediate.server.messagestrategy.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Bull SAS
 */
public class NetworkObjectDispatcher {

    Injector i;

    //private final Logger logger = LoggerFactory.getLogger(getClass());



    Map<Class<? extends Message>, MessageStrategy> strategyMap = new HashMap();


    public NetworkObjectDispatcher( Injector i){
        this.i = i;
    }

    public void onStart() {
        strategyMap.put(InfoMessage.class, new InfoMessageStrategy(i));
        strategyMap.put(ProcessOrderMessage.class, new ProcessOrderMessageStrategy(i));
        strategyMap.put(StateNotificationMessage.class, new StateNotificationMessageStrategy(i));
        strategyMap.put(TransferRequestMessage.class, new TransferRequestMessageStrategy(i));
        strategyMap.put(TransferResponseMessage.class, new TransferResponseMessageStrategy(i));

    }

    public void dispatch(Message object) throws NoStrategyException {
        MessageStrategy messageStrategy = strategyMap.get(object.getClass());
        if(messageStrategy == null) {
            throw new NoStrategyException();
        }
        try {
            if (object.tos.contains(i.confService.getUserTrigram())) {
                messageStrategy.applyStategy(object);
            }
        } catch (Exception e) {
            System.out.println("cannot apply strategie for object: " + object.getClass().getSimpleName());
        }
    }

}
