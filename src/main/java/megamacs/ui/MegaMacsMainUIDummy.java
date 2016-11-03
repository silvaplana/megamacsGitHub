package megamacs.ui;


import javax.swing.*;

public class MegaMacsMainUIDummy{

    public static final int EXIT_ON_CLOSE = 3;

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
		MegaMacsMainUI app = new MegaMacsMainUI(true/*is dummy*/);

		app.setDefaultCloseOperation(EXIT_ON_CLOSE);

	}



}