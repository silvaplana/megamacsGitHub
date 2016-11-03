package megamacs.tailing;

import megamacs.fileoperations.PathsService;
import megamacs.Enums;
import megamacs.Injector;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.nio.file.Path;

/**
 * Created by sebastien on 23/07/2016.
 */
public class TailService implements Serializable {

    //public static PathResponse PATH_NOT_FOUND = new PathResponse("", "element not found");

    Injector i;

    JTabbedPane pane;
    SettingHandler settingHandler;

    public TailService(Injector injector, JTabbedPane pane, SettingHandler settingHandler) {
        i = injector;
        this.pane = pane;
        this.settingHandler = settingHandler;
    }

   public void openTailTab(Enums.FileElement fileElement){
       PathsService.PathResponse pathResponse = i.pathsService.getPath(fileElement, null,  "");
        openTailTab(pathResponse.path);
   }

    public void openTailTab(Path path){
        File file = new File(path.toUri());
        System.out.println( " tab on Path " + file.toString() + "is asked to be closed");
        try {
            SwingTailTab tab = new SwingTailTab(pane, file, settingHandler);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void closeTailTab(Enums.FileElement fileElement){
       System.out.println( fileElement + " tab is asked to be closed");
    }

}
