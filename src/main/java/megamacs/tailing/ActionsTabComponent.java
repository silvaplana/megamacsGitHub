package megamacs.tailing;

import javax.swing.*;
import java.awt.*;

/**
 * Created by sebastien on 18/06/2016.
 */
public class ActionsTabComponent extends JPanel{

    ActionsTabComponent(String text){
        super (false);
        JLabel filler = new JLabel(text);
        filler.setHorizontalAlignment(JLabel.CENTER);
        setLayout(new GridLayout(1, 1));
        add(filler);
    }

}
