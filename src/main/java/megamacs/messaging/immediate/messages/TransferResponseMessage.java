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
public class TransferResponseMessage extends Message {


    public UUID id;
    public Enums.TransferResponseState fileTransferState;
    public String reason;

    public TransferResponseMessage() {
        super();
    }

    @Override
    protected void doWriteExternal(ObjectOutput out) throws IOException {
        out.writeUTF(id.toString());
        out.writeUTF(fileTransferState.name());
        out.writeUTF(reason);
    }

    @Override
    protected void doReadExternal(ObjectInput in) throws IOException {
        id = UUID.fromString(in.readUTF());
        fileTransferState = Enums.TransferResponseState.valueOf(in.readUTF());
        reason = in.readUTF();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TransferResponseMessage)) return false;
        if (!super.equals(o)) return false;

        TransferResponseMessage that = (TransferResponseMessage) o;

        if (fileTransferState != that.fileTransferState) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (reason != null ? !reason.equals(that.reason) : that.reason != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (fileTransferState != null ? fileTransferState.hashCode() : 0);
        result = 31 * result + (reason != null ? reason.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TransferResponseMessage{" +
                super.toString() +
                "id=" + id +
                ", fileTransferState=" + fileTransferState +
                ", reason='" + reason + '\'' +
                '}';
    }
}
