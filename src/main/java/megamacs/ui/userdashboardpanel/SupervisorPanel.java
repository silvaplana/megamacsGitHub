package megamacs.ui.userdashboardpanel;

import megamacs.Enums;
import megamacs.Injector;
import megamacs.conf.Host;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sebastien on 21/07/2016.
 */
public class SupervisorPanel extends JPanel {

    Injector i;

    DeployActionsPanel deployActionsPanel = null;


    SupervisorPanel(Injector i) {
        super(new GridLayout(0, 1));

        this.i = i;

        //add(new PR4GTypePanel());
        //add(new PR4GChannelsPanel());
        add(new PR4GChannelsConfigurationPanel());
        deployActionsPanel = new DeployActionsPanel();
        add(deployActionsPanel);
        add(new ProcessControlPanel());
        add(new GroupedActionsPanel());
    }


    /*
    class PR4GTypePanel extends JPanel
            implements ActionListener {

        private String PR4G_RADIO_BUTTON = "PR4G";
        private String PIMS_RADIO_BUTTON = "Pims";


        JRadioButton pr4gTypePr4gButton;
        JRadioButton pr4gTypePimsButton;
        ButtonGroup group;


        PR4GTypePanel() {
            super(new GridLayout(1, 3));

            /////////////////
            // pr4g Type
            ////////////////

            //Create the radio buttons.
            pr4gTypePr4gButton = new JRadioButton(PR4G_RADIO_BUTTON);
            pr4gTypePr4gButton.setActionCommand(PR4G_RADIO_BUTTON);

            pr4gTypePimsButton = new JRadioButton(PIMS_RADIO_BUTTON);
            pr4gTypePimsButton.setActionCommand(PIMS_RADIO_BUTTON);

            //Group the radio buttons.
            group = new ButtonGroup();
            group.add(pr4gTypePr4gButton);
            group.add(pr4gTypePimsButton);

            //Register a listener for the radio buttons.
            pr4gTypePr4gButton.addActionListener(this);
            pr4gTypePimsButton.addActionListener(this);

            //Put the radio buttons in a column in a panel.
            add(new JLabel("PR4G type:"));
            add(pr4gTypePr4gButton);
            add(pr4gTypePimsButton);

            // init value
            pr4gTypePr4gButton.setSelected(i.confService.getIsPR4G());
            pr4gTypePimsButton.setSelected(!i.confService.getIsPR4G());
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (PR4G_RADIO_BUTTON.equals(e.getActionCommand())) {
                i.confService.setIsPR4G(true);
            } else if (PIMS_RADIO_BUTTON.equals(e.getActionCommand())) {
                i.confService.setIsPR4G(false);
            }
        }
    }
    */


    /*
    class PR4GChannelsPanel extends JPanel
            implements ActionListener {

        JComboBox PR4GPortComboBox;

        JComboBox PR4GPublicChannelComboBox;
        JComboBox PR4GPrivateChannelComboBox;

        PR4GChannelsPanel() {
            super(new GridLayout(1, 5));
            add(new JLabel("PR4G channels:"));

            // public channel
            add(new JLabel("public:"));
            PR4GPublicChannelComboBox = new JComboBox(Enums.PR4GChannel.values());
            PR4GPublicChannelComboBox.addActionListener(this);
            add(PR4GPublicChannelComboBox);

            // private channel
            add(new JLabel("private:"));
            PR4GPrivateChannelComboBox = new JComboBox(Enums.PR4GChannel.values());
            PR4GPrivateChannelComboBox.addActionListener(this);
            add(PR4GPrivateChannelComboBox);

            // init values
            PR4GPublicChannelComboBox.setSelectedItem(i.confService.getPR4GPublicChannel());
            PR4GPrivateChannelComboBox.setSelectedItem(i.confService.getPR4GPrivateChannel());
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            System.out.println("action performed on PR4G channels:" + ((JComboBox) e.getSource()).getSelectedItem());

            if (PR4GPublicChannelComboBox.equals(e.getSource())) {
                i.confService.setPR4GPublicChannel(((Enums.PR4GChannel) ((JComboBox) e.getSource()).getSelectedItem()));
            }
            if (PR4GPrivateChannelComboBox.equals(e.getSource())) {
                i.confService.setPR4GPrivateChannel(((Enums.PR4GChannel) ((JComboBox) e.getSource()).getSelectedItem()));
            }

        }
    }
    */

    class PR4GChannelsConfigurationPanel extends JPanel
            implements ActionListener {

        JComboBox PR4GChannelsConfigurationComboBox;


        PR4GChannelsConfigurationPanel() {
            super(new GridLayout(1, 2));
            add(new JLabel("PR4G configuration:"));

            PR4GChannelsConfigurationComboBox = new JComboBox(Enums.PR4GChannelsConfiguration.values());
            add(PR4GChannelsConfigurationComboBox);
            PR4GChannelsConfigurationComboBox.addActionListener(this);

            // init values
            PR4GChannelsConfigurationComboBox.setSelectedItem(i.confService.getPR4GChannelsConfiguration());
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            System.out.println("action performed on PR4G channels:" + ((JComboBox) e.getSource()).getSelectedItem());

            if (PR4GChannelsConfigurationComboBox.equals(e.getSource())) {
                i.confService.setPR4GChannelsConfiguration(((Enums.PR4GChannelsConfiguration) ((JComboBox) e.getSource()).getSelectedItem()));
            }
        }
    }



    class DeployActionsPanel extends JPanel
            implements ActionListener {

       /*
        String[] deployActionsNames = {
                "Export to USB: pr4g.manager.jar (.m2)",
                "Export to USB: macs.zip",
                "Deploy from USB: pr4g.manager.jar",
                "Deploy from USB: macs.zip",
                "Deploy from local: pr4g.manager.jar (.m2)",
                "Deploy from local: userdashboardpanel.jar"
        };
        */


        JComboBox transferableElementCb;
        JComboBox sourceDriveCb;
        JComboBox destinationDriveCb;


        DeployActionsPanel() {
            super(new GridLayout(1, 7));


            transferableElementCb = new JComboBox(Enums.TransferableFileElement.values());
            transferableElementCb.setEditable(true);
            sourceDriveCb = new JComboBox(Enums.FileDrive.values());
            destinationDriveCb = new JComboBox(Enums.FileDrive.values());

            // init value
            transferableElementCb.setSelectedItem(i.confService.getDeployActionTransferElement());
            sourceDriveCb.setSelectedItem(i.confService.getDeployActionSourceDrive());
            destinationDriveCb.setSelectedItem(i.confService.getDeployActionDestinationDrive());


            transferableElementCb.addActionListener(this);
            sourceDriveCb.addActionListener(this);
            destinationDriveCb.addActionListener(this);


            add(new JLabel("Transfer action:"));
            add(new JLabel("Element to transfer:"));
            add(transferableElementCb);
            add(new JLabel("Source:"));
            add(sourceDriveCb);
            add(new JLabel("Destination:"));
            add(destinationDriveCb);
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            if (e.getSource().equals(transferableElementCb)) {
                i.confService.setDeployActionTransferElement((Enums.TransferableFileElement) ((JComboBox) e.getSource()).getSelectedItem());
            }
            if (e.getSource().equals(sourceDriveCb)) {
                i.confService.setDeployActionSourceDrive((Enums.FileDrive) ((JComboBox) e.getSource()).getSelectedItem());
            }
            if (e.getSource().equals(destinationDriveCb)) {
                i.confService.setDeployActionDestinationDrive((Enums.FileDrive) ((JComboBox) e.getSource()).getSelectedItem());
            }
        }
    }

    class ProcessControlPanel extends JPanel
            implements ActionListener {
        Map<String, ProcessControlUI> processControlUIs = new HashMap();

        ProcessControlPanel() {
            super(new GridLayout(0, 11));

            for (Host host : i.confService.getHosts()) {
                ProcessControlUI processControlUI = new ProcessControlUI();
                processControlUI.host = host;
                processControlUIs.put(host.getTrigram(), processControlUI);
                processControlUI.checkBox = new JCheckBox();
                processControlUI.trigramLabel = new JLabel(host.getTrigram());
                processControlUI.transferButton = new JButton("Transfer");
                processControlUI.prepareMacsButton = new JButton("Prepare Macs");
                processControlUI.launchMacsButton = new JButton("Launch Macs");
                processControlUI.killMacsButton = new JButton("Kill Macs");
                processControlUI.prepareSicsButton = new JButton("Prepare Sics");
                processControlUI.launchSicsButton = new JButton("Launch Sics");
                processControlUI.killSicsButton = new JButton("Kill Sics");
                processControlUI.progressBar = new JProgressBar();
                processControlUI.commentsLabel = new JLabel("Comments");


                processControlUI.checkBox.addActionListener(this);
                processControlUI.transferButton.addActionListener(this);
                processControlUI.prepareMacsButton.addActionListener(this);
                processControlUI.launchMacsButton.addActionListener(this);
                processControlUI.killMacsButton.addActionListener(this);
                processControlUI.prepareSicsButton.addActionListener(this);
                processControlUI.launchSicsButton.addActionListener(this);
                processControlUI.killSicsButton.addActionListener(this);


                add(processControlUI.checkBox);
                add(processControlUI.trigramLabel);
                add(processControlUI.transferButton);
                add(processControlUI.prepareMacsButton);
                add(processControlUI.launchMacsButton);
                add(processControlUI.killMacsButton);
                add(processControlUI.prepareSicsButton);
                add(processControlUI.launchSicsButton);
                add(processControlUI.killSicsButton);
                add(processControlUI.progressBar);
                add(processControlUI.commentsLabel);

                //init value
                if (host.getIsChecked()) {
                    processControlUI.checkBox.setSelected(true);
                }
            }
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            for (Map.Entry<String, ProcessControlUI> processControlUI : processControlUIs.entrySet()) {
                if (processControlUI.getValue().checkBox.equals(e.getSource())) {
                    System.out.println("CheckBox de la clé " + processControlUI.getKey());
                    processControlUI.getValue().host.setIsChecked(processControlUI.getValue().checkBox.isSelected());
                }

                if (processControlUI.getValue().transferButton.equals(e.getSource())) {
                    String transferRecipientTrigram =  processControlUI.getKey();
                    Enums.TransferableFileElement element = (Enums.TransferableFileElement)deployActionsPanel.transferableElementCb.getSelectedItem();
                    Enums.FileDrive sourceDrive = (Enums.FileDrive)deployActionsPanel.sourceDriveCb.getSelectedItem();
                    Enums.FileDrive destDrive = (Enums.FileDrive)deployActionsPanel.destinationDriveCb.getSelectedItem();
                    System.out.println("Transfer demandé vers " + transferRecipientTrigram + " element=" + element + " source=" + sourceDrive + " dest=" + destDrive );
                    i.transferService.initiateTransfer(transferRecipientTrigram, element, sourceDrive, destDrive );
                }

                if (processControlUI.getValue().prepareMacsButton.equals(e.getSource())) {
                    System.out.println("Prepare Macs demandé pour " + processControlUI.getKey());
                    i.processOrderService.sendPrepareRequest(i.confService.getUserTrigram(), processControlUI.getKey(), Enums.Process.MACS);
                }
                if (processControlUI.getValue().launchMacsButton.equals(e.getSource())) {
                    System.out.println("Launch Macs demandé pour " + processControlUI.getKey());
                    i.processOrderService.sendRunRequest(i.confService.getUserTrigram(), processControlUI.getKey(), Enums.Process.MACS);
                }
                if (processControlUI.getValue().killMacsButton.equals(e.getSource())) {
                    System.out.println("Kill Macs demandé pour " + processControlUI.getKey());
                    i.processOrderService.sendKillRequest(i.confService.getUserTrigram(), processControlUI.getKey(), Enums.Process.MACS);
                }
                if (processControlUI.getValue().prepareSicsButton.equals(e.getSource())) {
                    System.out.println("Prepare Sics demandé pour " + processControlUI.getKey());
                    i.processOrderService.sendPrepareRequest(i.confService.getUserTrigram(), processControlUI.getKey(), Enums.Process.SICS);

                }
                if (processControlUI.getValue().launchSicsButton.equals(e.getSource())) {
                    System.out.println("Launch Sics demandé pour " + processControlUI.getKey());
                    i.processOrderService.sendRunRequest(i.confService.getUserTrigram(), processControlUI.getKey(), Enums.Process.SICS);
                }
                if (processControlUI.getValue().killSicsButton.equals(e.getSource())) {
                    System.out.println("Kill Sics demandé pour " + processControlUI.getKey());
                    i.processOrderService.sendKillRequest(i.confService.getUserTrigram(), processControlUI.getKey(), Enums.Process.SICS);
                }
            }
        }

        class ProcessControlUI {
            JCheckBox checkBox;
            JLabel trigramLabel;
            JButton transferButton;
            JButton prepareMacsButton;
            JButton launchMacsButton;
            JButton killMacsButton;
            JButton prepareSicsButton;
            JButton launchSicsButton;
            JButton killSicsButton;
            JProgressBar progressBar;
            JLabel commentsLabel;
            Host host;
        }
    }

    class GroupedActionsPanel extends JPanel
            implements ActionListener {

        JButton groupedActionsLaunchButton;
        JButton groupedActionsTransferButton;
        JButton groupedActionsKillButton;

        JButton groupedActionsOpenConfigConfigButton;

        GroupedActionsPanel() {
            super(new GridLayout(1, 4));
            groupedActionsLaunchButton = new JButton("Launch Checked");
            groupedActionsTransferButton = new JButton("Transfer to Checked");
            groupedActionsKillButton = new JButton("Kill Checked");
            groupedActionsOpenConfigConfigButton = new JButton("Open config");

            groupedActionsLaunchButton.addActionListener(this);
            groupedActionsTransferButton.addActionListener(this);
            groupedActionsKillButton.addActionListener(this);
            groupedActionsOpenConfigConfigButton.addActionListener(this);

            add(groupedActionsLaunchButton);
            add(groupedActionsTransferButton);
            add(groupedActionsKillButton);
            add(groupedActionsOpenConfigConfigButton);
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            //Handle open button action.
            if (e.getSource() == groupedActionsLaunchButton) {
                System.out.println("LAUNCH");
            }
            if (e.getSource() == groupedActionsTransferButton) {
                System.out.println("TRANSFER");


                String transferRecipientTrigram = "DUM";
                Enums.TransferableFileElement element = Enums.TransferableFileElement.MACS_ZIP;
                Enums.FileDrive sourceDrive = Enums.FileDrive.M2;
                Enums.FileDrive destDrive2 = Enums.FileDrive.M2;
                i.transferService.initiateTransfer(transferRecipientTrigram, element, sourceDrive, destDrive2 );
            }
            if (e.getSource() == groupedActionsKillButton) {
                System.out.println("KILL");
            }
            if (e.getSource() == groupedActionsOpenConfigConfigButton) {
                System.out.println("OPEN CONFIG");
            }
        }

    }
}
