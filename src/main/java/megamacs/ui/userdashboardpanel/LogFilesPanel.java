package megamacs.ui.userdashboardpanel;

import megamacs.Enums;
import megamacs.Injector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by sebastien on 21/07/2016.
 */
public class LogFilesPanel extends JPanel
        implements ActionListener {

    java.util.List<Enums.FileElement> logFiles = new LinkedList(Arrays.asList(Enums.FileElement.MACS_KARAF, Enums.FileElement.MACS_SEGMENTED, Enums.FileElement.MACS_MOPD));

    Map<Enums.FileElement, JCheckBox> logFileUIs = new HashMap();

    Injector i;

    LogFilesPanel(Injector i){
       super (new GridLayout(0, 1));

        this.i = i;


        for (Enums.FileElement logFile : logFiles){
            JCheckBox checkBox  = new JCheckBox(logFile.toString());

            checkBox.addActionListener(this);

            logFileUIs.put(logFile, checkBox);
            add(checkBox);

            //init value
            if (i.confService.getCheckedLogFiles().contains(logFile)){
                checkBox.setSelected(true);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("LogFiles");


        for (
                Map.Entry<Enums.FileElement, JCheckBox> logFileUI :
                logFileUIs.entrySet()
                ) {
            if (logFileUI.getValue().equals(e.getSource())){
                System.out.println("CheckBox de la clé " + logFileUI.getKey());
                if (logFileUI.getValue().isSelected()){
                    i.confService.addCheckedLogFile(logFileUI.getKey());
                    i.tailService.openTailTab(logFileUI.getKey());
                }
               else{
                    i.confService.removeCheckedLogFile(logFileUI.getKey());
                    i.tailService.closeTailTab(logFileUI.getKey());
                }

            }
        }
    }
}
