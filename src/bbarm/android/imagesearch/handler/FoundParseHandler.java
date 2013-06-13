package bbarm.android.imagesearch.handler;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import bbarm.android.imagesearch.model.FoundImage;

public class FoundParseHandler extends DefaultHandler {
	private ArrayList<FoundImage> messages;
	private FoundImage currentMessage;
	private StringBuilder builder;

	public ArrayList<FoundImage> getMessages() {
		return this.messages;
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		super.characters(ch, start, length);
		builder.append(ch, start, length);
	}

	@Override
	public void endElement(String uri, String localName, String name)
			throws SAXException {
		super.endElement(uri, localName, name);
		if (this.currentMessage != null) {
			if (localName.equalsIgnoreCase("title")) {
				currentMessage.setTitle(builder.toString().trim());
			} else if (localName.equalsIgnoreCase("author")) {
				currentMessage.setAuthor(builder.toString().trim());
			} else if (localName.equalsIgnoreCase("link")) {
				currentMessage.setLink(builder.toString().trim());
			} else if (localName.equalsIgnoreCase("item")) {
				messages.add(currentMessage);
			}
			builder.setLength(0);
		}
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		messages = new ArrayList<FoundImage>();
		builder = new StringBuilder();
	}

	@Override
	public void startElement(String uri, String localName, String name,
			Attributes attributes) throws SAXException {
		super.startElement(uri, localName, name, attributes);

		if (localName.equalsIgnoreCase("item")) {
			this.currentMessage = new FoundImage();
		} else if (localName.equalsIgnoreCase("thumbnail")) {
			currentMessage.setThumbnail(attributes.getValue("url").trim());
		} else if (localName.equalsIgnoreCase("content")) {
			currentMessage.setThumbnailBig(attributes.getValue("url").trim());
		} else if (localName.equalsIgnoreCase("source")) {
			currentMessage.setContent(attributes.getValue("url").trim());
			currentMessage.setReferer(attributes.getValue("referer").trim());
		}

	}
}
