package megamacs.tailing;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;

public class RowLimitedTextArea extends JTextArea {

	private static final long serialVersionUID = 6003196936961930721L;
	int maxrows = 5;

	public RowLimitedTextArea() {
		super();
	}

	public RowLimitedTextArea(Document doc, String text, int rows, int columns) {
		super(doc, text, rows, columns);
	}

	public RowLimitedTextArea(Document doc) {
		super(doc);
	}

	public RowLimitedTextArea(int rows, int columns) {
		super(rows, columns);
	}

	public RowLimitedTextArea(String text, int rows, int columns) {
		super(text, rows, columns);
	}

	public RowLimitedTextArea(String text) {
		super(text);
	}

	public int getMaxrows() {
		return maxrows;
	}

	public void setMaxrows(int maxrows) {
		this.maxrows = maxrows;
	}

	@Override
	public void append(String str) {

		trim();

		super.append(str);
	}

	protected void trim() {

		Document doc = getDocument();

		Element map = doc.getDefaultRootElement();

		while(map.getElementCount() > maxrows){
			Element firstrow = map.getElement(0);

			try {
				doc.remove(firstrow.getStartOffset(), firstrow.getEndOffset()-firstrow.getStartOffset());
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
		}
	}
}
