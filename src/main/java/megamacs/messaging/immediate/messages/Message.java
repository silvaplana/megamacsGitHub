/*
 * PIMS: PR4G IMproved Simulator.
 * Copyright (c) 2014 Bull SAS.
 * All rights reserved.
 */

package megamacs.messaging.immediate.messages;


import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Base class for all distributed object
 *
 * @author Bull SAS
 */
public abstract class Message implements Externalizable {

    //private transient final Logger logger = LoggerFactory.getLogger(getClass());

    public UUID deviceId = UUID.randomUUID();
    public UUID jvmId = NetworkConstant.JVM_ID;
    public String agentName = "Agent";
    public String from;
    public Set<String> tos = new HashSet<>();

   /*
    public Message() {
        this("", Collections.EMPTY_SET);
    }
    */

    public Message() {
        this.deviceId = UUID.randomUUID();
    }


    //@Override
    public void writeExternal(ObjectOutput out) throws IOException {
        try {
            out.writeUTF(deviceId.toString());
            out.writeUTF(jvmId.toString());
            out.writeUTF(agentName);
            out.writeUTF(from);
            out.writeUTF(String.valueOf(tos.size()));
            for (String to:tos){
                out.writeUTF(to);
            }
            doWriteExternal(out);
        } catch (Exception e) {
            //logger.warn("can't write data", e);
        }
    }


    protected abstract void doWriteExternal(ObjectOutput out) throws IOException;

    //@Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        deviceId = UUID.fromString(in.readUTF());
        jvmId = UUID.fromString(in.readUTF());
        agentName = in.readUTF();
        from = in.readUTF();
        String tosLengthStr = in.readUTF();
        int tosLength = new Integer (tosLengthStr);
        tos.clear();
        for (int index=0;index<tosLength;index++){
            tos.add(in.readUTF());
        }
        doReadExternal(in);
    }

    protected abstract void doReadExternal(ObjectInput in) throws IOException;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Message)) return false;

        Message that = (Message) o;

        if (!agentName.equals(that.agentName)) return false;
        if (!deviceId.equals(that.deviceId)) return false;
        if (!from.equals(that.from)) return false;
        if (!jvmId.equals(that.jvmId)) return false;
        if (!tos.equals(that.tos)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = deviceId.hashCode();
        result = 31 * result + jvmId.hashCode();
        result = 31 * result + agentName.hashCode();
        result = 31 * result + from.hashCode();
        result = 31 * result + tos.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Message{" +
                "deviceId=" + deviceId +
                ", jvmId=" + jvmId +
                ", agentName='" + agentName + '\'' +
                ", from='" + from + '\'' +
                ", tos=" + tos +
                '}';
    }
}
