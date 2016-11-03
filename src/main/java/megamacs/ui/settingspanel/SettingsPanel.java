package megamacs.ui.settingspanel;

import megamacs.Enums;
import megamacs.Injector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by sebastien on 21/07/2016.
 */
public class SettingsPanel extends JPanel {

    Injector i;

    public SettingsPanel(Injector i) {
        super(new GridLayout(0, 1));

        this.i = i;

        add(new UserNamePanel());
        add(new HostPanel());
        add(new PathsPanel());
        add(new PR4GPortPanel());
        add(new HostsTablePanel(i));
    }

    class UserNamePanel extends JPanel
            implements ActionListener {

        JLabel userNameLabel;

        UserNamePanel(){
            super (new GridLayout(1, 2));
            add(new JLabel("User name:"));
            userNameLabel = new JLabel();

            // init value
            userNameLabel.setText(i.confService.getUserName());

            add(userNameLabel);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
        }
    }

    class HostPanel extends JPanel
            implements ActionListener {

        JTextField trigramTF;
        JTextField ipAddressTF;

        HostPanel(){
            super (new GridLayout(1, 5));
            add(new JLabel("Host:"));
            trigramTF = new JTextField();
            ipAddressTF = new JTextField();

            // init value
            trigramTF.setText(i.confService.getUserTrigram());
            ipAddressTF.setText(i.confService.getUserIpAdress());

            add(new JLabel("trigram:"));
            add(trigramTF);
            add(new JLabel("Ip address:"));
            add(ipAddressTF);


            trigramTF.addActionListener(this);
            ipAddressTF.addActionListener(this);

        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == trigramTF) {
                i.confService.setTrigram(trigramTF.getText());
            }
            if (e.getSource() == ipAddressTF) {
                i.confService.setIpAddress(ipAddressTF.getText());
            }

        }
    }

    class PathsPanel extends JPanel
            implements ActionListener {

        JFileChooser fileChooser;

        JTextField usbDirTextField;
        JButton usbDirButton;
        JTextField m2DirTextField;
        JButton m2DirButton;
        JTextField macsDirTextField;
        JButton macsDirButton;
        JTextField macsVersionTextField;
        JButton macsVersionButton;
        JTextField sicsDirTextField;
        JButton sicsDirButton;
        JTextField sicsJreTextField;
        JButton sicsJreButton;
        JTextField sicsVersionTextField;
        JButton sicsVersionButton;
        JTextField pimsDirTextField;
        JButton pimsDirButton;
        JTextField marsDirTextField;
        JButton marsDirButton;

        PathsPanel() {
            super (new GridLayout(0, 3));

            fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            //usb Dir
            usbDirTextField = new JTextField();
            usbDirButton = new JButton("Choose");
            createLine("USB directory:", usbDirTextField, usbDirButton);

            //m2 Dir
            m2DirTextField = new JTextField();
            m2DirButton = new JButton("Choose");
            createLine(".m2 directory (mandatory):", m2DirTextField, m2DirButton);

            //macs Dir
            macsDirTextField = new JTextField();
            macsDirButton = new JButton("Choose");
            createLine("Macs directory:", macsDirTextField, macsDirButton);

            //macs version
            macsVersionTextField = new JTextField();
            macsVersionButton = new JButton("");
            createLine("Macs version:", macsVersionTextField, macsVersionButton);
            macsVersionTextField.setEditable(true);
            macsVersionTextField.addActionListener(this);
            macsVersionButton.setEnabled(false);

            //sics Dir
            sicsDirTextField = new JTextField();
            sicsDirButton = new JButton("Choose");
            createLine("Sics directory:", sicsDirTextField, sicsDirButton);

            //sics version
            sicsVersionTextField = new JTextField();
            sicsVersionButton = new JButton("");
            createLine("Sics version:", sicsVersionTextField, sicsVersionButton);
            sicsVersionTextField.setEditable(true);
            sicsVersionTextField.addActionListener(this);
            sicsVersionButton.setEnabled(false);

            //sics Jre
            sicsJreTextField = new JTextField();
            sicsJreButton = new JButton("Choose");
            createLine("Sics Jre directory:", sicsJreTextField, sicsJreButton);

            //pims Dir
            pimsDirTextField = new JTextField();
            pimsDirButton = new JButton("Choose");
            createLine("Pims directory:", pimsDirTextField, pimsDirButton);

            //mars Dir
            marsDirTextField = new JTextField();
            marsDirButton = new JButton("Choose");
            createLine("Mars directory:", marsDirTextField, marsDirButton);

            // init values
            usbDirTextField.setText(i.confService.getDir(Enums.FileElement.USB_DIR));
            m2DirTextField.setText(i.confService.getDir(Enums.FileElement.M2_DIR));
            macsDirTextField.setText(i.confService.getDir(Enums.FileElement.MACS_DIR));
            macsVersionTextField.setText(i.confService.getDir(Enums.FileElement.MACS_VERSION));
            sicsDirTextField.setText(i.confService.getDir(Enums.FileElement.SICS_DIR));
            sicsVersionTextField.setText(i.confService.getDir(Enums.FileElement.SICS_VERSION));
            sicsJreTextField.setText(i.confService.getDir(Enums.FileElement.SICS_JRE_DIR));
            pimsDirTextField.setText(i.confService.getDir(Enums.FileElement.PIMS_DIR));
            marsDirTextField.setText(i.confService.getDir(Enums.FileElement.MARS_DIR));
        }


        void createLine (String textLabel, JTextField textField, JButton button){
            add(new JLabel(textLabel));
            add (textField);
            textField.setEditable(false);
            add ( button);
            button.addActionListener(this);
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            //Handle open button action.
            if (e.getSource() == usbDirButton) {
                int returnVal = fileChooser.showOpenDialog(SettingsPanel.this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    usbDirTextField.setText(fileChooser.getSelectedFile().getAbsolutePath());
                    i.confService.setDir(Enums.FileElement.USB_DIR, fileChooser.getSelectedFile().getAbsolutePath() );
                }
            }
            if (e.getSource() == m2DirButton) {
                int returnVal = fileChooser.showOpenDialog(SettingsPanel.this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    m2DirTextField.setText(fileChooser.getSelectedFile().getAbsolutePath());
                    i.confService.setDir(Enums.FileElement.M2_DIR, fileChooser.getSelectedFile().getAbsolutePath() );
                }
            }
            if (e.getSource() == macsDirButton) {
                int returnVal = fileChooser.showOpenDialog(SettingsPanel.this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    macsDirTextField.setText(fileChooser.getSelectedFile().getAbsolutePath());
                    i.confService.setDir(Enums.FileElement.MACS_DIR, fileChooser.getSelectedFile().getAbsolutePath() );
                }
            }
            if (e.getSource() == macsVersionTextField) {
               i.confService.setDir(Enums.FileElement.MACS_VERSION, macsVersionTextField.getText());
            }

            if (e.getSource() == sicsDirButton) {
                int returnVal = fileChooser.showOpenDialog(SettingsPanel.this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    sicsDirTextField.setText(fileChooser.getSelectedFile().getAbsolutePath());
                    i.confService.setDir(Enums.FileElement.SICS_DIR, fileChooser.getSelectedFile().getAbsolutePath() );
                }
            }

            if (e.getSource() == sicsJreButton) {
                int returnVal = fileChooser.showOpenDialog(SettingsPanel.this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    sicsJreTextField.setText(fileChooser.getSelectedFile().getAbsolutePath());
                    i.confService.setDir(Enums.FileElement.SICS_JRE_DIR, fileChooser.getSelectedFile().getAbsolutePath() );
                }
            }

            if (e.getSource() == sicsVersionTextField) {
                i.confService.setDir(Enums.FileElement.SICS_VERSION, sicsVersionTextField.getText());
            }

            if (e.getSource() == pimsDirButton) {
                int returnVal = fileChooser.showOpenDialog(SettingsPanel.this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    pimsDirTextField.setText(fileChooser.getSelectedFile().getAbsolutePath());
                    i.confService.setDir(Enums.FileElement.PIMS_DIR, fileChooser.getSelectedFile().getAbsolutePath() );
                }
            }
            if (e.getSource() == marsDirButton) {
                int returnVal = fileChooser.showOpenDialog(SettingsPanel.this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    marsDirTextField.setText(fileChooser.getSelectedFile().getAbsolutePath());
                    i.confService.setDir(Enums.FileElement.MARS_DIR, fileChooser.getSelectedFile().getAbsolutePath() );
                }
            }

        }
    }

    class PR4GPortPanel extends JPanel
            implements ActionListener {

        JComboBox PR4GPortComboBox;

        PR4GPortPanel() {
            super(new GridLayout(1, 2));
            add(new JLabel("PR4G port:"));
            PR4GPortComboBox = new JComboBox(Enums.PR4GPort.values());

            // init value
            PR4GPortComboBox.setSelectedItem(i.confService.getPR4GPort());

            PR4GPortComboBox.addActionListener(this);

            add(PR4GPortComboBox);
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            System.out.println("action performed on PR4G port:" + ((JComboBox) e.getSource()).getSelectedItem());

            i.confService.setPR4GPort(((Enums.PR4GPort) ((JComboBox) e.getSource()).getSelectedItem()));
        }
    }

}
