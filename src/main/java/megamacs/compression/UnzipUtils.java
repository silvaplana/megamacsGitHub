package megamacs.compression;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

/**
 * Created by sebastien on 26/10/2016.
 */
public class UnzipUtils {


    public static void unzip(String sourceZipFile, String destinationFolder){
        String password = "password";
        try {
            ZipFile zipFile = new ZipFile(sourceZipFile);
            if (zipFile.isEncrypted()) {
                zipFile.setPassword(password);
            }
            zipFile.extractAll(destinationFolder);
        } catch (ZipException e) {
            e.printStackTrace();
        }
    }
}
