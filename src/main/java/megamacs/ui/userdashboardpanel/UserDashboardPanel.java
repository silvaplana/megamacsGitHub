package megamacs.ui.userdashboardpanel;

import megamacs.Injector;

import javax.swing.*;
import java.awt.*;

/**
 * Created by sebastien on 21/07/2016.
 */
public class UserDashboardPanel extends JPanel {

    Injector i;

    public UserDashboardPanel(Injector i){
       super (new GridLayout(0, 1));

        this.i = i;

        SupervisorPanel supervisorPanel = new SupervisorPanel(i);
        LogFilesPanel logFilesPanel= new LogFilesPanel(i);

        add(supervisorPanel);
        add(logFilesPanel);
    }
}
