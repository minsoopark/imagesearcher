package bbarm.android.imagesearch.parser;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.helpers.DefaultHandler;

import bbarm.android.imagesearch.handler.DeviantParseHandler;
import bbarm.android.imagesearch.handler.FoundParseHandler;

import android.content.Context;
import android.os.AsyncTask;

public class Parser {

	public static int DEVIANT = 0;
	public static int FFFFOUND = 1;

	protected ArrayList<?> list;
	protected ParseCallBack callBack;
	protected int type;
	protected Context context;
	
	public interface ParseCallBack {
		public void parseFinishedCallBack(ArrayList<?> result);
		public void parseFailedCallBack(Exception e);
	}
	
	protected Parser(Context context) {
		this.context = context;
	}

	protected void parse(InputStream is) {
		DefaultHandler handler;
		if(type == DEVIANT) {
			handler = new DeviantParseHandler();
		} else {
			handler = new FoundParseHandler();
		}
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser parser = factory.newSAXParser();
			parser.parse(is, handler);
			if(type == DEVIANT) {
				list = ((DeviantParseHandler) handler).getMessages();
			} else {
				list = ((FoundParseHandler) handler).getMessages();
			}
		} catch (Exception e) {
			e.printStackTrace();
			callBack.parseFailedCallBack(e);
		}
		
		callBack.parseFinishedCallBack(list);
	}

	protected class StreamLoader extends AsyncTask<String, Void, Boolean> {

		@Override
		protected Boolean doInBackground(String... params) {
			URL u = null;
			InputStream is = null;
			try {
				u = new URL(params[0]);
				URLConnection con = u.openConnection();
				is = con.getInputStream();
				parse(is);
			} catch (MalformedURLException e) {
				e.printStackTrace();
				return false;
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
			
			return true;
			
		}
	}
	
	protected void setType(int type) {
		this.type = type;
	}
	
	protected int getType() {
		return this.type;
	}
}
