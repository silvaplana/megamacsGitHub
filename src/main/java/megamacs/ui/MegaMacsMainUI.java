package megamacs.ui;

import megamacs.Enums;
import megamacs.Injector;
import megamacs.conf.ConfService;
import megamacs.conf.UserConf;
import megamacs.fileoperations.PathsService;
import megamacs.filetransfer.ServerTransferService;
import megamacs.filetransfer.TransferService;
import megamacs.messaging.immediate.client.NetworkSender;
import megamacs.messaging.immediate.server.NetworkObjectDispatcher;
import megamacs.messaging.immediate.server.NetworkReceiver;
import megamacs.messaging.transfer.ServerTransfer;
import megamacs.preparedata.PrepareMacsDataService;
import megamacs.preparedata.PrepareSicsDataService;
import megamacs.processOrder.ProcessOrderService;
import megamacs.runandkill.RunService;
import megamacs.tailing.*;
import megamacs.ui.userdashboardpanel.UserDashboardPanel;
import megamacs.ui.settingspanel.SettingsPanel;
import megamacs.utils.Log;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

public class MegaMacsMainUI extends JFrame implements ActionListener, ContainerListener, WindowListener, MouseListener {

    // injector && services
    Injector i = new Injector();
    ConfService confService;
    PathsService pathsService;
    TailService tailService;
    TransferService transferService;
    ProcessOrderService processOrderService;
    NetworkSender networkSender;
    NetworkReceiver networkReceiver;
    ServerTransferService serverTransferService;
    PrepareMacsDataService prepareMacsDataService;
    PrepareSicsDataService prepareSicsDataService;
    RunService runService;
    Log log;

    private static final long serialVersionUID = 1712459908855365803L;

	private final Container cont = getContentPane();
	private final JTabbedPane pane = new JTabbedPane();
	private final JMenuBar menuBar = new JMenuBar();
	private final JMenu fileMenu = new JMenu("File");
    private final JMenu userMenu = new JMenu("Users");
	private final JMenu bookmarkMenu = new JMenu("Bookmarks");

	private final JMenuItem about = new JMenuItem("About...");
	private final JMenuItem open = new JMenuItem("Open...");
	private final JMenuItem save = new JMenuItem("Save...");


    private final java.util.List<JMenuItem> menuItemUsers = new LinkedList();
    private JMenuItem currentUserMenuItem = null;

	private final JMenuItem bookmarkMenuItem = new JMenuItem("Bookmark file");
	private final JMenuItem settings = new JMenuItem("Settings");
	private JMenuItem bookmarkItem;
	private JFileChooser fileChooser;

	private final static SettingHandler settingHandler = new SettingHandler();





	public MegaMacsMainUI(boolean isDummy) {

        initServices(isDummy);

        if (isDummy) {
            setTitle("MegaMacsDummy");
        }
        else{
            setTitle("MegaMacs");
        }
		bookmarkMenuItem.setEnabled(false);
		open.addActionListener(this);
		save.setEnabled(false);
		save.addActionListener(this);
		bookmarkMenuItem.addActionListener(this);
		settings.addActionListener(this);

        initMenuUsers();

        ///////////////////////////////////////////////////////////////////////

        // fin injector
        refreshUserPanel();


        /////////////////////////////////////////////////////////////////////////

		pane.addContainerListener(this);
        pane.addChangeListener(changeListener);


		fileMenu.add(open);
		fileMenu.add(save);
		fileMenu.add(settings);
		menuBar.add(fileMenu);
        menuBar.add(userMenu);



		setBookmarkMenu();

		// about.add(aboutItem);
		about.addActionListener(this);
		menuBar.add(about);

		setJMenuBar(menuBar);
		cont.add(pane);

		setSize(1000, 800);
		addWindowListener(this);
		setVisible(true);
    }

    private void initServices(boolean isDummy){

        confService = new ConfService(i, isDummy);
        pathsService = new PathsService(i);
        transferService = new TransferService(i);
        processOrderService = new ProcessOrderService(i);
        try {
            serverTransferService = new ServerTransferService(i);
            prepareMacsDataService = new PrepareMacsDataService(i);
            prepareSicsDataService = new PrepareSicsDataService(i);
            runService = new RunService(i);
        } catch (IOException e) {
            e.printStackTrace();
        }
        tailService = new TailService(i, pane, settingHandler);
        log = new Log(i);

        // network immediate server
        NetworkObjectDispatcher networkObjectDispatcher = new NetworkObjectDispatcher(i);
        networkObjectDispatcher.onStart();
        networkReceiver = new NetworkReceiver(networkObjectDispatcher);
        networkReceiver.onStart();

        // network immediate client
        networkSender = new NetworkSender();
        networkSender.onStart();




        System.out.println("A");

        // register in injector
        i.confService =confService;
        i.pathsService = pathsService;
        i.tailService = tailService;
        i.networkSender = networkSender;
        i.serverTransferService = serverTransferService;
        i.prepareMacsDataService = prepareMacsDataService;
        i.prepareSicsDataService = prepareSicsDataService;
        i.runService = runService;
        i.transferService = transferService;
        i.processOrderService = processOrderService;
        System.out.println("B");
    }


    // create new Macs panel and settingspanel pannel. Used at init or after having changed user in megamacs.conf
    private void refreshUserPanel(){
        UserDashboardPanel userDashboardPanel = new UserDashboardPanel(i);
        SettingsPanel settingsPanel = new SettingsPanel(i);

        // save currentTab, because pane.remove makes it falsely switch
        Enums.Tab currentTabSave = i.confService.getCurrenTab();

        System.out.println("CLassPath 1" + this.getClass().getResource("../ice-cream.png"));
        System.out.println("CLassPath 2" + this.getClass().getResource("../../ice-cream.png"));
        System.out.println("CLassPath 3" + this.getClass().getResource(""));
        System.out.println("CLassPath 4" + this.getClass().getResource("./ice-cream.png"));

        pane.insertTab(i.confService.getUserName(), new ImageIcon(this.getClass().getResource("../../ice-cream.png")), userDashboardPanel, "Does nothing", 0);
        pane.insertTab("", new ImageIcon(this.getClass().getResource("../../settings.gif")), settingsPanel, "Does nothing", 1);
        i.confService.setCurrentTab(currentTabSave);



        if (Enums.Tab.MACS.equals(confService.getCurrenTab())){
            pane.setSelectedComponent(userDashboardPanel);
        }
        if (Enums.Tab.SETTINGS.equals(confService.getCurrenTab())){
            pane.setSelectedComponent(settingsPanel);
        }
    }

    private void initMenuUsers(){
        for (UserConf userConf:i.confService.getUsers().values()){
            JMenuItem menuItem = new JMenuItem(userConf.getName());
            menuItemUsers.add(menuItem);
            menuItem.addActionListener(this);
            userMenu.add(menuItem);

            if (userConf.getName().equals(i.confService.getUserName())){
                currentUserMenuItem = menuItem;
            }
        }
    }

	private void setBookmarkMenu() {
		about.removeAll();
		bookmarkMenu.removeAll();
		bookmarkMenu.add(bookmarkMenuItem);
		bookmarkMenu.addSeparator();
		for (String s : settingHandler.getBookmarks()) {
			bookmarkItem = new JMenuItem(s);
			bookmarkItem.setName("bookmark");
			bookmarkItem.addActionListener(this);
			// bookmarkItem.addMouseListener(this);
			bookmarkMenu.add(bookmarkItem);
		}
		if (pane.getTabCount() < 1) {
			// bookmarkMenuItem.setVisible(false);
			bookmarkMenuItem.setEnabled(false);
		} else {
			// bookmarkMenuItem.setVisible(true);
			bookmarkMenuItem.setEnabled(true);
		}
		this.repaint();
		//menuBar.add(bookmarkMenu);
		// about.add(aboutItem);
		menuBar.add(about);
	}

	public static void main(String[] args) {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// if (settingHandler.isShowSplash()) {
		// SwingTailSplash splash = new SwingTailSplash();
		// splash.dispose();
		// }
		MegaMacsMainUI app = new MegaMacsMainUI(false/*is not dummy*/);

		app.setDefaultCloseOperation(EXIT_ON_CLOSE);

	}

	public void actionPerformed(ActionEvent e) {


        // change user
        for (JMenuItem userMenuTem: menuItemUsers){
            if (e.getSource().equals(userMenuTem)) {
                if (userMenuTem != currentUserMenuItem) {
                    if (i.confService.setCurrentUser(userMenuTem.getText())) {
                        System.out.println("A new User is selected: name=" + i.confService.getUserName() + " currentTab=" + i.confService.getCurrenTab());

                        // save currentTab, because pane.remove makes it falsely switch
                        Enums.Tab currentTabSave = i.confService.getCurrenTab();


                        pane.remove(0);
                        System.out.println("After First remove");
                        pane.remove(0);
                        System.out.println("After Second remove");

                        i.confService.setCurrentTab(currentTabSave);

                        System.out.println("A new new  User is selected: name=" + i.confService.getUserName() + " currentTab=" + i.confService.getCurrenTab());

                        currentUserMenuItem = userMenuTem;
                        refreshUserPanel();
                    }
                }
                return;
            }
        }

		if (e.getSource().equals(bookmarkMenuItem)) {
			settingHandler.getBookmarks().add(((SwingTailTab) pane.getTabComponentAt(pane.getSelectedIndex())).getFile().getAbsolutePath());
			setBookmarkMenu();
		}
		else if (e.getSource().equals(open)) {
			fileChooser = new JFileChooser();
			int result = fileChooser.showOpenDialog(pane);
			if (result == JFileChooser.APPROVE_OPTION) {
				// bookmarkMenuItem.setVisible(true);
				bookmarkMenuItem.setEnabled(true);
				File file = fileChooser.getSelectedFile();
				try {
					@SuppressWarnings("unused")
                    SwingTailTab tab = new SwingTailTab(pane, file, settingHandler);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		else if (e.getSource().equals(save)) {
			fileChooser = new JFileChooser();
			int result = fileChooser.showSaveDialog(pane);
			if (result == JFileChooser.APPROVE_OPTION) {
				File file = fileChooser.getSelectedFile();
				// RandomAccessFile rand;
				FileWriter rand;
				try {
					// rand = new RandomAccessFile(file,"rw");
					rand = new FileWriter(file);
					rand.write(((SwingTailTab) pane.getTabComponentAt(pane.getSelectedIndex())).getText());
					rand.close();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}

		else if (e.getSource().equals(settings)) {
			new SettingsDialog(settingHandler, pane);
		}

		else if (e.getSource().equals(about)) {
			new AboutScreen();
		}
        else {
            System.out.println(e.getModifiers());
            if (e.getModifiers() != 4) {
                try {

                    File file = new File(e.getActionCommand());
                    @SuppressWarnings("unused")
                    SwingTailTab tab = new SwingTailTab(pane, file, settingHandler);
                    setBookmarkMenu();
                } catch (FileNotFoundException e1) {
                    JOptionPane.showMessageDialog(this, "File " + e.getActionCommand() + " not found", "Unable to open file", JOptionPane.ERROR_MESSAGE);
                    pane.removeTabAt(pane.getTabCount() - 1);
                }
            } else {
                ArrayList<String> bookmarks = settingHandler.getBookmarks();
                for (int i = 0; i < bookmarks.size(); i++) {
                    if (bookmarks.get(i).equals(e.getActionCommand())) {
                        bookmarks.remove(i);
                    }
                }
                setBookmarkMenu();
            }
        }
	}

	public void componentAdded(ContainerEvent e) {
		bookmarkMenuItem.setEnabled(true);
		save.setEnabled(true);
	}

	public void componentRemoved(ContainerEvent e) {
		if (((JTabbedPane) e.getSource()).getTabCount() == 0) {
			// bookmarkMenuItem.setVisible(false);
			bookmarkMenuItem.setEnabled(false);
			save.setEnabled(false);
		}
	}

	@Override
	public void windowActivated(WindowEvent arg0) {}

	@Override
	public void windowClosed(WindowEvent arg0) {

	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		settingHandler.saveSettings();
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {}

	@Override
	public void windowDeiconified(WindowEvent arg0) {}

	@Override
	public void windowIconified(WindowEvent arg0) {}

	@Override
	public void windowOpened(WindowEvent arg0) {}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {
	// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
	// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			try {
				File file = new File(((JMenuItem) e.getSource()).getText());
				@SuppressWarnings("unused")
                SwingTailTab tab = new SwingTailTab(pane, file, settingHandler);
				// bookmarkMenuItem.setVisible(true);
				bookmarkMenuItem.setEnabled(true);

				setBookmarkMenu();
			} catch (FileNotFoundException e1) {
				JOptionPane.showMessageDialog(this, "File " + ((JMenuItem) e.getSource()).getText() + " not found", "Unable to open file", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {}


    ChangeListener changeListener = new ChangeListener() {
        public void stateChanged(ChangeEvent changeEvent) {
            JTabbedPane sourceTabbedPane = (JTabbedPane) changeEvent.getSource();
            int index = sourceTabbedPane.getSelectedIndex();

            if (index ==0){
                i.confService.setCurrentTab(Enums.Tab.MACS);
                System.out.println("Tab changed to MACS");
            }
            if (index ==1){
                i.confService.setCurrentTab(Enums.Tab.SETTINGS);
                System.out.println("Tab changed to SETTINGS");
            }
        }
    };



}