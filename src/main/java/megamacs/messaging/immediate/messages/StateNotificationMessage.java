/*
* MACS: Modular & Adaptive Communication Server.
* Copyright (c) 2014 Bull SAS.
* All rights reserved.
*/
package megamacs.messaging.immediate.messages;

import megamacs.Enums;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Set;
import java.util.UUID;

/**
 * @author Bull SAS
 */
public class StateNotificationMessage extends Message {


    public Enums.ProcessState process;
    public String text;


    public StateNotificationMessage() {
        super();
        /*
        this.process = process;
        this.text = text;
        */
    }

    @Override
    protected void doWriteExternal(ObjectOutput out) throws IOException {
        out.writeUTF(process.name());
        out.writeUTF(text);
    }

    @Override
    protected void doReadExternal(ObjectInput in) throws IOException {
        process = Enums.ProcessState.valueOf(in.readUTF());
        text = in.readUTF();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StateNotificationMessage)) return false;
        if (!super.equals(o)) return false;

        StateNotificationMessage that = (StateNotificationMessage) o;

        if (process != that.process) return false;
        if (text != null ? !text.equals(that.text) : that.text != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (process != null ? process.hashCode() : 0);
        result = 31 * result + (text != null ? text.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "InfoMessage{" +
                "process=" + process +
                ", text='" + text + '\'' +
                '}';
    }
}
