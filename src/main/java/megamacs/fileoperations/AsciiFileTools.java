package megamacs.fileoperations;

import oracle.jrockit.jfr.StringConstantPool;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by sebastien on 29/10/2016.
 */
public class AsciiFileTools {


    /**
     * This method when it finds bitOfLineToFind in a line replaces the whole line by lineToReplace
     * @param sourceFile
     * @param bitOfLineToFind
     * @param lineToReplace
     * @return
     */
    public static String replaceStringInFile (final String sourceFile, String bitOfLineToFind, String lineToReplace ){
        String res = "";

        // 1. convert file in array of String
        List<String> fileContent = new ArrayList<>();
        try {

            String currentLine;

            BufferedReader br = new BufferedReader(new FileReader(sourceFile));

            while ((currentLine = br.readLine()) != null) {
                fileContent.add(currentLine);
            }

            br.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        finally{
            // if (br!=null) br.close();
        }

        // 2. Make replacments
        for (int i = 0; i < fileContent.size(); i++) {
            if (fileContent.get(i).contains(bitOfLineToFind)) {
                fileContent.set(i, lineToReplace);
            }
        }

        // 3 . write file
        try {
            PrintWriter writer = new PrintWriter(sourceFile, "UTF-8");
            for (String str: fileContent){
                writer.println(str);
            }
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return res;
    }

    public static void copierFichier (String sourceFile, String destFile) throws IOException {
        FileInputStream fis = null;
        FileOutputStream fos = null;

        try{
            byte buffer[] = new byte[1024];
            int taille = 0;

            fis = new FileInputStream(sourceFile);
            fos = new FileOutputStream(destFile);

            while (( taille = fis.read(buffer))!=-1){
                System.out.println(taille);
                fos.write(buffer, 0, taille);
            }
        }
        finally{
            if (fis!=null){
                try{
                    fis.close();
                }catch (IOException e){
                }
            }
            if (fos!=null){
                try{
                    fos.close();
                }catch (IOException e){
                }
            }
        }
    }
}
