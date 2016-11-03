package megamacs.tailing;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class SettingHandler {
	private int rowsToRead = 50;
	private int rowsToShow = 200;
	private int polltimeMS = 500;
	private Font settingsFont = new Font("Arial",0,12);
	private boolean lineWrap = false;
	private boolean showSplash = true;
	private final ArrayList<String> bookmarks = new ArrayList<String>();

	public SettingHandler() {
		File settingsFile = new File("./settingspanel.xml");
		if (!settingsFile.exists()){
			saveSettings();
		}
		loadSettings();
	}

	public int getRowsToRead() {
		return rowsToRead;
	}
	public void setRowsToRead(int rowsToRead) {
		this.rowsToRead = rowsToRead;
	}
	public int getRowsToShow() {
		return rowsToShow;
	}
	public void setRowsToShow(int rowsToShow) {
		this.rowsToShow = rowsToShow;
	}
	public Font getSettingsFont() {
		return settingsFont;
	}
	public void setSettingsFont(Font settingsFont) {
		this.settingsFont = settingsFont;
	}
	public boolean isLineWrap() {
		return lineWrap;
	}
	public void setLineWrap(boolean lineWrap) {
		this.lineWrap = lineWrap;
	}
	public void setShowSplash(boolean showSplash) {
		this.showSplash = showSplash;
	}

	public boolean isShowSplash() {
		return showSplash;
	}

	public ArrayList<String> getBookmarks() {
		return bookmarks;
	}

	public void setPolltimeMS(int polltimeMS) {
		this.polltimeMS = polltimeMS;
	}

	public int getPolltimeMS() {
		return polltimeMS;
	}

	private void loadSettings(){

		//Try to read setting from config file
		try {
			Document doc = XMLUtils.parseXmlFile("settingspanel.xml", false);

			XPath xPath = XPathFactory.newInstance().newXPath();

			// rowsToRead
			Double doubleRowsToRead = (Double) xPath.evaluate("/swingtail/settings/rowsToRead", doc, XPathConstants.NUMBER);

			if(doubleRowsToRead != null){
				int rowsToRead = doubleRowsToRead.intValue();

				if(rowsToRead >= 0){
					this.rowsToRead = rowsToRead;
				}
			}

			// rowsToShow
			Double doubleRowsToShow = (Double) xPath.evaluate("/swingtail/settings/rowsToShow", doc, XPathConstants.NUMBER);

			if(doubleRowsToShow != null){
				int rowsToShow = doubleRowsToShow.intValue();

				if(rowsToShow >= 0){
					this.rowsToShow = rowsToShow;
				}
			}
			// polltime
			Double doublepolltime = (Double) xPath.evaluate("/swingtail/settings/polltime", doc, XPathConstants.NUMBER);

			if(doublepolltime != null){
				int polltime = doublepolltime.intValue();

				if(polltime >= 0){
					polltimeMS = polltime;
				}
			}

			// lineWrap
			String stringLineWrap = (String) xPath.evaluate("/swingtail/settings/lineWrap", doc, XPathConstants.STRING);
			if (stringLineWrap != null){
				if (stringLineWrap.equalsIgnoreCase("true")){
					lineWrap=true;
				}else{
					lineWrap=false;
				}
			}

			// showSplash
			String stringShowSplash = (String) xPath.evaluate("/swingtail/settings/showSplash", doc, XPathConstants.STRING);
			if (stringShowSplash != null){
				if (stringShowSplash.equalsIgnoreCase("true")){
					showSplash=true;
				}else{
					showSplash=false;
				}
			}

			// bookmarks
			NodeList bookmarkNodeList = (NodeList) xPath.evaluate("/swingtail/bookmarks/*[name()='bookmark']", doc, XPathConstants.NODESET);
			bookmarks.clear();
			for (int i=0; i<bookmarkNodeList.getLength(); i++){
				bookmarks.add(bookmarkNodeList.item(i).getTextContent());
			}

			// font
			String name = (String) xPath.evaluate("/swingtail/settings/font/fontName", doc, XPathConstants.STRING);
			Double doubleStyle = (Double) xPath.evaluate("/swingtail/settings/font/fontStyle", doc, XPathConstants.NUMBER);
			Double doubleSize = (Double) xPath.evaluate("/swingtail/settings/font/fontSize", doc, XPathConstants.NUMBER);

			if (name != null && doubleStyle != null && doubleSize != null){
				int style = doubleStyle.intValue();
				int size = doubleSize.intValue();

				if (style >= 0 && size > 0){
					settingsFont = new Font(name,style,size);
				}
			}

		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}

	}

	public void saveSettings(){
		Document doc = XMLUtils.createDomDocument();

		// Root
		Element swingtailElement = doc.createElement("swingtail");
		doc.appendChild(swingtailElement);

		// Settings
		Element configElement = doc.createElement("settingspanel");
		swingtailElement.appendChild(configElement);

		// Rows to read
		configElement.appendChild(XMLUtils.createElement("rowsToRead", rowsToRead + "", doc));
		// Rows to show
		configElement.appendChild(XMLUtils.createElement("rowsToShow", rowsToShow + "", doc));
		// Line wrap
		configElement.appendChild(XMLUtils.createElement("lineWrap", lineWrap + "", doc));
		// Show splash
		configElement.appendChild(XMLUtils.createElement("showSplash", showSplash + "", doc));
		// polltime
		configElement.appendChild(XMLUtils.createElement("polltime", polltimeMS + "", doc));

		// Font
		Element fontElement = doc.createElement("font");
		configElement.appendChild(fontElement);

		// Font name
		fontElement.appendChild(XMLUtils.createElement("fontName", settingsFont.getFontName(), doc));
		// Font style
		fontElement.appendChild(XMLUtils.createElement("fontStyle", settingsFont.getStyle() + "", doc));
		// Font size
		fontElement.appendChild(XMLUtils.createElement("fontSize", settingsFont.getSize() + "", doc));

		// Bookmarks
		Element bookmarksElement = doc.createElement("bookmarks");
		swingtailElement.appendChild(bookmarksElement);

		for(String filepath : bookmarks){
			// Bookmark
			bookmarksElement.appendChild(XMLUtils.createCDATAElement("bookmark", filepath, doc));
		}

		try {
			XMLUtils.writeXmlFile(doc, "settingspanel.xml", true);
		} catch (TransformerFactoryConfigurationError e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}
}
