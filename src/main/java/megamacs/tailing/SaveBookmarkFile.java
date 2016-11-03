package megamacs.tailing;

import java.io.*;
import java.util.ArrayList;

public class SaveBookmarkFile {

	private File file = null;
	private FileWriter bookmarkFile;
    private BufferedWriter out;
    private FileReader readBookmarks;
    private BufferedReader in;

	public SaveBookmarkFile(){

	}

	public void setBookmarks(File inFil){
		file = inFil;
		boolean finnsFil = false;
		ArrayList<String> fil = getBookmarks();
		for (String s : fil)
		{
			if (file.getAbsolutePath().equals(s)){
				finnsFil = true;
			}
		}
		if(!finnsFil){
			try {
				bookmarkFile = new FileWriter("bookmarks",true);
			} catch (IOException e) {
				e.printStackTrace();
			}
			out = new BufferedWriter(bookmarkFile);
			try {
				//out.write(file.getName() + ";" + file.getAbsolutePath() + "\n");
				out.write(file.getAbsolutePath() + "\n");
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public ArrayList<String> getBookmarks(){
		ArrayList<String> bookmarks = new ArrayList<String>();
		try {
			readBookmarks = new FileReader("./bookmarks");
			in = new BufferedReader(readBookmarks);
			String s = new String();
			while ((s = in.readLine()) != null){
				bookmarks.add(s);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bookmarks;
	}
}
