package megamacs.tailing;

import javax.swing.*;
import java.awt.*;

public class AboutScreen extends JDialog {

	private static final long serialVersionUID = 3083381558252282251L;
	private final Container cont = getContentPane();
	private final JPanel pane = new JPanel();
	private final JTextArea aboutPane = new JTextArea();

	public AboutScreen() {

		setTitle("MegaMacs - About");

		aboutPane.setText("MegaMacs beta\n" + "\n" + "Developed by:\n" + "Megatone\n");
		aboutPane.setLineWrap(true);
		aboutPane.setWrapStyleWord(true);
		aboutPane.setEditable(false);
		aboutPane.setBackground(pane.getBackground());

		pane.setLayout(null);

		pane.add(aboutPane);

		aboutPane.setBounds(20, 20, 360, 160);

		cont.add(pane);

		Point point = getLocation();
		point.x += 100;
		point.y += 100;
		setResizable(false);
		setLocation(point);
		setSize(400, 300);
		setVisible(true);
	}
}
