/*
* MACS: Modular & Adaptive Communication Server.
* Copyright (c) 2014 Bull SAS.
* All rights reserved.
*/
package megamacs.messaging.immediate.server.messagestrategy;


import megamacs.messaging.immediate.messages.Message;
import megamacs.messaging.immediate.server.DecodingException;

/**
 * @author Bull SAS
 */
public interface MessageStrategy<T extends Message> {

    public void applyStategy(T obj) throws DecodingException;

}
