package megamacs.tailing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsDialog extends JDialog implements ActionListener {

	private static final long serialVersionUID = 2897462460185997990L;

	private final Container cont = getContentPane();

	private final JPanel mainPanel = new JPanel();
	private final JScrollPane fontListPanel;
	private final JScrollPane fontSizeListPanel;

	private final JLabel rowsToReadLabel = new JLabel("How many lines from end of file should trace start?");
	private final JLabel rowsToShowLabel = new JLabel("How many rows to keep in memory");
	private final JLabel polltimeLabel = new JLabel("Polltime in ms");
	private final JLabel fontLabel = new JLabel("Choose font");
	private final JLabel lineWrapLabel = new JLabel("Line wrap");
	// private final JLabel showSplashLabel = new JLabel("Show splash screen at startup:");

	private final JTextField rowsToReadTextField = new JTextField();
	private final JTextField rowsToShowTextField = new JTextField();
	private final JTextField polltimeTextField = new JTextField();
	private final JList fontList;
	private final JList fontSizeList;
	private final JCheckBox lineWrapCheckBox = new JCheckBox();
	// private final JCheckBox showSplashCheckBox = new JCheckBox();

	private final JButton saveButton = new JButton("Save");

	private final SettingHandler settingHandler;

	// private final Font[] fonts;
	private final String[] fontsName;
	private final Integer[] fontsSize;
	private final JTabbedPane pane;

	public SettingsDialog(SettingHandler settingHandler, JTabbedPane pane) {

		this.settingHandler = settingHandler;
		this.pane = pane;

		// Fyller p� listan med fonter...
		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		fontsName = env.getAvailableFontFamilyNames();
		fontsSize = new Integer[] { 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22 };
		fontList = new JList(fontsName);
		fontSizeList = new JList(fontsSize);

		// S�tter titeln
		setTitle("megamacs.tailing.SwingTail - Options");

		// Main Panel
		mainPanel.setLayout(null);
		Insets insets = mainPanel.getInsets();

		// rowsToRead
		rowsToReadLabel.setPreferredSize(new Dimension(300, 50));
		rowsToReadTextField.setPreferredSize(new Dimension(10, 25));
		rowsToReadTextField.setColumns(10);
		rowsToReadTextField.setText(this.settingHandler.getRowsToRead() + "");

		// rowsToShow
		rowsToShowLabel.setPreferredSize(new Dimension(200, 50));
		rowsToReadTextField.setPreferredSize(new Dimension(10, 25));
		rowsToShowTextField.setColumns(10);
		rowsToShowTextField.setText(this.settingHandler.getRowsToShow() + "");

		// polltime
		polltimeLabel.setPreferredSize(new Dimension(200, 50));
		polltimeTextField.setPreferredSize(new Dimension(50, 25));
		polltimeTextField.setColumns(10);
		polltimeTextField.setText(this.settingHandler.getPolltimeMS() + "");

		// Font
		fontLabel.setPreferredSize(new Dimension(200, 50));

		// fontList.setPreferredSize(new Dimension(200, 50));
		fontList.setSelectedIndex(0);
		for (String element : fontsName) {
			if (element.equalsIgnoreCase(this.settingHandler.getSettingsFont().getName())) {
				// fontList.setSelectedIndex(i);
				fontList.setSelectedValue(element, true);
				fontList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				break;
			}
		}

		// fontSizeList.setPreferredSize(new Dimension(30,50));
		fontSizeList.setSelectedIndex(2);
		for (int i = 0; i < fontsSize.length; i++) {
			if (fontsSize[i].equals(this.settingHandler.getSettingsFont().getSize())) {
				fontSizeList.setSelectedIndex(i);
				break;
			}
		}

		fontListPanel = new JScrollPane(fontList);
		fontListPanel.setPreferredSize(new Dimension(250, 55));
		fontListPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		fontListPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		fontSizeListPanel = new JScrollPane(fontSizeList);
		fontSizeListPanel.setPreferredSize(new Dimension(50, 55));
		fontSizeListPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		fontSizeListPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		// Line wrap
		lineWrapLabel.setPreferredSize(new Dimension(200, 50));
		lineWrapCheckBox.setSelected(this.settingHandler.isLineWrap());

		// Show splash
		// showSplashLabel.setPreferredSize(new Dimension(200, 50));
		// showSplashCheckBox.setSelected(this.settingHandler.isShowSplash());

		mainPanel.add(rowsToReadLabel);
		mainPanel.add(rowsToReadTextField);
		mainPanel.add(rowsToShowLabel);
		mainPanel.add(rowsToShowTextField);
		mainPanel.add(polltimeLabel);
		mainPanel.add(polltimeTextField);
		mainPanel.add(fontLabel);
		mainPanel.add(fontListPanel);
		mainPanel.add(fontSizeListPanel);
		mainPanel.add(lineWrapLabel);
		mainPanel.add(lineWrapCheckBox);
		// mainPanel.add(showSplashLabel);
		// mainPanel.add(showSplashCheckBox);

		saveButton.addActionListener(this);
		mainPanel.add(saveButton);

		rowsToReadLabel.setBounds(10 + insets.left, 10 + insets.top, 290, 25);
		rowsToReadTextField.setBounds(320 + insets.left, 10 + insets.top, 200, 25);
		rowsToShowLabel.setBounds(10 + insets.left, 45 + insets.top, 290, 25);
		rowsToShowTextField.setBounds(320 + insets.left, 45 + insets.top, 200, 25);
		polltimeLabel.setBounds(10 + insets.left, 80 + insets.top, 290, 25);
		polltimeTextField.setBounds(320 + insets.left, 80 + insets.top, 200, 25);
		fontLabel.setBounds(10 + insets.left, 115 + insets.top, 290, 25);
		fontListPanel.setBounds(320 + insets.left, 115 + insets.top, 150, 130);
		fontSizeListPanel.setBounds(480 + insets.left, 115 + insets.top, 40, 130);
		lineWrapLabel.setBounds(10 + insets.left, 245 + insets.top, 290, 25);
		lineWrapCheckBox.setBounds(320 + insets.left, 245 + insets.top, 25, 25);
		// showSplashLabel.setBounds (10+insets.left, 280+insets.top, 290, 25);
		// showSplashCheckBox.setBounds (320+insets.left, 280+insets.top, 25, 25);

		saveButton.setBounds(420 + insets.left, 315 + insets.top, 100, 25);

		/*
		// Huvudpanelen
		mainPanel.setLayout(new FlowLayout());
		mainPanel.setAlignmentY(LEFT_ALIGNMENT);
		mainPanel.add(rowsToReadPanel);
		mainPanel.add(rowsToShowPanel);
		mainPanel.add(polltimePanel);
		mainPanel.add(fontPanel);
		mainPanel.add(lineWrapPanel);
		 */

		cont.add(mainPanel);

		Point point = getLocation();
		point.x += 100;
		point.y += 100;
		setLocation(point);
		setPreferredSize(new Dimension(540, 385));
		setSize(new Dimension(540, 385));
		setResizable(false);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		settingHandler.setLineWrap(lineWrapCheckBox.isSelected());
		// settingHandler.setShowSplash(showSplashCheckBox.isSelected());
		if (!rowsToReadTextField.getText().equals("")) {
			try {
				int kontroll = Integer.parseInt(rowsToReadTextField.getText());
				if (kontroll >= 0) {
					settingHandler.setRowsToRead(kontroll);
				}
			} catch (NumberFormatException nfe) {
				nfe.printStackTrace();
			}
		}
		if (!rowsToShowTextField.getText().equals("")) {
			try {
				int kontroll = Integer.parseInt(rowsToShowTextField.getText());
				if (kontroll >= 0) {
					settingHandler.setRowsToShow(kontroll);
				}
			} catch (NumberFormatException nfe) {
				nfe.printStackTrace();
			}
		}
		if (!polltimeTextField.getText().equals("")) {
			try {
				int kontroll = Integer.parseInt(polltimeTextField.getText());
				if (kontroll >= 0) {
					settingHandler.setPolltimeMS(kontroll);
				}
			} catch (NumberFormatException nfe) {
				nfe.printStackTrace();
			}
		}
		if (!fontList.isSelectionEmpty()) {
			Font font = new Font(fontsName[fontList.getSelectedIndex()], 0, fontsSize[fontSizeList.getSelectedIndex()]);
			settingHandler.setSettingsFont(font);
		}
		settingHandler.saveSettings();
		for (int i = 0; i < pane.getTabCount(); i++) {
			((SwingTailTab) pane.getTabComponentAt(i)).readSettings();
		}
		setVisible(false);
	}
}
