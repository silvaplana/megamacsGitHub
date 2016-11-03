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
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author Bull SAS
 */
public class ProcessOrderMessage extends Message {


    public Enums.ProcessOrder order;
    public Enums.Process process;
    public Set<String> targets = new HashSet<>();
    public Enums.FileElement fileElement;
    public String fileSource;
    public String fileDestination;


    public ProcessOrderMessage( ) {
        super();
        /*
        this.order = order;
        this.targets = targets;
        this.fileElement = fileElement;
        this.fileSource=fileSource;
        this.fileDestination=fileDestination;*/
    }

    @Override
    protected void doWriteExternal(ObjectOutput out) throws IOException {
        out.writeUTF(order.name());
        out.writeUTF(process.name());
        out.writeUTF(String.valueOf(targets.size()));
        for (String target:targets){
            out.writeUTF(target);
        }
        out.writeUTF(fileElement.name());
        out.writeUTF(fileSource);
        out.writeUTF(fileDestination);
    }

    @Override
    protected void doReadExternal(ObjectInput in) throws IOException {
        order = Enums.ProcessOrder.valueOf(in.readUTF());
        process = Enums.Process.valueOf(in.readUTF());
        String targetsLengthStr = in.readUTF();
        int targetssLength = new Integer (targetsLengthStr);
        targets.clear();
        for (int index=0;index<targetssLength;index++){
            targets.add(in.readUTF());
        }
        fileElement = Enums.FileElement.valueOf(in.readUTF());
        fileSource = in.readUTF();
        fileDestination = in.readUTF();
    }


    @Override
    public String toString() {
        return "ProcessOrderMessage{" +
                super.toString() +
                "order=" + order +
                " process=" + process +
                ", targets=" + targets +
                ", fileElement=" + fileElement +
                ", fileSource='" + fileSource + '\'' +
                ", fileDestination='" + fileDestination + '\'' +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProcessOrderMessage)) return false;
        if (!super.equals(o)) return false;

        ProcessOrderMessage that = (ProcessOrderMessage) o;

        if (fileDestination != null ? !fileDestination.equals(that.fileDestination) : that.fileDestination != null)
            return false;
        if (fileElement != that.fileElement) return false;
        if (fileSource != null ? !fileSource.equals(that.fileSource) : that.fileSource != null) return false;
        if (order != that.order) return false;
        if (process != that.process) return false;
        if (targets != null ? !targets.equals(that.targets) : that.targets != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (order != null ? order.hashCode() : 0);
        result = 31 * result + (process != null ? process.hashCode() : 0);
        result = 31 * result + (targets != null ? targets.hashCode() : 0);
        result = 31 * result + (fileElement != null ? fileElement.hashCode() : 0);
        result = 31 * result + (fileSource != null ? fileSource.hashCode() : 0);
        result = 31 * result + (fileDestination != null ? fileDestination.hashCode() : 0);
        return result;
    }
}
