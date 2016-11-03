package megamacs.tailing;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;


public class FileMonitor implements Runnable {
	private final File inFil;
	private final RandomAccessFile file;
	private final JTextArea text;
	private boolean pause = false;
	private boolean closeThread = false;
	private long placeInFile = 0;
	private final Thread thread;
	private final SettingHandler settingHandler;

	public FileMonitor(final JTextArea inText,final File inFile, SettingHandler settingHandler) throws FileNotFoundException {
		inFil = inFile;
		file = new RandomAccessFile(inFile,"r");
		text = inText;
		this.settingHandler = settingHandler;

		thread = new Thread(this);
		thread.start();
	}

	public synchronized void run() {
		String inText = new String();
		try {
			if(file.length() > 0 && settingHandler.getRowsToRead() > 0){

				int linesFound = 0;

				placeInFile = file.length();

				outer: while(placeInFile > 0){

					byte[] buffer;

					if(placeInFile > 1024){
						file.seek(placeInFile - 1024);
						buffer = new byte[1024];
						file.read(buffer);
					}else{
						file.seek(0);
						buffer = new byte[(int)placeInFile];
						file.read(buffer, 0, (int)placeInFile);
					}

					String currentText = new String(buffer);

					for(int i = currentText.length(); i > 0; i--){

						placeInFile--;

						if(currentText.charAt(i-1) == '\n'){
							linesFound++;

							if(linesFound == (settingHandler.getRowsToRead() + 1)){
								placeInFile++;
								break outer;
							}
						}
					}
				}
				file.seek(placeInFile);
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}


		while(true){
			if (closeThread){
				try {
					file.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return;

			}else if (pause){
				try {
					this.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{
				try {

					if (placeInFile>file.length()){
						placeInFile = 0;
						file.seek(placeInFile);
					}

					while((inText = file.readLine()) != null){

						text.append(inText + "\n");
						text.setCaretPosition( text.getText().length() );
						placeInFile = file.getFilePointer();
					}
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, this, "Error reading file " + e.getMessage(), JOptionPane.ERROR_MESSAGE);
				}

				try {
					Thread.sleep(settingHandler.getPolltimeMS());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void setPause(boolean pause) {

		if(!closeThread){
			this.pause = pause;

			if(!pause && thread.getState() == Thread.State.WAITING){
				synchronized(this){
					notifyAll();
				}
			}
		}
	}

	public boolean isPause() {
		return pause;
	}
	public boolean isCloseThread() {
		return closeThread;
	}

	public void closeThread() {
		closeThread = true;
		setPause(false);
	}
	public File getFile(){
		return inFil;
	}
}
