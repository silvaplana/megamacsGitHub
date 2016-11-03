package megamacs.runandkill;

import megamacs.Enums;
import megamacs.Injector;
import megamacs.compression.UnzipUtils;
import megamacs.fileoperations.AsciiFileTools;
import megamacs.fileoperations.PathsService;

import java.io.IOException;

public class RunService {

    Injector i;

    public RunService(Injector i) throws IOException {

        this.i = i;
    }


    public String run(Enums.Process process){
        String res = "";

        PathsService.PathResponse resp = null;

        if (process.equals(Enums.Process.MACS)){
            resp = i.pathsService.getPath(Enums.FileElement.MACS_EXE, "");
        }
        if (process.equals(Enums.Process.SICS)){
            resp = i.pathsService.getPath(Enums.FileElement.SICS_EXE, "");
        }

        if (resp == null){
            System.out.println("Cannot run " + process + ". Problem with path");
            return resp.reason;
        }

        if (resp.reason != ""){
            System.out.println("Cannot run " + process + ". Problem with path. reason " + resp.reason);
            return resp.reason;
        }

        // try to run the path
        System.out.println("Try to launch " + resp.path.toString());
        try {
            Process t = Runtime.getRuntime().exec(resp.path.toString());

            System.out.println("Wait 10 s....");
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            System.out.println("Try to kill");
            t.destroy();


        } catch (IOException e) {
            e.printStackTrace();
        }

        return res;
    }

    public String kill(Enums.Process process){
        String res = "";
        return res;
    }



}