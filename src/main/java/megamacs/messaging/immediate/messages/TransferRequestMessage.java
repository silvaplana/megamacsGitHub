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
public class TransferRequestMessage extends Message {


    public UUID id;
    public Enums.TransferableFileElement fileElement;
    public Enums.FileDrive destDrive;
    public String sourceFileName;

    public TransferRequestMessage() {
        super();
    }

    @Override
    protected void doWriteExternal(ObjectOutput out) throws IOException {
        out.writeUTF(id.toString());
        out.writeUTF(fileElement.name());
        out.writeUTF(destDrive.name());
        out.writeUTF(sourceFileName);
    }

    @Override
    protected void doReadExternal(ObjectInput in) throws IOException {

        id = UUID.fromString(in.readUTF());
        fileElement = Enums.TransferableFileElement.valueOf(in.readUTF());
        destDrive = Enums.FileDrive.valueOf(in.readUTF());
        sourceFileName = in.readUTF();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TransferRequestMessage)) return false;
        if (!super.equals(o)) return false;

        TransferRequestMessage that = (TransferRequestMessage) o;

        if (destDrive != that.destDrive) return false;
        if (fileElement != that.fileElement) return false;
        if (sourceFileName != null ? !sourceFileName.equals(that.sourceFileName) : that.sourceFileName != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (fileElement != null ? fileElement.hashCode() : 0);
        result = 31 * result + (destDrive != null ? destDrive.hashCode() : 0);
        result = 31 * result + (sourceFileName != null ? sourceFileName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TransferRequestMessage{" +
                super.toString() +
                "id=" + id +
                ", fileElement=" + fileElement +
                ", destDrive=" + destDrive +
                ", filePath='" + sourceFileName + '\'' +
                '}';
    }
}
