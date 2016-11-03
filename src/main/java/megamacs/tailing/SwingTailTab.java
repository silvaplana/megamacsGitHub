package megamacs.tailing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;

public class SwingTailTab extends ButtonTabComponent {
	private final GridLayout layout = new GridLayout();
	private final JPanel panel;
	private final RowLimitedTextArea text;
	private final JScrollPane scrollPane;
	private final SettingHandler settingHandler;

	private static final long serialVersionUID = 5676550860899134502L;

	public SwingTailTab(JTabbedPane inPane,File inFile, SettingHandler settingHandler) throws FileNotFoundException {
		super(inPane, inFile);
		this.settingHandler = settingHandler;
		panel = new JPanel(layout);
		panel.setName(file.getName());
		text = new RowLimitedTextArea();

		readSettings();

		scrollPane = new JScrollPane(text);
		panel.add(scrollPane);
		pane.addTab(file.getName(), panel);
		pane.setTabComponentAt(pane.getTabCount()-1, this);
		pane.setSelectedIndex(pane.getTabCount()-1);
		monitorFile = new FileMonitor(text,file, settingHandler);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
	}

	public File getFile(){
		return file;
	}

	public String getText(){
		return text.getText();
	}

	public void readSettings(){
		text.setLineWrap(settingHandler.isLineWrap());
		text.setFont(settingHandler.getSettingsFont());
		text.setMaxrows(settingHandler.getRowsToShow());
	}
}
